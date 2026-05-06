package com.lis.specimen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "检验项目查询请求参数")
public class TestItemQueryDTO implements Serializable {

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "项目编码")
    private String itemCode;

    @ApiModelProperty(value = "分类")
    private String category;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页大小", example = "10")
    private Integer pageSize = 10;
}
