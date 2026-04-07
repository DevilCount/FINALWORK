package com.lis.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.user.entity.RoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {

    RoleDO selectByRoleCode(@Param("roleCode") String roleCode);

    List<RoleDO> selectRoleList(@Param("roleName") String roleName,
                                @Param("roleCode") String roleCode,
                                @Param("status") Integer status);

    List<RoleDO> selectRolesByUserId(@Param("userId") Long userId);

    List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);

    List<Long> selectDeptIdsByRoleId(@Param("roleId") Long roleId);
}
