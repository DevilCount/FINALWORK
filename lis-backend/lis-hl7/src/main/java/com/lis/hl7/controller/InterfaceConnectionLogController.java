package com.lis.hl7.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.hl7.entity.InterfaceConnectionLogDO;
import com.lis.hl7.service.InterfaceConnectionLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "接口连接日志管理")
@RestController
@RequestMapping("/connection-log")
@RequiredArgsConstructor
public class InterfaceConnectionLogController {

    private final InterfaceConnectionLogService connectionLogService;

    @ApiOperation("分页查询接口连接日志")
    @PostMapping("/list")
    public Result<PageResult<InterfaceConnectionLogDO>> queryList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String interfaceCode,
            @RequestParam(required = false) String eventType) {
        LambdaQueryWrapper<InterfaceConnectionLogDO> wrapper = new LambdaQueryWrapper<>();
        if (interfaceCode != null && !interfaceCode.isEmpty()) {
            wrapper.eq(InterfaceConnectionLogDO::getInterfaceCode, interfaceCode);
        }
        if (eventType != null && !eventType.isEmpty()) {
            wrapper.eq(InterfaceConnectionLogDO::getEventType, eventType);
        }
        wrapper.orderByDesc(InterfaceConnectionLogDO::getEventTime);

        Page<InterfaceConnectionLogDO> page = new Page<>(pageNum, pageSize);
        IPage<InterfaceConnectionLogDO> result = connectionLogService.page(page, wrapper);

        return Result.success(PageResult.of(result.getTotal(), pageNum, pageSize, result.getRecords()));
    }

    @ApiOperation("根据接口编码查询连接日志")
    @GetMapping("/byCode/{interfaceCode}")
    public Result<List<InterfaceConnectionLogDO>> getByInterfaceCode(@PathVariable String interfaceCode) {
        LambdaQueryWrapper<InterfaceConnectionLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterfaceConnectionLogDO::getInterfaceCode, interfaceCode);
        wrapper.orderByDesc(InterfaceConnectionLogDO::getEventTime);
        List<InterfaceConnectionLogDO> list = connectionLogService.list(wrapper);
        return Result.success(list);
    }
}
