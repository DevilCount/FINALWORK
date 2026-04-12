package com.lis.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.lis.common.exception.BusinessException;
import com.lis.common.result.PageResult;
import com.lis.common.result.ResultCode;
import com.lis.user.dto.*;
import com.lis.user.entity.RoleDO;
import com.lis.user.entity.RoleDeptDO;
import com.lis.user.entity.RoleMenuDO;
import com.lis.user.mapper.RoleDeptMapper;
import com.lis.user.mapper.RoleMapper;
import com.lis.user.mapper.RoleMenuMapper;
import com.lis.user.service.RoleService;
import com.lis.user.vo.RoleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final RoleMenuMapper roleMenuMapper;
    private final RoleDeptMapper roleDeptMapper;

    @Override
    public PageResult<RoleVO> getRoleList(RoleQueryDTO queryDTO) {
        List<RoleDO> roleList = roleMapper.selectRoleList(
                queryDTO.getRoleName(),
                queryDTO.getRoleCode(),
                queryDTO.getStatus()
        );

        long total = roleList.size();
        int start = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();
        int end = (int) Math.min(start + queryDTO.getPageSize(), total);
        List<RoleDO> pagedList = roleList.subList(start, end);

        List<RoleVO> voList = pagedList.stream().map(this::convertToVO).collect(Collectors.toList());

        return PageResult.of(total, queryDTO.getPageNum(), queryDTO.getPageSize(), voList);
    }

    @Override
    public RoleVO getRoleById(Long id) {
        RoleDO roleDO = roleMapper.selectById(id);
        if (roleDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "角色不存在");
        }
        RoleVO roleVO = convertToVO(roleDO);
        roleVO.setMenuIds(roleMapper.selectMenuIdsByRoleId(id));
        roleVO.setDeptIds(roleMapper.selectDeptIdsByRoleId(id));
        return roleVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRole(RoleCreateDTO createDTO) {
        RoleDO existRole = roleMapper.selectByRoleCode(createDTO.getRoleCode());
        if (existRole != null) {
            throw new BusinessException("角色编码已存在");
        }

        RoleDO roleDO = new RoleDO();
        BeanUtil.copyProperties(createDTO, roleDO);
        roleDO.setStatus(createDTO.getStatus() != null ? createDTO.getStatus() : 0);
        roleDO.setDelFlag(0);
        roleDO.setCreateTime(LocalDateTime.now());
        roleDO.setUpdateTime(LocalDateTime.now());

        roleMapper.insert(roleDO);

        if (createDTO.getMenuIds() != null && !createDTO.getMenuIds().isEmpty()) {
            assignRoleMenus(roleDO.getId(), createDTO.getMenuIds());
        }

        if (createDTO.getDeptIds() != null && !createDTO.getDeptIds().isEmpty()) {
            assignRoleDepts(roleDO.getId(), createDTO.getDeptIds());
        }

        log.info("创建角色成功: roleId={}, roleName={}", roleDO.getId(), roleDO.getRoleName());
        return roleDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleUpdateDTO updateDTO) {
        RoleDO existRole = roleMapper.selectById(updateDTO.getId());
        if (existRole == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "角色不存在");
        }

        RoleDO roleDO = new RoleDO();
        BeanUtil.copyProperties(updateDTO, roleDO);
        roleDO.setUpdateTime(LocalDateTime.now());

        roleMapper.updateById(roleDO);

        if (updateDTO.getMenuIds() != null) {
            roleMenuMapper.deleteByRoleId(updateDTO.getId());
            if (!updateDTO.getMenuIds().isEmpty()) {
                assignRoleMenus(updateDTO.getId(), updateDTO.getMenuIds());
            }
        }

        if (updateDTO.getDeptIds() != null) {
            roleDeptMapper.deleteByRoleId(updateDTO.getId());
            if (!updateDTO.getDeptIds().isEmpty()) {
                assignRoleDepts(updateDTO.getId(), updateDTO.getDeptIds());
            }
        }

        log.info("更新角色成功: roleId={}", updateDTO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        RoleDO roleDO = roleMapper.selectById(id);
        if (roleDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "角色不存在");
        }

        roleMapper.deleteById(id);
        roleMenuMapper.deleteByRoleId(id);
        roleDeptMapper.deleteByRoleId(id);

        log.info("删除角色成功: roleId={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteRoles(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        for (Long id : ids) {
            roleMapper.deleteById(id);
            roleMenuMapper.deleteByRoleId(id);
            roleDeptMapper.deleteByRoleId(id);
        }

        log.info("批量删除角色成功: ids={}", ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignMenus(RoleMenuAssignDTO assignDTO) {
        RoleDO roleDO = roleMapper.selectById(assignDTO.getRoleId());
        if (roleDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "角色不存在");
        }

        roleMenuMapper.deleteByRoleId(assignDTO.getRoleId());

        if (assignDTO.getMenuIds() != null && !assignDTO.getMenuIds().isEmpty()) {
            assignRoleMenus(assignDTO.getRoleId(), assignDTO.getMenuIds());
        }

        log.info("分配菜单成功: roleId={}, menuIds={}", assignDTO.getRoleId(), assignDTO.getMenuIds());
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return roleMapper.selectMenuIdsByRoleId(roleId);
    }

    @Override
    public List<RoleVO> getAllRoles() {
        return roleMapper.selectRoleList(null, null, null).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleStatus(Long id, Integer status) {
        RoleDO roleDO = roleMapper.selectById(id);
        if (roleDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "角色不存在");
        }
        roleDO.setStatus(status);
        roleDO.setUpdateTime(LocalDateTime.now());
        roleMapper.updateById(roleDO);
        log.info("更新角色状态成功: roleId={}, status={}", id, status);
    }

    private RoleVO convertToVO(RoleDO roleDO) {
        RoleVO roleVO = new RoleVO();
        BeanUtil.copyProperties(roleDO, roleVO);
        return roleVO;
    }

    private void assignRoleMenus(Long roleId, List<Long> menuIds) {
        List<RoleMenuDO> roleMenuList = menuIds.stream()
                .map(menuId -> {
                    RoleMenuDO roleMenu = new RoleMenuDO();
                    roleMenu.setRoleId(roleId);
                    roleMenu.setMenuId(menuId);
                    return roleMenu;
                }).collect(Collectors.toList());

        roleMenuMapper.batchInsert(roleMenuList);
    }

    private void assignRoleDepts(Long roleId, List<Long> deptIds) {
        List<RoleDeptDO> roleDeptList = deptIds.stream()
                .map(deptId -> {
                    RoleDeptDO roleDept = new RoleDeptDO();
                    roleDept.setRoleId(roleId);
                    roleDept.setDeptId(deptId);
                    return roleDept;
                }).collect(Collectors.toList());

        roleDeptMapper.batchInsert(roleDeptList);
    }
}
