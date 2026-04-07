package com.lis.ai.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.ai.dto.DiagnosisRequestDTO;
import com.lis.ai.dto.DiagnosisRecordQueryDTO;
import com.lis.ai.dto.DiagnosisReviewDTO;
import com.lis.ai.entity.AiDiagnosisDetailDO;
import com.lis.ai.entity.AiDiagnosisRecordDO;
import com.lis.ai.enums.DiagnosisTypeEnum;
import com.lis.ai.enums.ReviewStatusEnum;
import com.lis.ai.mapper.AiDiagnosisDetailMapper;
import com.lis.ai.mapper.AiDiagnosisRecordMapper;
import com.lis.ai.service.*;
import com.lis.ai.vo.DiagnosisRecordVO;
import com.lis.ai.vo.DiagnosisResultVO;
import com.lis.common.result.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiagnosisRecordServiceImpl implements DiagnosisRecordService {

    private final AiDiagnosisRecordMapper recordMapper;
    private final AiDiagnosisDetailMapper detailMapper;
    private final BloodRoutineAnalysisService bloodRoutineAnalysisService;
    private final UrineRoutineAnalysisService urineRoutineAnalysisService;
    private final LiverFunctionAnalysisService liverFunctionAnalysisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DiagnosisResultVO performDiagnosis(DiagnosisRequestDTO request) {
        DiagnosisTypeEnum diagnosisType = DiagnosisTypeEnum.getByCode(request.getDiagnosisType());
        if (diagnosisType == null) {
            throw new IllegalArgumentException("不支持的诊断类型: " + request.getDiagnosisType());
        }

        DiagnosisResultVO result = switch (diagnosisType) {
            case BLOOD_ROUTINE -> bloodRoutineAnalysisService.analyze(request);
            case URINE_ROUTINE -> urineRoutineAnalysisService.analyze(request);
            case LIVER_FUNCTION -> liverFunctionAnalysisService.analyze(request);
            default -> throw new IllegalArgumentException("暂不支持该诊断类型: " + diagnosisType.getName());
        };

        if (Boolean.TRUE.equals(request.getSaveRecord())) {
            saveDiagnosisRecord(request, result);
        }

        return result;
    }

    private void saveDiagnosisRecord(DiagnosisRequestDTO request, DiagnosisResultVO result) {
        AiDiagnosisRecordDO record = new AiDiagnosisRecordDO();
        record.setDiagnosisNo(result.getDiagnosisNo());
        record.setReportId(request.getReportId());
        record.setReportNo(request.getReportNo());
        record.setSpecimenId(request.getSpecimenId());
        record.setSpecimenNo(request.getSpecimenNo());
        record.setPatientId(request.getPatientId());
        record.setPatientName(request.getPatientName());
        record.setPatientGender(request.getPatientGender());
        record.setPatientAge(request.getPatientAge());
        record.setDiagnosisResult(result.getDiagnosisResult());
        record.setDiagnosisCode(result.getDiagnosisCode());
        record.setDiagnosisName(result.getDiagnosisName());
        record.setConfidence(result.getConfidence());
        record.setProbability(result.getProbability());
        record.setSuggestion(result.getSuggestion());
        record.setRiskLevel(result.getRiskLevel());
        record.setIsAbnormal(result.getIsAbnormal());
        record.setIsPanic(result.getIsPanic());
        record.setDiagnosisTime(result.getDiagnosisTime());
        record.setDiagnosisDuration(result.getDiagnosisDuration());
        record.setReviewStatus(ReviewStatusEnum.PENDING.getCode());

        record.setInputData(JSON.toJSONString(request.getTestData()));

        if (result.getMatchedRules() != null && !result.getMatchedRules().isEmpty()) {
            DiagnosisResultVO.MatchedRuleVO primaryRule = result.getMatchedRules().get(0);
            record.setRuleId(primaryRule.getRuleId());
            record.setRuleName(primaryRule.getRuleName());
        }

        recordMapper.insert(record);

        if (result.getAbnormalItems() != null) {
            for (DiagnosisResultVO.AbnormalItemVO item : result.getAbnormalItems()) {
                AiDiagnosisDetailDO detail = new AiDiagnosisDetailDO();
                detail.setDiagnosisId(record.getId());
                detail.setItemCode(item.getItemCode());
                detail.setItemName(item.getItemName());
                detail.setResultValue(item.getResultValue());
                detail.setUnit(item.getUnit());
                detail.setReferenceLow(item.getReferenceLow());
                detail.setReferenceHigh(item.getReferenceHigh());
                detail.setIsAbnormal(1);
                detail.setAbnormalDegree(item.getAbnormalDegree());
                detail.setFeatureImportance(item.getFeatureImportance());
                detailMapper.insert(detail);
            }
        }

        log.info("保存诊断记录成功: diagnosisNo={}", result.getDiagnosisNo());
    }

    @Override
    public DiagnosisRecordVO getDiagnosisRecord(Long id) {
        AiDiagnosisRecordDO record = recordMapper.selectById(id);
        if (record == null) {
            return null;
        }
        return convertToVO(record);
    }

    @Override
    public DiagnosisRecordVO getDiagnosisRecordByNo(String diagnosisNo) {
        AiDiagnosisRecordDO record = recordMapper.selectOne(
                new LambdaQueryWrapper<AiDiagnosisRecordDO>()
                        .eq(AiDiagnosisRecordDO::getDiagnosisNo, diagnosisNo)
        );
        if (record == null) {
            return null;
        }
        return convertToVO(record);
    }

    @Override
    public PageResult<DiagnosisRecordVO> queryDiagnosisRecords(DiagnosisRecordQueryDTO queryDTO) {
        LambdaQueryWrapper<AiDiagnosisRecordDO> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(queryDTO.getDiagnosisNo())) {
            wrapper.like(AiDiagnosisRecordDO::getDiagnosisNo, queryDTO.getDiagnosisNo());
        }
        if (StringUtils.hasText(queryDTO.getReportNo())) {
            wrapper.like(AiDiagnosisRecordDO::getReportNo, queryDTO.getReportNo());
        }
        if (StringUtils.hasText(queryDTO.getSpecimenNo())) {
            wrapper.like(AiDiagnosisRecordDO::getSpecimenNo, queryDTO.getSpecimenNo());
        }
        if (StringUtils.hasText(queryDTO.getPatientName())) {
            wrapper.like(AiDiagnosisRecordDO::getPatientName, queryDTO.getPatientName());
        }
        if (queryDTO.getRiskLevel() != null) {
            wrapper.eq(AiDiagnosisRecordDO::getRiskLevel, queryDTO.getRiskLevel());
        }
        if (queryDTO.getIsAbnormal() != null) {
            wrapper.eq(AiDiagnosisRecordDO::getIsAbnormal, queryDTO.getIsAbnormal());
        }
        if (queryDTO.getReviewStatus() != null) {
            wrapper.eq(AiDiagnosisRecordDO::getReviewStatus, queryDTO.getReviewStatus());
        }
        if (StringUtils.hasText(queryDTO.getStartTime())) {
            LocalDateTime startTime = LocalDateTime.parse(queryDTO.getStartTime() + " 00:00:00",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            wrapper.ge(AiDiagnosisRecordDO::getDiagnosisTime, startTime);
        }
        if (StringUtils.hasText(queryDTO.getEndTime())) {
            LocalDateTime endTime = LocalDateTime.parse(queryDTO.getEndTime() + " 23:59:59",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            wrapper.le(AiDiagnosisRecordDO::getDiagnosisTime, endTime);
        }

        wrapper.orderByDesc(AiDiagnosisRecordDO::getDiagnosisTime);

        Page<AiDiagnosisRecordDO> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<AiDiagnosisRecordDO> resultPage = recordMapper.selectPage(page, wrapper);

        List<DiagnosisRecordVO> voList = resultPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(resultPage.getTotal(), queryDTO.getPageNum(), queryDTO.getPageSize(), voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reviewDiagnosis(DiagnosisReviewDTO reviewDTO, Long userId, String userName) {
        AiDiagnosisRecordDO record = recordMapper.selectById(reviewDTO.getDiagnosisId());
        if (record == null) {
            throw new IllegalArgumentException("诊断记录不存在");
        }

        record.setReviewStatus(reviewDTO.getReviewStatus());
        record.setReviewUserId(userId);
        record.setReviewUserName(userName);
        record.setReviewTime(LocalDateTime.now());
        record.setReviewRemark(reviewDTO.getReviewRemark());

        recordMapper.updateById(record);

        log.info("审核诊断记录: diagnosisId={}, status={}, reviewer={}",
                reviewDTO.getDiagnosisId(), reviewDTO.getReviewStatus(), userName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDiagnosisRecord(Long id) {
        AiDiagnosisRecordDO record = recordMapper.selectById(id);
        if (record == null) {
            throw new IllegalArgumentException("诊断记录不存在");
        }

        detailMapper.delete(new LambdaQueryWrapper<AiDiagnosisDetailDO>()
                .eq(AiDiagnosisDetailDO::getDiagnosisId, id));

        recordMapper.deleteById(id);

        log.info("删除诊断记录: id={}", id);
    }

    private DiagnosisRecordVO convertToVO(AiDiagnosisRecordDO record) {
        DiagnosisRecordVO vo = new DiagnosisRecordVO();
        BeanUtils.copyProperties(record, vo);
        return vo;
    }
}
