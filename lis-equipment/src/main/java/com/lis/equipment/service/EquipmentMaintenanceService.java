package com.lis.equipment.service;

import com.lis.common.result.PageResult;
import com.lis.equipment.dto.EquipmentMaintenanceDTO;
import com.lis.equipment.dto.EquipmentMaintenanceQueryDTO;
import com.lis.equipment.vo.EquipmentMaintenanceVO;

public interface EquipmentMaintenanceService {

    PageResult<EquipmentMaintenanceVO> pageList(EquipmentMaintenanceQueryDTO queryDTO);

    EquipmentMaintenanceVO getById(Long id);

    Long save(EquipmentMaintenanceDTO dto);

    void update(EquipmentMaintenanceDTO dto);

    void deleteById(Long id);

    void deleteBatch(Long[] ids);

    void completeMaintenance(Long id);
}
