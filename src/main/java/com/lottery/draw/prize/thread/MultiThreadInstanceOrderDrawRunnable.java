package com.lottery.draw.prize.thread;


import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.handler.PrizeHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by fengqinyun on 16/7/9.
 */
public class MultiThreadInstanceOrderDrawRunnable extends AbstractThreadRunnable {

    private long interval = 30000l; // 每次处理间隔(毫秒)
    private Queue<String> waintingDrawOrderQueue=new LinkedTransferQueue<String>();
    private String windcode;

    private PrizeHandler prizeHandler;

    public void addQueue(String orderId,String windcode){
        waintingDrawOrderQueue.add(orderId);
        this.windcode=windcode;
    }

    public void addQueue(List<String> orderIdList,String windcode){
        waintingDrawOrderQueue.addAll(orderIdList);
        this.windcode=windcode;
    }

    @Override
    protected void executeRun() {
        if(prizeHandler==null){
            logger.error("prizehandler is null,return");
            return;
        }
        while (running){
            while (true){
                String orderId=waintingDrawOrderQueue.poll();
                if(StringUtils.isBlank(orderId)){
                    break;
                }
                try {
                    prizeHandler.drawWork(orderId,windcode);
                } catch (Exception e) {
                    logger.error("orderId={}算奖出错",orderId);
                    logger.error("算奖出错",e);
                }

            }
            synchronized (this) {
                try {
                    wait(interval);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }

        }



    }


    public PrizeHandler getPrizeHandler() {
        return prizeHandler;
    }

    public void setPrizeHandler(PrizeHandler prizeHandler) {
        this.prizeHandler = prizeHandler;
    }
}
