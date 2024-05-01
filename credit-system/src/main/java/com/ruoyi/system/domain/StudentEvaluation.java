package com.ruoyi.system.domain;

import com.ruoyi.system.domain.entity.RankInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentEvaluation {
    // 综合分
    private RankInfo synthesizeEvaluation;

    // 成绩
    private RankInfo scoreEvaluation;

    // 绩点
    private RankInfo GPA;

    // 思想分
    private RankInfo thoughtEvaluation;

    // 文体分
    private RankInfo stylisticEvaluation;

    // 学业分
    private RankInfo academicEvaluation;
}
