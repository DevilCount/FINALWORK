package com.lis.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ai.diagnosis")
public class DiagnosisConfig {

    private Double confidenceThreshold = 0.8;
    private Integer maxResults = 5;
    private Boolean cacheEnabled = true;
    private Integer cacheTtl = 3600;
}
