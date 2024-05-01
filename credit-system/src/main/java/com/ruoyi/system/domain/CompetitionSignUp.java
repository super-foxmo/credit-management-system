package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 竞赛报名对象 competition_sign_up
 * 
 * @author foxmo
 * @date 2024-03-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompetitionSignUp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报名编号 */
    @TableId(type = IdType.AUTO)
    private Long signUpId;

    /** 学生id */
    private Long studentId;

    /** 学生学号 */
    @Excel(name = "学生学号")
    private Long studentNumber;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String studentName;

    /** 竞赛编号 */
    private Long activityId;

    /** 竞赛名称 */
    @Excel(name = "竞赛名称")
    private String activityName;

    // 获奖名称
    @TableField(exist = false)
    private String gradeName;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private Boolean delFlag;
}
