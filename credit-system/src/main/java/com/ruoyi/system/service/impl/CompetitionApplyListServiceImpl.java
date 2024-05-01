package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.CompetitionActivity;
import com.ruoyi.system.domain.CompetitionAwards;
import com.ruoyi.system.domain.CompetitionSignUp;
import com.ruoyi.system.mapper.CompetitionActivityMapper;
import com.ruoyi.system.mapper.CompetitionSignUpMapper;
import com.ruoyi.system.service.ICompetitionApplyListService;
import com.ruoyi.system.service.ICompetitionAwardsService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 报名列表Service业务层处理
 *
 * @author foxmo
 * @date 2024-03-20
 */
@Service
public class CompetitionApplyListServiceImpl implements ICompetitionApplyListService {
    @Resource
    private CompetitionSignUpMapper competitionSignUpMapper;

    @Resource
    private CompetitionActivityMapper competitionActivityMapper;

    @Resource
    private ICompetitionAwardsService competitionAwardsService;

    @Resource
    private MapperFacade mapperFacade;

    /**
     * 查询报名列表
     *
     * @param signUpId 报名列表主键
     * @return 报名列表
     */
    @Override
    public CompetitionSignUp selectCompetitionApplyListBySignUpId(Long signUpId) {
        return competitionSignUpMapper.selectCompetitionSignUpBySignUpId(signUpId);
    }

    /**
     * 根据登录用户查询竞赛报名列表
     *
     * @param competitionSignUp 竞赛报名
     * @return 竞赛报名集合
     */
    @Override
    public List<CompetitionSignUp> selectLoginCompetitionSignUpList(CompetitionSignUp competitionSignUp) {
        List<CompetitionSignUp> competitionSignUpList = competitionSignUpMapper.selectLoginCompetitionSignUpList(competitionSignUp);
        return competitionSignUpList;
    }

    /**
     * 批量删除报名列表
     *
     * @param signUpIds 需要删除的报名列表主键
     * @return 结果
     */
    @Override
    public int deleteCompetitionApplyListBySignUpIds(Long[] signUpIds) throws Error {
        List<CompetitionSignUp> competitionSignUpList = competitionSignUpMapper.selectList(new LambdaQueryWrapper<CompetitionSignUp>()
                .in(CompetitionSignUp::getSignUpId, signUpIds)
                .eq(CompetitionSignUp::getDelFlag, false));
        List<Long> activityIdList = competitionSignUpList.stream().map(item -> item.getActivityId()).distinct().collect(Collectors.toList());
        List<CompetitionActivity> competitionActivityList = competitionActivityMapper.selectList(new LambdaQueryWrapper<CompetitionActivity>()
                .in(CompetitionActivity::getActivityId, activityIdList)
                .lt(CompetitionActivity::getSignUpEndTime, DateUtils.getNowDate())
                .eq(CompetitionActivity::getStatus, "0")
                .eq(CompetitionActivity::getDelFlag, false));
        if (ObjectUtil.isNotEmpty(competitionActivityList)){
            throw new Error("竞赛活动报名时间结束，无法取消报名！");
        }
        CompetitionAwards competitionAwards = competitionAwardsService.selectEnableCompetitionAwardsByActivityIdAndStudentId(activityIdList.get(0), competitionSignUpList.get(0).getStudentId());
        if (ObjectUtil.isNotEmpty(competitionAwards)){
            throw new Error("竞赛活动成绩已发布，无法取消报名！");
        }
        int count = 0;
        for (CompetitionSignUp competitionSignUp : competitionSignUpList) {
            competitionSignUp.setUpdateBy(SecurityUtils.getUsername());
            competitionSignUp.setUpdateTime(DateUtils.getNowDate());
            competitionSignUp.setDelFlag(true);
            count += competitionSignUpMapper.update(competitionSignUp, new LambdaQueryWrapper<CompetitionSignUp>()
                    .eq(CompetitionSignUp::getSignUpId, competitionSignUp.getSignUpId()));
        }
        return count;
    }

    /**
     * 删除报名列表信息
     *
     * @param signUpId 报名列表主键
     * @return 结果
     */
    @Override
    public int deleteCompetitionApplyListBySignUpId(Long signUpId) {
        CompetitionSignUp competitionSignUp = competitionSignUpMapper.selectOne(new LambdaQueryWrapper<CompetitionSignUp>()
                .eq(CompetitionSignUp::getSignUpId, signUpId)
                .eq(CompetitionSignUp::getDelFlag, false));
        if (ObjectUtil.isEmpty(competitionSignUp)) {
            return 1;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        competitionSignUp.setUpdateBy(loginUser.getUsername());
        competitionSignUp.setUpdateTime(DateUtils.getNowDate());
        competitionSignUp.setDelFlag(true);
        return competitionSignUpMapper.update(competitionSignUp, new LambdaQueryWrapper<CompetitionSignUp>()
                .eq(CompetitionSignUp::getSignUpId, competitionSignUp.getSignUpId()));
    }
}
