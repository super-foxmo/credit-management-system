package com.ruoyi.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.SubjectInfo;

/**
 * 学科信息Mapper接口
 * 
 * @author foxmo
 * @date 2024-03-18
 */
public interface SubjectInfoMapper extends BaseMapper<SubjectInfo>
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
     * 查询登录学生所属专业的可以选修学科信息列表（同一学年，同一学院，同一专业或公共选修）
     *
     * @param subjectInfo 学科信息
     * @return 学科信息集合
     */
    public List<SubjectInfo> selectEnableSubjectInfoList(SubjectInfo subjectInfo);

    /**
     * 根据查询条件查询登录学生所属专业的所有选修学科信息列表
     *
     * @param subjectInfo 选课中心
     * @return 选课中心
     */
    public List<SubjectInfo> selectChooseCourseListByQueryParams(SubjectInfo subjectInfo);

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
     * 删除学科信息
     * 
     * @param subjectId 学科信息主键
     * @return 结果
     */
    public int deleteSubjectInfoBySubjectId(Long subjectId);

    /**
     * 批量删除学科信息
     * 
     * @param subjectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSubjectInfoBySubjectIds(Long[] subjectIds);
}
