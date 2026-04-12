package com.lis.statistics.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.statistics.dto.EquipmentQueryDTO;
import com.lis.statistics.service.EquipmentStatService;
import com.lis.statistics.vo.EChartsVO;
import com.lis.statistics.vo.EquipmentStatVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor
@Api(tags = "设备统计")
public class EquipmentStatController {

    private final EquipmentStatService equipmentStatService;

    @GetMapping("/daily")
    @ApiOperation("获取每日设备统计")
    public Result<List<EquipmentStatVO>> getDailyStat(EquipmentQueryDTO queryDTO) {
        return Result.success(equipmentStatService.getDailyStat(queryDTO));
    }

    @GetMapping("/page")
    @ApiOperation("分页获取设备统计")
    public Result<PageResult<EquipmentStatVO>> getEquipmentStatPage(EquipmentQueryDTO queryDTO) {
        return Result.success(equipmentStatService.getEquipmentStatPage(queryDTO));
    }

    @GetMapping("/trend")
    @ApiOperation("获取设备趋势统计")
    public Result<List<EquipmentStatVO>> getEquipmentTrend(EquipmentQueryDTO queryDTO) {
        return Result.success(equipmentStatService.getEquipmentTrend(queryDTO));
    }

    @GetMapping("/chart/test")
    @ApiOperation("获取设备检验量图表")
    public Result<EChartsVO> getEquipmentTestChart(EquipmentQueryDTO queryDTO) {
        return Result.success(equipmentStatService.getEquipmentTestChart(queryDTO));
    }

    @GetMapping("/chart/qc")
    @ApiOperation("获取设备质控图表")
    public Result<EChartsVO> getEquipmentQcChart(EquipmentQueryDTO queryDTO) {
        return Result.success(equipmentStatService.getEquipmentQcChart(queryDTO));
    }

    @GetMapping("/chart/uptime")
    @ApiOperation("获取设备可用率图表")
    public Result<EChartsVO> getEquipmentUptimeChart(EquipmentQueryDTO queryDTO) {
        return Result.success(equipmentStatService.getEquipmentUptimeChart(queryDTO));
    }
}
