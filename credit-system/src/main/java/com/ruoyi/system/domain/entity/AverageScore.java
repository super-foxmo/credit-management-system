package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 综合分、学习成绩、绩点、思想分、文体分、学业分平均分
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AverageScore {
    /** 班级编号 */
    private Long classId;

    /** 班级名称 */
    private String className;

    /** 专业编号 */
    private Long specialtyId;

    /** 专业名称 */
    private String specialtyName;

    /** 综合分平均分 */
    private Float averageSynthesizeScore;

    /** 学习成绩平均分 */
    private Float averageScore;

    /** 绩点平均分 */
    @TableField("average_GPA")
    private Float averageGPA;

    /** 思想分平均分 */
    private Float averageThoughtScore;

    /** 文体分平均分 */
    private Float averageStylisticScore;

    /** 学业分平均分 */
    private Float averageAcademicScore;

    /** 数据数组 */
    private Float[] scoreArray;
}
