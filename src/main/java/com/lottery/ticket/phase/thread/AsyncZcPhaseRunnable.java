package com.lottery.ticket.phase.thread;

import com.lottery.common.contains.LotteryConstant;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.*;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.ZcRace;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.ZcRaceService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

public class AsyncZcPhaseRunnable extends AbstractThreadRunnable{


	protected long invitl=180000;
	@Autowired
	protected LotteryPhaseService lotteryPhaseService;
	@Resource(name = "allPhaseHandlerBinder")
	protected Map<LotteryType, IPhaseHandler> phaseHandle;
	@Autowired
	protected ZcRaceService zcRaceService;

	public long getInvitl() {
		return invitl;
	}
	public void setInvitl(long invitl) {
		this.invitl = invitl;
	}

	@Override
	protected void executeRun() {
		
		while(running){
			try{
				for(int lotteryType:LotteryType.getZcValue()){
					 this.process(lotteryType);
				}
               
			}catch(Exception e){
				logger.error("获取足彩新期出错",e);
			}
			try {
				synchronized(this){
					this.wait(this.invitl);
				}
			} catch (InterruptedException e) {
				
			}
		}
		
		
	}

	private   void process( int lotteryType)throws Exception{

		String responseStr= HTTPUtil.sendPostMsg(LotteryConstant.ZC_PHASE_URL,"lottery_type="+lotteryType);
		try{
			if(StringUtils.isBlank(responseStr)){
				logger.error("彩种:{}返回为空",lotteryType);
				return;
			}
			JSONObject object=JSONObject.fromObject(responseStr);
			String errorcode=object.getString("code");
			if(errorcode.equals("0")){
				JSONArray jsArray=JSONArray.fromObject(object.get("message"));
				if(jsArray!=null){
					for(int i=0;i<jsArray.size();i++){
						JSONObject jsObject=jsArray.getJSONObject(i);
						String phase=jsObject.getString("phase");
						String starttime=jsObject.getString("start_sale_time");
						String endtime=jsObject.getString("end_sale_time");
						this.updatePhase(lotteryType,phase, new Date(),CoreDateUtils.parseDateTime(endtime));
						this.against(lotteryType,phase);
					}
				}
			}
		}catch (Exception e){
			logger.error("新期处理出错",e);
			logger.error("原始信息:{}",responseStr);
		}


	}


	protected  void updatePhase(int lotteryType,String phase,Date startDate,Date endDate) {
		LotteryPhase lotteryPhase = lotteryPhaseService.getByTypeAndPhase(lotteryType, phase);
		if (lotteryPhase == null) {
			lotteryPhase = new LotteryPhase();
			lotteryPhase.setLotteryType(lotteryType);
			lotteryPhase.setPhase(phase);
			lotteryPhase.setCreateTime(new Date());
			lotteryPhase.setStartSaleTime(startDate);
			lotteryPhase.setEndSaleTime(endDate);
			lotteryPhase.setEndTicketTime(endDate);
			lotteryPhase.setHemaiEndTime(endDate);
			lotteryPhase.setDrawTime(endDate);
			lotteryPhase.setPhaseStatus(PhaseStatus.open.getValue());
			lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.getValue());
			lotteryPhase.setPhaseTimeStatus(PhaseTimeStatus.NO_CORRECTION.value);
			lotteryPhase.setTerminalStatus(TerminalStatus.disenable.value);
			lotteryPhase.setForSale(YesNoStatus.yes.getValue());
			lotteryPhase.setForCurrent(YesNoStatus.no.getValue());
			lotteryPhaseService.insert(lotteryPhase);

		}else{
			if (lotteryPhase.getEndSaleTime().getTime() - endDate.getTime() != 0) {
				lotteryPhase.setEndSaleTime(endDate);
				lotteryPhase.setEndTicketTime(endDate);
				lotteryPhase.setHemaiEndTime(endDate);
				lotteryPhase.setDrawTime(endDate);
				lotteryPhaseService.update(lotteryPhase);
				IPhaseHandler phaseHandler=phaseHandle.get(LotteryType.get(lotteryType));
				if (phaseHandler!=null){
					phaseHandler.executeReload();
				}
			}
		}

	}

	protected void against(int lotteryType,String phase)throws Exception{

		String responseStr=HTTPUtil.sendPostMsg(LotteryConstant.ZC_MATCH_URL,"lottery_type="+lotteryType+"&phase="+phase);

		try{
			JSONObject object=JSONObject.fromObject(responseStr);
			String errorcode=object.getString("code");
			if(errorcode.equals("0")){
				JSONArray jsArray=JSONArray.fromObject(object.get("message"));
				if(jsArray!=null){
					for(int i=0;i<jsArray.size();i++){
						JSONObject jsObject=jsArray.getJSONObject(i);
						int matchNum=jsObject.getInt("match_num");
						String matchName=jsObject.getString("simplegbname");
						String matchTime=jsObject.getString("match_time");
						String hometeam=jsObject.getString("home_team");
						String awayteam=jsObject.getString("away_team");
						String averageindex=jsObject.getString("average_index");
						Date matchDate=CoreDateUtils.parseDateTime(matchTime);
						ZcRace race = zcRaceService.getByLotteryPhaseAndNum(lotteryType, phase, matchNum);
						if (race == null) {
							race = new ZcRace();
							race.setAwayTeam(awayteam);
							race.setHomeTeam(hometeam);
							race.setCreateTime(new Date());
							race.setLotteryType(lotteryType);
							race.setPhase(phase);
							race.setMatchName(matchName);
							race.setMatchNum(matchNum);
							race.setMatchDate(matchDate);
							race.setAverageIndex(averageindex);
							race.setMatchId(0);
							zcRaceService.save(race);
						} else {
							race.setAwayTeam(awayteam);
							race.setHomeTeam(hometeam);
							race.setCreateTime(new Date());
							race.setLotteryType(lotteryType);
							race.setPhase(phase);
							race.setMatchName(matchName);
							race.setMatchNum(matchNum);
							race.setMatchDate(matchDate);
							race.setAverageIndex(averageindex);
							zcRaceService.update(race);
						}

					}
				}
			}
		}catch (Exception e){
			logger.error("赛程处理出错",e);
			logger.error("出错数据:{}",responseStr);
		}


	}

}
