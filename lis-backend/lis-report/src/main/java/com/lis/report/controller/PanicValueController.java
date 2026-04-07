package com.lis.report.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.report.dto.PanicValueHandleDTO;
import com.lis.report.dto.PanicValueQueryDTO;
import com.lis.report.service.PanicValueService;
import com.lis.report.vo.PanicValueVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "危急值管理")
@RestController
@RequestMapping("/panic")
@RequiredArgsConstructor
public class PanicValueController {

    private final PanicValueService panicValueService;

    @ApiOperation("通知危急值")
    @PostMapping("/notify/{panicValueId}")
    public Result<Void> notifyPanicValue(
            @PathVariable Long panicValueId,
            @RequestParam String notifyMethod,
            @RequestParam String receiveUserName) {
        panicValueService.notifyPanicValue(panicValueId, notifyMethod, receiveUserName);
        return Result.success("通知成功", null);
    }

    @ApiOperation("处理危急值")
    @PostMapping("/handle")
    public Result<Void> handlePanicValue(@Validated @RequestBody PanicValueHandleDTO dto) {
        panicValueService.handlePanicValue(dto);
        return Result.success("处理成功", null);
    }

    @ApiOperation("确认危急值")
    @PostMapping("/confirm/{panicValueId}")
    public Result<Void> confirmPanicValue(@PathVariable Long panicValueId) {
        panicValueService.confirmPanicValue(panicValueId);
        return Result.success("确认成功", null);
    }

    @ApiOperation("获取危急值详情")
    @GetMapping("/{panicValueId}")
    public Result<PanicValueVO> getPanicValueById(@PathVariable Long panicValueId) {
        PanicValueVO panicValue = panicValueService.getPanicValueById(panicValueId);
        return Result.success(panicValue);
    }

    @ApiOperation("分页查询危急值")
    @PostMapping("/query")
    public Result<PageResult<PanicValueVO>> queryPanicValues(@RequestBody PanicValueQueryDTO dto) {
        PageResult<PanicValueVO> result = panicValueService.queryPanicValues(dto);
        return Result.success(result);
    }
}
