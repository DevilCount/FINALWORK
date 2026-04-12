package com.lis.hl7.converter;

import com.lis.hl7.vo.Hl7OrderVO;
import com.lis.hl7.vo.Hl7PatientVO;
import com.lis.hl7.vo.Hl7VisitVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Hl7ToReportConverter {

    public Map<String, Object> convert(Hl7PatientVO patient, Hl7VisitVO visit, List<Hl7OrderVO> orders) {
        Map<String, Object> dto = new HashMap<>();

        if (patient != null) {
            dto.put("patientName", patient.getPatientName());
            dto.put("patientGender", patient.getGender());
            dto.put("patientIdNo", patient.getIdCardNo());
        }

        if (visit != null) {
            dto.put("deptName", visit.getDepartment());
            dto.put("wardName", visit.getWard());
            dto.put("bedNo", visit.getBedNo());
            dto.put("doctorName", visit.getAttendingDoctor());
        }

        List<Map<String, Object>> testItems = new ArrayList<>();
        if (orders != null) {
            for (Hl7OrderVO order : orders) {
                Map<String, Object> item = new HashMap<>();
                item.put("itemCode", order.getOrderItemCode());
                item.put("itemName", order.getOrderItemName());
                testItems.add(item);
            }
        }
        dto.put("testItems", testItems);

        return dto;
    }
}
