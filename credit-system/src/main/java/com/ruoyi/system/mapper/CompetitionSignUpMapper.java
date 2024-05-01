package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.CompetitionSignUp;

import java.util.List;

/**
 * 竞赛报名Mapper接口
 * 
 * @author foxmo
 * @date 2024-03-14
 */
public interface CompetitionSignUpMapper extends BaseMapper<CompetitionSignUp>
{
    /**
     * 查询竞赛报名
     * 
     * @param signUpId 竞赛报名主键
     * @return 竞赛报名
     */
    public CompetitionSignUp selectCompetitionSignUpBySignUpId(Long signUpId);

    /**
     * 查询竞赛报名列表
     *
     * @param competitionSignUp 竞赛报名
     * @return 竞赛报名集合
     */
    public List<CompetitionSignUp> selectCompetitionSignUpList(CompetitionSignUp competitionSignUp);

    /**
     * 根据登录用户查询竞赛报名列表
     *
     * @param competitionSignUp 竞赛报名
     * @return 竞赛报名集合
     */
    public List<CompetitionSignUp> selectLoginCompetitionSignUpList(CompetitionSignUp competitionSignUp);

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
     * 删除竞赛报名
     * 
     * @param signUpId 竞赛报名主键
     * @return 结果
     */
    public int deleteCompetitionSignUpBySignUpId(Long signUpId);

    /**
     * 批量删除竞赛报名
     * 
     * @param signUpIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCompetitionSignUpBySignUpIds(Long[] signUpIds);
}
