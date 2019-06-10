package com.lottery.common.cache.redis;

import com.lottery.common.util.JsonUtil;
import redis.clients.jedis.ShardedJedis;

/**
 * Created by fengqinyun on 15/4/19.
 */
public class RedisPersist {
    private SharedJedisPoolManager redisManager;

    public  RedisPersist(SharedJedisPoolManager redisManager){
        this.redisManager=redisManager;
    }

    public  void saveString(String key,String value){
        ShardedJedis jedis=null;
        try{
            jedis=redisManager.getConnection();
            //jedis.set(key.getBytes(), SerializationUtils.serialize(value));
            jedis.set(key,value);
        }finally{
            redisManager.releaseConnection(jedis);
        }
    }
    /***
     * 过期时间，单位:秒
     * */
    public  void saveString(String key,int secondes,String value){
        ShardedJedis jedis=null;
        try{
            jedis=redisManager.getConnection();
            jedis.setex(key, secondes, value);
        }finally{
            redisManager.releaseConnection(jedis);
        }
    }
    public  String getString(String key){
        ShardedJedis jedis=null;
        try{
            jedis=redisManager.getConnection();
            //return SerializationUtils.deserialize(bytes)
            return jedis.get(key);
        }finally{
            redisManager.releaseConnection(jedis);
        }
    }
    public <T> void save(String key,T value){

        ShardedJedis jedis = null;
        try{
            jedis=redisManager.getConnection();

            jedis.set(key, JsonUtil.toJson(value));
        }finally{
            redisManager.releaseConnection(jedis);
        }
    }
    public <T> void save(String key,int seconds,T value){

        ShardedJedis jedis = null;
        try{
            jedis=redisManager.getConnection();

            jedis.setex(key, seconds, JsonUtil.toJson(value));
        }finally{
            redisManager.releaseConnection(jedis);
        }
    }
    public <T> T get(String key,Class<T> clazz){
        ShardedJedis jedis = null;
        try {
            jedis = redisManager.getConnection();
             String value=jedis.get(key);
            return value == null ? null : JsonUtil.fromJsonToObject(value,clazz);
        } finally {
            redisManager.releaseConnection(jedis);
        }
    }
    public  void delete(String key){
        ShardedJedis jedis = null;
        try{
            jedis=redisManager.getConnection();
            jedis.del(key);
        }finally{
            redisManager.releaseConnection(jedis);
        }
    }

}
