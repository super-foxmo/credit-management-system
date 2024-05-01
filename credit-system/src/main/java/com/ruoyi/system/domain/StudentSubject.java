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
 * 成绩管理表对象 student_subject
 * 
 * @author foxmo
 * @date 2024-03-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentSubject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键编号 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 学生编号 */
    private Long studentId;

    /** 学生学号 */
    @Excel(name = "学生学号")
    private Long studentNumber;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String studentName;

    /** 学科编号 */
    private Long subjectId;

    /** 学科名称 */
    @Excel(name = "学科名称")
    private String subjectName;

    /** 成绩 */
    @Excel(name = "成绩")
    private Float score;

    /** 总学分 */
    @Excel(name = "总学分")
    private Float allCredit;

    /** 学科类型 */
    @Excel(name = "学科类型")
    private String subjectType;

    /** 学年 */
    @Excel(name = "学年")
    private Long academicYear;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标志 */
    private Boolean delFlag;
}
