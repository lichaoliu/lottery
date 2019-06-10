package com.lottery.ticket.vender.process.huay;

import java.util.Collection;
import java.util.Map;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.impl.AbstractInternalDcService;

public class HuayInternalDcService extends AbstractInternalDcService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
	private GetDcPhaseFromHuay getDcPhaseFromHuay;
	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_HY;
	}
	@Override
	public JSONArray getBjdcSchedule(Map<String, String> requestMap) {
		for(IVenderConfig config:getVenderCofig()){
			JSONArray array=getDcPhaseFromHuay.getDcSchedule(requestMap, config);
			if(array!=null&&array.size()>0){
				return array;
			}
		}
		return null;
	}
	@Override
	public JSONArray getBjdcDrawResult(Map<String, String> requestMap) {
		logger.error("华阳手动 获取单场赛果");
		for(IVenderConfig config:getVenderCofig()){
			JSONArray array=getDcPhaseFromHuay.getDcDrawResult(requestMap, config);
			if(array!=null&&array.size()>0){
				return array;
			}
		}
		return null;
	}
	@Override
	public Collection<MatchSpDomain> getBjdcInstantSp(Map<String, String> requestMap) {
		for(IVenderConfig config:getVenderCofig()){
			Collection<MatchSpDomain> array=getDcPhaseFromHuay.getDcSp(requestMap, config);
			if(array!=null&&array.size()>0){
				return array;
			}
		}
		return null;
	}
	@Override
	public JSONArray getBjdcPhase(Map<String, String> requestMap) {
		for(IVenderConfig config:getVenderCofig()){
			JSONArray array=getDcPhaseFromHuay.getDcPhase(requestMap, config);
			if(array!=null&&array.size()>0){
				return array;
			}
		}
		return null;
	}
}
