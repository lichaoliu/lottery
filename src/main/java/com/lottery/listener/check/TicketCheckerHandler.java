package com.lottery.listener.check;

import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.checker.worker.IVenderTicketCheckWorker;
import com.lottery.ticket.processor.ITicketVenderProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by fengqinyun on 15/6/16.
 * 重新检票
 */
@Service
public class TicketCheckerHandler {
    protected final Logger logger= LoggerFactory.getLogger(getClass().getName());
    @Autowired
    protected TicketService ticketService;
    @Autowired
    private QueueMessageSendService queueMessageSendService;
    @Autowired
    protected VenderConfigHandler venderConfigService;
    @Resource(name = "ticketVenderProcessorMap")
    protected Map<TicketVenderStatus, ITicketVenderProcessor> ticketVenderProcessorMap;

    @Resource(name = "venderCheckerWorkerMap")
    protected Map<TerminalType, IVenderTicketCheckWorker> venderCheckerWorkerMap;

    @Resource(name = "venderConverterBinder")
    protected Map<TerminalType, IVenderConverter> venderConverterBinder;
    public  void process(String ticketId) {
        try {
            logger.error("票:{},手工检票开始",ticketId);
            Ticket ticket = ticketService.getTicket(ticketId);
            if (ticket == null) {
                return;
            }
            if (ticket.getStatus() == TicketStatus.PRINT_SUCCESS.value) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("orderId", ticket.getOrderId());
                queueMessageSendService.sendMessage(QueueName.betChercher, map);
            }
            if (ticket.getStatus() == TicketStatus.PRINTING.value) {
                Long terminalId = ticket.getTerminalId();
                Integer terminalTypeid = ticket.getTerminalType();
                if (terminalId == null || terminalTypeid == null) {
                    logger.error("termainId:{},terminaltypeid:{},都不能为空", terminalId, terminalTypeid);
                    return;
                }
                TerminalType terminalType = TerminalType.get(terminalTypeid);
                IVenderConfig venderConfig = venderConfigService.getByTerminalId(terminalId);
                IVenderTicketCheckWorker ticketCheckWorker = venderCheckerWorkerMap.get(terminalType);
                IVenderConverter venderConverter = venderConverterBinder.get(terminalType);
                if (ticketCheckWorker == null || venderConverter == null) {
                    logger.error("终端{}的检票配置={},转换配置={},两者都不为空", terminalType, ticketCheckWorker, venderConfig);
                    return;
                }
                List<Ticket> ticketList = new ArrayList<Ticket>();
                ticketList.add(ticket);

                List<TicketVender> ticketVenderList = ticketCheckWorker.process(ticketList, venderConfig, venderConverter);
                if (ticketVenderList == null || ticketVenderList.isEmpty()) {
                    logger.error("查询返回结果为空,请检查");
                    return;
                }

                //就查一张票
                TicketVender ticketVender = ticketVenderList.get(0);

                this.ticketProcess(ticket,ticketVender);
            }
        }catch (Exception e){
            logger.error("票:{}手工检票失败",ticketId);
        }



    }
    public void ticketProcess(Ticket ticket,TicketVender ticketVender){
        if (ticket.getStatus()!=TicketStatus.PRINTING.value){//不是出票中的立刻返回
            logger.error("票:{},状态为:{},不是出票中,isnotice={}",ticket.getId(),ticket.getStatus(),ticketVender.isTicketNotify());
            return;
        }


        TicketVenderStatus ticketVenderStatus = ticketVender.getStatus();
        ITicketVenderProcessor process = ticketVenderProcessorMap.get(ticketVenderStatus);
        if (process != null) {
            process.process(ticket, ticketVender);
        } else {
            logger.error("{}状态下的票处理器不存在", ticketVenderStatus);
        }
    }

    public void noticeProcess(TicketVender ticketVender){
        try{
            //logger.error("通知:{}处理开始",ticketVender.getId());
            Ticket ticket=ticketService.getTicket(ticketVender.getId());
            if (ticket!=null){
                ticketProcess(ticket,ticketVender);
            }else {
                logger.error("票:{}不存在",ticketVender.getId());
            }

        }catch (Exception e){
            logger.error("通知处理出错",e);
        }

    }


}
