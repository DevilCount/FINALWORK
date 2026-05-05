package com.lis.user.service;

import com.lis.common.result.PageResult;
import com.lis.user.dto.*;
import com.lis.user.vo.DictDataVO;
import com.lis.user.vo.DictTypeVO;

import java.util.List;

public interface DictService {

    PageResult<DictTypeVO> getDictTypeList(DictTypeQueryDTO queryDTO);

    DictTypeVO getDictTypeById(Long id);

    DictTypeVO getDictTypeByType(String dictType);

    Long createDictType(DictTypeCreateDTO createDTO);

    void updateDictType(DictTypeUpdateDTO updateDTO);

    void deleteDictType(Long id);

    void batchDeleteDictTypes(List<Long> ids);

    PageResult<DictDataVO> getDictDataList(DictDataQueryDTO queryDTO);

    DictDataVO getDictDataById(Long id);

    Long createDictData(DictDataCreateDTO createDTO);

    void updateDictData(DictDataUpdateDTO updateDTO);

    void deleteDictData(Long id);

    void batchDeleteDictData(List<Long> ids);

    List<DictDataVO> getDictDataByType(String dictType);
}
