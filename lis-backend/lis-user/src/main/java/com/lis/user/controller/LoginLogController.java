package com.lis.user.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.user.dto.LoginLogQueryDTO;
import com.lis.user.service.LoginLogService;
import com.lis.user.vo.LoginLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登录日志管理")
@RestController
@RequestMapping("/login-log")
@RequiredArgsConstructor
public class LoginLogController {

    private final LoginLogService loginLogService;

    @ApiOperation("分页查询登录日志")
    @PostMapping("/list")
    public Result<PageResult<LoginLogVO>> queryList(@RequestBody LoginLogQueryDTO dto) {
        PageResult<LoginLogVO> result = loginLogService.queryList(dto);
        return Result.success(result);
    }

    @ApiOperation("保存登录日志")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody com.lis.user.entity.LoginLogDO loginLogDO) {
        loginLogService.save(loginLogDO);
        return Result.success(null);
    }
}
