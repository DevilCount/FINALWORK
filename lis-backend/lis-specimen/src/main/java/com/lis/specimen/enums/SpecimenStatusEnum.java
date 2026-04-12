package com.lis.specimen.enums;

import lombok.Getter;

@Getter
public enum SpecimenStatusEnum {

    PENDING("pending", "待接收"),
    RECEIVED("received", "已接收"),
    STORED("stored", "已入库"),
    TESTING("testing", "检验中"),
    COMPLETED("completed", "已完成"),
    REJECTED("rejected", "已拒收"),
    CANCELLED("cancelled", "已取消");

    private final String code;
    private final String desc;

    SpecimenStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SpecimenStatusEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (SpecimenStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    public static String getDescByCode(String code) {
        SpecimenStatusEnum status = getByCode(code);
        return status != null ? status.getDesc() : "";
    }
}
