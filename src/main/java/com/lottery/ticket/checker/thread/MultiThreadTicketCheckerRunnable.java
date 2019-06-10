package com.lottery.ticket.checker.thread;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;

import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.common.thread.ThreadContainer;
import com.lottery.core.domain.terminal.TerminalConfig;

import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.core.service.TicketService;
import com.lottery.core.terminal.ITerminalSelector;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.checker.worker.IVenderTicketCheckWorker;
import com.lottery.ticket.processor.ITicketVenderProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;



/**
 * Created by fengqinyun on 16/10/26.
 * 终端多彩种同时检票
 */
public class MultiThreadTicketCheckerRunnable extends AbstractThreadRunnable {
    @Resource(name = "venderCheckerWorkerMap")
    protected Map<TerminalType, IVenderTicketCheckWorker> venderCheckerWorkerMap;
    @Resource(name = "ticketVenderProcessorMap")
    protected Map<TicketVenderStatus, ITicketVenderProcessor> ticketVenderProcessorMap;
    @Resource(name = "venderConverterBinder")
    protected Map<TerminalType, IVenderConverter> venderConverterBinder;
    private long interval = 10000l;
    @Autowired
    protected VenderConfigHandler venderConfigService;
    @Autowired
    protected ITerminalSelector terminalSelector;
    @Autowired
    private TicketService ticketService;
    protected TerminalType terminalType;
    protected int checkcount = 10;
    protected int timeOutSecondForCheck = 30;// 秒

    Map<Long,Map<Integer,SubThreadTicketCheckerRunnable>>  ticketCheckerRunnableMap=new ConcurrentHashMap<Long,Map<Integer,SubThreadTicketCheckerRunnable>>();



    protected SubThreadTicketCheckerRunnable instance(IVenderConfig venderConfig, TerminalConfig terminalConfig, TicketService ticketService, IVenderTicketCheckWorker ticketCheckWorker
            , IVenderConverter venderConverter, Map<TicketVenderStatus, ITicketVenderProcessor> ticketVenderProcessorMap){
        Long terminalId=venderConfig.getTerminalId();
        Integer lotteryType=terminalConfig.getLotteryType();
        if(ticketCheckerRunnableMap.containsKey(terminalId)){
            if(ticketCheckerRunnableMap.get(terminalId)!=null&&ticketCheckerRunnableMap.get(terminalId).containsKey(lotteryType)){
                return ticketCheckerRunnableMap.get(terminalId).get(lotteryType);
            }
        }

        synchronized (this){
            if(ticketCheckerRunnableMap.containsKey(terminalId)){
                if(ticketCheckerRunnableMap.get(terminalId)!=null&&ticketCheckerRunnableMap.get(terminalId).containsKey(lotteryType)){
                    return ticketCheckerRunnableMap.get(terminalId).get(lotteryType);
                }
            }
            Map<Integer,SubThreadTicketCheckerRunnable> lotteryCheckRunnableMap=new ConcurrentHashMap<Integer, SubThreadTicketCheckerRunnable>();
            SubThreadTicketCheckerRunnable subThreadTicketCheckerRunnable=new SubThreadTicketCheckerRunnable(venderConfig, terminalConfig, ticketService, ticketCheckWorker, venderConverter, ticketVenderProcessorMap);
            LotteryType nameLottery=LotteryType.get(lotteryType);
            String name=lotteryType+"";
            if(nameLottery!=null){
                name=nameLottery.name;
            }
            ThreadContainer threadContainer = new ThreadContainer(subThreadTicketCheckerRunnable, getTerminalType().getName()+"("+ terminalId.longValue()+")"+name+"检票");
            threadContainer.setBeforeRunWaitTime(100);
            threadContainer.start();

            if(ticketCheckerRunnableMap.containsKey(terminalId)&&ticketCheckerRunnableMap.get(terminalId)!=null){
                ticketCheckerRunnableMap.get(terminalId).put(lotteryType,subThreadTicketCheckerRunnable);
            }else{
                lotteryCheckRunnableMap.put(lotteryType,subThreadTicketCheckerRunnable);
                ticketCheckerRunnableMap.put(terminalId,lotteryCheckRunnableMap);
            }

            return subThreadTicketCheckerRunnable;
        }
    }

