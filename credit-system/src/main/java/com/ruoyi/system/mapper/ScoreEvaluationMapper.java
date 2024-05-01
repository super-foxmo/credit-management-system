package com.ruoyi.system.mapper;


import java.util.ArrayList;
import java.util.List;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.ScoreEvaluation;
import com.ruoyi.system.domain.entity.AverageScore;
import com.ruoyi.system.domain.entity.ScoreEvaluationQueryParams;
import org.apache.ibatis.annotations.Param;

/**
 * 综测数据汇总Mapper接口
 * 
 * @author foxmo
 * @date 2024-04-10
 */
public interface ScoreEvaluationMapper extends BaseMapper<ScoreEvaluation>
{
    /**
     * 查询综测数据汇总
     * 
     * @param comprehensiveId 综测数据汇总主键
     * @return 综测数据汇总
     */
    public ScoreEvaluation selectScoreEvaluationByComprehensiveId(Long comprehensiveId);

    /**
     * 查询综测数据汇总列表
     * 
     * @param scoreEvaluation 综测数据汇总
     * @return 综测数据汇总集合
     */
    public List<ScoreEvaluation> selectScoreEvaluationList(ScoreEvaluation scoreEvaluation);

    /**
     * 新增综测数据汇总
     * 
     * @param scoreEvaluation 综测数据汇总
     * @return 结果
     */
    public int insertScoreEvaluation(ScoreEvaluation scoreEvaluation);

    /**
     * 修改综测数据汇总
     * 
     * @param scoreEvaluation 综测数据汇总
     * @return 结果
     */
    public int updateScoreEvaluation(ScoreEvaluation scoreEvaluation);

    /**
     * 删除综测数据汇总
     * 
     * @param comprehensiveId 综测数据汇总主键
     * @return 结果
     */
    public int deleteScoreEvaluationByComprehensiveId(Long comprehensiveId);

    /**
     * 批量删除综测数据汇总
     * 
     * @param comprehensiveIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScoreEvaluationByComprehensiveIds(Long[] comprehensiveIds);

    /**
     * 以班级为单位计算每个班级的综测数据平均分（指定学年）
     *
     * @param queryParams 查询条件
     * @return 平均分计算结果
     */
    public ArrayList<AverageScore> selectAverageScoreByStudentIds(ScoreEvaluationQueryParams queryParams);
}
