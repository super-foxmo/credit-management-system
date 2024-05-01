package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.CompetitionActivity;
import com.ruoyi.system.domain.CompetitionSignUp;

/**
 * 竞赛报名Service接口
 * 
 * @author foxmo
 * @date 2024-03-20
 */
public interface ICompetitionApplyService 
{
    /**
     * 查询竞赛报名
     * 
     * @param activityId 竞赛报名主键
     * @return 竞赛报名
     */
    public CompetitionActivity selectCompetitionApplyByActivityId(Long activityId);

    /**
     * 查询状态开启且报名时间未结束竞赛报名列表
     * 
     * @param competitionActivity 竞赛报名
     * @return 竞赛报名集合
     */
    public List<CompetitionActivity> selectEnableIndateCompetitionApplyList(CompetitionActivity competitionActivity);

    /**
     * 竞赛报名
     *
     * @param competitionSignUp 竞赛报名
     * @return 结果
     */
    public int activityParticipation(CompetitionSignUp competitionSignUp);

    /**
     * 新增竞赛报名
     * 
     * @param competitionActivity 竞赛报名
     * @return 结果
     */
    public int insertCompetitionApply(CompetitionActivity competitionActivity);

    /**
     * 修改竞赛报名
     * 
     * @param competitionActivity 竞赛报名
     * @return 结果
     */
    public int updateCompetitionApply(CompetitionActivity competitionActivity);

    /**
     * 批量删除竞赛报名
     * 
     * @param activityIds 需要删除的竞赛报名主键集合
     * @return 结果
     */
    public int deleteCompetitionApplyByActivityIds(Long[] activityIds);

    /**
     * 删除竞赛报名信息
     * 
     * @param activityId 竞赛报名主键
     * @return 结果
     */
    public int deleteCompetitionApplyByActivityId(Long activityId);
}
