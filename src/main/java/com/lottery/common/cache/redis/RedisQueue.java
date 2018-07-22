package com.lottery.common.cache.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.ShardedJedis;

import com.lottery.common.util.JsonUtil;

public class RedisQueue {
	 private SharedJedisPoolManager redisManager;
	 
	 private String queueName;

 
    public RedisQueue(SharedJedisPoolManager redisManager,String queueName){
		 this.redisManager=redisManager;
		 this.queueName=queueName;
	 }
	 
	 public RedisQueue(){}
	 
	public SharedJedisPoolManager getRedisManager() {
		return redisManager;
	}


	public void setRedisManager(SharedJedisPoolManager redisManager) {
		this.redisManager = redisManager;
	}


	public <T> void push(List<T>  values){
		
		ShardedJedis jedis = redisManager.getConnection();
		try{
            for(T t:values){
                String value= JsonUtil.toJson(t);
                jedis.lpush(queueName, value);
            }

		}finally{
			redisManager.releaseConnection(jedis);
		}
	}
	
      public <T> void push(T value){
		
		ShardedJedis jedis = redisManager.getConnection();
		try{
			jedis.lpush(queueName, JsonUtil.toJson(value));
		}finally{
			redisManager.releaseConnection(jedis);
		}
	}

	public Long size(){
		ShardedJedis jedis = redisManager.getConnection();
        try {  
            if (!jedis.exists(queueName))  
                return 0l;  
            return jedis.llen(queueName);  
        } finally {  
        	redisManager.releaseConnection(jedis);
        }  
	}
	
	public <T> T poll(Class<T> clazz){
		ShardedJedis jedis = redisManager.getConnection();
        try {  
            if (!jedis.exists(queueName))  
                return null;
            String jsonT = jedis.rpop(queueName);

            return jsonT == null ? null : JsonUtil.fromJsonToObject(jsonT,clazz);
        } finally { 
        	redisManager.releaseConnection(jedis);
        }  
	}
	public <T> T poll(long timeout, TimeUnit unit,Class<T> clazz) {
        ShardedJedis jedis = redisManager.getConnection();

        try {  
            long nanos = unit.toNanos(timeout);  
            while (true) {  
                long lastTime = System.nanoTime();  
                try {
					Thread.sleep(300l);
				} catch (InterruptedException e) {
					
				}
                if (!jedis.exists(queueName))  
                    continue;  
                String jsonT = jedis.rpop(queueName);  
                if (jsonT != null) {  
                    return JsonUtil.fromJsonToObject(jsonT,clazz);
                }  
                if (nanos <= 0)  
                    return null;  
                nanos -= (System.nanoTime() - lastTime);  
            }  
        } finally {  
        	redisManager.releaseConnection(jedis);
        }  
    }


}
