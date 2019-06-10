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
import com.lottery.core.domain.JczqRace;
import com.lottery.core.service.JczqRaceService;

public abstract class AbstractGetJczqResultFrom implements IGetJczqResult{
	protected final Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private JczqRaceService jczqRaceService;
	
	public void process(String phaseNo, String sn){
		JSONArray ja = getArray(phaseNo, sn);
		if(ja == null){
			return;
		}
		for (int i = 0; i < ja.size(); i++) {
			updateJczqRace(ja.getJSONObject(i));
		}
	}
	
	private void updateJczqRace(JSONObject jo){
		String matchNum = jo.getString("matchNum");
		String resultFrom = jo.getString("resultFrom");
		Integer state = jo.getInt("state");
		JczqRace jr = jczqRaceService.getByMatchNum(matchNum);
		if(jr == null){
			return;
		}
		if(jr.getPrizeStatus().intValue() != PrizeStatus.result_null.value && jr.getPrizeStatus().intValue() != PrizeStatus.result_set.value){
			return;
		}
		if(jr.getStatus().intValue() != RaceStatus.CLOSE.value && jr.getStatus().intValue() != RaceStatus.CANCEL.value){
			return;
		}
		logger.error("更新前竞彩足球赛果matchNum:{},firstHalf:{},secondHalf:{},finalScore:{}", 
				new Object[]{matchNum, jr.getFirstHalf(), jr.getSecondHalf(), jr.getFinalScore()});
		if(state == RaceStatus.CLOSE.value){
			String firstHalf = jo.getString("firstHalf");
			String secondHalf = jo.getString("secondHalf");
			String finalScore = jo.getString("finalScore");
			jr.setFirstHalf(firstHalf); //上半场比分 1:1
			jr.setSecondHalf(secondHalf);//下半场比分 1:1
			jr.setFinalScore(finalScore); //最终比分 1:1
			
		}else if(state == RaceStatus.CANCEL.value){
			jr.setFirstHalf("-1:-1"); 
			jr.setSecondHalf("-1:-1");
			jr.setFinalScore("-1:-1"); 
			jr.setStatus(RaceStatus.CANCEL.value);
		}else {
			return;
		}
		
		jr.setPrizeStatus(PrizeStatus.result_set.value);  //开奖状态   获取到结果   开奖  派奖 
		jr.setStaticDrawStatus(YesNoStatus.yes.value);		//固定奖金开奖状态
		jr.setDynamicDrawStatus(YesNoStatus.yes.value);	//浮动奖金开奖状态
		jr.setResultFrom(resultFrom);
		jr.setUpdateTime(new Date());
		jczqRaceService.update(jr);
		logger.error("更新后竞彩足球赛果 matchNum:{},firstHalf:{},secondHalf:{},finalScore:{}", 
				new Object[]{jr.getMatchNum(), jr.getFirstHalf(), jr.getSecondHalf(), jr.getFinalScore()});
	}
	public abstract JSONArray getArray(String phaseNo, String sn);
	
	
}
