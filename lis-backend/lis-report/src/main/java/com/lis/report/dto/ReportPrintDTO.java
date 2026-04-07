package com.lis.report.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "报告打印请求参数")
public class ReportPrintDTO implements Serializable {

    @NotNull(message = "报告ID不能为空")
    @ApiModelProperty(value = "报告ID", required = true)
    private Long reportId;

    @ApiModelProperty(value = "打印份数")
    private Integer printCopies = 1;

    @ApiModelProperty(value = "打印机名称")
    private String printerName;

    @ApiModelProperty(value = "打印IP")
    private String printIp;
}
