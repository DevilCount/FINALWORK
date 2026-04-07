package com.lis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ApiModel(description = "设备统计VO")
public class EquipmentStatVO implements Serializable {

    @ApiModelProperty(value = "统计日期")
    private LocalDate statDate;

    @ApiModelProperty(value = "设备ID")
    private Long equipmentId;

    @ApiModelProperty(value = "设备编码")
    private String equipmentCode;

    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    @ApiModelProperty(value = "检验次数")
    private Integer testCount;

    @ApiModelProperty(value = "检验项目数")
    private Integer testItemCount;

    @ApiModelProperty(value = "质控次数")
    private Integer qcCount;

    @ApiModelProperty(value = "质控通过次数")
    private Integer qcPassCount;

    @ApiModelProperty(value = "质控失败次数")
    private Integer qcFailCount;

    @ApiModelProperty(value = "质控通过率(%)")
    private BigDecimal qcPassRate;

    @ApiModelProperty(value = "故障次数")
    private Integer faultCount;

    @ApiModelProperty(value = "故障时长（分钟）")
    private Integer faultDuration;

    @ApiModelProperty(value = "设备可用率(%)")
    private BigDecimal uptimeRate;
}
