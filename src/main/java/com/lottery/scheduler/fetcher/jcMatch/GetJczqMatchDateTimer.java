package com.lottery.scheduler.fetcher.jcMatch;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.lottery.JczqUtil;
import com.lottery.core.domain.JczqRace;
import com.lottery.core.domain.ticket.LotteryTicketConfig;

import com.lottery.core.service.JczqRaceService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class GetJczqMatchDateTimer extends AbstractGetMatchFrom {
	@Autowired
	private JczqRaceService jczqRaceService;
	@Override
	protected boolean executeRun() {
		try{
			JSONArray array = getArray(null);
			if(array!=null&&array.size()>0){
				for(int i=0;i<array.size();i++){
					JSONObject object=array.getJSONObject(i);
					process(object);
				}
				return true;
			}else{
				return false;
			}
		}catch (Exception e){
			logger.error("执行出错",e);
			return false;
		}

		
	}

	private void process(JSONObject jsonObject){
		try{
			String matchNum=jsonObject.getString("matchNum");
			String matchName=jsonObject.getString("matchName");
			Date matchDate=CoreDateUtils.parseDateTime(jsonObject.getString("matchDate"));
			Date endSaleTimej=CoreDateUtils.parseDateTime(jsonObject.getString("endSaleTime"));
			String phaseNo=jsonObject.getString("phaseNo");

			String officialNum=jsonObject.getString("officialNum");
			String officialWeekDay=jsonObject.getString("officialWeekDay");
			String homeTeam=jsonObject.getString("homeTeam");
			String awayTeam=jsonObject.getString("awayTeam");
			String officialId=jsonObject.getString("officialId");
			String homeTeamForShort=jsonObject.getString("homeTeamForShort");         //赛事
			String awayTeamForShort=jsonObject.getString("awayTeamForShort");      	//主队
			String matchNameForShort=jsonObject.getString("matchNameForShort");   
			
			String handicap = jsonObject.getString("handicap");
			int operation = jsonObject.getInt("operation");
			int staticSaleSpfStatus=jsonObject.getInt("staticSaleSpfStatus");
			int staticSaleSpfWrqStatus=jsonObject.getInt("staticSaleSpfWrqStatus");
			int staticSaleBfStatus=jsonObject.getInt("staticSaleBfStatus");
			int staticSaleJqsStatus=jsonObject.getInt("staticSaleJqsStatus");
			int staticSaleBqcStatus=jsonObject.getInt("staticSaleBqcStatus");
			
			int dgStaticSaleSpfStatus=jsonObject.getInt("dgStaticSaleSpfStatus");//单关固定奖金让球胜平负玩法销售状态
			int dgStaticSaleBfStatus=jsonObject.getInt("dgStaticSaleBfStatus");//单关固定奖金全场比分玩法销售状态
			int dgStaticSaleJqsStatus=jsonObject.getInt("dgStaticSaleJqsStatus");//单关固定奖金进球总数玩法销售状态
			int dgStaticSaleBqcStatus=jsonObject.getInt("dgStaticSaleBqcStatus");//单关固定奖金半全场胜平负玩法销售状态
			int dgStaticSaleSpfWrqStatus=jsonObject.getInt("dgStaticSaleSpfWrqStatus");//单关固定奖金胜平负玩法销售状态
			

			long endSaleForward = 0;
			LotteryTicketConfig config = lotteryTicketConfigService.get(LotteryType.JCZQ_RQ_SPF);
			if (config != null && config.getEndSaleForward() != null) {
				endSaleForward = config.getEndSaleForward();
			}
			Date officialDate= JczqUtil.getOfficialEndSaleTimeByMatchDate(endSaleTimej);
			Date endSaleTime=JczqUtil.getEndSaleTimeByMatchDate(endSaleTimej,endSaleForward);

			JczqRace jczqRace=jczqRaceService.getByMatchNum(matchNum);
			if(jczqRace == null){
				jczqRace = new JczqRace();
				jczqRace.setCreateTime(new Date());
				jczqRace.setPrizeStatus(PrizeStatus.result_null.value);
				jczqRace.setOfficialWeekDay(officialWeekDay);
				jczqRace.setMatchNum(matchNum);
				jczqRace.setMatchName(matchName);
				jczqRace.setHomeTeam(homeTeam);
				jczqRace.setAwayTeam(awayTeam);
				jczqRace.setMatchShortName(matchNameForShort);
				jczqRace.setHomeTeamShort(homeTeamForShort);
				jczqRace.setAwayTeamShort(awayTeamForShort);
				jczqRace.setOfficialId(officialId);
				jczqRace.setMatchDate(matchDate);
				jczqRace.setEndSaleTime(endSaleTime);
				jczqRace.setOfficialDate(officialDate);
				jczqRace.setPhase(phaseNo);
				jczqRace.setOfficialNum(officialNum);
				jczqRace.setStatus(operation);
				jczqRace.setHandicap(handicap);
				
				jczqRace.setStaticSaleSpfStatus(staticSaleSpfStatus);
				jczqRace.setStaticSaleSpfWrqStatus(staticSaleSpfWrqStatus);
				jczqRace.setStaticSaleBfStatus(staticSaleBfStatus);
				jczqRace.setStaticSaleJqsStatus(staticSaleJqsStatus);
				jczqRace.setStaticSaleBqcStatus(staticSaleBqcStatus);
				
				jczqRace.setDgStaticSaleBfStatus(dgStaticSaleBfStatus);
				jczqRace.setDgStaticSaleBqcStatus(dgStaticSaleBqcStatus);
				jczqRace.setDgStaticSaleJqsStatus(dgStaticSaleJqsStatus);
				jczqRace.setDgStaticSaleSpfStatus(dgStaticSaleSpfStatus);
				jczqRace.setDgStaticSaleSpfWrqStatus(dgStaticSaleSpfWrqStatus);
				jczqRaceService.save(jczqRace);
			}else{
				jczqRace.setOfficialDate(officialDate);

				if(jczqRace.getStatus()== RaceStatus.PAUSE.value){
					logger.error("请注意,场次:{},销售状态处于暂停状态",matchNum);
					return;
				}

				if(System.currentTimeMillis()-endSaleTime.getTime()>0){
					logger.error("场次:{}已过期",matchNum);
					return;
				}

				jczqRace.setOfficialWeekDay(officialWeekDay);
				jczqRace.setMatchNum(matchNum);
				jczqRace.setMatchName(matchName);
				jczqRace.setHomeTeam(homeTeam);
				jczqRace.setAwayTeam(awayTeam);
				jczqRace.setMatchShortName(matchNameForShort);
				jczqRace.setHomeTeamShort(homeTeamForShort);
				jczqRace.setAwayTeamShort(awayTeamForShort);
				jczqRace.setMatchDate(matchDate);
				jczqRace.setEndSaleTime(endSaleTime);
				jczqRace.setOfficialDate(officialDate);
				jczqRace.setPhase(phaseNo);
				jczqRace.setOfficialNum(officialNum);
				jczqRace.setHandicap(handicap);
				jczqRace.setOfficialId(officialId);
//				jczqRace.setStaticSaleSpfStatus(staticSaleSpfStatus);
//				jczqRace.setStaticSaleSpfWrqStatus(staticSaleSpfWrqStatus);
//				jczqRace.setStaticSaleBfStatus(staticSaleBfStatus);
//				jczqRace.setStaticSaleJqsStatus(staticSaleJqsStatus);
//				jczqRace.setStaticSaleBqcStatus(staticSaleBqcStatus);
//
//				jczqRace.setDgStaticSaleBfStatus(dgStaticSaleBfStatus);
//				jczqRace.setDgStaticSaleBqcStatus(dgStaticSaleBqcStatus);
//				jczqRace.setDgStaticSaleJqsStatus(dgStaticSaleJqsStatus);
//				jczqRace.setDgStaticSaleSpfStatus(dgStaticSaleSpfStatus);
//				jczqRace.setDgStaticSaleSpfWrqStatus(dgStaticSaleSpfWrqStatus);

				if(StringUtils.isNotBlank(handicap)){
					if(!handicap.equals(jczqRace.getHandicap())){
						logger.error("竞彩足球:{},让球发生改变",matchNum);
						jczqRace.setHandicap(handicap);

					}
				}
				if(jczqRace.getEndSaleTime().getTime()!=endSaleTime.getTime()){
					logger.error("竞彩足球:{},比赛时间发生改变",matchNum);
					jczqRace.setEndSaleTime(endSaleTime);
					jczqRace.setOfficialDate(officialDate);
				}
				jczqRace.setUpdateTime(new Date());
				jczqRace.setStatus(operation);
				jczqRaceService.update(jczqRace);

			}
			updateIssue(LotteryType.JCZQ_RQ_SPF.getValue(), phaseNo, new Date(), endSaleTime);
		}catch(Exception e){
			logger.error("处理竞彩足球赛程出错",e);
		}
	}
}
