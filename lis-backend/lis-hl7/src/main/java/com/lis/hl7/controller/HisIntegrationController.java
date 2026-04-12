package com.lis.hl7.controller;

import com.lis.common.result.Result;
import com.lis.hl7.dto.HisLabOrderDTO;
import com.lis.hl7.entity.Hl7MessageDO;
import com.lis.hl7.his.HisIntegrationService;
import com.lis.hl7.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "HIS系统集成")
@RestController
@RequestMapping("/his-integration")
@RequiredArgsConstructor
public class HisIntegrationController {

    private final HisIntegrationService hisIntegrationService;

    @ApiOperation("发送患者入院消息")
    @PostMapping("/patient-admit")
    public Result<Hl7MessageVO> sendPatientAdmit(
            @RequestParam String interfaceCode,
            @RequestBody PatientAdmitRequest request) {
        Hl7MessageDO result = hisIntegrationService.sendPatientAdmit(
                interfaceCode, request.getPatient(), request.getVisit());
        return Result.success(convertToVO(result));
    }

    @ApiOperation("发送检验结果")
    @PostMapping("/lab-result")
    public Result<Hl7MessageVO> sendLabResult(
            @RequestParam String interfaceCode,
            @RequestBody LabResultRequest request) {
        Hl7MessageDO result = hisIntegrationService.sendLabResult(
                interfaceCode, request.getPatient(), request.getVisit(), request.getObservations());
        return Result.success(convertToVO(result));
    }

    @ApiOperation("发送HIS检验申请")
    @PostMapping("/lab-order")
    public Result<Hl7MessageVO> sendLabOrder(@RequestBody HisLabOrderDTO labOrder) {
        String interfaceCode = labOrder.getInterfaceCode();
        if (interfaceCode == null || interfaceCode.isEmpty()) {
            return Result.fail(400, "接口编码不能为空");
        }
        Hl7MessageDO result = hisIntegrationService.sendLabOrder(interfaceCode, labOrder);
        return Result.success(convertToVO(result));
    }

    @ApiOperation("发送自定义HL7消息")
    @PostMapping("/custom-message")
    public Result<Hl7MessageVO> sendCustomMessage(
            @RequestParam String interfaceCode,
            @RequestParam String messageType,
            @RequestParam String triggerEvent,
            @RequestBody CustomMessageRequest request) {
        Hl7MessageDO result = hisIntegrationService.sendCustomMessage(
                interfaceCode, messageType, triggerEvent,
                request.getPatient(), request.getVisit(),
                request.getOrders(), request.getObservations());
        return Result.success(convertToVO(result));
    }

    @ApiOperation("发送原始HL7消息")
    @PostMapping("/raw-message")
    public Result<Hl7MessageVO> sendRawMessage(
            @RequestParam String interfaceCode,
            @RequestBody String hl7Message) {
        Hl7MessageDO result = hisIntegrationService.sendMessage(interfaceCode, hl7Message);
        return Result.success(convertToVO(result));
    }

    private Hl7MessageVO convertToVO(Hl7MessageDO message) {
        Hl7MessageVO vo = new Hl7MessageVO();
        org.springframework.beans.BeanUtils.copyProperties(message, vo);
        return vo;
    }

    @lombok.Data
    public static class PatientAdmitRequest {
        private Hl7PatientVO patient;
        private Hl7VisitVO visit;
    }

    @lombok.Data
    public static class LabResultRequest {
        private Hl7PatientVO patient;
        private Hl7VisitVO visit;
        private List<Hl7ObservationVO> observations;
    }

    @lombok.Data
    public static class CustomMessageRequest {
        private Hl7PatientVO patient;
        private Hl7VisitVO visit;
        private List<Hl7OrderVO> orders;
        private List<Hl7ObservationVO> observations;
    }
}
