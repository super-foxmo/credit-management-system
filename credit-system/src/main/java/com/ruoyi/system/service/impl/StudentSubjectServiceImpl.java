package com.ruoyi.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.UserStudent;
import com.ruoyi.system.service.IUserStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.StudentSubjectMapper;
import com.ruoyi.system.domain.StudentSubject;
import com.ruoyi.system.service.IStudentSubjectService;

import javax.annotation.Resource;

/**
 * 成绩管理表Service业务层处理
 * 
 * @author foxmo
 * @date 2024-03-19
 */
@Service
public class StudentSubjectServiceImpl implements IStudentSubjectService 
{
    @Resource
    private StudentSubjectMapper studentSubjectMapper;

    @Resource
    private IUserStudentService studentService;

    /**
     * 查询成绩管理表
     * 
     * @param id 成绩管理表主键
     * @return 成绩管理表
     */
    @Override
    public StudentSubject selectStudentSubjectById(Long id)
    {
        return studentSubjectMapper.selectOne(new LambdaQueryWrapper<StudentSubject>()
                .eq(StudentSubject::getId, id)
                .eq(StudentSubject::getStatus, "0")
                .eq(StudentSubject::getDelFlag, false));
    }

    /**
     * 根据 ids 查询成绩管理表集合
     *
     * @param ids 成绩管理表主键数组
     * @return 成绩管理表
     */
    @Override
    public List<StudentSubject> selectStudentSubjectListByIds(Long[] ids)
    {
        return studentSubjectMapper.selectList(new LambdaQueryWrapper<StudentSubject>()
                .in(StudentSubject::getId, ids)
                .eq(StudentSubject::getStatus, "0")
                .eq(StudentSubject::getDelFlag, false));
    }

    /**
     * 查询指定学生是否选修某课程
     *
     * @param studentId 学生主键
     * @param subjectId 学科主键
     * @return 成绩管理表
     */
    @Override
    public StudentSubject selectStudentSubjectByStudentIdAndSubjectId(Long studentId, Long subjectId){
        return studentSubjectMapper.selectOne(new LambdaQueryWrapper<StudentSubject>()
                .eq(StudentSubject::getStudentId, studentId)
                .eq(StudentSubject::getSubjectId, subjectId)
                .eq(StudentSubject::getStatus, "0")
                .eq(StudentSubject::getDelFlag, false));
    }

    /**
     * 查询指定学生某一学年的所有课程列表
     *
     * @param studentId 学生主键
     * @param academicYear 学年
     * @return 成绩管理表
     */
    @Override
    public List<StudentSubject> selectStudentSubjectsByStudentIdAndAcademicYear(Long studentId, Long academicYear){
        return studentSubjectMapper.selectList(new LambdaQueryWrapper<StudentSubject>()
                .eq(StudentSubject::getStudentId, studentId)
                .eq(StudentSubject::getAcademicYear, academicYear)
                .eq(StudentSubject::getStatus, "0")
                .eq(StudentSubject::getDelFlag, false));
    }

    /**
     * 查询成绩管理表列表
     * 
     * @param studentSubject 成绩管理表
     * @return 成绩管理表
     */
    @Override
    public List<StudentSubject> selectStudentSubjectList(StudentSubject studentSubject)
    {
        return studentSubjectMapper.selectStudentSubjectList(studentSubject);
    }


