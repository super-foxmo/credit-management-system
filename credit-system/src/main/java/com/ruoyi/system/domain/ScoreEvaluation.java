package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 综测数据汇总对象 comprehensive_evaluation
 * 
 * @author foxmo
 * @date 2024-04-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScoreEvaluation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键编号 */
    private Long comprehensiveId;

    /** 学生编号 */
    private Long studentId;

    /** 学生学号 */
    @Excel(name = "学生学号")
    private Long studentNumber;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String studentName;

    /** 班级编号 */
    private Long classId;

    /** 班级名称 */
    @Excel(name = "班级名称")
    private String className;

    /** 专业编号 */
    private Long specialtyId;

    /** 专业名称 */
    @Excel(name = "专业名称")
    private String specialtyName;

    /** 综合分 */
    @Excel(name = "综合分")
    private Float synthesizeScore;

    /** 成绩 */
    @Excel(name = "成绩")
    private Float score;

    /** 绩点 */
    @Excel(name = "绩点")
    @TableField("GPA")
    private Float GPA;

    /** 思想分 */
    @Excel(name = "思想分")
    private Float thoughtScore;

    /** 文体分 */
    @Excel(name = "文体分")
    private Float stylisticScore;

    /** 学业分 */
    @Excel(name = "学业分")
    private Float academicScore;

    /** 学年 0-任意学年（公选） 1-第一学年 2-第二学年...最大为4 */
    @Excel(name = "学年 0-任意学年", readConverterExp = "公=选")
    private Long academicYear;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    private Integer delFlag;

    // 班级排名
    @TableField(exist = false)
    private Integer classSort;

    // 专业排名
    @TableField(exist = false)
    private Integer specialtySort;
}
