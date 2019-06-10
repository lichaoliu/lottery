package com.lottery.ticket.phase.drawtime;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.handler.PrizeHandler;
import com.lottery.core.service.JingcaiService;
import com.lottery.core.service.LotteryPhaseService;

public abstract class AbstractPhaseDrawTimer extends AbstractThreadRunnable  {

	protected LotteryType lotteryType;
	@Autowired
	protected LotteryPhaseService phaseService;
	@Autowired
	protected PrizeHandler prizeHandler;
	@Autowired
	protected JingcaiService jingcaiService;
	
	protected long interval=10*60*1000;
	protected void executeRun(){
		while (running) {
	        try{
	        	List<Integer> statusList = new ArrayList<Integer>();
	    		statusList.add(PhaseStatus.open.getValue());
	    		statusList.add(PhaseStatus.close.getValue());
	    		List<LotteryPhase> closePhaseList=phaseService.getByLotteryAndStatuses(getLotteryType().value, statusList);
	    		for(LotteryPhase lotteryPhase:closePhaseList){
	    			try{
	    				getResult(lotteryPhase);
		    			//drawRace(lotteryPhase);
	    			}catch(Exception e){
	    				logger.error("彩种{},期号{}算奖失败",getLotteryType().value,lotteryPhase.getPhase(),e);
	    			}
	    			
	    		}
	        }catch(Exception e){
	        	logger.error("彩种算奖失败",e);
	        	
	        }
	        
	    	synchronized (this) {
				try {
					wait(this.getInterval());
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
				}
			}
	        
        }
		
		
		
	}
	protected abstract void getResult(LotteryPhase lotteryPhase);
	protected abstract void drawRace(LotteryPhase lotteryPhase);
	
	protected void drawTask(int lotteryType,String phase,String wincode,Long lastMatchNum){
		prizeHandler.drawExcetor(lotteryType, phase, wincode, lastMatchNum);
	}
	

	public LotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}
	public long getInterval() {
		return interval;
	}
	public void setInterval(long interval) {
		this.interval = interval;
	}
	
	
}
