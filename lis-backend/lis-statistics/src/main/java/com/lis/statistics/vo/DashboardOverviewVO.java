package com.lis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "仪表盘统计概览VO")
public class DashboardOverviewVO implements Serializable {

    @ApiModelProperty(value = "今日标本总数")
    private Integer todaySpecimenCount;

    @ApiModelProperty(value = "今日报告总数")
    private Integer todayReportCount;

    @ApiModelProperty(value = "今日危急值数")
    private Integer todayPanicCount;

    @ApiModelProperty(value = "今日设备在线数")
    private Integer todayOnlineEquipment;

    @ApiModelProperty(value = "本周标本趋势")
    private List<TrendData> weeklySpecimenTrend;

    @ApiModelProperty(value = "本周报告趋势")
    private List<TrendData> weeklyReportTrend;

    @ApiModelProperty(value = "科室标本分布")
    private List<DistributionData> deptSpecimenDistribution;

    @ApiModelProperty(value = "设备使用率排行")
    private List<RankData> equipmentUsageRank;

    @Data
    @ApiModel(description = "趋势数据")
    public static class TrendData implements Serializable {
        @ApiModelProperty(value = "日期")
        private String date;

        @ApiModelProperty(value = "数量")
        private Integer count;
    }

    @Data
    @ApiModel(description = "分布数据")
    public static class DistributionData implements Serializable {
        @ApiModelProperty(value = "名称")
        private String name;

        @ApiModelProperty(value = "数量")
        private Integer value;
    }

    @Data
    @ApiModel(description = "排行数据")
    public static class RankData implements Serializable {
        @ApiModelProperty(value = "名称")
        private String name;

        @ApiModelProperty(value = "数值")
        private Double value;

        @ApiModelProperty(value = "排名")
        private Integer rank;
    }
}
