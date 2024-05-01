package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 专业信息对象 structure_specialty
 *
 * @author foxmo
 * @date 2024-03-14
 */
public class StructureSpecialty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 专业编号 */
    @TableId(type = IdType.AUTO)
    private Long specialtyId;

    /** 专业名称 */
    @Excel(name = "专业名称")
    private String specialtyName;

    /** 所属学院id */
    private Long collegeId;

    /** 所属学院名称 */
    @Excel(name = "所属学院名称")
    private String collegeName;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private Boolean delFlag;

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
    public void setCollegeId(Long collegeId)
    {
        this.collegeId = collegeId;
    }

    public Long getCollegeId()
    {
        return collegeId;
    }
    public void setCollegeName(String collegeName)
    {
        this.collegeName = collegeName;
    }

    public String getCollegeName()
    {
        return collegeName;
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
                .append("specialtyId", getSpecialtyId())
                .append("specialtyName", getSpecialtyName())
                .append("collegeId", getCollegeId())
                .append("collegeName", getCollegeName())
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