package com.lottery.scheduler.fetcher.sp;

import java.util.List;

import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.ticket.IVenderConfig;

public interface IGetSpData {
	/**
	 * 竞彩足球
	 * @param url
	 * @return
	 */
	public List<MatchSpDomain> getJczqStatic(IVenderConfig config);
	
	/**
	 * 竞彩篮球
	 * @param url
	 * @return
	 */
	public List<MatchSpDomain> getJclqStatic(IVenderConfig config);
	

}
