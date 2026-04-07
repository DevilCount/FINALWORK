package com.lis.statistics.service.impl;

import com.lis.statistics.dto.EquipmentQueryDTO;
import com.lis.statistics.dto.ReportQueryDTO;
import com.lis.statistics.dto.SpecimenQueryDTO;
import com.lis.statistics.dto.WorkloadQueryDTO;
import com.lis.statistics.mapper.StatDeptDailyMapper;
import com.lis.statistics.mapper.StatEquipmentDailyMapper;
import com.lis.statistics.mapper.StatSpecimenDailyMapper;
import com.lis.statistics.mapper.StatTestItemDailyMapper;
import com.lis.statistics.mapper.StatWorkloadDailyMapper;
import com.lis.statistics.service.DashboardService;
import com.lis.statistics.vo.DashboardOverviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final StatSpecimenDailyMapper specimenDailyMapper;
    private final StatTestItemDailyMapper testItemDailyMapper;
    private final StatEquipmentDailyMapper equipmentDailyMapper;
    private final StatDeptDailyMapper deptDailyMapper;
    private final StatWorkloadDailyMapper workloadDailyMapper;

    @Override
    public DashboardOverviewVO getOverview() {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(6);

        DashboardOverviewVO overview = new DashboardOverviewVO();

        List<Map<String, Object>> todaySpecimen = specimenDailyMapper.selectDailyStat(today, today, null, null);
        overview.setTodaySpecimenCount(todaySpecimen.isEmpty() ? 0 : getIntValue(todaySpecimen.get(0), "total_count"));

        List<Map<String, Object>> todayReport = testItemDailyMapper.selectDailyStat(today, today, null);
        overview.setTodayReportCount(todayReport.isEmpty() ? 0 : getIntValue(todayReport.get(0), "total_count"));

        List<Map<String, Object>> todayPanic = testItemDailyMapper.selectDailyStat(today, today, null);
        overview.setTodayPanicCount(todayPanic.isEmpty() ? 0 : getIntValue(todayPanic.get(0), "panic_count"));

        List<Map<String, Object>> todayEquipment = equipmentDailyMapper.selectEquipmentStat(today, today, null);
        long onlineCount = todayEquipment.stream()
                .filter(m -> {
                    Object rate = m.get("uptime_rate");
                    if (rate == null) return false;
                    double uptimeRate = ((Number) rate).doubleValue();
                    return uptimeRate >= 90;
                })
                .count();
        overview.setTodayOnlineEquipment((int) onlineCount);

        List<Map<String, Object>> weeklySpecimen = specimenDailyMapper.selectDailyStat(weekStart, today, null, null);
        List<DashboardOverviewVO.TrendData> specimenTrend = new ArrayList<>();
        for (Map<String, Object> m : weeklySpecimen) {
            DashboardOverviewVO.TrendData data = new DashboardOverviewVO.TrendData();
            data.setDate(m.get("stat_date").toString());
            data.setCount(getIntValue(m, "total_count"));
            specimenTrend.add(data);
        }
        overview.setWeeklySpecimenTrend(specimenTrend);

        List<Map<String, Object>> weeklyReport = testItemDailyMapper.selectDailyStat(weekStart, today, null);
        List<DashboardOverviewVO.TrendData> reportTrend = new ArrayList<>();
        for (Map<String, Object> m : weeklyReport) {
            DashboardOverviewVO.TrendData data = new DashboardOverviewVO.TrendData();
            data.setDate(m.get("stat_date").toString());
            data.setCount(getIntValue(m, "total_count"));
            reportTrend.add(data);
        }
        overview.setWeeklyReportTrend(reportTrend);

        List<Map<String, Object>> deptSpecimen = specimenDailyMapper.selectDeptStat(weekStart, today, null);
        List<DashboardOverviewVO.DistributionData> deptDistribution = new ArrayList<>();
        for (int i = 0; i < Math.min(5, deptSpecimen.size()); i++) {
            Map<String, Object> m = deptSpecimen.get(i);
            DashboardOverviewVO.DistributionData data = new DashboardOverviewVO.DistributionData();
            data.setName((String) m.get("dept_name"));
            data.setValue(getIntValue(m, "total_count"));
            deptDistribution.add(data);
        }
        overview.setDeptSpecimenDistribution(deptDistribution);

        List<Map<String, Object>> equipmentUsage = equipmentDailyMapper.selectEquipmentStat(weekStart, today, null);
        List<DashboardOverviewVO.RankData> usageRank = new ArrayList<>();
        for (int i = 0; i < Math.min(5, equipmentUsage.size()); i++) {
            Map<String, Object> m = equipmentUsage.get(i);
            DashboardOverviewVO.RankData data = new DashboardOverviewVO.RankData();
            data.setName((String) m.get("equipment_name"));
            Object rate = m.get("uptime_rate");
            data.setValue(rate == null ? 0.0 : ((Number) rate).doubleValue());
            data.setRank(i + 1);
            usageRank.add(data);
        }
        overview.setEquipmentUsageRank(usageRank);

        return overview;
    }

    @Override
    public DashboardOverviewVO getOverviewByDateRange(WorkloadQueryDTO queryDTO) {
        DashboardOverviewVO overview = new DashboardOverviewVO();

        List<Map<String, Object>> specimen = specimenDailyMapper.selectDailyStat(
                queryDTO.getStartDate(), queryDTO.getEndDate(), queryDTO.getDeptId(), null);
        int totalSpecimen = specimen.stream()
                .mapToInt(m -> getIntValue(m, "total_count"))
                .sum();
        overview.setTodaySpecimenCount(totalSpecimen);

        List<Map<String, Object>> report = testItemDailyMapper.selectDailyStat(
                queryDTO.getStartDate(), queryDTO.getEndDate(), queryDTO.getDeptId());
        int totalReport = report.stream()
                .mapToInt(m -> getIntValue(m, "total_count"))
                .sum();
        overview.setTodayReportCount(totalReport);

        int totalPanic = report.stream()
                .mapToInt(m -> getIntValue(m, "panic_count"))
                .sum();
        overview.setTodayPanicCount(totalPanic);

        List<Map<String, Object>> equipment = equipmentDailyMapper.selectEquipmentStat(
                queryDTO.getStartDate(), queryDTO.getEndDate(), null);
        overview.setTodayOnlineEquipment(equipment.size());

        return overview;
    }

    private Integer getIntValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return 0;
        if (value instanceof Number) return ((Number) value).intValue();
        return Integer.parseInt(value.toString());
    }
}
