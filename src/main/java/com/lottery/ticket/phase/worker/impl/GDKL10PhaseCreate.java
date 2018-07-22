package com.lottery.ticket.phase.worker.impl;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by fengqinyun on 16/8/17.
 *
 * 第一期是9:10分开始,到23:00结束,全天84期,期号为yyyymmdd001
 */
@Component
public class GDKL10PhaseCreate extends AbstractPhaseCreate {
    @Override
    protected LotteryType getLotteryType() {
        return LotteryType.GDKL10;
    }

    @Override
    public LotteryPhase creatNextPhase(LotteryPhase lastPhase) {
        String last = lastPhase.getPhase();
        String numStr = last.substring(8);

        String newPhase = null;
        Date endtime = null;
        if ("084".equals(numStr)) {
            String dateStr = last.substring(0, 8);
            Date date = DateUtil.parse("yyyyMMdd", dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            newPhase = DateUtil.format("yyyyMMdd", calendar.getTime());
            endtime = DateUtil.parse("yyyyMMdd HH:mm", newPhase + " " + "09:10");
            newPhase+="001";
        } else {
            int num = Integer.parseInt(numStr);
            String newIssueNum = DateUtil.fillZero(num + 1, 3);
            newPhase = last.substring(0, 8) + newIssueNum;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lastPhase.getEndSaleTime());
            calendar.add(Calendar.MINUTE, 10);
            endtime = calendar.getTime();
        }
        LotteryPhase lotteryPhase = new LotteryPhase();
        lotteryPhase.setLotteryType(lastPhase.getLotteryType());
        lotteryPhase.setPhase(newPhase);
        lotteryPhase.setStartSaleTime(lastPhase.getEndSaleTime());
        lotteryPhase.setEndTicketTime(endtime);
        lotteryPhase.setEndSaleTime(endtime);
        lotteryPhase.setHemaiEndTime(endtime);
        lotteryPhase.setDrawTime(endtime);

        updateCreatePhase(lotteryPhase);
        return lotteryPhase;

    }
}
