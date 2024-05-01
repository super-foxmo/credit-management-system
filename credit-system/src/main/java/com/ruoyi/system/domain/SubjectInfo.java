package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学科信息对象 subject_info
 * 
 * @author foxmo
 * @date 2024-03-18
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubjectInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @TableId(type = IdType.AUTO)
    private Long subjectId;

    /** 学科 */
    @Excel(name = "学科")
    private String subjectName;

    /** 任课老师编号 */
    private Long teacherId;

    /** 任课老师 */
    @Excel(name = "任课老师")
    private String teacherName;

    /** 总学分 */
    @Excel(name = "总学分")
    private Float allCredit;

    /** 学院id */
    private Long collegeId;

    /** 学院 */
    @Excel(name = "学院")
    private String collegeName;

    /** 专业id */
    private Long specialtyId;

    /** 专业 */
    @Excel(name = "专业")
    private String specialtyName;

    /** 学科类型 */
    @Excel(name = "学科类型")
    private String subjectType;

    /** 学年 */
    @Excel(name = "学年")
    private Long academicYear;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    private Boolean delFlag;
}
