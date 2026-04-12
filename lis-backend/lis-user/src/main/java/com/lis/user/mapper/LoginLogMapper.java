package com.lis.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.user.entity.LoginLogDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLogDO> {
}
