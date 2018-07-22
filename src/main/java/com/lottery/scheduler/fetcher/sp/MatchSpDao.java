package com.lottery.scheduler.fetcher.sp;

import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;

public interface MatchSpDao {

	public void save(String key,MatchSpDomain spDomain);
	public MatchSpDomain merge(String key,MatchSpDomain spDomain);
	public MatchSpDomain get(String key,String tablename);
}
