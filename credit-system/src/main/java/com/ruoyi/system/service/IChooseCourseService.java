package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.StudentSubject;
import com.ruoyi.system.domain.SubjectInfo;

/**
 * 选课中心Service接口
 * 
 * @author foxmo
 * @date 2024-03-21
 */
public interface IChooseCourseService 
{
    /**
     * 查询选课中心
     * 
     * @param subjectId 选课中心主键
     * @return 选课中心
     */
    public SubjectInfo selectChooseCourseBySubjectId(Long subjectId);

    /**
     * 查询选课中心列表
     * 
     * @param subjectInfo 选课中心
     * @return 选课中心集合
     */
    public List<SubjectInfo> selectChooseCourseList(SubjectInfo subjectInfo);

    /**
     * 根据查询条件查询登录学生所属专业的所有选修学科信息列表
     *
     * @param subjectInfo 选课中心
     * @return 选课中心
     */
    public List<SubjectInfo> selectChooseCourseListByQueryParams(SubjectInfo subjectInfo);

    /**
     * 新增选课
     * 
     * @param studentSubject 选课信息
     * @return 结果
     */
    public int insertChooseCourse(StudentSubject studentSubject);

    /**
     * 修改选课中心
     * 
     * @param subjectInfo 选课中心
     * @return 结果
     */
    public int updateChooseCourse(SubjectInfo subjectInfo);

    /**
     * 批量删除选课中心
     * 
     * @param subjectIds 需要删除的选课中心主键集合
     * @return 结果
     */
    public int deleteChooseCourseBySubjectIds(Long[] subjectIds);

    /**
     * 删除选课中心信息
     * 
     * @param subjectId 选课中心主键
     * @return 结果
     */
    public int deleteChooseCourseBySubjectId(Long subjectId);
}
