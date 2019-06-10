package com.lottery.retrymodel.ticket;

import com.lottery.common.PageBean;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.retrymodel.ApiRetryCallback;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengqinyun on 16/8/18.
 */
public class TicketCheckCallback extends ApiRetryCallback<Object> {

    private Date createTime;

    private Date endTime;
    private Long terminalId;

    private TicketService ticketService;

    private QueueMessageSendService queueMessageSendService;
    private int max=15;
    @Override
    protected Object execute() throws Exception {

        if(createTime==null||endTime==null||terminalId==null||ticketService==null||queueMessageSendService==null){
            throw new Exception("参数不全,crateTime="+createTime+",endtime="+endTime+",terminalId="+terminalId+",ticketService="+ticketService+",queueMessageSendService="+queueMessageSendService);
        }
        PageBean<Ticket> pageBean = new PageBean<Ticket>();
        pageBean.setMaxResult(max);
        pageBean.setTotalFlag(false);
        int page = 1;

        try {
            while (true){
                pageBean.setPageIndex(page);
                List<Ticket> ticketList=null;
                try{
                    ticketList=ticketService.getByCreateTimeAndEndTimeAndStatus(createTime,endTime,terminalId, TicketStatus.PRINTING.value,pageBean);
                }catch(Exception e){
                    logger.error("查询出错",e);
                    break;
                }
                if (ticketList != null && ticketList.size() > 0) {

                        sendJms(ticketList);
                    if (ticketList.size() < max) {
                        logger.info("读取到的订单列表不足一页，已读取结束");
                        break;
                    }
                } else {
                    logger.info("已全部读取完毕,退出");
                    break;
                }
                // 准备读取下一页
                page ++;
            }
        }
        catch (Exception e){
            logger.error("一键检票出错",e);
        }






        return null;
    }

    private void sendJms(List<Ticket> ticketList) throws Exception{
        for(Ticket ticket:ticketList){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ticketId", ticket.getId());
            queueMessageSendService.sendMessage(QueueName.ticketCheck, map);
        }

    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public QueueMessageSendService getQueueMessageSendService() {
        return queueMessageSendService;
    }

    public void setQueueMessageSendService(QueueMessageSendService queueMessageSendService) {
        this.queueMessageSendService = queueMessageSendService;
    }
}
