package com.lottery.scheduler.fetcher.jcMatch;


import java.util.Date;


import com.lottery.core.domain.ticket.LotteryTicketConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.lottery.JclqUtil;
import com.lottery.core.domain.JclqRace;

import com.lottery.core.service.JclqRaceService;

public class GetJclqMatchDateTimer extends AbstractGetMatchFrom {
	@Autowired
	private JclqRaceService jclqRaceService;
	@Override
	protected boolean executeRun() {
		try {
			JSONArray array=getArray(null);
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
			String matchNum=jsonObject.getString("matchNum"); //比赛编码,年月日+官方赛事编码 20110314301
			Date matchDate=CoreDateUtils.parseDateTime(jsonObject.getString("matchDate"));//比赛日期
			Date endSaleTime=CoreDateUtils.parseDateTime(jsonObject.getString("endSaleTime"));//停止销售时间
			String phaseNo=jsonObject.getString("phaseNo"); 	//彩期号

			String officialNum=jsonObject.getString("officialNum");      //官方赛事编码,301
			String officialWeekDay=jsonObject.getString("officialWeekDay");  //官方赛事星期数，for view
			String matchName=jsonObject.getString("matchName");         //赛事
			String homeTeam=jsonObject.getString("homeTeam");      	//主队
			String awayTeam=jsonObject.getString("awayTeam");       	//客队
			String officialId=jsonObject.getString("officialId");
			String homeTeamForShort=jsonObject.getString("homeTeamForShort");         //赛事
			String awayTeamForShort=jsonObject.getString("awayTeamForShort");      	//主队
			String matchNameForShort=jsonObject.getString("matchNameForShort");       	//客队
			
			Integer operation = jsonObject.getInt("operation");
			String staticHandicap=jsonObject.getString("staticHandicap");      //固定奖金投注当前让分
			String staticPresetScore=jsonObject.getString("staticPresetScore");  //固定奖金投注当前预设总分
			
			Integer staticSaleSfStatus=jsonObject.getInt("staticSaleSfStatus");	//固定奖金胜负玩法销售状态
			Integer staticSaleRfsfStatus=jsonObject.getInt("staticSaleRfsfStatus");//固定奖金让分胜负玩法销售状态
			Integer staticSaleSfcStatus=jsonObject.getInt("staticSaleSfcStatus");	//固定奖金胜分差玩法销售状态
			Integer staticSaleDxfStatus=jsonObject.getInt("staticSaleDxfStatus");//固定奖金大小分玩法销售状态
			
			Integer dgStaticSaleSfStatus=jsonObject.getInt("dgStaticSaleSfStatus");	//单关固定奖金胜负玩法销售状态
			Integer dgStaticSaleRfsfStatus=jsonObject.getInt("dgStaticSaleRfsfStatus");//单关固定奖金让分胜负玩法销售状态
			Integer dgStaticSaleSfcStatus=jsonObject.getInt("dgStaticSaleSfcStatus");//浮动奖金胜分差玩法销售状态
			Integer dgStaticSaleDxfStatus=jsonObject.getInt("dgStaticSaleDxfStatus");//单关固定固定奖金大小分玩法销售状态
			


			long endSaleForward = 0;
			LotteryTicketConfig config = lotteryTicketConfigService.get(LotteryType.JCLQ_SF);
			if (config != null && config.getEndSaleForward() != null) {
				endSaleForward = config.getEndSaleForward();
			}

			Date officialDate=JclqUtil.getOfficialEndSaleTimeByMatchDate(endSaleTime);

			Date endTime = JclqUtil.getEndSaleTimeByMatchDate(endSaleTime,endSaleForward);


			JclqRace jclqRace=jclqRaceService.getByMatchNum(matchNum);
			if(jclqRace==null){
				jclqRace = new JclqRace();

				jclqRace.setStatus(operation);	//比赛状态   未开启  开启  关闭  取消
				jclqRace.setPrizeStatus(PrizeStatus.result_null.value);

				jclqRace.setCreateTime(new Date());

				jclqRace.setMatchNum(matchNum);
				jclqRace.setPhase(phaseNo); 	//彩期号
				jclqRace.setOfficialDate(officialDate);        	//官方公布比赛场次的日期
				jclqRace.setOfficialNum(officialNum);      //官方赛事编码,301
				jclqRace.setOfficialWeekDay(officialWeekDay);  //官方赛事星期数，for view
				jclqRace.setEndSaleTime(endTime);      	//停止销售时间
				jclqRace.setMatchName(matchName);         //赛事
				jclqRace.setMatchDate(matchDate);  		//比赛日期
				jclqRace.setHomeTeam(homeTeam);      	//主队
				jclqRace.setAwayTeam(awayTeam);       	//客队
				jclqRace.setOfficialId(officialId);
				jclqRace.setMatchShortName(matchNameForShort);
				jclqRace.setHomeTeamShort(homeTeamForShort);
				jclqRace.setAwayTeamShort(awayTeamForShort);
				
				jclqRace.setStaticHandicap(staticHandicap);      //固定奖金投注当前让分
				jclqRace.setStaticPresetScore(staticPresetScore);  //固定奖金投注当前预设总分

				jclqRace.setStaticSaleSfStatus(staticSaleSfStatus);
				jclqRace.setStaticSaleRfsfStatus(staticSaleRfsfStatus);
				jclqRace.setStaticSaleSfcStatus(staticSaleSfcStatus);
				jclqRace.setStaticSaleDxfStatus(staticSaleDxfStatus);
				jclqRace.setDgStaticSaleDxfStatus(dgStaticSaleDxfStatus);
				jclqRace.setDgStaticSaleRfsfStatus(dgStaticSaleRfsfStatus);
				jclqRace.setDgStaticSaleSfcStatus(dgStaticSaleSfcStatus);
				jclqRace.setDgStaticSaleSfStatus(dgStaticSaleSfStatus);

				jclqRaceService.save(jclqRace);
			}else{

				if(jclqRace.getStatus()== RaceStatus.PAUSE.value){
					logger.error("请注意,场次:{},销售状态处于暂停状态",matchNum);
					return;
				}

				if(System.currentTimeMillis()-endSaleTime.getTime()>0){
					logger.error("场次:{}已过期",matchNum);
					return;
				}
				jclqRace.setStatus(operation);
				jclqRace.setOfficialDate(officialDate);
				jclqRace.setMatchNum(matchNum);
				jclqRace.setPhase(phaseNo); 	//彩期号
				jclqRace.setOfficialDate(officialDate);        	//官方公布比赛场次的日期
				jclqRace.setOfficialNum(officialNum);      //官方赛事编码,301
				jclqRace.setOfficialWeekDay(officialWeekDay);  //官方赛事星期数，for view
				jclqRace.setEndSaleTime(endTime);      	//停止销售时间
				jclqRace.setMatchName(matchName);         //赛事
				jclqRace.setMatchDate(matchDate);  		//比赛日期
				jclqRace.setHomeTeam(homeTeam);      	//主队
				jclqRace.setAwayTeam(awayTeam);       	//客队
				jclqRace.setMatchShortName(matchNameForShort);
				jclqRace.setHomeTeamShort(homeTeamForShort);
				jclqRace.setAwayTeamShort(awayTeamForShort);
				jclqRace.setOfficialId(officialId);
				jclqRace.setStaticHandicap(staticHandicap);      //固定奖金投注当前让分
				jclqRace.setStaticPresetScore(staticPresetScore);  //固定奖金投注当前预设总分

//				jclqRace.setStaticSaleSfStatus(staticSaleSfStatus);
//				jclqRace.setStaticSaleRfsfStatus(staticSaleRfsfStatus);
//				jclqRace.setStaticSaleSfcStatus(staticSaleSfcStatus);
//				jclqRace.setStaticSaleDxfStatus(staticSaleDxfStatus);
//				jclqRace.setDgStaticSaleDxfStatus(dgStaticSaleDxfStatus);
//				jclqRace.setDgStaticSaleRfsfStatus(dgStaticSaleRfsfStatus);
//				jclqRace.setDgStaticSaleSfcStatus(dgStaticSaleSfcStatus);
//				jclqRace.setDgStaticSaleSfStatus(dgStaticSaleSfStatus);
				jclqRaceService.update(jclqRace);
			}
			 updateIssue(LotteryType.JCLQ_SF.getValue(), phaseNo, new Date(), endTime);
		}catch(Exception e){
			logger.error("处理竞彩篮球赛程出错",e);
		}
	}
}
