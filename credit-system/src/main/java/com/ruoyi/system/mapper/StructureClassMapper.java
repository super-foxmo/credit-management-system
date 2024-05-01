package com.ruoyi.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.StructureClass;

/**
 * 班级信息Mapper接口
 * 
 * @author foxmo
 * @date 2024-03-14
 */
public interface StructureClassMapper extends BaseMapper<StructureClass>
{
    /**
     * 查询班级信息
     * 
     * @param classId 班级信息主键
     * @return 班级信息
     */
    public StructureClass selectStructureClassByClassId(Long classId);

    /**
     * 查询班级信息列表
     * 
     * @param structureClass 班级信息
     * @return 班级信息集合
     */
    public List<StructureClass> selectStructureClassList(StructureClass structureClass);

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
     * 删除班级信息
     * 
     * @param classId 班级信息主键
     * @return 结果
     */
    public int deleteStructureClassByClassId(Long classId);

    /**
     * 批量删除班级信息
     * 
     * @param classIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStructureClassByClassIds(Long[] classIds);
}
