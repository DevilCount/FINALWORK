package com.lis.statistics.util;

import com.lis.statistics.vo.EChartsVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EChartsConverter {

    public static EChartsVO createBarChart(String title, String subtitle,
                                            List<String> xAxisData, List<Object> seriesData,
                                            String xAxisName, String yAxisName) {
        EChartsVO chart = new EChartsVO();
        chart.setTitle(title);
        chart.setSubtitle(subtitle);

        EChartsVO.XAxis xAxis = new EChartsVO.XAxis();
        xAxis.setType("category");
        xAxis.setData(xAxisData);
        xAxis.setName(xAxisName);
        chart.setXAxis(xAxis);

        EChartsVO.YAxis yAxis = new EChartsVO.YAxis();
        yAxis.setType("value");
        yAxis.setName(yAxisName);
        yAxis.setSplitLine(true);
        chart.setYAxis(yAxis);

        EChartsVO.Tooltip tooltip = new EChartsVO.Tooltip();
        tooltip.setTrigger("axis");
        tooltip.setShow(true);
        chart.setTooltip(tooltip);

        EChartsVO.Series series = new EChartsVO.Series();
        series.setName(title);
        series.setType("bar");
        series.setData(seriesData);
        chart.setSeries(List.of(series));

        return chart;
    }

    public static EChartsVO createLineChart(String title, String subtitle,
                                             List<String> xAxisData, List<Object> seriesData,
                                             String xAxisName, String yAxisName,
                                             boolean smooth, boolean area) {
        EChartsVO chart = new EChartsVO();
        chart.setTitle(title);
        chart.setSubtitle(subtitle);

        EChartsVO.XAxis xAxis = new EChartsVO.XAxis();
        xAxis.setType("category");
        xAxis.setData(xAxisData);
        xAxis.setName(xAxisName);
        chart.setXAxis(xAxis);

        EChartsVO.YAxis yAxis = new EChartsVO.YAxis();
        yAxis.setType("value");
        yAxis.setName(yAxisName);
        yAxis.setSplitLine(true);
        chart.setYAxis(yAxis);

        EChartsVO.Tooltip tooltip = new EChartsVO.Tooltip();
        tooltip.setTrigger("axis");
        tooltip.setShow(true);
        chart.setTooltip(tooltip);

        EChartsVO.Series series = new EChartsVO.Series();
        series.setName(title);
        series.setType("line");
        series.setData(seriesData);
        series.setSmooth(smooth);
        if (area) {
            EChartsVO.AreaStyle areaStyle = new EChartsVO.AreaStyle();
            areaStyle.setOpacity(0.3);
            series.setAreaStyle(areaStyle);
        }
        chart.setSeries(List.of(series));

        return chart;
    }

    public static EChartsVO createPieChart(String title, String subtitle,
                                            List<String> legendData, List<Map<String, Object>> seriesData) {
        EChartsVO chart = new EChartsVO();
        chart.setTitle(title);
        chart.setSubtitle(subtitle);

        EChartsVO.Legend legend = new EChartsVO.Legend();
        legend.setData(legendData);
        legend.setOrient("vertical");
        legend.setLeft("left");
        chart.setLegend(legend);

        EChartsVO.Tooltip tooltip = new EChartsVO.Tooltip();
        tooltip.setTrigger("item");
        tooltip.setShow(true);
        chart.setTooltip(tooltip);

        EChartsVO.Series series = new EChartsVO.Series();
        series.setName(title);
        series.setType("pie");
        series.setData(new ArrayList<>(seriesData));
        chart.setSeries(List.of(series));

        return chart;
    }

    public static EChartsVO createMultiLineChart(String title, String subtitle,
                                                  List<String> legendData,
                                                  List<String> xAxisData,
                                                  List<SeriesData> seriesDataList,
                                                  String xAxisName, String yAxisName) {
        EChartsVO chart = new EChartsVO();
        chart.setTitle(title);
        chart.setSubtitle(subtitle);

        EChartsVO.Legend legend = new EChartsVO.Legend();
        legend.setData(legendData);
        legend.setOrient("horizontal");
        legend.setLeft("center");
        chart.setLegend(legend);

        EChartsVO.XAxis xAxis = new EChartsVO.XAxis();
        xAxis.setType("category");
        xAxis.setData(xAxisData);
        xAxis.setName(xAxisName);
        chart.setXAxis(xAxis);

        EChartsVO.YAxis yAxis = new EChartsVO.YAxis();
        yAxis.setType("value");
        yAxis.setName(yAxisName);
        yAxis.setSplitLine(true);
        chart.setYAxis(yAxis);

        EChartsVO.Tooltip tooltip = new EChartsVO.Tooltip();
        tooltip.setTrigger("axis");
        tooltip.setShow(true);
        chart.setTooltip(tooltip);

        List<EChartsVO.Series> seriesList = new ArrayList<>();
        for (SeriesData sd : seriesDataList) {
            EChartsVO.Series series = new EChartsVO.Series();
            series.setName(sd.getName());
            series.setType("line");
            series.setData(sd.getData());
            series.setSmooth(true);
            seriesList.add(series);
        }
        chart.setSeries(seriesList);

        return chart;
    }

    public static EChartsVO createMultiBarChart(String title, String subtitle,
                                                 List<String> legendData,
                                                 List<String> xAxisData,
                                                 List<SeriesData> seriesDataList,
                                                 String xAxisName, String yAxisName) {
        EChartsVO chart = new EChartsVO();
        chart.setTitle(title);
        chart.setSubtitle(subtitle);

        EChartsVO.Legend legend = new EChartsVO.Legend();
        legend.setData(legendData);
        legend.setOrient("horizontal");
        legend.setLeft("center");
        chart.setLegend(legend);

        EChartsVO.XAxis xAxis = new EChartsVO.XAxis();
        xAxis.setType("category");
        xAxis.setData(xAxisData);
        xAxis.setName(xAxisName);
        chart.setXAxis(xAxis);

        EChartsVO.YAxis yAxis = new EChartsVO.YAxis();
        yAxis.setType("value");
        yAxis.setName(yAxisName);
        yAxis.setSplitLine(true);
        chart.setYAxis(yAxis);

        EChartsVO.Tooltip tooltip = new EChartsVO.Tooltip();
        tooltip.setTrigger("axis");
        tooltip.setShow(true);
        chart.setTooltip(tooltip);

        List<EChartsVO.Series> seriesList = new ArrayList<>();
        for (SeriesData sd : seriesDataList) {
            EChartsVO.Series series = new EChartsVO.Series();
            series.setName(sd.getName());
            series.setType("bar");
            series.setData(sd.getData());
            seriesList.add(series);
        }
        chart.setSeries(seriesList);

        return chart;
    }

    public static EChartsVO createRadarChart(String title, String subtitle,
                                              List<Map<String, Object>> indicatorData,
                                              List<RadarSeriesData> seriesDataList) {
        EChartsVO chart = new EChartsVO();
        chart.setTitle(title);
        chart.setSubtitle(subtitle);

        EChartsVO.Tooltip tooltip = new EChartsVO.Tooltip();
        tooltip.setTrigger("item");
        tooltip.setShow(true);
        chart.setTooltip(tooltip);

        List<EChartsVO.Series> seriesList = new ArrayList<>();
        for (RadarSeriesData sd : seriesDataList) {
            EChartsVO.Series series = new EChartsVO.Series();
            series.setName(sd.getName());
            series.setType("radar");
            series.setData(sd.getData());
            seriesList.add(series);
        }
        chart.setSeries(seriesList);

        return chart;
    }

    public static class SeriesData {
        private String name;
        private List<Object> data;

        public SeriesData(String name, List<Object> data) {
            this.name = name;
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
            this.data = data;
        }
    }

    public static class RadarSeriesData {
        private String name;
        private List<Object> data;

        public RadarSeriesData(String name, List<Object> data) {
            this.name = name;
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
            this.data = data;
        }
    }
}
