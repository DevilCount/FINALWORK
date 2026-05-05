package com.lis.hl7.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lis.hl7.entity.Hl7MessageDO;
import com.lis.hl7.entity.Hl7MessageSegmentDO;

import java.util.List;

public interface Hl7MessageService extends IService<Hl7MessageDO> {

    void saveMessage(Hl7MessageDO message);

    void saveMessageWithSegments(Hl7MessageDO message, List<Hl7MessageSegmentDO> segments);

    Hl7MessageDO getByMessageControlId(String messageControlId);

    List<Hl7MessageDO> listByInterfaceId(Long interfaceId);

    List<Hl7MessageDO> listByPatientId(String patientId);

    List<Hl7MessageDO> listByVisitNo(String visitNo);

    void updateProcessStatus(Long id, String status, String errorCode, String errorMsg);

    void incrementRetryCount(Long id);
}
