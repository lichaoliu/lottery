package com.lottery.retrymodel.prize;

import com.lottery.common.PageBean;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.lottery.HighFrequencyLottery;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.draw.ILotteryDrawTask;
import com.lottery.retrymodel.ApiRetryCallback;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengqinyun on 15/7/24.
 * 订单开奖是否完成
 */
public class OrderDrawCheckCallback extends ApiRetryCallback<Object> {

    private static List<Integer> orderStatusList;
    private static List<Integer> orderResultStatusList;

    private long interval = 45000; // 每次处理间隔(毫秒)

    static {
        orderStatusList = new ArrayList<Integer>();
        orderStatusList.add(OrderStatus.PRINTED.value);
        orderStatusList.add(OrderStatus.HALF_PRINTED.value);
        orderResultStatusList = new ArrayList<Integer>();
        orderResultStatusList.add(OrderResultStatus.not_open.value);
        orderResultStatusList.add(OrderResultStatus.prizing.value);
    }

    private ILotteryDrawTask lotteryDrawTask;
    private LotteryOrderService lotteryOrderService;
    private QueueMessageSendService queueMessageSendService;
    private int max=150;
    @Override
    protected Boolean execute() throws Exception {
        if (lotteryDrawTask==null||lotteryOrderService==null||queueMessageSendService==null){
            throw new Exception("参数不全");
        }
        int lotteryType = lotteryDrawTask.getLotteryType();
        String wincode=lotteryDrawTask.getWinCode();
        String phaseNo = lotteryDrawTask.getPhase();

        if(lotteryType==0|| StringUtils.isEmpty(phaseNo)){
            throw new LotteryException("参数不全,lotteryType="+lotteryType+",phase="+phaseNo);
        }

        if(!(LotteryType.getJcValue().contains(lotteryType)||LotteryType.getDcValue().contains(lotteryType))){
            if(StringUtils.isEmpty(wincode)){
                throw new LotteryException("彩种:"+lotteryType+",开奖号码不能为空");
            }
        }


       if (!HighFrequencyLottery.contains(LotteryType.getLotteryType(lotteryType))){
           interval=120000;
       }


        PageBean<LotteryOrder> pageBean = new PageBean<LotteryOrder>();
        pageBean.setMaxResult(max);
        int page = 1;
        pageBean.setPageIndex(page);
        pageBean.setTotalFlag(false);
        pageBean.setPageFlag(false);
        int count=0;
        while (true){
            try {
                List<LotteryOrder>  recycleOrderList = lotteryOrderService.getByStatusAndType(lotteryType, phaseNo, orderStatusList, orderResultStatusList, null, lotteryDrawTask.getLastMatchNum(), pageBean);
                if (recycleOrderList!=null&&recycleOrderList.size()>0){
                   count +=recycleOrderList.size();
                   this.process(recycleOrderList,wincode);
                    if (recycleOrderList.size()<max){
                        break;
                    }
                }else {
                    break;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                break;
            }
            synchronized (this) {
                try {
                    this.wait(interval);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }
        logger.error("彩种:{},期号:{}的未开奖,开奖中的订单数量是:{}",lotteryType,phaseNo,count);
        return true;
    }

    protected  void process(List<LotteryOrder> orderList,String wincode){
        for(LotteryOrder lotteryOrder:orderList){
            logger.error("订单:{},需要重新开奖",lotteryOrder.getId());
            if(lotteryOrder.getOrderResultStatus()==OrderResultStatus.not_open.value){
                lotteryOrderService.updateOrderResultStatus(lotteryOrder.getId(),OrderResultStatus.prizing.value,wincode);
            }
            this.sendJms(lotteryOrder.getId(),lotteryOrder.getLotteryType(),wincode);
        }
    }


    protected void sendJms(String orderId, int lotteryType,String windcode) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderId", orderId);
            map.put("wincode", windcode);
            QueueName queueName=QueueName.prizeExecute;
            if(HighFrequencyLottery.contains(lotteryType)){
                queueName=QueueName.prizeorderGaopin;
            }
            this.queueMessageSendService.sendMessage(queueName, map);
        } catch (Exception e) {
            logger.error("发送算奖订单出错", e);
        }
    }

    public ILotteryDrawTask getLotteryDrawTask() {
        return lotteryDrawTask;
    }

    public void setLotteryDrawTask(ILotteryDrawTask lotteryDrawTask) {
        this.lotteryDrawTask = lotteryDrawTask;
    }

    public LotteryOrderService getLotteryOrderService() {
        return lotteryOrderService;
    }

    public void setLotteryOrderService(LotteryOrderService lotteryOrderService) {
        this.lotteryOrderService = lotteryOrderService;
    }

    public QueueMessageSendService getQueueMessageSendService() {
        return queueMessageSendService;
    }

    public void setQueueMessageSendService(QueueMessageSendService queueMessageSendService) {
        this.queueMessageSendService = queueMessageSendService;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