    /**
     * 新增成绩管理表
     *
     * @param studentSubject 成绩管理表
     * @return 结果
     */
    @Override
    public int insertStudentSubject(StudentSubject studentSubject)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        studentSubject.setCreateBy(loginUser.getUsername());
        studentSubject.setCreateTime(DateUtils.getNowDate());
        return studentSubjectMapper.insertStudentSubject(studentSubject);
    }

    /**
     * 修改成绩管理表
     *
     * @param studentSubject 成绩管理表
     * @return 结果
     */
    @Override
    public int updateStudentSubject(StudentSubject studentSubject)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        studentSubject.setUpdateBy(loginUser.getUsername());
        studentSubject.setUpdateTime(DateUtils.getNowDate());
        return studentSubjectMapper.updateStudentSubject(studentSubject);
    }

    /**
     * 批量删除成绩管理表
     *
     * @param ids 需要删除的成绩管理表主键
     * @return 结果
     */
    @Override
    public int deleteStudentSubjectByIds(Long[] ids)
    {
        List<StudentSubject> studentSubjectList = studentSubjectMapper.selectList(new LambdaQueryWrapper<StudentSubject>()
                .in(StudentSubject::getId, ids)
                .eq(StudentSubject::getStatus, "0")
                .eq(StudentSubject::getDelFlag, false));
        LoginUser loginUser = SecurityUtils.getLoginUser();
        int count = 0;
        for (StudentSubject studentSubject : studentSubjectList) {
            studentSubject.setUpdateBy(loginUser.getUsername());
            studentSubject.setUpdateTime(DateUtils.getNowDate());
            studentSubject.setDelFlag(true);
            count += studentSubjectMapper.update(studentSubject, new LambdaQueryWrapper<StudentSubject>()
                    .eq(StudentSubject::getId, studentSubject.getId()));
        }
        return count;
    }

    /**
     * 删除成绩管理表信息
     *
     * @param id 成绩管理表主键
     * @return 结果
     */
    @Override
    public int deleteStudentSubjectById(Long id)
    {
        StudentSubject studentSubject = studentSubjectMapper.selectOne(new LambdaQueryWrapper<StudentSubject>()
                .eq(StudentSubject::getId, id)
                .eq(StudentSubject::getStatus, "0")
                .eq(StudentSubject::getDelFlag, false));
        LoginUser loginUser = SecurityUtils.getLoginUser();
        studentSubject.setUpdateBy(loginUser.getUsername());
        studentSubject.setUpdateTime(DateUtils.getNowDate());
        studentSubject.setDelFlag(true);
        return studentSubjectMapper.update(studentSubject, new LambdaQueryWrapper<StudentSubject>()
                .eq(StudentSubject::getId, studentSubject.getId()));
    }

    // ==================================================== 学生角色专用函数 ============================================================

    /**
     * 查询当前登录学生的成绩管理表列表
     *
     * @param studentSubject 成绩管理表
     * @return 成绩管理表集合
     */
    @Override
    public List<StudentSubject> selectLoginStudentSubjectScoreList(StudentSubject studentSubject){
        return studentSubjectMapper.selectStudentSubjectList(studentSubject);
    }

    /**
     * 查询当前登录学生成绩管理表
     *
     * @param id 成绩管理表主键
     * @return 成绩管理表
     */
    public StudentSubject selectLoginStudentStudentSubjectById(Long id){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UserStudent student = studentService.selectEnableStudentByUserId(loginUser.getUserId());
        return studentSubjectMapper.selectOne(new LambdaQueryWrapper<StudentSubject>()
                .eq(StudentSubject::getId, id)
                .eq(StudentSubject::getStudentId, student.getStudentId())
                .eq(StudentSubject::getStatus, "0")
                .eq(StudentSubject::getDelFlag, false));
    }

    /**
     * 根据 ids 查询当前登录学生成绩管理表集合
     *
     * @param ids 成绩管理表主键数组
     * @return 成绩管理表
     */
    @Override
    public List<StudentSubject> selectLoginStudentStudentSubjectListByIds(Long[] ids){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UserStudent student = studentService.selectEnableStudentByUserId(loginUser.getUserId());
        return studentSubjectMapper.selectList(new LambdaQueryWrapper<StudentSubject>()
                .in(StudentSubject::getId, ids)
                .eq(StudentSubject::getStudentId, student.getStudentId())
                .eq(StudentSubject::getStatus, "0")
                .eq(StudentSubject::getDelFlag, false));
    }
}
