package com.lis.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.result.PageResult;
import com.lis.user.dto.OperLogQueryDTO;
import com.lis.user.entity.OperLogDO;
import com.lis.user.mapper.OperLogMapper;
import com.lis.user.service.OperLogService;
import com.lis.user.vo.OperLogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperLogServiceImpl implements OperLogService {

    private final OperLogMapper operLogMapper;

    @Override
    public void save(OperLogDO operLogDO) {
        operLogMapper.insert(operLogDO);
    }

    @Override
    public PageResult<OperLogVO> queryList(OperLogQueryDTO dto) {
        LambdaQueryWrapper<OperLogDO> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(dto.getTitle())) {
            wrapper.like(OperLogDO::getTitle, dto.getTitle());
        }
        if (dto.getBusinessType() != null) {
            wrapper.eq(OperLogDO::getBusinessType, dto.getBusinessType());
        }
        if (StringUtils.hasText(dto.getOperName())) {
            wrapper.like(OperLogDO::getOperName, dto.getOperName());
        }
        if (dto.getBeginTime() != null) {
            wrapper.ge(OperLogDO::getOperTime, dto.getBeginTime());
        }
        if (dto.getEndTime() != null) {
            wrapper.le(OperLogDO::getOperTime, dto.getEndTime());
        }
        wrapper.orderByDesc(OperLogDO::getOperTime);

        Page<OperLogDO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<OperLogDO> result = operLogMapper.selectPage(page, wrapper);

        List<OperLogVO> list = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), dto.getPageNum(), dto.getPageSize(), list);
    }

    private OperLogVO convertToVO(OperLogDO operLogDO) {
        OperLogVO vo = new OperLogVO();
        BeanUtil.copyProperties(operLogDO, vo);
        return vo;
    }
}
