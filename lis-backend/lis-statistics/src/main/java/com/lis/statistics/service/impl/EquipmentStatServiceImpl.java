package com.lis.statistics.service.impl;

import com.lis.common.result.PageResult;
import com.lis.statistics.dto.EquipmentQueryDTO;
import com.lis.statistics.mapper.StatEquipmentDailyMapper;
import com.lis.statistics.service.EquipmentStatService;
import com.lis.statistics.util.EChartsConverter;
import com.lis.statistics.vo.EChartsVO;
import com.lis.statistics.vo.EquipmentStatVO;
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
public class EquipmentStatServiceImpl implements EquipmentStatService {

    private final StatEquipmentDailyMapper equipmentDailyMapper;

    @Override
    public List<EquipmentStatVO> getDailyStat(EquipmentQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = equipmentDailyMapper.selectDailyStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate()
        );
        return convertToDailyStatVO(dataList);
    }

    @Override
    public PageResult<EquipmentStatVO> getEquipmentStatPage(EquipmentQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = equipmentDailyMapper.selectEquipmentStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getEquipmentId()
        );

        List<EquipmentStatVO> voList = convertToEquipmentStatVO(dataList);

        int total = voList.size();
        int start = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();
        int end = Math.min(start + queryDTO.getPageSize(), total);

        List<EquipmentStatVO> pageData = start < total ? voList.subList(start, end) : new ArrayList<>();

        return new PageResult<>(pageData, (long) total, queryDTO.getPageNum(), queryDTO.getPageSize());
    }

    @Override
    public List<EquipmentStatVO> getEquipmentTrend(EquipmentQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = equipmentDailyMapper.selectEquipmentTrend(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getEquipmentId()
        );
        return convertToTrendVO(dataList);
    }

    @Override
    public EChartsVO getEquipmentTestChart(EquipmentQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = equipmentDailyMapper.selectEquipmentStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getEquipmentId()
        );

        List<String> equipmentNames = dataList.stream()
                .map(m -> (String) m.get("equipment_name"))
                .limit(10)
                .collect(Collectors.toList());

        List<Object> testCounts = dataList.stream()
                .map(m -> m.get("test_count"))
                .limit(10)
                .collect(Collectors.toList());

        return EChartsConverter.createBarChart(
                "设备检验量统计图",
                "设备检验次数排行",
                equipmentNames,
                testCounts,
                "设备",
                "检验次数"
        );
    }

    @Override
    public EChartsVO getEquipmentQcChart(EquipmentQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = equipmentDailyMapper.selectDailyStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate()
        );

        List<String> dates = dataList.stream()
                .map(m -> ((LocalDate) m.get("stat_date")).format(DateTimeFormatter.ofPattern("MM-dd")))
                .collect(Collectors.toList());

        List<Object> qcPassData = dataList.stream()
                .map(m -> m.get("qc_pass_count"))
                .collect(Collectors.toList());

        List<Object> qcFailData = dataList.stream()
                .map(m -> m.get("qc_fail_count"))
                .collect(Collectors.toList());

        EChartsVO chart = new EChartsVO();
        chart.setTitle("质控统计图");
        chart.setSubtitle(queryDTO.getStartDate() + " 至 " + queryDTO.getEndDate());

        EChartsVO.Legend legend = new EChartsVO.Legend();
        legend.setData(List.of("质控通过", "质控失败"));
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
        yAxis.setName("次数");
        yAxis.setSplitLine(true);
        chart.setYAxis(yAxis);

        EChartsVO.Tooltip tooltip = new EChartsVO.Tooltip();
        tooltip.setTrigger("axis");
        tooltip.setShow(true);
        chart.setTooltip(tooltip);

        List<EChartsVO.Series> seriesList = new ArrayList<>();
        seriesList.add(createSeries("质控通过", "bar", qcPassData));
        seriesList.add(createSeries("质控失败", "bar", qcFailData));
        chart.setSeries(seriesList);

        return chart;
    }

    @Override
    public EChartsVO getEquipmentUptimeChart(EquipmentQueryDTO queryDTO) {
        List<Map<String, Object>> dataList = equipmentDailyMapper.selectEquipmentStat(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getEquipmentId()
        );

        List<String> equipmentNames = dataList.stream()
                .map(m -> (String) m.get("equipment_name"))
                .limit(10)
                .collect(Collectors.toList());

        List<Object> uptimeRates = dataList.stream()
                .map(m -> m.get("uptime_rate"))
                .limit(10)
                .collect(Collectors.toList());

        return EChartsConverter.createBarChart(
                "设备可用率统计图",
                "设备可用率排行",
                equipmentNames,
                uptimeRates,
                "设备",
                "可用率(%)"
        );
    }

    private List<EquipmentStatVO> convertToDailyStatVO(List<Map<String, Object>> dataList) {
        return dataList.stream().map(m -> {
            EquipmentStatVO vo = new EquipmentStatVO();
            vo.setStatDate((LocalDate) m.get("stat_date"));
            vo.setTestCount(getIntValue(m, "test_count"));
            vo.setTestItemCount(getIntValue(m, "test_item_count"));
            vo.setQcCount(getIntValue(m, "qc_count"));
            vo.setQcPassCount(getIntValue(m, "qc_pass_count"));
            vo.setQcFailCount(getIntValue(m, "qc_fail_count"));
            vo.setQcPassRate(getBigDecimalValue(m, "qc_pass_rate"));
            vo.setFaultCount(getIntValue(m, "fault_count"));
            vo.setUptimeRate(getBigDecimalValue(m, "uptime_rate"));
            return vo;
        }).collect(Collectors.toList());
    }

    private List<EquipmentStatVO> convertToEquipmentStatVO(List<Map<String, Object>> dataList) {
        return dataList.stream().map(m -> {
            EquipmentStatVO vo = new EquipmentStatVO();
            vo.setEquipmentId(getLongValue(m, "equipment_id"));
            vo.setEquipmentCode((String) m.get("equipment_code"));
            vo.setEquipmentName((String) m.get("equipment_name"));
            vo.setTestCount(getIntValue(m, "test_count"));
            vo.setTestItemCount(getIntValue(m, "test_item_count"));
            vo.setQcCount(getIntValue(m, "qc_count"));
            vo.setQcPassRate(getBigDecimalValue(m, "qc_pass_rate"));
            vo.setFaultCount(getIntValue(m, "fault_count"));
            vo.setUptimeRate(getBigDecimalValue(m, "uptime_rate"));
            return vo;
        }).collect(Collectors.toList());
    }

    private List<EquipmentStatVO> convertToTrendVO(List<Map<String, Object>> dataList) {
        return dataList.stream().map(m -> {
            EquipmentStatVO vo = new EquipmentStatVO();
            vo.setStatDate((LocalDate) m.get("stat_date"));
            vo.setEquipmentId(getLongValue(m, "equipment_id"));
            vo.setEquipmentName((String) m.get("equipment_name"));
            vo.setTestCount(getIntValue(m, "test_count"));
            vo.setQcPassRate(getBigDecimalValue(m, "qc_pass_rate"));
            vo.setUptimeRate(getBigDecimalValue(m, "uptime_rate"));
            return vo;
        }).collect(Collectors.toList());
    }

    private EChartsVO.Series createSeries(String name, String type, List<Object> data) {
        EChartsVO.Series series = new EChartsVO.Series();
        series.setName(name);
        series.setType(type);
        series.setData(data);
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
