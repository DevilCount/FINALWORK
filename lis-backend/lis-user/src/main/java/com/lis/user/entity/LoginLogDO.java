package com.lis.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_login_log")
public class LoginLogDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String userName;

    @TableField("ipaddr")
    private String ip;

    private String loginLocation;

    private String browser;

    private String os;

    private Integer status;

    private String msg;

    private LocalDateTime loginTime;
}
