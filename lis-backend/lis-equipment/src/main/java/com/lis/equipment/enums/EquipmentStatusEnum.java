package com.lis.equipment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EquipmentStatusEnum {

    NORMAL("normal", "正常"),
    MAINTENANCE("maintenance", "维护中"),
    REPAIR("repair", "维修中"),
    SCRAP("scrap", "报废"),
    CALIBRATION("calibration", "校准中");

    private final String code;
    private final String desc;

    public static EquipmentStatusEnum getByCode(String code) {
        for (EquipmentStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
