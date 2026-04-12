package com.lis.user.service;

import com.lis.common.result.PageResult;
import com.lis.user.dto.AuditLogQueryDTO;
import com.lis.user.entity.AuditLogDO;
import com.lis.user.vo.AuditLogVO;

public interface AuditLogService {

    void save(AuditLogDO auditLogDO);

    PageResult<AuditLogVO> queryList(AuditLogQueryDTO dto);
}
