package com.lis.hl7.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lis.hl7.dto.InterfaceConfigDTO;
import com.lis.hl7.dto.InterfaceConfigQueryDTO;
import com.lis.hl7.entity.InterfaceConfigDO;
import com.lis.hl7.his.HisMessageClient;
import com.lis.hl7.mapper.InterfaceConfigMapper;
import com.lis.hl7.mllp.MllpServerManager;
import com.lis.hl7.service.InterfaceConfigService;
import com.lis.hl7.vo.InterfaceConfigVO;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class InterfaceConfigServiceImpl extends ServiceImpl<InterfaceConfigMapper, InterfaceConfigDO> implements InterfaceConfigService {

    private final HisMessageClient hisMessageClient;
    private final MllpServerManager mllpServerManager;

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
            throw new IllegalArgumentException("Interface config not found: " + dto.getId());
        }

        BeanUtils.copyProperties(dto, config, "id", "status", "totalReceived", "totalSent", "totalError", "createTime");
        config.setUpdateTime(LocalDateTime.now());
        updateById(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        InterfaceConfigDO config = getById(id);
        if (config != null && "running".equals(config.getStatus())) {
            throw new IllegalStateException("Cannot delete running interface");
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
            return false;
        }

        return hisMessageClient.testConnection(config.getHost(), config.getPort(),
                config.getConnectionTimeout() != null ? config.getConnectionTimeout() : 5000);
    }

    @Override
    public void startInterface(Long id) {
        InterfaceConfigDO config = getById(id);
        if (config == null) {
            throw new IllegalArgumentException("Interface config not found: " + id);
        }

        if ("running".equals(config.getStatus())) {
            return;
        }

        if (!testConnection(id)) {
            updateStatus(id, "error");
            incrementErrorCount(id, "Connection test failed");
            throw new IllegalStateException("Connection test failed");
        }

        mllpServerManager.startServer(config.getInterfaceCode());
    }

    @Override
    public void stopInterface(Long id) {
        InterfaceConfigDO config = getById(id);
        if (config == null) {
            throw new IllegalArgumentException("Interface config not found: " + id);
        }

        mllpServerManager.stopServer(config.getInterfaceCode());
    }

    @Override
    public void restartInterface(Long id) {
        stopInterface(id);
        startInterface(id);
    }

    private InterfaceConfigVO convertToVO(InterfaceConfigDO config) {
        InterfaceConfigVO vo = new InterfaceConfigVO();
        BeanUtils.copyProperties(config, vo);
        return vo;
    }
}
