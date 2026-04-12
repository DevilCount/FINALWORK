package com.lis.user.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.user.dto.ErrorLogQueryDTO;
import com.lis.user.service.ErrorLogService;
import com.lis.user.vo.ErrorLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "错误日志管理")
@RestController
@RequestMapping("/error-log")
@RequiredArgsConstructor
public class ErrorLogController {

    private final ErrorLogService errorLogService;

    @ApiOperation("分页查询错误日志")
    @PostMapping("/list")
    public Result<PageResult<ErrorLogVO>> queryList(@RequestBody ErrorLogQueryDTO dto) {
        PageResult<ErrorLogVO> result = errorLogService.queryList(dto);
        return Result.success(result);
    }

    @ApiOperation("保存错误日志")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody com.lis.user.entity.ErrorLogDO errorLogDO) {
        errorLogService.save(errorLogDO);
        return Result.success(null);
    }
}
