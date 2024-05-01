package com.ruoyi.system.service.impl;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.StructureCollege;
import com.ruoyi.system.mapper.StructureCollegeMapper;
import com.ruoyi.system.service.IStructureCollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 学院信息Service业务层处理
 *
 * @author foxmo
 * @date 2024-03-09
 */
@Service
public class StructureCollegeServiceImpl implements IStructureCollegeService {
    @Resource
    private StructureCollegeMapper structureCollegeMapper;

    /**
     * 查询学院信息
     *
     * @param collegeId 学院信息主键
     * @return 学院信息
     */
    @Override
    public StructureCollege selectStructureCollegeByCollegeId(Long collegeId) {
        return structureCollegeMapper.selectOne(new LambdaQueryWrapper<StructureCollege>()
                .eq(StructureCollege::getCollegeId, collegeId)
                .eq(StructureCollege::getDelFlag, false));
    }

    /**
     * 根据学院名称查询学院信息
     *
     * @param collegeName 学院名称
     * @return 学院信息
     */
    @Override
    public StructureCollege selectStructureCollegeByCollegeName(String collegeName) {
        return structureCollegeMapper.selectOne(new LambdaQueryWrapper<StructureCollege>()
                .eq(StructureCollege::getCollegeName, collegeName)
                .eq(StructureCollege::getDelFlag, false));
    }

    /**
     * 查询所有学院信息列表
     *
     * @param structureCollege 学院信息
     * @return 学院信息
     */
    @Override
    public List<StructureCollege> selectStructureCollegeList(StructureCollege structureCollege) {
        return structureCollegeMapper.selectStructureCollegeList(structureCollege);
    }

    /**
     * 查询状态开启的学院信息列表
     *
     * @param structureCollege 学院信息
     * @return 学院信息
     */
    @Override
    public List<StructureCollege> selectEnableStructureCollegeList(StructureCollege structureCollege) {
        return structureCollegeMapper.selectList(new LambdaQueryWrapper<StructureCollege>()
                .eq(StructureCollege::getStatus, "0")
                .eq(StructureCollege::getDelFlag, false));
    }

    /**
     * 新增学院信息
     *
     * @param structureCollege 学院信息
     * @return 结果
     */
    @Override
    public int insertStructureCollege(StructureCollege structureCollege) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        structureCollege.setCreateBy(loginUser.getUsername());
        structureCollege.setCreateTime(DateUtils.getNowDate());
        return structureCollegeMapper.insertStructureCollege(structureCollege);
    }

    /**
     * 修改学院信息
     *
     * @param structureCollege 学院信息
     * @return 结果
     */
    @Override
    public int updateStructureCollege(StructureCollege structureCollege) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        structureCollege.setUpdateBy(loginUser.getUsername());
        structureCollege.setUpdateTime(DateUtils.getNowDate());
        return structureCollegeMapper.updateStructureCollege(structureCollege);
    }

    /**
     * 批量删除学院信息
     *
     * @param collegeIds 需要删除的学院信息主键
     * @return 结果
     */
    @Override
    public int deleteStructureCollegeByCollegeIds(Long[] collegeIds) {
        List<StructureCollege> structureCollegeList = structureCollegeMapper.selectList(new LambdaQueryWrapper<StructureCollege>()
                .in(StructureCollege::getCollegeId, collegeIds)
                .eq(StructureCollege::getDelFlag, false));
        int count = 0;
        LoginUser loginUser = SecurityUtils.getLoginUser();
        for (StructureCollege structureCollege : structureCollegeList) {
            structureCollege.setUpdateBy(loginUser.getUsername());
            structureCollege.setUpdateTime(DateUtils.getNowDate());
            structureCollege.setDelFlag(true);
            structureCollegeMapper.update(structureCollege, new LambdaQueryWrapper<StructureCollege>()
                    .eq(StructureCollege::getCollegeId, structureCollege.getCollegeId()));
            count++;
        }
        return count;
    }

    /**
     * 删除学院信息信息
     *
     * @param collegeId 学院信息主键
     * @return 结果
     */
    @Override
    public int deleteStructureCollegeByCollegeId(Long collegeId) {
        StructureCollege structureCollege = this.selectStructureCollegeByCollegeId(collegeId);
        LoginUser loginUser = SecurityUtils.getLoginUser();
        structureCollege.setUpdateBy(loginUser.getUsername());
        structureCollege.setUpdateTime(DateUtils.getNowDate());
        structureCollege.setDelFlag(true);
        return structureCollegeMapper.update(structureCollege, new LambdaQueryWrapper<StructureCollege>()
                .eq(StructureCollege::getCollegeId, structureCollege.getCollegeId()));
    }
}
