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
@TableName("hl7_segment_template")
public class Hl7SegmentTemplateDO implements Serializable {

    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String templateCode;

    private String templateName;

    private String segmentName;

    private String segmentContent;

    private String fieldMapping;

    private Integer isDefault;

    private Integer status;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
