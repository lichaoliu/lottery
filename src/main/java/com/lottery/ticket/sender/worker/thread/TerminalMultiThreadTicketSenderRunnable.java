package com.lottery.ticket.sender.worker.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.thread.ThreadContainer;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.LotteryTicketConfigService;
import com.lottery.log.LotteryLog;

/**
 * Created by fengqinyun on 16/5/6.
 *
 * 终端多线程送票
 * 每个终端多个线程送票
 * @author fengqinyun
 */
public class TerminalMultiThreadTicketSenderRunnable extends CommonTicketSenderRunnable{
    private long instanceInterval = 1500l;//间隔时间(毫秒)
    private Map<Long, List<MultiThreadInstanceTicketSenderRunnable>> terminalRunnableMap = new ConcurrentHashMap<Long,  List<MultiThreadInstanceTicketSenderRunnable>>();



    @Autowired
    private LotteryTicketConfigService lotteryTicketConfigService;
    /**
     * 初始化送票子线程并启动
     * 如果指定终端号的子线程已存在，直接返回，否则创建
     * @throws Exception
     */
    protected List<MultiThreadInstanceTicketSenderRunnable> initInstance(Long terminalId,LotteryType lotteryType) {

        if (terminalRunnableMap.containsKey(terminalId)) {

            return terminalRunnableMap.get(terminalId);
        }

        synchronized (this) {
            if (terminalRunnableMap.containsKey(terminalId)) {

                return terminalRunnableMap.get(terminalId);
            }
            int count=getCount(lotteryType);

            List<MultiThreadInstanceTicketSenderRunnable> allRunableList=new ArrayList<MultiThreadInstanceTicketSenderRunnable>();
            for(int i=0;i<count;i++){
                MultiThreadInstanceTicketSenderRunnable runnable =new MultiThreadInstanceTicketSenderRunnable();
                runnable.setLotteryList(this.getLotteryList());
                runnable.setTicketSendWorker(ticketSendWorker);
                runnable.setTerminalSelector(this.getTerminalSelector());
                runnable.setTicketBatchService(this.getTicketBatchService());
                runnable.setInterval(this.getInstanceInterval());


                int num=i+1;
                ThreadContainer threadContainer = new ThreadContainer(runnable, "终端(" + terminalId+")送票子线程:"+count+"-"+num);
                threadContainer.setBeforeRunWaitTime(0);
                threadContainer.start();
                allRunableList.add(runnable);
            }
            terminalRunnableMap.put(terminalId, allRunableList);
            return allRunableList;
        }
    }




    /**
     * 销毁送票子线程
     * @throws Exception
     */
    protected void destroyInstance()  throws Exception {
        if (terminalRunnableMap != null) {
            Set<Long> terminalIdSet = terminalRunnableMap.keySet();
            for (Long terminalId : terminalIdSet) {
                List<MultiThreadInstanceTicketSenderRunnable> runnableList = terminalRunnableMap.get(terminalId);
                for (MultiThreadInstanceTicketSenderRunnable runnable:runnableList){
                    runnable.executeStop();
                }

            }
            terminalRunnableMap.clear();
        }
    }

    protected void dispatch(Long terminalId,List<TicketBatch> ticketBatchList,LotteryType lotteryType){
        List<MultiThreadInstanceTicketSenderRunnable> runnableList = this.initInstance(terminalId,lotteryType);
        for (MultiThreadInstanceTicketSenderRunnable runnable:runnableList){
            runnable.addQueue(ticketBatchList);
            runnable.executeNotify();
        }
    }

    protected void dispatch(List<TicketBatch> ticketBatchList,LotteryType lotteryType) {
        for (TicketBatch ticketBatch:ticketBatchList){
            Long terminalId=ticketBatch.getTerminalId();

            List<TicketBatch> subList=new ArrayList<TicketBatch>();
            subList.add(ticketBatch);
            this.dispatch(terminalId,subList,lotteryType);

        }
    }

