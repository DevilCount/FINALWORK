package com.lis.common.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {

    NORMAL(0, "正常"),
    LOCKED(1, "锁定"),
    DISABLED(2, "禁用"),
    EXPIRED(3, "过期");

    private final Integer code;
    private final String desc;

    UserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (UserStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    public static boolean isNormal(Integer code) {
        return NORMAL.getCode().equals(code);
    }

    public static boolean isLocked(Integer code) {
        return LOCKED.getCode().equals(code);
    }

    public static boolean isDisabled(Integer code) {
        return DISABLED.getCode().equals(code);
    }
}
