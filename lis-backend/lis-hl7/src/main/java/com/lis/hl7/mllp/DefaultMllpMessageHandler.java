package com.lis.hl7.mllp;

import ca.uhn.hl7v2.HL7Exception;
import com.alibaba.fastjson2.JSON;
import com.lis.hl7.builder.Hl7MessageBuilder;
import com.lis.hl7.entity.Hl7MessageDO;
import com.lis.hl7.entity.Hl7MessageSegmentDO;
import com.lis.hl7.entity.InterfaceConnectionLogDO;
import com.lis.hl7.enums.MessageDirectionEnum;
import com.lis.hl7.enums.ProcessStatusEnum;
import com.lis.hl7.parser.Hl7MessageParser;
import com.lis.hl7.service.Hl7MessageService;
import com.lis.hl7.service.InterfaceConnectionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultMllpMessageHandler implements MllpMessageHandler {

    private final Hl7MessageParser messageParser;
    private final Hl7MessageBuilder messageBuilder;
    private final Hl7MessageService hl7MessageService;
    private final InterfaceConnectionLogService connectionLogService;

    @Override
    public String handleMessage(String message, String clientIp) throws Exception {
        log.info("Processing HL7 message from client: {}", clientIp);

        long startTime = System.currentTimeMillis();
        Hl7MessageDO messageDO = new Hl7MessageDO();

        try {
            messageDO.setDirection(MessageDirectionEnum.INBOUND.getCode());
            messageDO.setRawMessage(message);
            messageDO.setProcessStatus(ProcessStatusEnum.PROCESSING.getCode());
            messageDO.setCreateTime(LocalDateTime.now());

            String messageType = messageParser.getMessageType(message);
            String triggerEvent = messageParser.getTriggerEvent(message);
            String messageControlId = messageParser.getMessageControlId(message);

            messageDO.setMessageType(messageType);
            messageDO.setTriggerEvent(triggerEvent);
            messageDO.setMessageControlId(messageControlId);

            try {
                Map<String, Object> parsedMap = messageParser.parseToMap(message);
                messageDO.setParsedMessage(JSON.toJSONString(parsedMap));

                extractPatientInfo(messageDO, parsedMap);
            } catch (HL7Exception e) {
                log.error("Failed to parse HL7 message", e);
                messageDO.setProcessStatus(ProcessStatusEnum.FAILED.getCode());
                messageDO.setErrorCode("PARSE_ERROR");
                messageDO.setErrorMsg(e.getMessage());

                String ack = messageBuilder.buildAckError(messageControlId, "Message parsing failed: " + e.getMessage());
                messageDO.setAckMessage(ack);
                messageDO.setAckStatus("AE");

                return ack;
            }

            try {
                List<Map<String, Object>> segments = messageParser.parseSegments(message);
                messageDO.setParsedMessage(JSON.toJSONString(segments));
            } catch (Exception e) {
                log.warn("Failed to parse segments", e);
            }

            String ack = messageBuilder.buildAckSuccess(messageControlId);
            messageDO.setAckMessage(ack);
            messageDO.setAckStatus("AA");
            messageDO.setProcessStatus(ProcessStatusEnum.SUCCESS.getCode());
            messageDO.setProcessTime(LocalDateTime.now());
            messageDO.setProcessDuration(System.currentTimeMillis() - startTime);

            return ack;

        } catch (Exception e) {
            log.error("Error processing HL7 message", e);
            messageDO.setProcessStatus(ProcessStatusEnum.FAILED.getCode());
            messageDO.setErrorCode("PROCESS_ERROR");
            messageDO.setErrorMsg(e.getMessage());
            messageDO.setProcessTime(LocalDateTime.now());
            messageDO.setProcessDuration(System.currentTimeMillis() - startTime);

            try {
                String messageControlId = messageParser.getMessageControlId(message);
                String ack = messageBuilder.buildAckError(messageControlId, "Processing error: " + e.getMessage());
                messageDO.setAckMessage(ack);
                messageDO.setAckStatus("AE");
                return ack;
            } catch (Exception ex) {
                log.error("Failed to build error ACK", ex);
                return null;
            }
        } finally {
            try {
                hl7MessageService.saveMessage(messageDO);
            } catch (Exception e) {
                log.error("Failed to save HL7 message", e);
            }
        }
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
            log.warn("Failed to extract patient info from parsed message", e);
        }
    }

    @Override
    public void onConnect(String clientIp) {
        log.info("Client connected: {}", clientIp);

        try {
            InterfaceConnectionLogDO log = new InterfaceConnectionLogDO();
            log.setEventType("connect");
            log.setClientIp(clientIp);
            log.setEventTime(LocalDateTime.now());
            log.setEventDesc("Client connected");
            connectionLogService.save(log);
        } catch (Exception e) {
            log.error("Failed to save connection log", e);
        }
    }

    @Override
    public void onDisconnect(String clientIp) {
        log.info("Client disconnected: {}", clientIp);

        try {
            InterfaceConnectionLogDO log = new InterfaceConnectionLogDO();
            log.setEventType("disconnect");
            log.setClientIp(clientIp);
            log.setEventTime(LocalDateTime.now());
            log.setEventDesc("Client disconnected");
            connectionLogService.save(log);
        } catch (Exception e) {
            log.error("Failed to save disconnection log", e);
        }
    }

    @Override
    public void onError(String clientIp, String error) {
        log.error("Client error: {} - {}", clientIp, error);

        try {
            InterfaceConnectionLogDO log = new InterfaceConnectionLogDO();
            log.setEventType("error");
            log.setClientIp(clientIp);
            log.setEventTime(LocalDateTime.now());
            log.setEventDesc("Connection error");
            log.setErrorMsg(error);
            connectionLogService.save(log);
        } catch (Exception e) {
            log.error("Failed to save error log", e);
        }
    }
}
