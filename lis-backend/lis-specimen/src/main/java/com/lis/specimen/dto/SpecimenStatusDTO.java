package com.lis.specimen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "标本状态流转请求参数")
public class SpecimenStatusDTO implements Serializable {

    @NotNull(message = "标本ID不能为空")
    @ApiModelProperty(value = "标本ID", required = true)
    private Long specimenId;

    @NotBlank(message = "目标状态不能为空")
    @ApiModelProperty(value = "目标状态", required = true)
    private String targetStatus;

    @ApiModelProperty(value = "操作人ID")
    private Long operatorId;

    @ApiModelProperty(value = "操作人姓名")
    private String operatorName;

    @ApiModelProperty(value = "备注")
    private String remark;
}
