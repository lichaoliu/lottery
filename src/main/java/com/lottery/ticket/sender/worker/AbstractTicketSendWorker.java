/**
 * 
 */
package com.lottery.ticket.sender.worker;

import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.lottery.*;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.domain.ticket.TicketBatchSendLog;
import com.lottery.core.handler.SystemExceptionMessageHandler;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.core.service.TicketAllotLogService;
import com.lottery.core.service.TicketBatchSendLogService;
import com.lottery.core.service.TicketBatchService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.core.terminal.ITerminalSelector;
import com.lottery.ticket.sender.ITicketSendWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractTicketSendWorker implements ITicketSendWorker {

	protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected TicketService ticketService;

	@Autowired
	protected TicketBatchService ticketBatchService;

	// private IWarningTool warningTool;
	@Autowired
	protected ITerminalSelector terminalSelector;
	
	@Autowired
	protected VenderConfigHandler venderConfigService;

	@Autowired
	private SystemExceptionMessageHandler systemExceptionMessageHandler;
	@Override
	public void execute(TicketBatch ticketBatch, LotteryType lotteryType) throws Exception {

		if (beforeHandler(ticketBatch, lotteryType)) {
			if (ticketBatch.getStatus()==TicketBatchStatus.SEND_QUEUED.value) {
				ticketBatch.setStatus(TicketBatchStatus.SEND_WAITING.value);	// 重置为待出票
				ticketBatch.setUpdateTime(new Date());
				ticketBatchService.updateTicketBatchStatus(ticketBatch);
			}
			return;
		}

		if (!terminalIsEnabled(ticketBatch, lotteryType)) {
			return;
		}

		// 取批次中状态为未送票的票
		List<Ticket> ticketList = null;
		try {
			ticketList = ticketService.getByBatchIdAndStatus(ticketBatch.getId(), TicketStatus.UNSENT.getValue());
		} catch (Exception e) {
			logger.error("查找批次{}中的票出错", ticketBatch.getId());
			throw e;
		}
		if (ticketList == null || ticketList.isEmpty()) {
			logger.error("批次{}中没有票,直接将批次设成已送票", ticketBatch.getId());
			ticketBatch.setSendTime(new Date());
			ticketBatch.setStatus(TicketBatchStatus.SEND_SUCCESS.value);
			ticketBatch.setUpdateTime(new Date());
			ticketBatchService.updateStatusAndSendTime(ticketBatch);
			// warningTool.sendMail(String.format("批次(%s)中没有票，直接将批次设成已送票",
			// ticketBatch.getId()));

			return;
		}
		try {
			writeTicketBatchSendLog(ticketBatch, "准备就绪");
			executeSend(ticketBatch, ticketList, lotteryType);
			afterSendHandler(ticketBatch, ticketList, lotteryType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			String errorMessage = e.getMessage();
			if (errorMessage != null && errorMessage.length() > 4000) {
				errorMessage = errorMessage.substring(0, 3800);
			}
			String errorStr=String.format("批次:%s,在终端:%s,送票出错,错误信息是:%s",ticketBatch.getId(),ticketBatch.getTerminalId(),e.getMessage());
			//systemExceptionMessageHandler.saveMessage(errorStr);
			writeTicketBatchSendLog(ticketBatch, "送票出错:" + errorMessage);
			errorHandler(ticketBatch, ticketList, lotteryType);
		}
	}

	protected boolean terminalIsEnabled(TicketBatch ticketBatch, LotteryType lotteryType) throws Exception {
		// 如果送票时终端已被禁用, 直接抛出异常进行重分
		if (!terminalSelector.isEnabledForLotteryType(ticketBatch.getTerminalId(), lotteryType,PlayType.get(ticketBatch.getPlayType()))) {
			// 记录送票出错日志
			this.writeTicketBatchSendLog(ticketBatch, "终端已被禁用,直接重分");

			logger.error("批次{}在终端:{}送票时终端已被禁用, 不再进行送票,直接进入错误处理", ticketBatch.getId(),ticketBatch.getTerminalId());
			errorHandler(ticketBatch, null, lotteryType);
			return false;
		}
		
		return true;
	}

	abstract public void executeSend(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType) throws Exception;

	protected void afterSendHandler(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType) throws Exception {

		// 未抛出异常不做重新分票处理的批次
		if (ticketBatch.getStatus() == TicketBatchStatus.SEND_QUEUED.value) {
			ticketBatch.setStatus(TicketBatchStatus.SEND_WAITING.value); // 重置为待出票
			ticketBatch.setUpdateTime(new Date());
			ticketBatchService.updateTicketBatchStatus(ticketBatch);
		}
	}

	protected void errorHandler(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType) throws Exception {
		// 送票失败后的处理
		// 常规做法：给批次指定一个新的出票终端

		// 失败计数器+1

		PlayType playType = null;
		try {
			playType = PlayType.get(ticketBatch.getPlayType());
		} catch (Exception e) {
			playType = PlayType.mix;
		}



		terminalSelector.increaseFailureCount(ticketBatch.getTerminalId(), lotteryType, ticketBatch.getPhase(), playType);

		// 发送邮件报警
		// Long failedTerminalId = ticketBatch.getTerminalId(); // 记录送票失败的终端号
		/*
		 * String message =
		 * String.format("批次(%s)送票失败,失败的终端号为(%s),%s,%s,请立即检查,系统将重新分配终端",
		 * ticketBatch.getId(), failedTerminalId, lotteryType.getName(),
		 * ticketBatch.getPhase()); // warningTool.sendMail(message);
		 */

		// 重新选择一个终端
		Long terminalId = terminalSelector.getTopPriorityTerminalId(lotteryType, ticketBatch.getPhase(), playType, ticketBatch.getTerminateTime());
		if (terminalId == null) {
			// warningTool.sendMail(String.format("批次(%s)送票失败，重新选择终端时未找到彩种(%s)的可用终端",
			// ticketBatch.getId(), lotteryType.getName()));

			if (ticketList == null) {
				ticketList = ticketService.getByBatchIdAndStatus(ticketBatch.getId(), TicketStatus.UNSENT.getValue());
			}
			if (ticketList == null || ticketList.isEmpty()) {
				ticketBatch.setStatus(TicketBatchStatus.SEND_SUCCESS.value); // 重置为送票成功
				ticketBatch.setTerminalId(terminalId);
				ticketBatch.setUpdateTime(new Date());
				ticketBatchService.updateStatusAndTerminalId(ticketBatch);
				writeTicketBatchSendLog(ticketBatch, "该批次没有未送的票,直接设置成功");
				return;
			}
			Date currentDate = new Date();
			for (Ticket ticket : ticketList) {
				if (ticket.getTerminateTime() != null && ticket.getTerminateTime().after(currentDate)) {
					logger.error("批次({})票未过截止期, 将票置为未分配等待重新分票",ticketBatch.getId());
					ticket.setStatus(TicketStatus.UNALLOTTED.value);
					ticketService.updateTicketStatus(ticket);
                    ticketBatch.setUpdateTime(new Date());
					ticketBatch.setStatus(TicketBatchStatus.SEND_WAITING.value); // 重置为待出票(其实本批次没有票,为了流程方便)
					ticketBatch.setTerminalId(terminalId);
					ticketBatchService.updateStatusAndTerminalId(ticketBatch);
				}else {
					ticket.setStatus(TicketStatus.CANCELLED.value);
					ticket.setFailureType(TicketFailureType.PRINT_EXPIRED.value);
					ticket.setFailureMessage(TicketFailureType.PRINT_EXPIRED.getName());
					ticketService.update(ticket);
				}
			}
			writeTicketBatchSendLog(ticketBatch, "无可用终端");

			throw new RuntimeException("批次"+ticketBatch.getId()+"送票失败,重新选择终端时无可用终端");
		}
		logger.error("批次{},原终端是:{},重新选择的终端id是:{}", new Object[] { ticketBatch.getId(),ticketBatch.getTerminalId(),terminalId });
		ticketBatch.setStatus(TicketBatchStatus.SEND_WAITING.value); // 重置为待出票
		ticketBatch.setTerminalId(terminalId);
		Terminal terminal = terminalSelector.getTerminal(terminalId);
		if (terminal != null)
			ticketBatch.setTerminalTypeId(terminal.getTerminalType());
		ticketBatchService.updateStatusAndTerminalId(ticketBatch);
		List<Ticket> list = ticketService.getByBatchId(ticketBatch.getId());
		for (Ticket ticket : list) {
			if (ticket.getStatus() == TicketStatus.PRINT_SUCCESS.value || ticket.getStatus() == TicketStatus.PRINTING.value) {
				continue;
			}
			ticket.setTerminalId(terminalId);
			if (terminal != null){
				ticket.setTerminalType(terminal.getTerminalType());
			}
			ticketService.update(ticket);
		}
		writeTicketBatchSendLog(ticketBatch, "更改送票终端");
	}

	/**
	 * 如果返回true则不往下处理
	 * 
	 * @param lotteryType
	 * @return
	 */
	protected boolean beforeHandler(TicketBatch ticketBatch, LotteryType lotteryType) throws Exception{
		// 送票前的处理
		// 常规做法：处理不同彩种固定时段不送票的问题

		return false;
	}

	public TicketService getTicketService() {
		return ticketService;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	public TicketBatchService getTicketBatchService() {
		return ticketBatchService;
	}

	public void setTicketBatchService(TicketBatchService ticketBatchService) {
		this.ticketBatchService = ticketBatchService;
	}

	public ITerminalSelector getTerminalSelector() {
		return terminalSelector;
	}

	public void setTerminalSelector(ITerminalSelector terminalSelector) {
		this.terminalSelector = terminalSelector;
	}


	/**
	 * 将票置为重新分票
	 * 
	 * @param ticketId
	 *            票id
	 * */
	protected void allotInit(String ticketId) {
		Ticket ticket = ticketService.getTicket(ticketId);
		if (ticket != null) {
			ticket.setTerminalId(null);
			ticket.setSendTime(null);
			ticket.setBatchId(null);
			ticket.setBatchIndex(null);
			ticket.setStatus(TicketStatus.UNALLOTTED.getValue());
			ticketService.update(ticket);
		}
	}

	@Autowired
	protected TicketBatchSendLogService ticketBatchSendLogService;

	protected void writeTicketBatchSendLog(TicketBatch ticketBatch, String message) {
		try {
			TicketBatchSendLog ticketBatchSendLog = new TicketBatchSendLog();
			ticketBatchSendLog.setCreateTime(new Date());
			ticketBatchSendLog.setBatchId(ticketBatch.getId());
			ticketBatchSendLog.setTerminalId(ticketBatch.getTerminalId());
			ticketBatchSendLog.setTerminalTypeId(ticketBatch.getTerminalTypeId());
			ticketBatchSendLog.setErrorMessage(message);
			ticketBatchSendLogService.save(ticketBatchSendLog);
		} catch (Exception e) {
			logger.error("批次送票日志出错batchId={}", ticketBatch.getId(), e);
		}
	}





}
