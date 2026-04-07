package com.lis.hl7.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("interface_connection_log")
public class InterfaceConnectionLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long interfaceId;

    private String interfaceCode;

    private String eventType;

    private LocalDateTime eventTime;

    private String clientIp;

    private Integer clientPort;

    private String eventDesc;

    private String errorCode;

    private String errorMsg;

    private LocalDateTime createTime;
}
