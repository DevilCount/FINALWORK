package com.lis.equipment.service;

import com.lis.common.result.PageResult;
import com.lis.equipment.dto.EquipmentCalibrationDTO;
import com.lis.equipment.dto.EquipmentCalibrationQueryDTO;
import com.lis.equipment.vo.EquipmentCalibrationVO;

public interface EquipmentCalibrationService {

    PageResult<EquipmentCalibrationVO> pageList(EquipmentCalibrationQueryDTO queryDTO);

    EquipmentCalibrationVO getById(Long id);

    Long save(EquipmentCalibrationDTO dto);

    void update(EquipmentCalibrationDTO dto);

    void deleteById(Long id);

    void deleteBatch(Long[] ids);

    void completeCalibration(Long id);
}
