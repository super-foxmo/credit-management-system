package com.ruoyi.system.service;

import com.ruoyi.system.domain.CompetitionSignUp;

import java.util.List;

/**
 * 竞赛报名Service接口
 * 
 * @author foxmo
 * @date 2024-03-14
 */
public interface ICompetitionSignUpService 
{
    /**
     * 查询竞赛报名
     * 
     * @param signUpId 竞赛报名主键
     * @return 竞赛报名
     */
    public CompetitionSignUp selectCompetitionSignUpBySignUpId(Long signUpId);

    /**
     * 根据用户id和竞赛id查询竞赛报名
     *
     * @param studentId 用户主键
     * @param activityId 竞赛活动主键
     * @return 竞赛报名
     */
    public CompetitionSignUp selectCompetitionSignUpByUserIdAndActivityId(Long studentId, Long activityId);

    /**
     * 查询竞赛报名列表
     * 
     * @param competitionSignUp 竞赛报名
     * @return 竞赛报名集合
     */
    public List<CompetitionSignUp> selectCompetitionSignUpList(CompetitionSignUp competitionSignUp);

    /**
     * 根据学生名称或学号模糊查询竞赛报名列表
     *
     * @param competitionSignUp 竞赛报名
     * @return 竞赛报名集合
     */
    public List<CompetitionSignUp> selectCompetitionSignUpListByStudent(CompetitionSignUp competitionSignUp);

    /**
     * 新增竞赛报名
     * 
     * @param competitionSignUp 竞赛报名
     * @return 结果
     */
    public int insertCompetitionSignUp(CompetitionSignUp competitionSignUp);

    /**
     * 修改竞赛报名
     * 
     * @param competitionSignUp 竞赛报名
     * @return 结果
     */
    public int updateCompetitionSignUp(CompetitionSignUp competitionSignUp);

    /**
     * 批量删除竞赛报名
     * 
     * @param signUpIds 需要删除的竞赛报名主键集合
     * @return 结果
     */
    public int deleteCompetitionSignUpBySignUpIds(Long[] signUpIds);

    /**
     * 删除竞赛报名信息
     * 
     * @param signUpId 竞赛报名主键
     * @return 结果
     */
    public int deleteCompetitionSignUpBySignUpId(Long signUpId);
}
