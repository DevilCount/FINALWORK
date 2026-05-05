package com.lis.ai.controller;

import com.lis.ai.dto.DiagnosisRuleDTO;
import com.lis.ai.service.DiagnosisRuleService;
import com.lis.ai.vo.DiagnosisRuleVO;
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

@Api(tags = "AI规则管理")
@RestController
@RequestMapping("/ai/rules")
@RequiredArgsConstructor
public class RulesController {

    private final DiagnosisRuleService ruleService;

    @ApiOperation("创建规则")
    @PostMapping
    public Result<DiagnosisRuleVO> createRule(@Validated @RequestBody DiagnosisRuleDTO dto) {
        Long ruleId = ruleService.createRule(dto);
        return Result.success(ruleService.getRule(ruleId));
    }

    @ApiOperation("更新规则")
    @PutMapping("/{id}")
    public Result<DiagnosisRuleVO> updateRule(@PathVariable String id, @Validated @RequestBody DiagnosisRuleDTO dto) {
        ruleService.updateRule(dto);
        return Result.success(ruleService.getRule(Long.parseLong(id)));
    }

    @ApiOperation("删除规则")
    @DeleteMapping("/{id}")
    public Result<Void> deleteRule(
            @ApiParam(value = "规则ID", required = true) @PathVariable String id) {
        ruleService.deleteRule(Long.parseLong(id));
        return Result.success("删除成功", null);
    }

    @ApiOperation("获取规则详情")
    @GetMapping("/{id}")
    public Result<DiagnosisRuleVO> getRule(
            @ApiParam(value = "规则ID", required = true) @PathVariable String id) {
        DiagnosisRuleVO rule = ruleService.getRule(Long.parseLong(id));
        return Result.success(rule);
    }

    @ApiOperation("分页查询规则")
    @GetMapping
    public Result<PageResult<DiagnosisRuleVO>> queryRules(
            @ApiParam(value = "规则名称") @RequestParam(required = false) String keyword,
            @ApiParam(value = "规则分类") @RequestParam(required = false) String category,
            @ApiParam(value = "是否启用") @RequestParam(required = false) Boolean isActive,
            @ApiParam(value = "页码") @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(value = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer enabled = isActive != null ? (isActive ? 1 : 0) : null;
        PageResult<DiagnosisRuleVO> result = ruleService.queryRules(keyword, category, enabled, page, pageSize);
        return Result.success(result);
    }

    @ApiOperation("切换规则状态")
    @PutMapping("/{id}/toggle")
    public Result<DiagnosisRuleVO> toggleAIRule(@PathVariable String id, @RequestBody Map<String, Boolean> request) {
        Boolean isActive = request.get("isActive");
        if (isActive) {
            ruleService.enableRule(Long.parseLong(id));
        } else {
            ruleService.disableRule(Long.parseLong(id));
        }
        return Result.success(ruleService.getRule(Long.parseLong(id)));
    }

    @ApiOperation("测试规则")
    @PostMapping("/{id}/test")
    public Result<Map<String, Object>> testAIRule(@PathVariable String id, @RequestBody Map<String, Object> testData) {
        return Result.success(Map.of("matched", false, "result", Map.of()));
    }

    @ApiOperation("获取规则分类")
    @GetMapping("/categories")
    public Result<List<Map<String, String>>> getAIRuleCategories() {
        return Result.success(List.of());
    }
}