    @Override
    protected void executeRun() {
        while (running) {
            try {
                execute();
            } catch (Exception e) {
                logger.error("检票出现错", e);
            }
            synchronized (this) {
                try {
                    wait(this.getInterval());
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }

        }

        try {
            destroyInstance();
        } catch (Exception e) {
            logger.error("检票子线程销毁失败", e);
        }
    }
    private void destroyInstance(){
        try{
            if (ticketCheckerRunnableMap.size()==0){
                return;
            }
            for (Map.Entry<Long,Map<Integer,SubThreadTicketCheckerRunnable>> entry : ticketCheckerRunnableMap.entrySet()) {
                Long terminalId=entry.getKey();
                Map<Integer,SubThreadTicketCheckerRunnable> subThreadTicketCheckerRunnableMap=entry.getValue();
                for (Map.Entry<Integer,SubThreadTicketCheckerRunnable> entrysub : subThreadTicketCheckerRunnableMap.entrySet()) {
                    logger.error("整个终端停止检票,terminalId={},彩种={}的检票线程停止",terminalId,entrysub.getKey());
                    entrysub.getValue().executeStop();
                }
            }
            ticketCheckerRunnableMap.clear();
        }catch (Exception e){
            logger.error("销毁整个线程出错",e);
        }


    }


    protected void execute() {
        if (getTerminalType() == null) {
            logger.error("终端不能为空");
            return;
        }

        List<IVenderConfig> configList = venderConfigService.getAllByTerminalType(getTerminalType());
        if(configList==null||configList.isEmpty()){
            return;
        }


        for(IVenderConfig venderConfig:configList){
            Long terminalId=venderConfig.getTerminalId();
            if (!terminalSelector.isCheckEnabled(terminalId)) {
                logger.error("终端[{}]已禁止检票",terminalId);
                this.closeTerminalCheck(terminalId);
                continue;
            }



            for(TerminalConfig terminalConfig:getListTerminalConfig(terminalId)){

                if(!isEnableForCheck(LotteryType.get(terminalConfig.getLotteryType()),terminalId)){
                    this.closeTerminalCheck(terminalId,terminalConfig.getLotteryType());
                    continue;
                }

                IVenderTicketCheckWorker venderTicketCheckWorker=venderCheckerWorkerMap.get(getTerminalType());
                IVenderConverter venderConverter=venderConverterBinder.get(getTerminalType());
                if(venderTicketCheckWorker==null||venderConverter==null){
                    logger.error("终端{}的检票配置={},转换配置={},两者都不为空",getTerminalType(),venderTicketCheckWorker,venderConverter);
                    continue;
                }

                if(venderConfig.getSyncTicketCheck()==Boolean.TRUE){
                    this.destroyInstance();
                    this.syncTicketCheck(terminalConfig.getLotteryType(),terminalId,venderConfig,venderTicketCheckWorker,venderConverter);
                }else {
                    SubThreadTicketCheckerRunnable subThreadTicketCheckerRunnable=this.instance(venderConfig,terminalConfig,ticketService,venderTicketCheckWorker,venderConverter,ticketVenderProcessorMap);

                    if(subThreadTicketCheckerRunnable!=null){
                        if(terminalConfig.getCheckWait()!=null&&terminalConfig.getCheckWait().longValue()>0)
                            subThreadTicketCheckerRunnable.setInterval(terminalConfig.getCheckWait().longValue());
                        subThreadTicketCheckerRunnable.executeNotify();
                    }
                }

            }
        }


    }



    private void syncTicketCheck(int lotteryType,Long terminalId,IVenderConfig venderConfig,IVenderTicketCheckWorker venderTicketCheckWorker,IVenderConverter venderConverter){
        List<Ticket> ticketList = ticketService.getUnCheckTicket(lotteryType,terminalId, getCheckcount(venderConfig), getTimeOutForCheck(venderConfig));

        if (ticketList==null||ticketList.size()==0){
            return;
        }
        int len = 0;
        int ticketCount=ticketList.size();
        if (ticketCount % checkcount == 0) {
            len =ticketCount/checkcount;
        } else {
            len =ticketCount/checkcount + 1;
        }
        for (int i=0;i<len;i++){

            List<Ticket> ticketBatchList=null;
            if (((i * checkcount) + checkcount) <ticketCount) {
                ticketBatchList = ticketList.subList(i * checkcount, i* checkcount + checkcount);
            } else {
                ticketBatchList = ticketList.subList(i * checkcount,ticketCount);
            }
           this.syncPreTicketCheck(ticketBatchList,venderConfig,venderTicketCheckWorker,venderConverter);

        }
    }


    private  void syncPreTicketCheck(List<Ticket> ticketList,IVenderConfig venderConfig,IVenderTicketCheckWorker venderTicketCheckWorker,IVenderConverter venderConverter){
        try{
            if (ticketList == null || ticketList.isEmpty()) {
                return;
            }
            Map<String, Ticket> mapTicket = new HashMap<String, Ticket>();
            for (Ticket ticket : ticketList) {
                mapTicket.put(ticket.getId(), ticket);
            }


            List<TicketVender>	ticketVenderList= venderTicketCheckWorker.process(ticketList,venderConfig,venderConverter);

            if (ticketVenderList == null || ticketVenderList.isEmpty()) {
                logger.error("查询返回结果为空,请检查");
                return;
            }
            for (TicketVender ticketVender : ticketVenderList) {
                TicketVenderStatus ticketVenderStatus = ticketVender.getStatus();
                ITicketVenderProcessor process = ticketVenderProcessorMap.get(ticketVenderStatus);
                if (process != null) {
                    Ticket ticket = mapTicket.get(ticketVender.getId());
                    process.process(ticket, ticketVender);
                } else {
                    logger.error("{}状态下的票处理器不存在", ticketVenderStatus);
                }
            }
        }catch (Exception e){
            logger.error("查票失败",e);
        }
    }


    private void closeTerminalCheck(Long terminalId){

        if(ticketCheckerRunnableMap.containsKey(terminalId)){
            Map<Integer,SubThreadTicketCheckerRunnable> lotteryMap=ticketCheckerRunnableMap.get(terminalId);
            if(lotteryMap!=null){
                for (Map.Entry<Integer,SubThreadTicketCheckerRunnable> entry : lotteryMap.entrySet()) {
                    logger.error("整个终端停止检票,terminalId({}),彩种({})的检票线程停止",terminalId,entry.getKey());
                    entry.getValue().executeStop();
                }
            }
            ticketCheckerRunnableMap.clear();
        }
    }


    private void closeTerminalCheck(Long terminalId,Integer lotteryType){
        if(ticketCheckerRunnableMap.containsKey(terminalId)){
            Map<Integer,SubThreadTicketCheckerRunnable> lotteryMap=ticketCheckerRunnableMap.get(terminalId);
            if(lotteryMap!=null){
                SubThreadTicketCheckerRunnable subThreadTicketCheckerRunnable=lotteryMap.get(lotteryType);
                if (subThreadTicketCheckerRunnable!=null){
                    logger.error("终端({}),彩种({})的检票线程停止",terminalId,lotteryType);
                    subThreadTicketCheckerRunnable.executeStop();
                    lotteryMap.remove(lotteryType);
                }
            }

        }
    }




    private List<TerminalConfig> getListTerminalConfig(Long terminalId){
        List<TerminalConfig> allList=new ArrayList<TerminalConfig>();
        List<TerminalConfig> allTerminalConfigList = terminalSelector.getTerminalConfigList(terminalId);
        if (allTerminalConfigList==null||allTerminalConfigList.size()==0){
            return allList;
        }

        return allTerminalConfigList;
    }


    protected  boolean isEnableForCheck(LotteryType lotteryType, Long terminalId){

        if (terminalSelector.isGlobalCheckDisabledOrDuringCheckForbidPeriod(lotteryType)) {
            logger.warn("彩种{}正处于全局禁止检票周期", lotteryType);
            return  false;
        }

        //如果终端不存在、禁用异步检票的某彩种终端不执行本次查票
        if (!terminalSelector.isCheckEnabledForLotteryType(terminalId, lotteryType)) {
            logger.warn("彩种{}当前终端设置为禁止检票, terminalId={}", lotteryType, terminalId);
            return false;
        }

        if (terminalSelector.isDuringCheckForbidPeriod(terminalId, lotteryType)) {
            logger.warn("彩种{}当前终端正处于禁止检票周期, terminalId={}", lotteryType, terminalId);
            return  false;
        }
        return true;

    }

    private int getCheckcount(IVenderConfig venderConfig) {
        if (venderConfig != null && venderConfig.getCheckCount() != null)
            return venderConfig.getCheckCount();
        return checkcount;
    }

    public Date getTimeOutForCheck(IVenderConfig venderConfig) {
        int timeout=timeOutSecondForCheck;
        if (venderConfig != null && venderConfig.getTimeOutSecondForCheck() != null)
            timeout= venderConfig.getTimeOutSecondForCheck();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MILLISECOND, -1 * timeout * 1000);
        return cal.getTime();
    }




    public TerminalType getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(TerminalType terminalType) {
        this.terminalType = terminalType;
    }


    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void setTimeOutSecondForCheck(int timeOutSecondForCheck) {
        this.timeOutSecondForCheck = timeOutSecondForCheck;
    }

    public void setCheckcount(int checkcount) {
        this.checkcount = checkcount;
    }
}
