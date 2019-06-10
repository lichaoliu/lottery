package com.lottery.ticket.phase.worker.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;

/**
 * 新疆11选5 16062801 每天97期 10分钟一期 第一期开奖时间2016-06-28 10:00:00
 */
@Component
public class Xj11x5PhaseCreate extends AbstractPhaseCreate {
	@Override
	public LotteryPhase creatNextPhase(LotteryPhase last)  {
		String lastPhase = last.getPhase();
		String numStr = lastPhase.substring(6);
		
		String newPhase = null;
		Date endtime = null;
		if ("97".equals(numStr)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtil.parse("yyyyMMdd", "20"+lastPhase.substring(0, 6)));
			calendar.add(Calendar.DATE, 1);
			newPhase = DateUtil.format("yyMMdd", calendar.getTime());
			endtime = DateUtil.parse("yyyyMMdd HH:mm", "20"+ newPhase + " 10:00");
			newPhase += "01";
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
		return LotteryType.XJ_11X5;
	}
	
}
