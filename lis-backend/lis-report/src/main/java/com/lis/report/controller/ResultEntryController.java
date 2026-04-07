package com.lis.report.controller;

import com.lis.common.result.Result;
import com.lis.report.dto.BatchResultEntryDTO;
import com.lis.report.dto.ResultEntryDTO;
import com.lis.report.service.ResultEntryService;
import com.lis.report.vo.ReportItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "结果录入管理")
@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
public class ResultEntryController {

    private final ResultEntryService resultEntryService;

    @ApiOperation("录入结果")
    @PostMapping
    public Result<Long> entryResult(@Validated @RequestBody ResultEntryDTO dto) {
        Long itemId = resultEntryService.entryResult(dto);
        return Result.success("录入成功", itemId);
    }

    @ApiOperation("批量录入结果")
    @PostMapping("/batch")
    public Result<Void> batchEntryResult(@Validated @RequestBody BatchResultEntryDTO dto) {
        resultEntryService.batchEntryResult(dto);
        return Result.success("批量录入成功", null);
    }

    @ApiOperation("删除结果")
    @DeleteMapping("/{reportItemId}")
    public Result<Void> deleteResult(@PathVariable Long reportItemId) {
        resultEntryService.deleteResult(reportItemId);
        return Result.success("删除成功", null);
    }

    @ApiOperation("获取结果明细")
    @GetMapping("/{reportItemId}")
    public Result<ReportItemVO> getResultItemById(@PathVariable Long reportItemId) {
        ReportItemVO item = resultEntryService.getResultItemById(reportItemId);
        return Result.success(item);
    }
}
