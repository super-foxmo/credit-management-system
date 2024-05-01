package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ComprehensiveEvaluation;
import com.ruoyi.system.domain.UserStudent;

/**
 * 综测数据汇总Service接口
 * 
 * @author foxmo
 * @date 2024-03-29
 */
public interface IComprehensiveEvaluationService 
{
    /**
     * 查询综测数据汇总
     * 
     * @param comprehensiveId 综测数据汇总主键
     * @return 综测数据汇总
     */
    public ComprehensiveEvaluation selectComprehensiveEvaluationByComprehensiveId(Long comprehensiveId);

    /**
     * 根据学生id和学年查询综测数据汇总
     *
     * @param studentId 学生主键
     * @param academicYear 学年
     * @return 综测数据汇总
     */
    public ComprehensiveEvaluation selectComprehensiveEvaluationByStudentIdAndAcademicYear(Long studentId, Long academicYear);

    /**
     * 查询当前登录学生的综测数据汇总列表
     *
     * @param student 当前登录学生信息
     * @return 综测数据汇总
     */
    public List<ComprehensiveEvaluation> selectStudentComprehensiveEvaluationList(UserStudent student);

    /**
     * 新增综测数据汇总
     * 
     * @param comprehensiveEvaluation 综测数据汇总
     * @return 结果
     */
    public int insertComprehensiveEvaluation(ComprehensiveEvaluation comprehensiveEvaluation);

    /**
     * 修改综测数据汇总
     * 
     * @param comprehensiveEvaluation 综测数据汇总
     * @return 结果
     */
    public int updateComprehensiveEvaluation(ComprehensiveEvaluation comprehensiveEvaluation);

    /**
     * 批量删除综测数据汇总
     * 
     * @param comprehensiveIds 需要删除的综测数据汇总主键集合
     * @return 结果
     */
    public int deleteComprehensiveEvaluationByComprehensiveIds(Long[] comprehensiveIds);

    /**
     * 删除综测数据汇总信息
     * 
     * @param comprehensiveId 综测数据汇总主键
     * @return 结果
     */
    public int deleteComprehensiveEvaluationByComprehensiveId(Long comprehensiveId);

    /**
     * 删除指定学生综测数据汇总信息
     *
     * @param studentId 学生主键
     * @return 结果
     */
    public int deleteComprehensiveEvaluationByStudentId(Long studentId);
}
