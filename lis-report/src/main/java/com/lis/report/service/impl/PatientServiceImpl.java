package com.lis.report.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.exception.BusinessException;
import com.lis.common.result.PageResult;
import com.lis.report.dto.PatientCreateDTO;
import com.lis.report.dto.PatientQueryDTO;
import com.lis.report.dto.PatientUpdateDTO;
import com.lis.report.entity.PatientDO;
import com.lis.report.mapper.PatientMapper;
import com.lis.report.service.PatientService;
import com.lis.report.vo.PatientVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientMapper patientMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPatient(PatientCreateDTO dto) {
        if (StrUtil.isNotBlank(dto.getIdNo())) {
            LambdaQueryWrapper<PatientDO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PatientDO::getIdNo, dto.getIdNo());
            PatientDO existPatient = patientMapper.selectOne(wrapper);
            if (existPatient != null) {
                throw new BusinessException("该证件号码已存在");
            }
        }

        PatientDO patientDO = new PatientDO();
        BeanUtils.copyProperties(dto, patientDO);
        patientDO.setPatientNo(generatePatientNo());
        patientDO.setStatus(0);
        patientDO.setCreateTime(LocalDateTime.now());
        patientDO.setUpdateTime(LocalDateTime.now());

        patientMapper.insert(patientDO);
        return patientDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePatient(PatientUpdateDTO dto) {
        PatientDO existPatient = patientMapper.selectById(dto.getId());
        if (existPatient == null) {
            throw new BusinessException("患者不存在");
        }

        if (StrUtil.isNotBlank(dto.getIdNo()) && !dto.getIdNo().equals(existPatient.getIdNo())) {
            LambdaQueryWrapper<PatientDO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PatientDO::getIdNo, dto.getIdNo());
            wrapper.ne(PatientDO::getId, dto.getId());
            PatientDO duplicatePatient = patientMapper.selectOne(wrapper);
            if (duplicatePatient != null) {
                throw new BusinessException("该证件号码已被其他患者使用");
            }
        }

        PatientDO patientDO = new PatientDO();
        BeanUtils.copyProperties(dto, patientDO);
        patientDO.setUpdateTime(LocalDateTime.now());

        patientMapper.updateById(patientDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePatient(Long id) {
        PatientDO existPatient = patientMapper.selectById(id);
        if (existPatient == null) {
            throw new BusinessException("患者不存在");
        }

        patientMapper.deleteById(id);
    }

    @Override
    public PatientVO getPatientById(Long id) {
        PatientDO patientDO = patientMapper.selectById(id);
        if (patientDO == null) {
            throw new BusinessException("患者不存在");
        }
        return convertToVO(patientDO);
    }

    @Override
    public PatientVO getPatientByIdNo(String idNo) {
        LambdaQueryWrapper<PatientDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PatientDO::getIdNo, idNo);
        PatientDO patientDO = patientMapper.selectOne(wrapper);
        if (patientDO == null) {
            return null;
        }
        return convertToVO(patientDO);
    }

    @Override
    public PageResult<PatientVO> queryPatients(PatientQueryDTO dto) {
        LambdaQueryWrapper<PatientDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getPatientNo()), PatientDO::getPatientNo, dto.getPatientNo());
        wrapper.like(StrUtil.isNotBlank(dto.getPatientName()), PatientDO::getPatientName, dto.getPatientName());
        wrapper.eq(StrUtil.isNotBlank(dto.getIdNo()), PatientDO::getIdNo, dto.getIdNo());
        wrapper.eq(StrUtil.isNotBlank(dto.getPhone()), PatientDO::getPhone, dto.getPhone());
        wrapper.eq(dto.getStatus() != null, PatientDO::getStatus, dto.getStatus());
        wrapper.orderByDesc(PatientDO::getCreateTime);

        Page<PatientDO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        Page<PatientDO> result = patientMapper.selectPage(page, wrapper);

        List<PatientVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), dto.getPageNum(), dto.getPageSize(), voList);
    }

    private String generatePatientNo() {
        return "P" + IdUtil.getSnowflakeNextIdStr();
    }

    private PatientVO convertToVO(PatientDO patientDO) {
        PatientVO vo = new PatientVO();
        BeanUtils.copyProperties(patientDO, vo);
        return vo;
    }
}
