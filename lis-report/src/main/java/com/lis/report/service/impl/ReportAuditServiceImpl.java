package com.lis.report.service.impl;

import cn.hutool.core.util.StrUtil;
import com.lis.common.exception.BusinessException;
import com.lis.report.dto.ReportAuditDTO;
import com.lis.report.entity.ReportDO;
import com.lis.report.entity.ReportItemDO;
import com.lis.report.mapper.ReportItemMapper;
import com.lis.report.mapper.ReportMapper;
import com.lis.report.service.ReportAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportAuditServiceImpl implements ReportAuditService {

    private final ReportMapper reportMapper;
    private final ReportItemMapper reportItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitForAudit(Long reportId) {
        ReportDO reportDO = reportMapper.selectById(reportId);
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }

        if (!"draft".equals(reportDO.getStatus())) {
            throw new BusinessException("只有草稿状态的报告才能提交审核");
        }

        List<ReportItemDO> items = reportItemMapper.selectByReportId(reportId);
        if (items == null || items.isEmpty()) {
            throw new BusinessException("报告没有检验结果，不能提交审核");
        }

        reportDO.setStatus("pending_review");
        reportDO.setTestTime(LocalDateTime.now());
        reportDO.setUpdateTime(LocalDateTime.now());
        reportMapper.updateById(reportDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditReport(ReportAuditDTO dto) {
        ReportDO reportDO = reportMapper.selectById(dto.getReportId());
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }

        if (!"pending_review".equals(reportDO.getStatus())) {
            throw new BusinessException("只有待审核状态的报告才能审核");
        }

        reportDO.setStatus("audited");
        reportDO.setAuditTime(LocalDateTime.now());
        reportDO.setUpdateTime(LocalDateTime.now());

        if (StrUtil.isNotBlank(dto.getAuditOpinion())) {
            reportDO.setRemark(dto.getAuditOpinion());
        }

        reportMapper.updateById(reportDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectReport(ReportAuditDTO dto) {
        ReportDO reportDO = reportMapper.selectById(dto.getReportId());
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }

        if (!"pending_review".equals(reportDO.getStatus())) {
            throw new BusinessException("只有待审核状态的报告才能驳回");
        }

        reportDO.setStatus("draft");
        reportDO.setUpdateTime(LocalDateTime.now());

        if (StrUtil.isNotBlank(dto.getAuditOpinion())) {
            reportDO.setRemark(dto.getAuditOpinion());
        }

        reportMapper.updateById(reportDO);
    }
}
