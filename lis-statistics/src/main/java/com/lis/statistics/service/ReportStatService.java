package com.lis.statistics.service;

import com.lis.common.result.PageResult;
import com.lis.statistics.dto.ReportQueryDTO;
import com.lis.statistics.vo.EChartsVO;
import com.lis.statistics.vo.ReportStatVO;

import java.util.List;

public interface ReportStatService {

    List<ReportStatVO> getDailyStat(ReportQueryDTO queryDTO);

    PageResult<ReportStatVO> getItemStatPage(ReportQueryDTO queryDTO);

    List<ReportStatVO> getDeptStat(ReportQueryDTO queryDTO);

    EChartsVO getReportTrendChart(ReportQueryDTO queryDTO);

    EChartsVO getAbnormalRateChart(ReportQueryDTO queryDTO);

    EChartsVO getPanicDistributionChart(ReportQueryDTO queryDTO);
}
