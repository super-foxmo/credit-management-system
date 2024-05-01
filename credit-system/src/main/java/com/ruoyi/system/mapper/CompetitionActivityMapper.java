package com.ruoyi.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.CompetitionActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 竞赛信息Mapper接口
 * 
 * @author foxmo
 * @date 2024-03-10
 */
@Mapper
public interface CompetitionActivityMapper extends BaseMapper<CompetitionActivity> {
    /**
     * 查询竞赛信息
     * 
     * @param activityId 竞赛信息主键
     * @return 竞赛信息
     */
    public CompetitionActivity selectCompetitionActivityByActivityId(Long activityId);

    /**
     * 查询竞赛信息列表
     *
     * @param competitionActivity 竞赛信息
     * @return 竞赛信息集合
     */
    public List<CompetitionActivity> selectCompetitionActivityList(CompetitionActivity competitionActivity);

    /**
     * 查询状态开启且报名时间为结束的竞赛信息列表
     *
     * @param competitionActivity 竞赛信息
     * @return 竞赛信息集合
     */
    public List<CompetitionActivity> selectEnableIndateCompetitionApplyList(CompetitionActivity competitionActivity);

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
     * 删除竞赛信息
     * 
     * @param activityId 竞赛信息主键
     * @return 结果
     */
    public int deleteCompetitionActivityByActivityId(Long activityId);

    /**
     * 批量删除竞赛信息
     * 
     * @param activityIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCompetitionActivityByActivityIds(Long[] activityIds);
}
