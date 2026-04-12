package com.lis.equipment.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.equipment.dto.EquipmentCalibrationDTO;
import com.lis.equipment.dto.EquipmentCalibrationQueryDTO;
import com.lis.equipment.service.EquipmentCalibrationService;
import com.lis.equipment.vo.EquipmentCalibrationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "设备校准记录管理")
@RestController
@RequestMapping("/calibration")
@RequiredArgsConstructor
public class EquipmentCalibrationController {

    private final EquipmentCalibrationService calibrationService;

    @ApiOperation("分页查询校准记录")
    @GetMapping("/page")
    public Result<PageResult<EquipmentCalibrationVO>> pageList(EquipmentCalibrationQueryDTO queryDTO) {
        PageResult<EquipmentCalibrationVO> result = calibrationService.pageList(queryDTO);
        return Result.success(result);
    }

    @ApiOperation("根据ID查询校准记录")
    @GetMapping("/{id}")
    public Result<EquipmentCalibrationVO> getById(@PathVariable Long id) {
        EquipmentCalibrationVO calibration = calibrationService.getById(id);
        return Result.success(calibration);
    }

    @ApiOperation("新增校准记录")
    @PostMapping
    public Result<Long> save(@Validated @RequestBody EquipmentCalibrationDTO dto) {
        Long id = calibrationService.save(dto);
        return Result.success("新增成功", id);
    }

    @ApiOperation("更新校准记录")
    @PutMapping
    public Result<Void> update(@Validated @RequestBody EquipmentCalibrationDTO dto) {
        calibrationService.update(dto);
        return Result.success("更新成功", null);
    }

    @ApiOperation("删除校准记录")
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        calibrationService.deleteById(id);
        return Result.success("删除成功", null);
    }

    @ApiOperation("批量删除校准记录")
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody Long[] ids) {
        calibrationService.deleteBatch(ids);
        return Result.success("批量删除成功", null);
    }

    @ApiOperation("完成校准")
    @PutMapping("/{id}/complete")
    public Result<Void> completeCalibration(@PathVariable Long id) {
        calibrationService.completeCalibration(id);
        return Result.success("校准完成", null);
    }
}
