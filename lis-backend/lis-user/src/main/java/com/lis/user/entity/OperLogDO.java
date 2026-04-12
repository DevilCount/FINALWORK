package com.lis.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_oper_log")
public class OperLogDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private Integer businessType;

    private String method;

    private String requestMethod;

    private String operName;

    private String operUrl;

    private String operIp;

    private String operParam;

    private String operResult;

    private Integer costTime;

    private String deptName;

    private String operLocation;

    private LocalDateTime operTime;

    private Integer delFlag;
}
