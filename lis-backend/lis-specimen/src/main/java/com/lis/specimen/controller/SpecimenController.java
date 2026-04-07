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

@Api(tags = "标本管理")
@RestController
@RequestMapping("/specimen")
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
    @GetMapping("/getBySpecimenNo")
    public Result<SpecimenDetailVO> getBySpecimenNo(@RequestParam String specimenNo) {
        SpecimenDetailVO vo = specimenService.getBySpecimenNo(specimenNo);
        return Result.success(vo);
    }

    @ApiOperation("根据ID查询标本")
    @GetMapping("/getById/{id}")
    public Result<SpecimenDetailVO> getById(@PathVariable Long id) {
        SpecimenDetailVO vo = specimenService.getById(id);
        return Result.success(vo);
    }

    @ApiOperation("分页查询标本列表")
    @PostMapping("/list")
    public Result<PageResult<SpecimenListVO>> queryList(@RequestBody SpecimenQueryDTO dto) {
        PageResult<SpecimenListVO> result = specimenService.queryList(dto);
        return Result.success(result);
    }

    @ApiOperation("标本签收")
    @PostMapping("/receive")
    public Result<Void> receive(@Validated @RequestBody SpecimenReceiveDTO dto) {
        specimenService.receive(dto);
        return Result.success("签收成功", null);
    }

    @ApiOperation("标本入库")
    @PostMapping("/storage")
    public Result<Void> storage(@Validated @RequestBody SpecimenStorageDTO dto) {
        specimenService.storage(dto);
        return Result.success("入库成功", null);
    }

    @ApiOperation("更新标本状态")
    @PostMapping("/updateStatus")
    public Result<Void> updateStatus(@Validated @RequestBody SpecimenStatusDTO dto) {
        specimenService.updateStatus(dto);
        return Result.success("状态更新成功", null);
    }

    @ApiOperation("标本拒收")
    @PostMapping("/reject")
    public Result<Void> reject(@Validated @RequestBody SpecimenReceiveDTO dto) {
        specimenService.reject(dto);
        return Result.success("拒收成功", null);
    }

    @ApiOperation("获取标本追溯记录")
    @GetMapping("/trace/{specimenId}")
    public Result<List<SpecimenTraceVO>> getTraceList(@PathVariable Long specimenId) {
        List<SpecimenTraceVO> list = specimenService.getTraceList(specimenId);
        return Result.success(list);
    }

    @ApiOperation("根据标本编号获取追溯记录")
    @GetMapping("/traceByNo")
    public Result<List<SpecimenTraceVO>> getTraceListByNo(@RequestParam String specimenNo) {
        List<SpecimenTraceVO> list = specimenService.getTraceListByNo(specimenNo);
        return Result.success(list);
    }

    @ApiOperation("获取标本统计信息")
    @PostMapping("/statistics")
    public Result<SpecimenStatisticsVO> getStatistics(@RequestBody SpecimenStatisticsDTO dto) {
        SpecimenStatisticsVO vo = specimenService.getStatistics(dto);
        return Result.success(vo);
    }

    @ApiOperation("附加检验项目")
    @PostMapping("/addition")
    public Result<Void> addition(@Validated @RequestBody SpecimenAdditionDTO dto) {
        specimenService.addition(dto);
        return Result.success("附加成功", null);
    }

    @ApiOperation("打印条码标记")
    @PostMapping("/printBarcode/{specimenId}")
    public Result<Void> printBarcode(@PathVariable Long specimenId) {
        specimenService.printBarcode(specimenId);
        return Result.success("打印标记成功", null);
    }

    @ApiOperation("获取标本类型列表")
    @GetMapping("/types")
    public Result<List<SpecimenTypeVO>> listSpecimenTypes() {
        List<SpecimenTypeVO> list = specimenService.listSpecimenTypes();
        return Result.success(list);
    }

    @ApiOperation("获取检验项目列表")
    @GetMapping("/testItems")
    public Result<List<TestItemVO>> listTestItems() {
        List<TestItemVO> list = specimenService.listTestItems();
        return Result.success(list);
    }
}
