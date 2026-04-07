package com.lis.common.enums;

import lombok.Getter;

@Getter
public enum YesNoEnum {

    NO(0, "否"),
    YES(1, "是");

    private final Integer code;
    private final String desc;

    YesNoEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static YesNoEnum getByCode(Integer code) {
        if (code == null) {
            return NO;
        }
        for (YesNoEnum yesNo : values()) {
            if (yesNo.getCode().equals(code)) {
                return yesNo;
            }
        }
        return NO;
    }

    public static boolean isYes(Integer code) {
        return YES.getCode().equals(code);
    }

    public static boolean isNo(Integer code) {
        return NO.getCode().equals(code) || code == null;
    }
}
