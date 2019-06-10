package com.lottery.scheduler.fetcher.jcResult;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.core.domain.JclqRace;
import com.lottery.core.service.JclqRaceService;

public abstract class AbstractGetJclqResultFrom implements IGetJclqResult{
	protected final Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private JclqRaceService jclqRaceService;
	
	public void process(String phaseNo, String sn){
		JSONArray ja = getArray(phaseNo, sn);
		if(ja == null){
			return;
		}
		for (int i = 0; i < ja.size(); i++) {
			updateJclqRace(ja.getJSONObject(i));
		}
	}
	public abstract JSONArray getArray(String phaseNo, String sn);
	
	public void updateJclqRace(JSONObject jo){
		String matchNum = jo.getString("matchNum");
		Integer state = jo.getInt("state");
		String resultFrom = jo.getString("resultFrom");
		
		JclqRace jr = jclqRaceService.getByMatchNum(matchNum);
		if(jr == null){
			return;
		}
		if(jr.getPrizeStatus().intValue() != PrizeStatus.result_null.value && jr.getPrizeStatus().intValue() != PrizeStatus.result_set.value){
			return;
		}
		if(jr.getStatus().intValue() != RaceStatus.CLOSE.value && jr.getStatus().intValue() != RaceStatus.CANCEL.value){
			return;
		}
		//logger.error("更新前竞彩篮球赛果matchNum：{}，finalScore：{}", new Object[]{matchNum, jr.getFinalScore()});
		if(state == RaceStatus.CLOSE.value){
			String finalScore = jo.getString("finalScore");
			jr.setFinalScore(finalScore);      
		} else if(state == RaceStatus.CANCEL.value){
			jr.setFinalScore("-1:-1");
			jr.setStatus(RaceStatus.CANCEL.value);
		} else {
			return;
		}
		jr.setPrizeStatus(PrizeStatus.result_set.value);  //开奖状态   获取到结果   开奖  派奖 
		jr.setStaticDrawStatus(YesNoStatus.yes.value);	//固定奖金开奖状态
		jr.setDynamicDrawStatus(YesNoStatus.yes.value);	//浮动奖金开奖状态
		jr.setUpdateTime(new Date());
		jr.setResultFrom(resultFrom);
		jclqRaceService.update(jr);
		logger.error("更新后竞彩篮球赛果matchNum：{}，finalScore：{}", new Object[]{matchNum, jr.getFinalScore()});
	}
	
	
}
