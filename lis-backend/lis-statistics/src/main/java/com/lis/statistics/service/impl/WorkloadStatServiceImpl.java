package com.lis.statistics.service.impl;

import com.lis.common.result.PageResult;
import com.lis.statistics.dto.WorkloadQueryDTO;
import com.lis.statistics.mapper.StatWorkloadDailyMapper;
import com.lis.statistics.service.WorkloadStatService;
import com.lis.statistics.util.EChartsConverter;
import com.lis.statistics.vo.EChartsVO;
import com.lis.statistics.vo.WorkloadStatVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkloadStatServiceImpl implements WorkloadStatService {

    private final StatWorkloadDailyMapper workloadDailyMapper;

    @Override
    public List<WorkloadStatVO> getDailyWorkload(WorkloadQueryDTO queryDTO) {
        fillDefaultDateRange(queryDTO);
        List<Map<String, Object>> dataList = workloadDailyMapper.selectDailyWorkload(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getDeptId()
        );
        return convertToDailyWorkloadVO(dataList);
    }

    @Override
    public PageResult<WorkloadStatVO> getUserWorkloadPage(WorkloadQueryDTO queryDTO) {
        fillDefaultDateRange(queryDTO);
        List<Map<String, Object>> dataList = workloadDailyMapper.selectUserWorkload(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getDeptId(),
                queryDTO.getUserId()
        );

        List<WorkloadStatVO> voList = convertToUserWorkloadVO(dataList);

        int total = voList.size();
        int start = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();
        int end = Math.min(start + queryDTO.getPageSize(), total);

        List<WorkloadStatVO> pageData = start < total ? voList.subList(start, end) : new ArrayList<>();

        return new PageResult<>((long) total, queryDTO.getPageNum(), queryDTO.getPageSize(), pageData);
    }

    @Override
    public List<WorkloadStatVO> getDeptWorkload(WorkloadQueryDTO queryDTO) {
        fillDefaultDateRange(queryDTO);
        List<Map<String, Object>> dataList = workloadDailyMapper.selectDeptWorkload(
                queryDTO.getStartDate(),
                queryDTO.getEndDate()
        );
        return convertToDeptWorkloadVO(dataList);
    }

    @Override
    public EChartsVO getWorkloadTrendChart(WorkloadQueryDTO queryDTO) {
        fillDefaultDateRange(queryDTO);
        List<Map<String, Object>> dataList = workloadDailyMapper.selectDailyWorkload(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getDeptId()
        );

        List<String> dates = dataList.stream()
                .map(m -> ((LocalDate) m.get("stat_date")).format(DateTimeFormatter.ofPattern("MM-dd")))
                .collect(Collectors.toList());

        List<Object> specimenReceiveData = dataList.stream()
                .map(m -> m.get("specimen_receive_count"))
                .collect(Collectors.toList());

        List<Object> testData = dataList.stream()
                .map(m -> m.get("test_count"))
                .collect(Collectors.toList());

        List<Object> auditData = dataList.stream()
                .map(m -> m.get("audit_count"))
                .collect(Collectors.toList());

        List<Object> reportData = dataList.stream()
                .map(m -> m.get("report_count"))
                .collect(Collectors.toList());

        EChartsVO chart = new EChartsVO();
        chart.setTitle("工作量趋势图");
        chart.setSubtitle(queryDTO.getStartDate() + " 至 " + queryDTO.getEndDate());

        EChartsVO.Legend legend = new EChartsVO.Legend();
        legend.setData(List.of("标本接收", "检验", "审核", "报告"));
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
        seriesList.add(createSeries("标本接收", "line", specimenReceiveData));
        seriesList.add(createSeries("检验", "line", testData));
        seriesList.add(createSeries("审核", "line", auditData));
        seriesList.add(createSeries("报告", "line", reportData));
        chart.setSeries(seriesList);

        return chart;
    }

    @Override
    public EChartsVO getWorkloadDistributionChart(WorkloadQueryDTO queryDTO) {
        fillDefaultDateRange(queryDTO);
        List<Map<String, Object>> dataList = workloadDailyMapper.selectUserWorkload(
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getDeptId(),
                queryDTO.getUserId()
        );

        List<String> userNames = dataList.stream()
                .map(m -> (String) m.get("user_name"))
                .limit(10)
                .collect(Collectors.toList());

        List<Object> workloads = dataList.stream()
                .map(m -> m.get("total_workload"))
                .limit(10)
                .collect(Collectors.toList());

        return EChartsConverter.createBarChart(
                "工作量分布图",
                "人员工作量排行",
                userNames,
                workloads,
                "人员",
                "工作量"
        );
    }

    private void fillDefaultDateRange(WorkloadQueryDTO queryDTO) {
        if (queryDTO.getStartDate() == null) {
            queryDTO.setStartDate(LocalDate.now().minusDays(30));
        }
        if (queryDTO.getEndDate() == null) {
            queryDTO.setEndDate(LocalDate.now());
        }
    }

    private List<WorkloadStatVO> convertToDailyWorkloadVO(List<Map<String, Object>> dataList) {
        return dataList.stream().map(m -> {
            WorkloadStatVO vo = new WorkloadStatVO();
            vo.setStatDate((LocalDate) m.get("stat_date"));
            vo.setSpecimenReceiveCount(getIntValue(m, "specimen_receive_count"));
            vo.setTestCount(getIntValue(m, "test_count"));
            vo.setAuditCount(getIntValue(m, "audit_count"));
            vo.setReportCount(getIntValue(m, "report_count"));
            vo.setWorkHours(getBigDecimalValue(m, "work_hours"));
            vo.setTotalWorkload(vo.getSpecimenReceiveCount() + vo.getTestCount() +
                    vo.getAuditCount() + vo.getReportCount());
            return vo;
        }).collect(Collectors.toList());
    }

    private List<WorkloadStatVO> convertToUserWorkloadVO(List<Map<String, Object>> dataList) {
        return dataList.stream().map(m -> {
            WorkloadStatVO vo = new WorkloadStatVO();
            vo.setUserId(getLongValue(m, "user_id"));
            vo.setUserName((String) m.get("user_name"));
            vo.setDeptId(getLongValue(m, "dept_id"));
            vo.setDeptName((String) m.get("dept_name"));
            vo.setSpecimenReceiveCount(getIntValue(m, "specimen_receive_count"));
            vo.setTestCount(getIntValue(m, "test_count"));
            vo.setAuditCount(getIntValue(m, "audit_count"));
            vo.setReportCount(getIntValue(m, "report_count"));
            vo.setTotalWorkload(getIntValue(m, "total_workload"));
            return vo;
        }).collect(Collectors.toList());
    }

    private List<WorkloadStatVO> convertToDeptWorkloadVO(List<Map<String, Object>> dataList) {
        return dataList.stream().map(m -> {
            WorkloadStatVO vo = new WorkloadStatVO();
            vo.setDeptId(getLongValue(m, "dept_id"));
            vo.setDeptName((String) m.get("dept_name"));
            vo.setSpecimenReceiveCount(getIntValue(m, "specimen_receive_count"));
            vo.setTestCount(getIntValue(m, "test_count"));
            vo.setAuditCount(getIntValue(m, "audit_count"));
            vo.setReportCount(getIntValue(m, "report_count"));
            vo.setTotalWorkload(vo.getSpecimenReceiveCount() + vo.getTestCount() +
                    vo.getAuditCount() + vo.getReportCount());
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
