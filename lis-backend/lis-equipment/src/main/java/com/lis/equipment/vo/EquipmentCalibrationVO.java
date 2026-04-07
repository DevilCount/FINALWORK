package com.lis.equipment.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "设备校准VO")
public class EquipmentCalibrationVO implements Serializable {

    @ApiModelProperty(value = "校准ID")
    private Long id;

    @ApiModelProperty(value = "校准编号")
    private String calibrationNo;

    @ApiModelProperty(value = "设备ID")
    private Long equipmentId;

    @ApiModelProperty(value = "设备编码")
    private String equipmentCode;

    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    @ApiModelProperty(value = "校准类型")
    private String calibrationType;

    @ApiModelProperty(value = "校准类型描述")
    private String calibrationTypeDesc;

    @ApiModelProperty(value = "校准日期")
    private LocalDate calibrationDate;

    @ApiModelProperty(value = "校准机构")
    private String calibrationOrg;

    @ApiModelProperty(value = "校准人ID")
    private Long calibrationUserId;

    @ApiModelProperty(value = "校准人姓名")
    private String calibrationUserName;

    @ApiModelProperty(value = "校准方法")
    private String calibrationMethod;

    @ApiModelProperty(value = "校准结果")
    private String calibrationResult;

    @ApiModelProperty(value = "校准结果描述")
    private String calibrationResultDesc;

    @ApiModelProperty(value = "校准证书号")
    private String calibrationCertificate;

    @ApiModelProperty(value = "有效期开始日期")
    private LocalDate validStartDate;

    @ApiModelProperty(value = "有效期结束日期")
    private LocalDate validEndDate;

    @ApiModelProperty(value = "校准报告路径")
    private String calibrationReport;

    @ApiModelProperty(value = "校准费用")
    private BigDecimal calibrationCost;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
