package com.lis.ai.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RiskLevelEnum {

    LOW("low", "低风险", 1),
    MEDIUM("medium", "中风险", 2),
    HIGH("high", "高风险", 3),
    CRITICAL("critical", "危急", 4);

    private final String code;
    private final String name;
    private final int level;

    public static RiskLevelEnum getByCode(String code) {
        for (RiskLevelEnum level : values()) {
            if (level.getCode().equals(code)) {
                return level;
            }
        }
        return LOW;
    }

    public static RiskLevelEnum getByLevel(int level) {
        for (RiskLevelEnum riskLevel : values()) {
            if (riskLevel.getLevel() == level) {
                return riskLevel;
            }
        }
        return LOW;
    }
}
