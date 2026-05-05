package com.lis.statistics.controller;

import com.lis.common.result.Result;
import com.lis.statistics.dto.WorkloadQueryDTO;
import com.lis.statistics.service.DashboardService;
import com.lis.statistics.vo.DashboardOverviewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/statistics/dashboard")
@RequiredArgsConstructor
@Api(tags = "仪表盘统计")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/overview")
    @ApiOperation("获取仪表盘概览数据")
    public Result<DashboardOverviewVO> getOverview() {
        return Result.success(dashboardService.getOverview());
    }

    @GetMapping("/overview/range")
    @ApiOperation("获取指定日期范围的概览数据")
    public Result<DashboardOverviewVO> getOverviewByDateRange(WorkloadQueryDTO queryDTO) {
        return Result.success(dashboardService.getOverviewByDateRange(queryDTO));
    }

    @GetMapping("/summary")
    @ApiOperation("获取仪表盘摘要数据")
    public Result<Map<String, Object>> getDashboardSummary() {
        return Result.success(Map.of(
            "totalEquipment", 0,
            "onlineEquipment", 0,
            "offlineEquipment", 0,
            "maintenanceEquipment", 0,
            "todaySamples", 0,
            "todayReports", 0,
            "pendingReports", 0,
            "criticalReports", 0,
            "avgTurnaroundTime", 0
        ));
    }
}
