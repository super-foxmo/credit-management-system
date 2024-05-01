package com.ruoyi.system.domain.vo;

import com.ruoyi.system.domain.StructureClass;
import com.ruoyi.system.domain.entity.AverageScore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * 综测汇总返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScoreEvaluationVO {
    // 班级列表
    private List<StructureClass> classList;
    // 平均分
    private Map<Long, AverageScore> averageScoreMap;
}
