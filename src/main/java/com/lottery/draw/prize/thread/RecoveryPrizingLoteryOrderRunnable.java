package com.lottery.draw.prize.thread;

import com.lottery.common.cache.redis.SharedJedisPoolManager;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.lottery.HighFrequencyLottery;

import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.draw.prize.impl.RecoveryPrizingLotteryOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by fengqinyun on 16/7/3.
 */

public class RecoveryPrizingLoteryOrderRunnable extends AbstractThreadRunnable {
    private long interval = 180000;
    private long subInterval=30000;
    @Autowired
    private SharedJedisPoolManager shareJedisPoolManager;
    @Autowired
    private LotteryOrderService lotteryOrderService;
    @Autowired
    protected QueueMessageSendService queueMessageSendService;

    @Override
    protected void executeRun() {
        while (running) {
            try {
                RecoveryPrizingLotteryOrder recoveryPrizingLoteryOrder = new RecoveryPrizingLotteryOrder(shareJedisPoolManager);
                int count = 0;
                int subCount=0;
                while (true) {
                    String orderId = recoveryPrizingLoteryOrder.poll();
                    if (StringUtils.isBlank(orderId)) {
                        break;
                    }
                    lotteryOrderService.updateOrderResultStatus(orderId, OrderResultStatus.not_open.value,null);
                    count++;
                    subCount++;
                    logger.error("将订单:{},开奖状态由开奖中改为未开奖,subCount={}", orderId,subCount);
                    if(subCount>200){
                        synchronized (this) {
                            try {
                                wait(subInterval);
                            } catch (InterruptedException e) {
                                logger.error("子计数出现问题", e);
                            }
                            subCount=0;
                        }
                    }
                }
                logger.error("本次处理的订单数为:{}", count);
            } catch (Exception e) {
                logger.error("回收出错", e);
            }



            synchronized (this) {
                try {
                    wait(this.getInterval());
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }

            this.orderPrizing();//定时任务做一下缓冲


        }
    }



    protected void orderPrizing(){
        List<Integer> orderStatusList=new ArrayList<Integer>();
        orderStatusList.add(OrderStatus.PRINTED.value);
        orderStatusList.add(OrderStatus.HALF_PRINTED.value);
     try {
         for(int orderStatus:orderStatusList){
             List<LotteryOrder> orderList=lotteryOrderService.getByStatus(orderStatus,OrderResultStatus.prizing.value,10);
             if(orderList!=null){
                 for (LotteryOrder lotteryOrder:orderList){
                      this.sendJms(lotteryOrder.getId(),lotteryOrder.getLotteryType(),lotteryOrder.getWincode());
                 }
             }
         }
     }catch (Exception e){
        logger.error("订单回收出错",e);
     }
    }


    protected void sendJms(String orderId,int lotteryType, String windcode) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderId", orderId);
            map.put("wincode", windcode);
            logger.error("orderId={},重新发送开奖队列",orderId);
            QueueName queueName=QueueName.prizeExecute;
            if (HighFrequencyLottery.contains(lotteryType)){
                queueName=QueueName.prizeorderGaopin;
            }
            this.queueMessageSendService.sendMessage(queueName, map);

        } catch (Exception e) {
            logger.error("回收算奖订单出错", e);
        }
    }


    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }



}
