package com.lis.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lis.common.exception.BusinessException;
import com.lis.common.result.PageResult;
import com.lis.common.result.ResultCode;
import com.lis.user.dto.*;
import com.lis.user.entity.DeptDO;
import com.lis.user.entity.UserDO;
import com.lis.user.entity.UserRoleDO;
import com.lis.user.mapper.DeptMapper;
import com.lis.user.mapper.MenuMapper;
import com.lis.user.mapper.RoleMapper;
import com.lis.user.mapper.UserMapper;
import com.lis.user.mapper.UserRoleMapper;
import com.lis.user.service.UserService;
import com.lis.user.vo.RoleVO;
import com.lis.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final UserRoleMapper userRoleMapper;
    private final DeptMapper deptMapper;

    @Override
    public PageResult<UserVO> getUserList(UserQueryDTO queryDTO) {
        Page<UserDO> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<UserDO> userList = userMapper.selectUserList(
                queryDTO.getDeptId(),
                queryDTO.getUserName(),
                queryDTO.getNickName(),
                queryDTO.getPhone(),
                queryDTO.getStatus(),
                queryDTO.getUserType()
        );

        long total = userList.size();
        int start = (int) ((queryDTO.getPageNum() - 1) * queryDTO.getPageSize());
        int end = (int) Math.min(start + queryDTO.getPageSize(), total);
        List<UserDO> pagedList = userList.subList(start, end);

        List<UserVO> voList = pagedList.stream().map(this::convertToVO).collect(Collectors.toList());

        return PageResult.of(total, queryDTO.getPageNum(), queryDTO.getPageSize(), voList);
    }

    @Override
    public UserVO getUserById(Long id) {
        UserDO userDO = userMapper.selectById(id);
        if (userDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        UserVO userVO = convertToVO(userDO);
        List<Long> roleIds = userRoleMapper.selectRoleIdsByUserId(id);
        userVO.setRoleIds(roleIds);
        return userVO;
    }

    @Override
    public UserVO getUserByUserName(String userName) {
        UserDO userDO = userMapper.selectByUserName(userName);
        if (userDO == null) {
            return null;
        }
        return convertToVO(userDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserCreateDTO createDTO) {
        UserDO existUser = userMapper.selectByUserName(createDTO.getUserName());
        if (existUser != null) {
            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTS, "用户名已存在");
        }

        if (createDTO.getPassword() == null || createDTO.getPassword().isBlank()) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "密码不能为空");
        }

        UserDO userDO = new UserDO();
        BeanUtil.copyProperties(createDTO, userDO);
        userDO.setPassword(BCrypt.hashpw(createDTO.getPassword()));
        userDO.setStatus(createDTO.getStatus() != null ? createDTO.getStatus() : 0);
        userDO.setDelFlag(0);
        userDO.setCreateTime(LocalDateTime.now());
        userDO.setUpdateTime(LocalDateTime.now());

        userMapper.insert(userDO);

        if (createDTO.getRoleIds() != null && !createDTO.getRoleIds().isEmpty()) {
            assignUserRoles(userDO.getId(), createDTO.getRoleIds());
        }

        log.info("创建用户成功: userId={}, userName={}", userDO.getId(), userDO.getUserName());
        return userDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserUpdateDTO updateDTO) {
        UserDO existUser = userMapper.selectById(updateDTO.getId());
        if (existUser == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }

        UserDO userDO = new UserDO();
        BeanUtil.copyProperties(updateDTO, userDO);
        userDO.setUpdateTime(LocalDateTime.now());

        userMapper.updateById(userDO);

        if (updateDTO.getRoleIds() != null) {
            userRoleMapper.deleteByUserId(updateDTO.getId());
            if (!updateDTO.getRoleIds().isEmpty()) {
                assignUserRoles(updateDTO.getId(), updateDTO.getRoleIds());
            }
        }

        log.info("更新用户成功: userId={}", updateDTO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        UserDO userDO = userMapper.selectById(id);
        if (userDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }

        userMapper.deleteById(id);
        userRoleMapper.deleteByUserId(id);

        log.info("删除用户成功: userId={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteUsers(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        for (Long id : ids) {
            userMapper.deleteById(id);
            userRoleMapper.deleteByUserId(id);
        }

        log.info("批量删除用户成功: ids={}", ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(PasswordUpdateDTO passwordDTO) {
        if (passwordDTO.getUserId() == null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "用户ID不能为空");
        }

        UserDO userDO = userMapper.selectById(passwordDTO.getUserId());
        if (userDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }

        if (!BCrypt.checkpw(passwordDTO.getOldPassword(), userDO.getPassword())) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "旧密码错误");
        }

        String newPassword = BCrypt.hashpw(passwordDTO.getNewPassword());
        userMapper.updatePassword(passwordDTO.getUserId(), newPassword);

        log.info("修改密码成功: userId={}", passwordDTO.getUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long userId, String newPassword) {
        UserDO userDO = userMapper.selectById(userId);
        if (userDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }

        String password = BCrypt.hashpw(newPassword);
        userMapper.updatePassword(userId, password);

        log.info("重置密码成功: userId={}", userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPasswordWithHash(Long userId, String passwordHash) {
        UserDO userDO = userMapper.selectById(userId);
        if (userDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }

        userMapper.updatePassword(userId, passwordHash);

        log.info("重置密码成功: userId={}", userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long userId, Integer status) {
        UserDO userDO = userMapper.selectById(userId);
        if (userDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }

        userMapper.updateStatus(userId, status);

        log.info("更新用户状态成功: userId={}, status={}", userId, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoles(UserRoleAssignDTO assignDTO) {
        UserDO userDO = userMapper.selectById(assignDTO.getUserId());
        if (userDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }

        userRoleMapper.deleteByUserId(assignDTO.getUserId());

        if (assignDTO.getRoleIds() != null && !assignDTO.getRoleIds().isEmpty()) {
            assignUserRoles(assignDTO.getUserId(), assignDTO.getRoleIds());
        }

        log.info("分配角色成功: userId={}, roleIds={}", assignDTO.getUserId(), assignDTO.getRoleIds());
    }

    @Override
    public List<String> getRolesByUserId(Long userId) {
        return roleMapper.selectRolesByUserId(userId).stream()
                .map(role -> role.getRoleKey())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPermissionsByUserId(Long userId) {
        return menuMapper.selectPermsByUserId(userId);
    }

    @Override
    public Map<String, Object> getUserInfoByUsername(String username) {
        UserDO userDO = userMapper.selectByUserName(username);
        if (userDO == null) {
            return null;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("id", userDO.getId());
        result.put("username", userDO.getUserName());
        result.put("realName", userDO.getNickName());
        result.put("password", userDO.getPassword());
        result.put("status", userDO.getStatus());
        result.put("phone", userDO.getPhone());
        result.put("email", userDO.getEmail());
        result.put("deptId", userDO.getDeptId());
        result.put("avatar", userDO.getAvatar());

        if (userDO.getDeptId() != null) {
            DeptDO dept = deptMapper.selectById(userDO.getDeptId());
            if (dept != null) {
                result.put("deptName", dept.getDeptName());
            }
        }

        return result;
    }

    @Override
    public Map<String, Object> getUserInfoById(Long id) {
        UserDO userDO = userMapper.selectById(id);
        if (userDO == null) {
            return null;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("id", userDO.getId());
        result.put("username", userDO.getUserName());
        result.put("realName", userDO.getNickName());
        result.put("password", userDO.getPassword());
        result.put("status", userDO.getStatus());
        result.put("phone", userDO.getPhone());
        result.put("email", userDO.getEmail());
        result.put("deptId", userDO.getDeptId());
        result.put("avatar", userDO.getAvatar());

        if (userDO.getDeptId() != null) {
            DeptDO dept = deptMapper.selectById(userDO.getDeptId());
            if (dept != null) {
                result.put("deptName", dept.getDeptName());
            }
        }

        return result;
    }

    private UserVO convertToVO(UserDO userDO) {
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(userDO, userVO);

        List<RoleVO> roles = roleMapper.selectRolesByUserId(userDO.getId()).stream()
                .map(role -> {
                    RoleVO roleVO = new RoleVO();
                    BeanUtil.copyProperties(role, roleVO);
                    return roleVO;
                }).collect(Collectors.toList());
        userVO.setRoles(roles);

        return userVO;
    }

    private void assignUserRoles(Long userId, List<Long> roleIds) {
        List<UserRoleDO> userRoleList = roleIds.stream()
                .map(roleId -> {
                    UserRoleDO userRole = new UserRoleDO();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    return userRole;
                }).collect(Collectors.toList());

        userRoleMapper.batchInsert(userRoleList);
    }
}
