package com.lis.hl7.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("hl7_message")
public class Hl7MessageDO implements Serializable {

    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String messageId;

    private Long interfaceId;

    private String interfaceCode;

    private String direction;

    private String messageType;

    private String triggerEvent;

    private String messageControlId;

    private String processingId;

    private String versionId;

    private String sendingApp;

    private String sendingFacility;

    private String receivingApp;

    private String receivingFacility;

    private LocalDateTime messageTime;

    private String patientId;

    private String patientName;

    private String visitNo;

    private String specimenNo;

    private String rawMessage;

    private String parsedMessage;

    private String transformedMessage;

    private String ackMessage;

    private String ackStatus;

    private String ackCode;

    private String ackMessageText;

    private String processStatus;

    private LocalDateTime processTime;

    private Long processDuration;

    private String errorCode;

    private String errorMsg;

    private Integer retryCount;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
