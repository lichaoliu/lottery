package com.lottery.ticket.sender.processor;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.contains.ticket.TicketBatchSendResultProcessType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.JingcaiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 出票成功
 * */
@Service
public class CommonPrintedTicketSendVenderProcessor extends AbstractTicketSendVenderProcessor {
	@Autowired
	private JingcaiService jingcaiService;
	@Override
	public TicketBatchSendResultProcessType process(TicketSendResult ticketSendResult, Ticket ticket, TicketBatch ticketBatch) {
		ticket.setStatus(TicketStatus.PRINT_SUCCESS.value);
		Date sendTime = ticketSendResult.getSendTime();
		if(ticket.getSendTime()==null){
			if (sendTime == null) {
				sendTime = new Date();
			}
			ticket.setSendTime(sendTime);
		}
		
		
		if(ticketSendResult.getPrintTime()!=null){
			ticket.setPrintTime(ticketSendResult.getPrintTime());
		}else{
			ticket.setPrintTime(sendTime);
		}
		ticket.setMachineCode(ticketSendResult.getMachineCode());
		ticket.setSellRunCode(ticketSendResult.getSellRunCode());
		ticket.setMd5Code(ticketSendResult.getMd5Code());
		ticket.setTerminalId(ticketBatch.getTerminalId());
		ticket.setExternalId(ticketSendResult.getExternalId());
		ticket.setPasswd(ticketSendResult.getPasswd());
		ticket.setSerialId(ticketSendResult.getSerialId());
		if(ticketSendResult.getTerminalType()!=null)
		ticket.setTerminalType(ticketSendResult.getTerminalType().getValue());
		int lotteryType = ticket.getLotteryType();

		if (LotteryType.getJczqValue().contains(lotteryType) || LotteryType.getJclqValue().contains(lotteryType)) {
			if (StringUtils.isBlank(ticketSendResult.getPeiLv()) ) {
				ticket.setStatus(TicketStatus.PRINTING.value);
				logger.error("票{}为竞彩,没有赔率,需等检票线程", ticket.getId());
			} else {

				if (!jingcaiService.validateJingcaiOdd(ticket.getLotteryType(),ticket.getContent(),ticketSendResult.getPeiLv())){
					logger.error("票:{},赔率不正确,票内容为:{},解析赔率:{},",ticket.getId(),ticket.getContent(),ticketSendResult.getPeiLv());
					ticket.setStatus(TicketStatus.PRINTING.value);
				}else{
					ticket.setPeilv(ticketSendResult.getPeiLv());
				}

			}
		}

		int count = ticketService.updateTicketSuccessInfo(ticket,ticketSendResult.isSyncPrinted());
		if (count > 0) {
			sendJms(ticket);
		}
		return TicketBatchSendResultProcessType.success;
	}

	@Override
	protected void init() {
		sendResultMap.put(TicketSendResultStatus.printed, this);

	}

}
