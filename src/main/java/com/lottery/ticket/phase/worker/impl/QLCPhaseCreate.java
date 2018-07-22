package com.lottery.ticket.phase.worker.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;

@Service
public class QLCPhaseCreate extends AbstractPhaseCreate {
	
	@Override
	public LotteryPhase creatNextPhase(LotteryPhase last) {
		Date startSaleTime = DateUtil.getPhaseTime(last.getEndTicketTime(), "20:00");
		Date endTime = getPhaseTicketEndTime(startSaleTime);
		
		LotteryPhase lotteryPhase = new LotteryPhase();
		lotteryPhase.setLotteryType(last.getLotteryType());
		lotteryPhase.setPhase(getPhaseNo(last.getPhase(), endTime));
		
		lotteryPhase.setStartSaleTime(startSaleTime);
		lotteryPhase.setEndTicketTime(endTime);
		lotteryPhase.setEndSaleTime(DateUtil.getPhaseTime(endTime, "20:00"));
		lotteryPhase.setHemaiEndTime(DateUtil.getPhaseTime(endTime, "20:00"));
		lotteryPhase.setDrawTime(DateUtil.getPhaseTime(endTime, "21:15"));
		
		updateCreatePhase(lotteryPhase);
		return lotteryPhase;
	}
	
	private Date getPhaseTicketEndTime(Date startSaleTime) {
		int dayweek = DateUtil.getDayWeek(startSaleTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startSaleTime);
		calendar.set(Calendar.HOUR_OF_DAY, 20);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		if(2 == dayweek) {
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 2);
		} else if(4 == dayweek) {
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 2);
		} else if(6 == dayweek) {
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 3);
		} else {
			throw new LotteryException(ErrorCode.Faile,"七乐彩期次开始时间不对");
		}
		return calendar.getTime();
	}
	
	

	@Override
	protected LotteryType getLotteryType() {
		
		return LotteryType.QLC;
	}

}
