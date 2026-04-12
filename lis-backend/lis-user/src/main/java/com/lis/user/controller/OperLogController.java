package com.lis.user.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.user.dto.OperLogQueryDTO;
import com.lis.user.service.OperLogService;
import com.lis.user.vo.OperLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "操作日志管理")
@RestController
@RequestMapping("/oper-log")
@RequiredArgsConstructor
public class OperLogController {

    private final OperLogService operLogService;

    @ApiOperation("分页查询操作日志")
    @PostMapping("/list")
    public Result<PageResult<OperLogVO>> queryList(@RequestBody OperLogQueryDTO dto) {
        PageResult<OperLogVO> result = operLogService.queryList(dto);
        return Result.success(result);
    }

    @ApiOperation("保存操作日志")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody com.lis.user.entity.OperLogDO operLogDO) {
        operLogService.save(operLogDO);
        return Result.success(null);
    }
}
