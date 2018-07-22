package com.lottery.ticket.sender.processor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.lottery.common.cache.redis.RedisLock;
import com.lottery.common.cache.redis.SharedJedisPoolManager;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.core.handler.OrderProcessHandler;
import com.lottery.core.handler.SystemExceptionMessageHandler;
import com.lottery.core.service.TicketServiceCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.lottery.HighFrequencyLottery;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.contains.ticket.TicketBatchSendResultProcessType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.queue.QueueMessageSendService;

public abstract class AbstractTicketSendVenderProcessor implements ITicketSendVenderProcessor {
	protected final transient Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	protected static String DATE_STR = "yyyy-MM-dd HH:mm:ss.SSS";
	@Autowired
	protected TicketService ticketService;
	@Autowired
	protected QueueMessageSendService queueMessageSendService;
	@Resource(name = "sendResultMap")
	protected Map<TicketSendResultStatus, ITicketSendVenderProcessor> sendResultMap;
	@Autowired
	protected SystemExceptionMessageHandler systemExceptionMessageHandler;
	@Autowired
	protected SharedJedisPoolManager shareJedisPoolManager;
	@Autowired
	private TicketServiceCache ticketServiceCache;

	@Autowired
	private OrderProcessHandler orderProcessHandler;
	protected TicketBatchSendResultProcessType processSuccessful(TicketSendResult ticketSendResult, Ticket ticket, TicketBatch ticketBatch) {

		RedisLock lock = new RedisLock(this.shareJedisPoolManager, String.format("ticket_vender_process_%s", ticketSendResult.getRequestId()), 5);

		boolean hasLocked = false;

		try {
			hasLocked = lock.tryLock(3000l, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		ticket.setStatus(TicketStatus.PRINTING.value);
		Date sendTime = ticketSendResult.getSendTime();
		if (sendTime == null) {
			sendTime = new Date();
		}
		ticket.setSendTime(sendTime);
		ticket.setTerminalId(ticketBatch.getTerminalId());
		if (ticketSendResult.getTerminalType() != null) {
			ticket.setTerminalType(ticketSendResult.getTerminalType().value);
		}
		ticketService.updateTicketPrintingInfo(ticket);
		// 如果有当前终端下的出票成功信息, 直接触发
		TicketVender ticketVender = ticketServiceCache.getTicketVenderCache(ticketSendResult.getId(), ticketSendResult.getTerminalId());
		if (ticketVender != null) {
			logger.error("检测到票({})有{}终端({})的出票信息({}), 直接触发票处理", new Object[]{
					ticket.getId(), ticketSendResult.getTerminalType(), ticketSendResult.getTerminalType(), ticketVender.getStatus()
			});
			sendJms(ticket);
		}

		if (hasLocked) {
			// 如果获取到锁, 执行完处理后解锁
			lock.unlock();
		}
		return TicketBatchSendResultProcessType.success;
	}

	@PostConstruct
	abstract protected void init();

	protected void sendJms(Ticket ticket) {
		try {
			String queuename = QueueName.betChercher.value;
			LotteryType lotteryType = LotteryType.get(ticket.getLotteryType());
			if (HighFrequencyLottery.contains(lotteryType)) {
				queuename = QueueName.gaopinChercher.value;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", ticket.getOrderId());
			this.queueMessageSendService.sendMessage(queuename, map);
		} catch (Exception e) {
			orderProcessHandler.check(ticket.getOrderId());
			String errormessage=String.format("送票发送jms检票消息失败,errormessage=%s",e.getMessage());
			systemExceptionMessageHandler.saveMessage(errormessage);
			logger.error("发送检票消息出错",e);
			logger.error("发送检票消息出错", e);
		}
	}
}
