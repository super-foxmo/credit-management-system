package com.ruoyi.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.ComprehensiveEvaluation;

/**
 * 综测数据汇总Mapper接口
 * 
 * @author foxmo
 * @date 2024-03-29
 */
public interface ComprehensiveEvaluationMapper extends BaseMapper<ComprehensiveEvaluation>
{
    /**
     * 查询综测数据汇总
     * 
     * @param comprehensiveId 综测数据汇总主键
     * @return 综测数据汇总
     */
    public ComprehensiveEvaluation selectComprehensiveEvaluationByComprehensiveId(Long comprehensiveId);

    /**
     * 查询综测数据汇总列表
     * 
     * @param comprehensiveEvaluation 综测数据汇总
     * @return 综测数据汇总集合
     */
    public List<ComprehensiveEvaluation> selectComprehensiveEvaluationList(ComprehensiveEvaluation comprehensiveEvaluation);

    /**
     * 查询指定某一学年的专业排名列表
     *
     * @param comprehensiveEvaluation 综测数据汇总
     * @return 综测数据
     */
    public List<ComprehensiveEvaluation> selectSpecialtySort(ComprehensiveEvaluation comprehensiveEvaluation);

    /**
     * 查询指定某一学年的班级排名列表
     *
     * @param comprehensiveEvaluation 综测数据汇总
     * @return 综测数据
     */
    public List<ComprehensiveEvaluation> selectClassSort(ComprehensiveEvaluation comprehensiveEvaluation);

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
     * 删除综测数据汇总
     * 
     * @param comprehensiveId 综测数据汇总主键
     * @return 结果
     */
    public int deleteComprehensiveEvaluationByComprehensiveId(Long comprehensiveId);

    /**
     * 批量删除综测数据汇总
     * 
     * @param comprehensiveIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteComprehensiveEvaluationByComprehensiveIds(Long[] comprehensiveIds);
}
