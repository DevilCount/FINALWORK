package com.lis.auth.feign;

import com.lis.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "lis-user", path = "/user", fallbackFactory = UserFeignClientFallbackFactory.class)
public interface UserFeignClient {

    @GetMapping("/internal/username/{username}")
    Result<Map<String, Object>> getUserByUsername(@PathVariable("username") String username);

    @GetMapping("/internal/id/{id}")
    Result<Map<String, Object>> getUserById(@PathVariable("id") Long id);

    @GetMapping("/{userId}/roles")
    Result<List<String>> getRolesByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/{userId}/permissions")
    Result<List<String>> getPermissionsByUserId(@PathVariable("userId") Long userId);

    @PutMapping("/{id}/reset-password")
    Result<Void> updatePassword(@PathVariable("id") Long id, @RequestParam("passwordHash") String passwordHash);
}
