package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.CompetitionAwards;

import java.util.List;

/**
 * 竞赛获奖Mapper接口
 * 
 * @author foxmo
 * @date 2024-03-14
 */
public interface CompetitionAwardsMapper extends BaseMapper<CompetitionAwards>
{
    /**
     * 查询竞赛获奖
     * 
     * @param awardsId 竞赛获奖主键
     * @return 竞赛获奖
     */
    public CompetitionAwards selectCompetitionAwardsByAwardsId(Long awardsId);

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
     * 删除竞赛获奖
     * 
     * @param awardsId 竞赛获奖主键
     * @return 结果
     */
    public int deleteCompetitionAwardsByAwardsId(Long awardsId);

    /**
     * 批量删除竞赛获奖
     * 
     * @param awardsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCompetitionAwardsByAwardsIds(Long[] awardsIds);
}
