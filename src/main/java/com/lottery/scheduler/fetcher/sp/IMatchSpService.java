package com.lottery.scheduler.fetcher.sp;

import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;

public interface IMatchSpService {
 
	/**
	 * 根据场次查询
	 * @param  matchNum 场次 
	 * */
	public MatchSpDomain get(String matchNum);
	public MatchSpDomain merge(MatchSpDomain spDomain);
}
