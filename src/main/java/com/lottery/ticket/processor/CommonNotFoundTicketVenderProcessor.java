package com.lottery.ticket.processor;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.allotter.ITicketAllotWorker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CommonNotFoundTicketVenderProcessor extends AbstractTicketVenderProcessor {

	@Override
	protected void execute(Ticket ticket, TicketVender ticketVender) {
		LotteryType lotteryType = LotteryType.get(ticket.getLotteryType());

		logger.error("票({})不存在,对方原始返回码为:{},发送:{},返回:{}",ticket.getId(),ticketVender.getStatusCode(),ticketVender.getSendMessage(),ticketVender.getResponseMessage());
		if (ticket.getStatus() != TicketStatus.PRINTING.getValue()) {
			logger.error("收到票(%s)在出票商不存在消息时不为出票中状态,不做处理", ticket.getId());
			return;
		}

		// 已过方案截止超时回收时间的失败通知不做处理, 仅作邮件报警记录
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MILLISECOND, -1 * (int) this.getTicketTimeout(ticket.getLotteryType()));
		if (calendar.getTime().after(ticket.getTerminateTime())) {
			// 过期通知不处理，发送报警邮件转人工处理
			String warn = String.format("收到彩种(%s)第(%s)期票(%s)的出票商(%s)未接收票信息,但已经超过出票截止超时回收时间,对通知不做任何处理,转人工处理, orderId=%s, 收到通知时间date=[%s]", lotteryType, ticket.getPhase(), ticket.getId(),ticket.getTerminalId(), ticket.getOrderId(), CoreDateUtils.formatDateTime(date));
			systemExceptionMessageHandler.saveMessage(warn);
			logger.error(warn);
		} else {
			// 获取分票器
			ITicketAllotWorker allotWorker = getAllotExecutor(lotteryType);
			if (allotWorker == null) {
				String allotWarn = String.format("出票商(%s)未接收票(%s)的投注信息,尝试拆分成单张批次重新分票时未找到彩种(%s)的分票器", ticket.getTerminalId(),ticket.getId(), lotteryType);
				systemExceptionMessageHandler.saveMessage(allotWarn);
				logger.error(allotWarn);
				return;
			}

			ticket.setTerminalId(null);
			ticket.setSendTime(null);
			ticket.setBatchId(null);
			ticket.setBatchIndex(null);
			ticket.setExternalId(null);
			ticket.setTerminalType(null);

			List<Ticket> ticketList = new ArrayList<Ticket>();
			ticketList.add(ticket);

			try {
				allotWorker.execute(ticketList, 1, lotteryType, ticket.getPhase());
				String successMsg = String.format("出票商未接收票(%s)的投注信息,已自动做拆分成单张批次重新分票处理", ticket.getId());
				systemExceptionMessageHandler.saveMessage(successMsg);
				logger.error(successMsg);
			} catch (Exception e) {
				logger.error("票({})重新选择终端失败", ticket.getId(), e);
				ticketService.updateTicketStatus(TicketStatus.UNALLOTTED.value,ticket.getId());
			}
		}
	}

	@Override
	protected void init() {
		ticketVenderProcessorMap.put(TicketVenderStatus.not_found, this);

	}

}
