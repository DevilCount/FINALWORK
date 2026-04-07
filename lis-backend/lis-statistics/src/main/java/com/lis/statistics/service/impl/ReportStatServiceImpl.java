package com.lis.statistics.service.impl;

import com.lis.common.result.PageResult;
import com.lis.statistics.dto.ReportQueryDTO;
import com.lis.statistics.mapper.StatTestItemDailyMapper;
import com.lis.statistics.service.ReportStatService;
import com.lis.statistics.util.EChartsConverter;
import com.lis.statistics.vo.EChartsVO;
import com.lis.statistics.vo.ReportStatVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportStatServiceImpl implements ReportStatService {

    private final StatTestItemDailyMapper testItemDailyMapper;

    @Override
    public List<ReportStatVO> getDailyStat(ReportQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = testItemDailyMapper.selectDailyStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getDeptId()
        );
        return convertToDailyStatVO(dataList);
    }

    @Override
    public PageResult<ReportStatVO> getItemStatPage(ReportQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = testItemDailyMapper.selectItemStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getDeptId(),
                queryDTO.getTestItemId()
        );

        List<ReportStatVO> voList = convertToItemStatVO(dataList);

        int total = voList.size();
        int start = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();
        int end = Math.min(start + queryDTO.getPageSize(), total);

        List<ReportStatVO> pageData = start < total ? voList.subList(start, end) : new ArrayList<>();

        return new PageResult<>(pageData, (long) total, queryDTO.getPageNum(), queryDTO.getPageSize());
    }

    @Override
    public List<ReportStatVO> getDeptStat(ReportQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = testItemDailyMapper.selectDeptStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate()
        );
        return convertToDeptStatVO(dataList);
    }

    @Override
    public EChartsVO getReportTrendChart(ReportQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = testItemDailyMapper.selectDailyStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getDeptId()
        );

        List<String> dates = dataList.stream()
                .map(m -> ((LocalDate) m.get("stat_date")).format(DateTimeFormatter.ofPattern("MM-dd")))
                .collect(Collectors.toList());

        List<Object> totalData = dataList.stream()
                .map(m -> m.get("total_count"))
                .collect(Collectors.toList());

        List<Object> normalData = dataList.stream()
                .map(m -> m.get("normal_count"))
                .collect(Collectors.toList());

        List<Object> abnormalData = dataList.stream()
                .map(m -> m.get("abnormal_count"))
                .collect(Collectors.toList());

        List<Object> panicData = dataList.stream()
                .map(m -> m.get("panic_count"))
                .collect(Collectors.toList());

        EChartsVO chart = new EChartsVO();
        chart.setTitle("报告趋势图");
        chart.setSubtitle(queryDTO.getStartDate() + " 至 " + queryDTO.getEndDate());

        EChartsVO.Legend legend = new EChartsVO.Legend();
        legend.setData(List.of("总数", "正常", "异常", "危急值"));
        legend.setOrient("horizontal");
        legend.setLeft("center");
        chart.setLegend(legend);

        EChartsVO.XAxis xAxis = new EChartsVO.XAxis();
        xAxis.setType("category");
        xAxis.setData(dates);
        xAxis.setName("日期");
        chart.setXAxis(xAxis);

        EChartsVO.YAxis yAxis = new EChartsVO.YAxis();
        yAxis.setType("value");
        yAxis.setName("数量");
        yAxis.setSplitLine(true);
        chart.setYAxis(yAxis);

        EChartsVO.Tooltip tooltip = new EChartsVO.Tooltip();
        tooltip.setTrigger("axis");
        tooltip.setShow(true);
        chart.setTooltip(tooltip);

        List<EChartsVO.Series> seriesList = new ArrayList<>();
        seriesList.add(createSeries("总数", "line", totalData));
        seriesList.add(createSeries("正常", "line", normalData));
        seriesList.add(createSeries("异常", "line", abnormalData));
        seriesList.add(createSeries("危急值", "line", panicData));
        chart.setSeries(seriesList);

        return chart;
    }

    @Override
    public EChartsVO getAbnormalRateChart(ReportQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = testItemDailyMapper.selectItemStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getDeptId(),
                queryDTO.getTestItemId()
        );

        List<String> itemNames = dataList.stream()
                .map(m -> (String) m.get("item_name"))
                .limit(10)
                .collect(Collectors.toList());

        List<Object> abnormalRates = dataList.stream()
                .map(m -> m.get("abnormal_rate"))
                .limit(10)
                .collect(Collectors.toList());

        return EChartsConverter.createBarChart(
                "异常率统计图",
                "检验项目异常率排行",
                itemNames,
                abnormalRates,
                "检验项目",
                "异常率(%)"
        );
    }

    @Override
    public EChartsVO getPanicDistributionChart(ReportQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = testItemDailyMapper.selectItemStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getDeptId(),
                queryDTO.getTestItemId()
        );

        List<Map<String, Object>> pieData = dataList.stream()
                .filter(m -> getIntValue(m, "panic_count") > 0)
                .limit(10)
                .map(m -> {
                    Map<String, Object> item = new java.util.HashMap<>();
                    item.put("name", m.get("item_name"));
                    item.put("value", m.get("panic_count"));
                    return item;
                })
                .collect(Collectors.toList());

        List<String> legendData = dataList.stream()
                .filter(m -> getIntValue(m, "panic_count") > 0)
                .limit(10)
                .map(m -> (String) m.get("item_name"))
                .collect(Collectors.toList());

        return EChartsConverter.createPieChart(
                "危急值分布图",
                "危急值项目分布",
                legendData,
                pieData
        );
    }

    private List<ReportStatVO> convertToDailyStatVO(List<Map<String, Object>> dataList) {
        return dataList.stream().map(m -> {
            ReportStatVO vo = new ReportStatVO();
            vo.setStatDate((LocalDate) m.get("stat_date"));
            vo.setTotalCount(getIntValue(m, "total_count"));
            vo.setNormalCount(getIntValue(m, "normal_count"));
            vo.setAbnormalCount(getIntValue(m, "abnormal_count"));
            vo.setPanicCount(getIntValue(m, "panic_count"));
            vo.setPositiveCount(getIntValue(m, "positive_count"));
            vo.setNegativeCount(getIntValue(m, "negative_count"));
            vo.setAbnormalRate(getBigDecimalValue(m, "abnormal_rate"));
            vo.setPositiveRate(getBigDecimalValue(m, "positive_rate"));
            return vo;
        }).collect(Collectors.toList());
    }

    private List<ReportStatVO> convertToItemStatVO(List<Map<String, Object>> dataList) {
        return dataList.stream().map(m -> {
            ReportStatVO vo = new ReportStatVO();
            vo.setTestItemId(getLongValue(m, "test_item_id"));
            vo.setItemCode((String) m.get("item_code"));
            vo.setItemName((String) m.get("item_name"));
            vo.setTotalCount(getIntValue(m, "total_count"));
            vo.setNormalCount(getIntValue(m, "normal_count"));
            vo.setAbnormalCount(getIntValue(m, "abnormal_count"));
            vo.setPanicCount(getIntValue(m, "panic_count"));
            vo.setAbnormalRate(getBigDecimalValue(m, "abnormal_rate"));
            vo.setPositiveRate(getBigDecimalValue(m, "positive_rate"));
            return vo;
        }).collect(Collectors.toList());
    }

    private List<ReportStatVO> convertToDeptStatVO(List<Map<String, Object>> dataList) {
        return dataList.stream().map(m -> {
            ReportStatVO vo = new ReportStatVO();
            vo.setDeptId(getLongValue(m, "dept_id"));
            vo.setDeptName((String) m.get("dept_name"));
            vo.setTotalCount(getIntValue(m, "total_count"));
            vo.setAbnormalCount(getIntValue(m, "abnormal_count"));
            vo.setPanicCount(getIntValue(m, "panic_count"));
            return vo;
        }).collect(Collectors.toList());
    }

    private EChartsVO.Series createSeries(String name, String type, List<Object> data) {
        EChartsVO.Series series = new EChartsVO.Series();
        series.setName(name);
        series.setType(type);
        series.setData(data);
        series.setSmooth(true);
        return series;
    }

    private Integer getIntValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return 0;
        if (value instanceof Number) return ((Number) value).intValue();
        return Integer.parseInt(value.toString());
    }

    private Long getLongValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof Number) return ((Number) value).longValue();
        return Long.parseLong(value.toString());
    }

    private BigDecimal getBigDecimalValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return BigDecimal.ZERO;
        if (value instanceof BigDecimal) return (BigDecimal) value;
        if (value instanceof Number) return BigDecimal.valueOf(((Number) value).doubleValue());
        return new BigDecimal(value.toString());
    }
}
