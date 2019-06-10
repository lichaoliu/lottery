package com.lottery.core.cache;

import java.util.Collection;
import java.util.Map;

import com.lottery.common.cache.IMemcachedObject;

import net.rubyeye.xmemcached.MemcachedClient;

public class MemcacheServiceImpl implements MemcachedService {

	private  MemcachedClient memcachedClient;
	@Override
	public boolean delete(String key) throws Exception {
		return memcachedClient.delete(key);
	}

	@Override
	public boolean set(String key, IMemcachedObject object, int alive)
			throws Exception {
		return memcachedClient.set(key, alive, object);
	}

	@Override
	public IMemcachedObject get(String key) throws Exception {
		return memcachedClient.get(key);
	}

	@Override
	public boolean set(String key, IMemcachedObject object, int alive,
			long timeout) throws Exception {
		return memcachedClient.set(key, alive, object, timeout);
	}

	@Override
	public IMemcachedObject get(String key, long timeout) throws Exception {
		return memcachedClient.get(key, timeout);
	}

	@Override
	public Map<String, IMemcachedObject> mget(Collection<String> keys)
			throws Exception {
		Map<String, IMemcachedObject> result = memcachedClient.get(keys);
		return result;
		
	}

	@Override
	public Map<String, IMemcachedObject> mget(Collection<String> keys,
			long timeout) throws Exception {
		Map<String, IMemcachedObject> result = memcachedClient.get(keys,timeout);
		return result;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
	

}
