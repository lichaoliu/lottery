package com.lottery.ticket.phase.worker;

import java.util.Date;

import com.lottery.core.domain.LotteryPhase;
/**
 * 新期创建
 * @author fengqinyun
 * */
public interface IPhaseCreate {
    /**
     * 根据上一期的时间创建新的一期
     * @param lashPhase 上一期
     * */
	public LotteryPhase creatNextPhase(LotteryPhase lastPhase);
	
	String getPhaseNo(String lastphase, Date endtime);
	
	String getTCPhaseNo(String lastphase, Date endtime);
}
