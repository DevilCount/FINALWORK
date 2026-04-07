package com.lis.statistics.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "报告统计查询DTO")
public class ReportQueryDTO extends StatQueryDTO implements Serializable {

    @ApiModelProperty(value = "检验项目ID")
    private Long testItemId;

    @ApiModelProperty(value = "项目编码")
    private String itemCode;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "报告状态")
    private String reportStatus;
}
