package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 班级信息对象 structure_class
 * 
 * @author foxmo
 * @date 2024-03-14
 */
public class StructureClass extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 班级编号 */
    @TableId(type = IdType.AUTO)
    private Long classId;

    /** 班级名称 */
    @Excel(name = "班级名称")
    private String className;

    /** 所属专业id */
    private Long specialtyId;

    /** 所属专业名称 */
    @Excel(name = "所属专业名称")
    private String specialtyName;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private Boolean delFlag;

    public void setClassId(Long classId) 
    {
        this.classId = classId;
    }

    public Long getClassId() 
    {
        return classId;
    }
    public void setClassName(String className) 
    {
        this.className = className;
    }

    public String getClassName() 
    {
        return className;
    }
    public void setSpecialtyId(Long specialtyId) 
    {
        this.specialtyId = specialtyId;
    }

    public Long getSpecialtyId() 
    {
        return specialtyId;
    }
    public void setSpecialtyName(String specialtyName) 
    {
        this.specialtyName = specialtyName;
    }

    public String getSpecialtyName() 
    {
        return specialtyName;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("classId", getClassId())
            .append("className", getClassName())
            .append("specialtyId", getSpecialtyId())
            .append("specialtyName", getSpecialtyName())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
