package com.ruoyi.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.StudentSubject;
import com.ruoyi.system.service.IStudentSubjectService;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.ISubjectScoreService;

import javax.annotation.Resource;

/**
 * 成绩管理表Service业务层处理
 * 
 * @author foxmo
 * @date 2024-03-22
 */
@Service
public class SubjectScoreServiceImpl implements ISubjectScoreService 
{
    @Resource
    private IStudentSubjectService studentSubjectService;

    /**
     * 查询当前登录学生成绩管理表
     *
     * @param id 成绩管理表主键
     * @return 成绩管理表
     */
    @Override
    public StudentSubject selectLoginStudentSubjectScoreById(Long id)
    {
        return studentSubjectService.selectLoginStudentStudentSubjectById(id);
    }

    /**
     * 查询当前登录学生成绩管理表集合
     *
     * @param ids 成绩管理表主键数组
     * @return 成绩管理表
     */
    @Override
    public List<StudentSubject> selectLoginStudentSubjectScoreListByIds(Long[] ids){
        return studentSubjectService.selectLoginStudentStudentSubjectListByIds(ids);
    }

    /**
     * 查询当前登录学生的成绩管理表列表
     *
     * @param studentSubject 成绩管理表
     * @return 成绩管理表集合
     */
    @Override
    public List<StudentSubject> selectLoginStudentSubjectScoreList(StudentSubject studentSubject)
    {
        return studentSubjectService.selectLoginStudentSubjectScoreList(studentSubject);
    }

    /**
     * 新增成绩管理表
     * 
     * @param studentSubject 成绩管理表
     * @return 结果
     */
    @Override
    public int insertSubjectScore(StudentSubject studentSubject)
    {
        return studentSubjectService.insertStudentSubject(studentSubject);
    }

    /**
     * 修改成绩管理表
     * 
     * @param studentSubject 成绩管理表
     * @return 结果
     */
    @Override
    public int updateSubjectScore(StudentSubject studentSubject)
    {
        return studentSubjectService.updateStudentSubject(studentSubject);
    }

    /**
     * 批量删除成绩管理表
     * 
     * @param ids 需要删除的成绩管理表主键
     * @return 结果
     */
    @Override
    public int deleteSubjectScoreByIds(Long[] ids)
    {
        return studentSubjectService.deleteStudentSubjectByIds(ids);
    }

    /**
     * 删除成绩管理表信息
     * 
     * @param id 成绩管理表主键
     * @return 结果
     */
    @Override
    public int deleteSubjectScoreById(Long id)
    {
        return studentSubjectService.deleteStudentSubjectById(id);
    }
}
