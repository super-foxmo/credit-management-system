package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.StudentSubject;

/**
 * 成绩管理表Service接口
 * 
 * @author foxmo
 * @date 2024-03-22
 */
public interface ISubjectScoreService 
{
    /**
     * 查询当前登录学生成绩管理表
     * 
     * @param id 成绩管理表主键
     * @return 成绩管理表
     */
    public StudentSubject selectLoginStudentSubjectScoreById(Long id);

    /**
     * 查询当前登录学生成绩管理表集合
     *
     * @param ids 成绩管理表主键数组
     * @return 成绩管理表
     */
    public List<StudentSubject> selectLoginStudentSubjectScoreListByIds(Long[] ids);

    /**
     * 查询当前登录学生的成绩管理表列表
     *
     * @param studentSubject 成绩管理表
     * @return 成绩管理表集合
     */
    public List<StudentSubject> selectLoginStudentSubjectScoreList(StudentSubject studentSubject);

    /**
     * 新增成绩管理表
     * 
     * @param studentSubject 成绩管理表
     * @return 结果
     */
    public int insertSubjectScore(StudentSubject studentSubject);

    /**
     * 修改成绩管理表
     * 
     * @param studentSubject 成绩管理表
     * @return 结果
     */
    public int updateSubjectScore(StudentSubject studentSubject);

    /**
     * 批量删除成绩管理表
     * 
     * @param ids 需要删除的成绩管理表主键集合
     * @return 结果
     */
    public int deleteSubjectScoreByIds(Long[] ids);

    /**
     * 删除成绩管理表信息
     * 
     * @param id 成绩管理表主键
     * @return 结果
     */
    public int deleteSubjectScoreById(Long id);
}
