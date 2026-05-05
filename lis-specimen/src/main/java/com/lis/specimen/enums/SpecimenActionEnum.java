package com.lis.specimen.enums;

import lombok.Getter;

@Getter
public enum SpecimenActionEnum {

    REGISTER("register", "登记"),
    RECEIVE("receive", "签收"),
    REJECT("reject", "拒收"),
    STORAGE("storage", "入库"),
    TESTING("testing", "开始检验"),
    COMPLETE("complete", "完成检验"),
    CANCEL("cancel", "取消");

    private final String code;
    private final String desc;

    SpecimenActionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SpecimenActionEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (SpecimenActionEnum action : values()) {
            if (action.getCode().equals(code)) {
                return action;
            }
        }
        return null;
    }
}
