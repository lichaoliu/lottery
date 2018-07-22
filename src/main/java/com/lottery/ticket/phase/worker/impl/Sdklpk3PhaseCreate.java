package com.lottery.ticket.phase.worker.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;
/**
 * 08:30:20-23:00:20 每10分钟一期，共88期，每10分钟一期，第一期开售时间23:01:00
 * 期号规则 yyMMddXX
 * */
@Component
public class Sdklpk3PhaseCreate extends AbstractPhaseCreate {
	
	@Override
	public LotteryPhase creatNextPhase(LotteryPhase lastPhase) {
		String lastPhaseNo = lastPhase.getPhase();
		String numStr = lastPhaseNo.substring(6);
		Date startTime=lastPhase.getEndSaleTime();
		
		Date endTime = null;
		String phase = null;
		if ("88".equals(numStr)) {
			Date date = DateUtil.parse("yyMMdd", lastPhaseNo.substring(0, 6));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			String newPhasePre = DateUtil.format("yyMMdd", calendar.getTime());
			endTime=DateUtil.parse("yyyyMMdd HH:mm:ss", "20" + newPhasePre + " 08:30:20");
			phase = newPhasePre + "01";
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(startTime);
			startCalendar.set(Calendar.MILLISECOND, 0);
			startCalendar.set(Calendar.SECOND, 0);
			startCalendar.set(Calendar.MINUTE, 1);
			startTime=startCalendar.getTime();
		} else {
			int num = Integer.parseInt(numStr);
			String newIssueNum = DateUtil.fillZero(num + 1, 2);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(lastPhase.getEndSaleTime());
			calendar.add(Calendar.MINUTE, 10);
			endTime = calendar.getTime();
			phase = lastPhaseNo.substring(0, 6) + newIssueNum;
		}
		LotteryPhase lotteryPhase = new LotteryPhase();
		lotteryPhase.setLotteryType(lastPhase.getLotteryType());
		lotteryPhase.setPhase(phase);
		lotteryPhase.setStartSaleTime(startTime);
		lotteryPhase.setEndTicketTime(endTime);
		lotteryPhase.setEndSaleTime(endTime);
		lotteryPhase.setHemaiEndTime(endTime);
		lotteryPhase.setDrawTime(endTime);
		updateCreatePhase(lotteryPhase);
		return lotteryPhase;
	}

	

	@Override
	protected LotteryType getLotteryType() {
		
		return LotteryType.SD_KLPK3;
	}
}
