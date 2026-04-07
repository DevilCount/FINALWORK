package com.lis.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.user.entity.DeptDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapper<DeptDO> {

    List<DeptDO> selectDeptList(@Param("deptName") String deptName,
                                @Param("deptCode") String deptCode,
                                @Param("status") Integer status,
                                @Param("parentId") Long parentId);

    DeptDO selectByDeptCode(@Param("deptCode") String deptCode);

    List<DeptDO> selectDeptByParentId(@Param("parentId") Long parentId);

    List<Long> selectDeptIdsByRoleId(@Param("roleId") Long roleId);

    List<DeptDO> selectAllDepts();

    int selectChildrenCountByParentId(@Param("parentId") Long parentId);
}
