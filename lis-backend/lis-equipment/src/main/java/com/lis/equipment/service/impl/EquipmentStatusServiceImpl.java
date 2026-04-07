package com.lis.equipment.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lis.equipment.entity.EquipmentDO;
import com.lis.equipment.enums.EquipmentStatusEnum;
import com.lis.equipment.mapper.EquipmentMapper;
import com.lis.equipment.service.EquipmentStatusService;
import com.lis.equipment.vo.EquipmentStatusVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipmentStatusServiceImpl implements EquipmentStatusService {

    private final EquipmentMapper equipmentMapper;

    private static final long OFFLINE_THRESHOLD_MINUTES = 5;

    @Override
    public List<EquipmentStatusVO> getAllEquipmentStatus() {
        LambdaQueryWrapper<EquipmentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(EquipmentDO::getId, EquipmentDO::getEquipmentCode, EquipmentDO::getEquipmentName,
                EquipmentDO::getStatus, EquipmentDO::getIsOnline, EquipmentDO::getIpAddress,
                EquipmentDO::getPort, EquipmentDO::getLastCommTime);

        List<EquipmentDO> equipmentList = equipmentMapper.selectList(wrapper);
        return equipmentList.stream()
                .map(this::convertToStatusVO)
                .collect(Collectors.toList());
    }

    @Override
    public EquipmentStatusVO getEquipmentStatus(Long equipmentId) {
        EquipmentDO equipmentDO = equipmentMapper.selectById(equipmentId);
        if (equipmentDO == null) {
            return null;
        }
        return convertToStatusVO(equipmentDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOnlineStatus(Long equipmentId, Integer isOnline) {
        EquipmentDO equipmentDO = new EquipmentDO();
        equipmentDO.setId(equipmentId);
        equipmentDO.setIsOnline(isOnline);
        equipmentDO.setUpdateTime(LocalDateTime.now());

        if (isOnline == 1) {
            equipmentDO.setLastCommTime(LocalDateTime.now());
        }

        equipmentMapper.updateById(equipmentDO);
        log.info("更新设备在线状态: equipmentId={}, isOnline={}", equipmentId, isOnline);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLastCommTime(Long equipmentId) {
        EquipmentDO equipmentDO = new EquipmentDO();
        equipmentDO.setId(equipmentId);
        equipmentDO.setLastCommTime(LocalDateTime.now());
        equipmentDO.setIsOnline(1);
        equipmentDO.setUpdateTime(LocalDateTime.now());

        equipmentMapper.updateById(equipmentDO);
        log.debug("更新设备最后通信时间: equipmentId={}", equipmentId);
    }

    private EquipmentStatusVO convertToStatusVO(EquipmentDO equipmentDO) {
        EquipmentStatusVO vo = new EquipmentStatusVO();
        vo.setId(equipmentDO.getId());
        vo.setEquipmentCode(equipmentDO.getEquipmentCode());
        vo.setEquipmentName(equipmentDO.getEquipmentName());
        vo.setStatus(equipmentDO.getStatus());
        vo.setIsOnline(equipmentDO.getIsOnline());
        vo.setIpAddress(equipmentDO.getIpAddress());
        vo.setPort(equipmentDO.getPort());

        EquipmentStatusEnum statusEnum = EquipmentStatusEnum.getByCode(equipmentDO.getStatus());
        if (statusEnum != null) {
            vo.setStatusDesc(statusEnum.getDesc());
        }

        if (equipmentDO.getLastCommTime() != null) {
            vo.setLastCommTime(DateUtil.format(equipmentDO.getLastCommTime(), "yyyy-MM-dd HH:mm:ss"));

            long minutes = ChronoUnit.MINUTES.between(equipmentDO.getLastCommTime(), LocalDateTime.now());
            if (minutes > OFFLINE_THRESHOLD_MINUTES) {
                vo.setCommStatus("offline");
                vo.setCommStatusDesc("离线");
                if (equipmentDO.getIsOnline() == 1) {
                    updateOnlineStatus(equipmentDO.getId(), 0);
                }
            } else {
                vo.setCommStatus("online");
                vo.setCommStatusDesc("在线");
            }
        } else {
            vo.setLastCommTime("-");
            vo.setCommStatus("unknown");
            vo.setCommStatusDesc("未知");
        }

        return vo;
    }
}
