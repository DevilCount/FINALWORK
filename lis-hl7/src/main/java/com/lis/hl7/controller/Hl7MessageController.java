package com.lis.hl7.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.hl7.dto.Hl7MessageDTO;
import com.lis.hl7.dto.Hl7MessageQueryDTO;
import com.lis.hl7.entity.Hl7MessageDO;
import com.lis.hl7.entity.Hl7MessageSegmentDO;
import com.lis.hl7.mapper.Hl7MessageSegmentMapper;
import com.lis.hl7.parser.Hl7MessageParser;
import com.lis.hl7.service.Hl7MessageService;
import com.lis.hl7.vo.Hl7MessageDetailVO;
import com.lis.hl7.vo.Hl7MessageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "HL7消息管理")
@RestController
@RequestMapping("/hl7-message")
@RequiredArgsConstructor
public class Hl7MessageController {

    private final Hl7MessageService hl7MessageService;
    private final Hl7MessageSegmentMapper segmentMapper;
    private final Hl7MessageParser messageParser;

    @ApiOperation("分页查询HL7消息")
    @GetMapping("/page")
    public Result<PageResult<Hl7MessageVO>> queryPage(Hl7MessageQueryDTO queryDTO) {
        Page<Hl7MessageDO> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());

        LambdaQueryWrapper<Hl7MessageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(queryDTO.getInterfaceCode()), Hl7MessageDO::getInterfaceCode, queryDTO.getInterfaceCode())
                .eq(StringUtils.hasText(queryDTO.getDirection()), Hl7MessageDO::getDirection, queryDTO.getDirection())
                .eq(StringUtils.hasText(queryDTO.getMessageType()), Hl7MessageDO::getMessageType, queryDTO.getMessageType())
                .eq(StringUtils.hasText(queryDTO.getTriggerEvent()), Hl7MessageDO::getTriggerEvent, queryDTO.getTriggerEvent())
                .eq(StringUtils.hasText(queryDTO.getMessageControlId()), Hl7MessageDO::getMessageControlId, queryDTO.getMessageControlId())
                .eq(StringUtils.hasText(queryDTO.getPatientId()), Hl7MessageDO::getPatientId, queryDTO.getPatientId())
                .like(StringUtils.hasText(queryDTO.getPatientName()), Hl7MessageDO::getPatientName, queryDTO.getPatientName())
                .eq(StringUtils.hasText(queryDTO.getVisitNo()), Hl7MessageDO::getVisitNo, queryDTO.getVisitNo())
                .eq(StringUtils.hasText(queryDTO.getProcessStatus()), Hl7MessageDO::getProcessStatus, queryDTO.getProcessStatus())
                .eq(StringUtils.hasText(queryDTO.getAckStatus()), Hl7MessageDO::getAckStatus, queryDTO.getAckStatus())
                .orderByDesc(Hl7MessageDO::getCreateTime);

        if (StringUtils.hasText(queryDTO.getStartTime())) {
            LocalDateTime startTime = LocalDateTime.parse(queryDTO.getStartTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            wrapper.ge(Hl7MessageDO::getCreateTime, startTime);
        }
        if (StringUtils.hasText(queryDTO.getEndTime())) {
            LocalDateTime endTime = LocalDateTime.parse(queryDTO.getEndTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            wrapper.le(Hl7MessageDO::getCreateTime, endTime);
        }

        Page<Hl7MessageDO> result = hl7MessageService.page(page, wrapper);

        Page<Hl7MessageVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<Hl7MessageVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);

        return Result.success(PageResult.of(voPage.getTotal(), queryDTO.getPageNum(), queryDTO.getPageSize(), voList));
    }

    @ApiOperation("获取HL7消息详情")
    @GetMapping("/{id}")
    public Result<Hl7MessageDetailVO> getDetail(@PathVariable Long id) {
        Hl7MessageDO message = hl7MessageService.getById(id);
        if (message == null) {
            return Result.success(null);
        }

        Hl7MessageDetailVO vo = new Hl7MessageDetailVO();
        BeanUtils.copyProperties(message, vo);

        List<Hl7MessageSegmentDO> segments = segmentMapper.selectList(
                new LambdaQueryWrapper<Hl7MessageSegmentDO>()
                        .eq(Hl7MessageSegmentDO::getMessageId, id)
                        .orderByAsc(Hl7MessageSegmentDO::getSegmentSeq));

        vo.setSegments(segments.stream().map(segment -> {
            com.lis.hl7.vo.Hl7MessageSegmentVO segmentVO = new com.lis.hl7.vo.Hl7MessageSegmentVO();
            BeanUtils.copyProperties(segment, segmentVO);
            return segmentVO;
        }).collect(Collectors.toList()));

        return Result.success(vo);
    }

    @ApiOperation("根据消息控制ID查询")
    @GetMapping("/by-control-id/{messageControlId}")
    public Result<Hl7MessageVO> getByMessageControlId(@PathVariable String messageControlId) {
        Hl7MessageDO message = hl7MessageService.getByMessageControlId(messageControlId);
        if (message == null) {
            return Result.success(null);
        }
        return Result.success(convertToVO(message));
    }

    @ApiOperation("根据患者ID查询消息列表")
    @GetMapping("/by-patient/{patientId}")
    public Result<List<Hl7MessageVO>> listByPatientId(@PathVariable String patientId) {
        List<Hl7MessageDO> messages = hl7MessageService.listByPatientId(patientId);
        List<Hl7MessageVO> voList = messages.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        return Result.success(voList);
    }

    @ApiOperation("根据就诊号查询消息列表")
    @GetMapping("/by-visit/{visitNo}")
    public Result<List<Hl7MessageVO>> listByVisitNo(@PathVariable String visitNo) {
        List<Hl7MessageDO> messages = hl7MessageService.listByVisitNo(visitNo);
        List<Hl7MessageVO> voList = messages.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        return Result.success(voList);
    }

    @ApiOperation("重新处理消息")
    @PostMapping("/{id}/reprocess")
    public Result<Void> reprocess(@PathVariable Long id) {
        hl7MessageService.updateProcessStatus(id, "pending", null, null);
        return Result.success();
    }

    @ApiOperation("删除消息")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        hl7MessageService.removeById(id);
        return Result.success();
    }

    @ApiOperation("解析HL7消息")
    @PostMapping("/parse")
    public Result<Object> parseMessage(@RequestBody Hl7MessageDTO dto) {
        try {
            Object parsed = messageParser.parseToMap(dto.getRawMessage());
            return Result.success(parsed);
        } catch (Exception e) {
            return Result.fail("解析失败: " + e.getMessage());
        }
    }

    private Hl7MessageVO convertToVO(Hl7MessageDO message) {
        Hl7MessageVO vo = new Hl7MessageVO();
        BeanUtils.copyProperties(message, vo);
        return vo;
    }
}
