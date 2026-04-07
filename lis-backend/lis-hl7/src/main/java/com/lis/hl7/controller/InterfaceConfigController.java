package com.lis.hl7.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.hl7.dto.InterfaceConfigDTO;
import com.lis.hl7.dto.InterfaceConfigQueryDTO;
import com.lis.hl7.service.InterfaceConfigService;
import com.lis.hl7.vo.InterfaceConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "接口配置管理")
@RestController
@RequestMapping("/interface-config")
@RequiredArgsConstructor
public class InterfaceConfigController {

    private final InterfaceConfigService interfaceConfigService;

    @ApiOperation("分页查询接口配置")
    @GetMapping("/page")
    public Result<PageResult<InterfaceConfigVO>> queryPage(InterfaceConfigQueryDTO queryDTO) {
        Page<InterfaceConfigVO> page = interfaceConfigService.queryPage(queryDTO);
        return Result.success(PageResult.of(page.getRecords(), page.getTotal()));
    }

    @ApiOperation("获取接口配置详情")
    @GetMapping("/{id}")
    public Result<InterfaceConfigVO> getDetail(@PathVariable Long id) {
        InterfaceConfigVO vo = interfaceConfigService.getDetail(id);
        return Result.success(vo);
    }

    @ApiOperation("创建接口配置")
    @PostMapping
    public Result<Long> create(@Valid @RequestBody InterfaceConfigDTO dto) {
        Long id = interfaceConfigService.create(dto);
        return Result.success(id);
    }

    @ApiOperation("更新接口配置")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody InterfaceConfigDTO dto) {
        interfaceConfigService.update(dto);
        return Result.success();
    }

    @ApiOperation("删除接口配置")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        interfaceConfigService.delete(id);
        return Result.success();
    }

    @ApiOperation("测试接口连接")
    @PostMapping("/{id}/test")
    public Result<Boolean> testConnection(@PathVariable Long id) {
        boolean result = interfaceConfigService.testConnection(id);
        return Result.success(result);
    }

    @ApiOperation("启动接口")
    @PostMapping("/{id}/start")
    public Result<Void> startInterface(@PathVariable Long id) {
        interfaceConfigService.startInterface(id);
        return Result.success();
    }

    @ApiOperation("停止接口")
    @PostMapping("/{id}/stop")
    public Result<Void> stopInterface(@PathVariable Long id) {
        interfaceConfigService.stopInterface(id);
        return Result.success();
    }

    @ApiOperation("重启接口")
    @PostMapping("/{id}/restart")
    public Result<Void> restartInterface(@PathVariable Long id) {
        interfaceConfigService.restartInterface(id);
        return Result.success();
    }

    @ApiOperation("启用接口")
    @PutMapping("/{id}/enable")
    public Result<Void> enable(@PathVariable Long id) {
        InterfaceConfigDTO dto = new InterfaceConfigDTO();
        dto.setId(id);
        dto.setIsEnabled(1);
        interfaceConfigService.update(dto);
        return Result.success();
    }

    @ApiOperation("禁用接口")
    @PutMapping("/{id}/disable")
    public Result<Void> disable(@PathVariable Long id) {
        InterfaceConfigDTO dto = new InterfaceConfigDTO();
        dto.setId(id);
        dto.setIsEnabled(0);
        interfaceConfigService.update(dto);
        return Result.success();
    }
}
