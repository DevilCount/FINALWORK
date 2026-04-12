package com.lis.report.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.report.dto.PatientCreateDTO;
import com.lis.report.dto.PatientQueryDTO;
import com.lis.report.dto.PatientUpdateDTO;
import com.lis.report.service.PatientService;
import com.lis.report.vo.PatientVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "患者管理")
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @ApiOperation("创建患者")
    @PostMapping
    public Result<Long> createPatient(@Validated @RequestBody PatientCreateDTO dto) {
        Long patientId = patientService.createPatient(dto);
        return Result.success("创建成功", patientId);
    }

    @ApiOperation("更新患者")
    @PutMapping
    public Result<Void> updatePatient(@Validated @RequestBody PatientUpdateDTO dto) {
        patientService.updatePatient(dto);
        return Result.success("更新成功", null);
    }

    @ApiOperation("删除患者")
    @DeleteMapping("/{id}")
    public Result<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return Result.success("删除成功", null);
    }

    @ApiOperation("患者列表")
    @GetMapping("/list")
    public Result<List<PatientVO>> listPatients(PatientQueryDTO dto) {
        List<PatientVO> list = patientService.listPatients(dto);
        return Result.success(list);
    }

    @ApiOperation("根据ID获取患者")
    @GetMapping("/{id}")
    public Result<PatientVO> getPatientById(@PathVariable Long id) {
        PatientVO patient = patientService.getPatientById(id);
        return Result.success(patient);
    }

    @ApiOperation("根据证件号码获取患者")
    @GetMapping("/byIdNo/{idNo}")
    public Result<PatientVO> getPatientByIdNo(@PathVariable String idNo) {
        PatientVO patient = patientService.getPatientByIdNo(idNo);
        return Result.success(patient);
    }

    @ApiOperation("分页查询患者")
    @PostMapping("/query")
    public Result<PageResult<PatientVO>> queryPatients(@RequestBody PatientQueryDTO dto) {
        PageResult<PatientVO> result = patientService.queryPatients(dto);
        return Result.success(result);
    }
}
