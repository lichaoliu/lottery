package com.lottery.test.redis;

import com.lottery.common.cache.redis.RedisPersist;
import com.lottery.common.cache.redis.SharedJedisPoolManager;
import com.lottery.test.SpringBeanTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisTest extends SpringBeanTest {
	@Autowired
	SharedJedisPoolManager shareJedisPoolManager;
	@Test
	public void testRedis(){
		
		try {
		//	RedisLock lock = new RedisLock(this.shareJedisPoolManager, String.format("plan_ticket_split_%s", "test=="), 600);
	        //boolean hasLocked = lock.tryLock(300000l, TimeUnit.MILLISECONDS);
           // RedisQueue queue=new RedisQueue(shareJedisPoolManager,"testqueue");
			RedisPersist persist=new RedisPersist(shareJedisPoolManager);
			persist.saveString("test_kk",30,"123456");
			System.out.printf("ddd");

        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
	
}
