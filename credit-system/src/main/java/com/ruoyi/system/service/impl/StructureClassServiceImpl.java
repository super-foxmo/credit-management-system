package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.StructureClass;
import com.ruoyi.system.domain.StructureCollege;
import com.ruoyi.system.mapper.StructureClassMapper;
import com.ruoyi.system.service.IStructureClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 班级信息Service业务层处理
 *
 * @author foxmo
 * @date 2024-03-14
 */
@Service
public class StructureClassServiceImpl implements IStructureClassService {
    @Resource
    private StructureClassMapper structureClassMapper;

    /**
     * 查询班级信息
     *
     * @param classId 班级信息主键
     * @return 班级信息
     */
    @Override
    public StructureClass selectStructureClassByClassId(Long classId) {
        return structureClassMapper.selectOne(new LambdaQueryWrapper<StructureClass>()
                .eq(StructureClass::getClassId, classId)
                .eq(StructureClass::getDelFlag, false));
    }

    /**
     * 查询状态开启的班级信息
     *
     * @param classIdList 班级信息主键集合
     * @return 班级信息
     */
    @Override
    public List<StructureClass> selectEnableStructureClassListByClassIds(List<Long> classIdList) {
        return structureClassMapper.selectList(new LambdaQueryWrapper<StructureClass>()
                .in(StructureClass::getClassId, classIdList)
                .eq(StructureClass::getStatus, "0")
                .eq(StructureClass::getDelFlag, false));
    }

    /**
     * 根据班级名称和专业Id查询班级信息
     *
     * @param className   班级名称
     * @param specialtyId 专业编号
     * @return 班级信息
     */
    @Override
    public StructureClass selectStructureClassByClassNameAndSpecialtyId(String className, Long specialtyId) {
        return structureClassMapper.selectOne(new LambdaQueryWrapper<StructureClass>()
                .eq(StructureClass::getClassName, className)
                .eq(StructureClass::getSpecialtyId, specialtyId)
                .eq(StructureClass::getDelFlag, false));
    }

    /**
     * 查询状态开启的班级信息列表
     *
     * @param structureClass 班级信息
     * @return 班级信息
     */
    @Override
    public List<StructureClass> selectEnableStructureClassList(StructureClass structureClass) {
        LambdaQueryWrapper<StructureClass> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(structureClass.getSpecialtyId())) {
            queryWrapper.eq(StructureClass::getSpecialtyId, structureClass.getSpecialtyId());
        }
        queryWrapper.eq(StructureClass::getStatus, "0")
                .eq(StructureClass::getDelFlag, false);
        return structureClassMapper.selectList(queryWrapper);
    }

    /**
     * 查询班级信息列表
     *
     * @param structureClass 班级信息
     * @return 班级信息
     */
    @Override
    public List<StructureClass> selectStructureClassList(StructureClass structureClass) {
        return structureClassMapper.selectStructureClassList(structureClass);
    }

    /**
     * 新增班级信息
     *
     * @param structureClass 班级信息
     * @return 结果
     */
    @Override
    public int insertStructureClass(StructureClass structureClass) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        structureClass.setCreateBy(loginUser.getUsername());
        structureClass.setCreateTime(DateUtils.getNowDate());
        return structureClassMapper.insertStructureClass(structureClass);
    }

    /**
     * 修改班级信息
     *
     * @param structureClass 班级信息
     * @return 结果
     */
    @Override
    public int updateStructureClass(StructureClass structureClass) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        structureClass.setUpdateBy(loginUser.getUsername());
        structureClass.setUpdateTime(DateUtils.getNowDate());
        return structureClassMapper.updateStructureClass(structureClass);
    }

    /**
     * 批量删除班级信息
     *
     * @param classIds 需要删除的班级信息主键
     * @return 结果
     */
    @Override
    public int deleteStructureClassByClassIds(Long[] classIds) {
        List<StructureClass> structureClassList = structureClassMapper.selectList(new LambdaQueryWrapper<StructureClass>()
                .in(StructureClass::getClassId, classIds)
                .eq(StructureClass::getDelFlag, false));
        int count = 0;
        LoginUser loginUser = SecurityUtils.getLoginUser();
        for (StructureClass structureClass : structureClassList) {
            structureClass.setUpdateBy(loginUser.getUsername());
            structureClass.setUpdateTime(DateUtils.getNowDate());
            structureClass.setDelFlag(true);
            structureClassMapper.update(structureClass, new LambdaQueryWrapper<StructureClass>()
                    .eq(StructureClass::getClassId, structureClass.getClassId()));
            count++;
        }
        return count;
    }

    /**
     * 删除班级信息信息
     *
     * @param classId 班级信息主键
     * @return 结果
     */
    @Override
    public int deleteStructureClassByClassId(Long classId) {
        StructureClass structureClass = structureClassMapper.selectOne(new LambdaQueryWrapper<StructureClass>()
                .eq(StructureClass::getClassId, classId));
        LoginUser loginUser = SecurityUtils.getLoginUser();
        structureClass.setUpdateBy(loginUser.getUsername());
        structureClass.setUpdateTime(DateUtils.getNowDate());
        structureClass.setDelFlag(true);
        return structureClassMapper.update(structureClass, new LambdaQueryWrapper<StructureClass>()
                .eq(StructureClass::getClassId, classId));
    }
}
