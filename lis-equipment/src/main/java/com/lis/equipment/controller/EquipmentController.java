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

@Api(tags = "设备台账管理")
@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @ApiOperation("分页查询设备列表")
    @GetMapping("/page")
    public Result<PageResult<EquipmentVO>> pageList(EquipmentQueryDTO queryDTO) {
        PageResult<EquipmentVO> result = equipmentService.pageList(queryDTO);
        return Result.success(result);
    }

    @ApiOperation("根据ID查询设备")
    @GetMapping("/{id}")
    public Result<EquipmentVO> getById(@PathVariable Long id) {
        EquipmentVO equipment = equipmentService.getById(id);
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
    public Result<Long> save(@Validated @RequestBody EquipmentDTO dto) {
        Long id = equipmentService.save(dto);
        return Result.success("新增成功", id);
    }

    @ApiOperation("更新设备")
    @PutMapping
    public Result<Void> update(@Validated @RequestBody EquipmentDTO dto) {
        equipmentService.update(dto);
        return Result.success("更新成功", null);
    }

    @ApiOperation("删除设备")
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        equipmentService.deleteById(id);
        return Result.success("删除成功", null);
    }

    @ApiOperation("批量删除设备")
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody Long[] ids) {
        equipmentService.deleteBatch(ids);
        return Result.success("批量删除成功", null);
    }

    @ApiOperation("更新设备状态")
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(
            @PathVariable Long id,
            @ApiParam(value = "设备状态", required = true) @RequestParam String status) {
        equipmentService.updateStatus(id, status);
        return Result.success("状态更新成功", null);
    }
}
