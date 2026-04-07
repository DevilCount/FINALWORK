package com.lis.equipment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CalibrationResultEnum {

    QUALIFIED("qualified", "合格"),
    UNQUALIFIED("unqualified", "不合格");

    private final String code;
    private final String desc;

    public static CalibrationResultEnum getByCode(String code) {
        for (CalibrationResultEnum result : values()) {
            if (result.getCode().equals(code)) {
                return result;
            }
        }
        return null;
    }
}
