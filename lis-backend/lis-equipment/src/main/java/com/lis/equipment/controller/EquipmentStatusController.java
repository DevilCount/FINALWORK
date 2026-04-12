package com.lis.equipment.controller;

import com.lis.common.result.Result;
import com.lis.equipment.service.EquipmentStatusService;
import com.lis.equipment.vo.EquipmentStatusVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "设备状态监控")
@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class EquipmentStatusController {

    private final EquipmentStatusService equipmentStatusService;

    @ApiOperation("获取所有设备状态")
    @GetMapping("/all")
    public Result<List<EquipmentStatusVO>> getAllEquipmentStatus() {
        List<EquipmentStatusVO> statusList = equipmentStatusService.getAllEquipmentStatus();
        return Result.success(statusList);
    }

    @ApiOperation("获取单个设备状态")
    @GetMapping("/{equipmentId}")
    public Result<EquipmentStatusVO> getEquipmentStatus(@PathVariable Long equipmentId) {
        EquipmentStatusVO status = equipmentStatusService.getEquipmentStatus(equipmentId);
        return Result.success(status);
    }

    @ApiOperation("更新设备在线状态")
    @PutMapping("/{equipmentId}/online")
    public Result<Void> updateOnlineStatus(
            @PathVariable Long equipmentId,
            @RequestParam Integer isOnline) {
        equipmentStatusService.updateOnlineStatus(equipmentId, isOnline);
        return Result.success("更新成功", null);
    }

    @ApiOperation("更新设备通信时间")
    @PutMapping("/{equipmentId}/comm")
    public Result<Void> updateLastCommTime(@PathVariable Long equipmentId) {
        equipmentStatusService.updateLastCommTime(equipmentId);
        return Result.success("更新成功", null);
    }
}
