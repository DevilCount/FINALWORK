package com.lis.statistics.service;

import com.lis.common.result.PageResult;
import com.lis.statistics.dto.SpecimenQueryDTO;
import com.lis.statistics.vo.EChartsVO;
import com.lis.statistics.vo.SpecimenStatVO;

import java.util.List;

public interface SpecimenStatService {

    List<SpecimenStatVO> getDailyStat(SpecimenQueryDTO queryDTO);

    PageResult<SpecimenStatVO> getDeptStatPage(SpecimenQueryDTO queryDTO);

    List<SpecimenStatVO> getTypeStat(SpecimenQueryDTO queryDTO);

    EChartsVO getSpecimenTrendChart(SpecimenQueryDTO queryDTO);

    EChartsVO getSpecimenDistributionChart(SpecimenQueryDTO queryDTO);

    EChartsVO getSpecimenTypePieChart(SpecimenQueryDTO queryDTO);
}
