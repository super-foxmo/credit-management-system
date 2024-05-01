package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.entity.AverageScore;
import com.ruoyi.system.domain.entity.ScoreEvaluationQueryParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ScoreEvaluationMapper;
import com.ruoyi.system.domain.ScoreEvaluation;
import com.ruoyi.system.service.IScoreEvaluationService;

import javax.annotation.Resource;

/**
 * 综测数据汇总Service业务层处理
 * 
 * @author foxmo
 * @date 2024-04-10
 */
@Service
public class ScoreEvaluationServiceImpl implements IScoreEvaluationService 
{
    @Resource
    private ScoreEvaluationMapper scoreEvaluationMapper;

    /**
     * 查询综测数据汇总
     * 
     * @param comprehensiveId 综测数据汇总主键
     * @return 综测数据汇总
     */
    @Override
    public ScoreEvaluation selectScoreEvaluationByComprehensiveId(Long comprehensiveId)
    {
        return scoreEvaluationMapper.selectScoreEvaluationByComprehensiveId(comprehensiveId);
    }

    /**
     * 查询综测数据汇总列表
     * 
     * @param scoreEvaluation 综测数据汇总
     * @return 综测数据汇总
     */
    @Override
    public List<ScoreEvaluation> selectScoreEvaluationList(ScoreEvaluation scoreEvaluation)
    {
        return scoreEvaluationMapper.selectScoreEvaluationList(scoreEvaluation);
    }

    /**
     * 新增综测数据汇总
     * 
     * @param scoreEvaluation 综测数据汇总
     * @return 结果
     */
    @Override
    public int insertScoreEvaluation(ScoreEvaluation scoreEvaluation)
    {
        scoreEvaluation.setCreateTime(DateUtils.getNowDate());
        return scoreEvaluationMapper.insertScoreEvaluation(scoreEvaluation);
    }

    /**
     * 修改综测数据汇总
     * 
     * @param scoreEvaluation 综测数据汇总
     * @return 结果
     */
    @Override
    public int updateScoreEvaluation(ScoreEvaluation scoreEvaluation)
    {
        scoreEvaluation.setUpdateTime(DateUtils.getNowDate());
        return scoreEvaluationMapper.updateScoreEvaluation(scoreEvaluation);
    }

    /**
     * 批量删除综测数据汇总
     * 
     * @param comprehensiveIds 需要删除的综测数据汇总主键
     * @return 结果
     */
    @Override
    public int deleteScoreEvaluationByComprehensiveIds(Long[] comprehensiveIds)
    {
        return scoreEvaluationMapper.deleteScoreEvaluationByComprehensiveIds(comprehensiveIds);
    }

    /**
     * 删除综测数据汇总信息
     * 
     * @param comprehensiveId 综测数据汇总主键
     * @return 结果
     */
    @Override
    public int deleteScoreEvaluationByComprehensiveId(Long comprehensiveId)
    {
        return scoreEvaluationMapper.deleteScoreEvaluationByComprehensiveId(comprehensiveId);
    }

    /**
     * 以班级为单位计算每个班级的综测数据平均分（指定学年）
     *
     * @param queryParams 查询条件
     * @return 平均分计算结果
     */
    @Override
    public Map<Long, AverageScore> selectAverageScoreByStudentIds(ScoreEvaluationQueryParams queryParams){
        ArrayList<AverageScore> averageScoreArrayList = scoreEvaluationMapper.selectAverageScoreByStudentIds(queryParams);
        return averageScoreArrayList.stream().collect(Collectors.toMap(AverageScore::getClassId, Function.identity()));
    }
}
