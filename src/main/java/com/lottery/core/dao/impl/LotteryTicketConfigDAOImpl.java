package com.lottery.core.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.LotteryTicketConfigDAO;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
@Repository
public class LotteryTicketConfigDAOImpl extends AbstractGenericDAO<LotteryTicketConfig, Long>
		implements LotteryTicketConfigDAO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3466250455064073616L;

	@Override
	public LotteryTicketConfig getByLotteryType(LotteryType lotteryType) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("lotteryType", lotteryType.getValue());
		return this.findByUnique(map);
	}

}
