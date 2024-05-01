package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.SubjectType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.StudentSubject;
import com.ruoyi.system.domain.UserStudent;
import com.ruoyi.system.mapper.StudentSubjectMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.UserStudentMapper;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SubjectInfoMapper;
import com.ruoyi.system.domain.SubjectInfo;
import com.ruoyi.system.service.ISubjectInfoService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.security.auth.Subject;

/**
 * 学科信息Service业务层处理
 *
 * @author foxmo
 * @date 2024-03-18
 */
@Service
public class SubjectInfoServiceImpl implements ISubjectInfoService {
    @Resource
    private SubjectInfoMapper subjectInfoMapper;

    @Resource
    private UserStudentMapper studentMapper;

    @Resource
    private StudentSubjectMapper studentSubjectMapper;

    @Resource
    private MapperFacade mapperFacade;

    /**
     * 查询学科信息
     *
     * @param subjectId 学科信息主键
     * @return 学科信息
     */
    @Override
    public SubjectInfo selectSubjectInfoBySubjectId(Long subjectId) {
        return subjectInfoMapper.selectOne(new LambdaQueryWrapper<SubjectInfo>()
                .eq(SubjectInfo::getSubjectId, subjectId)
                .eq(SubjectInfo::getStatus, "0")
                .eq(SubjectInfo::getDelFlag, false));
    }

    /**
     * 查询学科信息列表
     *
     * @param subjectInfo 学科信息
     * @return 学科信息
     */
    @Override
    public List<SubjectInfo> selectSubjectInfoList(SubjectInfo subjectInfo) {
        return subjectInfoMapper.selectSubjectInfoList(subjectInfo);
    }

    /**
     * 新增学科信息
     *
     * @param subjectInfo 学科信息
     * @return 结果
     */
    @Override
    public int insertSubjectInfo(SubjectInfo subjectInfo) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        subjectInfo.setCreateBy(loginUser.getUsername());
        subjectInfo.setCreateTime(DateUtils.getNowDate());
        subjectInfoMapper.insertSubjectInfo(subjectInfo);
        // 创建的学科为专业必修时，相关专业的学生课程中默认选中该课程，(即同时创建选课信息)
        String subjectType = subjectInfo.getSubjectType();
        if (subjectType.equals(SubjectType.MAJOR_COMPULSORY_COURSE.getCode())) {
            Long specialtyId = subjectInfo.getSpecialtyId();
            List<UserStudent> studentList = studentMapper.selectList(new LambdaQueryWrapper<UserStudent>()
                    .eq(UserStudent::getSpecialtyId, specialtyId)
                    .eq(UserStudent::getStatus, "0")
                    .eq(UserStudent::getDelFlag, false));
//            ArrayList<UserStudent> matchStudentList = new ArrayList<>();
//            Long subjectInfoAcademicYear = subjectInfo.getAcademicYear();
//            for (UserStudent student : studentList) {
//                // 相同学年的学生才需要添加
//                Long academicYear = DateUtil.betweenYear(student.getEntranceTime(), DateUtils.getNowDate(), false) + 1L;
//                if (academicYear.equals(subjectInfoAcademicYear)) {
//                    matchStudentList.add(student);
//                }
//            }
            StudentSubject studentSubject = mapperFacade.map(subjectInfo, StudentSubject.class);
            for (UserStudent student : studentList) {
                studentSubject.setStudentId(student.getStudentId());
                studentSubject.setStudentName(student.getStudentName());
                studentSubject.setStudentNumber(student.getStudentNumber());
                studentSubject.setCreateBy(loginUser.getUsername());
                studentSubject.setCreateTime(DateUtils.getNowDate());

                studentSubjectMapper.insert(studentSubject);
            }
        }

        return 1;
    }

    /**
     * 修改学科信息
     *
     * @param subjectInfo 学科信息
     * @return 结果
     */
    @Override
    public int updateSubjectInfo(SubjectInfo subjectInfo) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        subjectInfo.setUpdateBy(loginUser.getUsername());
        subjectInfo.setUpdateTime(DateUtils.getNowDate());
        return subjectInfoMapper.updateSubjectInfo(subjectInfo);
    }

    /**
     * 批量删除学科信息
     *
     * @param subjectIds 需要删除的学科信息主键
     * @return 结果
     */
    @Override
    public int deleteSubjectInfoBySubjectIds(Long[] subjectIds) {
        List<SubjectInfo> subjectInfoList = subjectInfoMapper.selectList(new LambdaQueryWrapper<SubjectInfo>()
                .in(SubjectInfo::getSubjectId, subjectIds)
                .eq(SubjectInfo::getStatus, "0")
                .eq(SubjectInfo::getDelFlag, false));
        int count = 0;
        LoginUser loginUser = SecurityUtils.getLoginUser();
        for (SubjectInfo subjectInfo : subjectInfoList) {
            subjectInfo.setUpdateBy(loginUser.getUsername());
            subjectInfo.setUpdateTime(DateUtils.getNowDate());
            subjectInfo.setDelFlag(true);
            count += subjectInfoMapper.update(subjectInfo, new LambdaQueryWrapper<SubjectInfo>()
                    .eq(SubjectInfo::getSubjectId, subjectInfo.getSubjectId()));
        }
        return count;
    }

    /**
     * 删除学科信息信息
     *
     * @param subjectId 学科信息主键
     * @return 结果
     */
    @Override
    public int deleteSubjectInfoBySubjectId(Long subjectId) {
        SubjectInfo subjectInfo = subjectInfoMapper.selectOne(new LambdaQueryWrapper<SubjectInfo>()
                .eq(SubjectInfo::getSubjectId, subjectId)
                .eq(SubjectInfo::getStatus, "0")
                .eq(SubjectInfo::getDelFlag, false));
        LoginUser loginUser = SecurityUtils.getLoginUser();
        subjectInfo.setUpdateBy(loginUser.getUsername());
        subjectInfo.setUpdateTime(DateUtils.getNowDate());
        subjectInfo.setDelFlag(true);
        return subjectInfoMapper.update(subjectInfo, new LambdaQueryWrapper<SubjectInfo>()
                .eq(SubjectInfo::getSubjectId, subjectInfo.getSubjectId()));
    }
}
