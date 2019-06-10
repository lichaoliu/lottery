package com.lottery.core.dao.impl.give;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.give.UserRechargeGiveDAO;
import com.lottery.core.domain.give.UserRechargeGive;
@Repository
public class UserRechargeGiveDAOImpl extends AbstractGenericDAO<UserRechargeGive,String> implements UserRechargeGiveDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -762034423582237751L;

	@Override
	public List<UserRechargeGive> getByAmountAndType(BigDecimal amount, int rechargeGiveType) {
		String whereSql="rechargeAmount>=:rechargeAmount and rechargeGiveType=:rechargeGiveType";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("rechargeAmount", amount);
		map.put("rechargeGiveType", rechargeGiveType);
		return findByCondition(whereSql, map);
	}

	@Override
    public List<UserRechargeGive> getType(int rechargeGiveType,int max) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("rechargeGiveType", rechargeGiveType);
		String orderBy="order by rechargeAmount desc";
	    return findByCondition(max, map, orderBy);
    }

	@Override
	public List<UserRechargeGive> getByGiveTypeAndAgencyno(int rechargeGiveType, String agencyno, int max) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("rechargeGiveType", rechargeGiveType);
		map.put("agencyno", agencyno);
		String orderBy="order by rechargeAmount desc";
	    return findByCondition(max, map, orderBy);
	}

}
