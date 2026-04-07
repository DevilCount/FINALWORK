package com.lis.hl7.mllp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "mllp")
public class MllpProperties {

    private Boolean enabled = true;

    private String host = "0.0.0.0";

    private Integer port = 2575;

    private Integer backlog = 50;

    private Integer connectionTimeout = 30000;

    private Integer readTimeout = 60000;

    private String charset = "UTF-8";

    private Boolean autoAck = true;

    private Integer threadPoolSize = 10;

    private Map<String, InterfaceConfig> interfaces = new HashMap<>();

    @Data
    public static class InterfaceConfig {
        private String code;
        private String name;
        private String host;
        private Integer port;
        private Boolean enabled;
        private Boolean autoAck;
        private String charset;
        private Integer connectionTimeout;
        private Integer readTimeout;
    }
}
