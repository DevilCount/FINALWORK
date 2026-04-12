package com.lis.user.service;

import com.lis.common.result.PageResult;
import com.lis.user.dto.LoginLogQueryDTO;
import com.lis.user.entity.LoginLogDO;
import com.lis.user.vo.LoginLogVO;

public interface LoginLogService {

    void save(LoginLogDO loginLogDO);

    PageResult<LoginLogVO> queryList(LoginLogQueryDTO dto);
}
