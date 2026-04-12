package com.lis.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.user.entity.ErrorLogDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ErrorLogMapper extends BaseMapper<ErrorLogDO> {
}
