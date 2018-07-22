package com.lottery.retrymodel.ticket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lottery.common.PageBean;
import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketAllotLog;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.TerminalService;
import com.lottery.core.service.TicketAllotLogService;
import com.lottery.core.service.TicketService;
import com.lottery.retrymodel.ApiRetryCallback;

/**
 * 票状态终端
 * */
public class TicketChangeTerminalIdCallback extends ApiRetryCallback<Object> {

	private Integer lotteryType;
	private Long fromId, toId;

	private TerminalService terminalService;

	private TicketService ticketService;

	private IdGeneratorService idGeneratorService;

	private TicketAllotLogService ticketAllotLogService;

	private Integer minute;
	private int max = 20;

	@Override
	protected Object execute() throws Exception {
		if (lotteryType == null || fromId == null || toId == null || terminalService == null || ticketAllotLogService == null || idGeneratorService == null || ticketService == null) {
			throw new Exception("参数不全");
		}
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(TicketStatus.PRINTING.value);
		// statusList.add(TicketStatus.UNSENT.value);
		PageBean<Ticket> page = new PageBean<Ticket>();
		page.setMaxResult(max);
		int pageIndex = 1;
		while (true) {
			List<Ticket> needList = null;
			page.setPageIndex(pageIndex);
			try {
				needList = ticketService.getChangeTerminal(fromId, lotteryType, statusList,minute,page);
			} catch (Exception e) {
				break;
			}
			if (needList != null && needList.size() > 0) {
				allot(needList, toId);
				if (needList.size() < max) {
					logger.info("读取到的方案列表不足一页，已读取结束");
					break;
				}
				synchronized (this) {
					try {
						this.wait(100);
					} catch (InterruptedException e) {
						logger.error(e.getMessage(), e);
					}

				}
			} else {
				break;
			}
			pageIndex++;
		}

		return null;
	}

	protected void allot(List<Ticket> ticketList, Long changeId) {
		for (Ticket ticket : ticketList) {
			Terminal terminal = terminalService.get(changeId);
			String batchId = idGeneratorService.getBatchTicketId();
			logger.error("票({})从终端:{},转换到终端:{},批次编号为:{}", ticket.getId(), fromId, changeId, batchId);
			TicketBatch ticketBatch = new TicketBatch();
			ticketBatch.setCreateTime(new Date());
			ticketBatch.setPhase(ticket.getPhase());
			ticketBatch.setTerminateTime(ticket.getDeadline());
			ticketBatch.setLotteryType(ticket.getLotteryType());
			ticketBatch.setTerminalId(changeId);
			ticketBatch.setId(batchId);
			ticketBatch.setStatus(TicketBatchStatus.SEND_WAITING.value);
			ticketBatch.setPlayType(PlayType.mix.value);
			ticketBatch.setTerminalTypeId(terminal.getTerminalType());
			List<Ticket> newList = new ArrayList<Ticket>();
			newList.add(ticket);
			ticketService.saveAllottedTicketsAndBatch(newList, ticketBatch);
			TicketAllotLog allotLog = new TicketAllotLog();
			allotLog.setBatchId(batchId);
			allotLog.setCreateTime(new Date());
			allotLog.setTerminalId(ticketBatch.getTerminalId());
			allotLog.setTicketId(ticket.getId());
			ticketAllotLogService.save(allotLog);
		}
	}

	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public TerminalService getTerminalService() {
		return terminalService;
	}

	public void setTerminalService(TerminalService terminalService) {
		this.terminalService = terminalService;
	}

	public TicketService getTicketService() {
		return ticketService;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	public IdGeneratorService getIdGeneratorService() {
		return idGeneratorService;
	}

	public void setIdGeneratorService(IdGeneratorService idGeneratorService) {
		this.idGeneratorService = idGeneratorService;
	}

	public TicketAllotLogService getTicketAllotLogService() {
		return ticketAllotLogService;
	}

	public void setTicketAllotLogService(TicketAllotLogService ticketAllotLogService) {
		this.ticketAllotLogService = ticketAllotLogService;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	

}
