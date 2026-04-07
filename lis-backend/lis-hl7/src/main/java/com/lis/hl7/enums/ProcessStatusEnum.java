package com.lis.hl7.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessStatusEnum {

    PENDING("pending", "待处理"),
    PROCESSING("processing", "处理中"),
    SUCCESS("success", "成功"),
    FAILED("failed", "失败");

    private final String code;
    private final String desc;

    public static ProcessStatusEnum getByCode(String code) {
        for (ProcessStatusEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
