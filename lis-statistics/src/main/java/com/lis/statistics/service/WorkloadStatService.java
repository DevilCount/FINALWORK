package com.lis.statistics.service;

import com.lis.common.result.PageResult;
import com.lis.statistics.dto.WorkloadQueryDTO;
import com.lis.statistics.vo.EChartsVO;
import com.lis.statistics.vo.WorkloadStatVO;

import java.util.List;

public interface WorkloadStatService {

    List<WorkloadStatVO> getDailyWorkload(WorkloadQueryDTO queryDTO);

    PageResult<WorkloadStatVO> getUserWorkloadPage(WorkloadQueryDTO queryDTO);

    List<WorkloadStatVO> getDeptWorkload(WorkloadQueryDTO queryDTO);

    EChartsVO getWorkloadTrendChart(WorkloadQueryDTO queryDTO);

    EChartsVO getWorkloadDistributionChart(WorkloadQueryDTO queryDTO);
}
