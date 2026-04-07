package com.lis.common.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {

    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    private final Integer code;
    private final String desc;

    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static StatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (StatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    public static boolean isEnable(Integer code) {
        return ENABLE.getCode().equals(code);
    }

    public static boolean isDisable(Integer code) {
        return DISABLE.getCode().equals(code);
    }
}
