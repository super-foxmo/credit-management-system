package com.ruoyi.system.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 综测数据查询条件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScoreEvaluationQueryParams {
    // 专业id
    private Long specialtyId;

    // 学年
    private Long academicYear;

    // 学生主键集合
    private Long[] studentIds;
}
