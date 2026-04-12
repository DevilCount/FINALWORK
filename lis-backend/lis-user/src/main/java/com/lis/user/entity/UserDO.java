package com.lis.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class UserDO extends BaseEntity {

    private Long deptId;

    private String userName;

    private String nickName;

    private String userType;

    private String email;

    private String phone;

    private String gender;

    private String avatar;

    private String password;

    private Integer status;

    private String loginIp;

    private LocalDateTime loginDate;

    private LocalDateTime pwdUpdateTime;
}
