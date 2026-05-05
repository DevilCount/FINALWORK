package com.lis.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.user.entity.DictDataDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictDataMapper extends BaseMapper<DictDataDO> {

    List<DictDataDO> selectDictDataList(@Param("dictType") String dictType,
                                        @Param("dictLabel") String dictLabel,
                                        @Param("status") Integer status);

    List<DictDataDO> selectByDictType(@Param("dictType") String dictType);

    DictDataDO selectDefaultByDictType(@Param("dictType") String dictType);
}
