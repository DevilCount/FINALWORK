package com.lis.statistics.service;

import com.lis.common.result.PageResult;
import com.lis.statistics.dto.EquipmentQueryDTO;
import com.lis.statistics.vo.EChartsVO;
import com.lis.statistics.vo.EquipmentStatVO;

import java.util.List;

public interface EquipmentStatService {

    List<EquipmentStatVO> getDailyStat(EquipmentQueryDTO queryDTO);

    PageResult<EquipmentStatVO> getEquipmentStatPage(EquipmentQueryDTO queryDTO);

    List<EquipmentStatVO> getEquipmentTrend(EquipmentQueryDTO queryDTO);

    EChartsVO getEquipmentTestChart(EquipmentQueryDTO queryDTO);

    EChartsVO getEquipmentQcChart(EquipmentQueryDTO queryDTO);

    EChartsVO getEquipmentUptimeChart(EquipmentQueryDTO queryDTO);
}
