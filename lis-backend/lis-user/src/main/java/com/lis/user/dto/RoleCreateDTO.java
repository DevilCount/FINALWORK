package com.lis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "角色创建参数")
public class RoleCreateDTO implements Serializable {

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称长度不能超过50个字符")
    @ApiModelProperty(value = "角色名称", required = true)
    private String roleName;

    @NotBlank(message = "角色编码不能为空")
    @Size(max = 50, message = "角色编码长度不能超过50个字符")
    @ApiModelProperty(value = "角色编码", required = true)
    private String roleCode;

    @NotBlank(message = "角色权限字符不能为空")
    @Size(max = 100, message = "角色权限字符长度不能超过100个字符")
    @ApiModelProperty(value = "角色权限字符", required = true)
    private String roleKey;

    @ApiModelProperty(value = "显示顺序")
    private Integer roleSort;

    @ApiModelProperty(value = "数据范围")
    private Integer dataScope;

    @ApiModelProperty(value = "菜单树选择项是否关联显示")
    private Integer menuCheckStrictly;

    @ApiModelProperty(value = "部门树选择项是否关联显示")
    private Integer deptCheckStrictly;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "菜单ID列表")
    private List<Long> menuIds;

    @ApiModelProperty(value = "部门ID列表")
    private List<Long> deptIds;

    @ApiModelProperty(value = "备注")
    private String remark;
}
