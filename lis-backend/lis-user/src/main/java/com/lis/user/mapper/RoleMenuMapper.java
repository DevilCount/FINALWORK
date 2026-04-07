package com.lis.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.user.entity.RoleMenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenuDO> {

    int deleteByRoleId(@Param("roleId") Long roleId);

    int batchInsert(@Param("list") List<RoleMenuDO> list);

    List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);
}
