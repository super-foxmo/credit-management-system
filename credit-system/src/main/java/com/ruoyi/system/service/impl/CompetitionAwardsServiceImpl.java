package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.CompetitionActivity;
import com.ruoyi.system.domain.CompetitionAwards;
import com.ruoyi.system.mapper.CompetitionAwardsMapper;
import com.ruoyi.system.service.ICompetitionAwardsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 竞赛获奖Service业务层处理
 *
 * @author foxmo
 * @date 2024-03-14
 */
@Service
public class CompetitionAwardsServiceImpl implements ICompetitionAwardsService {
    @Resource
    private CompetitionAwardsMapper competitionAwardsMapper;

    /**
     * 查询竞赛获奖
     *
     * @param awardsId 竞赛获奖主键
     * @return 竞赛获奖
     */
    @Override
    public CompetitionAwards selectCompetitionAwardsByAwardsId(Long awardsId) {
        return competitionAwardsMapper.selectOne(new LambdaQueryWrapper<CompetitionAwards>()
                .eq(CompetitionAwards::getAwardsId, awardsId)
                .eq(CompetitionAwards::getDelFlag, false));
    }

    /**
     * 根据ids查询未被删除的竞赛获奖集合
     *
     * @param awardsIds 竞赛获奖主键集合
     * @return 竞赛获奖
     */
    @Override
    public List<CompetitionAwards> selectEnableCompetitionAwardsByAwardsIds(Long[] awardsIds){
        return competitionAwardsMapper.selectList(new LambdaQueryWrapper<CompetitionAwards>()
                .in(CompetitionAwards::getAwardsId, awardsIds)
                .eq(CompetitionAwards::getStatus, "0")
                .eq(CompetitionAwards::getDelFlag, false));
    }

    /**
     * 根据学生id查询状态开启的的竞赛获奖集合
     *
     * @param studentId 学生id
     * @return 竞赛获奖
     */
    @Override
    public List<CompetitionAwards> selectEnableCompetitionAwardsByStudentId(Long studentId){
        return competitionAwardsMapper.selectList(new LambdaQueryWrapper<CompetitionAwards>()
                .eq(CompetitionAwards::getStudentId, studentId)
                .eq(CompetitionAwards::getStatus, "0")
                .eq(CompetitionAwards::getDelFlag, false));
    }

    /**
     * 根据竞赛id集合和学生id查询状态开启的的竞赛获奖集合
     *
     * @param awardsIdList 竞赛id集合
     * @param studentId 学生id
     * @return 竞赛获奖
     */
    @Override
    public CompetitionAwards selectEnableCompetitionAwardsByActivityIdAndStudentId(Long activityId, Long studentId){
        return competitionAwardsMapper.selectOne(new LambdaQueryWrapper<CompetitionAwards>()
                .eq(CompetitionAwards::getActivityId, activityId)
                .eq(CompetitionAwards::getStudentId, studentId)
                .eq(CompetitionAwards::getStatus, "0")
                .eq(CompetitionAwards::getDelFlag, false));
    }

    /**
     * 根据学生id查询指定学年的竞赛获奖集合
     *
     * @param studentId 学生主键
     * @param academicYear 学年
     * @return 竞赛获奖集合
     */
    @Override
    public List<CompetitionAwards> selectCompetitionAwardsByStudentIdAndAcademicYear(Long studentId, Long academicYear) {
        List<CompetitionAwards> competitionAwardsList = competitionAwardsMapper.selectList(new LambdaQueryWrapper<CompetitionAwards>()
                .eq(CompetitionAwards::getStudentId, studentId)
                .eq(CompetitionAwards::getStatus, "0")
                .eq(CompetitionAwards::getDelFlag, false));
        ArrayList<CompetitionAwards> result = new ArrayList<>();
        for (CompetitionAwards competitionAwards : competitionAwardsList) {
            if (academicYear.equals(DateUtil.betweenYear(competitionAwards.getCreateTime(), DateUtils.getNowDate(), false) + 1L)){
                result.add(competitionAwards);
            }
        }
        return result;
    }

    /**
     * 查询竞赛获奖列表
     *
     * @param competitionAwards 竞赛获奖
     * @return 竞赛获奖
     */
    @Override
    public List<CompetitionAwards> selectCompetitionAwardsList(CompetitionAwards competitionAwards) {
        return competitionAwardsMapper.selectCompetitionAwardsList(competitionAwards);
    }

    /**
     * 新增竞赛获奖
     *
     * @param competitionAwards 竞赛获奖
     * @return 结果
     */
    @Override
    public int insertCompetitionAwards(CompetitionAwards competitionAwards) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        competitionAwards.setCreateBy(loginUser.getUsername());
        competitionAwards.setCreateTime(DateUtils.getNowDate());
        return competitionAwardsMapper.insertCompetitionAwards(competitionAwards);
    }

    /**
     * 修改竞赛获奖
     *
     * @param competitionAwards 竞赛获奖
     * @return 结果
     */
    @Override
    public int updateCompetitionAwards(CompetitionAwards competitionAwards) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        competitionAwards.setUpdateBy(loginUser.getUsername());
        competitionAwards.setUpdateTime(DateUtils.getNowDate());
        return competitionAwardsMapper.update(competitionAwards, new LambdaQueryWrapper<CompetitionAwards>()
                .eq(CompetitionAwards::getAwardsId, competitionAwards.getAwardsId()));
    }

    /**
     * 批量删除竞赛获奖
     *
     * @param awardsIds 需要删除的竞赛获奖主键
     * @return 结果
     */
    @Override
    public int deleteCompetitionAwardsByAwardsIds(Long[] awardsIds) {
        List<CompetitionAwards> competitionAwardsList = competitionAwardsMapper.selectList(new LambdaQueryWrapper<CompetitionAwards>()
                .in(CompetitionAwards::getAwardsId, awardsIds)
                .eq(CompetitionAwards::getDelFlag, false));
        int count = 0;
        LoginUser loginUser = SecurityUtils.getLoginUser();
        for (CompetitionAwards competitionAwards : competitionAwardsList) {
            competitionAwards.setUpdateBy(loginUser.getUsername());
            competitionAwards.setUpdateTime(DateUtils.getNowDate());
            competitionAwards.setDelFlag(true);
            count += competitionAwardsMapper.update(competitionAwards, new LambdaQueryWrapper<CompetitionAwards>()
                    .eq(CompetitionAwards::getAwardsId, competitionAwards.getAwardsId()));
        }
        return count;
    }

    /**
     * 删除竞赛获奖信息
     *
     * @param awardsId 竞赛获奖主键
     * @return 结果
     */
    @Override
    public int deleteCompetitionAwardsByAwardsId(Long awardsId) {
        CompetitionAwards competitionAwards = competitionAwardsMapper.selectOne(new LambdaQueryWrapper<CompetitionAwards>()
                .eq(CompetitionAwards::getAwardsId, awardsId)
                .eq(CompetitionAwards::getDelFlag, false));
        if (ObjectUtil.isEmpty(competitionAwards)) {
            return 1;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        competitionAwards.setUpdateBy(loginUser.getUsername());
        competitionAwards.setUpdateTime(DateUtils.getNowDate());
        competitionAwards.setDelFlag(true);
        return competitionAwardsMapper.update(competitionAwards, new LambdaQueryWrapper<CompetitionAwards>()
                .eq(CompetitionAwards::getAwardsId, competitionAwards.getAwardsId()));
    }
}
