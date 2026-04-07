package com.lis.ai.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AbnormalDirectionEnum {

    NORMAL("normal", "正常"),
    HIGH("high", "偏高"),
    LOW("low", "偏低");

    private final String code;
    private final String name;

    public static AbnormalDirectionEnum getByCode(String code) {
        for (AbnormalDirectionEnum direction : values()) {
            if (direction.getCode().equals(code)) {
                return direction;
            }
        }
        return NORMAL;
    }

    public static AbnormalDirectionEnum calculate(Double value, Double low, Double high) {
        if (value == null) {
            return NORMAL;
        }
        if (low != null && value < low) {
            return LOW;
        }
        if (high != null && value > high) {
            return HIGH;
        }
        return NORMAL;
    }
}
