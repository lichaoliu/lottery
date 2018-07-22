package com.lottery.ticket.phase.thread;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.TerminalStatus;
import com.lottery.core.domain.LotteryPhase;

import java.util.Date;
import java.util.List;

public abstract class AbstractJcPhaseRunnable extends AbstractTicketPhaseRunnable {
	


	
	@Override
	protected void execute() {
		while(running){
			try{
				List<LotteryPhase> phaseList=phaseService.getByTypeAndForSale(getLotteryType().getValue(), YesNoStatus.yes.getValue());
				int i=0;
				if(phaseList.size()>0){
					for(LotteryPhase lotteryPhase:phaseList){
						Date endTicketTime=lotteryPhase.getEndTicketTime();
						if((new Date().getTime()-endTicketTime.getTime())>0){
							logger.error("竞彩:{},{}期已过期,进行关闭",new Object[]{getLotteryType(),lotteryPhase.getPhase()});
							lotteryPhase.setForSale(YesNoStatus.no.getValue());
							lotteryPhase.setTerminalStatus(TerminalStatus.close.value);
							lotteryPhase.setPhaseStatus(PhaseStatus.close.getValue());
							lotteryPhase.setForCurrent(YesNoStatus.no.getValue());
							phaseService.update(lotteryPhase);
						}else{
							if(i==0){
								//logger.error("将彩种{}，期号{}设置为当前期",new  Object[]{getLotteryType(),lotteryPhase.getPhase()});
                                if (lotteryPhase.getForCurrent()!=YesNoStatus.yes.value)
								phaseService.updateCurrent(lotteryPhase.getLotteryType(), lotteryPhase.getPhase());
								i++;
							}else{
								
							}
						}
					}
				}
				this.extracted();
				this.recyclePhase();
			}catch(Exception e){
				logger.error("竞彩新期,赛程错误",e);
			}
			
			try{
				synchronized(this){
					wait(getInvitl());
				}
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
		}
		


	}
	protected abstract void extracted();
	
	

	
}
