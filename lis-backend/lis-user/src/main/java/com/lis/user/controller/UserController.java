package com.lis.user.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.user.dto.*;
import com.lis.user.service.UserService;
import com.lis.user.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "用户管理")
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation("分页查询用户列表")
    @GetMapping("/list")
    public Result<PageResult<UserVO>> getUserList(UserQueryDTO queryDTO) {
        PageResult<UserVO> result = userService.getUserList(queryDTO);
        return Result.success(result);
    }

    @ApiOperation("根据ID查询用户")
    @GetMapping("/{id}")
    public Result<UserVO> getUserById(@PathVariable Long id) {
        UserVO userVO = userService.getUserById(id);
        return Result.success(userVO);
    }

    @ApiOperation("创建用户")
    @PostMapping
    public Result<Long> createUser(@Validated @RequestBody UserCreateDTO createDTO) {
        Long userId = userService.createUser(createDTO);
        return Result.success("创建成功", userId);
    }

    @ApiOperation("更新用户")
    @PutMapping
    public Result<Void> updateUser(@Validated @RequestBody UserUpdateDTO updateDTO) {
        userService.updateUser(updateDTO);
        return Result.success("更新成功", null);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success("删除成功", null);
    }

    @ApiOperation("批量删除用户")
    @DeleteMapping("/batch")
    public Result<Void> batchDeleteUsers(@RequestBody List<Long> ids) {
        userService.batchDeleteUsers(ids);
        return Result.success("删除成功", null);
    }

    @ApiOperation("修改密码")
    @PutMapping("/password")
    public Result<Void> updatePassword(@Validated @RequestBody PasswordUpdateDTO passwordDTO,
                                       @RequestHeader(value = "X-User-Id", required = false) Long headerUserId) {
        if (passwordDTO.getUserId() == null && headerUserId != null) {
            passwordDTO.setUserId(headerUserId);
        }
        userService.updatePassword(passwordDTO);
        return Result.success("密码修改成功", null);
    }

    @ApiOperation("重置密码")
    @PutMapping("/{id}/reset-password")
    public Result<Void> resetPassword(@PathVariable Long id, @RequestParam String passwordHash) {
        userService.resetPasswordWithHash(id, passwordHash);
        return Result.success("密码重置成功", null);
    }

    @ApiOperation("更新用户状态")
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        userService.updateStatus(id, status);
        return Result.success("状态更新成功", null);
    }

    @ApiOperation("分配角色")
    @PostMapping("/assign-roles")
    public Result<Void> assignRoles(@Validated @RequestBody UserRoleAssignDTO assignDTO) {
        userService.assignRoles(assignDTO);
        return Result.success("分配成功", null);
    }

    @ApiOperation("获取用户角色列表")
    @GetMapping("/{userId}/roles")
    public Result<List<String>> getRolesByUserId(@PathVariable Long userId) {
        List<String> roles = userService.getRolesByUserId(userId);
        return Result.success(roles);
    }

    @ApiOperation("获取用户权限列表")
    @GetMapping("/{userId}/permissions")
    public Result<List<String>> getPermissionsByUserId(@PathVariable Long userId) {
        List<String> permissions = userService.getPermissionsByUserId(userId);
        return Result.success(permissions);
    }

    @ApiOperation("根据用户名获取用户信息")
    @GetMapping("/username/{username}")
    public Result<Map<String, Object>> getUserInfoByUsername(@PathVariable String username) {
        Map<String, Object> userInfo = userService.getUserInfoByUsername(username);
        if (userInfo != null) {
            userInfo.remove("password");
            userInfo.put("hasPassword", true);
        }
        return Result.success(userInfo);
    }

    @GetMapping("/internal/username/{username}")
    public Result<Map<String, Object>> getUserInfoByUsernameInternal(@PathVariable String username) {
        Map<String, Object> userInfo = userService.getUserInfoByUsername(username);
        return Result.success(userInfo);
    }

    @GetMapping("/internal/id/{id}")
    public Result<Map<String, Object>> getUserInfoByIdInternal(@PathVariable Long id) {
        Map<String, Object> userInfo = userService.getUserInfoById(id);
        return Result.success(userInfo);
    }
}
