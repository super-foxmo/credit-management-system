package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.StructureCollege;

import java.util.List;

/**
 * 学院信息Mapper接口
 * 
 * @author foxmo
 * @date 2024-03-09
 */
public interface StructureCollegeMapper extends BaseMapper<StructureCollege>
{
    /**
     * 查询学院信息
     * 
     * @param collegeId 学院信息主键
     * @return 学院信息
     */
    public StructureCollege selectStructureCollegeByCollegeId(Long collegeId);

    /**
     * 查询学院信息列表
     * 
     * @param structureCollege 学院信息
     * @return 学院信息集合
     */
    public List<StructureCollege> selectStructureCollegeList(StructureCollege structureCollege);

    /**
     * 新增学院信息
     * 
     * @param structureCollege 学院信息
     * @return 结果
     */
    public int insertStructureCollege(StructureCollege structureCollege);

    /**
     * 修改学院信息
     * 
     * @param structureCollege 学院信息
     * @return 结果
     */
    public int updateStructureCollege(StructureCollege structureCollege);

    /**
     * 删除学院信息
     * 
     * @param collegeId 学院信息主键
     * @return 结果
     */
    public int deleteStructureCollegeByCollegeId(Long collegeId);

    /**
     * 批量删除学院信息
     * 
     * @param collegeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStructureCollegeByCollegeIds(Long[] collegeIds);
}
