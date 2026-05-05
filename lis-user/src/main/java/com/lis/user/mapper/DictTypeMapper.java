package com.lis.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.user.entity.DictTypeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictTypeMapper extends BaseMapper<DictTypeDO> {

    DictTypeDO selectByDictType(@Param("dictType") String dictType);

    List<DictTypeDO> selectDictTypeList(@Param("dictName") String dictName,
                                        @Param("dictType") String dictType,
                                        @Param("status") Integer status);
}
