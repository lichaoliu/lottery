package com.lottery.ticket.checker.thread;

import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.domain.terminal.TerminalConfig;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.service.TicketService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.checker.worker.IVenderTicketCheckWorker;
import com.lottery.ticket.processor.ITicketVenderProcessor;

import java.util.*;

/**
 * Created by fengqinyun on 16/10/26.
 */
public class SubThreadTicketCheckerRunnable extends AbstractThreadRunnable {

    protected TicketService ticketService;
    private int checkcount = 10;

    private int timeOutSecondForCheck = 30;// 秒

    protected IVenderConfig venderConfig;

    protected TerminalConfig terminalConfig;
    protected IVenderTicketCheckWorker ticketCheckWorker;

    protected IVenderConverter venderConverter;
    protected Map<TicketVenderStatus, ITicketVenderProcessor> ticketVenderProcessorMap;
    private long interval = 10000;
    public SubThreadTicketCheckerRunnable(IVenderConfig venderConfig,TerminalConfig terminalConfig,TicketService ticketService,IVenderTicketCheckWorker ticketCheckWorker
            ,IVenderConverter venderConverter,Map<TicketVenderStatus, ITicketVenderProcessor> ticketVenderProcessorMap){
        this.venderConfig=venderConfig;
        this.terminalConfig=terminalConfig;
        this.ticketService=ticketService;
        this.ticketCheckWorker=ticketCheckWorker;
        this.venderConverter=venderConverter;
        this.ticketVenderProcessorMap=ticketVenderProcessorMap;
    }
    @Override
    protected void executeRun() {

        while (running) {
            try {
               if(venderConfig==null||terminalConfig==null||ticketService==null){
                   logger.error("必须条件不能为空,venderConfig={},terminalConfig={},ticketService={},ticketCheckWorker={},venderConverter={},ticketVenderProcessorMap={}",venderConfig,terminalConfig,
                           ticketService,ticketCheckWorker,venderConverter,ticketVenderProcessorMap);
               }else {
                   this.executeCheck();
               }

            } catch (Exception e) {
                logger.error("检票子线程出现错", e);
            }
            synchronized (this) {
                try {
                    wait(this.getInterval());
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(),e);
                }
            }

        }
        logger.error("线程终止");
    }


    private void executeCheck(){
        int lotteryType=terminalConfig.getLotteryType();
        Long terminalId=venderConfig.getTerminalId();
        List<Ticket> ticketList = ticketService.getUnCheckTicket(lotteryType,terminalId, getCheckcount(venderConfig), getTimeOutForCheck(venderConfig));

        if (ticketList==null||ticketList.size()==0){
            return ;
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
            this.executePreCheck(ticketBatchList,venderConfig);

        }


    }


    private void executePreCheck(List<Ticket> ticketList,IVenderConfig venderConfig) {
        try{
            if (ticketList == null || ticketList.isEmpty()) {
                return;
            }
            Map<String, Ticket> mapTicket = new HashMap<String, Ticket>();
            for (Ticket ticket : ticketList) {
                mapTicket.put(ticket.getId(), ticket);
            }

            List<TicketVender>	ticketVenderList = ticketCheckWorker.process(ticketList,venderConfig,venderConverter);

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


    public int getCheckcount(IVenderConfig venderConfig) {
        if (venderConfig != null && venderConfig.getCheckCount() != null)
            return venderConfig.getCheckCount();
        return checkcount;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public Date getTimeOutForCheck(IVenderConfig venderConfig) {
        int timeout=timeOutSecondForCheck;
        if (venderConfig != null && venderConfig.getTimeOutSecondForCheck() != null)
            timeout= venderConfig.getTimeOutSecondForCheck();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MILLISECOND, -1 * timeout * 1000);
        return cal.getTime();
    }


    @Override
    public String toString() {
        return "SubThreadTicketCheckerRunnable{" +
                "TerminalId=" + terminalConfig.getTerminalId() +",lotteryType="+terminalConfig.getLotteryType()+
                '}';
    }
}
