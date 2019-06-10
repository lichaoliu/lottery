package com.lottery.ticket.phase.thread;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.core.domain.JczqRace;
import com.lottery.core.service.JczqRaceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AsyncJczqPhaseRunnable extends AbstractJcPhaseRunnable {

    @Autowired
    protected JczqRaceService jczqRaceService;
	protected void extracted() {
		List<Integer> statusList=new ArrayList<Integer>();
		statusList.add(RaceStatus.OPEN.getValue());
		statusList.add(RaceStatus.UNOPEN.getValue());
		List<JczqRace> raceList=jczqRaceService.getByStatus(statusList);
		if(raceList.size()>0){
			for(JczqRace jczqRace:raceList){
				if((new Date().getTime()-(jczqRace.getEndSaleTime().getTime()))>=0){
					logger.error("竞彩足球场次关闭:{}",jczqRace.toString());
					jczqRace.setStatus(RaceStatus.CLOSE.getValue());
					jczqRace.setUpdateTime(new Date());
					jczqRaceService.update(jczqRace);

				}
			}
		}
	}
	@Override
	protected List<LotteryType> getLotteryTypeList() {
		return LotteryType.getJczq();
	}
	@Override
	protected  boolean isNotPrize(String phase){
		List<JczqRace> list=jczqRaceService.getCloseByPhaseAndPrizeStatus(phase, PrizeStatus.result_null.value);
		if(list!=null&&list.size()>0){
			return true;
		}
		return false;
	}
}
