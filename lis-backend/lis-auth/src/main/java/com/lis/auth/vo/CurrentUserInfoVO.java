package com.lis.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "当前用户信息响应")
public class CurrentUserInfoVO implements Serializable {

    @ApiModelProperty(value = "用户信息")
    private UserInfoVO user;

    @ApiModelProperty(value = "角色列表")
    private List<String> roles;

    @ApiModelProperty(value = "权限列表")
    private List<String> permissions;
}
