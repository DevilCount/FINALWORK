package com.lis.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class DeptDO extends BaseEntity {

    private Long parentId;

    private String deptName;

    private String deptCode;

    private String leader;

    private String phone;

    private String email;

    private Integer sort;

    private Integer status;

    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;
}
