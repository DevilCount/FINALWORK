package com.lis.specimen.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.specimen.dto.*;
import com.lis.specimen.service.SpecimenService;
import com.lis.specimen.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "标本管理")
@RestController
@RequestMapping("/api/specimen")
@RequiredArgsConstructor
public class SpecimenController {

    private final SpecimenService specimenService;

    @ApiOperation("标本登记")
    @PostMapping("/register")
    public Result<SpecimenDetailVO> register(@Validated @RequestBody SpecimenRegisterDTO dto) {
        SpecimenDetailVO vo = specimenService.register(dto);
        return Result.success("标本登记成功", vo);
    }

    @ApiOperation("根据条码查询标本")
    @GetMapping("/getByBarcode")
    public Result<SpecimenDetailVO> getByBarcode(@RequestParam String barcode) {
        SpecimenDetailVO vo = specimenService.getByBarcode(barcode);
        return Result.success(vo);
    }

    @ApiOperation("根据标本编号查询标本")
    @GetMapping("/code/{specimenCode}")
    public Result<SpecimenDetailVO> getBySpecimenNo(@PathVariable String specimenCode) {
        SpecimenDetailVO vo = specimenService.getBySpecimenNo(specimenCode);
        return Result.success(vo);
    }

    @ApiOperation("根据ID查询标本")
    @GetMapping("/detail/{id}")
    public Result<SpecimenDetailVO> getById(@PathVariable Long id) {
        SpecimenDetailVO vo = specimenService.getById(id);
        return Result.success(vo);
    }

    @ApiOperation("分页查询标本列表")
    @GetMapping("/list")
    public Result<PageResult<SpecimenListVO>> queryList(SpecimenQueryDTO dto) {
        PageResult<SpecimenListVO> result = specimenService.queryList(dto);
        return Result.success(result);
    }

    @ApiOperation("标本签收")
    @PostMapping("/receive/{id}")
    public Result<SpecimenDetailVO> receive(@PathVariable String id) {
        specimenService.receive(new SpecimenReceiveDTO());
        return Result.success(null);
    }

    @ApiOperation("标本采集")
    @PostMapping("/collect/{id}")
    public Result<SpecimenDetailVO> collect(@PathVariable String id) {
        return Result.success(null);
    }

    @ApiOperation("标本拒收")
    @PostMapping("/reject/{id}")
    public Result<SpecimenDetailVO> reject(@PathVariable String id, @RequestBody Map<String, String> request) {
        SpecimenReceiveDTO dto = new SpecimenReceiveDTO();
        dto.setRejectReason(request.get("reason"));
        specimenService.reject(dto);
        return Result.success(null);
    }

    @ApiOperation("获取标本追溯记录")
    @GetMapping("/trace/{specimenId}")
    public Result<List<SpecimenTraceVO>> getTraceList(@PathVariable String specimenId) {
        List<SpecimenTraceVO> list = specimenService.getTraceList(Long.parseLong(specimenId));
        return Result.success(list);
    }

    @ApiOperation("获取标本类型列表")
    @GetMapping("/types")
    public Result<List<SpecimenTypeVO>> listSpecimenTypes() {
        List<SpecimenTypeVO> list = specimenService.listSpecimenTypes();
        return Result.success(list);
    }

    @ApiOperation("获取检验项目列表")
    @GetMapping("/test-item/list")
    public Result<PageResult<TestItemVO>> listTestItems(TestItemQueryDTO dto) {
        return Result.success(new PageResult<>());
    }

    @ApiOperation("获取所有检验项目")
    @GetMapping("/test-item/all")
    public Result<List<TestItemVO>> getAllTestItems() {
        return Result.success(List.of());
    }

    @ApiOperation("获取检验项目分类")
    @GetMapping("/test-item/categories")
    public Result<List<Map<String, String>>> getTestItemCategories() {
        return Result.success(List.of());
    }

    @ApiOperation("获取部门列表")
    @GetMapping("/department/list")
    public Result<List<Map<String, String>>> getDepartments() {
        return Result.success(List.of());
    }

    @ApiOperation("打印标本标签")
    @GetMapping("/print-label/{id}")
    public Result<byte[]> printSpecimenLabel(@PathVariable String id) {
        return Result.success(new byte[0]);
    }

    @ApiOperation("批量采集")
    @PostMapping("/batch-collect")
    public Result<Map<String, Integer>> batchCollectSpecimen(@RequestBody Map<String, List<String>> request) {
        return Result.success(Map.of("success", 0, "failed", 0));
    }

    @ApiOperation("批量签收")
    @PostMapping("/batch-receive")
    public Result<Map<String, Integer>> batchReceiveSpecimen(@RequestBody Map<String, List<String>> request) {
        return Result.success(Map.of("success", 0, "failed", 0));
    }

    @ApiOperation("更新标本")
    @PutMapping("/update/{id}")
    public Result<SpecimenDetailVO> updateSpecimen(@PathVariable String id, @RequestBody SpecimenRegisterDTO dto) {
        return Result.success(null);
    }

    @ApiOperation("删除标本")
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteSpecimen(@PathVariable String id) {
        return Result.success("删除成功", null);
    }

    @ApiOperation("导出标本列表")
    @GetMapping("/export")
    public Result<byte[]> exportSpecimenList(SpecimenQueryDTO dto) {
        return Result.success(new byte[0]);
    }

    @ApiOperation("获取标本追溯记录列表")
    @GetMapping("/trace")
    public Result<List<SpecimenTraceVO>> getSpecimenTraceRecords(SpecimenTraceQueryDTO dto) {
        return Result.success(List.of());
    }
}
