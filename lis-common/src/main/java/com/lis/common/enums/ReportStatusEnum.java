package com.lis.common.enums;

import lombok.Getter;

@Getter
public enum ReportStatusEnum {

    DRAFT(0, "草稿"),
    PENDING_REVIEW(1, "待审核"),
    REVIEWED(2, "已审核"),
    PUBLISHED(3, "已发布"),
    PRINTED(4, "已打印"),
    CANCELLED(5, "已取消");

    private final Integer code;
    private final String desc;

    ReportStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ReportStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ReportStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
