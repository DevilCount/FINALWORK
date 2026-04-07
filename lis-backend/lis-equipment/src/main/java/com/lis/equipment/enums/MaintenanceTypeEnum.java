package com.lis.equipment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MaintenanceTypeEnum {

    ROUTINE("routine", "日常维护"),
    PERIODIC("periodic", "周期维护"),
    PREVENTIVE("preventive", "预防性维护"),
    REPAIR("repair", "维修");

    private final String code;
    private final String desc;

    public static MaintenanceTypeEnum getByCode(String code) {
        for (MaintenanceTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
