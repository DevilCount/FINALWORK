package com.lis.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_role_dept")
public class RoleDeptDO implements Serializable {

    private Long roleId;

    private Long deptId;
}
