package com.lis.user.service;

import com.lis.common.result.PageResult;
import com.lis.user.dto.OperLogQueryDTO;
import com.lis.user.entity.OperLogDO;
import com.lis.user.vo.OperLogVO;

public interface OperLogService {

    void save(OperLogDO operLogDO);

    PageResult<OperLogVO> queryList(OperLogQueryDTO dto);
}
