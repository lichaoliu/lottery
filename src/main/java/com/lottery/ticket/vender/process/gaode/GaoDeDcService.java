package com.lottery.ticket.vender.process.gaode;

import java.util.Collection;
import java.util.Map;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.JsonUtil;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.impl.AbstractInternalDcService;
import com.lottery.ticket.vender.impl.gaode.GaodeConfig;

public class GaoDeDcService extends AbstractInternalDcService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
	private GetDcPhaseFromGaoDe getDcPhaseFromGaode;
	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_GAODE;
	}
	@Override
	public JSONArray getBjdcSchedule(Map<String, String> requestMap) {
		for(IVenderConfig config:getVenderCofig()){
			GaodeConfig con=(GaodeConfig) config;
			JSONArray array=getDcPhaseFromGaode.getDcSchedule(requestMap, con);
			if(array!=null&&array.size()>0){
				return array;
			}
		}
		return null;
	}
	@Override
	public JSONArray getBjdcDrawResult(Map<String, String> requestMap) {
		for(IVenderConfig config:getVenderCofig()){
			GaodeConfig con=(GaodeConfig) config;
			JSONArray array=getDcPhaseFromGaode.getDcDrawResult(requestMap, con);
			if(array!=null&&array.size()>0){
				return array;
			}
		}
		return null;
	}
	@Override
	public Collection<MatchSpDomain> getBjdcInstantSp(Map<String, String> requestMap) {
		for(IVenderConfig config:getVenderCofig()){
			GaodeConfig con=(GaodeConfig) config;
			Collection<MatchSpDomain> array=getDcPhaseFromGaode.getDcSp(requestMap, con);
			if(array!=null&&array.size()>0){
				return array;
			}
			
		}
		return null;
	}
	@Override
	public JSONArray getBjdcPhase(Map<String, String> requestMap) {
		for(IVenderConfig config:getVenderCofig()){
			JSONArray array=getDcPhaseFromGaode.getDcPhase(requestMap, config);
			if(array!=null&&array.size()>0){
				return array;
			}
		}
		return null;
	}
}
