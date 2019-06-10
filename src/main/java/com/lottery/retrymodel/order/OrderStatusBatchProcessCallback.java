package com.lottery.retrymodel.order;

import com.lottery.common.PageBean;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.contains.pay.PayStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.retrymodel.ApiRetryCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengqinyun on 15/5/8.
 */
public class OrderStatusBatchProcessCallback extends ApiRetryCallback<Object> {

    private LotteryOrderService lotteryOrderService;
    private Integer orderStatus;
    private String queueName;
    private Integer lotteryType;
    private QueueMessageSendService queueMessageSendService;

    private TicketService ticketService;
    private  int max=15;
    @Override
    protected Object execute() throws Exception {
        if(lotteryOrderService==null||orderStatus==null||queueName==null||queueMessageSendService==null||ticketService==null){
            throw new LotteryException("参数不全");
        }

        List<Integer> allList=new ArrayList<Integer>();
        if (lotteryType==LotteryType.ALL.value){
            allList=LotteryType.getAllLotteryType();
        }else {
            allList=LotteryType.getLotteryTypeList(lotteryType);
        }
        for (Integer lottype:allList){
            try {
                List<LotteryOrder> orderList=getLotteryOrderList(lottype,orderStatus);
                for (LotteryOrder lotteryOrder:orderList){
                    if (queueName.equals("撤单")){
                        if(lotteryOrder.getOrderStatus()==OrderStatus.PRINTED.value||lotteryOrder.getOrderStatus()==OrderStatus.HALF_PRINTED.value){
                            continue;
                        }
                        if(lotteryOrder.getOrderStatus()== OrderStatus.PRINT_WAITING.value){
                            lotteryOrderService.updateOrderStatus(OrderStatus.UNPAY_OBSOLETE.value,lotteryOrder.getId());
                        }else{
                            canle(lotteryOrder);
                        }

                    }else{
                        sendJms(lotteryOrder.getId());
                    }

                }
            }catch (Exception e){
                logger.error("彩种重置出错",e);
            }
        }

        return null;
    }

    protected void sendJms(String orderId){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("orderId",orderId);
        try {
            queueMessageSendService.sendMessage(queueName,map);
        } catch (Exception e) {
            logger.error("发送jms消息出错",e);
        }
    }

    protected  void canle(LotteryOrder lotteryOrder){
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderId", lotteryOrder.getId());
            List<Ticket> ticketList = ticketService.getByorderId(lotteryOrder.getId());
            if (ticketList==null||ticketList.size()==0){
                if (lotteryOrder.getPayStatus()== PayStatus.ALREADY_PAY.value){
                    queueMessageSendService.sendMessage(QueueName.betFailuerUnfreeze, map);
                }
                int orderStatus=lotteryOrder.getOrderStatus();
                if (orderStatus== OrderStatus.NOT_SPLIT.value){
                    queueMessageSendService.sendMessage(QueueName.betFailuerUnfreeze, map);
                }
                if (orderStatus== OrderStatus.PRINT_WAITING.value){
                    lotteryOrderService.updateOrderStatus(OrderStatus.UNPAY_OBSOLETE.value,lotteryOrder.getId());
                }
            }else{
                int total = 0;
                for (Ticket ticket : ticketList) {
                    if (ticket.getStatus() != TicketStatus.PRINT_SUCCESS.value) {
                        ticketService.updateTicketStatus(TicketStatus.CANCELLED.value, ticket.getId());
                        total++;
                    }
                }

                map.put("orderId", lotteryOrder.getId());
                queueMessageSendService.sendMessage(QueueName.betChercher, map);
                logger.error("订单{}共撤单:{}",lotteryOrder.getId(),total);
            }

        }catch (Exception e){
            logger.error("操作失败",e);
        }


    }

    protected List<LotteryOrder> getLotteryOrderList(int lotteryType,int orderStatus){

        List<LotteryOrder> orderServiceList=new ArrayList<LotteryOrder>();
        PageBean<LotteryOrder> pageBean = new PageBean<LotteryOrder>();
        pageBean.setMaxResult(max);
        int page = 1;
        try {
            while (true){
                pageBean.setPageIndex(page);
                List<LotteryOrder> orders=null;
                try{
                    orders=lotteryOrderService.getByOrderStatus(lotteryType,orderStatus,pageBean);
                }catch(Exception e){
                    logger.error("订单查询出错",e);
                    break;
                }
                if (orders != null && orders.size() > 0) {
                    orderServiceList.addAll(orders);
                    if (orders.size() < max) {
                        logger.info("读取到的订单列表不足一页，已读取结束");
                        break;
                    }
                } else {
                    logger.info("已读取完所有超时的{}的订单");
                    break;
                }
                // 准备读取下一页
                page ++;
            }
        }catch (Exception e){
            logger.error("查询出错",e);

        }

        return orderServiceList;

    }

    public LotteryOrderService getLotteryOrderService() {
        return lotteryOrderService;
    }

    public void setLotteryOrderService(LotteryOrderService lotteryOrderService) {
        this.lotteryOrderService = lotteryOrderService;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public QueueMessageSendService getQueueMessageSendService() {
        return queueMessageSendService;
    }

    public void setQueueMessageSendService(QueueMessageSendService queueMessageSendService) {
        this.queueMessageSendService = queueMessageSendService;
    }

    public Integer getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(Integer lotteryType) {
        this.lotteryType = lotteryType;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
}
