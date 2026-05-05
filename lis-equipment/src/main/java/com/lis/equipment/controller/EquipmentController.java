package com.lis.equipment.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.equipment.dto.EquipmentDTO;
import com.lis.equipment.dto.EquipmentQueryDTO;
import com.lis.equipment.service.EquipmentService;
import com.lis.equipment.vo.EquipmentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "设备台账管理")
@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @ApiOperation("分页查询设备列表")
    @GetMapping("/list")
    public Result<PageResult<EquipmentVO>> pageList(EquipmentQueryDTO queryDTO) {
        PageResult<EquipmentVO> result = equipmentService.pageList(queryDTO);
        return Result.success(result);
    }

    @ApiOperation("根据ID查询设备")
    @GetMapping("/{id}")
    public Result<EquipmentVO> getById(@PathVariable String id) {
        EquipmentVO equipment = equipmentService.getById(Long.parseLong(id));
        return Result.success(equipment);
    }

    @ApiOperation("根据编码查询设备")
    @GetMapping("/code/{equipmentCode}")
    public Result<EquipmentVO> getByCode(@PathVariable String equipmentCode) {
        EquipmentVO equipment = equipmentService.getByCode(equipmentCode);
        return Result.success(equipment);
    }

    @ApiOperation("新增设备")
    @PostMapping
    public Result<EquipmentVO> save(@Validated @RequestBody EquipmentDTO dto) {
        Long id = equipmentService.save(dto);
        return Result.success(equipmentService.getById(id));
    }

    @ApiOperation("更新设备")
    @PutMapping("/{id}")
    public Result<EquipmentVO> update(@PathVariable String id, @Validated @RequestBody EquipmentDTO dto) {
        equipmentService.update(dto);
        return Result.success(equipmentService.getById(Long.parseLong(id)));
    }

    @ApiOperation("删除设备")
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable String id) {
        equipmentService.deleteById(Long.parseLong(id));
        return Result.success("删除成功", null);
    }

    @ApiOperation("批量删除设备")
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody Map<String, List<String>> request) {
        Long[] ids = request.get("ids").stream().map(Long::parseLong).toArray(Long[]::new);
        equipmentService.deleteBatch(ids);
        return Result.success("批量删除成功", null);
    }

    @ApiOperation("更新设备状态")
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> request) {
        equipmentService.updateStatus(Long.parseLong(id), request.get("status"));
        return Result.success("状态更新成功", null);
    }

    @ApiOperation("获取设备状态")
    @GetMapping("/status")
    public Result<List<Map<String, Object>>> getEquipmentStatus(@RequestParam(required = false) String equipmentId) {
        return Result.success(List.of());
    }

    @ApiOperation("获取设备告警")
    @GetMapping("/alerts")
    public Result<List<Map<String, Object>>> getEquipmentAlerts(@RequestParam(required = false) String equipmentId) {
        return Result.success(List.of());
    }

    @ApiOperation("标记告警已读")
    @PutMapping("/alerts/{alertId}/read")
    public Result<Void> markAlertAsRead(@PathVariable String alertId) {
        return Result.success("标记成功", null);
    }
}
