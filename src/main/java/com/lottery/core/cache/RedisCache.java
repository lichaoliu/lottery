package com.lottery.core.cache;

import com.lottery.common.cache.redis.RedisPersist;
import com.lottery.common.cache.redis.SharedJedisPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fengqinyun on 15/4/20.
 */
@Service
public class RedisCache {
    @Autowired
    protected SharedJedisPoolManager shareJedisPoolManager;

    protected RedisPersist getPersist() {
        return new RedisPersist(shareJedisPoolManager);
    }

    public void saveString(String key, String value) {
        try {
            getPersist().saveString(key, value);
        } catch (Exception e) {

        }
    }

    /**
     * 过期时间，单位:秒
     */
    public void saveString(String key, int secondes, String value) {
        try {
            getPersist().saveString(key, secondes, value);
        } catch (Exception e) {

        }
    }

    public String getString(String key) {

        try {
            return getPersist().getString(key);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> void save(String key, T value) {
        try {
            getPersist().save(key, value);
        } catch (Exception e) {

        }
    }

    public <T> void save(String key, int seconds, T value) {
        try {
            getPersist().save(key, seconds, value);
        } catch (Exception e) {

        }
    }

    public <T> T get(String key, Class<T> clazz) {

        try {
            return getPersist().get(key, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public void delete(String key) {

        try {
            getPersist().delete(key);
        } catch (Exception e) {

        }
    }
}
