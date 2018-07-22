package com.lottery.ticket.phase.worker.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;

@Component
public class Sd11x5PhaseCreate extends AbstractPhaseCreate {
	//08:35:20~22:55:20，10分钟一期，每天87期,第一期开售 22:56:00
	//期号14042962
	@Override
	public LotteryPhase creatNextPhase(LotteryPhase last)  {
		String lastPhase = last.getPhase();
		String numStr = lastPhase.substring(6);
		Date startTime=last.getEndSaleTime();
		
		String newPhase = null;
		Date endtime = null;
		if ("87".equals(numStr)) {
			String dateStr = lastPhase.substring(0, 6);
			Date date = DateUtil.parse("yyyyMMdd", "20"+dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			newPhase = DateUtil.format("yyMMdd", calendar.getTime());
			endtime = DateUtil.parse("yyyyMMdd HH:mm:ss", "20"+ newPhase + " 08:35:20");
			newPhase += "01";
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(startTime);
			startCalendar.set(Calendar.MILLISECOND, 0);
			startCalendar.set(Calendar.SECOND, 0);
			startCalendar.set(Calendar.MINUTE, 56);
			startTime=startCalendar.getTime();

		} else {
			int num = Integer.parseInt(numStr);
			String newIssueNum = DateUtil.fillZero(num + 1, 2);
			newPhase = lastPhase.substring(0, 6) + newIssueNum;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(last.getEndSaleTime());
			calendar.add(Calendar.MINUTE, 10);
			endtime = calendar.getTime();
		}
		
		LotteryPhase lotteryPhase = new LotteryPhase();
		lotteryPhase.setLotteryType(last.getLotteryType());
		lotteryPhase.setPhase(newPhase);
		
		lotteryPhase.setStartSaleTime(startTime);
		lotteryPhase.setEndTicketTime(endtime);
		lotteryPhase.setEndSaleTime(endtime);
		lotteryPhase.setHemaiEndTime(endtime);
		lotteryPhase.setDrawTime(endtime);
		
		updateCreatePhase(lotteryPhase);
		return lotteryPhase;
	}

	
	@Override
	protected LotteryType getLotteryType() {
		
		return LotteryType.SD_11X5;
	}

}
