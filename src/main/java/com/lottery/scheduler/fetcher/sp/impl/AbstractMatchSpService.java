package com.lottery.scheduler.fetcher.sp.impl;

import javax.annotation.Resource;

import com.lottery.scheduler.fetcher.sp.IMatchSpService;
import com.lottery.scheduler.fetcher.sp.MatchSpDao;

public abstract class AbstractMatchSpService implements IMatchSpService{

	@Resource(name="redis")
	protected MatchSpDao matchSpDao;
	
}
