package com.lottery.common.cache.redis;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import redis.clients.jedis.ShardedJedis;

/**
 * 基于Redis实现的锁, 供不同的Java实例之间使用
 * 
 */
public class RedisLock implements Lock {
    //key的默认超时时间,单位秒，过期删除
    protected static final int DEFAULT_KEY_EXPIRE = 5;

    protected static final String KEY_PREFIX = "Lock_redis_key_";

    private String key;
    private int keyExpire;// lockkey的过期时间,单位秒
    private volatile boolean  locked = false;
    private SharedJedisPoolManager redisManager;


    /**
     * default key expire : 5 , timeunit:second
     *
     * @param redisManager
     * @param key
     */
    public RedisLock(SharedJedisPoolManager redisManager, String key) {
        this(redisManager, key, DEFAULT_KEY_EXPIRE);
    }

    /**
     * @param redisManager
     * @param key
     * @param keyExpire    key过期时间,单位:秒
     */
    public RedisLock(SharedJedisPoolManager redisManager, String key, int keyExpire) {
        this.redisManager = redisManager;
        this.key = KEY_PREFIX + key;
        this.keyExpire = keyExpire;
    }

 
    public void lock() {
        try {
            lockInterruptibly();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }

 
    public void lockInterruptibly() throws InterruptedException {
        while (!tryLock()) {

        }
    }

 
    public boolean tryLock() {
        ShardedJedis jedisCommands=null;
        try {
            jedisCommands = redisManager.getConnection();
            if (jedisCommands == null) {
                return false;
            }
            long expires = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(keyExpire) + 1;
            String expiresStr = String.valueOf(expires);
            if (jedisCommands.setnx(key, expiresStr) == 1) {
                jedisCommands.expire(key, keyExpire);
                locked = true;
                return true;
            }

            String currentValueStr = jedisCommands.get(key);
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                String oldValueStr = jedisCommands.getSet(key, expiresStr);
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    jedisCommands.expire(key, keyExpire);
                    locked = true;
                    return true;
                }
            }

        }finally {
            redisManager.releaseConnection(jedisCommands);
        }
        return false;

    }

   
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
    	
    	 /**
        long timeout = timeUnit.toNanos(l);// timeout单位纳秒

        long nano = System.nanoTime();

        final Random random = new Random();
        while ((System.nanoTime() - nano) < timeout) {
              if (!tryLock()){
                  break;
              }
            // 短暂休眠，避免出现活锁
            Thread.sleep(3, random.nextInt(500));
        }
        return true;
        **/
       
        long timeout = timeUnit.toMillis(l);// timeout单位毫秒
        while (!tryLock()){
           if(timeout <= 0){
               return false;
            }
           timeout -= 100;
           try{
        	   synchronized (this) {
                   this.wait(100);
             }
           }catch(Exception e){
        	   
           }
           
        }
        return true;


    }

 
    public void unlock() {
    	ShardedJedis jedisCommands = null;
        try {
            if (locked) {
                jedisCommands = redisManager.getConnection();
                if (jedisCommands == null){
                    throw new IllegalStateException("unlock failed, lock key is "+ key +", cause by :connection is null! ");
                }
                jedisCommands.del(key);
            }
        } finally {
            redisManager.releaseConnection(jedisCommands);
            locked = false;
        }
    }


    public Condition newCondition() {
        return null;
    }


}
