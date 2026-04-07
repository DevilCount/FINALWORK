package com.lis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "菜单查询参数")
public class MenuQueryDTO implements Serializable {

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;
}
