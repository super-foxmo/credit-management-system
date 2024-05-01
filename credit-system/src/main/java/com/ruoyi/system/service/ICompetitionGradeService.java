package com.ruoyi.system.service;

import com.ruoyi.system.domain.CompetitionGrade;

import java.util.List;


/**
 * 竞赛等级Service接口
 * 
 * @author foxmo
 * @date 2024-03-10
 */
public interface ICompetitionGradeService 
{
    /**
     * 查询竞赛等级
     * 
     * @param gradeId 竞赛等级主键
     * @return 竞赛等级
     */
    public CompetitionGrade selectCompetitionGradeByGradeId(Long gradeId);

    /**
     * 根据名称查询竞赛等级
     *
     * @param gradeName 竞赛等级名称
     * @return 竞赛等级
     */
    public CompetitionGrade selectCompetitionGradeByGradeName(String gradeName);

    /**
     * 查询竞赛等级列表
     * 
     * @param competitionGrade 竞赛等级
     * @return 竞赛等级集合
     */
    public List<CompetitionGrade> selectCompetitionGradeList(CompetitionGrade competitionGrade);

    /**
     * 查询状态开启的竞赛等级列表
     *
     * @return 竞赛等级
     */
    public List<CompetitionGrade> selectEnableCompetitionGradeList();

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
     * 批量删除竞赛等级
     * 
     * @param gradeIds 需要删除的竞赛等级主键集合
     * @return 结果
     */
    public int deleteCompetitionGradeByGradeIds(Long[] gradeIds);

    /**
     * 删除竞赛等级信息
     * 
     * @param gradeId 竞赛等级主键
     * @return 结果
     */
    public int deleteCompetitionGradeByGradeId(Long gradeId);
}
