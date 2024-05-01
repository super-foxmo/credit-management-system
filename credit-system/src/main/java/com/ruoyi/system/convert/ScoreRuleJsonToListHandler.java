package com.ruoyi.system.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ruoyi.common.convert.ListTypeHandler;
import com.ruoyi.system.domain.entity.ScoreRule;

import java.util.List;

public class ScoreRuleJsonToListHandler extends ListTypeHandler<ScoreRule> {
    @Override
    protected TypeReference<List<ScoreRule>> specificType() {
        return new TypeReference<List<ScoreRule>>() {
        };
    }
}

