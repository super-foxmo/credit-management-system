package com.ruoyi.system.service.impl;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.CompetitionGrade;
import com.ruoyi.system.mapper.CompetitionGradeMapper;
import com.ruoyi.system.service.ICompetitionGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 竞赛等级Service业务层处理
 *
 * @author foxmo
 * @date 2024-03-10
 */
@Service
public class CompetitionGradeServiceImpl implements ICompetitionGradeService {
    @Resource
    private CompetitionGradeMapper competitionGradeMapper;

    /**
     * 查询竞赛等级
     *
     * @param gradeId 竞赛等级主键
     * @return 竞赛等级
     */
    @Override
    public CompetitionGrade selectCompetitionGradeByGradeId(Long gradeId) {
        return competitionGradeMapper.selectOne(new LambdaQueryWrapper<CompetitionGrade>()
                .eq(CompetitionGrade::getGradeId, gradeId)
                .eq(CompetitionGrade::getDelFlag, false));
    }

    /**
     * 根据名称查询竞赛等级
     *
     * @param gradeName 竞赛等级名称
     * @return 竞赛等级
     */
    @Override
    public CompetitionGrade selectCompetitionGradeByGradeName(String gradeName) {
        return competitionGradeMapper.selectOne(new LambdaQueryWrapper<CompetitionGrade>()
                .eq(CompetitionGrade::getGradeName, gradeName)
                .eq(CompetitionGrade::getDelFlag, false));
    }

    /**
     * 查询竞赛等级列表
     *
     * @param competitionGrade 竞赛等级
     * @return 竞赛等级
     */
    @Override
    public List<CompetitionGrade> selectCompetitionGradeList(CompetitionGrade competitionGrade) {
        return competitionGradeMapper.selectCompetitionGradeList(competitionGrade);
    }

    /**
     * 查询状态开启的竞赛等级列表
     *
     * @return 竞赛等级
     */
    @Override
    public List<CompetitionGrade> selectEnableCompetitionGradeList() {
        return competitionGradeMapper.selectList(new LambdaQueryWrapper<CompetitionGrade>()
                .eq(CompetitionGrade::getStatus, "0")
                .eq(CompetitionGrade::getDelFlag, false));
    }

    /**
     * 新增竞赛等级
     *
     * @param competitionGrade 竞赛等级
     * @return 结果
     */
    @Override
    public int insertCompetitionGrade(CompetitionGrade competitionGrade) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        competitionGrade.setCreateBy(loginUser.getUsername());
        competitionGrade.setCreateTime(DateUtils.getNowDate());
        return competitionGradeMapper.insertCompetitionGrade(competitionGrade);
    }

    /**
     * 修改竞赛等级
     *
     * @param competitionGrade 竞赛等级
     * @return 结果
     */
    @Override
    public int updateCompetitionGrade(CompetitionGrade competitionGrade) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        competitionGrade.setUpdateBy(loginUser.getUsername());
        competitionGrade.setUpdateTime(DateUtils.getNowDate());
        return competitionGradeMapper.updateCompetitionGrade(competitionGrade);
    }

    /**
     * 批量删除竞赛等级
     *
     * @param gradeIds 需要删除的竞赛等级主键
     * @return 结果
     */
    @Override
    public int deleteCompetitionGradeByGradeIds(Long[] gradeIds) {
        List<CompetitionGrade> competitionGradeList = competitionGradeMapper.selectList(new LambdaQueryWrapper<CompetitionGrade>()
                .in(CompetitionGrade::getGradeId, gradeIds)
                .eq(CompetitionGrade::getDelFlag, false));
        int count = 0;
        LoginUser loginUser = SecurityUtils.getLoginUser();
        for (CompetitionGrade competitionGrade : competitionGradeList) {
            competitionGrade.setUpdateBy(loginUser.getUsername());
            competitionGrade.setUpdateTime(DateUtils.getNowDate());
            competitionGrade.setDelFlag(true);
            competitionGradeMapper.update(competitionGrade, new LambdaQueryWrapper<CompetitionGrade>()
                    .eq(CompetitionGrade::getGradeId, competitionGrade.getGradeId()));
            count++;
        }
        return count;
    }

    /**
     * 删除竞赛等级信息
     *
     * @param gradeId 竞赛等级主键
     * @return 结果
     */
    @Override
    public int deleteCompetitionGradeByGradeId(Long gradeId) {
        CompetitionGrade competitionGrade = competitionGradeMapper.selectOne(new LambdaQueryWrapper<CompetitionGrade>()
                .eq(CompetitionGrade::getGradeId, gradeId)
                .eq(CompetitionGrade::getDelFlag, false));
        if (ObjectUtil.isEmpty(competitionGrade)) {
            return 1;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        competitionGrade.setUpdateBy(loginUser.getUsername());
        competitionGrade.setUpdateTime(DateUtils.getNowDate());
        competitionGrade.setDelFlag(true);
        return competitionGradeMapper.update(competitionGrade, new LambdaQueryWrapper<CompetitionGrade>()
                .eq(CompetitionGrade::getGradeId, competitionGrade.getGradeId()));
    }
}
