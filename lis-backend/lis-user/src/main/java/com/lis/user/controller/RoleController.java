package com.lis.user.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.user.dto.*;
import com.lis.user.service.RoleService;
import com.lis.user.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @ApiOperation("分页查询角色列表")
    @GetMapping("/list")
    public Result<PageResult<RoleVO>> getRoleList(RoleQueryDTO queryDTO) {
        PageResult<RoleVO> result = roleService.getRoleList(queryDTO);
        return Result.success(result);
    }

    @ApiOperation("根据ID查询角色")
    @GetMapping("/{id}")
    public Result<RoleVO> getRoleById(@PathVariable Long id) {
        RoleVO roleVO = roleService.getRoleById(id);
        return Result.success(roleVO);
    }

    @ApiOperation("创建角色")
    @PostMapping
    public Result<Long> createRole(@Validated @RequestBody RoleCreateDTO createDTO) {
        Long roleId = roleService.createRole(createDTO);
        return Result.success("创建成功", roleId);
    }

    @ApiOperation("更新角色")
    @PutMapping
    public Result<Void> updateRole(@Validated @RequestBody RoleUpdateDTO updateDTO) {
        roleService.updateRole(updateDTO);
        return Result.success("更新成功", null);
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/{id}")
    public Result<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return Result.success("删除成功", null);
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping("/batch")
    public Result<Void> batchDeleteRoles(@RequestBody List<Long> ids) {
        roleService.batchDeleteRoles(ids);
        return Result.success("删除成功", null);
    }

    @ApiOperation("分配菜单")
    @PostMapping("/assign-menus")
    public Result<Void> assignMenus(@Validated @RequestBody RoleMenuAssignDTO assignDTO) {
        roleService.assignMenus(assignDTO);
        return Result.success("分配成功", null);
    }

    @ApiOperation("获取角色菜单ID列表")
    @GetMapping("/{roleId}/menus")
    public Result<List<Long>> getMenuIdsByRoleId(@PathVariable Long roleId) {
        List<Long> menuIds = roleService.getMenuIdsByRoleId(roleId);
        return Result.success(menuIds);
    }

    @ApiOperation("获取所有角色")
    @GetMapping("/all")
    public Result<List<RoleVO>> getAllRoles() {
        List<RoleVO> roles = roleService.getAllRoles();
        return Result.success(roles);
    }
}
