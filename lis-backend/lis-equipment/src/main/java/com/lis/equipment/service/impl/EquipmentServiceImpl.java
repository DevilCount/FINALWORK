package com.lis.equipment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.exception.BusinessException;
import com.lis.common.result.PageResult;
import com.lis.common.result.ResultCode;
import com.lis.equipment.dto.EquipmentDTO;
import com.lis.equipment.dto.EquipmentQueryDTO;
import com.lis.equipment.entity.EquipmentDO;
import com.lis.equipment.enums.EquipmentStatusEnum;
import com.lis.equipment.mapper.EquipmentMapper;
import com.lis.equipment.service.EquipmentService;
import com.lis.equipment.vo.EquipmentVO;
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
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentMapper equipmentMapper;

    @Override
    public PageResult<EquipmentVO> pageList(EquipmentQueryDTO queryDTO) {
        LambdaQueryWrapper<EquipmentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(queryDTO.getEquipmentCode()), EquipmentDO::getEquipmentCode, queryDTO.getEquipmentCode())
                .like(StrUtil.isNotBlank(queryDTO.getEquipmentName()), EquipmentDO::getEquipmentName, queryDTO.getEquipmentName())
                .eq(queryDTO.getEquipmentTypeId() != null, EquipmentDO::getEquipmentTypeId, queryDTO.getEquipmentTypeId())
                .like(StrUtil.isNotBlank(queryDTO.getBrand()), EquipmentDO::getBrand, queryDTO.getBrand())
                .like(StrUtil.isNotBlank(queryDTO.getModel()), EquipmentDO::getModel, queryDTO.getModel())
                .eq(StrUtil.isNotBlank(queryDTO.getStatus()), EquipmentDO::getStatus, queryDTO.getStatus())
                .eq(queryDTO.getIsOnline() != null, EquipmentDO::getIsOnline, queryDTO.getIsOnline())
                .eq(queryDTO.getLabId() != null, EquipmentDO::getLabId, queryDTO.getLabId())
                .eq(queryDTO.getResponsibleUserId() != null, EquipmentDO::getResponsibleUserId, queryDTO.getResponsibleUserId())
                .orderByDesc(EquipmentDO::getCreateTime);

        Page<EquipmentDO> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<EquipmentDO> result = equipmentMapper.selectPage(page, wrapper);

        List<EquipmentVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(result.getTotal(), queryDTO.getPageNum(), queryDTO.getPageSize(), voList);
    }

    @Override
    public EquipmentVO getById(Long id) {
        EquipmentDO equipmentDO = equipmentMapper.selectById(id);
        if (equipmentDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "设备不存在");
        }
        return convertToVO(equipmentDO);
    }

    @Override
    public EquipmentVO getByCode(String equipmentCode) {
        LambdaQueryWrapper<EquipmentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentDO::getEquipmentCode, equipmentCode);
        EquipmentDO equipmentDO = equipmentMapper.selectOne(wrapper);
        if (equipmentDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "设备不存在");
        }
        return convertToVO(equipmentDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(EquipmentDTO dto) {
        LambdaQueryWrapper<EquipmentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentDO::getEquipmentCode, dto.getEquipmentCode());
        if (equipmentMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTS, "设备编码已存在");
        }

        EquipmentDO equipmentDO = new EquipmentDO();
        BeanUtil.copyProperties(dto, equipmentDO);
        if (StrUtil.isBlank(equipmentDO.getStatus())) {
            equipmentDO.setStatus(EquipmentStatusEnum.NORMAL.getCode());
        }
        equipmentDO.setIsOnline(0);
        equipmentDO.setCreateTime(LocalDateTime.now());
        equipmentDO.setUpdateTime(LocalDateTime.now());

        equipmentMapper.insert(equipmentDO);
        log.info("新增设备成功: id={}, code={}", equipmentDO.getId(), equipmentDO.getEquipmentCode());
        return equipmentDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(EquipmentDTO dto) {
        if (dto.getId() == null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "设备ID不能为空");
        }

        EquipmentDO existEquipment = equipmentMapper.selectById(dto.getId());
        if (existEquipment == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "设备不存在");
        }

        if (!existEquipment.getEquipmentCode().equals(dto.getEquipmentCode())) {
            LambdaQueryWrapper<EquipmentDO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(EquipmentDO::getEquipmentCode, dto.getEquipmentCode())
                    .ne(EquipmentDO::getId, dto.getId());
            if (equipmentMapper.selectCount(wrapper) > 0) {
                throw new BusinessException(ResultCode.DATA_ALREADY_EXISTS, "设备编码已存在");
            }
        }

        EquipmentDO equipmentDO = new EquipmentDO();
        BeanUtil.copyProperties(dto, equipmentDO);
        equipmentDO.setUpdateTime(LocalDateTime.now());

        equipmentMapper.updateById(equipmentDO);
        log.info("更新设备成功: id={}", equipmentDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        EquipmentDO equipmentDO = equipmentMapper.selectById(id);
        if (equipmentDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "设备不存在");
        }

        equipmentMapper.deleteById(id);
        log.info("删除设备成功: id={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        if (ids == null || ids.length == 0) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "请选择要删除的设备");
        }

        equipmentMapper.deleteBatchIds(Arrays.asList(ids));
        log.info("批量删除设备成功: ids={}", Arrays.toString(ids));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, String status) {
        EquipmentDO equipmentDO = equipmentMapper.selectById(id);
        if (equipmentDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "设备不存在");
        }

        EquipmentStatusEnum statusEnum = EquipmentStatusEnum.getByCode(status);
        if (statusEnum == null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "无效的设备状态");
        }

        EquipmentDO updateDO = new EquipmentDO();
        updateDO.setId(id);
        updateDO.setStatus(status);
        updateDO.setUpdateTime(LocalDateTime.now());

        equipmentMapper.updateById(updateDO);
        log.info("更新设备状态成功: id={}, status={}", id, status);
    }

    private EquipmentVO convertToVO(EquipmentDO equipmentDO) {
        EquipmentVO vo = new EquipmentVO();
        BeanUtil.copyProperties(equipmentDO, vo);

        EquipmentStatusEnum statusEnum = EquipmentStatusEnum.getByCode(equipmentDO.getStatus());
        if (statusEnum != null) {
            vo.setStatusDesc(statusEnum.getDesc());
        }

        return vo;
    }
}
