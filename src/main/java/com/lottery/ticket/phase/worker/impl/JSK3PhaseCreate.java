package com.lottery.ticket.phase.worker.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;
/**
 * 江苏快3  每天82期 160601001 2016-05-31 22:10:00 2016-06-01 08:40:00
 * 					160531082 2016-05-31 22:00:00 2016-05-31 22:10:00
 * */
@Component
public class JSK3PhaseCreate extends AbstractPhaseCreate {
	
	@Override
	public LotteryPhase creatNextPhase(LotteryPhase lastPhase) {
		String lastPhaseNo = lastPhase.getPhase();
		String numStr = lastPhaseNo.substring(6);
		
		Date endTime = null;
		String phase = null;
		if ("082".equals(numStr)) {
			Date date = DateUtil.parse("yyMMdd", lastPhaseNo.substring(0, 6));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			String newPhasePre = DateUtil.format("yyMMdd", calendar.getTime());
			endTime=DateUtil.parse("yyyyMMdd HH:mm", "20" + newPhasePre+" 08:40");
			phase = newPhasePre + "001";
		} else {
			int num = Integer.parseInt(numStr);
			String newIssueNum = DateUtil.fillZero(num + 1, 3);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(lastPhase.getEndSaleTime());
			calendar.add(Calendar.MINUTE, 10);
			endTime = calendar.getTime();
			phase = lastPhaseNo.substring(0, 6) + newIssueNum;
		}
		LotteryPhase lotteryPhase = new LotteryPhase();
		lotteryPhase.setLotteryType(lastPhase.getLotteryType());
		lotteryPhase.setPhase(phase);
		lotteryPhase.setStartSaleTime(lastPhase.getEndSaleTime());
		lotteryPhase.setEndTicketTime(endTime);
		lotteryPhase.setEndSaleTime(endTime);
		lotteryPhase.setHemaiEndTime(endTime);
		lotteryPhase.setDrawTime(endTime);
		updateCreatePhase(lotteryPhase);
		return lotteryPhase;
	}

	
	
	public static void main(String[] args) {
		LotteryPhase p = new LotteryPhase();
		p.setPhase("160601001");
		p.setLotteryType(LotteryType.JSK3.value);
		p.setStartSaleTime(DateUtil.parse("2016-05-31 22:10:00"));
		p.setEndTicketTime(DateUtil.parse("2016-06-01 08:40:00"));
		for (int i = 0; i < 182; i++) {
			System.out.println(p);
			p = new JSK3PhaseCreate().creatNextPhase(p);
		}
		
	}

	@Override
	protected LotteryType getLotteryType() {
		
		return LotteryType.JSK3;
	}
}
