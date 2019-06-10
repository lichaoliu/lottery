package com.lottery.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.LotteryAgencyPointLocationDao;
import com.lottery.core.domain.agency.LotteryAgencyPointLocation;

@Repository
public class LotteryAgencyPointLocationDaoImpl  extends AbstractGenericDAO<LotteryAgencyPointLocation, Long> implements LotteryAgencyPointLocationDao{

	@Override
	public List<LotteryAgencyPointLocation> findAgencyPointsByAgencyNo(String agencyNo){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("agencyNo", agencyNo);
		return findByCondition(param);
	}

	@Override
	public LotteryAgencyPointLocation findByAgencyNoAndLotteryType(String agencyNo, Integer lotteryType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("agencyNo", agencyNo);
		param.put("lotteryType", lotteryType);
		return findByUnique(param);
	}
}
