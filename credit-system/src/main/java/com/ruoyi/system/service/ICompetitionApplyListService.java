package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.CompetitionSignUp;

/**
 * 报名列表Service接口
 * 
 * @author foxmo
 * @date 2024-03-20
 */
public interface ICompetitionApplyListService 
{
    /**
     * 查询报名列表
     * 
     * @param signUpId 报名列表主键
     * @return 报名列表
     */
    public CompetitionSignUp selectCompetitionApplyListBySignUpId(Long signUpId);


    /**
     * 根据登录用户查询竞赛报名列表
     *
     * @param competitionSignUp 竞赛报名
     * @return 竞赛报名集合
     */
    public List<CompetitionSignUp> selectLoginCompetitionSignUpList(CompetitionSignUp competitionSignUp);

    /**
     * 批量删除报名列表
     * 
     * @param signUpIds 需要删除的报名列表主键集合
     * @return 结果
     */
    public int deleteCompetitionApplyListBySignUpIds(Long[] signUpIds);

    /**
     * 删除报名列表信息
     * 
     * @param signUpId 报名列表主键
     * @return 结果
     */
    public int deleteCompetitionApplyListBySignUpId(Long signUpId);
}
