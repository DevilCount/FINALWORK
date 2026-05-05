package com.lis.ai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "诊断记录响应")
public class DiagnosisRecordVO implements Serializable {

    @ApiModelProperty(value = "诊断ID")
    private Long id;

    @ApiModelProperty(value = "诊断编号")
    private String diagnosisNo;

    @ApiModelProperty(value = "报告ID")
    private Long reportId;

    @ApiModelProperty(value = "报告编号")
    private String reportNo;

    @ApiModelProperty(value = "标本ID")
    private Long specimenId;

    @ApiModelProperty(value = "标本编号")
    private String specimenNo;

    @ApiModelProperty(value = "患者ID")
    private Long patientId;

    @ApiModelProperty(value = "患者姓名")
    private String patientName;

    @ApiModelProperty(value = "患者性别")
    private String patientGender;

    @ApiModelProperty(value = "患者年龄")
    private String patientAge;

    @ApiModelProperty(value = "使用模型ID")
    private Long modelId;

    @ApiModelProperty(value = "使用模型名称")
    private String modelName;

    @ApiModelProperty(value = "匹配规则ID")
    private Long ruleId;

    @ApiModelProperty(value = "匹配规则名称")
    private String ruleName;

    @ApiModelProperty(value = "诊断结果")
    private String diagnosisResult;

    @ApiModelProperty(value = "诊断编码（ICD-10）")
    private String diagnosisCode;

    @ApiModelProperty(value = "诊断名称")
    private String diagnosisName;

    @ApiModelProperty(value = "置信度")
    private BigDecimal confidence;

    @ApiModelProperty(value = "概率")
    private BigDecimal probability;

    @ApiModelProperty(value = "建议")
    private String suggestion;

    @ApiModelProperty(value = "风险等级：low-低 medium-中 high-高 critical-危急")
    private String riskLevel;

    @ApiModelProperty(value = "是否异常：0-否 1-是")
    private Integer isAbnormal;

    @ApiModelProperty(value = "是否危急：0-否 1-是")
    private Integer isPanic;

    @ApiModelProperty(value = "诊断时间")
    private LocalDateTime diagnosisTime;

    @ApiModelProperty(value = "诊断耗时（毫秒）")
    private Long diagnosisDuration;

    @ApiModelProperty(value = "审核状态：0-待审核 1-已确认 2-已拒绝")
    private Integer reviewStatus;

    @ApiModelProperty(value = "审核人ID")
    private Long reviewUserId;

    @ApiModelProperty(value = "审核人姓名")
    private String reviewUserName;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime reviewTime;

    @ApiModelProperty(value = "审核备注")
    private String reviewRemark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
