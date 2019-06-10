package com.lottery.ticket.sender.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.core.cache.RedisCache;
import com.lottery.core.cache.TerminalFailureCache;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.checker.worker.IVenderTicketCheckWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.HighFrequencyLottery;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.ticket.TicketBatchSendResultProcessType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;

import javax.annotation.Resource;

@Service
public class CommonTimeoutTicketSendVenderProcessor extends AbstractTicketSendVenderProcessor{
	  /**
     * 截止期最后一小时以内的超时才重分
     */
    private long reallotDeadlineForward = 1800000;//大盘半个小时
    private long highFrequencyUrgentProcessTime=180000;//高频3分钟

	@Resource(name = "venderCheckerWorkerMap")
	private Map<TerminalType, IVenderTicketCheckWorker> venderCheckerWorkerMap;
	@Resource(name = "venderConverterBinder")
	private Map<TerminalType, IVenderConverter> venderConverterBinder;
	@Autowired
	private RedisCache redisCache;
    @Autowired
	private VenderConfigHandler venderConfigHandler;
	@Override
	public TicketBatchSendResultProcessType process(TicketSendResult ticketSendResult, Ticket ticket, TicketBatch ticketBatch) {
		Date sendtime=ticketSendResult.getSendTime();
		String dateStr=null;
		if(sendtime!=null){
			dateStr=CoreDateUtils.formatDate(sendtime, DATE_STR);
		}
		String timemsg = String.format("票(%s)在终端(%s),id=(%s)送票超时,发送的时间是:[%s]内容是:%s",
                ticket.getId(), ticketSendResult.getTerminalType().name,ticketBatch.getTerminalId(),dateStr,ticketSendResult.getSendMessage());
		systemExceptionMessageHandler.saveMessage(timemsg);
		logger.error(timemsg);


		//送票超时处理,送3次超时,然后进行检票,如果没有,进行重分处理
		try{
			String key=String.format("timeout_ticket_send_%s_%s",ticket.getId(),ticket.getTerminalId());
			TerminalFailureCache failureCache=redisCache.get(key,TerminalFailureCache.class);
			if(failureCache==null){
				failureCache=new TerminalFailureCache();
				failureCache.setTicketId(ticket.getId());
				failureCache.setTerminalId(ticketBatch.getTerminalId());
				failureCache.setFailureCount(0);
				redisCache.save(key,180,failureCache);
				return  TicketBatchSendResultProcessType.retry;
			}else {
				int count= (int)failureCache.getFailureCount();
				if(count<3){
					String error=String.format("票:(%s),在终端:(%s),重试送票次数是:%s",ticket.getId(),ticket.getTerminalId(),count+1);
					failureCache.setFailureCount(failureCache.getFailureCount()+1);
					redisCache.save(key,180,failureCache);
					systemExceptionMessageHandler.saveMessage(error);
					logger.error(error);
                    return  TicketBatchSendResultProcessType.retry;
				}else {
                    TerminalType terminalType=venderConfigHandler.getTypeByTerminalId(ticketBatch.getTerminalId());
					IVenderTicketCheckWorker ticketCheckWorker=venderCheckerWorkerMap.get(terminalType);
					IVenderConverter venderConverter=venderConverterBinder.get(terminalType);
					IVenderConfig venderConfig=venderConfigHandler.getByTerminalId(ticketBatch.getTerminalId());
					if(ticketCheckWorker!=null&&venderConverter!=null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						List<TicketVender> ticketVenderList=ticketCheckWorker.process(ticketList,venderConfig,venderConverter);
						if(ticketVenderList!=null&&ticketVenderList.size()>0){
							TicketVender ticketVender=ticketVenderList.get(0);
							TicketVenderStatus ticketVenderStatus=ticketVender.getStatus();
							if(ticketVenderStatus==TicketVenderStatus.printing||ticketVenderStatus==TicketVenderStatus.success||ticketVenderStatus==TicketVenderStatus.duplicated){
								return TicketBatchSendResultProcessType.success;
							}else{
								String  error=String.format("票:(%s),在终端:(%s),重试送票次数是:%s,未找到该票,直接重分",ticket.getId(),ticket.getTerminalId(),count);
								systemExceptionMessageHandler.saveMessage(error);
								logger.error(error);
								return TicketBatchSendResultProcessType.reallot;
							}
						}else {
							return TicketBatchSendResultProcessType.reallot;
						}
					}else {
						return  TicketBatchSendResultProcessType.reallot;
					}

				}
			}
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}







		if (ticket.getTerminateTime() != null && ticket.getTerminateTime().getTime() - System.currentTimeMillis() > this.getReallotDeadlineForward(ticket.getLotteryType())) {
            String message = String.format("票(%s)在终端(%s)送票超时,但还未进入重分截止期, 继续重试",
                    ticket.getId(), ticketBatch.getTerminalId());
            logger.error(message);
			systemExceptionMessageHandler.saveMessage(message);
            return TicketBatchSendResultProcessType.retry;
        } else {
        	 String message = String.format("票(%s)在终端(%s)送票超时,进入截止期，重新分票",
                     ticket.getId(), ticketBatch.getTerminalId());
			 systemExceptionMessageHandler.saveMessage(message);
             logger.error(message);
           return TicketBatchSendResultProcessType.reallot;
        }
	}

	private long getReallotDeadlineForward(int lotteryType) {
		if (HighFrequencyLottery.contains(LotteryType.get(lotteryType))) {
			reallotDeadlineForward = highFrequencyUrgentProcessTime;
		}
		return reallotDeadlineForward;
	}
	@Override
	protected void init() {
		sendResultMap.put(TicketSendResultStatus.timeout, this);
		
	}
	
	

}
