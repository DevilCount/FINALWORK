package com.lis.auth.feign;

import com.lis.common.exception.BusinessException;
import com.lis.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {

    @Override
    public UserFeignClient create(Throwable cause) {
        return new UserFeignClient() {
            @Override
            public Result<Map<String, Object>> getUserByUsername(String username) {
                log.error("UserFeignClient.getUserByUsername调用失败: username={}, error={}", username, cause.getMessage());
                throw new BusinessException("用户服务不可用，请稍后重试");
            }

            @Override
            public Result<Map<String, Object>> getUserById(Long id) {
                log.error("UserFeignClient.getUserById调用失败: id={}, error={}", id, cause.getMessage());
                throw new BusinessException("用户服务不可用，请稍后重试");
            }

            @Override
            public Result<List<String>> getRolesByUserId(Long userId) {
                log.error("UserFeignClient.getRolesByUserId调用失败: userId={}, error={}", userId, cause.getMessage());
                throw new BusinessException("用户服务不可用，请稍后重试");
            }

            @Override
            public Result<List<String>> getPermissionsByUserId(Long userId) {
                log.error("UserFeignClient.getPermissionsByUserId调用失败: userId={}, error={}", userId, cause.getMessage());
                throw new BusinessException("用户服务不可用，请稍后重试");
            }

            @Override
            public Result<Void> updatePassword(Long id, String passwordHash) {
                log.error("UserFeignClient.updatePassword调用失败: id={}, error={}", id, cause.getMessage());
                throw new BusinessException("用户服务不可用，请稍后重试");
            }
        };
    }
}
