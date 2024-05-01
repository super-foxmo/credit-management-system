package com.ruoyi.system.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.CompetitionActivity;
import com.ruoyi.system.domain.ComprehensiveEvaluation;
import com.ruoyi.system.mapper.CompetitionActivityMapper;
import com.ruoyi.system.mapper.CompetitionGradeMapper;
import com.ruoyi.system.service.ICompetitionActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 竞赛信息Service业务层处理
 *
 * @author foxmo
 * @date 2024-03-10
 */
@Service
public class CompetitionActivityServiceImpl implements ICompetitionActivityService {
    @Resource
    private CompetitionActivityMapper competitionActivityMapper;

    @Resource
    private CompetitionGradeMapper competitionGradeMapper;

    /**
     * 查询竞赛信息
     *
     * @param activityId 竞赛信息主键
     * @return 竞赛信息
     */
    @Override
    public CompetitionActivity selectCompetitionActivityByActivityId(Long activityId) {
        return competitionActivityMapper.selectOne(new LambdaQueryWrapper<CompetitionActivity>()
                .eq(CompetitionActivity::getActivityId, activityId)
                .eq(CompetitionActivity::getDelFlag, false));
    }

    /**
     *根据ids查询竞赛信息集合
     *
     * @param activityIds 竞赛信息主键集合
     * @return 竞赛信息集合
     */
    @Override
    public List<CompetitionActivity> selectCompetitionActivityByActivityIds(List<Long> activityIds){
        return competitionActivityMapper.selectList(new LambdaQueryWrapper<CompetitionActivity>()
                .in(CompetitionActivity::getActivityId, activityIds)
                .eq(CompetitionActivity::getStatus, "0")
                .eq(CompetitionActivity::getDelFlag, false));
    }

    /**
     * 查询状态开启的竞赛信息
     *
     * @param activityId 竞赛信息主键
     * @return 竞赛信息
     */
    @Override
    public CompetitionActivity selectEnableCompetitionActivityByActivityId(Long activityId) {
        return competitionActivityMapper.selectOne(new LambdaQueryWrapper<CompetitionActivity>()
                .eq(CompetitionActivity::getActivityId, activityId)
                .eq(CompetitionActivity::getStatus, "0")
                .eq(CompetitionActivity::getDelFlag, false));
    }

    /**
     * 根据名称查询竞赛信息
     *
     * @param activityName 竞赛信息名称
     * @return 竞赛信息
     */
    @Override
    public CompetitionActivity selectCompetitionActivityByActivityName(String activityName) {
        return competitionActivityMapper.selectOne(new LambdaQueryWrapper<CompetitionActivity>()
                .eq(CompetitionActivity::getActivityName, activityName)
                .eq(CompetitionActivity::getDelFlag, false));
    }

    /**
     * 查询竞赛信息列表
     *
     * @param competitionActivity 竞赛信息
     * @return 竞赛信息
     */
    @Override
    public List<CompetitionActivity> selectCompetitionActivityList(CompetitionActivity competitionActivity) {
        return competitionActivityMapper.selectCompetitionActivityList(competitionActivity);
    }

    /**
     * 查询状态开启的竞赛信息列表
     *
     * @param competitionActivity 竞赛信息
     * @return 竞赛信息
     */
    @Override
    public List<CompetitionActivity> selectEnableCompetitionActivityList(CompetitionActivity competitionActivity) {
        return competitionActivityMapper.selectList(new LambdaQueryWrapper<CompetitionActivity>()
                .eq(CompetitionActivity::getStatus, "0")
                .eq(CompetitionActivity::getDelFlag, false)
                .orderByDesc(CompetitionActivity::getCreateTime));
    }

    /**
     * 新增竞赛信息
     *
     * @param competitionActivity 竞赛信息
     * @return 结果
     */
    @Override
    public int insertCompetitionActivity(CompetitionActivity competitionActivity) {
//        List<Long> gradeIdList = competitionActivity.getScoreRule().stream().map(item -> item.getGradeId()).collect(Collectors.toList());
//        List<CompetitionGrade> competitionGradeList = competitionGradeMapper.selectList(new LambdaQueryWrapper<CompetitionGrade>()
//                .in(CompetitionGrade::getGradeId, gradeIdList)
//                .eq(CompetitionGrade::getStatus, false)
//                .eq(CompetitionGrade::getDelFlag, false));
//        // gradeId -> CompetitionGrade
//        Map<Long, CompetitionGrade> competitionGradeMap = competitionGradeList.stream().collect(Collectors.toMap(CompetitionGrade::getGradeId, Function.identity()));
//
//        for (ScoreRule scoreRule : competitionActivity.getScoreRule()) {
//            CompetitionGrade competitionGrade = competitionGradeMap.get(scoreRule.getGradeId());
//            if (ObjectUtil.isNotEmpty(competitionGrade)){
//                scoreRule.setGradeName(competitionGrade.getGradeName());
//            }
//        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        competitionActivity.setCreateBy(loginUser.getUsername());
        competitionActivity.setCreateTime(DateUtils.getNowDate());
        return competitionActivityMapper.insert(competitionActivity);
    }

    /**
     * 修改竞赛信息
     *
     * @param competitionActivity 竞赛信息
     * @return 结果
     */
    @Override
    public int updateCompetitionActivity(CompetitionActivity competitionActivity) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        competitionActivity.setUpdateBy(loginUser.getUsername());
        competitionActivity.setUpdateTime(DateUtils.getNowDate());
        return competitionActivityMapper.update(competitionActivity, new LambdaQueryWrapper<CompetitionActivity>()
                .eq(CompetitionActivity::getActivityId, competitionActivity.getActivityId()));
    }

    /**
     * 批量删除竞赛信息
     *
     * @param activityIds 需要删除的竞赛信息主键
     * @return 结果
     */
    @Override
    public int deleteCompetitionActivityByActivityIds(Long[] activityIds) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<CompetitionActivity> competitionActivityList = competitionActivityMapper.selectList(new LambdaQueryWrapper<CompetitionActivity>()
                .in(CompetitionActivity::getActivityId, activityIds));
        int count = 0;
        for (CompetitionActivity competitionActivity : competitionActivityList) {
            competitionActivity.setUpdateBy(loginUser.getUsername());
            competitionActivity.setUpdateTime(DateUtils.getNowDate());
            competitionActivity.setDelFlag(true);
            count += competitionActivityMapper.update(competitionActivity, new LambdaQueryWrapper<CompetitionActivity>()
                    .eq(CompetitionActivity::getActivityId, competitionActivity.getActivityId()));
        }
        return count;
    }

    /**
     * 删除竞赛信息信息
     *
     * @param activityId 竞赛信息主键
     * @return 结果
     */
    @Override
    public int deleteCompetitionActivityByActivityId(Long activityId) {
        CompetitionActivity competitionActivity = competitionActivityMapper.selectOne(new LambdaQueryWrapper<CompetitionActivity>()
                .eq(CompetitionActivity::getActivityId, activityId)
                .eq(CompetitionActivity::getDelFlag, false));
        if (ObjectUtil.isEmpty(competitionActivity)) {
            return 0;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        competitionActivity.setUpdateBy(loginUser.getUsername());
        competitionActivity.setUpdateTime(DateUtils.getNowDate());
        competitionActivity.setDelFlag(true);
        return competitionActivityMapper.update(competitionActivity, new LambdaQueryWrapper<CompetitionActivity>()
                .eq(CompetitionActivity::getActivityId, activityId));
    }
}
