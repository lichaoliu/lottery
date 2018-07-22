package com.lottery.ticket.phase.worker;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseEncaseStatus;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.PhaseTimeStatus;
import com.lottery.common.contains.lottery.TerminalStatus;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;


public abstract class AbstractPhaseCreate implements IPhaseCreate {
	@Resource(name="lotteryPhaseCreateMap")
	protected Map<LotteryType,IPhaseCreate> map;
	
	protected abstract LotteryType getLotteryType();
	@PostConstruct
	protected  void init(){
		map.put(getLotteryType(), this);
	}
	//修改新期
	protected void updateCreatePhase(LotteryPhase lotteryPhase){
		lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.value);
		lotteryPhase.setPhaseTimeStatus(PhaseTimeStatus.NO_CORRECTION.value);
		lotteryPhase.setTerminalStatus(TerminalStatus.disenable.value);
		lotteryPhase.setForSale(YesNoStatus.no.value);
		lotteryPhase.setForCurrent(YesNoStatus.no.value);
		lotteryPhase.setPhaseStatus(PhaseStatus.unopen.value);
		lotteryPhase.setCreateTime(new Date());
	}
	

	@Override
	public String getPhaseNo(String lastphase, Date endtime) {
		String year = DateUtil.format("yyyy", endtime);
		String phase = null;
		if(lastphase.startsWith(year)) {
			phase = String.valueOf(Long.parseLong(lastphase) + 1);
		} else {
			phase = year + "001";
		}
		return phase;
	}
	
	@Override
	public String getTCPhaseNo(String lastphase, Date endtime) {
		String year = DateUtil.format("yy", endtime);
		String phase = null;
		if(lastphase.startsWith(year)) {
			phase = String.valueOf(Long.parseLong(lastphase) + 1);
		} else {
			phase = year + "001";
		}
		return phase;
	}
}
