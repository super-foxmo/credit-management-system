package com.ruoyi.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.StudentSubject;

/**
 * 成绩管理表Mapper接口
 * 
 * @author foxmo
 * @date 2024-03-19
 */
public interface StudentSubjectMapper extends BaseMapper<StudentSubject>
{
    /**
     * 查询成绩管理表
     * 
     * @param id 成绩管理表主键
     * @return 成绩管理表
     */
    public StudentSubject selectStudentSubjectById(Long id);

    /**
     * 查询成绩管理表列表
     * 
     * @param studentSubject 成绩管理表
     * @return 成绩管理表集合
     */
    public List<StudentSubject> selectStudentSubjectList(StudentSubject studentSubject);

    /**
     * 新增成绩管理表
     * 
     * @param studentSubject 成绩管理表
     * @return 结果
     */
    public int insertStudentSubject(StudentSubject studentSubject);

    /**
     * 修改成绩管理表
     * 
     * @param studentSubject 成绩管理表
     * @return 结果
     */
    public int updateStudentSubject(StudentSubject studentSubject);

    /**
     * 删除成绩管理表
     * 
     * @param id 成绩管理表主键
     * @return 结果
     */
    public int deleteStudentSubjectById(Long id);

    /**
     * 批量删除成绩管理表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentSubjectByIds(Long[] ids);
}
