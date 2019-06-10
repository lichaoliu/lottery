package com.lottery.scheduler.fetcher.jcMatch;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lottery.core.service.LotteryTicketConfigService;
import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.PhaseEncaseStatus;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.PhaseTimeStatus;
import com.lottery.common.contains.lottery.TerminalStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.schedule.AbstractSingletonScheduler;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.scheduler.fetcher.IGetDataFrom;
import com.lottery.ticket.IVenderConfig;
import com.lottery.core.handler.VenderConfigHandler;

public abstract class AbstractGetMatchFrom extends AbstractSingletonScheduler{

	protected final Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
    protected LotteryPhaseService lotteryPhaseService;
	@Autowired
	private VenderConfigHandler venderConfigService;
	private IGetDataFrom  iGetDataFrom;
	private TerminalType terminalType;

	@Autowired
	protected LotteryTicketConfigService lotteryTicketConfigService;
	
	protected boolean updateIssue(int lotteryType,String phase,Date startDate,Date endDate){
		 try{
			 LotteryPhase lotteryPhase=lotteryPhaseService.getByTypeAndPhase(lotteryType, phase);
			 if(lotteryPhase==null){
				 lotteryPhase=new LotteryPhase();
				 lotteryPhase.setLotteryType(lotteryType);
				 lotteryPhase.setPhase(phase);
				 lotteryPhase.setStartSaleTime(startDate);
				 lotteryPhase.setEndSaleTime(endDate);
				 lotteryPhase.setEndTicketTime(endDate);
				 lotteryPhase.setHemaiEndTime(endDate);
				 lotteryPhase.setPhaseStatus(PhaseStatus.open.value);
				 lotteryPhase.setForSale(YesNoStatus.yes.value);
				 lotteryPhase.setPhaseTimeStatus(PhaseTimeStatus.CORRECTION.value);
				 lotteryPhase.setTerminalStatus(TerminalStatus.enable.value);
				 lotteryPhase.setDrawTime(endDate);
				 lotteryPhase.setSwitchTime(new Date());
				 lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.getValue());
				 lotteryPhase.setForCurrent(YesNoStatus.no.getValue());
				 lotteryPhase.setCreateTime(new Date());
				 lotteryPhaseService.save(lotteryPhase);
			 }else{
				 if(lotteryPhase.getEndSaleTime().getTime()-endDate.getTime()!=0){
					 lotteryPhase.setEndSaleTime(endDate);
					 lotteryPhase.setEndTicketTime(endDate);
					 lotteryPhase.setHemaiEndTime(endDate);
					 lotteryPhase.setDrawTime(endDate);
					 lotteryPhase.setUpdateTime(new Date());
					 lotteryPhaseService.update(lotteryPhase);
				 }
				 if(lotteryPhase.getPhaseStatus()!=PhaseStatus.open.value){
					 lotteryPhase.setPhaseStatus(PhaseStatus.open.value);
					 lotteryPhase.setForSale(YesNoStatus.yes.value);
					 lotteryPhase.setPhaseTimeStatus(PhaseTimeStatus.CORRECTION.value);
					 lotteryPhase.setTerminalStatus(TerminalStatus.enable.value);
					 lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.getValue());
					 lotteryPhase.setForCurrent(YesNoStatus.no.getValue());
					 lotteryPhase.setEndSaleTime(endDate);
					 lotteryPhase.setEndTicketTime(endDate);
					 lotteryPhase.setHemaiEndTime(endDate);
					 lotteryPhase.setDrawTime(endDate);
					 lotteryPhase.setUpdateTime(new Date());
					 lotteryPhaseService.update(lotteryPhase);
				 }
			 }
			 return true;
		 }catch(Exception e){
			 logger.error("修改新期出错", e);
			 return false;
		 }
	 }
	 
	 public JSONArray getArray(Map<String, String> reqMap){
		 JSONArray array = null;
			try {
				IVenderConfig config = null;
				if(terminalType != null){
					List<IVenderConfig> list=venderConfigService.getAllByTerminalType(terminalType);
					if(list!=null&&list.size()>0){
						config = list.get(0);
					}
				}
				array=iGetDataFrom.perfrom(reqMap, config);
			} catch (Exception e) {
				logger.error("获取对阵出错",e);
			} 
			return array;
	 }

	public IGetDataFrom getiGetDataFrom() {
		return iGetDataFrom;
	}

	public void setiGetDataFrom(IGetDataFrom iGetDataFrom) {
		this.iGetDataFrom = iGetDataFrom;
	}

	public TerminalType getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(TerminalType terminalType) {
		this.terminalType = terminalType;
	}
}
