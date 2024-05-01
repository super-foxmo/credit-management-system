package com.ruoyi.system.service.impl;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.CompetitionGrade;
import com.ruoyi.system.domain.CompetitionSignUp;
import com.ruoyi.system.mapper.CompetitionSignUpMapper;
import com.ruoyi.system.service.ICompetitionSignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 竞赛报名Service业务层处理
 *
 * @author foxmo
 * @date 2024-03-14
 */
@Service
public class CompetitionSignUpServiceImpl implements ICompetitionSignUpService {
    @Resource
    private CompetitionSignUpMapper competitionSignUpMapper;


    /**
     * 查询竞赛报名
     *
     * @param signUpId 竞赛报名主键
     * @return 竞赛报名
     */
    @Override
    public CompetitionSignUp selectCompetitionSignUpBySignUpId(Long signUpId) {
        return competitionSignUpMapper.selectOne(new LambdaQueryWrapper<CompetitionSignUp>()
                .eq(CompetitionSignUp::getSignUpId, signUpId)
                .eq(CompetitionSignUp::getDelFlag, false));
    }

    /**
     * 根据用户id和竞赛id查询竞赛报名
     *
     * @param studentId  用户主键
     * @param activityId 竞赛活动主键
     * @return 竞赛报名
     */
    @Override
    public CompetitionSignUp selectCompetitionSignUpByUserIdAndActivityId(Long studentId, Long activityId) {
        return competitionSignUpMapper.selectOne(new LambdaQueryWrapper<CompetitionSignUp>()
                .eq(CompetitionSignUp::getStudentId, studentId)
                .eq(CompetitionSignUp::getActivityId, activityId)
                .eq(CompetitionSignUp::getStatus, "0")
                .eq(CompetitionSignUp::getDelFlag, false));
    }

    /**
     * 查询竞赛报名列表
     *
     * @param competitionSignUp 竞赛报名
     * @return 竞赛报名
     */
    @Override
    public List<CompetitionSignUp> selectCompetitionSignUpList(CompetitionSignUp competitionSignUp) {
        return competitionSignUpMapper.selectCompetitionSignUpList(competitionSignUp);
    }

    /**
     * 根据学生名称或学号模糊查询竞赛报名列表
     *
     * @param competitionSignUp 竞赛报名
     * @return 竞赛报名集合
     */
    @Override
    public List<CompetitionSignUp> selectCompetitionSignUpListByStudent(CompetitionSignUp competitionSignUp){
        LambdaQueryWrapper<CompetitionSignUp> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(competitionSignUp.getStudentNumber())){
            queryWrapper.like(CompetitionSignUp::getStudentNumber, competitionSignUp.getStudentNumber());
        }
        if (ObjectUtil.isEmpty(competitionSignUp.getStudentNumber()) && ObjectUtil.isNotEmpty(competitionSignUp.getStudentName())){
            queryWrapper.like(CompetitionSignUp::getStudentName, competitionSignUp.getStudentName());
        }
        queryWrapper.eq(CompetitionSignUp::getStatus, "0")
                .eq(CompetitionSignUp::getDelFlag, false);
        return competitionSignUpMapper.selectList(queryWrapper);
    }

    /**
     * 新增竞赛报名
     *
     * @param competitionSignUp 竞赛报名
     * @return 结果
     */
    @Override
    public int insertCompetitionSignUp(CompetitionSignUp competitionSignUp) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        competitionSignUp.setCreateBy(loginUser.getUsername());
        competitionSignUp.setCreateTime(DateUtils.getNowDate());
        return competitionSignUpMapper.insertCompetitionSignUp(competitionSignUp);
    }

    /**
     * 修改竞赛报名
     *
     * @param competitionSignUp 竞赛报名
     * @return 结果
     */
    @Override
    public int updateCompetitionSignUp(CompetitionSignUp competitionSignUp) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        competitionSignUp.setUpdateBy(loginUser.getUsername());
        competitionSignUp.setUpdateTime(DateUtils.getNowDate());
        return competitionSignUpMapper.update(competitionSignUp, new LambdaQueryWrapper<CompetitionSignUp>()
                .eq(CompetitionSignUp::getSignUpId, competitionSignUp.getSignUpId()));
    }

    /**
     * 批量删除竞赛报名
     *
     * @param signUpIds 需要删除的竞赛报名主键
     * @return 结果
     */
    @Override
    public int deleteCompetitionSignUpBySignUpIds(Long[] signUpIds) {
        List<CompetitionSignUp> competitionSignUpList = competitionSignUpMapper.selectList(new LambdaQueryWrapper<CompetitionSignUp>()
                .in(CompetitionSignUp::getSignUpId, signUpIds)
                .eq(CompetitionSignUp::getDelFlag, false));
        int count = 0;
        LoginUser loginUser = SecurityUtils.getLoginUser();
        for (CompetitionSignUp competitionSignUp : competitionSignUpList) {
            competitionSignUp.setUpdateBy(loginUser.getUsername());
            competitionSignUp.setUpdateTime(DateUtils.getNowDate());
            competitionSignUp.setDelFlag(true);
            count += competitionSignUpMapper.update(competitionSignUp, new LambdaQueryWrapper<CompetitionSignUp>()
                    .eq(CompetitionSignUp::getSignUpId, competitionSignUp.getSignUpId()));
        }
        return count;
    }

    /**
     * 删除竞赛报名信息
     *
     * @param signUpId 竞赛报名主键
     * @return 结果
     */
    @Override
    public int deleteCompetitionSignUpBySignUpId(Long signUpId) {
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
