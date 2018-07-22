package com.lottery.ticket.phase.worker.impl;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 重庆快乐十分
 * 23:52:20秒开售第二天的第一期，10分钟一期，到01:52:20开售第13期，夜间一共13期。第14期从9:52:20秒开售，10分钟一期，一直到第97期
 * 期号 160804062
 * 014期开始02:02:20 结束  10:02:20
 *
 * 原先提前了40秒 现在改成整点
 */
@Component
public class CQKL10PhaseCreate extends AbstractPhaseCreate {
	
	@Override
	public LotteryPhase creatNextPhase(LotteryPhase last)  {
		String lastPhase = last.getPhase();
		String numStr = lastPhase.substring(6);

		String newPhase = null;
		Date endtime = null;
		int num = Integer.parseInt(numStr);
		String newIssueNum = DateUtil.fillZero(num + 1, 3);
		newPhase = lastPhase.substring(0, 6) + newIssueNum;
		if(num==13){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String value = format.format(last.getEndSaleTime()) + " 10:03:00";
			endtime = DateUtil.parse(value);
		}else{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(last.getEndSaleTime());
			calendar.add(Calendar.MINUTE, 10);
			endtime = calendar.getTime();
			if ("097".equals(numStr)){
				SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
				newPhase = format.format(calendar.getTime())+"001";
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
		return LotteryType.CQKL10;
	}
}
