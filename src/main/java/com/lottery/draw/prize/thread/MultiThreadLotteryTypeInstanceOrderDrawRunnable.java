package com.lottery.draw.prize.thread;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.common.thread.ThreadContainer;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.handler.PrizeHandler;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.draw.ILotteryDrawTask;

import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fengqinyun on 16/7/9.
 */
public class MultiThreadLotteryTypeInstanceOrderDrawRunnable extends AbstractThreadRunnable {
    private long interval = 90000l; // 每次处理间隔(毫秒)

    private static List<Integer> orderStatusList;
    private static List<Integer> orderResultStatusList;

    static {
        orderStatusList = new ArrayList<Integer>();
        orderStatusList.add(OrderStatus.PRINTED.value);
        orderStatusList.add(OrderStatus.HALF_PRINTED.value);
        orderResultStatusList = new ArrayList<Integer>();
        orderResultStatusList.add(OrderResultStatus.not_open.value);
        orderResultStatusList.add(OrderResultStatus.prizing.value);
    }


    private Map<Integer,MultiThreadInstanceOrderDrawRunnable> orderDrawRunnableMap=new ConcurrentHashMap<Integer,MultiThreadInstanceOrderDrawRunnable>();

    @Autowired
    protected PrizeHandler prizeHandler;

    @Autowired
    private LotteryOrderService lotteryOrderService;
    private ILotteryDrawTask lotteryDrawTask;





    public void addDrawTask(ILotteryDrawTask lotteryDrawTask){
        this.lotteryDrawTask=lotteryDrawTask;

    }
    protected MultiThreadInstanceOrderDrawRunnable initInstance(Integer lotteryType) {
        if (orderDrawRunnableMap.containsKey(lotteryType)) {
            return orderDrawRunnableMap.get(lotteryType);
        }

        synchronized (this) {
            if (orderDrawRunnableMap.containsKey(lotteryType)) {
                return orderDrawRunnableMap.get(lotteryType);
            }
            MultiThreadInstanceOrderDrawRunnable runnable =new MultiThreadInstanceOrderDrawRunnable();
            runnable.setPrizeHandler(prizeHandler);
            ThreadContainer threadContainer = new ThreadContainer(runnable, "彩种(" + lotteryType + ")算奖子线程");
            threadContainer.setBeforeRunWaitTime(100);
            threadContainer.start();
            orderDrawRunnableMap.put(lotteryType, runnable);
            return runnable;
        }
    }

    @Override
    protected void executeRun() {



        this.execute();
        this.destroyInstance();

    }

    private void execute(){
        while (running){

            if(lotteryDrawTask!=null){
                int lotteryType = lotteryDrawTask.getLotteryType();
                String phase = lotteryDrawTask.getPhase();
                int count=0;
                int max=15;
                PageBean<LotteryOrder> pageBean = new PageBean<LotteryOrder>();
                pageBean.setMaxResult(max);
                int page = 1;
                do {
                    pageBean.setPageIndex(page);
                    List<LotteryOrder> recycleOrderList = null;
                    try {
                        recycleOrderList = lotteryOrderService.getByStatusAndType(lotteryType, phase, orderStatusList, orderResultStatusList, null, lotteryDrawTask.getLastMatchNum(), pageBean);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        break;
                    }
                    if (recycleOrderList == null || recycleOrderList.isEmpty()) {
                        break;
                    }
                    count +=recycleOrderList.size();
                    dispatch(recycleOrderList);
                    if (recycleOrderList.size() < max) {
                        break;
                    }
                    page++;
                } while (true);

                logger.error("开奖详情:{},共处理:{}条数据",lotteryDrawTask,count);
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




    private void dispatch(List<LotteryOrder> lotteryOrderList){
        for (LotteryOrder lotteryOrder:lotteryOrderList){
            String orderId=lotteryOrder.getId();
            Integer lotteryType=lotteryDrawTask.getLotteryType();
            if(lotteryOrder.getOrderStatus()==OrderResultStatus.not_open.value){
                lotteryOrderService.updateOrderResultStatus(orderId,OrderResultStatus.prizing.value,lotteryDrawTask.getWinCode());
            }
            MultiThreadInstanceOrderDrawRunnable runnable=this.initInstance(lotteryType);
            runnable.addQueue(orderId,lotteryDrawTask.getWinCode());
            runnable.executeNotify();
        }
    }


    public void executeStop() {
        super.executeStop();
        this.destroyInstance();
    }



    /**
     * 销毁送票子线程
     * @throws Exception
     */
    protected void destroyInstance() {
        try{
            if (orderDrawRunnableMap != null) {
                Set<Integer> terminalIdSet = orderDrawRunnableMap.keySet();
                for (Integer lotteryType : terminalIdSet) {
                    MultiThreadInstanceOrderDrawRunnable runnable = orderDrawRunnableMap.get(lotteryType);
                    runnable.executeStop();
                }
                orderDrawRunnableMap.clear();
            }
        }catch (Exception e){
            logger.error("销毁线程出错",e);
        }

    }
}
