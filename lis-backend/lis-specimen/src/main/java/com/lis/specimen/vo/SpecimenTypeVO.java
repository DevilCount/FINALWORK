package com.lis.specimen.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "标本类型响应")
public class SpecimenTypeVO implements Serializable {

    @ApiModelProperty(value = "标本类型ID")
    private Long id;

    @ApiModelProperty(value = "类型编码")
    private String typeCode;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "类型描述")
    private String typeDesc;

    @ApiModelProperty(value = "颜色编码")
    private String colorCode;

    @ApiModelProperty(value = "容器类型")
    private String containerType;

    @ApiModelProperty(value = "存储条件")
    private String storageCondition;

    @ApiModelProperty(value = "有效期（小时）")
    private Integer validityPeriod;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
