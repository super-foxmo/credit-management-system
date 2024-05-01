package com.ruoyi.system.service;

import com.ruoyi.system.domain.CompetitionAwards;

import java.util.List;

/**
 * 竞赛获奖Service接口
 * 
 * @author foxmo
 * @date 2024-03-14
 */
public interface ICompetitionAwardsService 
{
    /**
     * 查询竞赛获奖
     * 
     * @param awardsId 竞赛获奖主键
     * @return 竞赛获奖
     */
    public CompetitionAwards selectCompetitionAwardsByAwardsId(Long awardsId);

    /**
     * 根据ids查询未被删除的竞赛获奖集合
     *
     * @param awardsIds 竞赛获奖主键集合
     * @return 竞赛获奖
     */
    public List<CompetitionAwards> selectEnableCompetitionAwardsByAwardsIds(Long[] awardsIds);

    /**
     * 根据学生id查询状态开启的的竞赛获奖集合
     *
     * @param studentId 学生id
     * @return 竞赛获奖
     */
    public List<CompetitionAwards> selectEnableCompetitionAwardsByStudentId(Long studentId);

    /**
     * 根据竞赛id集合和学生id查询状态开启的的竞赛获奖集合
     *
     * @param awardsIdList 竞赛id集合
     * @param studentId 学生id
     * @return 竞赛获奖
     */
    public CompetitionAwards selectEnableCompetitionAwardsByActivityIdAndStudentId(Long activityId, Long studentId);

    /**
     * 根据学生id查询指定学年的竞赛获奖集合
     *
     * @param studentId 学生主键
     * @param academicYear 学年
     * @return 竞赛获奖集合
     */
    public List<CompetitionAwards> selectCompetitionAwardsByStudentIdAndAcademicYear(Long studentId, Long academicYear);

    /**
     * 查询竞赛获奖列表
     * 
     * @param competitionAwards 竞赛获奖
     * @return 竞赛获奖集合
     */
    public List<CompetitionAwards> selectCompetitionAwardsList(CompetitionAwards competitionAwards);

    /**
     * 新增竞赛获奖
     * 
     * @param competitionAwards 竞赛获奖
     * @return 结果
     */
    public int insertCompetitionAwards(CompetitionAwards competitionAwards);

    /**
     * 修改竞赛获奖
     * 
     * @param competitionAwards 竞赛获奖
     * @return 结果
     */
    public int updateCompetitionAwards(CompetitionAwards competitionAwards);

    /**
     * 批量删除竞赛获奖
     * 
     * @param awardsIds 需要删除的竞赛获奖主键集合
     * @return 结果
     */
    public int deleteCompetitionAwardsByAwardsIds(Long[] awardsIds);

    /**
     * 删除竞赛获奖信息
     * 
     * @param awardsId 竞赛获奖主键
     * @return 结果
     */
    public int deleteCompetitionAwardsByAwardsId(Long awardsId);
}
