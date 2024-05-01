package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.StructureSpecialty;

import java.util.List;

/**
 * 专业信息Mapper接口
 *
 * @author foxmo
 * @date 2024-03-14
 */
public interface StructureSpecialtyMapper extends BaseMapper<StructureSpecialty>
{
    /**
     * 查询专业信息
     *
     * @param specialtyId 专业信息主键
     * @return 专业信息
     */
    public StructureSpecialty selectStructureSpecialtyBySpecialtyId(Long specialtyId);

    /**
     * 查询专业信息列表
     *
     * @param structureSpecialty 专业信息
     * @return 专业信息集合
     */
    public List<StructureSpecialty> selectStructureSpecialtyList(StructureSpecialty structureSpecialty);

    /**
     * 新增专业信息
     *
     * @param structureSpecialty 专业信息
     * @return 结果
     */
    public int insertStructureSpecialty(StructureSpecialty structureSpecialty);

    /**
     * 修改专业信息
     *
     * @param structureSpecialty 专业信息
     * @return 结果
     */
    public int updateStructureSpecialty(StructureSpecialty structureSpecialty);

    /**
     * 删除专业信息
     *
     * @param specialtyId 专业信息主键
     * @return 结果
     */
    public int deleteStructureSpecialtyBySpecialtyId(Long specialtyId);

    /**
     * 批量删除专业信息
     *
     * @param specialtyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStructureSpecialtyBySpecialtyIds(Long[] specialtyIds);
}