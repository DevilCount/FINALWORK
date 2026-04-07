package com.lis.equipment.service;

import com.lis.common.result.PageResult;
import com.lis.equipment.dto.EquipmentDTO;
import com.lis.equipment.dto.EquipmentQueryDTO;
import com.lis.equipment.vo.EquipmentVO;

public interface EquipmentService {

    PageResult<EquipmentVO> pageList(EquipmentQueryDTO queryDTO);

    EquipmentVO getById(Long id);

    EquipmentVO getByCode(String equipmentCode);

    Long save(EquipmentDTO dto);

    void update(EquipmentDTO dto);

    void deleteById(Long id);

    void deleteBatch(Long[] ids);

    void updateStatus(Long id, String status);
}
