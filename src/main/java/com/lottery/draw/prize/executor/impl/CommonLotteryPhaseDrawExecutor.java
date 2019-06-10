/**
 *
 */
package com.lottery.draw.prize.executor.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


import com.lottery.draw.prize.thread.MultiThreadLotteryTypeInstanceOrderDrawRunnable;
import com.lottery.draw.prize.thread.RecoveryPrizingLoteryOrderRunnable;

import org.springframework.stereotype.Service;

import com.lottery.common.PageBean;

import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.TopicName;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.HighFrequencyLottery;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.contains.lottery.PhaseEncaseStatus;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.CoreMathUtils;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.LottypeConfig;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.draw.ILotteryDrawTask;
import com.lottery.draw.prize.executor.AbstractLotteryDrawExecutor;


/**
 * @author fengqinyun
 */
@Service
public class  CommonLotteryPhaseDrawExecutor extends AbstractLotteryDrawExecutor {

    private static List<Integer> orderStatusList;
    private static List<Integer> orderResultStatusList;

    static {
        orderStatusList = new ArrayList<Integer>();
        orderStatusList.add(OrderStatus.PRINTED.value);
        orderStatusList.add(OrderStatus.HALF_PRINTED.value);
        orderResultStatusList = new ArrayList<Integer>();
        orderResultStatusList.add(OrderResultStatus.not_open.value);
    }

    @Override
    public void updateDrawStatus(ILotteryDrawTask lotteryDrawTask) throws Exception {
        lotteryPhaseService.updatePhasePrizeState(lotteryDrawTask.getLotteryType(), lotteryDrawTask.getPhase(), PhaseStatus.prize_start.value);
        lotteryPhaseService.updatePhaseEncashState(lotteryDrawTask.getLotteryType(), lotteryDrawTask.getPhase(), PhaseEncaseStatus.draw_start.value);

    }



    @Override
    protected void afterDrawHandle(ILotteryDrawTask lotteryDrawTask) throws Exception {
        if (LotteryType.getJclqValue().contains(lotteryDrawTask.getLotteryType()) || LotteryType.getJczqValue().contains(lotteryDrawTask.getLotteryType()) 
        		|| LotteryType.getGuanyajunValue().contains(lotteryDrawTask.getLotteryType())|| LotteryType.getDcValue().contains(lotteryDrawTask.getLotteryType())) {

            try{
                  RecoveryPrizingLoteryOrderRunnable prizingLoteryOrderRunnable=(RecoveryPrizingLoteryOrderRunnable)systemService.get("recoveryPrizingLoteryOrderRunnable");
                  prizingLoteryOrderRunnable.executeNotify();
            }catch (Exception e){
                logger.error("算奖回收线程出错",e);
            }

            return;
        }
       prizeHandler.prizeCallbak(lotteryDrawTask.getLotteryType(), lotteryDrawTask.getPhase());

    }

    @Override
    protected boolean beforeDrawHandle(ILotteryDrawTask lotteryDrawTask) throws Exception {

        if (LotteryType.getJclq().contains(LotteryType.getLotteryType(lotteryDrawTask.getLotteryType()))) {
            jingcaiService.updateLqResultToOpen(String.valueOf(lotteryDrawTask.getLastMatchNum()));
        } else if (LotteryType.getJczq().contains(LotteryType.getLotteryType(lotteryDrawTask.getLotteryType()))) {
            jingcaiService.updateZqResultToOpen(String.valueOf(lotteryDrawTask.getLastMatchNum()));
        } else if (LotteryType.getDc().contains(LotteryType.getLotteryType(lotteryDrawTask.getLotteryType()))) {
            beidanService.updateDanchangResultToOpen(lotteryDrawTask.getPhase(), String.valueOf(lotteryDrawTask.getLastMatchNum()), LotteryType.getLotteryType(lotteryDrawTask.getLotteryType()));
        } else if(LotteryType.getGuanyajun().contains(LotteryType.getLotteryType(lotteryDrawTask.getLotteryType()))) {
        	jingcaiGuanjunService.updateGuanyajunResultToOpen(lotteryDrawTask.getLotteryType(), lotteryDrawTask.getPhase(), lotteryDrawTask.getLastMatchNum()<10?"0"+lotteryDrawTask.getLastMatchNum():lotteryDrawTask.getLastMatchNum()+"");
        } else {
            LotteryPhase currentphase = lotteryPhaseService.getByTypeAndPhase(lotteryDrawTask.getLotteryType(), lotteryDrawTask.getPhase());
            if (currentphase == null) {
                logger.error("进行开奖期号不存在lotterytype={} phase={}", new Object[]{lotteryDrawTask.getLotteryType(), lotteryDrawTask.getPhase()});
                return false;
            }

            if (LotteryType.getZc().contains(LotteryType.getLotteryType(lotteryDrawTask.getLotteryType()))) {
                if (StringUtil.isEmpty(currentphase.getPrizeDetail())) {
                    logger.error("开奖彩种为足彩,奖级信息为空,不能算奖lotterytype={} phase={}", new Object[]{lotteryDrawTask.getLotteryType(), lotteryDrawTask.getPhase()});
                    return false;
                }
            }
            updateDrawStatus(lotteryDrawTask);
            sendPrizeTopic(lotteryDrawTask.getLotteryType(), lotteryDrawTask.getPhase(), lotteryDrawTask.getWinCode());
        }
        return true;
    }


