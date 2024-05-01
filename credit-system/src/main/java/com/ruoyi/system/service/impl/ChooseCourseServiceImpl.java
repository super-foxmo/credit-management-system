package com.ruoyi.system.service.impl;

import java.util.List;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.StudentSubject;
import com.ruoyi.system.domain.SubjectInfo;
import com.ruoyi.system.domain.UserStudent;
import com.ruoyi.system.mapper.StudentSubjectMapper;
import com.ruoyi.system.mapper.SubjectInfoMapper;
import com.ruoyi.system.mapper.UserStudentMapper;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.IChooseCourseService;

import javax.annotation.Resource;

/**
 * 选课中心Service业务层处理
 *
 * @author foxmo
 * @date 2024-03-21
 */
@Service
public class ChooseCourseServiceImpl implements IChooseCourseService {
    @Resource
    private SubjectInfoMapper subjectInfoMapper;

    @Resource
    private UserStudentMapper userStudentMapper;

    @Resource
    private StudentSubjectMapper studentSubjectMapper;

    /**
     * 查询选课中心
     *
     * @param subjectId 选课中心主键
     * @return 选课中心
     */
    @Override
    public SubjectInfo selectChooseCourseBySubjectId(Long subjectId) {
        return subjectInfoMapper.selectSubjectInfoBySubjectId(subjectId);
    }

    /**
     * 查询登录学生所属专业的可以选修学科信息列表（同一学年，同一学院，同一专业或公共选修）
     *
     * @param subjectInfo 选课中心
     * @return 选课中心
     */
    @Override
    public List<SubjectInfo> selectChooseCourseList(SubjectInfo subjectInfo) {
        return subjectInfoMapper.selectEnableSubjectInfoList(subjectInfo);
    }

    /**
     * 根据查询条件查询登录学生所属专业的所有选修学科信息列表
     *
     * @param subjectInfo 选课中心
     * @return 选课中心
     */
    @Override
    public List<SubjectInfo> selectChooseCourseListByQueryParams(SubjectInfo subjectInfo) {
        return subjectInfoMapper.selectChooseCourseListByQueryParams(subjectInfo);
    }

    /**
     * 新增选课
     *
     * @param studentSubject 选课信息
     * @return 结果
     */
    @Override
    public int insertChooseCourse(StudentSubject studentSubject) {
        return studentSubjectMapper.insert(studentSubject);
    }

    /**
     * 修改选课中心
     *
     * @param subjectInfo 选课中心
     * @return 结果
     */
    @Override
    public int updateChooseCourse(SubjectInfo subjectInfo) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        subjectInfo.setUpdateBy(loginUser.getUsername());
        subjectInfo.setUpdateTime(DateUtils.getNowDate());
        return subjectInfoMapper.updateSubjectInfo(subjectInfo);
    }

    /**
     * 批量删除选课中心
     *
     * @param subjectIds 需要删除的选课中心主键
     * @return 结果
     */
    @Override
    public int deleteChooseCourseBySubjectIds(Long[] subjectIds) {
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
     * 删除选课中心信息
     *
     * @param subjectId 选课中心主键
     * @return 结果
     */
    @Override
    public int deleteChooseCourseBySubjectId(Long subjectId) {
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
