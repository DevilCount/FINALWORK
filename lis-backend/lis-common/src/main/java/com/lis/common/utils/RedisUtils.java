package com.lis.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public void set(String key, Object value, long seconds) {
        set(key, value, seconds, TimeUnit.SECONDS);
    }

    public void setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public String getString(String key) {
        Object value = get(key);
        return value != null ? value.toString() : null;
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    public Boolean expire(String key, long seconds) {
        return expire(key, seconds, TimeUnit.SECONDS);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public Long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    public void hSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public void hSetAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    @SuppressWarnings("unchecked")
    public <T> T hGet(String key, String field) {
        return (T) redisTemplate.opsForHash().get(key, field);
    }

    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void hDelete(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    public Boolean hHasKey(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    public Long lPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public Long rPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T lPop(String key) {
        return (T) redisTemplate.opsForList().leftPop(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T rPop(String key) {
        return (T) redisTemplate.opsForList().rightPop(key);
    }

    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    public Boolean zAdd(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    public Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public Set<Object> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    public RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }

    public RLock getFairLock(String lockKey) {
        return redissonClient.getFairLock(lockKey);
    }

    public RLock getReadWriteLock(String lockKey) {
        return redissonClient.getReadWriteLock(lockKey).writeLock();
    }

    public boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        RLock lock = getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            log.error("获取分布式锁失败: {}", e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void unlock(String lockKey) {
        RLock lock = getLock(lockKey);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    public boolean isLocked(String lockKey) {
        RLock lock = getLock(lockKey);
        return lock.isLocked();
    }

    public RBucket<Object> getBucket(String key) {
        return redissonClient.getBucket(key);
    }

    public void setBucket(String key, Object value) {
        redissonClient.getBucket(key).set(value);
    }

    public void setBucket(String key, Object value, long timeToLive, TimeUnit timeUnit) {
        redissonClient.getBucket(key).set(value, timeToLive, timeUnit);
    }

    public Object getBucketValue(String key) {
        return redissonClient.getBucket(key).get();
    }

    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void flushDb() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }
}
