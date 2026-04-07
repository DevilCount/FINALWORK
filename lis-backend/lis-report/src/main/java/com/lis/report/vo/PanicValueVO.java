package com.lis.report.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "危急值信息响应")
public class PanicValueVO implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "危急值编号")
    private String panicNo;

    @ApiModelProperty(value = "报告ID")
    private Long reportId;

    @ApiModelProperty(value = "报告编号")
    private String reportNo;

    @ApiModelProperty(value = "标本ID")
    private Long specimenId;

    @ApiModelProperty(value = "标本编号")
    private String specimenNo;

    @ApiModelProperty(value = "患者ID")
    private Long patientId;

    @ApiModelProperty(value = "患者姓名")
    private String patientName;

    @ApiModelProperty(value = "科室ID")
    private Long deptId;

    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @ApiModelProperty(value = "病区名称")
    private String wardName;

    @ApiModelProperty(value = "床号")
    private String bedNo;

    @ApiModelProperty(value = "项目编码")
    private String itemCode;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "结果值")
    private String resultValue;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "危急值下限")
    private BigDecimal panicLow;

    @ApiModelProperty(value = "危急值上限")
    private BigDecimal panicHigh;

    @ApiModelProperty(value = "危急值类型（high偏高 low偏低）")
    private String panicType;

    @ApiModelProperty(value = "发现时间")
    private LocalDateTime findTime;

    @ApiModelProperty(value = "发现人ID")
    private Long findUserId;

    @ApiModelProperty(value = "发现人姓名")
    private String findUserName;

    @ApiModelProperty(value = "通知时间")
    private LocalDateTime notifyTime;

    @ApiModelProperty(value = "通知人ID")
    private Long notifyUserId;

    @ApiModelProperty(value = "通知人姓名")
    private String notifyUserName;

    @ApiModelProperty(value = "通知方式")
    private String notifyMethod;

    @ApiModelProperty(value = "接收人姓名")
    private String receiveUserName;

    @ApiModelProperty(value = "接收时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "处理状态（0待处理 1已通知 2已处理 3已确认）")
    private Integer handleStatus;

    @ApiModelProperty(value = "处理时间")
    private LocalDateTime handleTime;

    @ApiModelProperty(value = "处理人ID")
    private Long handleUserId;

    @ApiModelProperty(value = "处理人姓名")
    private String handleUserName;

    @ApiModelProperty(value = "处理结果")
    private String handleResult;

    @ApiModelProperty(value = "确认时间")
    private LocalDateTime confirmTime;

    @ApiModelProperty(value = "确认人ID")
    private Long confirmUserId;

    @ApiModelProperty(value = "确认人姓名")
    private String confirmUserName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
