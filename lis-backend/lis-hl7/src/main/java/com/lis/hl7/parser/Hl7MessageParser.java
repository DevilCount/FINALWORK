package com.lis.hl7.parser;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v25.message.ADT_A01;
import ca.uhn.hl7v2.model.v25.message.ORU_R01;
import ca.uhn.hl7v2.model.v25.message.QRY_Q01;
import ca.uhn.hl7v2.model.v25.message.ACK;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import ca.uhn.hl7v2.model.v25.segment.PID;
import ca.uhn.hl7v2.model.v25.segment.PV1;
import ca.uhn.hl7v2.model.v25.segment.ORC;
import ca.uhn.hl7v2.model.v25.segment.OBR;
import ca.uhn.hl7v2.model.v25.segment.OBX;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.util.Terser;
import com.alibaba.fastjson2.JSON;
import com.lis.hl7.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class Hl7MessageParser {

    private final PipeParser parser = new PipeParser();

    private static final DateTimeFormatter HL7_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public Hl7ParsedMessageVO parse(String rawMessage) throws HL7Exception {
        Message message = parser.parse(rawMessage);
        Hl7ParsedMessageVO result = new Hl7ParsedMessageVO();

        MSH msh = (MSH) message.get("MSH");
        result.setMessageType(msh.getMessageType().getMessageCode().getValue());
        result.setTriggerEvent(msh.getMessageType().getTriggerEvent().getValue());
        result.setMessageControlId(msh.getMessageControlID().getValue());
        result.setProcessingId(msh.getProcessingID().getProcessingID().getValue());
        result.setVersionId(msh.getVersionID().getVersionID().getValue());
        result.setSendingApp(msh.getSendingApplication().getNamespaceID().getValue());
        result.setSendingFacility(msh.getSendingFacility().getNamespaceID().getValue());
        result.setReceivingApp(msh.getReceivingApplication().getNamespaceID().getValue());
        result.setReceivingFacility(msh.getReceivingFacility().getNamespaceID().getValue());
        result.setMessageTime(msh.getDateTimeOfMessage().getTime().getValue());

        String messageType = result.getMessageType();
        String triggerEvent = result.getTriggerEvent();

        if ("ADT".equals(messageType)) {
            parseAdtMessage(message, result, triggerEvent);
        } else if ("ORM".equals(messageType)) {
            parseOrmMessage(message, result);
        } else if ("ORU".equals(messageType)) {
            parseOruMessage(message, result);
        } else if ("QRY".equals(messageType)) {
            parseQryMessage(message, result);
        }

        return result;
    }

    private void parseAdtMessage(Message message, Hl7ParsedMessageVO result, String triggerEvent) throws HL7Exception {
        try {
            ADT_A01 adt = (ADT_A01) message;

            PID pid = adt.getPID();
            Hl7PatientVO patient = parsePatient(pid);
            result.setPatient(patient);

            try {
                PV1 pv1 = adt.getPV1();
                Hl7VisitVO visit = parseVisit(pv1);
                result.setVisit(visit);
            } catch (Exception e) {
                log.debug("PV1 segment not found or error parsing: {}", e.getMessage());
            }
        } catch (Exception e) {
            log.error("Error parsing ADT message", e);
            throw new HL7Exception("Error parsing ADT message: " + e.getMessage());
        }
    }

    private void parseOrmMessage(Message message, Hl7ParsedMessageVO result) throws HL7Exception {
        try {
            Terser terser = new Terser(message);

            String pidSegment = terser.get("/.PID");
            if (pidSegment != null && !pidSegment.isEmpty()) {
                PID pid = (PID) message.get("PID");
                Hl7PatientVO patient = parsePatient(pid);
                result.setPatient(patient);
            }

            String pv1Segment = terser.get("/.PV1");
            if (pv1Segment != null && !pv1Segment.isEmpty()) {
                PV1 pv1 = (PV1) message.get("PV1");
                Hl7VisitVO visit = parseVisit(pv1);
                result.setVisit(visit);
            }

            List<Hl7OrderVO> orders = new ArrayList<>();
            int orcCount = 0;
            try {
                while (terser.get("/.ORC(" + orcCount + ")") != null) {
                    Hl7OrderVO order = parseOrder(message, orcCount);
                    orders.add(order);
                    orcCount++;
                }
            } catch (Exception e) {
                log.debug("No more ORC segments");
            }
            result.setOrders(orders);
        } catch (Exception e) {
            log.error("Error parsing ORM message", e);
            throw new HL7Exception("Error parsing ORM message: " + e.getMessage());
        }
    }

    private void parseOruMessage(Message message, Hl7ParsedMessageVO result) throws HL7Exception {
        try {
            ORU_R01 oru = (ORU_R01) message;

            PID pid = oru.getPATIENT_RESULT().getPATIENT().getPID();
            Hl7PatientVO patient = parsePatient(pid);
            result.setPatient(patient);

            try {
                PV1 pv1 = oru.getPATIENT_RESULT().getPATIENT().getVISIT().getPV1();
                Hl7VisitVO visit = parseVisit(pv1);
                result.setVisit(visit);
            } catch (Exception e) {
                log.debug("PV1 segment not found in ORU message");
            }

            List<Hl7ObservationVO> observations = new ArrayList<>();
            int obxCount = oru.getPATIENT_RESULT().getORDER_OBSERVATIONReps();
            for (int i = 0; i < obxCount; i++) {
                int obxRepCount = oru.getPATIENT_RESULT().getORDER_OBSERVATION(i).getOBSERVATIONReps();
                for (int j = 0; j < obxRepCount; j++) {
                    OBX obx = oru.getPATIENT_RESULT().getORDER_OBSERVATION(i).getOBSERVATION(j).getOBX();
                    Hl7ObservationVO observation = parseObservation(obx, j);
                    observations.add(observation);
                }
            }
            result.setObservations(observations);
        } catch (Exception e) {
            log.error("Error parsing ORU message", e);
            throw new HL7Exception("Error parsing ORU message: " + e.getMessage());
        }
    }

    private void parseQryMessage(Message message, Hl7ParsedMessageVO result) throws HL7Exception {
        try {
            QRY_Q01 qry = (QRY_Q01) message;
            log.info("Parsing QRY message for query purposes");
        } catch (Exception e) {
            log.error("Error parsing QRY message", e);
            throw new HL7Exception("Error parsing QRY message: " + e.getMessage());
        }
    }

    private Hl7PatientVO parsePatient(PID pid) throws HL7Exception {
        Hl7PatientVO patient = new Hl7PatientVO();

        try {
            patient.setPatientId(pid.getPatientID().getIDNumber().getValue());
        } catch (Exception e) {
            log.debug("Patient ID not found");
        }

        try {
            String lastName = pid.getPatientName(0).getFamilyName().getSurname().getValue();
            String firstName = pid.getPatientName(0).getGivenName().getValue();
            patient.setPatientName((lastName != null ? lastName : "") + (firstName != null ? firstName : ""));
        } catch (Exception e) {
            log.debug("Patient name not found");
        }

        try {
            patient.setIdCardNo(pid.getPatientIdentifierList(0).getIDNumber().getValue());
        } catch (Exception e) {
            log.debug("ID card not found");
        }

        try {
            patient.setGender(pid.getAdministrativeSex().getValue());
        } catch (Exception e) {
            log.debug("Gender not found");
        }

        try {
            patient.setBirthDate(pid.getDateTimeOfBirth().getTime().getValue());
        } catch (Exception e) {
            log.debug("Birth date not found");
        }

        try {
            patient.setPhone(pid.getPhoneNumberHome(0).getTelephoneNumber().getValue());
        } catch (Exception e) {
            log.debug("Phone not found");
        }

        try {
            patient.setAddress(pid.getPatientAddress(0).getStreetAddress().getStreetOrMailingAddress().getValue());
        } catch (Exception e) {
            log.debug("Address not found");
        }

        return patient;
    }

    private Hl7VisitVO parseVisit(PV1 pv1) throws HL7Exception {
        Hl7VisitVO visit = new Hl7VisitVO();

        try {
            visit.setVisitNo(pv1.getVisitNumber().getIDNumber().getValue());
        } catch (Exception e) {
            log.debug("Visit number not found");
        }

        try {
            visit.setVisitClass(pv1.getPatientClass().getValue());
        } catch (Exception e) {
            log.debug("Visit class not found");
        }

        try {
            visit.setDepartment(pv1.getAssignedPatientLocation().getPointOfCare().getValue());
        } catch (Exception e) {
            log.debug("Department not found");
        }

        try {
            visit.setWard(pv1.getAssignedPatientLocation().getFacility().getNamespaceID().getValue());
        } catch (Exception e) {
            log.debug("Ward not found");
        }

        try {
            visit.setBedNo(pv1.getAssignedPatientLocation().getBed().getValue());
        } catch (Exception e) {
            log.debug("Bed number not found");
        }

        try {
            visit.setAttendingDoctor(pv1.getAttendingDoctor(0).getIDNumber().getValue());
        } catch (Exception e) {
            log.debug("Attending doctor not found");
        }

        try {
            visit.setAdmissionTime(pv1.getAdmitDateTime().getTime().getValue());
        } catch (Exception e) {
            log.debug("Admission time not found");
        }

        return visit;
    }

    private Hl7OrderVO parseOrder(Message message, int index) throws HL7Exception {
        Hl7OrderVO order = new Hl7OrderVO();
        Terser terser = new Terser(message);

        try {
            order.setOrderControl(terser.get("/.ORC(" + index + ")-1"));
            order.setOrderStatus(terser.get("/.ORC(" + index + ")-5"));
            order.setPlacerOrderNo(terser.get("/.ORC(" + index + ")-2-1"));
            order.setFillerOrderNo(terser.get("/.ORC(" + index + ")-3-1"));
            order.setOrderingDoctor(terser.get("/.ORC(" + index + ")-12-1"));
        } catch (Exception e) {
            log.debug("ORC segment parsing error: {}", e.getMessage());
        }

        try {
            order.setOrderItemCode(terser.get("/.OBR(" + index + ")-4-1"));
            order.setOrderItemName(terser.get("/.OBR(" + index + ")-4-2"));
            order.setSpecimenType(terser.get("/.OBR(" + index + ")-15-2"));
            order.setCollectionTime(terser.get("/.OBR(" + index + ")-7-1"));
            order.setPriority(terser.get("/.OBR(" + index + ")-5"));
            order.setClinicalInfo(terser.get("/.OBR(" + index + ")-13"));
        } catch (Exception e) {
            log.debug("OBR segment parsing error: {}", e.getMessage());
        }

        return order;
    }

    private Hl7ObservationVO parseObservation(OBX obx, int index) throws HL7Exception {
        Hl7ObservationVO observation = new Hl7ObservationVO();

        try {
            observation.setSetId(obx.getSetIDOBX().getValue());
        } catch (Exception e) {
            log.debug("Set ID not found");
        }

        try {
            observation.setObservationId(obx.getObservationIdentifier().getIdentifier().getValue());
            observation.setObservationName(obx.getObservationIdentifier().getText().getValue());
        } catch (Exception e) {
            log.debug("Observation identifier not found");
        }

        try {
            observation.setObservationValue(obx.getObservationValue(0).getData().toString());
        } catch (Exception e) {
            log.debug("Observation value not found");
        }

        try {
            observation.setUnits(obx.getUnits().getIdentifier().getValue());
        } catch (Exception e) {
            log.debug("Units not found");
        }

        try {
            observation.setReferenceRange(obx.getReferencesRange().getValue());
        } catch (Exception e) {
            log.debug("Reference range not found");
        }

        try {
            observation.setAbnormalFlag(obx.getAbnormalFlags(0).getValue());
        } catch (Exception e) {
            log.debug("Abnormal flag not found");
        }

        try {
            observation.setObservationResultStatus(obx.getObservationResultStatus().getValue());
        } catch (Exception e) {
            log.debug("Observation result status not found");
        }

        try {
            observation.setDateTimeOfObservation(obx.getDateTimeOfTheObservation().getTime().getValue());
        } catch (Exception e) {
            log.debug("Date time of observation not found");
        }

        return observation;
    }

    public Map<String, Object> parseToMap(String rawMessage) throws HL7Exception {
        Hl7ParsedMessageVO parsedMessage = parse(rawMessage);
        String json = JSON.toJSONString(parsedMessage);
        return JSON.parseObject(json, Map.class);
    }

    public List<Map<String, Object>> parseSegments(String rawMessage) throws HL7Exception {
        List<Map<String, Object>> segments = new ArrayList<>();
        String[] lines = rawMessage.split("\r|\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            Map<String, Object> segment = new HashMap<>();
            String[] fields = line.split("\\|");

            if (fields.length > 0) {
                segment.put("segmentName", fields[0]);
                segment.put("segmentSeq", i);
                segment.put("segmentContent", line);

                Map<String, Object> parsedData = parseSegmentFields(fields);
                segment.put("parsedData", JSON.toJSONString(parsedData));

                segments.add(segment);
            }
        }

        return segments;
    }

    private Map<String, Object> parseSegmentFields(String[] fields) {
        Map<String, Object> data = new HashMap<>();
        String segmentName = fields[0];

        for (int i = 1; i < fields.length; i++) {
            String fieldKey = segmentName + "." + i;
            String fieldValue = fields[i];

            if (fieldValue.contains("^")) {
                String[] components = fieldValue.split("\\^");
                Map<String, String> componentMap = new HashMap<>();
                for (int j = 0; j < components.length; j++) {
                    if (!components[j].isEmpty()) {
                        componentMap.put(String.valueOf(j + 1), components[j]);
                    }
                }
                data.put(fieldKey, componentMap);
            } else {
                data.put(fieldKey, fieldValue);
            }
        }

        return data;
    }

    public String getMessageType(String rawMessage) {
        try {
            String[] lines = rawMessage.split("\r|\n");
            for (String line : lines) {
                if (line.startsWith("MSH|")) {
                    String[] fields = line.split("\\|");
                    if (fields.length >= 9) {
                        String messageTypeField = fields[8];
                        if (messageTypeField.contains("^")) {
                            String[] parts = messageTypeField.split("\\^");
                            return parts[0];
                        }
                        return messageTypeField;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error getting message type", e);
        }
        return null;
    }

    public String getTriggerEvent(String rawMessage) {
        try {
            String[] lines = rawMessage.split("\r|\n");
            for (String line : lines) {
                if (line.startsWith("MSH|")) {
                    String[] fields = line.split("\\|");
                    if (fields.length >= 9) {
                        String messageTypeField = fields[8];
                        if (messageTypeField.contains("^")) {
                            String[] parts = messageTypeField.split("\\^");
                            if (parts.length >= 2) {
                                return parts[1];
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error getting trigger event", e);
        }
        return null;
    }

    public String getMessageControlId(String rawMessage) {
        try {
            String[] lines = rawMessage.split("\r|\n");
            for (String line : lines) {
                if (line.startsWith("MSH|")) {
                    String[] fields = line.split("\\|");
                    if (fields.length >= 10) {
                        return fields[9];
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error getting message control ID", e);
        }
        return null;
    }
}
