package com.lottery.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.contains.lottery.LotteryAddPrizeStrategyStatus;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.LotteryAddPrizeStrategyDao;
import com.lottery.core.domain.LotteryAddPrizeStrategy;
import com.lottery.core.domain.LotteryAddPrizeStrategyPK;

@Repository
public class LotteryAddPrizeStrategyDaoImpl extends AbstractGenericDAO<LotteryAddPrizeStrategy, LotteryAddPrizeStrategyPK> implements LotteryAddPrizeStrategyDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	@Override
	public List<LotteryAddPrizeStrategy> findAddPrizeStrategys(int lotterytype,
			String prizelevel, String phase, LotteryAddPrizeStrategyStatus status) {
		String whereString = "id.lotteryType =:lotterytype and id.prizeLevel=:prizelevel and status =:status and id.startPhase<=:startPhase and id.endPhase>=:endPhase";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotterytype", lotterytype);
		map.put("prizelevel", prizelevel);
		map.put("status", status.value);
		map.put("startPhase", phase);
		map.put("endPhase", phase);
		return findByCondition(whereString, map);
	}

	@Override
	public List<LotteryAddPrizeStrategy> findExistAddPrizeStrategys(
			int lotterytype, String prizelevel) {
		String whereString = "id.lotteryType =:lotterytype and id.prizeLevel=:prizelevel and (status =:status1 or status =:status2) ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotterytype", lotterytype);
		map.put("prizelevel", prizelevel);
		map.put("status1", LotteryAddPrizeStrategyStatus.OPEN.value);
		map.put("status2", LotteryAddPrizeStrategyStatus.SAVED.value);
		return findByCondition(whereString, map);
	}

	@Override
	@Transactional
	public LotteryAddPrizeStrategy createLotteryAddPrizeStrategy(
			LotteryAddPrizeStrategy lotteryAddPrizeStrategy) {
		return merge(lotteryAddPrizeStrategy);
	}

	@Override
	public List<LotteryAddPrizeStrategy> findExistAddPrizeStrategys(
			int lotterytype, String prizelevel,
			LotteryAddPrizeStrategyStatus status) {
		StringBuilder whereString = new StringBuilder(" id.lotteryType =:lotterytype ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotterytype", lotterytype);
		if(!StringUtil.isEmpty(prizelevel)) {
			whereString.append(" and id.prizeLevel=:prizelevel ");
			map.put("prizelevel", prizelevel);
		}
		if(status!=null) {
			whereString.append(" and status =:status ");
			map.put("status", status.value);
		}
		return findByCondition(whereString.toString(), map);
	}

	@Override
	public List<LotteryAddPrizeStrategy> findAddPrizeStrategys(int lotterytype,
			String phase, LotteryAddPrizeStrategyStatus status) {
		String whereString = "id.lotteryType =:lotterytype and status =:status and id.startPhase<=:startPhase and id.endPhase>=:endPhase";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotterytype", lotterytype);
		map.put("status", status.value);
		map.put("startPhase", phase);
		map.put("endPhase", phase);
		return findByCondition(whereString, map);
	}



}
