package com.lis.equipment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CalibrationTypeEnum {

    ROUTINE("routine", "常规校准"),
    CALIBRATION("calibration", "校准"),
    VERIFICATION("verification", "验证");

    private final String code;
    private final String desc;

    public static CalibrationTypeEnum getByCode(String code) {
        for (CalibrationTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
