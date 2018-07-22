package com.lottery.ticket.phase.thread;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.core.domain.JclqRace;
import com.lottery.core.service.JclqRaceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AsyncJclqPhaseRunnable extends AbstractJcPhaseRunnable {

    @Autowired
    protected JclqRaceService jclqRaceService;
 

	protected void extracted() {
		List<Integer> statusList=new ArrayList<Integer>();
		statusList.add(RaceStatus.OPEN.getValue());
		statusList.add(RaceStatus.UNOPEN.getValue());
		List<JclqRace> raceList=jclqRaceService.getByStatus(statusList);
		if(raceList.size()>0){
			for(JclqRace jclqRace:raceList){
				if((new Date().getTime()-(jclqRace.getEndSaleTime().getTime()))>=0){
					logger.error("竞彩篮球场次关闭:{}",jclqRace.toString());
					jclqRace.setStatus(RaceStatus.CLOSE.getValue());
					jclqRace.setUpdateTime(new Date());
					jclqRaceService.update(jclqRace);
				}
			}
		}
	}


	@Override
	protected List<LotteryType> getLotteryTypeList() {
		return LotteryType.getJclq();
	}
	@Override
	protected  boolean isNotPrize(String phase){
		List<JclqRace> list=jclqRaceService.getCloseByPhaseAndPrizeStatus(phase, PrizeStatus.result_null.value);
		if(list!=null&&list.size()>0){
			return true;
		}
		return false;
	}

}
