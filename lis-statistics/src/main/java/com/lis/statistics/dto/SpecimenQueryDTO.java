package com.lis.statistics.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "标本统计查询DTO")
public class SpecimenQueryDTO extends StatQueryDTO implements Serializable {

    @ApiModelProperty(value = "标本类型ID")
    private Long specimenTypeId;

    @ApiModelProperty(value = "标本类型名称")
    private String specimenTypeName;
}
