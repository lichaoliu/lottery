package com.lottery.core.handler;

import com.lottery.core.IdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by fengqinyun on 15/9/8.
 */
@Service
public class IdGeneratorHandler {
    private  static final   int lotteryOrderCount=1000;
    private Queue<Long> orderqueue=new ArrayBlockingQueue<Long>(lotteryOrderCount);
    private Lock lock = new ReentrantLock();
    @Autowired
    private IdGeneratorService idGeneratorService;
    public String getLotteryOrderId(){

        Long id=orderqueue.poll();
        if (id==null){
            try {
                lock.lock();
                if (orderqueue.poll()!=null){
                    return idGeneratorService.getLotteryOrderId(orderqueue.poll());
                }else {
                    Long start=idGeneratorService.getLotteryOrderCount(lotteryOrderCount);
                    for (int i=0;i<lotteryOrderCount;i++){
                        orderqueue.add(start);
                        start ++;
                    }
                    id=orderqueue.poll();
                    if (id!=null){
                        return idGeneratorService.getLotteryOrderId(id);
                    }
                }

            }finally {
                lock.unlock();

            }

        }else {
            return idGeneratorService.getLotteryOrderId(id);
        }

        return null;
    }
}
