package com.ruoyi.system.domain.entity;

import lombok.*;

@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRule {
    // 获奖等级编号
    private Long gradeId;
    // 获奖等级名称
    private String gradeName;
    // 思想分
    private Float thoughtScore;
    // 文体分
    private Float stylisticScore;
    // 学业分
    private Float academicScore;
}
