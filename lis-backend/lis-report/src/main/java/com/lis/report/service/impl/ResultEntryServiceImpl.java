package com.lis.report.service.impl;

import cn.hutool.core.util.StrUtil;
import com.lis.common.exception.BusinessException;
import com.lis.report.dto.BatchResultEntryDTO;
import com.lis.report.dto.ResultEntryDTO;
import com.lis.report.entity.ReportDO;
import com.lis.report.entity.ReportItemDO;
import com.lis.report.mapper.ReportItemMapper;
import com.lis.report.mapper.ReportMapper;
import com.lis.report.service.PanicValueService;
import com.lis.report.service.ResultEntryService;
import com.lis.report.vo.ReportItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultEntryServiceImpl implements ResultEntryService {

    private final ReportMapper reportMapper;
    private final ReportItemMapper reportItemMapper;
    private final PanicValueService panicValueService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long entryResult(ResultEntryDTO dto) {
        ReportDO reportDO = reportMapper.selectById(dto.getReportId());
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }

        if ("reported".equals(reportDO.getStatus()) || "printed".equals(reportDO.getStatus())) {
            throw new BusinessException("已发布或已打印的报告不能录入结果");
        }

        ReportItemDO itemDO = new ReportItemDO();
        BeanUtils.copyProperties(dto, itemDO);
        itemDO.setReportNo(reportDO.getReportNo());
        itemDO.setIsPanic(0);
        itemDO.setIsAbnormal(0);
        itemDO.setCreateTime(LocalDateTime.now());
        itemDO.setUpdateTime(LocalDateTime.now());

        checkAndSetResultFlag(itemDO, dto);

        reportItemMapper.insert(itemDO);

        panicValueService.checkAndCreatePanicValue(dto.getReportId(), itemDO);

        updateReportTestInfo(reportDO);

        return itemDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEntryResult(BatchResultEntryDTO dto) {
        ReportDO reportDO = reportMapper.selectById(dto.getReportId());
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }

        if ("reported".equals(reportDO.getStatus()) || "printed".equals(reportDO.getStatus())) {
            throw new BusinessException("已发布或已打印的报告不能录入结果");
        }

        List<ResultEntryDTO> results = dto.getResults();
        for (ResultEntryDTO result : results) {
            result.setReportId(dto.getReportId());
            entryResult(result);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteResult(Long reportItemId) {
        ReportItemDO itemDO = reportItemMapper.selectById(reportItemId);
        if (itemDO == null) {
            throw new BusinessException("结果记录不存在");
        }

        ReportDO reportDO = reportMapper.selectById(itemDO.getReportId());
        if (reportDO != null && ("reported".equals(reportDO.getStatus()) || "printed".equals(reportDO.getStatus()))) {
            throw new BusinessException("已发布或已打印的报告不能删除结果");
        }

        reportItemMapper.deleteById(reportItemId);
    }

    @Override
    public ReportItemVO getResultItemById(Long reportItemId) {
        ReportItemDO itemDO = reportItemMapper.selectById(reportItemId);
        if (itemDO == null) {
            throw new BusinessException("结果记录不存在");
        }
        return convertToVO(itemDO);
    }

    private void checkAndSetResultFlag(ReportItemDO itemDO, ResultEntryDTO dto) {
        if (StrUtil.isBlank(dto.getResultValue())) {
            return;
        }

        BigDecimal resultValue;
        try {
            resultValue = new BigDecimal(dto.getResultValue());
        } catch (NumberFormatException e) {
            itemDO.setResultFlag("N");
            return;
        }

        BigDecimal refLow = dto.getReferenceLow();
        BigDecimal refHigh = dto.getReferenceHigh();

        if (refLow != null && refHigh != null) {
            if (resultValue.compareTo(refLow) < 0) {
                itemDO.setResultFlag("L");
                itemDO.setIsAbnormal(1);
            } else if (resultValue.compareTo(refHigh) > 0) {
                itemDO.setResultFlag("H");
                itemDO.setIsAbnormal(1);
            } else {
                itemDO.setResultFlag("N");
            }
        }
    }

    private void updateReportTestInfo(ReportDO reportDO) {
        reportDO.setTestTime(LocalDateTime.now());
        reportDO.setUpdateTime(LocalDateTime.now());
        reportMapper.updateById(reportDO);
    }

    private ReportItemVO convertToVO(ReportItemDO itemDO) {
        ReportItemVO vo = new ReportItemVO();
        BeanUtils.copyProperties(itemDO, vo);
        return vo;
    }
}
