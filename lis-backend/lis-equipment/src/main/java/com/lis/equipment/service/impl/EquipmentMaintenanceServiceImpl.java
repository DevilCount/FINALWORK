package com.lis.equipment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.exception.BusinessException;
import com.lis.common.result.PageResult;
import com.lis.common.result.ResultCode;
import com.lis.equipment.dto.EquipmentMaintenanceDTO;
import com.lis.equipment.dto.EquipmentMaintenanceQueryDTO;
import com.lis.equipment.entity.EquipmentDO;
import com.lis.equipment.entity.EquipmentMaintenanceDO;
import com.lis.equipment.enums.MaintenanceTypeEnum;
import com.lis.equipment.enums.RecordStatusEnum;
import com.lis.equipment.mapper.EquipmentMapper;
import com.lis.equipment.mapper.EquipmentMaintenanceMapper;
import com.lis.equipment.service.EquipmentMaintenanceService;
import com.lis.equipment.vo.EquipmentMaintenanceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipmentMaintenanceServiceImpl implements EquipmentMaintenanceService {

    private final EquipmentMaintenanceMapper maintenanceMapper;
    private final EquipmentMapper equipmentMapper;

    @Override
    public PageResult<EquipmentMaintenanceVO> pageList(EquipmentMaintenanceQueryDTO queryDTO) {
        LambdaQueryWrapper<EquipmentMaintenanceDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(queryDTO.getMaintenanceNo()), EquipmentMaintenanceDO::getMaintenanceNo, queryDTO.getMaintenanceNo())
                .eq(queryDTO.getEquipmentId() != null, EquipmentMaintenanceDO::getEquipmentId, queryDTO.getEquipmentId())
                .like(StrUtil.isNotBlank(queryDTO.getEquipmentCode()), EquipmentMaintenanceDO::getEquipmentCode, queryDTO.getEquipmentCode())
                .like(StrUtil.isNotBlank(queryDTO.getEquipmentName()), EquipmentMaintenanceDO::getEquipmentName, queryDTO.getEquipmentName())
                .eq(StrUtil.isNotBlank(queryDTO.getMaintenanceType()), EquipmentMaintenanceDO::getMaintenanceType, queryDTO.getMaintenanceType())
                .eq(StrUtil.isNotBlank(queryDTO.getMaintenanceStatus()), EquipmentMaintenanceDO::getMaintenanceStatus, queryDTO.getMaintenanceStatus())
                .eq(queryDTO.getStatus() != null, EquipmentMaintenanceDO::getStatus, queryDTO.getStatus())
                .orderByDesc(EquipmentMaintenanceDO::getCreateTime);

        Page<EquipmentMaintenanceDO> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<EquipmentMaintenanceDO> result = maintenanceMapper.selectPage(page, wrapper);

        List<EquipmentMaintenanceVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), queryDTO.getPageNum(), queryDTO.getPageSize(), voList);
    }

    @Override
    public EquipmentMaintenanceVO getById(Long id) {
        EquipmentMaintenanceDO maintenanceDO = maintenanceMapper.selectById(id);
        if (maintenanceDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "维护记录不存在");
        }
        return convertToVO(maintenanceDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(EquipmentMaintenanceDTO dto) {
        LambdaQueryWrapper<EquipmentMaintenanceDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentMaintenanceDO::getMaintenanceNo, dto.getMaintenanceNo());
        if (maintenanceMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTS, "维护编号已存在");
        }

        EquipmentDO equipmentDO = equipmentMapper.selectById(dto.getEquipmentId());
        if (equipmentDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "设备不存在");
        }

        EquipmentMaintenanceDO maintenanceDO = new EquipmentMaintenanceDO();
        BeanUtil.copyProperties(dto, maintenanceDO);
        maintenanceDO.setEquipmentCode(equipmentDO.getEquipmentCode());
        maintenanceDO.setEquipmentName(equipmentDO.getEquipmentName());
        if (maintenanceDO.getStatus() == null) {
            maintenanceDO.setStatus(RecordStatusEnum.PENDING.getCode());
        }
        maintenanceDO.setCreateTime(LocalDateTime.now());
        maintenanceDO.setUpdateTime(LocalDateTime.now());

        maintenanceMapper.insert(maintenanceDO);
        log.info("新增维护记录成功: id={}, maintenanceNo={}", maintenanceDO.getId(), maintenanceDO.getMaintenanceNo());
        return maintenanceDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(EquipmentMaintenanceDTO dto) {
        if (dto.getId() == null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "维护ID不能为空");
        }

        EquipmentMaintenanceDO existMaintenance = maintenanceMapper.selectById(dto.getId());
        if (existMaintenance == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "维护记录不存在");
        }

        EquipmentMaintenanceDO maintenanceDO = new EquipmentMaintenanceDO();
        BeanUtil.copyProperties(dto, maintenanceDO);
        maintenanceDO.setUpdateTime(LocalDateTime.now());

        maintenanceMapper.updateById(maintenanceDO);
        log.info("更新维护记录成功: id={}", maintenanceDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        EquipmentMaintenanceDO maintenanceDO = maintenanceMapper.selectById(id);
        if (maintenanceDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "维护记录不存在");
        }

        maintenanceMapper.deleteById(id);
        log.info("删除维护记录成功: id={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        if (ids == null || ids.length == 0) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "请选择要删除的维护记录");
        }

        maintenanceMapper.deleteBatchIds(Arrays.asList(ids));
        log.info("批量删除维护记录成功: ids={}", Arrays.toString(ids));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeMaintenance(Long id) {
        EquipmentMaintenanceDO maintenanceDO = maintenanceMapper.selectById(id);
        if (maintenanceDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "维护记录不存在");
        }

        maintenanceDO.setStatus(RecordStatusEnum.COMPLETED.getCode());
        maintenanceDO.setEndTime(LocalDateTime.now());
        maintenanceDO.setUpdateTime(LocalDateTime.now());
        maintenanceMapper.updateById(maintenanceDO);

        EquipmentDO equipmentDO = new EquipmentDO();
        equipmentDO.setId(maintenanceDO.getEquipmentId());
        equipmentDO.setLastMaintenanceDate(maintenanceDO.getMaintenanceDate());
        equipmentDO.setUpdateTime(LocalDateTime.now());
        equipmentMapper.updateById(equipmentDO);

        log.info("完成维护成功: id={}, equipmentId={}", id, maintenanceDO.getEquipmentId());
    }

    private EquipmentMaintenanceVO convertToVO(EquipmentMaintenanceDO maintenanceDO) {
        EquipmentMaintenanceVO vo = new EquipmentMaintenanceVO();
        BeanUtil.copyProperties(maintenanceDO, vo);

        MaintenanceTypeEnum typeEnum = MaintenanceTypeEnum.getByCode(maintenanceDO.getMaintenanceType());
        if (typeEnum != null) {
            vo.setMaintenanceTypeDesc(typeEnum.getDesc());
        }

        RecordStatusEnum statusEnum = RecordStatusEnum.getByCode(maintenanceDO.getStatus());
        if (statusEnum != null) {
            vo.setStatusDesc(statusEnum.getDesc());
        }

        return vo;
    }
}
