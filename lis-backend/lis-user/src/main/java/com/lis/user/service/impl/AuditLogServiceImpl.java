package com.lis.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.result.PageResult;
import com.lis.user.dto.AuditLogQueryDTO;
import com.lis.user.entity.AuditLogDO;
import com.lis.user.mapper.AuditLogMapper;
import com.lis.user.service.AuditLogService;
import com.lis.user.vo.AuditLogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogMapper auditLogMapper;

    @Override
    public void save(AuditLogDO auditLogDO) {
        auditLogMapper.insert(auditLogDO);
    }

    @Override
    public PageResult<AuditLogVO> queryList(AuditLogQueryDTO dto) {
        LambdaQueryWrapper<AuditLogDO> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(dto.getAuditType())) {
            wrapper.eq(AuditLogDO::getAuditType, dto.getAuditType());
        }
        if (StringUtils.hasText(dto.getAction())) {
            wrapper.eq(AuditLogDO::getAction, dto.getAction());
        }
        if (StringUtils.hasText(dto.getTargetType())) {
            wrapper.eq(AuditLogDO::getTargetType, dto.getTargetType());
        }
        if (StringUtils.hasText(dto.getAuditUserName())) {
            wrapper.like(AuditLogDO::getOperatorName, dto.getAuditUserName());
        }
        if (dto.getBeginTime() != null) {
            wrapper.ge(AuditLogDO::getAuditTime, dto.getBeginTime());
        }
        if (dto.getEndTime() != null) {
            wrapper.le(AuditLogDO::getAuditTime, dto.getEndTime());
        }
        wrapper.orderByDesc(AuditLogDO::getAuditTime);

        Page<AuditLogDO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<AuditLogDO> result = auditLogMapper.selectPage(page, wrapper);

        List<AuditLogVO> list = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), dto.getPageNum(), dto.getPageSize(), list);
    }

    private AuditLogVO convertToVO(AuditLogDO auditLogDO) {
        AuditLogVO vo = new AuditLogVO();
        BeanUtil.copyProperties(auditLogDO, vo);
        return vo;
    }
}
