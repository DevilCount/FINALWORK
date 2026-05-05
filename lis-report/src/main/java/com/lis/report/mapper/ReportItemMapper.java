package com.lis.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.report.entity.ReportItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportItemMapper extends BaseMapper<ReportItemDO> {

    List<ReportItemDO> selectByReportId(Long reportId);

    int deleteByReportId(Long reportId);
}
