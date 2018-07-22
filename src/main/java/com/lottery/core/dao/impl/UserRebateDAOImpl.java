package com.lottery.core.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.PageBean;
import com.lottery.common.contains.account.RebateType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.UserRebateDAO;
import com.lottery.core.domain.account.UserRebate;
@Repository
public class UserRebateDAOImpl extends AbstractGenericDAO<UserRebate, String> implements UserRebateDAO{

	@Override
	public UserRebate getFix(String userno, Integer lotteryType) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userno", userno);
		map.put("lotteryType", lotteryType);
		map.put("rebateType", RebateType.fix.value);
		return findByUnique(map);
	}

	@Override
    public List<UserRebate> getByType(Integer rebateType,PageBean<UserRebate> page) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("rebateType", rebateType);
	    return findPageByCondition(map, page);
    }

	@Override
	public List<UserRebate> getFloat(String userno, int lotteryType, BigDecimal amount) {
		String whereSql="betAmount>=:betAmount and rebateType=:rebateType and userno=:userno and lotteryType=:lotteryType";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("betAmount", amount.intValue());
		map.put("userno", userno);
		map.put("rebateType", RebateType.float_rebate.value);
		map.put("lotteryType", LotteryType.getPhaseTypeValue(lotteryType));
		return findByCondition(whereSql, map);
	}

	@Override
	public List<UserRebate> getByUsernoAndLottery(String userno, int lotteryType, int rebateType, int max) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("rebateType", rebateType);
		map.put("userno", userno);
		map.put("lotteryType", LotteryType.getPhaseTypeValue(lotteryType));
		String orderByString="order by betAmount desc";
		return findByCondition(max, map, orderByString);
	}

}
