package com.lis.hl7.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AckStatusEnum {

    AA("AA", "接受"),
    AE("AE", "应用错误"),
    AR("AR", "拒绝");

    private final String code;
    private final String desc;

    public static AckStatusEnum getByCode(String code) {
        for (AckStatusEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
