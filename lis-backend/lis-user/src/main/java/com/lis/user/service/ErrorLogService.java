package com.lis.user.service;

import com.lis.common.result.PageResult;
import com.lis.user.dto.ErrorLogQueryDTO;
import com.lis.user.entity.ErrorLogDO;
import com.lis.user.vo.ErrorLogVO;

public interface ErrorLogService {

    void save(ErrorLogDO errorLogDO);

    PageResult<ErrorLogVO> queryList(ErrorLogQueryDTO dto);
}
