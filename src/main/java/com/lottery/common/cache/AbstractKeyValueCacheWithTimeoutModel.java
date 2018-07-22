package com.lottery.common.cache;

import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.common.util.MD5Util;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: fengqinyun
 */
public abstract class AbstractKeyValueCacheWithTimeoutModel<K extends Serializable, V extends Serializable> extends AbstractKeyValueCacheModel<K, V> {

    /**
     * 缓存超时时长，单位毫秒
     */
    private long timeoutMillis = 30000;

    @Resource(name="xmemcachedService")
	private CacheService cacheService;
    
    protected int mcExpire=600;//秒
    /**
     * 缓存创建时的时间戳
     */
    protected Map<K, Long> cachedTimeMillisMap = new ConcurrentHashMap<K, Long>();

    abstract protected V getFromCacheWithoutTimeout(K key) throws CacheNotFoundException;

    @Override
    protected V getFromCache(K key) throws CacheNotFoundException {
        if (this.getTimeoutMillis() <= 0) {
            // 如果未设置有效的超时时间，等价于不使用超期
            return this.getFromCacheWithoutTimeout(key);
        }
        V value = this.getFromCacheWithoutTimeout(key);

        Long cachedTime = this.cachedTimeMillisMap.get(key);
        if (cachedTime == null || cachedTime + this.getTimeoutMillis() <= System.currentTimeMillis()) {
            // 如果未设置时间戳或已过超期时间，认为缓存不存在
            logger.info("找到缓存，但是缓存已超期，需要更新，key={}, value={}", key, value);
            throw new CacheNotFoundException("找到缓存，但是缓存已超期，需要更新");
        }
        return value;
    }

    abstract protected void setCacheWithoutTimeout(K key, V value) throws CacheUpdateException;

    @Override
    protected void setCache(K key, V value) throws CacheUpdateException {
        this.setCacheWithoutTimeout(key, value);
        this.cachedTimeMillisMap.put(key, System.currentTimeMillis());
    }

    public void setTimeoutMillis(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public CacheService getCacheService() {
        return cacheService;
    }

    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }


    public int getMcExpire() {
        return mcExpire;
    }

    public void setMcExpire(int mcExpire) {
        this.mcExpire = mcExpire;
    }

    /**
     * 
     * */
    protected void setMC(K key, V v){
    	this.setMC(key, v,getMcExpire());
    }



/**
 * @param  expire 缓存时间(单位:秒)
 * */
    protected  void setMC(K key, V v,int expire){
        try{
            String md5Key=getKey(key);
            cacheService.set(md5Key,expire, v);
        }catch(Exception e){

        }

    }

    protected V getFromMC(K key){
    	try{
    		String md5Key=getKey(key);
        	return cacheService.get(md5Key,3000);//3秒钟超时
    	}catch(Exception e){
    		return null;
    	}
    	
    }
    protected  String getKey(K key){
        try{
            return  MD5Util.toMd5(key+getClass().getName());
        }catch (Exception e){
            return key.toString();
        }

    }
    @Override
    public void  remove(K key){
        try {
            cacheService.delete(getKey(key));
        }catch (Exception e){
            logger.error("清除缓存失败",e);

        }

    }
}
