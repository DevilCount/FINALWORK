package com.lis.report.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.exception.BusinessException;
import com.lis.common.result.PageResult;
import com.lis.report.dto.ReportApplyDTO;
import com.lis.report.dto.ReportApplyItemDTO;
import com.lis.report.dto.ReportQueryDTO;
import com.lis.report.entity.ReportDO;
import com.lis.report.entity.ReportItemDO;
import com.lis.report.mapper.ReportItemMapper;
import com.lis.report.mapper.ReportMapper;
import com.lis.report.service.ReportApplyService;
import com.lis.report.vo.ReportDetailVO;
import com.lis.report.vo.ReportItemVO;
import com.lis.report.vo.ReportVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportApplyServiceImpl implements ReportApplyService {

    private final ReportMapper reportMapper;
    private final ReportItemMapper reportItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createReportApply(ReportApplyDTO dto) {
        // 如果提供了标本编号，检查是否已存在报告
        if (StrUtil.isNotBlank(dto.getSpecimenNo())) {
            LambdaQueryWrapper<ReportDO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ReportDO::getSpecimenNo, dto.getSpecimenNo());
            ReportDO existReport = reportMapper.selectOne(wrapper);
            if (existReport != null) {
                throw new BusinessException("该标本已存在报告");
            }
        }

        ReportDO reportDO = new ReportDO();
        BeanUtils.copyProperties(dto, reportDO);
        reportDO.setReportNo(generateReportNo());
        reportDO.setStatus("draft");
        reportDO.setIsStat(dto.getIsStat() != null ? dto.getIsStat() : 0);
        reportDO.setIsPanic(0);
        reportDO.setIsAbnormal(0);
        reportDO.setPrintCount(0);
        reportDO.setCreateTime(LocalDateTime.now());
        reportDO.setUpdateTime(LocalDateTime.now());

        reportMapper.insert(reportDO);

        // 创建报告检验项目
        if (dto.getTestItems() != null && !dto.getTestItems().isEmpty()) {
            int sort = 1;
            for (ReportApplyItemDTO itemDTO : dto.getTestItems()) {
                ReportItemDO itemDO = new ReportItemDO();
                itemDO.setReportId(reportDO.getId());
                itemDO.setReportNo(reportDO.getReportNo());
                itemDO.setSpecimenTestItemId(itemDTO.getSpecimenTestItemId());
                itemDO.setItemCode(itemDTO.getItemCode());
                itemDO.setItemName(itemDTO.getItemName());
                itemDO.setItemNameEn(itemDTO.getItemNameEn());
                itemDO.setUnit(itemDTO.getUnit());
                itemDO.setReferenceLow(itemDTO.getReferenceLow());
                itemDO.setReferenceHigh(itemDTO.getReferenceHigh());
                itemDO.setReferenceText(itemDTO.getReferenceText());
                itemDO.setPanicLow(itemDTO.getPanicLow());
                itemDO.setPanicHigh(itemDTO.getPanicHigh());
                itemDO.setMethod(itemDTO.getMethod());
                itemDO.setIsPanic(0);
                itemDO.setIsAbnormal(0);
                itemDO.setSort(itemDTO.getSort() != null ? itemDTO.getSort() : sort++);
                itemDO.setCreateTime(LocalDateTime.now());
                itemDO.setUpdateTime(LocalDateTime.now());
                reportItemMapper.insert(itemDO);
            }
        }

        return reportDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReportApply(Long reportId, ReportApplyDTO dto) {
        ReportDO reportDO = reportMapper.selectById(reportId);
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }
        if (!"draft".equals(reportDO.getStatus())) {
            throw new BusinessException("只有草稿状态的报告才能修改");
        }
        BeanUtils.copyProperties(dto, reportDO);
        reportDO.setId(reportId);
        reportDO.setUpdateTime(LocalDateTime.now());
        reportMapper.updateById(reportDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteReportApply(Long reportId) {
        ReportDO reportDO = reportMapper.selectById(reportId);
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }
        if (!"draft".equals(reportDO.getStatus()) && !"cancelled".equals(reportDO.getStatus())) {
            throw new BusinessException("只有草稿或已取消状态的报告才能删除");
        }
        // 删除报告明细
        reportItemMapper.deleteByReportId(reportId);
        // 删除报告
        reportMapper.deleteById(reportId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelReport(Long reportId, String reason) {
        ReportDO reportDO = reportMapper.selectById(reportId);
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }

        if ("reported".equals(reportDO.getStatus()) || "printed".equals(reportDO.getStatus())) {
            throw new BusinessException("已发布或已打印的报告不能取消");
        }

        reportDO.setStatus("cancelled");
        reportDO.setRemark(reason);
        reportDO.setUpdateTime(LocalDateTime.now());
        reportMapper.updateById(reportDO);
    }

    @Override
    public ReportVO getReportById(Long reportId) {
        ReportDO reportDO = reportMapper.selectById(reportId);
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }
        return convertToVO(reportDO);
    }

    @Override
    public ReportDetailVO getReportDetailById(Long reportId) {
        ReportDO reportDO = reportMapper.selectById(reportId);
        if (reportDO == null) {
            throw new BusinessException("报告不存在");
        }

        ReportDetailVO detailVO = new ReportDetailVO();
        BeanUtils.copyProperties(reportDO, detailVO);

        List<ReportItemDO> items = reportItemMapper.selectByReportId(reportId);
        List<ReportItemVO> itemVOList = items.stream()
                .map(this::convertItemToVO)
                .collect(Collectors.toList());
        detailVO.setItems(itemVOList);

        return detailVO;
    }

    @Override
    public PageResult<ReportVO> queryReports(ReportQueryDTO dto) {
        LambdaQueryWrapper<ReportDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getReportNo()), ReportDO::getReportNo, dto.getReportNo());
        wrapper.like(StrUtil.isNotBlank(dto.getSpecimenNo()), ReportDO::getSpecimenNo, dto.getSpecimenNo());
        wrapper.like(StrUtil.isNotBlank(dto.getPatientName()), ReportDO::getPatientName, dto.getPatientName());
        wrapper.eq(StrUtil.isNotBlank(dto.getPatientIdNo()), ReportDO::getPatientIdNo, dto.getPatientIdNo());
        wrapper.eq(dto.getDeptId() != null, ReportDO::getDeptId, dto.getDeptId());
        wrapper.eq(StrUtil.isNotBlank(dto.getStatus()), ReportDO::getStatus, dto.getStatus());
        wrapper.eq(dto.getIsStat() != null, ReportDO::getIsStat, dto.getIsStat());
        wrapper.eq(dto.getIsPanic() != null, ReportDO::getIsPanic, dto.getIsPanic());
        wrapper.orderByDesc(ReportDO::getCreateTime);

        Page<ReportDO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        Page<ReportDO> result = reportMapper.selectPage(page, wrapper);

        List<ReportVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), dto.getPageNum(), dto.getPageSize(), voList);
    }

    private String generateReportNo() {
        return "RPT" + IdUtil.getSnowflakeNextIdStr();
    }

    private ReportVO convertToVO(ReportDO reportDO) {
        ReportVO vo = new ReportVO();
        BeanUtils.copyProperties(reportDO, vo);
        return vo;
    }

    private ReportItemVO convertItemToVO(ReportItemDO itemDO) {
        ReportItemVO vo = new ReportItemVO();
        BeanUtils.copyProperties(itemDO, vo);
        return vo;
    }
}
