package com.lis.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.result.PageResult;
import com.lis.user.dto.LoginLogQueryDTO;
import com.lis.user.entity.LoginLogDO;
import com.lis.user.mapper.LoginLogMapper;
import com.lis.user.service.LoginLogService;
import com.lis.user.vo.LoginLogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginLogServiceImpl implements LoginLogService {

    private final LoginLogMapper loginLogMapper;

    @Override
    public void save(LoginLogDO loginLogDO) {
        loginLogMapper.insert(loginLogDO);
    }

    @Override
    public PageResult<LoginLogVO> queryList(LoginLogQueryDTO dto) {
        LambdaQueryWrapper<LoginLogDO> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(dto.getUserName())) {
            wrapper.like(LoginLogDO::getUserName, dto.getUserName());
        }
        if (dto.getStatus() != null) {
            wrapper.eq(LoginLogDO::getStatus, dto.getStatus());
        }
        if (dto.getBeginTime() != null) {
            wrapper.ge(LoginLogDO::getLoginTime, dto.getBeginTime());
        }
        if (dto.getEndTime() != null) {
            wrapper.le(LoginLogDO::getLoginTime, dto.getEndTime());
        }
        wrapper.orderByDesc(LoginLogDO::getLoginTime);

        Page<LoginLogDO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<LoginLogDO> result = loginLogMapper.selectPage(page, wrapper);

        List<LoginLogVO> list = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), dto.getPageNum(), dto.getPageSize(), list);
    }

    private LoginLogVO convertToVO(LoginLogDO loginLogDO) {
        LoginLogVO vo = new LoginLogVO();
        BeanUtil.copyProperties(loginLogDO, vo);
        return vo;
    }
}
