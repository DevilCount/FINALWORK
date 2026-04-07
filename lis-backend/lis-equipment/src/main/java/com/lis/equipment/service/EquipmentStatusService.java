package com.lis.equipment.service;

import com.lis.equipment.vo.EquipmentStatusVO;

import java.util.List;

public interface EquipmentStatusService {

    List<EquipmentStatusVO> getAllEquipmentStatus();

    EquipmentStatusVO getEquipmentStatus(Long equipmentId);

    void updateOnlineStatus(Long equipmentId, Integer isOnline);

    void updateLastCommTime(Long equipmentId);
}
