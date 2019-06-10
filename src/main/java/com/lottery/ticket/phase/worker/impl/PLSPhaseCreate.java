package com.lottery.ticket.phase.worker.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;
@Component
public class PLSPhaseCreate extends AbstractPhaseCreate {
	
	@Override
	public LotteryPhase creatNextPhase(LotteryPhase last) {
		Date startSaleTime = DateUtil.getPhaseTime(last.getEndTicketTime(), "20:00");
		Date endTime = getPhaseTicketEndTime(startSaleTime);
		
		LotteryPhase lotteryPhase = new LotteryPhase();
		lotteryPhase.setLotteryType(last.getLotteryType());
		lotteryPhase.setPhase(getTCPhaseNo(last.getPhase(), endTime));
		
		lotteryPhase.setStartSaleTime(startSaleTime);
		lotteryPhase.setEndTicketTime(endTime);
		lotteryPhase.setEndSaleTime(DateUtil.getPhaseTime(endTime, "20:00"));
		lotteryPhase.setHemaiEndTime(DateUtil.getPhaseTime(endTime, "20:00"));
		lotteryPhase.setDrawTime(DateUtil.getPhaseTime(endTime, "20:30"));
		
		updateCreatePhase(lotteryPhase);
		return lotteryPhase;
	}
	
	private Date getPhaseTicketEndTime(Date startSaleTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startSaleTime);
		calendar.set(Calendar.HOUR_OF_DAY, 20);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
		return calendar.getTime();
	}

	

	@Override
	protected LotteryType getLotteryType() {
		
		return LotteryType.PL3;
	}
}
