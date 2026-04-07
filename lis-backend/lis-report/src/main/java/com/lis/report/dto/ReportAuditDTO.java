package com.lis.report.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "报告审核请求参数")
public class ReportAuditDTO implements Serializable {

    @NotNull(message = "报告ID不能为空")
    @ApiModelProperty(value = "报告ID", required = true)
    private Long reportId;

    @ApiModelProperty(value = "审核通过")
    private Boolean approved;

    @ApiModelProperty(value = "审核意见")
    private String auditOpinion;
}
