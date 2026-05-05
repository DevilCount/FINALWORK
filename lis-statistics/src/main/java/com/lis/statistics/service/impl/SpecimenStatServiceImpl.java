package com.lis.statistics.service.impl;

import com.lis.common.result.PageResult;
import com.lis.statistics.dto.SpecimenQueryDTO;
import com.lis.statistics.mapper.StatSpecimenDailyMapper;
import com.lis.statistics.service.SpecimenStatService;
import com.lis.statistics.util.EChartsConverter;
import com.lis.statistics.vo.EChartsVO;
import com.lis.statistics.vo.SpecimenStatVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecimenStatServiceImpl implements SpecimenStatService {

    private final StatSpecimenDailyMapper specimenDailyMapper;

    @Override
    public List<SpecimenStatVO> getDailyStat(SpecimenQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = specimenDailyMapper.selectDailyStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getDeptId(),
                queryDTO.getSpecimenTypeId()
        );
        return convertToDailyStatVO(dataList);
    }

    @Override
    public PageResult<SpecimenStatVO> getDeptStatPage(SpecimenQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = specimenDailyMapper.selectDeptStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getSpecimenTypeId()
        );

        List<SpecimenStatVO> voList = convertToDeptStatVO(dataList);

        int total = voList.size();
        int start = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();
        int end = Math.min(start + queryDTO.getPageSize(), total);

        List<SpecimenStatVO> pageData = start < total ? voList.subList(start, end) : new ArrayList<>();

        return new PageResult<>((long) total, queryDTO.getPageNum(), queryDTO.getPageSize(), pageData);
    }

    @Override
    public List<SpecimenStatVO> getTypeStat(SpecimenQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = specimenDailyMapper.selectTypeStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate()
        );
        return convertToTypeStatVO(dataList);
    }

    @Override
    public EChartsVO getSpecimenTrendChart(SpecimenQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = specimenDailyMapper.selectDailyStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getDeptId(),
                queryDTO.getSpecimenTypeId()
        );

        List<String> dates = dataList.stream()
                .map(m -> ((LocalDate) m.get("stat_date")).format(DateTimeFormatter.ofPattern("MM-dd")))
                .collect(Collectors.toList());

        List<Object> totalData = dataList.stream()
                .map(m -> m.get("total_count"))
                .collect(Collectors.toList());

        List<Object> completedData = dataList.stream()
                .map(m -> m.get("completed_count"))
                .collect(Collectors.toList());

        List<Object> rejectedData = dataList.stream()
                .map(m -> m.get("rejected_count"))
                .collect(Collectors.toList());

        EChartsVO chart = new EChartsVO();
        chart.setTitle("标本趋势图");
        chart.setSubtitle(queryDTO.getStartDate() + " 至 " + queryDTO.getEndDate());

        EChartsVO.Legend legend = new EChartsVO.Legend();
        legend.setData(List.of("标本总数", "已完成", "拒收"));
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
        seriesList.add(createSeries("标本总数", "line", totalData));
        seriesList.add(createSeries("已完成", "line", completedData));
        seriesList.add(createSeries("拒收", "line", rejectedData));
        chart.setSeries(seriesList);

        return chart;
    }

    @Override
    public EChartsVO getSpecimenDistributionChart(SpecimenQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = specimenDailyMapper.selectDeptStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getSpecimenTypeId()
        );

        List<String> deptNames = dataList.stream()
                .map(m -> (String) m.get("dept_name"))
                .limit(10)
                .collect(Collectors.toList());

        List<Object> counts = dataList.stream()
                .map(m -> m.get("total_count"))
                .limit(10)
                .collect(Collectors.toList());

        return EChartsConverter.createBarChart(
                "科室标本分布图",
                "各科室标本数量统计",
                deptNames,
                counts,
                "科室",
                "标本数"
        );
    }

    @Override
    public EChartsVO getSpecimenTypePieChart(SpecimenQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = specimenDailyMapper.selectTypeStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate()
        );

        List<Map<String, Object>> pieData = dataList.stream()
                .limit(10)
                .map(m -> {
                    Map<String, Object> item = new java.util.HashMap<>();
                    item.put("name", m.get("specimen_type_name"));
                    item.put("value", m.get("total_count"));
                    return item;
                })
                .collect(Collectors.toList());

        List<String> legendData = dataList.stream()
                .limit(10)
                .map(m -> (String) m.get("specimen_type_name"))
                .collect(Collectors.toList());

        return EChartsConverter.createPieChart(
                "标本类型分布图",
                "标本类型占比统计",
                legendData,
                pieData
        );
    }

    private List<SpecimenStatVO> convertToDailyStatVO(List<Map<String, Object>> dataList) {
        return dataList.stream().map(m -> {
            SpecimenStatVO vo = new SpecimenStatVO();
            vo.setStatDate((LocalDate) m.get("stat_date"));
            vo.setTotalCount(getIntValue(m, "total_count"));
            vo.setReceivedCount(getIntValue(m, "received_count"));
            vo.setCompletedCount(getIntValue(m, "completed_count"));
            vo.setRejectedCount(getIntValue(m, "rejected_count"));
            vo.setStatCount(getIntValue(m, "stat_count"));
            vo.setTatAvg(getBigDecimalValue(m, "tat_avg"));
            vo.setTatWithinRate(getBigDecimalValue(m, "tat_within_rate"));
            if (vo.getTotalCount() > 0) {
                BigDecimal rate = BigDecimal.valueOf(vo.getCompletedCount())
                        .divide(BigDecimal.valueOf(vo.getTotalCount()), 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
                vo.setCompletionRate(rate);
            }
            return vo;
        }).collect(Collectors.toList());
    }

    private List<SpecimenStatVO> convertToDeptStatVO(List<Map<String, Object>> dataList) {
        return dataList.stream().map(m -> {
            SpecimenStatVO vo = new SpecimenStatVO();
            vo.setDeptId(getLongValue(m, "dept_id"));
            vo.setDeptName((String) m.get("dept_name"));
            vo.setTotalCount(getIntValue(m, "total_count"));
            vo.setReceivedCount(getIntValue(m, "received_count"));
            vo.setCompletedCount(getIntValue(m, "completed_count"));
            vo.setRejectedCount(getIntValue(m, "rejected_count"));
            return vo;
        }).collect(Collectors.toList());
    }

    private List<SpecimenStatVO> convertToTypeStatVO(List<Map<String, Object>> dataList) {
        return dataList.stream().map(m -> {
            SpecimenStatVO vo = new SpecimenStatVO();
            vo.setSpecimenTypeId(getLongValue(m, "specimen_type_id"));
            vo.setSpecimenTypeName((String) m.get("specimen_type_name"));
            vo.setTotalCount(getIntValue(m, "total_count"));
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
