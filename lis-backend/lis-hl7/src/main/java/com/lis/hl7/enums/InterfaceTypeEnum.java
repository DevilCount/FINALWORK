package com.lis.hl7.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InterfaceTypeEnum {

    HL7("hl7", "HL7"),
    ASTM("astm", "ASTM"),
    XML("xml", "XML"),
    JSON("json", "JSON"),
    WEBSOCKET("websocket", "WebSocket");

    private final String code;
    private final String desc;

    public static InterfaceTypeEnum getByCode(String code) {
        for (InterfaceTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
