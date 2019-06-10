package com.lottery.core.dao;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.ticket.LotteryTicketConfig;

public interface LotteryTicketConfigDAO extends IGenericDAO<LotteryTicketConfig, Long>{

	/*@PersistenceContext
	EntityManager entityManager;
	
	
	public LotteryTicketConfig getByLotteryType(LotteryType lotteryType){
		TypedQuery<LotteryTicketConfig> query=entityManager.createQuery("select o from LotteryTicketConfig o where o.lotteryType=:lotteryType", LotteryTicketConfig.class);
		query.setParameter("lotteryType", lotteryType);
		return query.getSingleResult();
	}*/
	public LotteryTicketConfig getByLotteryType(LotteryType lotteryType);
}
