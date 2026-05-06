package com.lis.specimen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "标本追溯查询请求参数")
public class SpecimenTraceQueryDTO implements Serializable {

    @ApiModelProperty(value = "标本ID")
    private Long specimenId;

    @ApiModelProperty(value = "标本编号")
    private String specimenNo;

    @ApiModelProperty(value = "标本条码")
    private String barcode;

    @ApiModelProperty(value = "操作类型")
    private String actionType;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "操作开始时间")
    private LocalDateTime operationTimeStart;

    @ApiModelProperty(value = "操作结束时间")
    private LocalDateTime operationTimeEnd;

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页大小", example = "10")
    private Integer pageSize = 10;
}
