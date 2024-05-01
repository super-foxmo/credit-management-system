package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.StudentSubject;

/**
 * 成绩管理表Service接口
 * 
 * @author foxmo
 * @date 2024-03-19
 */
public interface IStudentSubjectService 
{
    /**
     * 查询成绩管理表
     * 
     * @param id 成绩管理表主键
     * @return 成绩管理表
     */
    public StudentSubject selectStudentSubjectById(Long id);

    /**
     * 根据 ids 查询成绩管理表集合
     *
     * @param ids 成绩管理表主键数组
     * @return 成绩管理表
     */
    public List<StudentSubject> selectStudentSubjectListByIds(Long[] ids);

    /**
     * 查询指定学生是否选修某课程
     *
     * @param studentId 学生主键
     * @param subjectId 学科主键
     * @return 成绩管理表
     */
    public StudentSubject selectStudentSubjectByStudentIdAndSubjectId(Long studentId, Long subjectId);

    /**
     * 查询指定学生某一学年的所有课程列表
     *
     * @param studentId 学生主键
     * @param academicYear 学年
     * @return 成绩管理表
     */
    public List<StudentSubject> selectStudentSubjectsByStudentIdAndAcademicYear(Long studentId, Long academicYear);

    /**
     * 查询成绩管理表列表
     * 
     * @param studentSubject 成绩管理表
     * @return 成绩管理表集合
     */
    public List<StudentSubject> selectStudentSubjectList(StudentSubject studentSubject);


    /**
     * 新增成绩管理表
     *
     * @param studentSubject 成绩管理表
     * @return 结果
     */
    public int insertStudentSubject(StudentSubject studentSubject);

    /**
     * 修改成绩管理表
     *
     * @param studentSubject 成绩管理表
     * @return 结果
     */
    public int updateStudentSubject(StudentSubject studentSubject);

    /**
     * 批量删除成绩管理表
     *
     * @param ids 需要删除的成绩管理表主键集合
     * @return 结果
     */
    public int deleteStudentSubjectByIds(Long[] ids);

    /**
     * 删除成绩管理表信息
     *
     * @param id 成绩管理表主键
     * @return 结果
     */
    public int deleteStudentSubjectById(Long id);

    // ====================================================== 学生角色专用函数 ==========================================================
    /**
     * 查询当前登录学生的成绩管理表列表
     *
     * @param studentSubject 成绩管理表
     * @return 成绩管理表集合
     */
    public List<StudentSubject> selectLoginStudentSubjectScoreList(StudentSubject studentSubject);

    /**
     * 查询当前登录学生成绩管理表
     *
     * @param id 成绩管理表主键
     * @return 成绩管理表
     */
    public StudentSubject selectLoginStudentStudentSubjectById(Long id);

    /**
     * 根据 ids 查询当前登录学生成绩管理表集合
     *
     * @param ids 成绩管理表主键数组
     * @return 成绩管理表
     */
    public List<StudentSubject> selectLoginStudentStudentSubjectListByIds(Long[] ids);
}