    protected void beforeDispatch(List<TicketBatch> ticketBatchList) throws Exception {
        for (TicketBatch ticketBatch : ticketBatchList) {
            ticketBatch.setStatus(TicketBatchStatus.SEND_QUEUED.value);
            ticketBatchService.updateTicketBatchStatus(ticketBatch);
        }
    }

    @Override
    public void execute() {
        // 回收上次队列中未处理完的批次
        for (LotteryType lotteryType : this.getLotteryList()) {
            // 获取当前期待送票的批次
            List<TicketBatch> ticketBatchList = null;
            try {
                ticketBatchList = ticketBatchService.findForSendRecycle(lotteryType.value);
            } catch (Exception ex) {
                logger.error("查询({})要送票的批次出错!",lotteryType.getName(), ex);
            }
            if (ticketBatchList == null || ticketBatchList.isEmpty()) {

                continue;
            }

            ticketBatchList = this.filterTerminalSingletonDispatch(ticketBatchList);

            // 分派到子线程
            dispatch(ticketBatchList,lotteryType);
        }

        while (running) {
            for (LotteryType lotteryType : this.getLotteryList()) {


                if (this.isDuringGlobalSendForbidPeriod(lotteryType)) {
                    LotteryLog.getLotterWarnLog().info("此彩种处于全局停售期，不做送票处理, {}", lotteryType);
                    continue;
                }



                // 获取此彩种可以送票的终端ID列表
                List<Long> enableTerminalConfigList = this.getTerminalSelector().getEnabledTerminalIdList(lotteryType);


                for(Long terminalId:enableTerminalConfigList){
                    // 获取当前期待送票的批次
                    List<TicketBatch> ticketBatchList = null;
                    try {
                        ticketBatchList = this.getTicketBatchService().findForSend(lotteryType, terminalId, this.getMaxProcessBatchCount());
                    } catch (Exception ex) {
                        logger.error("查询({})要送票的批次出错!",lotteryType.getName(), ex);
                    }
                    if (ticketBatchList == null || ticketBatchList.isEmpty()) {
                        logger.info("彩种({})没有未送票的批次", lotteryType.getName());
                        continue;
                    }

                    try {
                        ticketBatchList = this.filterSendPaused(ticketBatchList);

                        ticketBatchList = this.filterTerminalSingletonDispatch(ticketBatchList);

                        // 处理成中间态
                        beforeDispatch(ticketBatchList);

                        // 分派到子线程
                        dispatch(terminalId,ticketBatchList,lotteryType);
                    } catch (Exception e) {
                        logger.error("彩种{}, 批次送票处理失败", lotteryType.getName());
                        logger.error(e.getMessage(),e);
                    }
                }

            }




            synchronized (this) {
                try {
                    wait(this.getInterval());
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        logger.info("线程退出，销毁送票子线程");
        try {
            destroyInstance();
        } catch (Exception e) {
            logger.error("送票子线程销毁失败", e);
        }
    }

    public long getInstanceInterval() {
        return instanceInterval;
    }

    public void setInstanceInterval(long instanceInterval) {
        this.instanceInterval = instanceInterval;
    }


    private int getCount(LotteryType lotteryType){
        int count=1;
        LotteryTicketConfig lotteryTicketConfig=lotteryTicketConfigService.get(lotteryType);
        if(lotteryTicketConfig==null){
            lotteryTicketConfig=lotteryTicketConfigService.get(LotteryType.getPhaseType(lotteryType));
        }

        if (lotteryTicketConfig!=null&&lotteryTicketConfig.getSendThreadCount()!=null){
            if (lotteryTicketConfig.getSendThreadCount().intValue()>0)
                count=lotteryTicketConfig.getSendThreadCount();
        }
        logger.error("线程数:lotterytype={},count={}",lotteryType,count);
        return count;
    }


    /**
     * 重新加载
     * */
    public void reload()throws Exception{
       logger.error("收到线程重载请求");
        this.destroyInstance();
        logger.error("重载完成");
    }

}
