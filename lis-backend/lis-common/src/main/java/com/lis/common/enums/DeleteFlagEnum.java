package com.lis.common.enums;

import lombok.Getter;

@Getter
public enum DeleteFlagEnum {

    NORMAL(0, "正常"),
    DELETED(1, "已删除");

    private final Integer code;
    private final String desc;

    DeleteFlagEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DeleteFlagEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (DeleteFlagEnum flag : values()) {
            if (flag.getCode().equals(code)) {
                return flag;
            }
        }
        return null;
    }

    public static boolean isDeleted(Integer code) {
        return DELETED.getCode().equals(code);
    }

    public static boolean isNormal(Integer code) {
        return NORMAL.getCode().equals(code);
    }
}
