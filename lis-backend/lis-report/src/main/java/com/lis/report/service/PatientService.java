package com.lis.report.service;

import com.lis.common.result.PageResult;
import com.lis.report.dto.PatientCreateDTO;
import com.lis.report.dto.PatientQueryDTO;
import com.lis.report.dto.PatientUpdateDTO;
import com.lis.report.vo.PatientVO;

import java.util.List;

public interface PatientService {

    Long createPatient(PatientCreateDTO dto);

    void updatePatient(PatientUpdateDTO dto);

    void deletePatient(Long id);

    PatientVO getPatientById(Long id);

    PatientVO getPatientByIdNo(String idNo);

    PageResult<PatientVO> queryPatients(PatientQueryDTO dto);

    List<PatientVO> listPatients(PatientQueryDTO dto);
}
