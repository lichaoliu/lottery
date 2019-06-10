package com.lottery.scheduler.fetcher.sp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.schedule.AbstractSingletonScheduler;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.scheduler.fetcher.sp.impl.JclqMatchSpService;
import com.lottery.scheduler.fetcher.sp.impl.JczqMatchSpService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.core.handler.VenderConfigHandler;

public class GetJcSpTimer extends AbstractSingletonScheduler {
	@Autowired
	private JczqMatchSpService jczqMatchSpService;
	@Autowired
	private JclqMatchSpService jclqMatchSpService;
	@Autowired
	private VenderConfigHandler venderConfigService;
	
	private IGetSpData iGetSpData;
	private TerminalType terminalType;

	@Override
	protected boolean executeRun() {
		try {
			IVenderConfig config = null;
			if(terminalType != null){
				List<IVenderConfig> list=venderConfigService.getAllByTerminalType(terminalType);
				if(list.size()<1){
					return true;
				}
				config = list.get(0);
			}
			
		    List<MatchSpDomain> jss = iGetSpData.getJczqStatic(config);
			if(jss==null){
				return false;
			}
		    for (MatchSpDomain matchSpDomain : jss) {
		    	jczqMatchSpService.merge(matchSpDomain);
			}
		    List<MatchSpDomain> lss = iGetSpData.getJclqStatic(config);
		    for (MatchSpDomain matchSpDomain : lss) {
		    	jclqMatchSpService.merge(matchSpDomain);
			}
		} catch (Exception e) {
			logger.error("定时获取竞彩sp出错", e);
			return false;
		}
		return true;
	}

	public TerminalType getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(TerminalType terminalType) {
		this.terminalType = terminalType;
	}
	public IGetSpData getiGetSpData() {
		return iGetSpData;
	}
	public void setiGetSpData(IGetSpData iGetSpData) {
		this.iGetSpData = iGetSpData;
	}
}
