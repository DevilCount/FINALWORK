package com.lis.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.lis.common.exception.BusinessException;
import com.lis.common.result.PageResult;
import com.lis.user.dto.*;
import com.lis.user.entity.DictDataDO;
import com.lis.user.entity.DictTypeDO;
import com.lis.user.mapper.DictDataMapper;
import com.lis.user.mapper.DictTypeMapper;
import com.lis.user.service.DictService;
import com.lis.user.vo.DictDataVO;
import com.lis.user.vo.DictTypeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictServiceImpl implements DictService {

    private final DictTypeMapper dictTypeMapper;
    private final DictDataMapper dictDataMapper;

    @Override
    public PageResult<DictTypeVO> getDictTypeList(DictTypeQueryDTO queryDTO) {
        List<DictTypeDO> dictTypeList = dictTypeMapper.selectDictTypeList(
                queryDTO.getDictName(),
                queryDTO.getDictType(),
                queryDTO.getStatus()
        );

        long total = dictTypeList.size();
        int start = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();
        int end = (int) Math.min(start + queryDTO.getPageSize(), total);
        List<DictTypeDO> pagedList = dictTypeList.subList(start, end);

        List<DictTypeVO> voList = pagedList.stream().map(this::convertToTypeVO).collect(Collectors.toList());

        return PageResult.of(total, queryDTO.getPageNum(), queryDTO.getPageSize(), voList);
    }

    @Override
    public DictTypeVO getDictTypeById(Long id) {
        DictTypeDO dictTypeDO = dictTypeMapper.selectById(id);
        if (dictTypeDO == null) {
            throw new BusinessException("字典类型不存在");
        }
        return convertToTypeVO(dictTypeDO);
    }

    @Override
    public DictTypeVO getDictTypeByType(String dictType) {
        DictTypeDO dictTypeDO = dictTypeMapper.selectByDictType(dictType);
        if (dictTypeDO == null) {
            return null;
        }
        return convertToTypeVO(dictTypeDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDictType(DictTypeCreateDTO createDTO) {
        DictTypeDO existDictType = dictTypeMapper.selectByDictType(createDTO.getDictType());
        if (existDictType != null) {
            throw new BusinessException("字典类型已存在");
        }

        DictTypeDO dictTypeDO = new DictTypeDO();
        BeanUtil.copyProperties(createDTO, dictTypeDO);
        dictTypeDO.setStatus(createDTO.getStatus() != null ? createDTO.getStatus() : 0);
        dictTypeDO.setCreateTime(LocalDateTime.now());
        dictTypeDO.setUpdateTime(LocalDateTime.now());

        dictTypeMapper.insert(dictTypeDO);

        log.info("创建字典类型成功: dictTypeId={}, dictType={}", dictTypeDO.getId(), dictTypeDO.getDictType());
        return dictTypeDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictType(DictTypeUpdateDTO updateDTO) {
        DictTypeDO existDictType = dictTypeMapper.selectById(updateDTO.getId());
        if (existDictType == null) {
            throw new BusinessException("字典类型不存在");
        }

        DictTypeDO dictTypeDO = new DictTypeDO();
        BeanUtil.copyProperties(updateDTO, dictTypeDO);
        dictTypeDO.setUpdateTime(LocalDateTime.now());

        dictTypeMapper.updateById(dictTypeDO);

        log.info("更新字典类型成功: dictTypeId={}", updateDTO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictType(Long id) {
        DictTypeDO dictTypeDO = dictTypeMapper.selectById(id);
        if (dictTypeDO == null) {
            throw new BusinessException("字典类型不存在");
        }

        dictTypeMapper.deleteById(id);

        log.info("删除字典类型成功: dictTypeId={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteDictTypes(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        for (Long id : ids) {
            dictTypeMapper.deleteById(id);
        }

        log.info("批量删除字典类型成功: ids={}", ids);
    }

    @Override
    public PageResult<DictDataVO> getDictDataList(DictDataQueryDTO queryDTO) {
        List<DictDataDO> dictDataList = dictDataMapper.selectDictDataList(
                queryDTO.getDictType(),
                queryDTO.getDictLabel(),
                queryDTO.getStatus()
        );

        long total = dictDataList.size();
        int start = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();
        int end = (int) Math.min(start + queryDTO.getPageSize(), total);
        List<DictDataDO> pagedList = dictDataList.subList(start, end);

        List<DictDataVO> voList = pagedList.stream().map(this::convertToDataVO).collect(Collectors.toList());

        return PageResult.of(total, queryDTO.getPageNum(), queryDTO.getPageSize(), voList);
    }

    @Override
    public DictDataVO getDictDataById(Long id) {
        DictDataDO dictDataDO = dictDataMapper.selectById(id);
        if (dictDataDO == null) {
            throw new BusinessException("字典数据不存在");
        }
        return convertToDataVO(dictDataDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDictData(DictDataCreateDTO createDTO) {
        DictTypeDO dictTypeDO = dictTypeMapper.selectByDictType(createDTO.getDictType());
        if (dictTypeDO == null) {
            throw new BusinessException("字典类型不存在");
        }

        DictDataDO dictDataDO = new DictDataDO();
        BeanUtil.copyProperties(createDTO, dictDataDO);
        dictDataDO.setStatus(createDTO.getStatus() != null ? createDTO.getStatus() : 0);
        dictDataDO.setCreateTime(LocalDateTime.now());
        dictDataDO.setUpdateTime(LocalDateTime.now());

        dictDataMapper.insert(dictDataDO);

        log.info("创建字典数据成功: dictDataId={}, dictType={}", dictDataDO.getId(), dictDataDO.getDictType());
        return dictDataDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictData(DictDataUpdateDTO updateDTO) {
        DictDataDO existDictData = dictDataMapper.selectById(updateDTO.getId());
        if (existDictData == null) {
            throw new BusinessException("字典数据不存在");
        }

        DictDataDO dictDataDO = new DictDataDO();
        BeanUtil.copyProperties(updateDTO, dictDataDO);
        dictDataDO.setUpdateTime(LocalDateTime.now());

        dictDataMapper.updateById(dictDataDO);

        log.info("更新字典数据成功: dictDataId={}", updateDTO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictData(Long id) {
        DictDataDO dictDataDO = dictDataMapper.selectById(id);
        if (dictDataDO == null) {
            throw new BusinessException("字典数据不存在");
        }

        dictDataMapper.deleteById(id);

        log.info("删除字典数据成功: dictDataId={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteDictData(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        for (Long id : ids) {
            dictDataMapper.deleteById(id);
        }

        log.info("批量删除字典数据成功: ids={}", ids);
    }

    @Override
    public List<DictDataVO> getDictDataByType(String dictType) {
        List<DictDataDO> dictDataList = dictDataMapper.selectByDictType(dictType);
        return dictDataList.stream().map(this::convertToDataVO).collect(Collectors.toList());
    }

    private DictTypeVO convertToTypeVO(DictTypeDO dictTypeDO) {
        DictTypeVO dictTypeVO = new DictTypeVO();
        BeanUtil.copyProperties(dictTypeDO, dictTypeVO);
        return dictTypeVO;
    }

    private DictDataVO convertToDataVO(DictDataDO dictDataDO) {
        DictDataVO dictDataVO = new DictDataVO();
        BeanUtil.copyProperties(dictDataDO, dictDataVO);
        return dictDataVO;
    }
}
