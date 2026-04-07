package com.lis.ai.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewStatusEnum {

    PENDING(0, "待审核"),
    CONFIRMED(1, "已确认"),
    REJECTED(2, "已拒绝");

    private final int code;
    private final String name;

    public static ReviewStatusEnum getByCode(int code) {
        for (ReviewStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return PENDING;
    }
}
