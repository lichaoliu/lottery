package com.lottery.ticket.phase.worker.impl;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class XJSSCPhaseCreate extends AbstractPhaseCreate {
	/**
	 * 新疆时时彩每天10:00-02:00,每10分钟一期，共96期 期号：yyyymmddxx  2017032801
	 * */
	@Override
	public LotteryPhase creatNextPhase(LotteryPhase last)  {
		String lastPhase = last.getPhase();
		String numStr = lastPhase.substring(8);
		
		String newPhase = null;
		Date endtime = null;
		if ("96".equals(numStr)) {
			String dateStr = lastPhase.substring(0, 8);
			Date date = DateUtil.parse("yyyyMMdd", dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			newPhase = DateUtil.format("yyyyMMdd", calendar.getTime());
			endtime = DateUtil.parse("yyyyMMdd HH:mm",  newPhase + " 10:10");
			newPhase += "01";
		} else {
			int num = Integer.parseInt(numStr);
			String newIssueNum = DateUtil.fillZero(num + 1, 2);
			newPhase = lastPhase.substring(0, 8) + newIssueNum;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(last.getEndSaleTime());
			calendar.add(Calendar.MINUTE, 10);
			endtime = calendar.getTime();
		}
		
		LotteryPhase lotteryPhase = new LotteryPhase();
		lotteryPhase.setLotteryType(last.getLotteryType());
		lotteryPhase.setPhase(newPhase);
		
		lotteryPhase.setStartSaleTime(last.getEndSaleTime());
		lotteryPhase.setEndTicketTime(endtime);
		lotteryPhase.setEndSaleTime(endtime);
		lotteryPhase.setHemaiEndTime(endtime);
		lotteryPhase.setDrawTime(endtime);
		
		updateCreatePhase(lotteryPhase);
		return lotteryPhase;
	}

	@Override
	protected LotteryType getLotteryType() {
		
		return LotteryType.XJSSC;
	}

}
