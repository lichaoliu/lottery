/**
 * 
 */
package com.lottery.core.cache;

import java.util.Collection;
import java.util.Map;

import com.lottery.common.cache.IMemcachedObject;

/**
 * Memcached服务接口
 * @author fengqinyun
 *
 */
public interface MemcachedService {
	
	public boolean delete(String key) throws Exception;

	/**
	 * @param key
	 * @param object
	 * @param alive 缓存存活时间 单位：秒
	 * @return
	 */
	public boolean set(String key, IMemcachedObject object, int alive) throws Exception;
	
	/**
	 * 读取缓存，按照默认超时时间处理
	 * @param key
	 * @return
	 */
	public IMemcachedObject get(String key) throws Exception;
	
	/**
	 * 支持自定义超时设置的设置缓存方法，单位：秒
	 * @param key
	 * @param object
	 * @param timeout
	 * @param alive 缓存存活时间 单位：秒
	 * @return
	 */
	public boolean set(String key, IMemcachedObject object, int alive, long timeout) throws Exception;
	
	/**
	 * 读取缓存，支持设置超时时间
	 * @param key
	 * @param timeout
	 * @return
	 */
	public IMemcachedObject get(String key, long timeout) throws Exception;
	
	/**
	 * 批量读取缓存
	 * @param keys key的集合
	 * @return
	 */
	public Map<String, IMemcachedObject> mget(Collection<String> keys) throws Exception;
	
	/**
	 * 批量读取缓存
	 * @param keys key的集合
	 * @param timeout
	 * @return
	 */
	public Map<String, IMemcachedObject> mget(Collection<String> keys, long timeout) throws Exception;
}
