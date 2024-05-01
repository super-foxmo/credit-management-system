package com.ruoyi.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.enums.AcademicYear;
import com.ruoyi.common.enums.SubjectType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.entity.RankInfo;
import com.ruoyi.system.domain.entity.ScoreRule;
import com.ruoyi.system.service.*;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentEvaluationServiceImpl implements IStudentEvaluationService {
    @Resource
    private IStudentSubjectService studentSubjectService;

    @Resource
    private ICompetitionActivityService competitionActivityService;

    @Resource
    private ICompetitionAwardsService competitionAwardsService;

    @Resource
    private IUserStudentService studentService;

    @Resource
    private IComprehensiveEvaluationService comprehensiveEvaluationService;

    @Resource
    private MapperFacade mapperFacade;

    /**
     * 获取指定学生的综测分数和排名信息
     *
     * @param studentId    学生主键
     * @param academicYear 学年
     * @return
     */
    @Override
    public StudentEvaluation getStudentEvaluation(Long studentId, Long academicYear) {
        // 若学年为空，则默认查询最后已完结学年的综测分数排名信息
        if (ObjectUtil.isEmpty(academicYear)) {
            UserStudent student = studentService.selectUserStudentByStudentId(studentId);
            academicYear = DateUtil.betweenYear(student.getEntranceTime(), DateUtils.getNowDate(), false);
        }
        List<StudentSubject> studentSubjectList = studentSubjectService.selectStudentSubjectsByStudentIdAndAcademicYear(studentId, academicYear);
        // 绩点总和
        Float allScore = 0F;
        // 学分总和
        Float allCredit = 0F;
        // 绩点总和（排除公选）
        Float allScoreNoPublic = 0F;
        // 学分总和（排除公选）
        Float allCreditNoPublic = 0F;
        for (StudentSubject studentSubject : studentSubjectList) {
            allScore += studentSubject.getScore();
            allCredit += studentSubject.getAllCredit();
            if (!SubjectType.PUBLIC_ELECTIVE_COURSE.getCode().equals(studentSubject.getSubjectType())) {
                allScoreNoPublic += studentSubject.getScore();
                allCreditNoPublic += studentSubject.getAllCredit();
            }
        }
        // 综合分
        RankInfo synthesizeEvaluation = new RankInfo();
        // 成绩
        RankInfo scoreEvaluation = new RankInfo();
        // 绩点
        RankInfo GPA = new RankInfo();
        // 思想分
        RankInfo thoughtEvaluation = new RankInfo();
        // 文体分
        RankInfo stylisticEvaluation = new RankInfo();
        // 学业分
        RankInfo academicEvaluation = new RankInfo();
        GPA.setScore(allScore / allCredit);
        scoreEvaluation.setScore((allScoreNoPublic / allCreditNoPublic + 5) * 9);

        return null;
    }

    /**
     * 更新指定学生某学年的课程综测数据
     *
     * @param studentId    学生主键
     * @param academicYear 学年
     * @return
     */
    @Override
    public int updateSubjectEvaluation(Long studentId, Long academicYear) {
        ComprehensiveEvaluation comprehensiveEvaluationDB = comprehensiveEvaluationService
                .selectComprehensiveEvaluationByStudentIdAndAcademicYear(studentId, academicYear);
        if (ObjectUtil.isEmpty(comprehensiveEvaluationDB)) {
            throw new Error("更新指定学生某学年的课程综测数据时该学生的综合评测数据为空");
        }
        List<StudentSubject> studentSubjectList = studentSubjectService
                .selectStudentSubjectsByStudentIdAndAcademicYear(studentId, academicYear);
        // 绩点总和
        Float allScore = 0F;
        // 学分总和
        Float allCredit = 0F;
        // 绩点总和（排除公选）
        Float allScoreNoPublic = 0F;
        // 学分总和（排除公选）
        Float allCreditNoPublic = 0F;
        for (StudentSubject studentSubject : studentSubjectList) {
            allScore += ObjectUtil.isNotEmpty(studentSubject.getScore()) ? studentSubject.getScore() : 0;
            allCredit += ObjectUtil.isNotEmpty(studentSubject.getAllCredit()) ? studentSubject.getAllCredit() : 0;
            if (!SubjectType.PUBLIC_ELECTIVE_COURSE.getCode().equals(studentSubject.getSubjectType())) {
                allScoreNoPublic += ObjectUtil.isNotEmpty(studentSubject.getScore()) ? studentSubject.getScore() : 0;
                allCreditNoPublic += ObjectUtil.isNotEmpty(studentSubject.getAllCredit()) ? studentSubject.getAllCredit() : 0;
            }
        }
        // 基础学业分
        Float score = (allScoreNoPublic / allCreditNoPublic + 5) * 9;
        // 绩点
        Float GPA = allScore / allCredit;
        Float thoughtScore = comprehensiveEvaluationDB.getThoughtScore();
        Float stylisticScore = comprehensiveEvaluationDB.getStylisticScore();
        Float academicScore = comprehensiveEvaluationDB.getAcademicScore();
        // 总学业分
        Float allAcademicScore = score + academicScore;
        // 综合分
        Double synthesizeEvaluation = (allAcademicScore * 0.7 + thoughtScore * 0.2 + stylisticScore * 0.1);
        // 保留两位小数
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        Float synthesizeScore = Float.valueOf(numberFormat.format(synthesizeEvaluation));
        // 更新学生综测数据
        comprehensiveEvaluationDB.setSynthesizeScore(synthesizeScore);
        comprehensiveEvaluationDB.setScore(score);
        comprehensiveEvaluationDB.setGPA(GPA);
        return comprehensiveEvaluationService.updateComprehensiveEvaluation(comprehensiveEvaluationDB);
    }

    /**
     * 更新指定学生某学年的竞赛活动综测数据
     * @param studentId 学生主键
     * @param academicYear 学年
     * @return
     */
    @Override
    public int updateActivityEvaluation(Long studentId, Long academicYear){
        List<CompetitionAwards> competitionAwardsList = competitionAwardsService.selectCompetitionAwardsByStudentIdAndAcademicYear(studentId, academicYear);
        List<Long> activityIds = competitionAwardsList.stream().map(item -> item.getActivityId()).distinct().collect(Collectors.toList());
        List<CompetitionActivity> competitionActivityList = competitionActivityService.selectCompetitionActivityByActivityIds(activityIds);
        Map<Long, List<ScoreRule>> actvityIdToRuleMap = competitionActivityList.stream().collect(Collectors.toMap(CompetitionActivity::getActivityId, CompetitionActivity::getScoreRule));
        // 分数统计
        ComprehensiveEvaluation comprehensiveEvaluation = comprehensiveEvaluationService.selectComprehensiveEvaluationByStudentIdAndAcademicYear(studentId, academicYear);
        Float thoughtScore = 0F;
        Float stylisticScore = 0F;
        Float academicScore = 0F;
        for (CompetitionAwards competitionAwards : competitionAwardsList) {
            List<ScoreRule> scoreRules = actvityIdToRuleMap.get(competitionAwards.getActivityId());
            ScoreRule targetScoreRule = null;
            for (ScoreRule scoreRule : scoreRules) {
                if (scoreRule.getGradeId().equals(competitionAwards.getGradeId())){
                    targetScoreRule = scoreRule;
                    break;
                }
            }
            if (ObjectUtil.isNotEmpty(targetScoreRule)){
                thoughtScore += targetScoreRule.getThoughtScore();
                stylisticScore += targetScoreRule.getStylisticScore();
                academicScore += targetScoreRule.getAcademicScore();
            }
        }

        // 总学业分
        Float allAcademicScore = comprehensiveEvaluation.getScore() + academicScore;
        // 综合分
        Double synthesizeEvaluation = (allAcademicScore * 0.7 + thoughtScore * 0.2 + stylisticScore * 0.1);
        // 保留两位小数
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        Float synthesizeScore = Float.valueOf(numberFormat.format(synthesizeEvaluation));
        // 更新学生综测数据
        comprehensiveEvaluation.setThoughtScore(thoughtScore);
        comprehensiveEvaluation.setStylisticScore(stylisticScore);
        comprehensiveEvaluation.setAcademicScore(academicScore);
        comprehensiveEvaluation.setSynthesizeScore(synthesizeScore);
        return comprehensiveEvaluationService.updateComprehensiveEvaluation(comprehensiveEvaluation);
    }

    /**
     * 初始化指定学生所有学年的课程综测数据
     * @param student 学生信息
     * @return
     */
    @Override
    public int initStudentEvaluation(UserStudent student){
        ComprehensiveEvaluation comprehensiveEvaluation = mapperFacade.map(student, ComprehensiveEvaluation.class);
        int result = 0;
        comprehensiveEvaluation.setAcademicYear(Long.valueOf(AcademicYear.ONE.getCode()));
        result += comprehensiveEvaluationService.insertComprehensiveEvaluation(comprehensiveEvaluation);
        comprehensiveEvaluation.setAcademicYear(Long.valueOf(AcademicYear.TWO.getCode()));
        result += comprehensiveEvaluationService.insertComprehensiveEvaluation(comprehensiveEvaluation);
        comprehensiveEvaluation.setAcademicYear(Long.valueOf(AcademicYear.THREE.getCode()));
        result += comprehensiveEvaluationService.insertComprehensiveEvaluation(comprehensiveEvaluation);
        comprehensiveEvaluation.setAcademicYear(Long.valueOf(AcademicYear.FOUR.getCode()));
        result += comprehensiveEvaluationService.insertComprehensiveEvaluation(comprehensiveEvaluation);
        return result;
    }

    /**
     * 删除指定学生所有学年的课程综测数据
     * @param studentId 学生主键
     * @return
     */
    @Override
    public int deleteStudentEvaluation(Long studentId){
        return comprehensiveEvaluationService.deleteComprehensiveEvaluationByStudentId(studentId);
    }

}
