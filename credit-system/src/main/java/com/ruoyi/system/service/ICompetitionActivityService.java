package com.ruoyi.system.service;

import com.ruoyi.system.domain.CompetitionActivity;

import java.util.List;

/**
 * 竞赛信息Service接口
 * 
 * @author foxmo
 * @date 2024-03-10
 */
public interface ICompetitionActivityService 
{
    /**
     * 查询竞赛信息
     * 
     * @param activityId 竞赛信息主键
     * @return 竞赛信息
     */
    public CompetitionActivity selectCompetitionActivityByActivityId(Long activityId);

    /**
     *根据ids查询竞赛信息集合
     *
     * @param activityIds 竞赛信息主键集合
     * @return 竞赛信息集合
     */
    public List<CompetitionActivity> selectCompetitionActivityByActivityIds(List<Long> activityIds);

    /**
     * 查询状态开启的竞赛信息
     *
     * @param activityId 竞赛信息主键
     * @return 竞赛信息
     */
    public CompetitionActivity selectEnableCompetitionActivityByActivityId(Long activityId);

    /**
     * 根据名称查询竞赛信息
     *
     * @param activityName 竞赛信息名称
     * @return 竞赛信息
     */
    public CompetitionActivity selectCompetitionActivityByActivityName(String activityName);

    /**
     * 查询竞赛信息列表
     * 
     * @param competitionActivity 竞赛信息
     * @return 竞赛信息集合
     */
    public List<CompetitionActivity> selectCompetitionActivityList(CompetitionActivity competitionActivity);

    /**
     * 查询状态开启的竞赛信息列表
     *
     * @param competitionActivity 竞赛信息
     * @return 竞赛信息
     */
    public List<CompetitionActivity> selectEnableCompetitionActivityList(CompetitionActivity competitionActivity);

    /**
     * 新增竞赛信息
     * 
     * @param competitionActivity 竞赛信息
     * @return 结果
     */
    public int insertCompetitionActivity(CompetitionActivity competitionActivity);

    /**
     * 修改竞赛信息
     * 
     * @param competitionActivity 竞赛信息
     * @return 结果
     */
    public int updateCompetitionActivity(CompetitionActivity competitionActivity);

    /**
     * 批量删除竞赛信息
     * 
     * @param activityIds 需要删除的竞赛信息主键集合
     * @return 结果
     */
    public int deleteCompetitionActivityByActivityIds(Long[] activityIds);

    /**
     * 删除竞赛信息信息
     * 
     * @param activityId 竞赛信息主键
     * @return 结果
     */
    public int deleteCompetitionActivityByActivityId(Long activityId);
}
