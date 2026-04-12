package com.lis.user.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.user.dto.AuditLogQueryDTO;
import com.lis.user.service.AuditLogService;
import com.lis.user.vo.AuditLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "审计日志管理")
@RestController
@RequestMapping("/audit-log")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @ApiOperation("分页查询审计日志")
    @PostMapping("/list")
    public Result<PageResult<AuditLogVO>> queryList(@RequestBody AuditLogQueryDTO dto) {
        PageResult<AuditLogVO> result = auditLogService.queryList(dto);
        return Result.success(result);
    }

    @ApiOperation("保存审计日志")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody com.lis.user.entity.AuditLogDO auditLogDO) {
        auditLogService.save(auditLogDO);
        return Result.success(null);
    }
}
