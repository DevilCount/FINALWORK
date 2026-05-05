package com.lis.ai.controller;

import com.lis.ai.dto.DiagnosisRequestDTO;
import com.lis.ai.dto.DiagnosisRecordQueryDTO;
import com.lis.ai.dto.DiagnosisReviewDTO;
import com.lis.ai.service.DiagnosisRecordService;
import com.lis.ai.vo.DiagnosisRecordVO;
import com.lis.ai.vo.DiagnosisResultVO;
import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "AI分析管理")
@RestController
@RequestMapping("/ai/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final DiagnosisRecordService diagnosisRecordService;

    @ApiOperation("执行诊断分析")
    @PostMapping
    public Result<DiagnosisRecordVO> analyze(@Validated @RequestBody Map<String, String> request) {
        DiagnosisRequestDTO dto = new DiagnosisRequestDTO();
        dto.setReportId(request.get("reportId"));
        DiagnosisResultVO result = diagnosisRecordService.performDiagnosis(dto);
        return Result.success("分析完成", null);
    }

    @ApiOperation("获取分析记录详情")
    @GetMapping("/{id}")
    public Result<DiagnosisRecordVO> getRecord(
            @ApiParam(value = "分析记录ID", required = true) @PathVariable String id) {
        DiagnosisRecordVO record = diagnosisRecordService.getDiagnosisRecord(Long.parseLong(id));
        return Result.success(record);
    }

    @ApiOperation("查询分析记录列表")
    @GetMapping
    public Result<PageResult<DiagnosisRecordVO>> queryRecords(DiagnosisRecordQueryDTO queryDTO) {
        PageResult<DiagnosisRecordVO> result = diagnosisRecordService.queryDiagnosisRecords(queryDTO);
        return Result.success(result);
    }

    @ApiOperation("审核分析记录")
    @PostMapping("/review")
    public Result<Void> review(@Validated @RequestBody DiagnosisReviewDTO reviewDTO,
                                @RequestHeader(value = "X-User-Id", required = false) Long userId,
                                @RequestHeader(value = "X-User-Name", required = false) String userName) {
        diagnosisRecordService.reviewDiagnosis(reviewDTO, userId, userName);
        return Result.success("审核完成", null);
    }

    @ApiOperation("删除分析记录")
    @DeleteMapping("/{id}")
    public Result<Void> deleteRecord(
            @ApiParam(value = "分析记录ID", required = true) @PathVariable String id) {
        diagnosisRecordService.deleteDiagnosisRecord(Long.parseLong(id));
        return Result.success("删除成功", null);
    }

    @ApiOperation("取消分析")
    @PutMapping("/{id}/cancel")
    public Result<Void> cancelAIAnalysis(@PathVariable String id) {
        return Result.success("取消成功", null);
    }

    @ApiOperation("获取待分析报告")
    @GetMapping("/reports")
    public Result<PageResult<Map<String, Object>>> getReportsForAnalysis(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String reportType,
            @RequestParam(required = false) String status) {
        return Result.success(new PageResult<>());
    }
}
