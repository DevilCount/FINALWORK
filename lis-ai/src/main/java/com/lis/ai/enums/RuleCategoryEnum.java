package com.lis.ai.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RuleCategoryEnum {

    ANEMIA("anemia", "贫血"),
    INFECTION("infection", "感染"),
    LIVER("liver", "肝脏"),
    KIDNEY("kidney", "肾脏"),
    METABOLISM("metabolism", "代谢"),
    CARDIOVASCULAR("cardiovascular", "心血管"),
    ENDOCRINE("endocrine", "内分泌"),
    TUMOR("tumor", "肿瘤"),
    IMMUNE("immune", "免疫"),
    OTHER("other", "其他");

    private final String code;
    private final String name;

    public static RuleCategoryEnum getByCode(String code) {
        for (RuleCategoryEnum category : values()) {
            if (category.getCode().equals(code)) {
                return category;
            }
        }
        return OTHER;
    }
}
