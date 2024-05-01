package com.ruoyi.system.convert;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTypeHandler<T> extends AbstractJsonTypeHandler<T> {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final Class<T> type;

    public JsonTypeHandler(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    protected T parse(String json) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String toJson(T obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}