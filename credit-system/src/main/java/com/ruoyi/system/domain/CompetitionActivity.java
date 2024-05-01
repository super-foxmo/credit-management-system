package com.ruoyi.system.domain;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.system.convert.ScoreRuleJsonToListHandler;
import com.ruoyi.system.domain.entity.ScoreRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.security.DenyAll;

/**
 * 竞赛信息对象 competition_activity
 * 
 * @author foxmo
 * @date 2024-03-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "competition_activity",autoResultMap = true)
public class CompetitionActivity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 竞赛编号 */
    @TableId(type = IdType.AUTO)
    private Long activityId;

    /** 竞赛名称 */
    @Excel(name = "竞赛名称")
    private String activityName;

    /** 报名开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报名开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signUpStartTime;

    /** 报名结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报名结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signUpEndTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 加分规则 */
//    @TableField(typeHandler = FastjsonTypeHandler.class)
    @TableField(typeHandler = ScoreRuleJsonToListHandler.class)
    private List<ScoreRule> scoreRule;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private Boolean delFlag;
}
