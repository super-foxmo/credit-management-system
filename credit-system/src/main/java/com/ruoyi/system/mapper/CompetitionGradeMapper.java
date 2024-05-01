package com.ruoyi.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.CompetitionGrade;

import java.util.List;

/**
 * 竞赛等级Mapper接口
 * 
 * @author foxmo
 * @date 2024-03-10
 */
public interface CompetitionGradeMapper extends BaseMapper<CompetitionGrade>
{
    /**
     * 查询竞赛等级
     * 
     * @param gradeId 竞赛等级主键
     * @return 竞赛等级
     */
    public CompetitionGrade selectCompetitionGradeByGradeId(Long gradeId);

    /**
     * 查询竞赛等级列表
     * 
     * @param competitionGrade 竞赛等级
     * @return 竞赛等级集合
     */
    public List<CompetitionGrade> selectCompetitionGradeList(CompetitionGrade competitionGrade);

    /**
     * 新增竞赛等级
     * 
     * @param competitionGrade 竞赛等级
     * @return 结果
     */
    public int insertCompetitionGrade(CompetitionGrade competitionGrade);

    /**
     * 修改竞赛等级
     * 
     * @param competitionGrade 竞赛等级
     * @return 结果
     */
    public int updateCompetitionGrade(CompetitionGrade competitionGrade);

    /**
     * 删除竞赛等级
     * 
     * @param gradeId 竞赛等级主键
     * @return 结果
     */
    public int deleteCompetitionGradeByGradeId(Long gradeId);

    /**
     * 批量删除竞赛等级
     * 
     * @param gradeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCompetitionGradeByGradeIds(Long[] gradeIds);
}
