package com.lis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@ApiModel(description = "菜单创建参数")
public class MenuCreateDTO implements Serializable {

    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    @ApiModelProperty(value = "菜单名称", required = true)
    private String menuName;

    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @Size(max = 200, message = "路由地址长度不能超过200个字符")
    @ApiModelProperty(value = "路由地址")
    private String path;

    @Size(max = 255, message = "组件路径长度不能超过255个字符")
    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "路由参数")
    private String query;

    @Size(max = 50, message = "路由名称长度不能超过50个字符")
    @ApiModelProperty(value = "路由名称")
    private String routeName;

    @ApiModelProperty(value = "是否为外链")
    private Integer isFrame;

    @ApiModelProperty(value = "是否缓存")
    private Integer isCache;

    @NotBlank(message = "菜单类型不能为空")
    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）", required = true)
    private String menuType;

    @ApiModelProperty(value = "是否显示")
    private Integer visible;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    @ApiModelProperty(value = "权限标识")
    private String perms;

    @Size(max = 100, message = "菜单图标长度不能超过100个字符")
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "备注")
    private String remark;
}
