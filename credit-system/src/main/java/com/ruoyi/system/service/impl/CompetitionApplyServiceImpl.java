package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.CompetitionActivity;
import com.ruoyi.system.domain.CompetitionSignUp;
import com.ruoyi.system.mapper.CompetitionActivityMapper;
import com.ruoyi.system.service.ICompetitionApplyService;
import com.ruoyi.system.service.ICompetitionSignUpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 竞赛报名Service业务层处理
 * 
 * @author foxmo
 * @date 2024-03-20
 */
@Service
public class CompetitionApplyServiceImpl implements ICompetitionApplyService
{
    @Resource
    private CompetitionActivityMapper competitionActivityMapper;

    @Resource
    private ICompetitionSignUpService competitionSignUpService;

    /**
     * 查询竞赛报名
     * 
     * @param activityId 竞赛报名主键
     * @return 竞赛报名
     */
    @Override
    public CompetitionActivity selectCompetitionApplyByActivityId(Long activityId)
    {
        return competitionActivityMapper.selectCompetitionActivityByActivityId(activityId);
    }

    /**
     * 查询状态开启且报名时间为结束的竞赛信息列表
     * 
     * @param competitionActivity 竞赛报名
     * @return 竞赛报名
     */
    @Override
    public List<CompetitionActivity> selectEnableIndateCompetitionApplyList(CompetitionActivity competitionActivity)
    {
        return competitionActivityMapper.selectEnableIndateCompetitionApplyList(competitionActivity);
    }

    /**
     * 竞赛报名
     *
     * @param competitionSignUp 竞赛报名
     * @return 结果
     */
    @Override
    public int activityParticipation(CompetitionSignUp competitionSignUp){

        return competitionSignUpService.insertCompetitionSignUp(competitionSignUp);
    }

    /**
     * 新增竞赛报名
     *
     * @param competitionActivity 竞赛报名
     * @return 结果
     */
    @Override
    public int insertCompetitionApply(CompetitionActivity competitionActivity)
    {
        competitionActivity.setCreateTime(DateUtils.getNowDate());
        return competitionActivityMapper.insertCompetitionActivity(competitionActivity);
    }

    /**
     * 修改竞赛报名
     *
     * @param competitionActivity 竞赛报名
     * @return 结果
     */
    @Override
    public int updateCompetitionApply(CompetitionActivity competitionActivity)
    {
        competitionActivity.setUpdateTime(DateUtils.getNowDate());
        return competitionActivityMapper.updateCompetitionActivity(competitionActivity);
    }

    /**
     * 批量删除竞赛报名
     *
     * @param activityIds 需要删除的竞赛报名主键
     * @return 结果
     */
    @Override
    public int deleteCompetitionApplyByActivityIds(Long[] activityIds)
    {
        return competitionActivityMapper.deleteCompetitionActivityByActivityIds(activityIds);
    }

    /**
     * 删除竞赛报名信息
     *
     * @param activityId 竞赛报名主键
     * @return 结果
     */
    @Override
    public int deleteCompetitionApplyByActivityId(Long activityId)
    {
        return competitionActivityMapper.deleteCompetitionActivityByActivityId(activityId);
    }
}
