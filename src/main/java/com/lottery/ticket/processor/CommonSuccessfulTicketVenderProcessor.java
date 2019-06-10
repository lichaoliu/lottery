package com.lottery.ticket.processor;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.JingcaiService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class CommonSuccessfulTicketVenderProcessor extends AbstractTicketVenderProcessor {
    @Autowired
	private JingcaiService jingcaiService;
	@Override
	protected void execute(Ticket ticket, TicketVender ticketVender) {

		// 记录出票序列号
		ticket.setExternalId(ticketVender.getExternalId());
		ticket.setPeilv(ticketVender.getPeiLv());
		ticket.setSerialId(ticketVender.getSerialId());
		// 处理出票状态
		if (ticket.getStatus() == TicketStatus.PRINTING.getValue()) {
			ticket.setStatus(TicketStatus.PRINT_SUCCESS.value);
		} else {
			if (ticket.getStatus() != TicketStatus.PRINT_SUCCESS.getValue()) {
				// 如果是未送票状态, 有可能是出票成功通知早于送票状态被更新时间到达, 记录缓存等待后续处理
				if (ticket.getStatus() == TicketStatus.UNSENT.getValue()) {
					logger.error("票({})({})收到出票商({})终端({})出票成功信息,但数据库中票为({})状态,有可能是出票成功通知早于送票状态被更新时间到达,放入缓存等待送票更新后续处理", new Object[] { ticket.getId(), ticket.getLotteryType(), ticketVender.getTerminalType(), ticketVender.getTerminalId(), ticket.getStatus() });
					ticketServiceCache.setTicketVenderCache(ticketVender, ticketVender.getTerminalId());
					return;
				}
				// 其他状态不进行状态变更，且对于原状态不为出票成功状态的发送邮件通知
				String unsetMsg = String.format("票(%s)(%s)收到出票商(%s)终端(%s)出票成功信息,但数据库中票为(%s)状态,不进行更新,请人工处理", ticket.getId(), ticket.getLotteryType(), ticketVender.getTerminalType(), ticketVender.getTerminalId(), ticket.getStatus());
				systemExceptionMessageHandler.saveMessage(unsetMsg);
				logger.error(unsetMsg);
				return;
			} else {
				logger.error("票:{}不是已成功状态",ticket.getId());
				return;
			}
		}

		// 处理出票时间
		if (ticket.getPrintTime() == null) {
			Date printTime = ticketVender.getPrintTime();
			if (printTime == null) {
				ticket.setPrintTime(new Date());
			} else {
				if ((ticket.getSendTime() != null && printTime.before(ticket.getSendTime())) || printTime.after(ticket.getTerminateTime())) {
					Calendar checkTimeCalendar = Calendar.getInstance();

					ticket.setPrintTime(checkTimeCalendar.getTime());

					// 误差在一分钟之内不报警
					boolean warning = true;

					if (ticket.getSendTime() != null && printTime.before(ticket.getSendTime()) && ticket.getSendTime().getTime() - printTime.getTime() <= 60000) {
						warning = false;
					}

					if (warning) {
						String message = String.format("出票商返回票(%s)的打票时间在送票时间之前或在截止时间之后,仅做邮件记录,获取到的出票时间为:%s,查询时间为:%s,截止时间为:%s", ticket.getId(), CoreDateUtils.formatDateTime(printTime), CoreDateUtils.formatDateTime(checkTimeCalendar.getTime()), CoreDateUtils.formatDateTime(ticket.getTerminateTime()));
						systemExceptionMessageHandler.saveMessage(message);
						logger.error(message);
					}
				} else {
					ticket.setPrintTime(printTime);
				}
			}
		}

		// 如果送票时发生超时等情况，可能产生已送票但是数据库中没有送票时间和终端号的情况，这时候需要补全
		if (ticket.getTerminalId() == null || ticket.getTerminalId() == 0 || ticket.getSendTime() == null) {
			if (ticket.getBatchId() != null) {
				TicketBatch ticketBatch = ticketBatchService.get(ticket.getBatchId());
				if (ticketBatch != null) {
					ticket.setTerminalId(ticketBatch.getTerminalId());
					ticket.setSendTime(ticketBatch.getSendTime());
				}
			}
		}
		if (ticket.getSendTime() == null) {
			// 如果最后还是未取到送票时间，直接设成和出票时间相同
			ticket.setSendTime(ticket.getPrintTime());
		}

		if (LotteryType.getJcValue().contains(ticket.getLotteryType())){
			if (StringUtils.isBlank(ticketVender.getPeiLv())){
				logger.error("票{}为竞彩,但赔率为空,请检查,isnotice:{}",ticket.getId(),ticketVender.isTicketNotify());
				String message=String.format("票%s为竞彩,但赔率为空,请检查,isnotice:%s",ticket.getId(),ticketVender.isTicketNotify());
				systemExceptionMessageHandler.saveMessage(message);
				return;
			}
            if (!jingcaiService.validateJingcaiOdd(ticket.getLotteryType(),ticket.getContent(),ticketVender.getPeiLv())){
               logger.error("票:{},赔率不正确,票内容为:{},解析赔率:{},原始赔率为:{},是否通知:{}",ticket.getId(),ticket.getContent(),ticketVender.getPeiLv(),ticketVender.getOtherPeilv(),ticketVender.isTicketNotify());
				String message=String.format("票:%s,赔率不正确,票内容为:%s,解析赔率:%s,原始赔率为:%s,是否通知:%s",ticket.getId(),ticket.getContent(),ticketVender.getPeiLv(),ticketVender.getOtherPeilv(),ticketVender.isTicketNotify());
				systemExceptionMessageHandler.saveMessage(message);
				return;
			}

		}
		ticketService.updateTicketSuccessInfo(ticket,false);


		sendJms(ticket);

	}

	@Override
	protected void init() {
		ticketVenderProcessorMap.put(TicketVenderStatus.success, this);
	}

}
