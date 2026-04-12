package com.lis.report.service.impl;

import cn.hutool.core.util.StrUtil;
import com.lis.common.exception.BusinessException;
import com.lis.report.dto.ReportAuditDTO;
import com.lis.report.entity.ReportDO;
import com.lis.report.mapper.ReportMapper;
import com.lis.report.service.ReportAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportAuditServiceImpl implements ReportAuditService {

    private final ReportMapper reportMapper;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void firstAudit(ReportAuditDTO dto) {
        ReportDO reportDO = reportMapper.selectById(dto.getReportId());
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }

        if (!"pending_review".equals(reportDO.getStatus())) {
            throw new BusinessException("只有待审核状态的报告才能初审");
        }

        reportDO.setStatus("first_audited");
        reportDO.setFirstAuditUserId(dto.getAuditUserId());
        reportDO.setFirstAuditUserName(dto.getAuditUserName());
        reportDO.setFirstAuditTime(LocalDateTime.now());
        reportDO.setFirstAuditOpinion(dto.getAuditOpinion());
        reportDO.setAuditUserId(dto.getAuditUserId());
        reportDO.setAuditUserName(dto.getAuditUserName());
        reportDO.setAuditTime(LocalDateTime.now());
        reportDO.setUpdateTime(LocalDateTime.now());
        reportMapper.updateById(reportDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void firstAuditReject(ReportAuditDTO dto) {
        ReportDO reportDO = reportMapper.selectById(dto.getReportId());
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }

        if (!"pending_review".equals(reportDO.getStatus())) {
            throw new BusinessException("只有待审核状态的报告才能初审驳回");
        }

        reportDO.setStatus("draft");
        reportDO.setFirstAuditUserId(dto.getAuditUserId());
        reportDO.setFirstAuditUserName(dto.getAuditUserName());
        reportDO.setFirstAuditTime(LocalDateTime.now());
        reportDO.setFirstAuditOpinion(dto.getAuditOpinion());
        reportDO.setUpdateTime(LocalDateTime.now());
        reportMapper.updateById(reportDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finalAudit(ReportAuditDTO dto) {
        ReportDO reportDO = reportMapper.selectById(dto.getReportId());
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }

        if (!"first_audited".equals(reportDO.getStatus())) {
            throw new BusinessException("只有初审通过状态的报告才能终审");
        }

        if (dto.getAuditUserId() != null && dto.getAuditUserId().equals(reportDO.getFirstAuditUserId())) {
            throw new BusinessException("终审人不能与初审人相同");
        }

        reportDO.setStatus("final_audited");
        reportDO.setFinalAuditUserId(dto.getAuditUserId());
        reportDO.setFinalAuditUserName(dto.getAuditUserName());
        reportDO.setFinalAuditTime(LocalDateTime.now());
        reportDO.setFinalAuditOpinion(dto.getAuditOpinion());
        reportDO.setUpdateTime(LocalDateTime.now());
        reportMapper.updateById(reportDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finalAuditReject(ReportAuditDTO dto) {
        ReportDO reportDO = reportMapper.selectById(dto.getReportId());
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }

        if (!"first_audited".equals(reportDO.getStatus())) {
            throw new BusinessException("只有初审通过状态的报告才能终审驳回");
        }

        if (dto.getAuditUserId() != null && dto.getAuditUserId().equals(reportDO.getFirstAuditUserId())) {
            throw new BusinessException("终审人不能与初审人相同");
        }

        reportDO.setStatus("pending_review");
        reportDO.setFinalAuditUserId(dto.getAuditUserId());
        reportDO.setFinalAuditUserName(dto.getAuditUserName());
        reportDO.setFinalAuditTime(LocalDateTime.now());
        reportDO.setFinalAuditOpinion(dto.getAuditOpinion());
        reportDO.setUpdateTime(LocalDateTime.now());
        reportMapper.updateById(reportDO);
    }
}
