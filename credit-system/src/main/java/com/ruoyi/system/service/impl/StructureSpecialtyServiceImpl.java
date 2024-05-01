package com.ruoyi.system.service.impl;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.StructureSpecialty;
import com.ruoyi.system.mapper.StructureSpecialtyMapper;
import com.ruoyi.system.service.IStructureSpecialtyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 专业信息Service业务层处理
 *
 * @author foxmo
 * @date 2024-03-14
 */
@Service
public class StructureSpecialtyServiceImpl implements IStructureSpecialtyService {
    @Resource
    private StructureSpecialtyMapper structureSpecialtyMapper;

    /**
     * 查询专业信息
     *
     * @param specialtyId 专业信息主键
     * @return 专业信息
     */
    @Override
    public StructureSpecialty selectStructureSpecialtyBySpecialtyId(Long specialtyId) {
        return structureSpecialtyMapper.selectOne(new LambdaQueryWrapper<StructureSpecialty>()
                .eq(StructureSpecialty::getSpecialtyId, specialtyId)
                .eq(StructureSpecialty::getDelFlag, false));
    }

    /**
     * 根据ids查询专业信息集合
     *
     * @param specialtyIdList 专业信息主键集合
     * @return 专业信息
     */
    @Override
    public List<StructureSpecialty> selectEnableStructureSpecialtyListBySpecialtyIdList(List<Long> specialtyIdList){
        return structureSpecialtyMapper.selectList(new LambdaQueryWrapper<StructureSpecialty>()
                .in(StructureSpecialty::getSpecialtyId, specialtyIdList)
                .eq(StructureSpecialty::getStatus, "0")
                .eq(StructureSpecialty::getDelFlag, false));
    }

    /**
     * 根据专业名称和学院id查询专业信息
     *
     * @param specialtyName 专业名称
     * @return 专业信息
     */
    @Override
    public StructureSpecialty selectStructureSpecialtyBySpecialtyNameAndCollegeId(String specialtyName, Long collegeId) {
        return structureSpecialtyMapper.selectOne(new LambdaQueryWrapper<StructureSpecialty>()
                .eq(StructureSpecialty::getSpecialtyName, specialtyName)
                .eq(StructureSpecialty::getCollegeId, collegeId)
                .eq(StructureSpecialty::getDelFlag, false));
    }

    /**
     * 查询专业信息列表
     *
     * @param structureSpecialty 专业信息
     * @return 专业信息
     */
    @Override
    public List<StructureSpecialty> selectStructureSpecialtyList(StructureSpecialty structureSpecialty) {
        return structureSpecialtyMapper.selectStructureSpecialtyList(structureSpecialty);
    }

    /**
     * 查询状态开启的专业信息列表
     *
     * @param structureSpecialty 专业信息
     * @return 专业信息
     */
    @Override
    public List<StructureSpecialty> selectEnableStructureSpecialtyList(StructureSpecialty structureSpecialty) {
        LambdaQueryWrapper<StructureSpecialty> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(structureSpecialty) && ObjectUtil.isNotEmpty(structureSpecialty.getCollegeId())) {
            queryWrapper.eq(StructureSpecialty::getCollegeId, structureSpecialty.getCollegeId());
        }
        queryWrapper.eq(StructureSpecialty::getStatus, "0")
                .eq(StructureSpecialty::getDelFlag, false);
        return structureSpecialtyMapper.selectList(queryWrapper);
    }

    /**
     * 新增专业信息
     *
     * @param structureSpecialty 专业信息
     * @return 结果
     */
    @Override
    public int insertStructureSpecialty(StructureSpecialty structureSpecialty) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        structureSpecialty.setCreateBy(loginUser.getUsername());
        structureSpecialty.setCreateTime(DateUtils.getNowDate());
        return structureSpecialtyMapper.insertStructureSpecialty(structureSpecialty);
    }

    /**
     * 修改专业信息
     *
     * @param structureSpecialty 专业信息
     * @return 结果
     */
    @Override
    public int updateStructureSpecialty(StructureSpecialty structureSpecialty) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        structureSpecialty.setUpdateBy(loginUser.getUsername());
        structureSpecialty.setUpdateTime(DateUtils.getNowDate());
        return structureSpecialtyMapper.updateStructureSpecialty(structureSpecialty);
    }

    /**
     * 批量删除专业信息
     *
     * @param specialtyIds 需要删除的专业信息主键
     * @return 结果
     */
    @Override
    public int deleteStructureSpecialtyBySpecialtyIds(Long[] specialtyIds) {
        List<StructureSpecialty> structureSpecialtyList = structureSpecialtyMapper.selectList(new LambdaQueryWrapper<StructureSpecialty>()
                .in(StructureSpecialty::getSpecialtyId, specialtyIds)
                .eq(StructureSpecialty::getDelFlag, false));
        int count = 0;
        LoginUser loginUser = SecurityUtils.getLoginUser();
        for (StructureSpecialty structureSpecialty : structureSpecialtyList) {
            structureSpecialty.setUpdateBy(loginUser.getUsername());
            structureSpecialty.setUpdateTime(DateUtils.getNowDate());
            structureSpecialty.setDelFlag(true);
            structureSpecialtyMapper.update(structureSpecialty, new LambdaQueryWrapper<StructureSpecialty>()
                    .eq(StructureSpecialty::getSpecialtyId, structureSpecialty.getSpecialtyId()));
            count++;
        }
        return count;
    }

    /**
     * 删除专业信息信息
     *
     * @param specialtyId 专业信息主键
     * @return 结果
     */
    @Override
    public int deleteStructureSpecialtyBySpecialtyId(Long specialtyId) {
        StructureSpecialty structureSpecialty = this.selectStructureSpecialtyBySpecialtyId(specialtyId);
        if (ObjectUtil.isNotEmpty(structureSpecialty)) {
            LoginUser loginUser = SecurityUtils.getLoginUser();
            structureSpecialty.setUpdateBy(loginUser.getUsername());
            structureSpecialty.setUpdateTime(DateUtils.getNowDate());
            structureSpecialty.setDelFlag(true);
            return structureSpecialtyMapper.update(structureSpecialty, new LambdaQueryWrapper<StructureSpecialty>()
                    .eq(StructureSpecialty::getSpecialtyId, structureSpecialty.getSpecialtyId()));
        }
        return 1;
    }
}