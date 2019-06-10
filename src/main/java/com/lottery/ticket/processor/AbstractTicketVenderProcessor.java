package com.lottery.ticket.processor;

import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.lottery.HighFrequencyLottery;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.handler.LotteryPhaseHandler;
import com.lottery.core.handler.OrderProcessHandler;
import com.lottery.core.handler.SystemExceptionMessageHandler;
import com.lottery.core.service.TicketBatchService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.TicketServiceCache;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.allotter.ITicketAllotWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTicketVenderProcessor implements ITicketVenderProcessor {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    protected TicketService ticketService;
    @Autowired
    protected TicketServiceCache ticketServiceCache;
    @Autowired
    protected TicketBatchService ticketBatchService;
	@Resource(name="executorBinder")
	protected Map<LotteryType, ITicketAllotWorker> executorBinder;
   
    @Resource(name="venderConverterBinder")
    private Map<TerminalType, IVenderConverter> venderConverterBinder;
    
    @Resource(name="ticketVenderProcessorMap")
    protected Map<TicketVenderStatus,ITicketVenderProcessor> ticketVenderProcessorMap;
    @Autowired
    protected LotteryPhaseHandler phaseHandler;

    @Autowired
    protected SystemExceptionMessageHandler systemExceptionMessageHandler;
  
    @Autowired
    protected QueueMessageSendService queueMessageSendService;
    @Autowired
    private OrderProcessHandler orderProcessHandler;
	protected long getTicketTimeout(Integer lotteryType) {
		
		return phaseHandler.getEndTicketTimeout(lotteryType);
	}
    protected IVenderConverter getVenderConverter(TerminalType terminalType) {
        return venderConverterBinder.get(terminalType);
    }

    protected ITicketAllotWorker getAllotExecutor(LotteryType lotteryType) {
		ITicketAllotWorker worker=this.executorBinder.get(lotteryType);
		if(worker==null){
			worker=this.executorBinder.get(LotteryType.ALL);
		}
		return worker;
	}

    public void process(Ticket ticket, TicketVender ticketVender){//往后好扩展
        Ticket newTicket=ticketService.getTicket(ticket.getId());
        if(newTicket.getStatus()!=ticket.getStatus()){
            logger.error("票:{},检票状态和新状态不同,检票状态:{},新状态:{},不做处理,isnotice={}",ticket.getId(),ticket.getStatus(),newTicket.getStatus(),ticketVender.isTicketNotify());
            return;
        }
        if (ticket.getTerminalId().intValue()!=ticketVender.getTerminalId().intValue()){
            logger.error("票:{}的终端id不同,只做日志记录,票中terminalId:{},通知的termianlId:{}",ticket.getId(),ticket.getTerminalId(),ticketVender.getTerminalId());
            return;
        }

     this.execute(newTicket, ticketVender);

    }


    private  boolean statusJudge(Ticket ticket, TicketVender ticketVender){
        Ticket newticket=ticketService.getTicket(ticket.getId());
        if(!ticketVender.isTerminalIdJudge()){//不需要判断终端号

        }else{
            if (newticket.getTerminalId()!=ticketVender.getTerminalId()){
                logger.error("票:{},终端号不同,票中终端id={},检票Id={}",ticket.getId(),newticket.getTerminalId(),ticketVender.getTerminalId());
                return false;
            }
        }
        return true;
    }



    protected abstract  void execute(Ticket ticket, TicketVender ticketVender);


    protected void sendJms(Ticket ticket){
    	
    	try {
    		String queuename=QueueName.betChercher.value;
    		LotteryType lotteryType=LotteryType.get(ticket.getLotteryType());
    		if(HighFrequencyLottery.contains(lotteryType)){
    			queuename=QueueName.gaopinChercher.value;
    		}
    		Map<String,Object> map=new HashMap<String,Object>();
    		map.put("orderId", ticket.getOrderId());
			this.queueMessageSendService.sendMessage(queuename, map);
		} catch (Exception e) {
            orderProcessHandler.check(ticket.getOrderId());
            String errormessage=String.format("发送jms检票消息失败,errormessage=%s",e.getMessage());
            systemExceptionMessageHandler.saveMessage(errormessage);
		   logger.error("发送检票消息出错",e);
		}
    }
    @PostConstruct
    abstract protected void init();

  
}
