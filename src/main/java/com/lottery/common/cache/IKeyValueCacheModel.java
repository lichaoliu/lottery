package com.lottery.common.cache;

import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * key-value缓存处理模型，支持自动创建
 * @author fengqinyun
 * @param <K> 缓存的key类型
 * @param <V> 缓存的value类型
 */
public interface IKeyValueCacheModel<K extends Serializable, V extends Serializable> {

    /**
     * 查找单个缓存
     * @param key 缓存中的key
     * @return 缓存中的对象值
     * @throws CacheNotFoundException
     * @throws CacheUpdateException
     */
    public V get(K key) throws CacheNotFoundException, CacheUpdateException;

    /**
     * 批量查找，只返回找到的值，取得返回值以后需要进行处理
     * @param keyList 批量查找的key列表
     * @return 已找到的缓存集合
     * @throws CacheNotFoundException
     * @throws CacheUpdateException
     */
    public Map<K, V> mget(List<K> keyList) throws CacheNotFoundException, CacheUpdateException;

    public  void remove(K key);
}
