package com.ruoyi.common.enums;

public enum AcademicYear {
    ONE(1L, "第一学年"),

    TWO(2L, "第二学年"),

    THREE(3L, "第三学年"),

    FOUR(4L, "第四学年");

    private final Long code;
    private final String info;

    AcademicYear(Long code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public Long getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
