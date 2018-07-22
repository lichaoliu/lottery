package com.lottery.ticket.processor;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TicketFailureType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.allotter.ITicketAllotWorker;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommonFailedTicketVenderProcessor extends AbstractTicketVenderProcessor {

	@Override
	protected void execute(Ticket ticket, TicketVender ticketVender) {
		LotteryType lotteryType = LotteryType.get(ticket.getLotteryType());


		logger.error("票({}),在终端:({})出票失败,对方原始返回码为:{},是否通知:{},原始返回字符串:{}",ticket.getId(),ticketVender.getTerminalId(),ticketVender.getStatusCode(),ticketVender.isTicketNotify(),ticketVender.getResponseMessage());

		String failemsg=String.format("票(%s),在终端:(%s)出票失败,对方原始返回码为:%s,是否通知:%s,原始返回字符串:%s",ticket.getId(),ticketVender.getTerminalId(),ticketVender.getStatusCode(),ticketVender.isTicketNotify(),ticketVender.getResponseMessage());
		systemExceptionMessageHandler.saveMessage(failemsg);

		if(ticket.getStatus()!=TicketStatus.PRINTING.value){
			logger.error("票:{},不是出票中状态,不做处理",ticket.getId());
			return;
		}

		if (ticket.getStatus() == TicketStatus.PRINT_FAILURE.getValue()) {
			logger.error("收到票({}, {}, {}期)出票失败消息时已经为出票失败状态, 不做处理", new Object[] { ticket.getId(), ticket.getLotteryType(), ticket.getPhase() });
			// 发送方案检票队列
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", ticket.getOrderId());
			sendJms(ticket);
			return;
		}

		// 已过方案截止超时回收时间的失败通知不做处理, 仅作邮件报警记录
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MILLISECOND, -1 * (int) this.getTicketTimeout(ticket.getLotteryType()));
		if (calendar.getTime().after(ticket.getTerminateTime())) {
			// 过期通知不处理，发送报警邮件转人工处理
			String warn = String.format("收到来自终端(%s, %s)彩种(%s)第(%s)期票(%s)出票失败信息, 但已经超过出票截止超时回收时间, 对通知不做任何处理, 转人工处理, orderId=%s, 收到通知时间date=[%s]", ticketVender.getTerminalId(), ticketVender.getTerminalType(), lotteryType, ticket.getPhase(), ticket.getId(), ticket.getOrderId(), CoreDateUtils.formatDateTime(date));
			logger.error(warn);
		} else if (ticket.getStatus() == TicketStatus.PRINTING.getValue()) {
			// 获取分票器
			ITicketAllotWorker allotWorker = getAllotExecutor(lotteryType);
			if (allotWorker == null) {
				String allotWar = String.format("收到票(%s)的出票失败消息, 尝试拆分成单张批次重新分票时未找到彩种(%s)的分票器", ticket.getId(), lotteryType);
				logger.error(allotWar);
				return;
			}
		
			TicketFailureType venderFailureType = ticketVender.getFailureType() == null ? TicketFailureType.ALL : ticketVender.getFailureType();
			TicketFailureType checkFailureType = TicketFailureType.PRINT_LIMITED;




			if (this.checkAllotWithFailureCheck(ticket, venderFailureType, checkFailureType)) {
				try {
					Long oldTerminalId = ticket.getTerminalId();
					Long newTerminalId = allotWorker.executeWithFailureCheck(ticket);
					if (newTerminalId != null) {
						// 分票成功
						String alloterSuccess=String.format("票(%s)在终端(%s)出票失败(%s), 尝试重新选择其他可用终端分票成功, 新的终端号为: %s", ticket.getId(), oldTerminalId, checkFailureType, newTerminalId);
						systemExceptionMessageHandler.saveMessage(alloterSuccess);
						logger.error("票({})在终端({})出票失败({}), 尝试重新选择其他可用终端分票成功, 新的终端号为: {}", ticket.getId(), oldTerminalId, checkFailureType, newTerminalId);
					} else {
						// 分票失败说明没有可用的其他终端, 直接设置失败
						ticket.setFailureType(checkFailureType.getValue());
						ticket.setFailureMessage(ticketVender.getMessage());
						ticket.setStatus(TicketStatus.PRINT_FAILURE.value);

						ticketService.updateTicketStatusAndFailureInfo(ticket);
						String message = String.format("票(%s, %s, %s)在终端(%s)出票失败(%s), 未找到其他可用终端, 直接设置出票失败", ticket.getId(), ticket.getLotteryType(), ticket.getPhase(), oldTerminalId, checkFailureType);
						systemExceptionMessageHandler.saveMessage(message);
						logger.error(message);
						
					}
					return;
				} catch (Exception e) {
					logger.error("票({})出票限号, 尝试重新选择其他可用终端分票时出错, 不做特殊处理", ticket.getId(), e);
				}
			}

			// TODO 针对不同彩种可进行不同的扩展处理

			// 防止重新分票的时候一个批次包含已经送过的票而导致整个批次的票都返回相同结果，需要将整个批次拆分成单张票进行重新分配
			// 说明该票是同时成功同时失败的票，不知道是对哪张票进行了限号，需要重新分配
			// 增加外部票号的存储变量
			String externalId = ticket.getExternalId();
			try {
				Long oldterminalid=ticket.getTerminalId();
			    Long newterminalId=allotWorker.executeWithFailureCheck(ticket);
				String message = String.format("收到来自(%s)号终端(%s)票(%s)对方票号(%s)的出票失败消息, 已重分终端,旧终端为(%s)新终端为(%s)", ticketVender.getTerminalId(), ticketVender.getTerminalType(), ticket.getId(), externalId,oldterminalid,newterminalId);
				systemExceptionMessageHandler.saveMessage(message);
				logger.error(message);
			} catch (Exception e) {
				String message = String.format("收到来自(%s)号终端(%s)票(%s)对方票号(%s)的出票失败消息, 尝试拆分成单张批次重新分票时失败,直接将票设置为未分配,errormsg=%s", ticketVender.getTerminalId(), ticketVender.getTerminalType(), ticket.getId(), externalId,e.getMessage());
				ticketService.updateTicketStatus(TicketStatus.UNALLOTTED.value,ticket.getId());
				logger.error(message);

			}
		}

	}

	protected boolean checkAllotWithFailureCheck(Ticket ticket, TicketFailureType venderFailureType, TicketFailureType checkFailureType) {
		if (checkFailureType == null) {
			checkFailureType = TicketFailureType.ALL;
		}
		if (venderFailureType == null) {
			venderFailureType = TicketFailureType.ALL;
		}

		boolean result = false;

		// 明确匹配的失败类型需要进行重分检查处理, 同时设置失败记录缓存
		if (venderFailureType.getValue() == checkFailureType.getValue()) {
			// 设置失败记录
			ticketServiceCache.setTicketFailureTypeCache(ticket.getId(), checkFailureType);
			result = true;
		} else {
			if (ticketServiceCache.hasTicketFailureTypeCache(ticket.getId(), checkFailureType)) {
				// 对于某些不能明确返回出错类型的, 检查是否有其他终端设置了失败记录
				result = true;
			}
		}

		return result;
	}

	@Override
	protected void init() {
		ticketVenderProcessorMap.put(TicketVenderStatus.failed, this);
		ticketVenderProcessorMap.put(TicketVenderStatus.unkown, this);

	}

}
