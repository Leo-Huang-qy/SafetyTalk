package com.leo.safetytalk.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 保存数据到Redis并设置过期时间
     *
     * @param key                Redis键
     * @param value              要保存的值
     * @param expirationMinutes 过期时间（分钟）
     */
    public void saveWithExpiration(String key, Object value, long expirationMinutes) {
        redisTemplate.opsForValue().set(key, value, expirationMinutes, TimeUnit.MINUTES);
    }

    /**
     * 获取Redis中保存的数据
     *
     * @param key  Redis键
     * @param type 类型
     * @param <T>  泛型
     * @return 保存的值
     */
    public <T> T get(String key, Class<T> type) {
        return type.cast(redisTemplate.opsForValue().get(key));
    }


    /**
     * 获取Redis中保存的数据
     *
     * @param key           Redis键
     * @param typeReference 类型引用
     * @param <T>           泛型
     * @return 保存的值
     */
    public <T> T get(String key, ParameterizedTypeReference<T> typeReference) {
        return (T) redisTemplate.<Object>opsForValue().get(key);
    }

    /**
     * 从Redis中删除指定的键
     *
     * @param key Redis键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}