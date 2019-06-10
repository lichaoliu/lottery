package com.lottery.core.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.LotteryTerminalConfigDAO;
import com.lottery.core.domain.terminal.LotteryTerminalConfig;

@Repository
public class LotteryTerminalConfigDAOImpl extends AbstractGenericDAO<LotteryTerminalConfig, Long> implements LotteryTerminalConfigDAO{

	@Override
	public LotteryTerminalConfig get(int lotteryType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryType", lotteryType);
		return findByUnique(map);
	}

	@Override
	public Map<LotteryType, LotteryTerminalConfig> mget(List<LotteryType> lotteryTypeList) {
		if (lotteryTypeList == null || lotteryTypeList.size() == 0) {
			return null;
		}
		String whereSql = "lotteryType in (:lotteryType)";
		List<Integer> list = new ArrayList<Integer>();
		for (LotteryType lotteryType : lotteryTypeList) {
			list.add(lotteryType.value);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryType", list);
		List<LotteryTerminalConfig> lotteryTerminalConfigList = findByCondition(whereSql, map);
		if (lotteryTerminalConfigList == null || lotteryTerminalConfigList.isEmpty()) {

			return null;
		}

		Map<LotteryType, LotteryTerminalConfig> lotteryTerminalConfigMap = new HashMap<LotteryType, LotteryTerminalConfig>();
		for (LotteryTerminalConfig lotteryTerminalConfig : lotteryTerminalConfigList) {
			lotteryTerminalConfigMap.put(LotteryType.getLotteryType(lotteryTerminalConfig.getLotteryType()), lotteryTerminalConfig);
		}
		return lotteryTerminalConfigMap;

	}

}
