package com.lis.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.result.PageResult;
import com.lis.user.dto.ErrorLogQueryDTO;
import com.lis.user.entity.ErrorLogDO;
import com.lis.user.mapper.ErrorLogMapper;
import com.lis.user.service.ErrorLogService;
import com.lis.user.vo.ErrorLogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErrorLogServiceImpl implements ErrorLogService {

    private final ErrorLogMapper errorLogMapper;

    @Override
    public void save(ErrorLogDO errorLogDO) {
        errorLogMapper.insert(errorLogDO);
    }

    @Override
    public PageResult<ErrorLogVO> queryList(ErrorLogQueryDTO dto) {
        LambdaQueryWrapper<ErrorLogDO> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(dto.getClassName())) {
            wrapper.like(ErrorLogDO::getErrorClass, dto.getClassName());
        }
        if (StringUtils.hasText(dto.getMethodName())) {
            wrapper.like(ErrorLogDO::getErrorMethod, dto.getMethodName());
        }
        if (dto.getBeginTime() != null) {
            wrapper.ge(ErrorLogDO::getErrorTime, dto.getBeginTime());
        }
        if (dto.getEndTime() != null) {
            wrapper.le(ErrorLogDO::getErrorTime, dto.getEndTime());
        }
        wrapper.orderByDesc(ErrorLogDO::getErrorTime);

        Page<ErrorLogDO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<ErrorLogDO> result = errorLogMapper.selectPage(page, wrapper);

        List<ErrorLogVO> list = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), dto.getPageNum(), dto.getPageSize(), list);
    }

    private ErrorLogVO convertToVO(ErrorLogDO errorLogDO) {
        ErrorLogVO vo = new ErrorLogVO();
        BeanUtil.copyProperties(errorLogDO, vo);
        return vo;
    }
}
