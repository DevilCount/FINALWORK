package com.lis.report.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lis.common.exception.BusinessException;
import com.lis.report.dto.ReportPrintDTO;
import com.lis.report.dto.ReportPublishDTO;
import com.lis.report.entity.ReportDO;
import com.lis.report.entity.ReportItemDO;
import com.lis.report.entity.ReportPrintLogDO;
import com.lis.report.mapper.ReportItemMapper;
import com.lis.report.mapper.ReportMapper;
import com.lis.report.mapper.ReportPrintLogMapper;
import com.lis.report.service.ReportPublishService;
import com.lis.report.vo.ReportPrintLogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportPublishServiceImpl implements ReportPublishService {

    private final ReportMapper reportMapper;
    private final ReportItemMapper reportItemMapper;
    private final ReportPrintLogMapper reportPrintLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishReport(ReportPublishDTO dto) {
        ReportDO reportDO = reportMapper.selectById(dto.getReportId());
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }

        if (!"audited".equals(reportDO.getStatus())) {
            throw new BusinessException("只有已审核状态的报告才能发布");
        }

        List<ReportItemDO> items = reportItemMapper.selectByReportId(dto.getReportId());
        checkAndMarkAbnormal(reportDO, items);

        reportDO.setStatus("reported");
        reportDO.setReportTime(LocalDateTime.now());
        reportDO.setUpdateTime(LocalDateTime.now());

        if (StrUtil.isNotBlank(dto.getReportConclusion())) {
            reportDO.setReportConclusion(dto.getReportConclusion());
        }

        reportMapper.updateById(reportDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void printReport(ReportPrintDTO dto) {
        ReportDO reportDO = reportMapper.selectById(dto.getReportId());
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }

        if (!"reported".equals(reportDO.getStatus()) && !"printed".equals(reportDO.getStatus())) {
            throw new BusinessException("只有已发布状态的报告才能打印");
        }

        reportDO.setStatus("printed");
        reportDO.setPrintCount(reportDO.getPrintCount() + dto.getPrintCopies());
        reportDO.setLastPrintTime(LocalDateTime.now());
        reportDO.setUpdateTime(LocalDateTime.now());
        reportMapper.updateById(reportDO);

        ReportPrintLogDO printLogDO = new ReportPrintLogDO();
        printLogDO.setReportId(dto.getReportId());
        printLogDO.setReportNo(reportDO.getReportNo());
        printLogDO.setPrintTime(LocalDateTime.now());
        printLogDO.setPrintCopies(dto.getPrintCopies());
        printLogDO.setPrinterName(dto.getPrinterName());
        printLogDO.setPrintIp(dto.getPrintIp());
        printLogDO.setCreateTime(LocalDateTime.now());
        reportPrintLogMapper.insert(printLogDO);
    }

    @Override
    public List<ReportPrintLogVO> getPrintLogs(Long reportId) {
        LambdaQueryWrapper<ReportPrintLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReportPrintLogDO::getReportId, reportId);
        wrapper.orderByDesc(ReportPrintLogDO::getPrintTime);
        List<ReportPrintLogDO> logs = reportPrintLogMapper.selectList(wrapper);
        return logs.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    private void checkAndMarkAbnormal(ReportDO reportDO, List<ReportItemDO> items) {
        boolean hasAbnormal = false;
        boolean hasPanic = false;

        for (ReportItemDO item : items) {
            if (item.getIsAbnormal() != null && item.getIsAbnormal() == 1) {
                hasAbnormal = true;
            }
            if (item.getIsPanic() != null && item.getIsPanic() == 1) {
                hasPanic = true;
            }
        }

        reportDO.setIsAbnormal(hasAbnormal ? 1 : 0);
        reportDO.setIsPanic(hasPanic ? 1 : 0);
    }

    private ReportPrintLogVO convertToVO(ReportPrintLogDO logDO) {
        ReportPrintLogVO vo = new ReportPrintLogVO();
        BeanUtils.copyProperties(logDO, vo);
        return vo;
    }
}
