package com.lis.statistics.service;

import com.lis.statistics.dto.WorkloadQueryDTO;
import com.lis.statistics.dto.SpecimenQueryDTO;
import com.lis.statistics.dto.EquipmentQueryDTO;
import com.lis.statistics.dto.ReportQueryDTO;
import com.lis.statistics.vo.DashboardOverviewVO;

public interface DashboardService {

    DashboardOverviewVO getOverview();

    DashboardOverviewVO getOverviewByDateRange(WorkloadQueryDTO queryDTO);
}
