package com.lottery.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.ZcRaceDAO;
import com.lottery.core.domain.ZcRace;

@Repository
public class ZcRaceDAOImpl extends AbstractGenericDAO<ZcRace, Long> implements ZcRaceDAO {

	@Override
	public ZcRace getByLotteryPhaseAndNum(int lotteryType, String phase, int matchNum) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		whereMap.put("matchNum", matchNum);
		return findByUnique(whereMap);
	}

	@Override
	public List<ZcRace> getByLotteryTypeAndPhase(int lotteryType, String phase) {
		Map<String,Object> whereMap=new HashMap<String, Object>();
    	whereMap.put("lotteryType", lotteryType);
    	whereMap.put("phase", phase);
    	return findByCondition(whereMap);
	}

}
