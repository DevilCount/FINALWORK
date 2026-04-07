package com.lis.user.service;

import com.lis.common.result.PageResult;
import com.lis.user.dto.*;
import com.lis.user.vo.RoleVO;

import java.util.List;

public interface RoleService {

    PageResult<RoleVO> getRoleList(RoleQueryDTO queryDTO);

    RoleVO getRoleById(Long id);

    Long createRole(RoleCreateDTO createDTO);

    void updateRole(RoleUpdateDTO updateDTO);

    void deleteRole(Long id);

    void batchDeleteRoles(List<Long> ids);

    void assignMenus(RoleMenuAssignDTO assignDTO);

    List<Long> getMenuIdsByRoleId(Long roleId);

    List<RoleVO> getAllRoles();
}
