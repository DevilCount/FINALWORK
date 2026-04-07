package com.lis.hl7.builder;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v25.message.ACK;
import ca.uhn.hl7v2.model.v25.message.ADT_A01;
import ca.uhn.hl7v2.model.v25.message.ORU_R01;
import ca.uhn.hl7v2.model.v25.segment.*;
import ca.uhn.hl7v2.parser.PipeParser;
import com.lis.hl7.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class Hl7MessageBuilder {

    private final PipeParser parser = new PipeParser();

    private static final DateTimeFormatter HL7_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final String HL7_VERSION = "2.5";
    private static final String SENDING_APP = "LIS";
    private static final String SENDING_FACILITY = "LIS_LAB";

    public String buildAck(String originalMessageControlId, String ackCode, String ackMessage) throws HL7Exception {
        ACK ack = new ACK();

        MSH msh = ack.getMSH();
        msh.getFieldSeparator().setValue("|");
        msh.getEncodingCharacters().setValue("^~\\&");
        msh.getSendingApplication().getNamespaceID().setValue(SENDING_APP);
        msh.getSendingFacility().getNamespaceID().setValue(SENDING_FACILITY);
        msh.getDateTimeOfMessage().getTime().setValue(getCurrentTime());
        msh.getMessageType().getMessageCode().setValue("ACK");
        msh.getMessageType().getTriggerEvent().setValue("A01");
        msh.getMessageControlID().setValue(generateMessageControlId());
        msh.getProcessingID().getProcessingID().setValue("P");
        msh.getVersionID().getVersionID().setValue(HL7_VERSION);

        MSA msa = ack.getMSA();
        msa.getAcknowledgmentCode().setValue(ackCode);
        msa.getMessageControlID().setValue(originalMessageControlId);
        msa.getTextMessage().setValue(ackMessage != null ? ackMessage : "");

        return parser.encode(ack);
    }

    public String buildAckSuccess(String originalMessageControlId) throws HL7Exception {
        return buildAck(originalMessageControlId, "AA", "Message accepted");
    }

    public String buildAckError(String originalMessageControlId, String errorMessage) throws HL7Exception {
        return buildAck(originalMessageControlId, "AE", errorMessage);
    }

    public String buildAckReject(String originalMessageControlId, String rejectReason) throws HL7Exception {
        return buildAck(originalMessageControlId, "AR", rejectReason);
    }

    public String buildAdtA01(Hl7PatientVO patient, Hl7VisitVO visit) throws HL7Exception {
        ADT_A01 adt = new ADT_A01();

        MSH msh = adt.getMSH();
        msh.getFieldSeparator().setValue("|");
        msh.getEncodingCharacters().setValue("^~\\&");
        msh.getSendingApplication().getNamespaceID().setValue(SENDING_APP);
        msh.getSendingFacility().getNamespaceID().setValue(SENDING_FACILITY);
        msh.getDateTimeOfMessage().getTime().setValue(getCurrentTime());
        msh.getMessageType().getMessageCode().setValue("ADT");
        msh.getMessageType().getTriggerEvent().setValue("A01");
        msh.getMessageControlID().setValue(generateMessageControlId());
        msh.getProcessingID().getProcessingID().setValue("P");
        msh.getVersionID().getVersionID().setValue(HL7_VERSION);

        buildPidSegment(adt.getPID(), patient);

        if (visit != null) {
            buildPv1Segment(adt.getPV1(), visit);
        }

        return parser.encode(adt);
    }

    public String buildOruR01(Hl7PatientVO patient, Hl7VisitVO visit, List<Hl7ObservationVO> observations) throws HL7Exception {
        ORU_R01 oru = new ORU_R01();

        MSH msh = oru.getMSH();
        msh.getFieldSeparator().setValue("|");
        msh.getEncodingCharacters().setValue("^~\\&");
        msh.getSendingApplication().getNamespaceID().setValue(SENDING_APP);
        msh.getSendingFacility().getNamespaceID().setValue(SENDING_FACILITY);
        msh.getDateTimeOfMessage().getTime().setValue(getCurrentTime());
        msh.getMessageType().getMessageCode().setValue("ORU");
        msh.getMessageType().getTriggerEvent().setValue("R01");
        msh.getMessageControlID().setValue(generateMessageControlId());
        msh.getProcessingID().getProcessingID().setValue("P");
        msh.getVersionID().getVersionID().setValue(HL7_VERSION);

        PID pid = oru.getPATIENT_RESULT().getPATIENT().getPID();
        buildPidSegment(pid, patient);

        if (visit != null) {
            PV1 pv1 = oru.getPATIENT_RESULT().getPATIENT().getVISIT().getPV1();
            buildPv1Segment(pv1, visit);
        }

        if (observations != null && !observations.isEmpty()) {
            int index = 0;
            for (Hl7ObservationVO observation : observations) {
                OBX obx = oru.getPATIENT_RESULT().getORDER_OBSERVATION().getOBSERVATION(index).getOBX();
                buildObxSegment(obx, observation, index + 1);
                index++;
            }
        }

        return parser.encode(oru);
    }

    private void buildPidSegment(PID pid, Hl7PatientVO patient) throws HL7Exception {
        pid.getSetIDPID().setValue("1");

        if (patient.getPatientId() != null) {
            pid.getPatientID().getIDNumber().setValue(patient.getPatientId());
            pid.getPatientIdentifierList(0).getIDNumber().setValue(patient.getPatientId());
            pid.getPatientIdentifierList(0).getIdentifierTypeCode().setValue("PI");
        }

        if (patient.getPatientName() != null) {
            pid.getPatientName(0).getFamilyName().getSurname().setValue(patient.getPatientName());
        }

        if (patient.getIdCardNo() != null) {
            pid.getPatientIdentifierList(1).getIDNumber().setValue(patient.getIdCardNo());
            pid.getPatientIdentifierList(1).getIdentifierTypeCode().setValue("ID");
        }

        if (patient.getGender() != null) {
            pid.getAdministrativeSex().setValue(patient.getGender());
        }

        if (patient.getBirthDate() != null) {
            pid.getDateTimeOfBirth().getTime().setValue(patient.getBirthDate());
        }

        if (patient.getPhone() != null) {
            pid.getPhoneNumberHome(0).getTelephoneNumber().setValue(patient.getPhone());
        }

        if (patient.getAddress() != null) {
            pid.getPatientAddress(0).getStreetAddress().getStreetOrMailingAddress().setValue(patient.getAddress());
        }
    }

    private void buildPv1Segment(PV1 pv1, Hl7VisitVO visit) throws HL7Exception {
        pv1.getSetIDPV1().setValue("1");

        if (visit.getVisitClass() != null) {
            pv1.getPatientClass().setValue(visit.getVisitClass());
        }

        if (visit.getDepartment() != null) {
            pv1.getAssignedPatientLocation().getPointOfCare().setValue(visit.getDepartment());
        }

        if (visit.getWard() != null) {
            pv1.getAssignedPatientLocation().getFacility().getNamespaceID().setValue(visit.getWard());
        }

        if (visit.getBedNo() != null) {
            pv1.getAssignedPatientLocation().getBed().setValue(visit.getBedNo());
        }

        if (visit.getVisitNo() != null) {
            pv1.getVisitNumber().getIDNumber().setValue(visit.getVisitNo());
        }

        if (visit.getAttendingDoctor() != null) {
            pv1.getAttendingDoctor(0).getIDNumber().setValue(visit.getAttendingDoctor());
        }

        if (visit.getAdmissionTime() != null) {
            pv1.getAdmitDateTime().getTime().setValue(visit.getAdmissionTime());
        }
    }

    private void buildObxSegment(OBX obx, Hl7ObservationVO observation, int setId) throws HL7Exception {
        obx.getSetIDOBX().setValue(String.valueOf(setId));
        obx.getValueType().setValue("ST");

        if (observation.getObservationId() != null) {
            obx.getObservationIdentifier().getIdentifier().setValue(observation.getObservationId());
            obx.getObservationIdentifier().getText().setValue(observation.getObservationName());
        }

        if (observation.getObservationValue() != null) {
            obx.getObservationValue(0).getData().setValue(observation.getObservationValue());
        }

        if (observation.getUnits() != null) {
            obx.getUnits().getIdentifier().setValue(observation.getUnits());
        }

        if (observation.getReferenceRange() != null) {
            obx.getReferencesRange().setValue(observation.getReferenceRange());
        }

        if (observation.getAbnormalFlag() != null) {
            obx.getAbnormalFlags(0).setValue(observation.getAbnormalFlag());
        }

        if (observation.getObservationResultStatus() != null) {
            obx.getObservationResultStatus().setValue(observation.getObservationResultStatus());
        }

        if (observation.getDateTimeOfObservation() != null) {
            obx.getDateTimeOfTheObservation().getTime().setValue(observation.getDateTimeOfObservation());
        }
    }

    public String buildCustomMessage(String messageType, String triggerEvent, 
                                     Hl7PatientVO patient, Hl7VisitVO visit,
                                     List<Hl7OrderVO> orders, List<Hl7ObservationVO> observations) throws HL7Exception {
        StringBuilder message = new StringBuilder();

        String messageControlId = generateMessageControlId();
        String currentTime = getCurrentTime();

        message.append(buildMshSegment(messageType, triggerEvent, messageControlId, currentTime));

        if (patient != null) {
            message.append(buildPidSegmentString(patient));
        }

        if (visit != null) {
            message.append(buildPv1SegmentString(visit));
        }

        if (orders != null) {
            int orderIndex = 0;
            for (Hl7OrderVO order : orders) {
                message.append(buildOrcSegmentString(order, orderIndex));
                message.append(buildObrSegmentString(order, orderIndex));
                orderIndex++;
            }
        }

        if (observations != null) {
            int obxIndex = 1;
            for (Hl7ObservationVO observation : observations) {
                message.append(buildObxSegmentString(observation, obxIndex));
                obxIndex++;
            }
        }

        return message.toString();
    }

    private String buildMshSegment(String messageType, String triggerEvent, String messageControlId, String currentTime) {
        return String.format("MSH|^~\\&|%s|%s||||%s^%s|%s|P|2.5\r",
                SENDING_APP, SENDING_FACILITY, messageType, triggerEvent, messageControlId);
    }

    private String buildPidSegmentString(Hl7PatientVO patient) {
        StringBuilder pid = new StringBuilder("PID|1|");

        if (patient.getPatientId() != null) {
            pid.append(patient.getPatientId()).append("^^^HIS^PI");
        }
        pid.append("|");

        if (patient.getPatientId() != null) {
            pid.append(patient.getPatientId()).append("^^^HIS^PI");
        }
        pid.append("||");

        if (patient.getPatientName() != null) {
            pid.append(patient.getPatientName());
        }
        pid.append("|");

        if (patient.getBirthDate() != null) {
            pid.append(patient.getBirthDate());
        }
        pid.append("|");

        if (patient.getGender() != null) {
            pid.append(patient.getGender());
        }
        pid.append("||||||||");

        if (patient.getIdCardNo() != null) {
            pid.append(patient.getIdCardNo()).append("^^^HIS^ID");
        }

        pid.append("\r");
        return pid.toString();
    }

    private String buildPv1SegmentString(Hl7VisitVO visit) {
        StringBuilder pv1 = new StringBuilder("PV1|1|");

        if (visit.getVisitClass() != null) {
            pv1.append(visit.getVisitClass());
        } else {
            pv1.append("O");
        }
        pv1("|");

        if (visit.getDepartment() != null) {
            pv1.append(visit.getDepartment());
        }
        pv1.append("|||||||||||||");

        if (visit.getVisitNo() != null) {
            pv1.append(visit.getVisitNo());
        }

        pv1.append("\r");
        return pv1.toString();
    }

    private String buildOrcSegmentString(Hl7OrderVO order, int index) {
        StringBuilder orc = new StringBuilder("ORC|");

        if (order.getOrderControl() != null) {
            orc.append(order.getOrderControl());
        } else {
            orc.append("NW");
        }
        orc.append("|");

        if (order.getPlacerOrderNo() != null) {
            orc.append(order.getPlacerOrderNo());
        }
        orc.append("|");

        if (order.getFillerOrderNo() != null) {
            orc.append(order.getFillerOrderNo());
        }
        orc.append("||||||||||||");

        if (order.getOrderingDoctor() != null) {
            orc.append(order.getOrderingDoctor());
        }

        orc.append("\r");
        return orc.toString();
    }

    private String buildObrSegmentString(Hl7OrderVO order, int index) {
        StringBuilder obr = new StringBuilder("OBR|1|");

        if (order.getPlacerOrderNo() != null) {
            obr.append(order.getPlacerOrderNo());
        }
        obr.append("|");

        if (order.getFillerOrderNo() != null) {
            obr.append(order.getFillerOrderNo());
        }
        obr.append("|");

        if (order.getOrderItemCode() != null) {
            obr.append(order.getOrderItemCode());
            if (order.getOrderItemName() != null) {
                obr.append("^").append(order.getOrderItemName());
            }
        }
        obr.append("|");

        if (order.getPriority() != null) {
            obr.append(order.getPriority());
        }
        obr.append("|||");

        if (order.getClinicalInfo() != null) {
            obr.append(order.getClinicalInfo());
        }

        obr.append("\r");
        return obr.toString();
    }

    private String buildObxSegmentString(Hl7ObservationVO observation, int setId) {
        StringBuilder obx = new StringBuilder("OBX|");
        obx.append(setId).append("|ST|");

        if (observation.getObservationId() != null) {
            obx.append(observation.getObservationId());
            if (observation.getObservationName() != null) {
                obx.append("^").append(observation.getObservationName());
            }
        }
        obx.append("|");

        if (observation.getObservationValue() != null) {
            obx.append(observation.getObservationValue());
        }
        obx.append("|");

        if (observation.getUnits() != null) {
            obx.append(observation.getUnits());
        }
        obx.append("|");

        if (observation.getReferenceRange() != null) {
            obx.append(observation.getReferenceRange());
        }
        obx.append("|");

        if (observation.getAbnormalFlag() != null) {
            obx.append(observation.getAbnormalFlag());
        }
        obx.append("|||");

        if (observation.getObservationResultStatus() != null) {
            obx.append(observation.getObservationResultStatus());
        }

        obx.append("\r");
        return obx.toString();
    }

    private String getCurrentTime() {
        return LocalDateTime.now().format(HL7_DATE_FORMAT);
    }

    private String generateMessageControlId() {
        return "LIS" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
