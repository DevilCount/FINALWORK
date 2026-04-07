package com.lis.report.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.exception.BusinessException;
import com.lis.common.result.PageResult;
import com.lis.report.dto.PanicValueHandleDTO;
import com.lis.report.dto.PanicValueQueryDTO;
import com.lis.report.entity.PanicValueDO;
import com.lis.report.entity.ReportDO;
import com.lis.report.entity.ReportItemDO;
import com.lis.report.mapper.PanicValueMapper;
import com.lis.report.mapper.ReportItemMapper;
import com.lis.report.mapper.ReportMapper;
import com.lis.report.service.PanicValueService;
import com.lis.report.vo.PanicValueVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PanicValueServiceImpl implements PanicValueService {

    private final PanicValueMapper panicValueMapper;
    private final ReportMapper reportMapper;
    private final ReportItemMapper reportItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkAndCreatePanicValue(Long reportId, ReportItemDO itemDO) {
        if (itemDO.getPanicLow() == null && itemDO.getPanicHigh() == null) {
            return;
        }

        if (StrUtil.isBlank(itemDO.getResultValue())) {
            return;
        }

        BigDecimal resultValue;
        try {
            resultValue = new BigDecimal(itemDO.getResultValue());
        } catch (NumberFormatException e) {
            return;
        }

        boolean isPanic = false;
        String panicType = null;

        if (itemDO.getPanicLow() != null && resultValue.compareTo(itemDO.getPanicLow()) < 0) {
            isPanic = true;
            panicType = "low";
        } else if (itemDO.getPanicHigh() != null && resultValue.compareTo(itemDO.getPanicHigh()) > 0) {
            isPanic = true;
            panicType = "high";
        }

        if (isPanic) {
            createPanicValueRecord(reportId, itemDO, resultValue, panicType);
            itemDO.setIsPanic(1);
            reportItemMapper.updateById(itemDO);
            log.warn("发现危急值: 报告ID={}, 项目={}, 结果={}", reportId, itemDO.getItemName(), resultValue);
        }
    }

    private void createPanicValueRecord(Long reportId, ReportItemDO itemDO, BigDecimal resultValue, String panicType) {
        ReportDO reportDO = reportMapper.selectById(reportId);
        if (reportDO == null) {
            return;
        }

        PanicValueDO panicValueDO = new PanicValueDO();
        panicValueDO.setPanicNo("PV" + IdUtil.getSnowflakeNextIdStr());
        panicValueDO.setReportId(reportId);
        panicValueDO.setReportNo(reportDO.getReportNo());
        panicValueDO.setSpecimenId(reportDO.getSpecimenId());
        panicValueDO.setSpecimenNo(reportDO.getSpecimenNo());
        panicValueDO.setPatientId(reportDO.getPatientId());
        panicValueDO.setPatientName(reportDO.getPatientName());
        panicValueDO.setDeptId(reportDO.getDeptId());
        panicValueDO.setDeptName(reportDO.getDeptName());
        panicValueDO.setWardName(reportDO.getWardName());
        panicValueDO.setBedNo(reportDO.getBedNo());
        panicValueDO.setItemCode(itemDO.getItemCode());
        panicValueDO.setItemName(itemDO.getItemName());
        panicValueDO.setResultValue(resultValue.toString());
        panicValueDO.setUnit(itemDO.getUnit());
        panicValueDO.setPanicLow(itemDO.getPanicLow());
        panicValueDO.setPanicHigh(itemDO.getPanicHigh());
        panicValueDO.setPanicType(panicType);
        panicValueDO.setFindTime(LocalDateTime.now());
        panicValueDO.setHandleStatus(0);
        panicValueDO.setCreateTime(LocalDateTime.now());
        panicValueDO.setUpdateTime(LocalDateTime.now());

        panicValueMapper.insert(panicValueDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notifyPanicValue(Long panicValueId, String notifyMethod, String receiveUserName) {
        PanicValueDO panicValueDO = panicValueMapper.selectById(panicValueId);
        if (panicValueDO == null) {
            throw new BusinessException("危急值记录不存在");
        }

        panicValueDO.setHandleStatus(1);
        panicValueDO.setNotifyTime(LocalDateTime.now());
        panicValueDO.setNotifyMethod(notifyMethod);
        panicValueDO.setReceiveUserName(receiveUserName);
        panicValueDO.setReceiveTime(LocalDateTime.now());
        panicValueDO.setUpdateTime(LocalDateTime.now());

        panicValueMapper.updateById(panicValueDO);
        log.info("危急值已通知: panicNo={}, 方式={}, 接收人={}", panicValueDO.getPanicNo(), notifyMethod, receiveUserName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePanicValue(PanicValueHandleDTO dto) {
        PanicValueDO panicValueDO = panicValueMapper.selectById(dto.getPanicValueId());
        if (panicValueDO == null) {
            throw new BusinessException("危急值记录不存在");
        }

        panicValueDO.setHandleStatus(2);
        panicValueDO.setHandleTime(LocalDateTime.now());
        panicValueDO.setHandleResult(dto.getHandleResult());
        panicValueDO.setUpdateTime(LocalDateTime.now());

        if (StrUtil.isNotBlank(dto.getNotifyMethod())) {
            panicValueDO.setNotifyMethod(dto.getNotifyMethod());
        }
        if (StrUtil.isNotBlank(dto.getReceiveUserName())) {
            panicValueDO.setReceiveUserName(dto.getReceiveUserName());
        }
        if (StrUtil.isNotBlank(dto.getRemark())) {
            panicValueDO.setRemark(dto.getRemark());
        }

        panicValueMapper.updateById(panicValueDO);
        log.info("危急值已处理: panicNo={}", panicValueDO.getPanicNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmPanicValue(Long panicValueId) {
        PanicValueDO panicValueDO = panicValueMapper.selectById(panicValueId);
        if (panicValueDO == null) {
            throw new BusinessException("危急值记录不存在");
        }

        panicValueDO.setHandleStatus(3);
        panicValueDO.setConfirmTime(LocalDateTime.now());
        panicValueDO.setUpdateTime(LocalDateTime.now());

        panicValueMapper.updateById(panicValueDO);
        log.info("危急值已确认: panicNo={}", panicValueDO.getPanicNo());
    }

    @Override
    public PanicValueVO getPanicValueById(Long panicValueId) {
        PanicValueDO panicValueDO = panicValueMapper.selectById(panicValueId);
        if (panicValueDO == null) {
            throw new BusinessException("危急值记录不存在");
        }
        return convertToVO(panicValueDO);
    }

    @Override
    public PageResult<PanicValueVO> queryPanicValues(PanicValueQueryDTO dto) {
        LambdaQueryWrapper<PanicValueDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getPanicNo()), PanicValueDO::getPanicNo, dto.getPanicNo());
        wrapper.like(StrUtil.isNotBlank(dto.getReportNo()), PanicValueDO::getReportNo, dto.getReportNo());
        wrapper.like(StrUtil.isNotBlank(dto.getPatientName()), PanicValueDO::getPatientName, dto.getPatientName());
        wrapper.eq(dto.getDeptId() != null, PanicValueDO::getDeptId, dto.getDeptId());
        wrapper.eq(dto.getHandleStatus() != null, PanicValueDO::getHandleStatus, dto.getHandleStatus());
        wrapper.eq(StrUtil.isNotBlank(dto.getItemCode()), PanicValueDO::getItemCode, dto.getItemCode());
        wrapper.orderByDesc(PanicValueDO::getFindTime);

        Page<PanicValueDO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        Page<PanicValueDO> result = panicValueMapper.selectPage(page, wrapper);

        List<PanicValueVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), dto.getPageNum(), dto.getPageSize(), voList);
    }

    private PanicValueVO convertToVO(PanicValueDO panicValueDO) {
        PanicValueVO vo = new PanicValueVO();
        BeanUtils.copyProperties(panicValueDO, vo);
        return vo;
    }
}
