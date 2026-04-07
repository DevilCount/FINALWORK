package com.lis.hl7.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lis.hl7.entity.Hl7MessageDO;
import com.lis.hl7.entity.Hl7MessageSegmentDO;
import com.lis.hl7.mapper.Hl7MessageMapper;
import com.lis.hl7.mapper.Hl7MessageSegmentMapper;
import com.lis.hl7.service.Hl7MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Hl7MessageServiceImpl extends ServiceImpl<Hl7MessageMapper, Hl7MessageDO> implements Hl7MessageService {

    private final Hl7MessageSegmentMapper segmentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMessage(Hl7MessageDO message) {
        save(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMessageWithSegments(Hl7MessageDO message, List<Hl7MessageSegmentDO> segments) {
        save(message);

        if (segments != null && !segments.isEmpty()) {
            for (Hl7MessageSegmentDO segment : segments) {
                segment.setMessageId(message.getId());
                segmentMapper.insert(segment);
            }
        }
    }

    @Override
    public Hl7MessageDO getByMessageControlId(String messageControlId) {
        return getOne(new LambdaQueryWrapper<Hl7MessageDO>()
                .eq(Hl7MessageDO::getMessageControlId, messageControlId)
                .last("LIMIT 1"));
    }

    @Override
    public List<Hl7MessageDO> listByInterfaceId(Long interfaceId) {
        return list(new LambdaQueryWrapper<Hl7MessageDO>()
                .eq(Hl7MessageDO::getInterfaceId, interfaceId)
                .orderByDesc(Hl7MessageDO::getCreateTime));
    }

    @Override
    public List<Hl7MessageDO> listByPatientId(String patientId) {
        return list(new LambdaQueryWrapper<Hl7MessageDO>()
                .eq(Hl7MessageDO::getPatientId, patientId)
                .orderByDesc(Hl7MessageDO::getCreateTime));
    }

    @Override
    public List<Hl7MessageDO> listByVisitNo(String visitNo) {
        return list(new LambdaQueryWrapper<Hl7MessageDO>()
                .eq(Hl7MessageDO::getVisitNo, visitNo)
                .orderByDesc(Hl7MessageDO::getCreateTime));
    }

    @Override
    public void updateProcessStatus(Long id, String status, String errorCode, String errorMsg) {
        Hl7MessageDO message = new Hl7MessageDO();
        message.setId(id);
        message.setProcessStatus(status);
        message.setErrorCode(errorCode);
        message.setErrorMsg(errorMsg);
        updateById(message);
    }

    @Override
    public void incrementRetryCount(Long id) {
        Hl7MessageDO message = getById(id);
        if (message != null) {
            message.setRetryCount(message.getRetryCount() != null ? message.getRetryCount() + 1 : 1);
            updateById(message);
        }
    }
}
