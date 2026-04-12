package com.lis.specimen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.specimen.entity.TestItemDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestItemMapper extends BaseMapper<TestItemDO> {

    List<TestItemDO> selectByIds(@Param("ids") List<Long> ids);
}
