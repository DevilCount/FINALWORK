package com.lis.hl7.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InterfaceStatusEnum {

    STOPPED("stopped", "已停止"),
    RUNNING("running", "运行中"),
    ERROR("error", "异常");

    private final String code;
    private final String desc;

    public static InterfaceStatusEnum getByCode(String code) {
        for (InterfaceStatusEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
