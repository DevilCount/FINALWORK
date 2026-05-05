package com.lis.specimen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.specimen.entity.TestItemDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestItemMapper extends BaseMapper<TestItemDO> {

    @Select("SELECT * FROM test_item WHERE id IN (${ids})")
    List<TestItemDO> selectByIds(@Param("ids") String ids);
}
