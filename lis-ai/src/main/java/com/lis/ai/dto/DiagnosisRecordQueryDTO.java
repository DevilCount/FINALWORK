package com.lis.ai.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "诊断记录查询参数")
public class DiagnosisRecordQueryDTO implements Serializable {

    @ApiModelProperty(value = "诊断编号")
    private String diagnosisNo;

    @ApiModelProperty(value = "报告编号")
    private String reportNo;

    @ApiModelProperty(value = "标本编号")
    private String specimenNo;

    @ApiModelProperty(value = "患者姓名")
    private String patientName;

    @ApiModelProperty(value = "诊断类型")
    private String diagnosisType;

    @ApiModelProperty(value = "风险等级：low-低 medium-中 high-高 critical-危急")
    private String riskLevel;

    @ApiModelProperty(value = "是否异常：0-否 1-是")
    private Integer isAbnormal;

    @ApiModelProperty(value = "审核状态：0-待审核 1-已确认 2-已拒绝")
    private Integer reviewStatus;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize = 10;
}
