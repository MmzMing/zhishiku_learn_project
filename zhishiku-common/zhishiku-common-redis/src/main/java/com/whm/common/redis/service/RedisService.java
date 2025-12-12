package com.whm.common.redis.service;


import cn.hutool.core.thread.ThreadUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis服务工具类，提供对Redis各种数据结构的操作方法
 * 包括字符串、列表、集合、哈希表等常用操作，以及分布式锁支持的安全自增ID生成功能
 *
 * @author : 吴华明
 * @since 2025-12-12  18:43
 */
@Component
public class RedisService {
    @Resource
    public RedisTemplate redisTemplate;

    @Resource
    private RedissonClient redission;

    /**
     * 设置键值如果不存在
     *
     * @param key   键
     * @param value 值
     * @param <T>   值的类型
     * @return 操作是否成功
     */
    public <T> boolean setCacheObjectIfAbsent(final String key, final T value) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value);
        return result != null && result;
    }

    /**
     * 获取字符串类型的缓存值
     *
     * @param key 缓存键
     * @return 缓存值，如果不存在则返回空字符串
     */
    public String getWithString(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? value.toString() : "";
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @param <T>   值的类型
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     * @param <T>      值的类型
     */
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @param <T> 返回值类型
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key key
     * @return 删除是否成功
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return 删除的数量
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 获取模糊匹配的key集合
     *
     * @param vagueKey 模糊匹配的key前缀
     * @return 匹配的key集合
     */
    public Set<String> allKey(final String vagueKey) {
        return redisTemplate.keys(vagueKey + "*");
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @param <T>      数据类型
     * @return 缓存的元素数量
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return 0;
        }
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @param <T> 数据类型
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @param <T>     数据类型
     * @return 缓存操作对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        if (dataSet != null && !dataSet.isEmpty()) {
            setOperation.add(dataSet.toArray((T[]) new Object[dataSet.size()]));
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key key
     * @param <T> 数据类型
     * @return 缓存数据的集合
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key     缓存键值
     * @param dataMap Map缓存的数据
     * @param <T>     值的类型
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null && !dataMap.isEmpty()) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key key
     * @param <T> 值的类型
     * @return 缓存数据的Map
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     * @param <T>   值的类型
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @param <T>  值的类型
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 删除Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return 删除的元素个数
     */
    public Long deleteCacheMapValue(final String key, final String hKey) {
        return redisTemplate.opsForHash().delete(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @param <T>   值的类型
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 获取自增id
     *
     * @param key 自增id的key
     * @return 自增id
     */
    public Long getNextId(String key) {
        String lockKey = "lock:" + key;
        // 获取分布式锁，设置10秒的租约时间防止死锁
        RLock lock = redission.getLock(lockKey);
        try {
            if (!lock.tryLock(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("获取锁失败: " + key);
            }
            // 自增操作
            return redisTemplate.opsForValue().increment(key, 1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("线程在获取键值锁时被中断: " + key, e);
        } finally {
            // 确保只有持有锁的线程才能释放锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 增量操作指定的键
     *
     * @param key   自增id的key
     * @param delta 增量值
     * @return 增量后的值
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 获取自增id，带重试机制
     *
     * @param key        自增id的key
     * @param retryCount 重试次数
     * @return 自增id
     */
    public Long getNextIdWithRetry(String key, int retryCount) {
        for (int i = 0; i < retryCount; i++) {
            try {
                Long nextId = getNextId(key);
                if (nextId != null) {
                    return nextId;
                }
            } catch (Exception e) {
                // 记录异常但继续重试
                if (i == retryCount - 1) {
                    // 最后一次重试仍然失败，则抛出异常
                    throw new RuntimeException("Failed to get next ID for key: " + key + " after " + retryCount + " retries", e);
                }
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted during retry delay", e);
            }
        }
        return null;
    }

    /**
     * 获取自增id，无重试次数限制
     *
     * @param key 自增id的key
     * @return 自增id
     */
    public Long getNextIdWithRetry(String key) {
        int maxRetries = 10; // 设置最大重试次数防止无限循环
        for (int i = 0; i < maxRetries; i++) {
            try {
                Long nextId = getNextId(key);
                if (nextId != null) {
                    return nextId;
                }
            } catch (Exception e) {
                // 记录异常但继续重试
                if (i == maxRetries - 1) {
                    // 最后一次重试仍然失败，则抛出异常
                    throw new RuntimeException("Failed to get next ID for key: " + key + " after " + maxRetries + " retries", e);
                }
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted during retry delay", e);
            }
        }
        throw new RuntimeException("Failed to get next ID for key: " + key + " after " + maxRetries + " retries");
    }

    /**
     * 获取业务 id
     *
     * @param preKey      redisKey前置
     * @param planVersion 计划名称
     * @param planType    计划类型
     * @return id
     */
    public String getBusinessId(String preKey, String planVersion, String planType) {
        String redisKey = preKey + planVersion + "_" + planType;
        //获取自增id
        long nextId = getNextIdWithRetry(redisKey);
        return planVersion + nextId;
    }
}

