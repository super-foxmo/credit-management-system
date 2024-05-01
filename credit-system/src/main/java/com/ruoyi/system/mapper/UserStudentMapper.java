package com.ruoyi.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.UserStudent;
import com.ruoyi.system.domain.entity.ScoreEvaluationQueryParams;

/**
 * 学生信息Mapper接口
 * 
 * @author foxmo
 * @date 2024-03-15
 */
public interface UserStudentMapper extends BaseMapper<UserStudent>
{
    /**
     * 查询学生信息
     * 
     * @param studentId 学生信息主键
     * @return 学生信息
     */
    public UserStudent selectUserStudentByStudentId(Long studentId);

    /**
     * 查询学生信息列表
     * 
     * @param userStudent 学生信息
     * @return 学生信息集合
     */
    public List<UserStudent> selectUserStudentList(UserStudent userStudent);

    /**
     * 新增学生信息
     * 
     * @param userStudent 学生信息
     * @return 结果
     */
    public int insertUserStudent(UserStudent userStudent);

    /**
     * 修改学生信息
     * 
     * @param userStudent 学生信息
     * @return 结果
     */
    public int updateUserStudent(UserStudent userStudent);

    /**
     * 删除学生信息
     * 
     * @param studentId 学生信息主键
     * @return 结果
     */
    public int deleteUserStudentByStudentId(Long studentId);

    /**
     * 批量删除学生信息
     * 
     * @param studentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserStudentByStudentIds(Long[] studentIds);

    /**
     * 查询指定专业和学年的学生信息
     *
     * @param queryParams 查询条件
     * @return 学生信息集合
     */
    public List<UserStudent> selectStudentBySpecialtyIdAndAcademicYear(ScoreEvaluationQueryParams queryParams);
}
