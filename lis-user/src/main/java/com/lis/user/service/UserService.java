package com.lis.user.service;

import com.lis.common.result.PageResult;
import com.lis.user.dto.*;
import com.lis.user.vo.UserVO;

import java.util.List;
import java.util.Map;

public interface UserService {

    PageResult<UserVO> getUserList(UserQueryDTO queryDTO);

    UserVO getUserById(Long id);

    UserVO getUserByUserName(String userName);

    Long createUser(UserCreateDTO createDTO);

    void updateUser(UserUpdateDTO updateDTO);

    void deleteUser(Long id);

    void batchDeleteUsers(List<Long> ids);

    void updatePassword(PasswordUpdateDTO passwordDTO);

    void resetPassword(Long userId, String newPassword);

    void updateStatus(Long userId, Integer status);

    void assignRoles(UserRoleAssignDTO assignDTO);

    List<String> getRolesByUserId(Long userId);

    List<String> getPermissionsByUserId(Long userId);

    Map<String, Object> getUserInfoByUsername(String username);
}
