package com.lis.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "菜单信息")
public class MenuVO implements Serializable {

    @ApiModelProperty(value = "菜单ID")
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "路由参数")
    private String query;

    @ApiModelProperty(value = "路由名称")
    private String routeName;

    @ApiModelProperty(value = "是否为外链")
    private Integer isFrame;

    @ApiModelProperty(value = "是否缓存")
    private Integer isCache;

    @ApiModelProperty(value = "菜单类型")
    private String menuType;

    @ApiModelProperty(value = "是否显示")
    private Integer visible;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "子菜单")
    private List<MenuVO> children;

    @ApiModelProperty(value = "备注")
    private String remark;
}
