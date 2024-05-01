package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompetitionStudentVO extends BaseEntity {
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
    private String gradeName;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private Boolean delFlag;
}
