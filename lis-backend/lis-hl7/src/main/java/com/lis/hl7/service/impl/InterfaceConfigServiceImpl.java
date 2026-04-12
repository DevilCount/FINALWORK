package com.lis.hl7.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lis.common.exception.BusinessException;
import com.lis.common.result.ResultCode;
import com.lis.hl7.dto.InterfaceConfigDTO;
import com.lis.hl7.dto.InterfaceConfigQueryDTO;
import com.lis.hl7.entity.InterfaceConfigDO;
import com.lis.hl7.mapper.InterfaceConfigMapper;
import com.lis.hl7.service.InterfaceConfigService;
import com.lis.hl7.vo.InterfaceConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InterfaceConfigServiceImpl extends ServiceImpl<InterfaceConfigMapper, InterfaceConfigDO> implements InterfaceConfigService {

    @Override
    public InterfaceConfigDO getByCode(String interfaceCode) {
        return getOne(new LambdaQueryWrapper<InterfaceConfigDO>()
                .eq(InterfaceConfigDO::getInterfaceCode, interfaceCode));
    }

    @Override
    public Page<InterfaceConfigVO> queryPage(InterfaceConfigQueryDTO queryDTO) {
        Page<InterfaceConfigDO> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());

        LambdaQueryWrapper<InterfaceConfigDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(queryDTO.getInterfaceCode()), InterfaceConfigDO::getInterfaceCode, queryDTO.getInterfaceCode())
                .like(StringUtils.hasText(queryDTO.getInterfaceName()), InterfaceConfigDO::getInterfaceName, queryDTO.getInterfaceName())
                .eq(StringUtils.hasText(queryDTO.getInterfaceType()), InterfaceConfigDO::getInterfaceType, queryDTO.getInterfaceType())
                .eq(StringUtils.hasText(queryDTO.getDirection()), InterfaceConfigDO::getDirection, queryDTO.getDirection())
                .eq(StringUtils.hasText(queryDTO.getProtocol()), InterfaceConfigDO::getProtocol, queryDTO.getProtocol())
                .eq(StringUtils.hasText(queryDTO.getStatus()), InterfaceConfigDO::getStatus, queryDTO.getStatus())
                .eq(queryDTO.getIsEnabled() != null, InterfaceConfigDO::getIsEnabled, queryDTO.getIsEnabled())
                .orderByDesc(InterfaceConfigDO::getCreateTime);

        Page<InterfaceConfigDO> result = page(page, wrapper);

        Page<InterfaceConfigVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<InterfaceConfigVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public InterfaceConfigVO getDetail(Long id) {
        InterfaceConfigDO config = getById(id);
        if (config == null) {
            return null;
        }
        return convertToVO(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(InterfaceConfigDTO dto) {
        InterfaceConfigDO config = new InterfaceConfigDO();
        BeanUtils.copyProperties(dto, config);
        config.setStatus("stopped");
        config.setTotalReceived(0L);
        config.setTotalSent(0L);
        config.setTotalError(0L);
        config.setCreateTime(LocalDateTime.now());

        save(config);
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(InterfaceConfigDTO dto) {
        InterfaceConfigDO config = getById(dto.getId());
        if (config == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "接口配置不存在: " + dto.getId());
        }

        BeanUtils.copyProperties(dto, config, "id", "status", "totalReceived", "totalSent", "totalError", "createTime");
        config.setUpdateTime(LocalDateTime.now());
        updateById(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        InterfaceConfigDO config = getById(id);
        if (config == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "接口配置不存在");
        }
        if ("running".equals(config.getStatus())) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "不能删除运行中的接口");
        }
        removeById(id);
    }

    @Override
    public void updateStatus(Long id, String status) {
        InterfaceConfigDO config = new InterfaceConfigDO();
        config.setId(id);
        config.setStatus(status);

        if ("running".equals(status)) {
            config.setLastStartTime(LocalDateTime.now());
        } else if ("stopped".equals(status)) {
            config.setLastStopTime(LocalDateTime.now());
        }

        updateById(config);
    }

    @Override
    public void incrementReceivedCount(Long id) {
        InterfaceConfigDO config = getById(id);
        if (config != null) {
            config.setTotalReceived(config.getTotalReceived() != null ? config.getTotalReceived() + 1 : 1);
            updateById(config);
        }
    }

    @Override
    public void incrementSentCount(Long id) {
        InterfaceConfigDO config = getById(id);
        if (config != null) {
            config.setTotalSent(config.getTotalSent() != null ? config.getTotalSent() + 1 : 1);
            updateById(config);
        }
    }

    @Override
    public void incrementErrorCount(Long id, String errorMsg) {
        InterfaceConfigDO config = getById(id);
        if (config != null) {
            config.setTotalError(config.getTotalError() != null ? config.getTotalError() + 1 : 1);
            config.setLastErrorTime(LocalDateTime.now());
            config.setLastErrorMsg(errorMsg);
            updateById(config);
        }
    }

    @Override
    public boolean testConnection(Long id) {
        InterfaceConfigDO config = getById(id);
        if (config == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "接口配置不存在");
        }

        // 简单连接测试：检查host和port是否配置
        if (config.getHost() == null || config.getHost().isBlank() || config.getPort() == null) {
            return false;
        }
        return true;
    }

    @Override
    public void startInterface(Long id) {
        InterfaceConfigDO config = getById(id);
        if (config == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "接口配置不存在");
        }

        if ("running".equals(config.getStatus())) {
            return;
        }

        updateStatus(id, "running");
        log.info("启动接口配置: id={}, code={}", id, config.getInterfaceCode());
    }

    @Override
    public void stopInterface(Long id) {
        InterfaceConfigDO config = getById(id);
        if (config == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "接口配置不存在");
        }

        updateStatus(id, "stopped");
        log.info("停止接口配置: id={}, code={}", id, config.getInterfaceCode());
    }

    @Override
    public void restartInterface(Long id) {
        InterfaceConfigDO config = getById(id);
        if (config == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "接口配置不存在");
        }

        updateStatus(id, "running");
        log.info("重启接口配置: id={}, code={}", id, config.getInterfaceCode());
    }

    private InterfaceConfigVO convertToVO(InterfaceConfigDO config) {
        InterfaceConfigVO vo = new InterfaceConfigVO();
        BeanUtils.copyProperties(config, vo);
        return vo;
    }
}
