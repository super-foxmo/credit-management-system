package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SubjectInfo;

/**
 * 学科信息Service接口
 * 
 * @author foxmo
 * @date 2024-03-18
 */
public interface ISubjectInfoService 
{
    /**
     * 查询学科信息
     * 
     * @param subjectId 学科信息主键
     * @return 学科信息
     */
    public SubjectInfo selectSubjectInfoBySubjectId(Long subjectId);

    /**
     * 查询学科信息列表
     * 
     * @param subjectInfo 学科信息
     * @return 学科信息集合
     */
    public List<SubjectInfo> selectSubjectInfoList(SubjectInfo subjectInfo);

    /**
     * 新增学科信息
     * 
     * @param subjectInfo 学科信息
     * @return 结果
     */
    public int insertSubjectInfo(SubjectInfo subjectInfo);

    /**
     * 修改学科信息
     * 
     * @param subjectInfo 学科信息
     * @return 结果
     */
    public int updateSubjectInfo(SubjectInfo subjectInfo);

    /**
     * 批量删除学科信息
     * 
     * @param subjectIds 需要删除的学科信息主键集合
     * @return 结果
     */
    public int deleteSubjectInfoBySubjectIds(Long[] subjectIds);

    /**
     * 删除学科信息信息
     * 
     * @param subjectId 学科信息主键
     * @return 结果
     */
    public int deleteSubjectInfoBySubjectId(Long subjectId);
}
