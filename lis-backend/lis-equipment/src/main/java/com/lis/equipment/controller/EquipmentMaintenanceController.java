package com.lis.equipment.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.equipment.dto.EquipmentMaintenanceDTO;
import com.lis.equipment.dto.EquipmentMaintenanceQueryDTO;
import com.lis.equipment.service.EquipmentMaintenanceService;
import com.lis.equipment.vo.EquipmentMaintenanceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "设备维护记录管理")
@RestController
@RequestMapping("/equipment/maintenance")
@RequiredArgsConstructor
public class EquipmentMaintenanceController {

    private final EquipmentMaintenanceService maintenanceService;

    @ApiOperation("分页查询维护记录")
    @GetMapping("/page")
    public Result<PageResult<EquipmentMaintenanceVO>> pageList(EquipmentMaintenanceQueryDTO queryDTO) {
        PageResult<EquipmentMaintenanceVO> result = maintenanceService.pageList(queryDTO);
        return Result.success(result);
    }

    @ApiOperation("根据ID查询维护记录")
    @GetMapping("/{id}")
    public Result<EquipmentMaintenanceVO> getById(@PathVariable Long id) {
        EquipmentMaintenanceVO maintenance = maintenanceService.getById(id);
        return Result.success(maintenance);
    }

    @ApiOperation("新增维护记录")
    @PostMapping
    public Result<Long> save(@Validated @RequestBody EquipmentMaintenanceDTO dto) {
        Long id = maintenanceService.save(dto);
        return Result.success("新增成功", id);
    }

    @ApiOperation("更新维护记录")
    @PutMapping
    public Result<Void> update(@Validated @RequestBody EquipmentMaintenanceDTO dto) {
        maintenanceService.update(dto);
        return Result.success("更新成功", null);
    }

    @ApiOperation("删除维护记录")
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        maintenanceService.deleteById(id);
        return Result.success("删除成功", null);
    }

    @ApiOperation("批量删除维护记录")
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody Long[] ids) {
        maintenanceService.deleteBatch(ids);
        return Result.success("批量删除成功", null);
    }

    @ApiOperation("完成维护")
    @PutMapping("/{id}/complete")
    public Result<Void> completeMaintenance(@PathVariable Long id) {
        maintenanceService.completeMaintenance(id);
        return Result.success("维护完成", null);
    }
}
