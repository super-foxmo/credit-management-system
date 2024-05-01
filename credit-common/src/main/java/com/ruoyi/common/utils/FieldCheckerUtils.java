package com.ruoyi.common.utils;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;

public class FieldCheckerUtils {
    public static boolean areAllFieldsEmpty(Object obj) {
        Field[] fields = ReflectUtil.getFields(obj.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value != null && value instanceof String && !((String) value).isEmpty()) {
                    return false;
                } else if (value != null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
