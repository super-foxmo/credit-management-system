package com.ruoyi.system.service;

import com.ruoyi.system.domain.StructureSpecialty;

import java.util.List;

/**
 * 专业信息Service接口
 *
 * @author foxmo
 * @date 2024-03-14
 */
public interface IStructureSpecialtyService
{
    /**
     * 查询专业信息
     *
     * @param specialtyId 专业信息主键
     * @return 专业信息
     */
    public StructureSpecialty selectStructureSpecialtyBySpecialtyId(Long specialtyId);

    /**
     * 根据ids查询专业信息集合
     *
     * @param specialtyIdList 专业信息主键集合
     * @return 专业信息
     */
    public List<StructureSpecialty> selectEnableStructureSpecialtyListBySpecialtyIdList(List<Long> specialtyIdList);

    /**
     * 根据专业名称和学院id查询专业信息
     *
     * @param specialtyName 专业名称
     * @return 专业信息
     */
    public StructureSpecialty selectStructureSpecialtyBySpecialtyNameAndCollegeId(String specialtyName, Long CollegeId);

    /**
     * 查询专业信息列表
     *
     * @param structureSpecialty 专业信息
     * @return 专业信息集合
     */
    public List<StructureSpecialty> selectStructureSpecialtyList(StructureSpecialty structureSpecialty);

    /**
     * 查询状态开启的专业信息列表
     *
     * @param structureSpecialty 专业信息
     * @return 专业信息
     */
    public List<StructureSpecialty> selectEnableStructureSpecialtyList(StructureSpecialty structureSpecialty);

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
     * 批量删除专业信息
     *
     * @param specialtyIds 需要删除的专业信息主键集合
     * @return 结果
     */
    public int deleteStructureSpecialtyBySpecialtyIds(Long[] specialtyIds);

    /**
     * 删除专业信息信息
     *
     * @param specialtyId 专业信息主键
     * @return 结果
     */
    public int deleteStructureSpecialtyBySpecialtyId(Long specialtyId);
}