package com.lottery.retrymodel.prize.error;

import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.TicketFailureType;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.PrizeService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.account.UserAccountService;
import com.lottery.lottype.PrizeResult;
import com.lottery.retrymodel.ApiRetryCallback;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fengqinyun on 2017/4/15.
 * 出票失败赔付
 */
public class OrderFailureCompensateCallback extends ApiRetryCallback<Object> {



    private String[] orders;
    private Integer lotteryType;
    private String phase;

    private LotteryOrderService lotteryOrderService;
    private PrizeService prizeService;
    private TicketService ticketService;
    private LotteryPhaseService lotteryPhaseService;
    private UserAccountService userAccountService;
    @Override
    protected Object execute() throws Exception {
        if (lotteryOrderService==null||prizeService==null||ticketService==null||userAccountService==null){
            throw new Exception("参数不全");
        }


        if(orders!=null&&orders.length>0){
            for(String orderId:orders){
                try {
                    LotteryOrder lotteryOrder=lotteryOrderService.get(orderId);
                    if(lotteryOrder!=null){
                        process(lotteryOrder);
                    }
                }catch (Exception e){
                    logger.error("orderId={}",orderId);
                    logger.error(e.getMessage(),e);
                }
            }
            return null;
        }

        if(lotteryType==null||phase==null){
            throw new Exception("不能为空:lotteryType="+lotteryType+",phase="+phase);
        }


        try {
            int max=15;
            while (true){
                List<LotteryOrder> lotteryOrderList=lotteryOrderService.getByTypePhaseAndStatus(lotteryType,phase, OrderResultStatus.unable_to_draw.value,max);
                if(lotteryOrderList==null||lotteryOrderList.size()==0){
                    break;
                }
                processList(lotteryOrderList);
                if(lotteryOrderList.size()<max){
                    break;
                }

            }

        }catch (Exception e){
            logger.error("中奖赔付出错",e);
        }
        return null;
    }


    private void processList(List<LotteryOrder> orderList)throws Exception{
       for (LotteryOrder lotteryOrder:orderList){
           process(lotteryOrder);
       }

    }



    private void process(LotteryOrder lotteryOrder) throws Exception{
        LotteryPhase lotteryPhase=lotteryPhaseService.getPhase(lotteryOrder.getLotteryType(),lotteryOrder.getPhase());
        String wincode=lotteryPhase.getWincode();
        lotteryOrder.setWincode(wincode);
        List<Ticket> ticketList=ticketService.getByorderId(lotteryOrder.getId());
        if(ticketList==null||ticketList.size()==0){
            logger.error("订单:{},没有票",lotteryOrder.getId());
            return;
        }
        BigDecimal winPreAmount=BigDecimal.ZERO;
        BigDecimal winAfAmount=BigDecimal.ZERO;
        boolean isBig=false;
        int limitCount=0;
        for (Ticket ticket:ticketList){
            if(ticket.getFailureType()!=null&&ticket.getFailureType()== TicketFailureType.PRINT_LIMITED.value){
                logger.error("ticket={},出票限号，无法开奖",ticket.getId());
                limitCount++;
                continue;
            }
            PrizeResult prizeResult=prizeService.caculateTicket(wincode,ticket);
            if(prizeResult!=null){
                winPreAmount.add(prizeResult.getAfterTaxAmt());
                winAfAmount.add(prizeResult.getPreTaxAmt());
               if(prizeResult.isBig()==true){
                   if(!isBig){
                       isBig=true;
                   }
               }

            }
        }
        if(limitCount==ticketList.size()){
            logger.error("orderId={}的票全是限号",lotteryOrder.getId());
            return;
        }

        lotteryOrder.setPretaxPrize(winPreAmount);
        lotteryOrder.setSmallPosttaxPrize(winAfAmount);
        if(winAfAmount.intValue()==0){
            lotteryOrder.setOrderResultStatus(OrderResultStatus.not_win.value);
        }else {
            if(isBig){
                lotteryOrder.setOrderResultStatus(OrderResultStatus.win_big.value);
            }else {
                lotteryOrder.setOrderResultStatus(OrderResultStatus.win_already.value);
            }
        }

        lotteryOrderService.update(lotteryOrder);

        BigDecimal compensationAmount=lotteryOrder.getSmallPosttaxPrize().subtract(lotteryOrder.getAmount());
        if(compensationAmount.intValue()>0){//赔付金额大于0
            userAccountService.betCompensation(lotteryOrder.getUserno(),lotteryOrder.getId(),compensationAmount,lotteryOrder.getAccountType());
        }
        lotteryOrderService.update(lotteryOrder);


    }


    public String[] getOrders() {
        return orders;
    }

    public void setOrders(String[] orders) {
        this.orders = orders;
    }

    public Integer getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(Integer lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public LotteryOrderService getLotteryOrderService() {
        return lotteryOrderService;
    }

    public void setLotteryOrderService(LotteryOrderService lotteryOrderService) {
        this.lotteryOrderService = lotteryOrderService;
    }

    public PrizeService getPrizeService() {
        return prizeService;
    }

    public void setPrizeService(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public LotteryPhaseService getLotteryPhaseService() {
        return lotteryPhaseService;
    }

    public void setLotteryPhaseService(LotteryPhaseService lotteryPhaseService) {
        this.lotteryPhaseService = lotteryPhaseService;
    }

    public UserAccountService getUserAccountService() {
        return userAccountService;
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }
}
