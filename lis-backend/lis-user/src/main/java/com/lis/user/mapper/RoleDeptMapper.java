package com.lis.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.user.entity.RoleDeptDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDeptMapper extends BaseMapper<RoleDeptDO> {

    int deleteByRoleId(@Param("roleId") Long roleId);

    int batchInsert(@Param("list") List<RoleDeptDO> list);

    List<Long> selectDeptIdsByRoleId(@Param("roleId") Long roleId);
}
