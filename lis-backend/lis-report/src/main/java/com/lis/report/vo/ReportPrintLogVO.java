package com.lis.report.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "报告打印记录响应")
public class ReportPrintLogVO implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "报告ID")
    private Long reportId;

    @ApiModelProperty(value = "报告编号")
    private String reportNo;

    @ApiModelProperty(value = "打印人ID")
    private Long printUserId;

    @ApiModelProperty(value = "打印人姓名")
    private String printUserName;

    @ApiModelProperty(value = "打印时间")
    private LocalDateTime printTime;

    @ApiModelProperty(value = "打印份数")
    private Integer printCopies;

    @ApiModelProperty(value = "打印机名称")
    private String printerName;

    @ApiModelProperty(value = "打印IP")
    private String printIp;
}
