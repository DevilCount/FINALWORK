package com.lis.user.feign;

import com.lis.common.result.Result;
import com.lis.user.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "lis-user", path = "/user")
public interface UserFeignClient {

    @GetMapping("/username/{username}")
    Result<Map<String, Object>> getUserByUsername(@PathVariable("username") String username);

    @GetMapping("/{userId}/roles")
    Result<List<String>> getRolesByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/{userId}/permissions")
    Result<List<String>> getPermissionsByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/{id}")
    Result<UserVO> getUserById(@PathVariable("id") Long id);
}
