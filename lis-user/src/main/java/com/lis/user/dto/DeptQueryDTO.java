package com.lis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "部门查询参数")
public class DeptQueryDTO implements Serializable {

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "部门编码")
    private String deptCode;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "父部门ID")
    private Long parentId;
}
