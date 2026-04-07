package com.lis.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "部门树节点")
public class DeptTreeVO implements Serializable {

    @ApiModelProperty(value = "节点ID")
    private Long id;

    @ApiModelProperty(value = "父节点ID")
    private Long parentId;

    @ApiModelProperty(value = "节点名称")
    private String label;

    @ApiModelProperty(value = "子节点")
    private List<DeptTreeVO> children;
}