    private void sendPrizeTopic(int lotterytype, String phase, String wincode) {
        try {
            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("lotterytype", lotterytype);
            headers.put("phase", phase);
            headers.put("wincode", wincode);
            topicMessageSendService.sendMessage(TopicName.prizeOpen, headers);
        } catch (Exception e) {
            logger.error("发送开奖号码出错", e);
        }

    }




    @Override
    protected void dispatchOrderToThread(ILotteryDrawTask lotteryDrawTask) throws Exception {

        int lotteryType = lotteryDrawTask.getLotteryType();
        String phaseNo = lotteryDrawTask.getPhase();

        int queryThreadCount = getQueryThreadCount();

        LotteryPhase lotteryphase=lotteryPhaseService.getByTypeAndPhase(lotteryType,phaseNo);
        if (lotteryphase==null){
            throw new Exception("彩种:"+lotteryphase+",彩期:"+phaseNo+"不存在");
        }
        Calendar cdFirst = Calendar.getInstance();
        cdFirst.setTime(lotteryphase.getStartSaleTime());
        if (HighFrequencyLottery.contains(LotteryType.getLotteryType(lotteryType))){//如果是快彩,
            cdFirst.add(Calendar.MINUTE,-5);
        }
//        else if (LotteryType.getJcValue().contains(lotteryType)){
//            cdFirst.add(Calendar.DATE,-7);
//        }
        else{
            cdFirst.add(Calendar.DATE,-2);
        }

        Calendar cdLast = Calendar.getInstance();
        cdLast.setTime(lotteryphase.getEndSaleTime());
        logger.error("彩种:{},期号:{},开始时间:{},结束时间:{}",lotteryType,phaseNo,CoreDateUtils.DateToStr(cdFirst.getTime()),CoreDateUtils.DateToStr(cdLast.getTime()));
        long timeFirst = cdFirst.getTimeInMillis();
        long timeLast = cdLast.getTimeInMillis();

        DecimalFormat df = new DecimalFormat("0");
        int minutesCount = Integer.valueOf(df.format(CoreMathUtils.div(CoreMathUtils.sub(timeLast, timeFirst), 60 * 1000d) + 1));
        int count = Integer.valueOf(df.format(CoreMathUtils.div(minutesCount, queryThreadCount) + 1));
        final String threadStr = lotteryType + "_" + phaseNo;
        ThreadFactory threadFactory = new ThreadFactory() {
            private int i = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, threadStr + "_draw_query_thread_" + i++);
            }

        };
        ExecutorService queryThreadPool = Executors.newCachedThreadPool(threadFactory);

        CountDownLatch queryLatch = new CountDownLatch(queryThreadCount);

        for (int i = 0; i < queryThreadCount; i++) {

            int beginMinutes = i * count;
            int endMinutes = (i + 1) * count;

            CommonOrderQueryRunnable commonOrderQueryRunnable = new CommonOrderQueryRunnable(queryLatch);
            commonOrderQueryRunnable.setLotteryDrawTask(lotteryDrawTask);
            commonOrderQueryRunnable.setBeginMinutes(beginMinutes);
            commonOrderQueryRunnable.setEndMinutes(endMinutes);
            commonOrderQueryRunnable.setBeginDate(cdFirst.getTime());
            queryThreadPool.execute(commonOrderQueryRunnable);
        }
        try {
            queryLatch.await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }

        queryThreadPool.shutdown();
        logger.info("分发订单到开奖线程结束");

    }

    protected List<LotteryOrder> getLotteryOrderBySqlOrderType(ILotteryDrawTask lotteryDrawTask, String sqlOrderType, boolean totalFlag) {

        int lotteryType = lotteryDrawTask.getLotteryType();
        String phaseNo = lotteryDrawTask.getPhase();
        PageBean<LotteryOrder> pageBean = new PageBean<LotteryOrder>();
        pageBean.setMaxResult(1);
        pageBean.setTotalFlag(totalFlag);
        return lotteryOrderService.getByStatusAndType(lotteryType, phaseNo, orderStatusList, orderResultStatusList, sqlOrderType, lotteryDrawTask.getLastMatchNum(), pageBean);

    }


    protected void dispatch(List<LotteryOrder> orderList, String wincode) {
        if (orderList == null || orderList.isEmpty()) {
            return;
        }
        int perThreadPlanCount = (int) Math.ceil((double) orderList.size() / getDrawThreadCount());

        logger.info("每个子线程每次处理方案的最大数：{}", perThreadPlanCount);
        for (int i = 0, index = 0; i < orderList.size() && index < getDrawThreadCount(); i += perThreadPlanCount, index++) {
            int sub_start = i;
            int sub_end = i + perThreadPlanCount;

            // 参考subList方法的声明，这里是 *大于* 而不是 *>=*
            if (sub_end > orderList.size()) {
                sub_end = orderList.size();
            }

            // 分拆待处理任务
            List<LotteryOrder> subList = orderList.subList(sub_start, sub_end);

            logger.info("获取通用开奖子线程， index = {}", index);

            for (LotteryOrder lotteryOrder : subList) {
                sendJms(lotteryOrder.getId(),lotteryOrder.getLotteryType(), wincode);
            }

        }
    }

    @Override
    protected Integer initDrawOrderTotal(ILotteryDrawTask lotteryDrawTask) {
        int lotteryType = lotteryDrawTask.getLotteryType();
        String phaseNo = lotteryDrawTask.getPhase();
        PageBean<LotteryOrder> pageBean = new PageBean<LotteryOrder>();

        pageBean.setMaxResult(1);
        pageBean.setTotalFlag(true);
        lotteryOrderService.getByStatusAndType(lotteryType, phaseNo, orderStatusList, orderResultStatusList, null, lotteryDrawTask.getLastMatchNum(), pageBean);

        int totalOrderCount = pageBean.getTotalResult();
        logger.error("彩种:{},期号:{},最大场次:{},需要开奖的个数是:{}", lotteryDrawTask.getLotteryType(), lotteryDrawTask.getPhase(), lotteryDrawTask.getLastMatchNum(), totalOrderCount);
        return totalOrderCount;
    }


    @Override
    protected boolean validateDrawTask(ILotteryDrawTask lotteryDrawTask) {
        if (lotteryDrawTask == null) {
            logger.error("进行开奖lotteryDrawTask为空");
           return  false;
        }
        LottypeConfig lottypeConfig = null;
        try {
            lottypeConfig = lottypeconfigCache.get(LotteryType.getPhaseTypeValue(LotteryType.getSingleValue(lotteryDrawTask.getLotteryType())));
        } catch (Exception e) {
            logger.error("lottypeConfig lotterytype={} phase={}获取异常", new Object[]{lotteryDrawTask.getLotteryType(), lotteryDrawTask.getPhase()});
            return false;
        }

        if (lottypeConfig == null || lottypeConfig.getOnprize() == null) {
            logger.error("lottypeConfig lotterytype={}为空，退出算奖", lotteryDrawTask.getLotteryType());
            return false;
        }
        if (lottypeConfig.getOnprize() != YesNoStatus.yes.value) {
            logger.error("lottypeConfig lotterytype={} phase={}不自动算奖，退出算奖", new Object[]{lotteryDrawTask.getLotteryType(), lotteryDrawTask.getPhase()});
            return false;
        }

        try {
            if (LotteryType.getJclqValue().contains(lotteryDrawTask.getLotteryType()) || LotteryType.getJczqValue().contains(lotteryDrawTask.getLotteryType()) 
            		||LotteryType.getGuanyajunValue().contains(lotteryDrawTask.getLotteryType())|| LotteryType.getDcValue().contains(lotteryDrawTask.getLotteryType())) {
                return true;
            }
            return prizeService.updatePhaseWinStatus(lotteryDrawTask.getLotteryType(), lotteryDrawTask.getPhase(), lotteryDrawTask.getWinCode());
        } catch (Exception e) {
            logger.error("算奖校验异常",e);
           return false;
        }
    }

    @Override
    protected void checkOrder(ILotteryDrawTask lotteryDrawTask) throws Exception {
        if (LotteryType.getJclqValue().contains(lotteryDrawTask.getLotteryType()) || LotteryType.getJczqValue().contains(lotteryDrawTask.getLotteryType()) || LotteryType.getDcValue().contains(lotteryDrawTask.getLotteryType())||LotteryType.getGuanyajunValue().contains(lotteryDrawTask.getLotteryType())) {
            return;
        }


    }


    /**
     * 订单回收
     */
    protected void recycleOrder(ILotteryDrawTask lotteryDrawTask) {
        if (LotteryType.getJclqValue().contains(lotteryDrawTask.getLotteryType()) || LotteryType.getJczqValue().contains(lotteryDrawTask.getLotteryType()) || LotteryType.getDcValue().contains(lotteryDrawTask.getLotteryType())||LotteryType.getGuanyajunValue().contains(lotteryDrawTask.getLotteryType())) {
            return;
        }

        long waittime = 1200 * 1000;//毫秒
        if (HighFrequencyLottery.contains(LotteryType.get(lotteryDrawTask.getLotteryType()))) {
            waittime = 6*1000;
        }


        synchronized (this) {
            try {
                this.wait(waittime);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }

        MultiThreadLotteryTypeInstanceOrderDrawRunnable multiThreadLotteryTypeInstanceOrderDrawRunnable=(MultiThreadLotteryTypeInstanceOrderDrawRunnable)systemService.get("multiThreadLotteryTypeInstanceOrderDrawRunnable");
        if (multiThreadLotteryTypeInstanceOrderDrawRunnable!=null){
            multiThreadLotteryTypeInstanceOrderDrawRunnable.addDrawTask(lotteryDrawTask);
            multiThreadLotteryTypeInstanceOrderDrawRunnable.executeNotify();
        }else {
            logger.error("获取的开奖线程出错");
        }

    }


    protected class CommonOrderQueryRunnable extends AbstractThreadRunnable {
        private CountDownLatch latch;
        private ILotteryDrawTask lotteryDrawTask;

        private Date beginDate;
        private int beginMinutes;// 每个时间段查询线程开始的分数
        private int endMinutes;// 每个时间段查询线程结束的分数


        public CommonOrderQueryRunnable(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        protected void executeRun() {


            Calendar cdBegin = Calendar.getInstance();
            cdBegin.setTime(beginDate);
            cdBegin.add(Calendar.MINUTE, beginMinutes);
            Date beginTime = cdBegin.getTime();

            Calendar cdEnd = Calendar.getInstance();
            cdEnd.setTime(beginDate);
            cdEnd.add(Calendar.MINUTE, endMinutes);
            Date endTime = cdEnd.getTime();

            String threadName=Thread.currentThread().getName();

           // logger.error("算奖线程:{},开始算奖",threadName);


            int totalSize = getWaitingHandleLotteryOrderIds(beginTime, endTime);

//            if (orderList != null && !orderList.isEmpty()) {
//
//                totalSize += orderList.size();
//                dispatch(orderList, lotteryDrawTask.getWinCode());
//            }


            latch.countDown();
            logger.error("查询可开奖订单查询子线程:" + threadName + ",退出,共查询到订单个数:" + totalSize);
        }



        private int  getWaitingHandleLotteryOrderIds(Date beginTime, Date endTime) {

            int lotteryType = lotteryDrawTask.getLotteryType();
            String phase = lotteryDrawTask.getPhase();
            int count=0;
            PageBean<LotteryOrder> pageBean = new PageBean<LotteryOrder>();
            pageBean.setMaxResult(getQueryPageSize());
            int page = 1;
            do {
                pageBean.setPageIndex(page);
                List<String> waitingOrderList = null;
                try {
                    waitingOrderList = lotteryOrderService.getIdByStatusAndDate(lotteryType, phase, orderStatusList, orderResultStatusList, beginTime, endTime, lotteryDrawTask.getLastMatchNum(), pageBean);
                } catch (Exception e) {
                    logger.error("根据时间查询报错", e);

                }
                if (waitingOrderList == null || waitingOrderList.isEmpty()) {
                    break;
                }


                count +=waitingOrderList.size();

                try{


                    for(String orderId:waitingOrderList){

                        lotteryOrderService.updateOrderResultStatus(orderId,OrderResultStatus.prizing.value,lotteryDrawTask.getWinCode());

                        sendJms(orderId,lotteryDrawTask.getLotteryType(),lotteryDrawTask.getWinCode());
                    }

                }catch (Exception e){
                    logger.error("发送jms出错",e);
                }
                if (waitingOrderList.size() < getQueryPageSize()) {
                    break;
                }
            } while (true);

            return count;

        }


       



        public CountDownLatch getLatch() {
            return latch;
        }

        public void setLatch(CountDownLatch latch) {
            this.latch = latch;
        }

        public ILotteryDrawTask getLotteryDrawTask() {
            return lotteryDrawTask;
        }

        public void setLotteryDrawTask(ILotteryDrawTask lotteryDrawTask) {
            this.lotteryDrawTask = lotteryDrawTask;
        }

        public Date getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(Date beginDate) {
            this.beginDate = beginDate;
        }

        public int getBeginMinutes() {
            return beginMinutes;
        }

        public void setBeginMinutes(int beginMinutes) {
            this.beginMinutes = beginMinutes;
        }

        public int getEndMinutes() {
            return endMinutes;
        }

        public void setEndMinutes(int endMinutes) {
            this.endMinutes = endMinutes;
        }


    }

    @Override
    protected List<LotteryOrder> findOrderListForRecycle(ILotteryDrawTask lotteryDrawTask) {
        int lotteryType = lotteryDrawTask.getLotteryType();
        String phaseNo = lotteryDrawTask.getPhase();
        List<LotteryOrder> allRecycleOrderList = new ArrayList<LotteryOrder>();

        PageBean<LotteryOrder> pageBean = new PageBean<LotteryOrder>();
        pageBean.setMaxResult(getQueryPageSize());
        int page = 1;
        do {
            pageBean.setPageIndex(page);
            List<LotteryOrder> recycleOrderList = null;
            try {
                recycleOrderList = lotteryOrderService.getByStatusAndType(lotteryType, phaseNo, orderStatusList, orderResultStatusList, null, lotteryDrawTask.getLastMatchNum(), pageBean);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                break;
            }
            if (recycleOrderList == null || recycleOrderList.isEmpty()) {
                break;
            }
            allRecycleOrderList.addAll(recycleOrderList);
            if (recycleOrderList.size() < getQueryPageSize()) {
                break;
            }
            page++;
        } while (true);

        return allRecycleOrderList;
    }


    protected void sendJms(String orderId,int lotteryType, String windcode) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderId", orderId);
            map.put("wincode", windcode);
            QueueName queueName=QueueName.prizeExecute;
            if (HighFrequencyLottery.contains(lotteryType)){
                queueName=QueueName.prizeorderGaopin;
            }
            this.queueMessageSendService.sendMessage(queueName, map);
        } catch (Exception e) {
            logger.error("发送算奖订单出错", e);
        }
    }



    @Override
    protected void startDrawThread(CountDownLatch latch, ILotteryDrawTask lotteryDrawTask) {


    }


}
