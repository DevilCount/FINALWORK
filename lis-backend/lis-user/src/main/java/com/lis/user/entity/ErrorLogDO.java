package com.lis.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_error_log")
public class ErrorLogDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private LocalDateTime errorTime;

    private String errorLevel;

    private String errorClass;

    private String errorMethod;

    private Integer errorLine;

    private String errorMessage;

    private String errorStack;

    private String requestUrl;

    private String requestMethod;

    private String requestParams;

    private Long userId;

    private String userName;

    private String userIp;

    private String serverNode;

    private String threadName;

    private Integer delFlag;

    private LocalDateTime createTime;
}
