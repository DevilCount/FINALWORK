package com.lis.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.user.entity.UserRoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

    int deleteByUserId(@Param("userId") Long userId);

    int batchInsert(@Param("list") List<UserRoleDO> list);

    List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);
}
