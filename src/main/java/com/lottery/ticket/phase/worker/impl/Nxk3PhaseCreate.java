package com.lottery.ticket.phase.worker.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;
/**
 * 宁夏快3新期生成
 * */
@Component
public class Nxk3PhaseCreate extends AbstractPhaseCreate {
	//快三 9:28 到22:28   每天78期
	@Override
	public LotteryPhase creatNextPhase(LotteryPhase lastPhase) {
		String lastPhaseNo = lastPhase.getPhase();
		String numStr = lastPhaseNo.substring(6);
		
		Date endTime = null;
		String phase = null;
		if ("078".equals(numStr)) {
			Date date = DateUtil.parse("yyMMdd", lastPhaseNo.substring(0, 6));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			String newPhasePre = DateUtil.format("yyMMdd", calendar.getTime());
			endTime=DateUtil.parse("yyMMdd HH:mm", newPhasePre+" "+"09:10");
			phase = newPhasePre + "001";
		} else {
			int num = Integer.parseInt(numStr);
			String newIssueNum = DateUtil.fillZero(num + 1, 3);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(lastPhase.getEndTicketTime());
			calendar.add(Calendar.MINUTE, 10);
			endTime = calendar.getTime();
			phase = lastPhaseNo.substring(0, 6) + newIssueNum;
		}
		
		LotteryPhase lotteryPhase = new LotteryPhase();
		lotteryPhase.setLotteryType(lastPhase.getLotteryType());
		lotteryPhase.setPhase(phase);
		lotteryPhase.setStartSaleTime(lastPhase.getEndTicketTime());
		lotteryPhase.setEndTicketTime(endTime);
		lotteryPhase.setEndSaleTime(endTime);
		lotteryPhase.setHemaiEndTime(endTime);
		lotteryPhase.setDrawTime(endTime);
		updateCreatePhase(lotteryPhase);
		return lotteryPhase;
	}

	

	@Override
	protected LotteryType getLotteryType() {
		
		return LotteryType.NXK3;
	}

}
