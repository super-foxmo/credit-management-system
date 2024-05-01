package com.ruoyi.system.service.impl;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.domain.entity.ScoreEvaluationQueryParams;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.IStudentEvaluationService;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.UserStudentMapper;
import com.ruoyi.system.domain.UserStudent;
import com.ruoyi.system.service.IUserStudentService;

import javax.annotation.Resource;

/**
 * 学生信息Service业务层处理
 *
 * @author foxmo
 * @date 2024-03-15
 */
@Service
public class UserStudentServiceImpl implements IUserStudentService {
    @Resource
    private UserStudentMapper userStudentMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private IStudentEvaluationService studentEvaluationService;

    /**
     * 查询学生信息
     *
     * @param studentId 学生信息主键
     * @return 学生信息
     */
    @Override
    public UserStudent selectUserStudentByStudentId(Long studentId) {
        return userStudentMapper.selectOne(new LambdaQueryWrapper<UserStudent>()
                .eq(UserStudent::getStudentId, studentId)
                .eq(UserStudent::getStatus, "0")
                .eq(UserStudent::getDelFlag, false));
    }

    /**
     * 查询学生信息
     *
     * @param studentIds 学生信息主键集合
     * @return 学生信息集合
     */
    @Override
    public List<UserStudent> selectUserStudentListByStudentIds(List<Long> studentIds) {
        return userStudentMapper.selectList(new LambdaQueryWrapper<UserStudent>()
                .in(UserStudent::getStudentId, studentIds)
                .eq(UserStudent::getStatus, "0")
                .eq(UserStudent::getDelFlag, false));
    }

    /**
     * 根据用户id查询状态开启的学生信息
     *
     * @param userId 用户主键
     * @return 学生信息
     */
    @Override
    public UserStudent selectEnableStudentByUserId(Long userId){
        return userStudentMapper.selectOne(new LambdaQueryWrapper<UserStudent>()
                .eq(UserStudent::getUserId, userId)
                .eq(UserStudent::getStatus, "0")
                .eq(UserStudent::getDelFlag, false));
    }

    /**
     * 查询学生信息列表
     *
     * @param userStudent 学生信息
     * @return 学生信息
     */
    @Override
    public List<UserStudent> selectUserStudentList(UserStudent userStudent) {
        return userStudentMapper.selectUserStudentList(userStudent);
    }

