package com.lottery.scheduler.fetcher.sp;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.schedule.AbstractSingletonScheduler;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.scheduler.fetcher.sp.impl.BdMatchSpService;
import com.lottery.ticket.vender.IVenderInternalDcService;

public class GetBeiDanSpTimer extends AbstractSingletonScheduler {
	@Autowired
	private BdMatchSpService bdMatchSpService;
	
	protected IVenderInternalDcService internalDcService;

	@Override
	protected boolean executeRun() {
		try {

		    Collection<MatchSpDomain> bdsps = internalDcService.getBjdcInstantSp(null);
		    if(bdsps==null||bdsps.isEmpty()){
		    	return false;
		    }
		    for (MatchSpDomain matchSpDomain : bdsps) {
		    	bdMatchSpService.merge(matchSpDomain);
			}
		} catch (Exception e) {
			logger.error("定时获取北单sp出错", e);
		}
		return true;
	}

	public IVenderInternalDcService getInternalDcService() {
		return internalDcService;
	}

	public void setInternalDcService(IVenderInternalDcService internalDcService) {
		this.internalDcService = internalDcService;
	}
}
