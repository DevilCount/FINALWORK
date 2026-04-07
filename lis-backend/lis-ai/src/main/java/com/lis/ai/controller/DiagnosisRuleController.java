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

@Api(tags = "诊断规则管理")
@RestController
@RequestMapping("/rule")
@RequiredArgsConstructor
public class DiagnosisRuleController {

    private final DiagnosisRuleService ruleService;

    @ApiOperation("创建诊断规则")
    @PostMapping
    public Result<Long> createRule(@Validated @RequestBody DiagnosisRuleDTO dto) {
        Long ruleId = ruleService.createRule(dto);
        return Result.success("创建成功", ruleId);
    }

    @ApiOperation("更新诊断规则")
    @PutMapping
    public Result<Void> updateRule(@Validated @RequestBody DiagnosisRuleDTO dto) {
        ruleService.updateRule(dto);
        return Result.success("更新成功", null);
    }

    @ApiOperation("删除诊断规则")
    @DeleteMapping("/{id}")
    public Result<Void> deleteRule(
            @ApiParam(value = "规则ID", required = true) @PathVariable Long id) {
        ruleService.deleteRule(id);
        return Result.success("删除成功", null);
    }

    @ApiOperation("获取规则详情")
    @GetMapping("/{id}")
    public Result<DiagnosisRuleVO> getRule(
            @ApiParam(value = "规则ID", required = true) @PathVariable Long id) {
        DiagnosisRuleVO rule = ruleService.getRule(id);
        return Result.success(rule);
    }

    @ApiOperation("根据规则编码获取详情")
    @GetMapping("/code/{ruleCode}")
    public Result<DiagnosisRuleVO> getRuleByCode(
            @ApiParam(value = "规则编码", required = true) @PathVariable String ruleCode) {
        DiagnosisRuleVO rule = ruleService.getRuleByCode(ruleCode);
        return Result.success(rule);
    }

    @ApiOperation("根据分类查询规则列表")
    @GetMapping("/category/{category}")
    public Result<List<DiagnosisRuleVO>> listByCategory(
            @ApiParam(value = "规则分类", required = true) @PathVariable String category) {
        List<DiagnosisRuleVO> rules = ruleService.listRulesByCategory(category);
        return Result.success(rules);
    }

    @ApiOperation("获取所有启用的规则")
    @GetMapping("/enabled")
    public Result<List<DiagnosisRuleVO>> listEnabledRules() {
        List<DiagnosisRuleVO> rules = ruleService.listEnabledRules();
        return Result.success(rules);
    }

    @ApiOperation("分页查询规则")
    @GetMapping("/page")
    public Result<PageResult<DiagnosisRuleVO>> queryRules(
            @ApiParam(value = "规则名称") @RequestParam(required = false) String ruleName,
            @ApiParam(value = "规则分类") @RequestParam(required = false) String category,
            @ApiParam(value = "是否启用") @RequestParam(required = false) Integer isEnabled,
            @ApiParam(value = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult<DiagnosisRuleVO> result = ruleService.queryRules(ruleName, category, isEnabled, pageNum, pageSize);
        return Result.success(result);
    }

    @ApiOperation("启用规则")
    @PutMapping("/{id}/enable")
    public Result<Void> enableRule(
            @ApiParam(value = "规则ID", required = true) @PathVariable Long id) {
        ruleService.enableRule(id);
        return Result.success("启用成功", null);
    }

    @ApiOperation("停用规则")
    @PutMapping("/{id}/disable")
    public Result<Void> disableRule(
            @ApiParam(value = "规则ID", required = true) @PathVariable Long id) {
        ruleService.disableRule(id);
        return Result.success("停用成功", null);
    }
}
