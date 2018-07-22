package com.lottery.ticket.phase.worker.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;

@Component
public class HLJKL10PhaseCreate extends AbstractPhaseCreate {
	//8:35开始卖  8:45第一期结期 10:35，10分钟一期，每天84期  14080801
	@Override
	public LotteryPhase creatNextPhase(LotteryPhase last)  {
		String lastPhase = last.getPhase();
		String numStr = lastPhase.substring(6);
		
		String newPhase = null;
		Date endtime = null;
		if ("84".equals(numStr)) {
			String dateStr = lastPhase.substring(0, 6);
			Date date = DateUtil.parse("yyyyMMdd", "20"+dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			newPhase = DateUtil.format("yyMMdd", calendar.getTime());
			endtime = DateUtil.parse("yyyyMMdd HH:mm", "20"+ newPhase + " 08:35");
			newPhase += "01";
		} else {
			int num = Integer.parseInt(numStr);
			String newIssueNum = DateUtil.fillZero(num + 1, 2);
			newPhase = lastPhase.substring(0, 6) + newIssueNum;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(last.getEndTicketTime());
			calendar.add(Calendar.MINUTE, 10);
			endtime = calendar.getTime();
		}
		
		LotteryPhase lotteryPhase = new LotteryPhase();
		lotteryPhase.setLotteryType(last.getLotteryType());
		lotteryPhase.setPhase(newPhase);
		
		lotteryPhase.setStartSaleTime(last.getEndTicketTime());
		lotteryPhase.setEndTicketTime(endtime);
		lotteryPhase.setEndSaleTime(endtime);
		lotteryPhase.setHemaiEndTime(endtime);
		lotteryPhase.setDrawTime(endtime);
		
		updateCreatePhase(lotteryPhase);
		return lotteryPhase;
	}

	@Override
	protected LotteryType getLotteryType() {
		
		return LotteryType.HLJKL10;
	}

}
