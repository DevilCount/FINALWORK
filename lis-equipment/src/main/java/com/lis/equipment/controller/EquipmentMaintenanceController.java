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

import java.util.List;
import java.util.Map;

@Api(tags = "设备维护记录管理")
@RestController
@RequestMapping("/equipment/maintenance")
@RequiredArgsConstructor
public class EquipmentMaintenanceController {

    private final EquipmentMaintenanceService maintenanceService;

    @ApiOperation("分页查询维护记录")
    @GetMapping("/list")
    public Result<PageResult<EquipmentMaintenanceVO>> pageList(EquipmentMaintenanceQueryDTO queryDTO) {
        PageResult<EquipmentMaintenanceVO> result = maintenanceService.pageList(queryDTO);
        return Result.success(result);
    }

    @ApiOperation("根据ID查询维护记录")
    @GetMapping("/{id}")
    public Result<EquipmentMaintenanceVO> getById(@PathVariable String id) {
        EquipmentMaintenanceVO maintenance = maintenanceService.getById(Long.parseLong(id));
        return Result.success(maintenance);
    }

    @ApiOperation("新增维护记录")
    @PostMapping
    public Result<EquipmentMaintenanceVO> save(@Validated @RequestBody EquipmentMaintenanceDTO dto) {
        Long id = maintenanceService.save(dto);
        return Result.success(maintenanceService.getById(id));
    }

    @ApiOperation("更新维护记录")
    @PutMapping("/{id}")
    public Result<EquipmentMaintenanceVO> update(@PathVariable String id, @Validated @RequestBody EquipmentMaintenanceDTO dto) {
        maintenanceService.update(dto);
        return Result.success(maintenanceService.getById(Long.parseLong(id)));
    }

    @ApiOperation("删除维护记录")
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable String id) {
        maintenanceService.deleteById(Long.parseLong(id));
        return Result.success("删除成功", null);
    }

    @ApiOperation("批量删除维护记录")
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody Map<String, List<String>> request) {
        Long[] ids = request.get("ids").stream().map(Long::parseLong).toArray(Long[]::new);
        maintenanceService.deleteBatch(ids);
        return Result.success("批量删除成功", null);
    }

    @ApiOperation("完成维护")
    @PutMapping("/{id}/complete")
    public Result<Void> completeMaintenance(@PathVariable String id) {
        maintenanceService.completeMaintenance(Long.parseLong(id));
        return Result.success("维护完成", null);
    }
}
