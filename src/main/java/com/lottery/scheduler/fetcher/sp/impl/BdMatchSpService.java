package com.lottery.scheduler.fetcher.sp.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;

@Service
public class BdMatchSpService extends AbstractMatchSpService {
	private final Logger logger= LoggerFactory.getLogger(getClass());
	protected static String TABLE_NAME="bdsp_";
	
	@Override
	public MatchSpDomain get(String matchNum) {
		return matchSpDao.get(matchNum, TABLE_NAME);
	}

	@Override
	public MatchSpDomain merge(MatchSpDomain spDomain) {

		return matchSpDao.merge(TABLE_NAME, spDomain);
	}

}
