package com.lottery.ticket.phase.drawtime.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.service.JclqRaceService;
import com.lottery.scheduler.fetcher.jcResult.datacenter.GetJclqResultFromDateCenter;
import com.lottery.ticket.phase.drawtime.AbstractPhaseDrawTimer;
import com.lottery.ticket.phase.drawtime.IPhaseDrawTimer;

public class JclqPhaseDrawTimer extends AbstractPhaseDrawTimer implements IPhaseDrawTimer {
    @Autowired
	protected JclqRaceService jclqRaceService;
    @Autowired
    private GetJclqResultFromDateCenter dateCenter;
	@Override
	protected void getResult(LotteryPhase lotteryPhase) {
		try {
			dateCenter.process(lotteryPhase.getPhase(), null);
        } catch (Exception e) {
	        logger.error("获取竞彩篮球赛果出错",e);
        }
	}

	@Override
	protected void drawRace(LotteryPhase lotteryPhase) {
	String minMathId=jclqRaceService.getMaxCloseAndResultMatchid();
	if(StringUtils.isBlank(minMathId)){
    	logger.error("竞彩篮球{}期最大场次为空",lotteryPhase.getPhase());
    	if(lotteryPhase.getPhaseStatus()==PhaseStatus.close.value){
    		this.drawTask(lotteryPhase.getLotteryType(), lotteryPhase.getPhase(), null, null);
    	}
    	return;
    }
	 this.drawTask(lotteryPhase.getLotteryType(), lotteryPhase.getPhase(), null, Long.valueOf(minMathId));

	}

}
