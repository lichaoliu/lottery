package com.lottery.draw.prize.impl;


import com.lottery.common.cache.redis.RedisQueue;
import com.lottery.common.cache.redis.SharedJedisPoolManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by fengqinyun on 16/7/3.
 *
 * 回收正在
 */
public class RecoveryPrizingLotteryOrder {

    private final Logger logger= LoggerFactory.getLogger(getClass());


    public static  String RECOVERY_ORDER_QUEUE="order_recovery_prizing";

    private SharedJedisPoolManager sharedJedisPoolManager;


    public RecoveryPrizingLotteryOrder(SharedJedisPoolManager sharedJedisPoolManager){
        this.sharedJedisPoolManager=sharedJedisPoolManager;
    }

    public void pull(String orderId){


        try{

            RedisQueue queue=new RedisQueue(sharedJedisPoolManager,RECOVERY_ORDER_QUEUE);
            queue.push(orderId);
        }catch (Exception e){
          logger.error("放入redis出错",e);
        }
    }


    public String poll(){
        try{
            RedisQueue queue=new RedisQueue(sharedJedisPoolManager,RECOVERY_ORDER_QUEUE);
            return queue.poll(String.class);
        }catch (Exception e){
            logger.error("取redis数据出错",e);
            return null;
        }

    }
}
