package com.lis.ai.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "诊断审核请求参数")
public class DiagnosisReviewDTO implements Serializable {

    @NotNull(message = "诊断ID不能为空")
    @ApiModelProperty(value = "诊断ID", required = true)
    private Long diagnosisId;

    @NotNull(message = "审核状态不能为空")
    @ApiModelProperty(value = "审核状态：1-已确认 2-已拒绝", required = true)
    private Integer reviewStatus;

    @ApiModelProperty(value = "审核备注")
    private String reviewRemark;
}
