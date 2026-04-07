package com.lis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "用户更新参数")
public class UserUpdateDTO implements Serializable {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Long id;

    @Size(min = 2, max = 50, message = "用户名长度必须在2-50个字符之间")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @Size(min = 1, max = 50, message = "昵称长度必须在1-50个字符之间")
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "用户类型")
    private String userType;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "角色ID列表")
    private List<Long> roleIds;

    @ApiModelProperty(value = "备注")
    private String remark;
}
