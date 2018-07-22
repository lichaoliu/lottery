package com.lottery.test.service.ticket.redis;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.cache.redis.RedisLock;
import com.lottery.common.cache.redis.SharedJedisPoolManager;
import com.lottery.test.SpringBeanTest;

public class RedisLockTest extends SpringBeanTest {

	
	@Autowired
	protected SharedJedisPoolManager shareJedisPoolManager;
	@Test
	public void testLock(){
		String orderId="ddd";
		RedisLock lock = new RedisLock(shareJedisPoolManager, String.format("lottery_order_test_%s", orderId), 180);

		boolean hasLocked = false;

		try {
			// 等待获取锁30秒
			hasLocked = lock.tryLock(30000l, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("123");
		System.out.println("456");
		
		
		if (hasLocked) {
			// 如果获取到锁, 执行完处理后解锁
			lock.unlock();
		}
	}
}
