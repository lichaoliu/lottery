package com.lottery.ticket.phase.drawtime.impl;

import com.lottery.common.contains.lottery.LotteryType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.DcType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.service.BeidanService;
import com.lottery.core.service.DcRaceService;
import com.lottery.ticket.phase.drawtime.AbstractPhaseDrawTimer;
import com.lottery.ticket.phase.drawtime.IPhaseDrawTimer;
import com.lottery.ticket.vender.process.datacenter.GetMatchResultFromDateCenter;

public class DcPhaseDrawTimer extends AbstractPhaseDrawTimer implements IPhaseDrawTimer {
    @Autowired
	protected DcRaceService dcRaceService;
	@Autowired
    protected BeidanService beidanService;
	@Autowired
	private GetMatchResultFromDateCenter getMatchResultFromDateCenter;
	@Override
	protected void getResult(LotteryPhase lotteryPhase) {
		try {
			if (lotteryPhase.getLotteryType()== LotteryType.DC_SFGG.value){
				getMatchResultFromDateCenter.updateDcSFGGResult(lotteryPhase.getPhase(), null);
			}else {
				getMatchResultFromDateCenter.updateDcResult(lotteryPhase.getPhase(), null);
			}

        } catch (Exception e) {
	        logger.error("获取单场赛果出错",e);
        }

	}

	@Override
	protected void drawRace(LotteryPhase lotteryPhase) {
		Integer min=dcRaceService.getMaxCloseAndResultMatchid(lotteryPhase.getPhase(), DcType.normal);
		if(StringUtils.isBlank(min+"")){
	    	if(lotteryPhase.getPhaseStatus()==PhaseStatus.close.value){
	    		this.drawTask(lotteryPhase.getLotteryType(), lotteryPhase.getPhase(), null, null);
	    	}
	    	return;
	    }
		
        this.drawTask(lotteryPhase.getLotteryType(), lotteryPhase.getPhase(), null, Long.valueOf(min));
	}

}
