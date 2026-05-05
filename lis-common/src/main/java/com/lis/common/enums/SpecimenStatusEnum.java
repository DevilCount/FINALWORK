package com.lis.common.enums;

import lombok.Getter;

@Getter
public enum SpecimenStatusEnum {

    COLLECTED(0, "已采集"),
    RECEIVED(1, "已接收"),
    PROCESSING(2, "处理中"),
    TESTING(3, "检测中"),
    COMPLETED(4, "已完成"),
    CANCELLED(5, "已取消"),
    REJECTED(6, "已拒收");

    private final Integer code;
    private final String desc;

    SpecimenStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SpecimenStatusEnum getByCode(Integer code) {
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
}