    /**
     * 新增学生信息
     *
     * @param userStudent 学生信息
     * @return 结果
     */
    @Override
    public int insertUserStudent(UserStudent userStudent) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        userStudent.setPassword(SecurityUtils.encryptPassword(userStudent.getPassword()));
        userStudent.setCreateBy(loginUser.getUsername());
        userStudent.setCreateTime(DateUtils.getNowDate());
        // 同时创建系统用户
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userStudent.getStudentName());
        sysUser.setNickName(userStudent.getStudentName());
        sysUser.setUserType("02");
        sysUser.setEmail(userStudent.getEmail());
        sysUser.setPhonenumber(userStudent.getPhoneNumber());
        sysUser.setSex(userStudent.getSex());
        sysUser.setAvatar(userStudent.getAvatar());
        sysUser.setPassword(userStudent.getPassword());
        sysUser.setCreateBy(loginUser.getUsername());
        sysUser.setCreateTime(DateUtils.getNowDate());
        sysUserMapper.insert(sysUser);
        // 同时给学生用户绑定学生角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getUserId());
        // 学生角色
        sysUserRole.setRoleId(101L);
        sysUserRoleMapper.insert(sysUserRole);
        userStudent.setUserId(sysUser.getUserId());
        int result = userStudentMapper.insert(userStudent);
        // 初始化学生所有学年的综测信息
        if (result > 0){
            studentEvaluationService.initStudentEvaluation(userStudent);
        }
        return result;
    }

    /**
     * 修改学生信息
     *
     * @param userStudent 学生信息
     * @return 结果
     */
    @Override
    public int updateUserStudent(UserStudent userStudent) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        userStudent.setUpdateBy(loginUser.getUsername());
        userStudent.setUpdateTime(DateUtils.getNowDate());
        // 同时修改系统用户
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserId, userStudent.getUserId())
                .eq(SysUser::getUserType, "02")
                .eq(SysUser::getDelFlag, false));
        sysUser.setUserName(userStudent.getStudentName());
        sysUser.setNickName(userStudent.getStudentName());
        sysUser.setUserType("02");
        sysUser.setEmail(userStudent.getEmail());
        sysUser.setPhonenumber(userStudent.getPhoneNumber());
        sysUser.setSex(userStudent.getSex());
        sysUser.setAvatar(userStudent.getAvatar());
        sysUser.setPassword(userStudent.getPassword());
        sysUser.setUpdateBy(loginUser.getUsername());
        sysUser.setUpdateTime(DateUtils.getNowDate());
        sysUserMapper.update(sysUser, new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserId, sysUser.getUserId()));
        return userStudentMapper.updateUserStudent(userStudent);
    }

    /**
     * 批量删除学生信息
     *
     * @param studentIds 需要删除的学生信息主键
     * @return 结果
     */
    @Override
    public int deleteUserStudentByStudentIds(Long[] studentIds) {
        List<UserStudent> userStudentList = userStudentMapper.selectList(new LambdaQueryWrapper<UserStudent>()
                .in(UserStudent::getStudentId, studentIds)
                .eq(UserStudent::getDelFlag, false));
        int count = 0;
        for (UserStudent userStudent : userStudentList) {
            LoginUser loginUser = SecurityUtils.getLoginUser();
            userStudent.setUpdateBy(loginUser.getUsername());
            userStudent.setUpdateTime(DateUtils.getNowDate());
            userStudent.setDelFlag(true);
            // 同时删除系统用户和角色绑定
            SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                    .eq(SysUser::getUserId, userStudent.getUserId())
                    .eq(SysUser::getUserType, "02")
                    .eq(SysUser::getDelFlag, false));
            if (ObjectUtil.isNotEmpty(sysUser)) {
                sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, sysUser.getUserId()));

                sysUser.setDelFlag(true);
                sysUser.setUpdateBy(loginUser.getUsername());
                sysUser.setUpdateTime(DateUtils.getNowDate());
                sysUserMapper.update(sysUser, new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUserId, sysUser.getUserId()));
            }
            // 同时删除学生所有学年的综测信息
            studentEvaluationService.deleteStudentEvaluation(userStudent.getStudentId());
            // 删除学生
            count += userStudentMapper.update(userStudent, new LambdaQueryWrapper<UserStudent>()
                    .eq(UserStudent::getStudentId, userStudent.getStudentId()));
        }
        return count;
    }

    /**
     * 删除学生信息信息
     *
     * @param studentId 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteUserStudentByStudentId(Long studentId) {
        UserStudent userStudent = userStudentMapper.selectOne(new LambdaQueryWrapper<UserStudent>()
                .eq(UserStudent::getStudentId, studentId)
                .eq(UserStudent::getDelFlag, false));
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserId, userStudent.getUserId())
                .eq(SysUser::getDelFlag, false));
        LoginUser loginUser = SecurityUtils.getLoginUser();
        sysUser.setUpdateBy(loginUser.getUsername());
        sysUser.setUpdateTime(DateUtils.getNowDate());
        sysUser.setDelFlag(true);
        sysUserMapper.update(sysUser, new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserId, sysUser.getUserId()));
        userStudent.setUpdateBy(loginUser.getUsername());
        userStudent.setUpdateTime(DateUtils.getNowDate());
        userStudent.setDelFlag(true);
        return userStudentMapper.update(userStudent, new LambdaQueryWrapper<UserStudent>()
                .eq(UserStudent::getStudentId, userStudent.getStudentId()));
    }

    /**
     * 查询指定专业和学年的学生信息
     *
     * @param queryParams 查询条件
     * @return 学生信息集合
     */
    @Override
    public List<UserStudent> selectStudentBySpecialtyIdAndAcademicYear(ScoreEvaluationQueryParams queryParams){
        return userStudentMapper.selectStudentBySpecialtyIdAndAcademicYear(queryParams);
    }
}
