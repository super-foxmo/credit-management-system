package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.AcademicYear;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.UserStudent;
import com.ruoyi.system.service.IUserStudentService;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ComprehensiveEvaluationMapper;
import com.ruoyi.system.domain.ComprehensiveEvaluation;
import com.ruoyi.system.service.IComprehensiveEvaluationService;

import javax.annotation.Resource;

/**
 * 综测数据汇总Service业务层处理
 * 
 * @author foxmo
 * @date 2024-03-29
 */
@Service
public class ComprehensiveEvaluationServiceImpl implements IComprehensiveEvaluationService 
{
    @Resource
    private ComprehensiveEvaluationMapper comprehensiveEvaluationMapper;

    @Resource
    private IUserStudentService studentService;

    /**
     * 查询综测数据汇总
     * 
     * @param comprehensiveId 综测数据汇总主键
     * @return 综测数据汇总
     */
    @Override
    public ComprehensiveEvaluation selectComprehensiveEvaluationByComprehensiveId(Long comprehensiveId)
    {
        return comprehensiveEvaluationMapper.selectComprehensiveEvaluationByComprehensiveId(comprehensiveId);
    }

    /**
     * 根据学生id和学年查询综测数据汇总
     *
     * @param studentId 学生主键
     * @param academicYear 学年
     * @return 综测数据汇总
     */
    @Override
    public ComprehensiveEvaluation selectComprehensiveEvaluationByStudentIdAndAcademicYear(Long studentId, Long academicYear){
        return comprehensiveEvaluationMapper.selectOne(new LambdaQueryWrapper<ComprehensiveEvaluation>()
                .eq(ComprehensiveEvaluation::getStudentId, studentId)
                .eq(ComprehensiveEvaluation::getAcademicYear, academicYear)
                .eq(ComprehensiveEvaluation::getStatus, "0")
                .eq(ComprehensiveEvaluation::getDelFlag, false));
    }

    /**
     * 查询当前登录学生的综测数据汇总列表
     *
     * @return 综测数据汇总
     */
    @Override
    public List<ComprehensiveEvaluation> selectStudentComprehensiveEvaluationList(UserStudent student)
    {
        ComprehensiveEvaluation sortParams = new ComprehensiveEvaluation();
        sortParams.setSpecialtyId(student.getSpecialtyId());
        Long maxAcademicYear = AcademicYear.FOUR.getCode();
        ArrayList<ComprehensiveEvaluation> result = new ArrayList<>();
        List<ComprehensiveEvaluation> tempComprehensiveEvaluations = new ArrayList<>();
        for (Long i = 1L; i <= maxAcademicYear; i++) {
            sortParams.setAcademicYear(i);
            tempComprehensiveEvaluations = comprehensiveEvaluationMapper.selectSpecialtySort(sortParams);
            result.add(tempComprehensiveEvaluations.stream()
                    .filter(item -> item.getStudentId().equals(student.getStudentId())).collect(Collectors.toList()).get(0));
        }
        sortParams.setSpecialtyId(null);
        sortParams.setClassId(student.getClassId());
        for (Long i = 1L; i <= maxAcademicYear; i++) {
            sortParams.setAcademicYear(i);
            tempComprehensiveEvaluations = comprehensiveEvaluationMapper.selectClassSort(sortParams);
            ComprehensiveEvaluation comprehensiveEvaluation = tempComprehensiveEvaluations.stream()
                    .filter(item -> item.getStudentId().equals(student.getStudentId())).collect(Collectors.toList()).get(0);
            result.get(i.intValue() - 1).setClassSort(comprehensiveEvaluation.getClassSort());
        }
        return result;
    }

    /**
     * 新增综测数据汇总
     * 
     * @param comprehensiveEvaluation 综测数据汇总
     * @return 结果
     */
    @Override
    public int insertComprehensiveEvaluation(ComprehensiveEvaluation comprehensiveEvaluation)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        comprehensiveEvaluation.setCreateBy(loginUser.getUsername());
        comprehensiveEvaluation.setCreateTime(DateUtils.getNowDate());
        return comprehensiveEvaluationMapper.insertComprehensiveEvaluation(comprehensiveEvaluation);
    }

    /**
     * 修改综测数据汇总
     * 
     * @param comprehensiveEvaluation 综测数据汇总
     * @return 结果
     */
    @Override
    public int updateComprehensiveEvaluation(ComprehensiveEvaluation comprehensiveEvaluation)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        comprehensiveEvaluation.setUpdateBy(loginUser.getUsername());
        comprehensiveEvaluation.setUpdateTime(DateUtils.getNowDate());
        return comprehensiveEvaluationMapper.updateComprehensiveEvaluation(comprehensiveEvaluation);
    }

    /**
     * 批量删除综测数据汇总
     * 
     * @param comprehensiveIds 需要删除的综测数据汇总主键
     * @return 结果
     */
    @Override
    public int deleteComprehensiveEvaluationByComprehensiveIds(Long[] comprehensiveIds)
    {
        return comprehensiveEvaluationMapper.deleteComprehensiveEvaluationByComprehensiveIds(comprehensiveIds);
    }

    /**
     * 删除综测数据汇总信息
     * 
     * @param comprehensiveId 综测数据汇总主键
     * @return 结果
     */
    @Override
    public int deleteComprehensiveEvaluationByComprehensiveId(Long comprehensiveId)
    {
        return comprehensiveEvaluationMapper.deleteComprehensiveEvaluationByComprehensiveId(comprehensiveId);
    }

    /**
     * 删除指定学生综测数据汇总信息
     *
     * @param studentId 学生主键
     * @return 结果
     */
    @Override
    public int deleteComprehensiveEvaluationByStudentId(Long studentId){
        return comprehensiveEvaluationMapper.delete(new LambdaQueryWrapper<ComprehensiveEvaluation>()
                .eq(ComprehensiveEvaluation::getStudentId,studentId));
    }
}
