package com.lottery.ticket.phase.worker.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;

/**
 * 重庆时时彩 120期 20140914023 2014-09-14 01:50:00
 */
@Component
public class CqsscPhaseCreate extends AbstractPhaseCreate {
	
	@Override
	public LotteryPhase creatNextPhase(LotteryPhase last)  {
		String lastPhase = last.getPhase();
		String numStr = lastPhase.substring(8);
		
		String newPhase = null;
		Date endtime = null;
		
		if ("120".equals(numStr)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String dateStr = lastPhase.substring(0, 8);
			Date date = null;
			try {
				date = format.parse(dateStr);
			} catch (Exception e) {
				
			}
			if(date==null){
				return null;
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			newPhase = format.format(calendar.getTime());
			newPhase = newPhase + "001";
			calendar = Calendar.getInstance();
			calendar.setTime(last.getEndSaleTime());
			calendar.add(Calendar.MINUTE, 5);
			endtime = calendar.getTime();
		} else {

			int num = Integer.parseInt(numStr);
			String newIssueNum = DateUtil.fillZero(num + 1, 3);
			newPhase = lastPhase.substring(0, 8) + newIssueNum;
			Calendar calendar = Calendar.getInstance();
			if (num >= 1 && num < 23) {
				calendar.setTime(last.getEndSaleTime());
				calendar.add(Calendar.MINUTE, 5);
				endtime = calendar.getTime();
			} else if (num == 23) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String value = format.format(last.getEndSaleTime()) + " 10:00:00";
				endtime = DateUtil.parse(value);
			} else if (num > 23 && num <= 95) {
				calendar.setTime(last.getEndSaleTime());
				calendar.add(Calendar.MINUTE, 10);
				endtime = calendar.getTime();
			} else {
				calendar.setTime(last.getEndSaleTime());
				calendar.add(Calendar.MINUTE, 5);
				endtime =  calendar.getTime();
			}
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
		return LotteryType.CQSSC;
	}
}
