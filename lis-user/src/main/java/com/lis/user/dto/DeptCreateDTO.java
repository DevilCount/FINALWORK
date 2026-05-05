package com.lis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@ApiModel(description = "部门创建参数")
public class DeptCreateDTO implements Serializable {

    @ApiModelProperty(value = "父部门ID")
    private Long parentId;

    @NotBlank(message = "部门名称不能为空")
    @Size(max = 50, message = "部门名称长度不能超过50个字符")
    @ApiModelProperty(value = "部门名称", required = true)
    private String deptName;

    @Size(max = 50, message = "部门编码长度不能超过50个字符")
    @ApiModelProperty(value = "部门编码")
    private String deptCode;

    @Size(max = 50, message = "负责人长度不能超过50个字符")
    @ApiModelProperty(value = "负责人")
    private String leader;

    @Size(max = 20, message = "联系电话长度不能超过20个字符")
    @ApiModelProperty(value = "联系电话")
    private String phone;

    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "显示顺序")
    private Integer sort;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
