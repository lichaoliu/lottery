package com.lottery.core.dao;

import java.util.List;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.agency.LotteryAgencyPointLocation;

public interface LotteryAgencyPointLocationDao extends IGenericDAO<LotteryAgencyPointLocation, Long>{
	
	public List<LotteryAgencyPointLocation> findAgencyPointsByAgencyNo(String agencyNo);
	
	public LotteryAgencyPointLocation findByAgencyNoAndLotteryType(String agencyNo, Integer lotteryType);
	
}
