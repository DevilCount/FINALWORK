package com.lis.report.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "报告发布请求参数")
public class ReportPublishDTO implements Serializable {

    @NotNull(message = "报告ID不能为空")
    @ApiModelProperty(value = "报告ID", required = true)
    private Long reportId;

    @ApiModelProperty(value = "报告结论")
    private String reportConclusion;
}
