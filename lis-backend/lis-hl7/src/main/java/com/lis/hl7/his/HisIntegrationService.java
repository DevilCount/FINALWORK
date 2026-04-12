package com.lis.hl7.his;

import ca.uhn.hl7v2.HL7Exception;
import com.alibaba.fastjson2.JSON;
import com.lis.common.exception.BusinessException;
import com.lis.hl7.builder.Hl7MessageBuilder;
import com.lis.hl7.converter.Hl7ToReportConverter;
import com.lis.hl7.converter.Hl7ToSpecimenConverter;
import com.lis.hl7.dto.HisLabOrderDTO;
import com.lis.hl7.entity.Hl7MessageDO;
import com.lis.hl7.entity.InterfaceConfigDO;
import com.lis.hl7.enums.MessageDirectionEnum;
import com.lis.hl7.enums.ProcessStatusEnum;
import com.lis.hl7.feign.ReportFeignClient;
import com.lis.hl7.feign.SpecimenFeignClient;
import com.lis.hl7.parser.Hl7MessageParser;
import com.lis.hl7.service.Hl7MessageService;
import com.lis.hl7.service.InterfaceConfigService;
import com.lis.hl7.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HisIntegrationService {

    private final HisMessageClient hisMessageClient;
    private final Hl7MessageParser messageParser;
    private final Hl7MessageBuilder messageBuilder;
    private final Hl7MessageService hl7MessageService;
    private final InterfaceConfigService interfaceConfigService;
    private final SpecimenFeignClient specimenFeignClient;
    private final ReportFeignClient reportFeignClient;
    private final Hl7ToSpecimenConverter hl7ToSpecimenConverter;
    private final Hl7ToReportConverter hl7ToReportConverter;

    public Hl7MessageDO sendPatientAdmit(String interfaceCode, Hl7PatientVO patient, Hl7VisitVO visit) {
        return executeOutbound(interfaceCode, "ADT", "A01", () -> {
            return messageBuilder.buildAdtA01(patient, visit);
        });
    }

    public Hl7MessageDO sendLabResult(String interfaceCode, Hl7PatientVO patient, 
                                       Hl7VisitVO visit, List<Hl7ObservationVO> observations) {
        return executeOutbound(interfaceCode, "ORU", "R01", () -> {
            return messageBuilder.buildOruR01(patient, visit, observations);
        });
    }

    public Hl7MessageDO sendLabOrder(String interfaceCode, HisLabOrderDTO labOrder) {
        return executeOutbound(interfaceCode, "ORM", "O01", () -> {
            return messageBuilder.buildOrmO01(labOrder);
        });
    }

    public Hl7MessageDO sendCustomMessage(String interfaceCode, String messageType, String triggerEvent,
                                           Hl7PatientVO patient, Hl7VisitVO visit,
                                           List<Hl7OrderVO> orders, List<Hl7ObservationVO> observations) {
        return executeOutbound(interfaceCode, messageType, triggerEvent, () -> {
            return messageBuilder.buildCustomMessage(messageType, triggerEvent, patient, visit, orders, observations);
        });
    }

    public Hl7MessageDO sendMessage(String interfaceCode, String hl7Message) {
        return executeOutbound(interfaceCode, null, null, () -> hl7Message);
    }

    private Hl7MessageDO executeOutbound(String interfaceCode, String messageType, String triggerEvent,
                                          MessageBuilder builder) {
        Hl7MessageDO messageDO = new Hl7MessageDO();
        long startTime = System.currentTimeMillis();

        try {
            InterfaceConfigDO config = interfaceConfigService.getByCode(interfaceCode);
            if (config == null) {
                throw new BusinessException("接口配置不存在: " + interfaceCode);
            }

            messageDO.setInterfaceId(config.getId());
            messageDO.setInterfaceCode(interfaceCode);
            messageDO.setDirection(MessageDirectionEnum.OUTBOUND.getCode());
            messageDO.setProcessStatus(ProcessStatusEnum.PROCESSING.getCode());
            messageDO.setCreateTime(LocalDateTime.now());

            String message = builder.build();
            messageDO.setRawMessage(message);

            if (messageType == null) {
                messageType = messageParser.getMessageType(message);
                triggerEvent = messageParser.getTriggerEvent(message);
            }

            messageDO.setMessageType(messageType);
            messageDO.setTriggerEvent(triggerEvent);
            messageDO.setMessageControlId(messageParser.getMessageControlId(message));

            try {
                Map<String, Object> parsedMap = messageParser.parseToMap(message);
                messageDO.setParsedMessage(JSON.toJSONString(parsedMap));
                extractPatientInfo(messageDO, parsedMap);
            } catch (Exception e) {
                log.warn("Failed to parse outbound message", e);
            }

            String response = hisMessageClient.sendAndValidate(config, message);
            messageDO.setAckMessage(response);

            String ackStatus = extractAckStatus(response);
            messageDO.setAckStatus(ackStatus);
            messageDO.setAckCode(ackStatus);

            if ("AA".equals(ackStatus)) {
                messageDO.setProcessStatus(ProcessStatusEnum.SUCCESS.getCode());
            } else {
                messageDO.setProcessStatus(ProcessStatusEnum.FAILED.getCode());
                messageDO.setErrorMsg("HIS returned non-success ACK: " + ackStatus);
            }

            interfaceConfigService.incrementSentCount(config.getId());

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error sending outbound message", e);
            messageDO.setProcessStatus(ProcessStatusEnum.FAILED.getCode());
            messageDO.setErrorCode("SEND_ERROR");
            messageDO.setErrorMsg(e.getMessage());
        } finally {
            messageDO.setProcessTime(LocalDateTime.now());
            messageDO.setProcessDuration(System.currentTimeMillis() - startTime);

            try {
                hl7MessageService.saveMessage(messageDO);
            } catch (Exception e) {
                log.error("Failed to save outbound message", e);
            }
        }

        return messageDO;
    }

    public void processInboundMessage(String rawMessage, String interfaceCode) {
        Hl7MessageDO messageDO = new Hl7MessageDO();
        long startTime = System.currentTimeMillis();

        try {
            InterfaceConfigDO config = interfaceConfigService.getByCode(interfaceCode);

            messageDO.setInterfaceCode(interfaceCode);
            if (config != null) {
                messageDO.setInterfaceId(config.getId());
            }
            messageDO.setDirection(MessageDirectionEnum.INBOUND.getCode());
            messageDO.setRawMessage(rawMessage);
            messageDO.setProcessStatus(ProcessStatusEnum.PROCESSING.getCode());
            messageDO.setCreateTime(LocalDateTime.now());

            String messageType = messageParser.getMessageType(rawMessage);
            String triggerEvent = messageParser.getTriggerEvent(rawMessage);
            String messageControlId = messageParser.getMessageControlId(rawMessage);

            messageDO.setMessageType(messageType);
            messageDO.setTriggerEvent(triggerEvent);
            messageDO.setMessageControlId(messageControlId);

            Hl7ParsedMessageVO parsedMessage = messageParser.parse(rawMessage);
            messageDO.setParsedMessage(JSON.toJSONString(parsedMessage));

            if (parsedMessage.getPatient() != null) {
                messageDO.setPatientId(parsedMessage.getPatient().getPatientId());
                messageDO.setPatientName(parsedMessage.getPatient().getPatientName());
            }

            if (parsedMessage.getVisit() != null) {
                messageDO.setVisitNo(parsedMessage.getVisit().getVisitNo());
            }

            processByMessageType(messageDO, parsedMessage);

            String ack = messageBuilder.buildAckSuccess(messageControlId);
            messageDO.setAckMessage(ack);
            messageDO.setAckStatus("AA");
            messageDO.setProcessStatus(ProcessStatusEnum.SUCCESS.getCode());

            if (config != null) {
                interfaceConfigService.incrementReceivedCount(config.getId());
            }

        } catch (Exception e) {
            log.error("Error processing inbound message", e);
            messageDO.setProcessStatus(ProcessStatusEnum.FAILED.getCode());
            messageDO.setErrorCode("PROCESS_ERROR");
            messageDO.setErrorMsg(e.getMessage());

            try {
                String messageControlId = messageParser.getMessageControlId(rawMessage);
                String ack = messageBuilder.buildAckError(messageControlId, e.getMessage());
                messageDO.setAckMessage(ack);
                messageDO.setAckStatus("AE");
            } catch (Exception ex) {
                log.error("Failed to build error ACK", ex);
            }
        } finally {
            messageDO.setProcessTime(LocalDateTime.now());
            messageDO.setProcessDuration(System.currentTimeMillis() - startTime);

            try {
                hl7MessageService.saveMessage(messageDO);
            } catch (Exception e) {
                log.error("Failed to save inbound message", e);
            }
        }
    }

    private void processByMessageType(Hl7MessageDO messageDO, Hl7ParsedMessageVO parsedMessage) {
        String messageType = parsedMessage.getMessageType();
        String triggerEvent = parsedMessage.getTriggerEvent();

        log.info("Processing message type: {}^{}", messageType, triggerEvent);

        switch (messageType) {
            case "ADT":
                processAdtMessage(messageDO, parsedMessage, triggerEvent);
                break;
            case "ORM":
                processOrmMessage(messageDO, parsedMessage);
                break;
            case "ORU":
                processOruMessage(messageDO, parsedMessage);
                break;
            case "QRY":
                processQryMessage(messageDO, parsedMessage);
                break;
            default:
                log.warn("Unknown message type: {}", messageType);
        }
    }

    private void processAdtMessage(Hl7MessageDO messageDO, Hl7ParsedMessageVO parsedMessage, String triggerEvent) {
        log.info("Processing ADT message with trigger event: {}", triggerEvent);

        Hl7PatientVO patient = parsedMessage.getPatient();
        Hl7VisitVO visit = parsedMessage.getVisit();

        switch (triggerEvent) {
            case "A01":
                log.info("Patient admit: patientId={}, name={}", 
                        patient != null ? patient.getPatientId() : null,
                        patient != null ? patient.getPatientName() : null);
                break;
            case "A02":
                log.info("Patient transfer");
                break;
            case "A03":
                log.info("Patient discharge");
                break;
            case "A04":
                log.info("Patient registration");
                break;
            case "A05":
                log.info("Patient pre-admit");
                break;
            case "A08":
                log.info("Patient update");
                break;
            default:
                log.info("Other ADT event: {}", triggerEvent);
        }
    }

    private void processOrmMessage(Hl7MessageDO messageDO, Hl7ParsedMessageVO parsedMessage) {
        log.info("Processing ORM message (lab order)");

        Hl7PatientVO patient = parsedMessage.getPatient();
        Hl7VisitVO visit = parsedMessage.getVisit();
        List<Hl7OrderVO> orders = parsedMessage.getOrders();

        try {
            Map<String, Object> specimenDTO = hl7ToSpecimenConverter.convert(patient, visit, orders);
            com.lis.common.result.Result<?> specimenResult = specimenFeignClient.register(specimenDTO);
            if (specimenResult != null && specimenResult.isSuccess()) {
                log.info("ORM消息处理: 标本登记成功");

                Map<String, Object> reportDTO = hl7ToReportConverter.convert(patient, visit, orders);
                if (specimenResult.getData() != null) {
                    try {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> dataMap = (Map<String, Object>) specimenResult.getData();
                        if (dataMap != null && dataMap.get("id") != null) {
                            reportDTO.put("specimenId", Long.parseLong(dataMap.get("id").toString()));
                        }
                    } catch (Exception e) {
                        log.warn("解析标本ID失败", e);
                    }
                }
                com.lis.common.result.Result<?> reportResult = reportFeignClient.createReportApply(reportDTO);
                if (reportResult != null && reportResult.isSuccess()) {
                    log.info("ORM消息处理: 检验申请创建成功");
                } else {
                    log.warn("ORM消息处理: 检验申请创建失败");
                }
            } else {
                log.warn("ORM消息处理: 标本登记失败");
            }
        } catch (Exception e) {
            log.error("ORM消息处理业务逻辑异常", e);
        }

        if (orders != null && !orders.isEmpty()) {
            for (Hl7OrderVO order : orders) {
                log.info("Order: code={}, name={}, specimen={}", 
                        order.getOrderItemCode(), order.getOrderItemName(), order.getSpecimenType());
            }
        }
    }

    private void processOruMessage(Hl7MessageDO messageDO, Hl7ParsedMessageVO parsedMessage) {
        log.info("Processing ORU message (lab result)");

        List<Hl7ObservationVO> observations = parsedMessage.getObservations();
        if (observations != null && !observations.isEmpty()) {
            for (Hl7ObservationVO observation : observations) {
                log.info("Observation: id={}, name={}, value={}, unit={}", 
                        observation.getObservationId(), observation.getObservationName(),
                        observation.getObservationValue(), observation.getUnits());
            }
        }
    }

    private void processQryMessage(Hl7MessageDO messageDO, Hl7ParsedMessageVO parsedMessage) {
        log.info("Processing QRY message (query)");
    }

    private void extractPatientInfo(Hl7MessageDO messageDO, Map<String, Object> parsedMap) {
        try {
            Object patient = parsedMap.get("patient");
            if (patient instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> patientMap = (Map<String, Object>) patient;
                messageDO.setPatientId((String) patientMap.get("patientId"));
                messageDO.setPatientName((String) patientMap.get("patientName"));
            }

            Object visit = parsedMap.get("visit");
            if (visit instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> visitMap = (Map<String, Object>) visit;
                messageDO.setVisitNo((String) visitMap.get("visitNo"));
            }
        } catch (Exception e) {
            log.warn("Failed to extract patient info", e);
        }
    }

    private String extractAckStatus(String ackMessage) {
        try {
            String[] lines = ackMessage.split("\r|\n");
            for (String line : lines) {
                if (line.startsWith("MSA|")) {
                    String[] fields = line.split("\\|");
                    if (fields.length >= 2) {
                        return fields[1];
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Failed to extract ACK status", e);
        }
        return null;
    }

    @FunctionalInterface
    private interface MessageBuilder {
        String build() throws HL7Exception;
    }
}
