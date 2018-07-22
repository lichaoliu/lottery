package com.lottery.core.cache;

import com.lottery.common.cache.CacheService;
import net.rubyeye.xmemcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class XmemcachedService implements CacheService {
    private final Logger logger=LoggerFactory.getLogger(getClass());
	private  MemcachedClient memcachedClient;

    public XmemcachedService(){}
	@Override
	public <T extends Serializable> void set(String key, T t) {
		try{
			memcachedClient.set(key, 0, t);
			
		}catch(Exception e){
			//logger.error("保存key={}出错",key,e);
			logger.error("保存key={}出错:{}",new Object[]{key,e.getMessage()});
		}

	}

	@Override
	public <T extends Serializable> void set(String key, Integer expire, T t) {
		try{
			if(expire==null){
				expire=0;
			}
			memcachedClient.set(key, expire, t);
		}catch(Exception e){
			//logger.error("保存key={}出错",key,e);
			logger.error("保存key={}出错:{}",new Object[]{key,e.getMessage()});
		}

	}

	@Override
	public <T extends Serializable> void set(String key, Integer expire, T t, long timeout) {
		try{
			if(expire==null){
				expire=0;
			}
			
			memcachedClient.set(key, expire, t,timeout);
		}catch(Exception e){
			//logger.error("保存key={}出错",key,e);
			logger.error("保存key={}出错:{}",new Object[]{key,e.getMessage()});
		}

	}

	@Override
	public <T extends Serializable> void set(String key, T t, long timeout) {
		try{
			memcachedClient.set(key, 0, t, timeout);
		}catch(Exception e){
			//logger.error("保存key={}出错",key,e);
			logger.error("保存key={}出错:{}",new Object[]{key,e.getMessage()});
		}

	}

	@Override
	public <T extends Serializable>  T get(String key) {
		T t=null;
		try{
			
			t=memcachedClient.get(key);
		}catch(Exception e){
			//logger.error("获取key={}缓存出错",key,e);
			logger.error("获取key={}出错:{}",new Object[]{key,e.getMessage()});
		}
		return t;
	}

	@Override
	public <T extends Serializable> void checkToSet(String key, T t) {
		 Object temp = null;
		 try{
			 temp=memcachedClient.get(key);
		 }catch(Exception e){
			 //logger.error("检查key={}缓存出错",key,e);
			 logger.error("检查key={}出错:{}",new Object[]{key,e.getMessage()});
		 }
		 if(temp==null){
			 set(key, t);
		 }

	}

	@Override
	public void delete(String key) {
		try{
			memcachedClient.delete(key);
		}catch(Exception e){
			logger.error("删除key={}缓存出错",key,e);
		}

	}

	@Override
	public void flushAll() {
		try{
			memcachedClient.flushAll();;
		}catch(Exception e){
			logger.error("删除所有缓存出错",e);
		}

	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	

	@Override
	public <T extends Serializable> Map<String, T> mget(Collection<String> listkey) {
		Map<String,T> map=null;
		try{
			map=memcachedClient.get(listkey);
		}catch(Exception e){
			logger.error("批量获取出错",e);
		}
		return map;
	}

    @Override
    public <T extends Serializable> T get(String tableName, String key) {
        return null;
    }

    public <T extends Serializable> XmemcachedService(String tableName, String key) {

    }

    @Override
	public <T extends Serializable> T get(String key, long timeout) {
		T t=null;
		try{
			
			t=memcachedClient.get(key,timeout);
		}catch(Exception e){
			logger.error("获取key={}出错:{}",new Object[]{key,e.getMessage()});
		}
		return t;
	}
	
	

}
