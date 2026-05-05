package com.lis.ai.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiagnosisTypeEnum {

    BLOOD_ROUTINE("blood_routine", "血常规"),
    URINE_ROUTINE("urine_routine", "尿常规"),
    LIVER_FUNCTION("liver_function", "肝功能"),
    KIDNEY_FUNCTION("kidney_function", "肾功能"),
    BLOOD_LIPID("blood_lipid", "血脂"),
    BLOOD_SUGAR("blood_sugar", "血糖"),
    THYROID("thyroid", "甲状腺功能"),
    TUMOR_MARKER("tumor_marker", "肿瘤标志物");

    private final String code;
    private final String name;

    public static DiagnosisTypeEnum getByCode(String code) {
        for (DiagnosisTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
