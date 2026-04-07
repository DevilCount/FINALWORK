package com.lis.equipment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.exception.BusinessException;
import com.lis.common.result.PageResult;
import com.lis.equipment.dto.EquipmentCalibrationDTO;
import com.lis.equipment.dto.EquipmentCalibrationQueryDTO;
import com.lis.equipment.entity.EquipmentCalibrationDO;
import com.lis.equipment.entity.EquipmentDO;
import com.lis.equipment.enums.CalibrationResultEnum;
import com.lis.equipment.enums.CalibrationTypeEnum;
import com.lis.equipment.enums.RecordStatusEnum;
import com.lis.equipment.mapper.EquipmentCalibrationMapper;
import com.lis.equipment.mapper.EquipmentMapper;
import com.lis.equipment.service.EquipmentCalibrationService;
import com.lis.equipment.vo.EquipmentCalibrationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipmentCalibrationServiceImpl implements EquipmentCalibrationService {

    private final EquipmentCalibrationMapper calibrationMapper;
    private final EquipmentMapper equipmentMapper;

    @Override
    public PageResult<EquipmentCalibrationVO> pageList(EquipmentCalibrationQueryDTO queryDTO) {
        LambdaQueryWrapper<EquipmentCalibrationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(queryDTO.getCalibrationNo()), EquipmentCalibrationDO::getCalibrationNo, queryDTO.getCalibrationNo())
                .eq(queryDTO.getEquipmentId() != null, EquipmentCalibrationDO::getEquipmentId, queryDTO.getEquipmentId())
                .like(StrUtil.isNotBlank(queryDTO.getEquipmentCode()), EquipmentCalibrationDO::getEquipmentCode, queryDTO.getEquipmentCode())
                .like(StrUtil.isNotBlank(queryDTO.getEquipmentName()), EquipmentCalibrationDO::getEquipmentName, queryDTO.getEquipmentName())
                .eq(StrUtil.isNotBlank(queryDTO.getCalibrationType()), EquipmentCalibrationDO::getCalibrationType, queryDTO.getCalibrationType())
                .eq(StrUtil.isNotBlank(queryDTO.getCalibrationResult()), EquipmentCalibrationDO::getCalibrationResult, queryDTO.getCalibrationResult())
                .eq(queryDTO.getStatus() != null, EquipmentCalibrationDO::getStatus, queryDTO.getStatus())
                .orderByDesc(EquipmentCalibrationDO::getCreateTime);

        Page<EquipmentCalibrationDO> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<EquipmentCalibrationDO> result = calibrationMapper.selectPage(page, wrapper);

        List<EquipmentCalibrationVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), queryDTO.getPageNum(), queryDTO.getPageSize(), voList);
    }

    @Override
    public EquipmentCalibrationVO getById(Long id) {
        EquipmentCalibrationDO calibrationDO = calibrationMapper.selectById(id);
        if (calibrationDO == null) {
            throw new BusinessException("校准记录不存在");
        }
        return convertToVO(calibrationDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(EquipmentCalibrationDTO dto) {
        LambdaQueryWrapper<EquipmentCalibrationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentCalibrationDO::getCalibrationNo, dto.getCalibrationNo());
        if (calibrationMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("校准编号已存在");
        }

        EquipmentDO equipmentDO = equipmentMapper.selectById(dto.getEquipmentId());
        if (equipmentDO == null) {
            throw new BusinessException("设备不存在");
        }

        EquipmentCalibrationDO calibrationDO = new EquipmentCalibrationDO();
        BeanUtil.copyProperties(dto, calibrationDO);
        calibrationDO.setEquipmentCode(equipmentDO.getEquipmentCode());
        calibrationDO.setEquipmentName(equipmentDO.getEquipmentName());
        if (calibrationDO.getStatus() == null) {
            calibrationDO.setStatus(RecordStatusEnum.PENDING.getCode());
        }
        calibrationDO.setCreateTime(LocalDateTime.now());
        calibrationDO.setUpdateTime(LocalDateTime.now());

        calibrationMapper.insert(calibrationDO);
        log.info("新增校准记录成功: id={}, calibrationNo={}", calibrationDO.getId(), calibrationDO.getCalibrationNo());
        return calibrationDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(EquipmentCalibrationDTO dto) {
        if (dto.getId() == null) {
            throw new BusinessException("校准ID不能为空");
        }

        EquipmentCalibrationDO existCalibration = calibrationMapper.selectById(dto.getId());
        if (existCalibration == null) {
            throw new BusinessException("校准记录不存在");
        }

        EquipmentCalibrationDO calibrationDO = new EquipmentCalibrationDO();
        BeanUtil.copyProperties(dto, calibrationDO);
        calibrationDO.setUpdateTime(LocalDateTime.now());

        calibrationMapper.updateById(calibrationDO);
        log.info("更新校准记录成功: id={}", calibrationDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        EquipmentCalibrationDO calibrationDO = calibrationMapper.selectById(id);
        if (calibrationDO == null) {
            throw new BusinessException("校准记录不存在");
        }

        calibrationMapper.deleteById(id);
        log.info("删除校准记录成功: id={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        if (ids == null || ids.length == 0) {
            throw new BusinessException("请选择要删除的校准记录");
        }

        calibrationMapper.deleteBatchIds(Arrays.asList(ids));
        log.info("批量删除校准记录成功: ids={}", Arrays.toString(ids));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeCalibration(Long id) {
        EquipmentCalibrationDO calibrationDO = calibrationMapper.selectById(id);
        if (calibrationDO == null) {
            throw new BusinessException("校准记录不存在");
        }

        calibrationDO.setStatus(RecordStatusEnum.COMPLETED.getCode());
        calibrationDO.setUpdateTime(LocalDateTime.now());
        calibrationMapper.updateById(calibrationDO);

        EquipmentDO equipmentDO = new EquipmentDO();
        equipmentDO.setId(calibrationDO.getEquipmentId());
        equipmentDO.setLastCalibrationDate(calibrationDO.getCalibrationDate());
        equipmentDO.setNextCalibrationDate(calibrationDO.getValidEndDate());
        equipmentDO.setUpdateTime(LocalDateTime.now());
        equipmentMapper.updateById(equipmentDO);

        log.info("完成校准成功: id={}, equipmentId={}", id, calibrationDO.getEquipmentId());
    }

    private EquipmentCalibrationVO convertToVO(EquipmentCalibrationDO calibrationDO) {
        EquipmentCalibrationVO vo = new EquipmentCalibrationVO();
        BeanUtil.copyProperties(calibrationDO, vo);

        CalibrationTypeEnum typeEnum = CalibrationTypeEnum.getByCode(calibrationDO.getCalibrationType());
        if (typeEnum != null) {
            vo.setCalibrationTypeDesc(typeEnum.getDesc());
        }

        CalibrationResultEnum resultEnum = CalibrationResultEnum.getByCode(calibrationDO.getCalibrationResult());
        if (resultEnum != null) {
            vo.setCalibrationResultDesc(resultEnum.getDesc());
        }

        RecordStatusEnum statusEnum = RecordStatusEnum.getByCode(calibrationDO.getStatus());
        if (statusEnum != null) {
            vo.setStatusDesc(statusEnum.getDesc());
        }

        return vo;
    }
}
