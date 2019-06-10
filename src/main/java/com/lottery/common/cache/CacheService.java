package com.lottery.common.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface CacheService {
	 /**
	  * 增加缓存
	  * @param key 缓存名称
	  * @param t 缓存值
	  * */
	  public abstract <T extends Serializable> void set(String key, T t);
		 /**
		  * 增加缓存
		  * @param key 缓存名称
		  * @param expire 过期时间 单位：s
		  * @param t 缓存值
		  * */
	  public abstract <T extends Serializable> void set(String key, Integer expire, T t);
		 /**
		  * 增加缓存
		  * @param key 缓存名称
		  * @param expire 过期时间 单位：s
		  * @param timeout 超时时间
		  * @param t 缓存值
		  * */
	  public abstract <T extends Serializable> void set(String key, Integer expire, T t,long timeout);
	  /**
		  * 增加缓存
		  * @param key 缓存名称
		  * @param t 缓存值
		  * @param  timeout 超时时间：毫秒
		  * */
	  public abstract <T extends Serializable> void set(String key, T t, long timeout);
	  /**
       * 获取缓存
       * @param key 缓存名称
       * @return T 缓存
       * */
	  public abstract <T extends Serializable> T get(String key);
	  /**
       * 获取缓存
       * @param key 缓存名称
       * @param timeout 超时时间 ：毫秒
       * @return T 缓存
       * */
	  public abstract <T extends Serializable> T get(String key,long timeout);
      /**
       * 检查缓存
       * @param key 缓存名称
       * @param t 缓存值
       * */
	  public abstract <T extends Serializable> void checkToSet(String key, T t);
	   /**
       * 删除缓存
       * @param key 缓存名称
       * */
	  public abstract void delete(String key);
	   /**
       * 清除所有缓存
       * */
	  public abstract void flushAll();
	  
	  public <T extends Serializable> Map<String,T> mget(Collection<String> listkey);
      /***
       *
       * 获取获取缓存
       * @param  tableName 某张表
       * @param key
       * */

      public    <T extends Serializable> T get(String tableName, String key);

}
