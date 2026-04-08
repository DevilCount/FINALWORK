package com.lis.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class RoleDO extends BaseEntity {

    private String roleName;

    private String roleCode;

    private String roleKey;

    private Integer roleSort;

    private Integer dataScope;

    private Integer menuCheckStrictly;

    private Integer deptCheckStrictly;

    private Integer status;

    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;
}
