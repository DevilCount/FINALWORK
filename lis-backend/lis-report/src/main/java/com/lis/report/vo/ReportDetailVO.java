package com.lis.report.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "报告详情响应")
public class ReportDetailVO implements Serializable {

    @ApiModelProperty(value = "报告ID")
    private Long id;

    @ApiModelProperty(value = "报告编号")
    private String reportNo;

    @ApiModelProperty(value = "报告类型")
    private String reportType;

    @ApiModelProperty(value = "标本ID")
    private Long specimenId;

    @ApiModelProperty(value = "标本编号")
    private String specimenNo;

    @ApiModelProperty(value = "患者ID")
    private Long patientId;

    @ApiModelProperty(value = "患者姓名")
    private String patientName;

    @ApiModelProperty(value = "患者性别")
    private String patientGender;

    @ApiModelProperty(value = "患者年龄")
    private String patientAge;

    @ApiModelProperty(value = "患者身份证号")
    private String patientIdNo;

    @ApiModelProperty(value = "科室ID")
    private Long deptId;

    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @ApiModelProperty(value = "病区名称")
    private String wardName;

    @ApiModelProperty(value = "床号")
    private String bedNo;

    @ApiModelProperty(value = "送检医生")
    private String doctorName;

    @ApiModelProperty(value = "标本类型名称")
    private String specimenTypeName;

    @ApiModelProperty(value = "采集时间")
    private LocalDateTime collectTime;

    @ApiModelProperty(value = "接收时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "检验时间")
    private LocalDateTime testTime;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime auditTime;

    @ApiModelProperty(value = "报告时间")
    private LocalDateTime reportTime;

    @ApiModelProperty(value = "检验人ID")
    private Long testUserId;

    @ApiModelProperty(value = "检验人姓名")
    private String testUserName;

    @ApiModelProperty(value = "审核人ID")
    private Long auditUserId;

    @ApiModelProperty(value = "审核人姓名")
    private String auditUserName;

    @ApiModelProperty(value = "初审人ID")
    private Long firstAuditUserId;

    @ApiModelProperty(value = "初审人姓名")
    private String firstAuditUserName;

    @ApiModelProperty(value = "初审时间")
    private LocalDateTime firstAuditTime;

    @ApiModelProperty(value = "初审意见")
    private String firstAuditOpinion;

    @ApiModelProperty(value = "终审人ID")
    private Long finalAuditUserId;

    @ApiModelProperty(value = "终审人姓名")
    private String finalAuditUserName;

    @ApiModelProperty(value = "终审时间")
    private LocalDateTime finalAuditTime;

    @ApiModelProperty(value = "终审意见")
    private String finalAuditOpinion;

    @ApiModelProperty(value = "报告人ID")
    private Long reportUserId;

    @ApiModelProperty(value = "报告人姓名")
    private String reportUserName;

    @ApiModelProperty(value = "报告状态")
    private String status;

    @ApiModelProperty(value = "是否急诊")
    private Integer isStat;

    @ApiModelProperty(value = "是否含危急值")
    private Integer isPanic;

    @ApiModelProperty(value = "是否异常")
    private Integer isAbnormal;

    @ApiModelProperty(value = "打印次数")
    private Integer printCount;

    @ApiModelProperty(value = "最后打印时间")
    private LocalDateTime lastPrintTime;

    @ApiModelProperty(value = "临床诊断")
    private String clinicalDiagnosis;

    @ApiModelProperty(value = "报告结论")
    private String reportConclusion;

    @ApiModelProperty(value = "报告明细列表")
    private List<ReportItemVO> items;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
