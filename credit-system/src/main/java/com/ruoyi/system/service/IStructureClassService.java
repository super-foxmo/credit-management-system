package com.ruoyi.system.service;

import java.util.ArrayList;
import java.util.List;
import com.ruoyi.system.domain.StructureClass;

/**
 * 班级信息Service接口
 * 
 * @author foxmo
 * @date 2024-03-14
 */
public interface IStructureClassService 
{
    /**
     * 查询班级信息
     * 
     * @param classId 班级信息主键
     * @return 班级信息
     */
    public StructureClass selectStructureClassByClassId(Long classId);

    /**
     * 查询状态开启的班级信息
     *
     * @param classIdList 班级信息主键集合
     * @return 班级信息
     */
    public List<StructureClass> selectEnableStructureClassListByClassIds(List<Long> classIdList);

    /**
     * 根据班级名称和专业Id查询班级信息
     *
     * @param className 班级名称
     * @param specialtyId 专业编号
     * @return 班级信息
     */
    public StructureClass selectStructureClassByClassNameAndSpecialtyId(String className, Long specialtyId);

    /**
     * 查询班级信息列表
     *
     * @param structureClass 班级信息
     * @return 班级信息集合
     */
    public List<StructureClass> selectStructureClassList(StructureClass structureClass);

    /**
     * 查询状态开启的班级信息列表
     *
     * @param structureClass 班级信息
     * @return 班级信息
     */
    public List<StructureClass> selectEnableStructureClassList(StructureClass structureClass);

    /**
     * 新增班级信息
     * 
     * @param structureClass 班级信息
     * @return 结果
     */
    public int insertStructureClass(StructureClass structureClass);

    /**
     * 修改班级信息
     * 
     * @param structureClass 班级信息
     * @return 结果
     */
    public int updateStructureClass(StructureClass structureClass);

    /**
     * 批量删除班级信息
     * 
     * @param classIds 需要删除的班级信息主键集合
     * @return 结果
     */
    public int deleteStructureClassByClassIds(Long[] classIds);

    /**
     * 删除班级信息信息
     * 
     * @param classId 班级信息主键
     * @return 结果
     */
    public int deleteStructureClassByClassId(Long classId);
}
