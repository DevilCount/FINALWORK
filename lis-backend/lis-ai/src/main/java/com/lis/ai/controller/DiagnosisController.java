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

@Api(tags = "AI诊断管理")
@RestController
@RequestMapping("/diagnosis")
@RequiredArgsConstructor
public class DiagnosisController {

    private final DiagnosisRecordService diagnosisRecordService;

    @ApiOperation("执行诊断分析")
    @PostMapping("/analyze")
    public Result<DiagnosisResultVO> analyze(@Validated @RequestBody DiagnosisRequestDTO request) {
        DiagnosisResultVO result = diagnosisRecordService.performDiagnosis(request);
        return Result.success("诊断完成", result);
    }

    @ApiOperation("获取诊断记录详情")
    @GetMapping("/record/{id}")
    public Result<DiagnosisRecordVO> getRecord(
            @ApiParam(value = "诊断记录ID", required = true) @PathVariable Long id) {
        DiagnosisRecordVO record = diagnosisRecordService.getDiagnosisRecord(id);
        return Result.success(record);
    }

    @ApiOperation("根据诊断编号获取记录")
    @GetMapping("/record/no/{diagnosisNo}")
    public Result<DiagnosisRecordVO> getRecordByNo(
            @ApiParam(value = "诊断编号", required = true) @PathVariable String diagnosisNo) {
        DiagnosisRecordVO record = diagnosisRecordService.getDiagnosisRecordByNo(diagnosisNo);
        return Result.success(record);
    }

    @ApiOperation("查询诊断记录列表")
    @GetMapping("/records")
    public Result<PageResult<DiagnosisRecordVO>> queryRecords(DiagnosisRecordQueryDTO queryDTO) {
        PageResult<DiagnosisRecordVO> result = diagnosisRecordService.queryDiagnosisRecords(queryDTO);
        return Result.success(result);
    }

    @ApiOperation("审核诊断记录")
    @PostMapping("/review")
    public Result<Void> review(@Validated @RequestBody DiagnosisReviewDTO reviewDTO,
                                @RequestHeader(value = "X-User-Id", required = false) Long userId,
                                @RequestHeader(value = "X-User-Name", required = false) String userName) {
        diagnosisRecordService.reviewDiagnosis(reviewDTO, userId, userName);
        return Result.success("审核完成", null);
    }

    @ApiOperation("删除诊断记录")
    @DeleteMapping("/record/{id}")
    public Result<Void> deleteRecord(
            @ApiParam(value = "诊断记录ID", required = true) @PathVariable Long id) {
        diagnosisRecordService.deleteDiagnosisRecord(id);
        return Result.success("删除成功", null);
    }
}
