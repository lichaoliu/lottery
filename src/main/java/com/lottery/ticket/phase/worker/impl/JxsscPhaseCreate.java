package com.lottery.ticket.phase.worker.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;

/**
 * 江西时时彩 09:00-23:15（84期） 20151009008 2015-10-09 10:18:30 开奖 10:21:00
 * 10分钟一期  第一期结期时间 9:10分
 */
@Component
public class JxsscPhaseCreate extends AbstractPhaseCreate {
	
	@Override
	public LotteryPhase creatNextPhase(LotteryPhase last)  {
		String lastPhase = last.getPhase();
		String numStr = lastPhase.substring(8);
		
		String newPhase = null;
		Date endtime = null;
		
		if ("084".equals(numStr)) {
			String dateStr = lastPhase.substring(0, 8);
			Date date = DateUtil.parse("yyyyMMdd", dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			newPhase = DateUtil.format("yyyyMMdd", calendar.getTime());
			
			endtime = DateUtil.parse("yyyyMMdd HH:mm", newPhase + " 09:10");
			newPhase += "001";
		} else {
			int num = Integer.parseInt(numStr);
			String newIssueNum = DateUtil.fillZero(num + 1, 3);
			newPhase = lastPhase.substring(0, 8) + newIssueNum;
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

	
	
	public static void main(String[] args) {
		LotteryPhase lp = new LotteryPhase();
		lp.setLotteryType(LotteryType.JXSSC.value);
		lp.setPhase("20151009010");
		lp.setEndTicketTime(CoreDateUtils.parseDateTime("2015-10-09 10:40:00"));
		for (int i = 0; i < 100; i++) {
			lp = new JxsscPhaseCreate().creatNextPhase(lp);
			System.out.println(lp.getPhase() +" "+ CoreDateUtils.formatDateTime(lp.getEndTicketTime()));
		}
	}

	@Override
	protected LotteryType getLotteryType() {
		
		return LotteryType.JXSSC;
	}
}
