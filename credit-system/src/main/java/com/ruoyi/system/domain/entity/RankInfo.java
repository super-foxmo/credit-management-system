package com.ruoyi.system.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RankInfo {
    // 分数
    private Float Score;

    // 班级排名
    private Integer classRank;

    // 专业排名
    private Integer specialtyRank;
}
