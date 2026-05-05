package com.lis.hl7.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageDirectionEnum {

    INBOUND("inbound", "入站"),
    OUTBOUND("outbound", "出站");

    private final String code;
    private final String desc;

    public static MessageDirectionEnum getByCode(String code) {
        for (MessageDirectionEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
