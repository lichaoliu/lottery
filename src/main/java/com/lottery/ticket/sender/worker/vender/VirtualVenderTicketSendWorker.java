package com.lottery.ticket.sender.worker.vender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.PrizeService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
@Component
public class VirtualVenderTicketSendWorker extends AbstractVenderTicketSendWorker{

	@Autowired
	protected PrizeService prizeService;
	public List<TicketSendResult> executeSend(TicketBatch ticketBatch, List<Ticket> ticketList,LotteryType lotteryType) throws Exception{
		if (this.getTicketSendInterval() > 0) {
			synchronized (this) {
				try{
					this.wait(this.getTicketSendInterval());
				}catch (Exception e){
					logger.error(e.getMessage(),e);
				}

			}
		}
		return this.executePerSend(ticketBatch, ticketList,  lotteryType,null,null);
	}
	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList,LotteryType lotteryType, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
		for(Ticket ticket:ticketList){
			TicketSendResult ticketSendResult=createInitializedTicketSendResult(ticket);
			ticketSendResultList.add(ticketSendResult);
			ticketSendResult.setSendTime(this.getSendTime());
			ticketSendResult.setStatus(TicketSendResultStatus.printed);
			ticketSendResult.setTerminalType(getTerminalType());
			ticketSendResult.setTerminalId(ticketBatch.getTerminalId());
			ticketSendResult.setExternalId(getExtermianlId());
			ticketSendResult.setSyncPrinted(true);
			String odds="";
			if(LotteryType.getJclq().contains(lotteryType)||LotteryType.getJczq().contains(lotteryType)){
				lotteryType=LotteryType.getLotteryType(ticket.getLotteryType());
				odds=prizeService.simulateOdd(ticket.getContent(),lotteryType);
				logger.error("票({})的虚拟赔率是:{}",ticket.getId(),odds);
				ticketSendResult.setPeiLv(odds);
			}

            if (lotteryType==LotteryType.JC_GUANJUN||lotteryType==LotteryType.JC_GUANYAJUN){
				odds=prizeService.simulateGuanyajunOdd(ticket.getContent(),lotteryType,ticket.getPhase());
				logger.error("票({})的冠亚军虚拟赔率是:{}",ticket.getId(),odds);
				ticketSendResult.setPeiLv(odds);
			}

		}

		return ticketSendResultList;
	}

	protected  String getExtermianlId(){
		return System.currentTimeMillis()+"";
	}

	protected  Date getSendTime(){
		return new Date();
	}
	
	@Override
	protected TerminalType getTerminalType() {
		
		return TerminalType.T_Virtual;
	}

}
