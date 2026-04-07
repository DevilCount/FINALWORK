package com.lis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "ECharts图表数据VO")
public class EChartsVO implements Serializable {

    @ApiModelProperty(value = "图表标题")
    private String title;

    @ApiModelProperty(value = "图表副标题")
    private String subtitle;

    @ApiModelProperty(value = "图例数据")
    private Legend legend;

    @ApiModelProperty(value = "X轴数据")
    private XAxis xAxis;

    @ApiModelProperty(value = "Y轴数据")
    private YAxis yAxis;

    @ApiModelProperty(value = "系列数据")
    private List<Series> series;

    @ApiModelProperty(value = "提示框配置")
    private Tooltip tooltip;

    @Data
    @ApiModel(description = "图例配置")
    public static class Legend implements Serializable {
        @ApiModelProperty(value = "图例数据列表")
        private List<String> data;

        @ApiModelProperty(value = "图例位置")
        private String orient;

        @ApiModelProperty(value = "图例左边距")
        private String left;
    }

    @Data
    @ApiModel(description = "X轴配置")
    public static class XAxis implements Serializable {
        @ApiModelProperty(value = "轴类型")
        private String type;

        @ApiModelProperty(value = "轴数据")
        private List<String> data;

        @ApiModelProperty(value = "轴名称")
        private String name;
    }

    @Data
    @ApiModel(description = "Y轴配置")
    public static class YAxis implements Serializable {
        @ApiModelProperty(value = "轴类型")
        private String type;

        @ApiModelProperty(value = "轴名称")
        private String name;

        @ApiModelProperty(value = "是否显示分割线")
        private Boolean splitLine;
    }

    @Data
    @ApiModel(description = "系列数据配置")
    public static class Series implements Serializable {
        @ApiModelProperty(value = "系列名称")
        private String name;

        @ApiModelProperty(value = "图表类型")
        private String type;

        @ApiModelProperty(value = "数据")
        private List<Object> data;

        @ApiModelProperty(value = "是否平滑曲线")
        private Boolean smooth;

        @ApiModelProperty(value = "是否堆叠")
        private String stack;

        @ApiModelProperty(value = "面积图填充")
        private AreaStyle areaStyle;
    }

    @Data
    @ApiModel(description = "面积图样式")
    public static class AreaStyle implements Serializable {
        @ApiModelProperty(value = "透明度")
        private Double opacity;
    }

    @Data
    @ApiModel(description = "提示框配置")
    public static class Tooltip implements Serializable {
        @ApiModelProperty(value = "触发类型")
        private String trigger;

        @ApiModelProperty(value = "是否显示")
        private Boolean show;
    }
}
