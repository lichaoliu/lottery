package com.lottery.ticket.phase.drawtime.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.service.JczqRaceService;
import com.lottery.scheduler.fetcher.jcResult.datacenter.GetJczqResultFromDateCenter;
import com.lottery.ticket.phase.drawtime.AbstractPhaseDrawTimer;
import com.lottery.ticket.phase.drawtime.IPhaseDrawTimer;

public class JczqPhaseDrawTimer extends AbstractPhaseDrawTimer implements IPhaseDrawTimer {
    @Autowired
	protected JczqRaceService jczqRaceService;
    @Autowired
    private GetJczqResultFromDateCenter dateCenter;
	@Override
    protected void getResult(LotteryPhase lotteryPhase) {
	   
		try {
			dateCenter.process(lotteryPhase.getPhase(), null);
        } catch (Exception e) {
	        logger.error("获取竞彩足球赛果出错",e);
        }
    }

	@Override
    protected void drawRace(LotteryPhase lotteryPhase) {
	    String minMachId=jczqRaceService.getMaxCloseAndResultMatchid();
	    if(StringUtils.isBlank(minMachId)){
	    	logger.error("竞彩足球{}期最大场次为空",lotteryPhase.getPhase());
	    	if(lotteryPhase.getPhaseStatus()==PhaseStatus.close.value){
	    		this.drawTask(lotteryPhase.getLotteryType(), lotteryPhase.getPhase(), null, null);
	    	}
	    	return;
	    }
	    Long lastId=Long.valueOf(minMachId);
	   
	    this.drawTask(lotteryPhase.getLotteryType(), lotteryPhase.getPhase(), null, lastId);
    }

	

}
