package com.lis.ai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "诊断结果响应")
public class DiagnosisResultVO implements Serializable {

    @ApiModelProperty(value = "诊断ID")
    private Long id;

    @ApiModelProperty(value = "诊断编号")
    private String diagnosisNo;

    @ApiModelProperty(value = "诊断类型")
    private String diagnosisType;

    @ApiModelProperty(value = "诊断编码（ICD-10）")
    private String diagnosisCode;

    @ApiModelProperty(value = "诊断名称")
    private String diagnosisName;

    @ApiModelProperty(value = "诊断结果描述")
    private String diagnosisResult;

    @ApiModelProperty(value = "置信度")
    private BigDecimal confidence;

    @ApiModelProperty(value = "概率")
    private BigDecimal probability;

    @ApiModelProperty(value = "风险等级：low-低 medium-中 high-高 critical-危急")
    private String riskLevel;

    @ApiModelProperty(value = "是否异常：0-否 1-是")
    private Integer isAbnormal;

    @ApiModelProperty(value = "是否危急：0-否 1-是")
    private Integer isPanic;

    @ApiModelProperty(value = "建议")
    private String suggestion;

    @ApiModelProperty(value = "诊断时间")
    private LocalDateTime diagnosisTime;

    @ApiModelProperty(value = "诊断耗时（毫秒）")
    private Long diagnosisDuration;

    @ApiModelProperty(value = "异常项目列表")
    private List<AbnormalItemVO> abnormalItems;

    @ApiModelProperty(value = "匹配的规则列表")
    private List<MatchedRuleVO> matchedRules;

    @Data
    @ApiModel(description = "异常项目")
    public static class AbnormalItemVO implements Serializable {

        @ApiModelProperty(value = "项目编码")
        private String itemCode;

        @ApiModelProperty(value = "项目名称")
        private String itemName;

        @ApiModelProperty(value = "结果值")
        private String resultValue;

        @ApiModelProperty(value = "单位")
        private String unit;

        @ApiModelProperty(value = "参考值下限")
        private BigDecimal referenceLow;

        @ApiModelProperty(value = "参考值上限")
        private BigDecimal referenceHigh;

        @ApiModelProperty(value = "异常程度")
        private BigDecimal abnormalDegree;

        @ApiModelProperty(value = "异常方向：high-偏高 low-偏低")
        private String abnormalDirection;

        @ApiModelProperty(value = "特征重要性")
        private BigDecimal featureImportance;
    }

    @Data
    @ApiModel(description = "匹配的规则")
    public static class MatchedRuleVO implements Serializable {

        @ApiModelProperty(value = "规则ID")
        private Long ruleId;

        @ApiModelProperty(value = "规则编码")
        private String ruleCode;

        @ApiModelProperty(value = "规则名称")
        private String ruleName;

        @ApiModelProperty(value = "规则分类")
        private String category;

        @ApiModelProperty(value = "匹配置信度")
        private BigDecimal matchConfidence;
    }
}
