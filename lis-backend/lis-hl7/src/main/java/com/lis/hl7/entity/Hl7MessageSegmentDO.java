package com.lis.hl7.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("hl7_message_segment")
public class Hl7MessageSegmentDO implements Serializable {

    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private Long messageId;

    private String segmentName;

    private Integer segmentSeq;

    private String segmentContent;

    private String parsedData;

    private LocalDateTime createTime;
}
