package com.lis.user.controller;

import com.lis.common.result.Result;
import com.lis.user.dto.*;
import com.lis.user.service.MenuService;
import com.lis.user.vo.MenuTreeVO;
import com.lis.user.vo.MenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "菜单权限管理")
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @ApiOperation("查询菜单列表")
    @GetMapping("/list")
    public Result<List<MenuVO>> getMenuList(MenuQueryDTO queryDTO) {
        List<MenuVO> result = menuService.getMenuList(queryDTO);
        return Result.success(result);
    }

    @ApiOperation("根据ID查询菜单")
    @GetMapping("/{id}")
    public Result<MenuVO> getMenuById(@PathVariable Long id) {
        MenuVO menuVO = menuService.getMenuById(id);
        return Result.success(menuVO);
    }

    @ApiOperation("创建菜单")
    @PostMapping
    public Result<Long> createMenu(@Validated @RequestBody MenuCreateDTO createDTO) {
        Long menuId = menuService.createMenu(createDTO);
        return Result.success("创建成功", menuId);
    }

    @ApiOperation("更新菜单")
    @PutMapping
    public Result<Void> updateMenu(@Validated @RequestBody MenuUpdateDTO updateDTO) {
        menuService.updateMenu(updateDTO);
        return Result.success("更新成功", null);
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("/{id}")
    public Result<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return Result.success("删除成功", null);
    }

    @ApiOperation("获取菜单树")
    @GetMapping("/tree")
    public Result<List<MenuVO>> getMenuTree() {
        List<MenuVO> tree = menuService.getMenuTree();
        return Result.success(tree);
    }

    @ApiOperation("获取菜单树节点（用于选择器）")
    @GetMapping("/tree-nodes")
    public Result<List<MenuTreeVO>> getMenuTreeNodes() {
        List<MenuTreeVO> nodes = menuService.getMenuTreeNodes();
        return Result.success(nodes);
    }

    @ApiOperation("获取用户菜单")
    @GetMapping("/user/{userId}")
    public Result<List<MenuVO>> getMenusByUserId(@PathVariable Long userId) {
        List<MenuVO> menus = menuService.getMenusByUserId(userId);
        return Result.success(menus);
    }

    @ApiOperation("获取角色菜单")
    @GetMapping("/role/{roleId}")
    public Result<List<MenuVO>> getMenusByRoleId(@PathVariable Long roleId) {
        List<MenuVO> menus = menuService.getMenusByRoleId(roleId);
        return Result.success(menus);
    }

    @ApiOperation("获取用户权限列表")
    @GetMapping("/user/{userId}/permissions")
    public Result<List<String>> getPermissionsByUserId(@PathVariable Long userId) {
        List<String> permissions = menuService.getPermissionsByUserId(userId);
        return Result.success(permissions);
    }
}
