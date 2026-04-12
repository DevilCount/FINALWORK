package com.lis.report.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "报告审核请求参数")
public class ReportAuditDTO implements Serializable {

    @NotNull(message = "报告ID不能为空")
    @JsonAlias("id")
    @ApiModelProperty(value = "报告ID", required = true)
    private Long reportId;

    @ApiModelProperty(value = "审核通过")
    private Boolean approved;

    @ApiModelProperty(value = "审核意见")
    private String auditOpinion;

    @ApiModelProperty(value = "审核级别(1初审 2终审)")
    private Integer auditLevel;

    @ApiModelProperty(value = "审核人ID")
    private Long auditUserId;

    @ApiModelProperty(value = "审核人姓名")
    private String auditUserName;
}
