package com.lis.specimen.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.exception.BusinessException;
import com.lis.common.result.PageResult;
import com.lis.common.result.ResultCode;
import com.lis.specimen.dto.*;
import com.lis.specimen.entity.*;
import com.lis.specimen.enums.SpecimenActionEnum;
import com.lis.specimen.enums.SpecimenStatusEnum;
import com.lis.specimen.mapper.*;
import com.lis.specimen.service.BarcodeGeneratorService;
import com.lis.specimen.service.SpecimenService;
import com.lis.specimen.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecimenServiceImpl implements SpecimenService {

    private final SpecimenMapper specimenMapper;
    private final SpecimenTestItemMapper specimenTestItemMapper;
    private final SpecimenTraceMapper specimenTraceMapper;
    private final SpecimenTypeMapper specimenTypeMapper;
    private final TestItemMapper testItemMapper;
    private final SpecimenRejectMapper specimenRejectMapper;
    private final TestItemCategoryMapper testItemCategoryMapper;
    private final BarcodeGeneratorService barcodeGeneratorService;

    private static final Map<String, List<String>> ALLOWED_TRANSITIONS = new HashMap<>();

    static {
        ALLOWED_TRANSITIONS.put("pending", Arrays.asList("received", "rejected", "cancelled"));
        ALLOWED_TRANSITIONS.put("received", Arrays.asList("stored", "testing", "rejected", "cancelled"));
        ALLOWED_TRANSITIONS.put("stored", Arrays.asList("testing", "rejected", "cancelled"));
        ALLOWED_TRANSITIONS.put("testing", Arrays.asList("completed", "cancelled"));
        ALLOWED_TRANSITIONS.put("completed", Arrays.asList());
        ALLOWED_TRANSITIONS.put("rejected", Arrays.asList());
        ALLOWED_TRANSITIONS.put("cancelled", Arrays.asList());
    }

    private void validateStatusTransition(String fromStatus, String toStatus) {
        List<String> allowedTargets = ALLOWED_TRANSITIONS.get(fromStatus);
        if (allowedTargets == null || !allowedTargets.contains(toStatus)) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "标本状态不允许从[" + SpecimenStatusEnum.getDescByCode(fromStatus) + "]变更为[" + SpecimenStatusEnum.getDescByCode(toStatus) + "]");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SpecimenDetailVO register(SpecimenRegisterDTO dto) {
        SpecimenDO specimen = new SpecimenDO();
        BeanUtil.copyProperties(dto, specimen);
        
        String specimenNo = barcodeGeneratorService.generateSpecimenNo();
        String barcode = barcodeGeneratorService.generateBarcode();
        
        specimen.setSpecimenNo(specimenNo);
        specimen.setBarcode(barcode);
        specimen.setStatus(SpecimenStatusEnum.PENDING.getCode());
        specimen.setIsStat(dto.getIsStat() != null ? dto.getIsStat() : 0);
        specimen.setIsPrint(0);
        specimen.setCreateTime(LocalDateTime.now());
        
        if (dto.getCollectTime() == null) {
            specimen.setCollectTime(LocalDateTime.now());
        }
        
        specimenMapper.insert(specimen);
        
        if (!CollectionUtils.isEmpty(dto.getTestItemIds())) {
            List<SpecimenTestItemDO> testItems = new ArrayList<>();
            for (Long itemId : dto.getTestItemIds()) {
                TestItemDO testItem = testItemMapper.selectById(itemId);
                if (testItem != null) {
                    SpecimenTestItemDO testItemDO = new SpecimenTestItemDO();
                    testItemDO.setSpecimenId(specimen.getId());
                    testItemDO.setSpecimenNo(specimenNo);
                    testItemDO.setTestItemId(itemId);
                    testItemDO.setItemCode(testItem.getItemCode());
                    testItemDO.setItemName(testItem.getItemName());
                    testItemDO.setUnit(testItem.getUnit());
                    testItemDO.setReferenceLow(testItem.getReferenceLow());
                    testItemDO.setReferenceHigh(testItem.getReferenceHigh());
                    testItemDO.setReferenceText(testItem.getReferenceText());
                    testItemDO.setStatus("pending");
                    testItemDO.setIsPanic(0);
                    testItemDO.setIsAbnormal(0);
                    testItemDO.setCreateTime(LocalDateTime.now());
                    testItems.add(testItemDO);
                }
            }
            for (SpecimenTestItemDO item : testItems) {
                specimenTestItemMapper.insert(item);
            }
        }
        
        saveTrace(specimen.getId(), specimenNo, SpecimenActionEnum.REGISTER.getCode(),
                null, SpecimenStatusEnum.PENDING.getCode(), null, null,
                dto.getCollectUserId(), dto.getCollectUserName(), "标本登记");
        
        log.info("标本登记成功: specimenNo={}, barcode={}", specimenNo, barcode);
        return getById(specimen.getId());
    }

    @Override
    public SpecimenDetailVO getByBarcode(String barcode) {
        LambdaQueryWrapper<SpecimenDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpecimenDO::getBarcode, barcode);
        SpecimenDO specimen = specimenMapper.selectOne(wrapper);
        if (specimen == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "标本不存在");
        }
        return convertToDetailVO(specimen);
    }

    @Override
    public SpecimenDetailVO getBySpecimenNo(String specimenNo) {
        LambdaQueryWrapper<SpecimenDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpecimenDO::getSpecimenNo, specimenNo);
        SpecimenDO specimen = specimenMapper.selectOne(wrapper);
        if (specimen == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "标本不存在");
        }
        return convertToDetailVO(specimen);
    }

    @Override
    public SpecimenDetailVO getById(Long id) {
        SpecimenDO specimen = specimenMapper.selectById(id);
        if (specimen == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "标本不存在");
        }
        return convertToDetailVO(specimen);
    }

    @Override
    public PageResult<SpecimenListVO> queryList(SpecimenQueryDTO dto) {
        LambdaQueryWrapper<SpecimenDO> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(dto.getSpecimenNo())) {
            wrapper.like(SpecimenDO::getSpecimenNo, dto.getSpecimenNo());
        }
        if (StringUtils.hasText(dto.getBarcode())) {
            wrapper.like(SpecimenDO::getBarcode, dto.getBarcode());
        }
        if (StringUtils.hasText(dto.getPatientName())) {
            wrapper.like(SpecimenDO::getPatientName, dto.getPatientName());
        }
        if (StringUtils.hasText(dto.getPatientIdNo())) {
            wrapper.eq(SpecimenDO::getPatientIdNo, dto.getPatientIdNo());
        }
        if (dto.getDeptId() != null) {
            wrapper.eq(SpecimenDO::getDeptId, dto.getDeptId());
        }
        if (dto.getWardId() != null) {
            wrapper.eq(SpecimenDO::getWardId, dto.getWardId());
        }
        if (dto.getSpecimenTypeId() != null) {
            wrapper.eq(SpecimenDO::getSpecimenTypeId, dto.getSpecimenTypeId());
        }
        if (StringUtils.hasText(dto.getStatus())) {
            wrapper.eq(SpecimenDO::getStatus, dto.getStatus());
        }
        if (dto.getIsStat() != null) {
            wrapper.eq(SpecimenDO::getIsStat, dto.getIsStat());
        }
        if (dto.getCollectTimeStart() != null) {
            wrapper.ge(SpecimenDO::getCollectTime, dto.getCollectTimeStart());
        }
        if (dto.getCollectTimeEnd() != null) {
            wrapper.le(SpecimenDO::getCollectTime, dto.getCollectTimeEnd());
        }
        if (dto.getReceiveTimeStart() != null) {
            wrapper.ge(SpecimenDO::getReceiveTime, dto.getReceiveTimeStart());
        }
        if (dto.getReceiveTimeEnd() != null) {
            wrapper.le(SpecimenDO::getReceiveTime, dto.getReceiveTimeEnd());
        }
        
        wrapper.orderByDesc(SpecimenDO::getCreateTime);
        
        Page<SpecimenDO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<SpecimenDO> result = specimenMapper.selectPage(page, wrapper);
        
        List<SpecimenListVO> list = result.getRecords().stream()
                .map(this::convertToListVO)
                .collect(Collectors.toList());
        
        return PageResult.of(result.getTotal(), dto.getPageNum(), dto.getPageSize(), list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receive(SpecimenReceiveDTO dto) {
        SpecimenDO specimen = getSpecimenByBarcode(dto.getBarcode());
        
        if (!SpecimenStatusEnum.PENDING.getCode().equals(specimen.getStatus())) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "标本状态不正确，无法签收");
        }
        
        String fromStatus = specimen.getStatus();
        specimen.setStatus(SpecimenStatusEnum.RECEIVED.getCode());
        specimen.setReceiveTime(LocalDateTime.now());
        specimen.setReceiveUserId(dto.getReceiveUserId());
        specimen.setReceiveUserName(dto.getReceiveUserName());
        specimen.setUpdateTime(LocalDateTime.now());
        
        specimenMapper.updateById(specimen);
        
        saveTrace(specimen.getId(), specimen.getSpecimenNo(), SpecimenActionEnum.RECEIVE.getCode(),
                fromStatus, SpecimenStatusEnum.RECEIVED.getCode(), null, null,
                dto.getReceiveUserId(), dto.getReceiveUserName(), dto.getRemark());
        
        log.info("标本签收成功: specimenNo={}", specimen.getSpecimenNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void storage(SpecimenStorageDTO dto) {
        SpecimenDO specimen;
        if (dto.getSpecimenId() != null) {
            specimen = specimenMapper.selectById(dto.getSpecimenId());
            if (specimen == null) {
                throw new BusinessException(ResultCode.NOT_FOUND, "标本不存在");
            }
        } else if (StringUtils.hasText(dto.getBarcode())) {
            specimen = getSpecimenByBarcode(dto.getBarcode());
        } else {
            throw new BusinessException(ResultCode.BAD_REQUEST, "标本ID和条码不能同时为空");
        }
        
        String fromStatus = specimen.getStatus();
        String toStatus = SpecimenStatusEnum.STORED.getCode();
        
        validateStatusTransition(fromStatus, toStatus);
        
        specimen.setStatus(toStatus);
        specimen.setUpdateTime(LocalDateTime.now());
        specimenMapper.updateById(specimen);
        
        saveTrace(specimen.getId(), specimen.getSpecimenNo(), SpecimenActionEnum.STORAGE.getCode(),
                fromStatus, toStatus, null, dto.getStorageLocation(),
                dto.getOperatorId(), dto.getOperatorName(), dto.getRemark());
        
        log.info("标本入库成功: specimenNo={}, location={}", specimen.getSpecimenNo(), dto.getStorageLocation());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(SpecimenStatusDTO dto) {
        SpecimenDO specimen = specimenMapper.selectById(dto.getSpecimenId());
        if (specimen == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "标本不存在");
        }
        
        SpecimenStatusEnum targetStatus = SpecimenStatusEnum.getByCode(dto.getTargetStatus());
        if (targetStatus == null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "无效的目标状态");
        }
        
        String fromStatus = specimen.getStatus();
        validateStatusTransition(fromStatus, dto.getTargetStatus());
        
        specimen.setStatus(dto.getTargetStatus());
        specimen.setUpdateTime(LocalDateTime.now());
        specimenMapper.updateById(specimen);
        
        SpecimenActionEnum action = getActionByStatus(dto.getTargetStatus());
        saveTrace(specimen.getId(), specimen.getSpecimenNo(), action.getCode(),
                fromStatus, dto.getTargetStatus(), null, null,
                dto.getOperatorId(), dto.getOperatorName(), dto.getRemark());
        
        log.info("标本状态更新成功: specimenNo={}, from={}, to={}", 
                specimen.getSpecimenNo(), fromStatus, dto.getTargetStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reject(SpecimenReceiveDTO dto) {
        SpecimenDO specimen = getSpecimenByBarcode(dto.getBarcode());
        
        String fromStatus = specimen.getStatus();
        String toStatus = SpecimenStatusEnum.REJECTED.getCode();
        
        if (!"pending".equals(fromStatus) && !"received".equals(fromStatus) && !"stored".equals(fromStatus)) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "只有待接收/已接收/已入库状态的标本才能拒收");
        }
        
        specimen.setStatus(toStatus);
        specimen.setRejectReason(dto.getRejectReason());
        specimen.setUpdateTime(LocalDateTime.now());
        specimenMapper.updateById(specimen);
        
        SpecimenRejectDO rejectDO = new SpecimenRejectDO();
        rejectDO.setSpecimenId(specimen.getId());
        rejectDO.setSpecimenNo(specimen.getSpecimenNo());
        rejectDO.setRejectType(dto.getRejectType());
        rejectDO.setRejectReason(dto.getRejectReason());
        rejectDO.setRejectUserId(dto.getReceiveUserId());
        rejectDO.setRejectUserName(dto.getReceiveUserName());
        rejectDO.setRejectTime(LocalDateTime.now());
        rejectDO.setHandleStatus(0);
        specimenRejectMapper.insert(rejectDO);
        
        saveTrace(specimen.getId(), specimen.getSpecimenNo(), SpecimenActionEnum.REJECT.getCode(),
                fromStatus, toStatus, null, null,
                dto.getReceiveUserId(), dto.getReceiveUserName(), dto.getRejectReason());
        
        log.info("标本拒收成功: specimenNo={}, reason={}", specimen.getSpecimenNo(), dto.getRejectReason());
    }

    @Override
    public List<SpecimenTraceVO> getTraceList(Long specimenId) {
        List<SpecimenTraceDO> traces = specimenTraceMapper.selectBySpecimenId(specimenId);
        return traces.stream()
                .map(this::convertToTraceVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecimenTraceVO> getTraceListByNo(String specimenNo) {
        List<SpecimenTraceDO> traces = specimenTraceMapper.selectBySpecimenNo(specimenNo);
        return traces.stream()
                .map(this::convertToTraceVO)
                .collect(Collectors.toList());
    }

    @Override
    public SpecimenStatisticsVO getStatistics(SpecimenStatisticsDTO dto) {
        SpecimenStatisticsVO vo = new SpecimenStatisticsVO();
        
        vo.setTotalCount(specimenMapper.selectCount(new LambdaQueryWrapper<>()));
        vo.setPendingCount(specimenMapper.countByStatus(SpecimenStatusEnum.PENDING.getCode()));
        vo.setReceivedCount(specimenMapper.countByStatus(SpecimenStatusEnum.RECEIVED.getCode()));
        vo.setTestingCount(specimenMapper.countByStatus(SpecimenStatusEnum.TESTING.getCode()));
        vo.setCompletedCount(specimenMapper.countByStatus(SpecimenStatusEnum.COMPLETED.getCode()));
        vo.setRejectedCount(specimenMapper.countByStatus(SpecimenStatusEnum.REJECTED.getCode()));
        vo.setStatCount(specimenMapper.countStat());
        vo.setPanicCount(specimenTestItemMapper.countPanic());
        vo.setAbnormalCount(specimenTestItemMapper.countAbnormal());
        vo.setTodayCollectCount(specimenMapper.countTodayCollected());
        vo.setTodayReceiveCount(specimenMapper.countTodayReceived());
        vo.setTodayCompleteCount(specimenMapper.countByStatus(SpecimenStatusEnum.COMPLETED.getCode()));
        
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addition(SpecimenAdditionDTO dto) {
        SpecimenDO specimen = specimenMapper.selectById(dto.getSpecimenId());
        if (specimen == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "标本不存在");
        }
        
        if (!CollectionUtils.isEmpty(dto.getTestItemIds())) {
            for (Long itemId : dto.getTestItemIds()) {
                TestItemDO testItem = testItemMapper.selectById(itemId);
                if (testItem != null) {
                    SpecimenTestItemDO testItemDO = new SpecimenTestItemDO();
                    testItemDO.setSpecimenId(dto.getSpecimenId());
                    testItemDO.setSpecimenNo(dto.getSpecimenNo());
                    testItemDO.setTestItemId(itemId);
                    testItemDO.setItemCode(testItem.getItemCode());
                    testItemDO.setItemName(testItem.getItemName());
                    testItemDO.setUnit(testItem.getUnit());
                    testItemDO.setReferenceLow(testItem.getReferenceLow());
                    testItemDO.setReferenceHigh(testItem.getReferenceHigh());
                    testItemDO.setReferenceText(testItem.getReferenceText());
                    testItemDO.setStatus("pending");
                    testItemDO.setIsPanic(0);
                    testItemDO.setIsAbnormal(0);
                    testItemDO.setCreateTime(LocalDateTime.now());
                    specimenTestItemMapper.insert(testItemDO);
                }
            }
        }
        
        log.info("标本附加检验项目成功: specimenNo={}, items={}", dto.getSpecimenNo(), dto.getTestItemIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void printBarcode(Long specimenId) {
        SpecimenDO specimen = specimenMapper.selectById(specimenId);
        if (specimen == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "标本不存在");
        }
        
        specimen.setIsPrint(1);
        specimen.setUpdateTime(LocalDateTime.now());
        specimenMapper.updateById(specimen);
        
        log.info("标本条码打印标记成功: specimenId={}", specimenId);
    }

    @Override
    public List<SpecimenTypeVO> listSpecimenTypes() {
        LambdaQueryWrapper<SpecimenTypeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpecimenTypeDO::getStatus, 0);
        wrapper.orderByAsc(SpecimenTypeDO::getSort);
        
        List<SpecimenTypeDO> list = specimenTypeMapper.selectList(wrapper);
        return list.stream()
                .map(this::convertToTypeVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestItemVO> listTestItems() {
        LambdaQueryWrapper<TestItemDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TestItemDO::getStatus, 0);
        wrapper.orderByAsc(TestItemDO::getSort);
        
        List<TestItemDO> list = testItemMapper.selectList(wrapper);
        return list.stream()
                .map(this::convertToTestItemVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listTestItemCategories() {
        LambdaQueryWrapper<TestItemDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TestItemDO::getStatus, 0);
        wrapper.isNotNull(TestItemDO::getCategoryId);
        wrapper.select(TestItemDO::getCategoryId);
        
        List<TestItemDO> list = testItemMapper.selectList(wrapper);
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<Long> categoryIds = list.stream()
                .map(TestItemDO::getCategoryId)
                .distinct()
                .collect(Collectors.toList());
        
        LambdaQueryWrapper<TestItemCategoryDO> categoryWrapper = new LambdaQueryWrapper<>();
        categoryWrapper.in(TestItemCategoryDO::getId, categoryIds);
        List<TestItemCategoryDO> categories = testItemCategoryMapper.selectList(categoryWrapper);
        
        return categories.stream()
                .map(TestItemCategoryDO::getCategoryName)
                .collect(Collectors.toList());
    }

    private SpecimenDO getSpecimenByBarcode(String barcode) {
        LambdaQueryWrapper<SpecimenDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpecimenDO::getBarcode, barcode);
        SpecimenDO specimen = specimenMapper.selectOne(wrapper);
        if (specimen == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "标本不存在");
        }
        return specimen;
    }

    private void saveTrace(Long specimenId, String specimenNo, String action,
                          String fromStatus, String toStatus, String fromLocation, String toLocation,
                          Long operatorId, String operatorName, String remark) {
        SpecimenTraceDO trace = new SpecimenTraceDO();
        trace.setSpecimenId(specimenId);
        trace.setSpecimenNo(specimenNo);
        trace.setAction(action);
        trace.setActionName(SpecimenActionEnum.getByCode(action).getDesc());
        trace.setFromStatus(fromStatus);
        trace.setToStatus(toStatus);
        trace.setFromLocation(fromLocation);
        trace.setToLocation(toLocation);
        trace.setOperatorId(operatorId);
        trace.setOperatorName(operatorName);
        trace.setOperateTime(LocalDateTime.now());
        trace.setRemark(remark);
        specimenTraceMapper.insert(trace);
    }

    private SpecimenActionEnum getActionByStatus(String status) {
        if (SpecimenStatusEnum.TESTING.getCode().equals(status)) {
            return SpecimenActionEnum.TESTING;
        } else if (SpecimenStatusEnum.COMPLETED.getCode().equals(status)) {
            return SpecimenActionEnum.COMPLETE;
        } else if (SpecimenStatusEnum.CANCELLED.getCode().equals(status)) {
            return SpecimenActionEnum.CANCEL;
        } else if (SpecimenStatusEnum.STORED.getCode().equals(status)) {
            return SpecimenActionEnum.STORAGE;
        }
        return SpecimenActionEnum.RECEIVE;
    }

    private SpecimenDetailVO convertToDetailVO(SpecimenDO specimen) {
        SpecimenDetailVO vo = new SpecimenDetailVO();
        BeanUtil.copyProperties(specimen, vo);
        vo.setStatusName(SpecimenStatusEnum.getDescByCode(specimen.getStatus()));
        
        List<SpecimenTestItemDO> testItems = specimenTestItemMapper.selectBySpecimenId(specimen.getId());
        vo.setTestItems(testItems.stream()
                .map(this::convertToTestItemVO)
                .collect(Collectors.toList()));
        
        List<SpecimenTraceDO> traces = specimenTraceMapper.selectBySpecimenId(specimen.getId());
        vo.setTraces(traces.stream()
                .map(this::convertToTraceVO)
                .collect(Collectors.toList()));
        
        return vo;
    }

    private SpecimenListVO convertToListVO(SpecimenDO specimen) {
        SpecimenListVO vo = new SpecimenListVO();
        BeanUtil.copyProperties(specimen, vo);
        vo.setStatusName(SpecimenStatusEnum.getDescByCode(specimen.getStatus()));
        vo.setTestItemCount(specimenTestItemMapper.countBySpecimenId(specimen.getId()));
        return vo;
    }

    private SpecimenTestItemVO convertToTestItemVO(SpecimenTestItemDO item) {
        SpecimenTestItemVO vo = new SpecimenTestItemVO();
        BeanUtil.copyProperties(item, vo);
        vo.setStatusName(getTestItemStatusName(item.getStatus()));
        return vo;
    }

    private SpecimenTraceVO convertToTraceVO(SpecimenTraceDO trace) {
        SpecimenTraceVO vo = new SpecimenTraceVO();
        BeanUtil.copyProperties(trace, vo);
        vo.setFromStatusName(SpecimenStatusEnum.getDescByCode(trace.getFromStatus()));
        vo.setToStatusName(SpecimenStatusEnum.getDescByCode(trace.getToStatus()));
        return vo;
    }

    private SpecimenTypeVO convertToTypeVO(SpecimenTypeDO type) {
        SpecimenTypeVO vo = new SpecimenTypeVO();
        BeanUtil.copyProperties(type, vo);
        return vo;
    }

    private TestItemVO convertToTestItemVO(TestItemDO item) {
        TestItemVO vo = new TestItemVO();
        BeanUtil.copyProperties(item, vo);
        return vo;
    }

    private String getTestItemStatusName(String status) {
        if ("pending".equals(status)) {
            return "待检验";
        } else if ("testing".equals(status)) {
            return "检验中";
        } else if ("completed".equals(status)) {
            return "已完成";
        }
        return "";
    }
}
