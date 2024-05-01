package com.ruoyi.common.enums;

public enum SubjectType {
    MAJOR_COMPULSORY_COURSE("0", "专业必修"),

    SELECTIVE_COURSE("1", "专业选修"),

    PUBLIC_ELECTIVE_COURSE("2", "公共选修");

    private final String code;
    private final String info;

    SubjectType(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
