package com.lottery.core.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.lottery.common.contains.account.StatisticType;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.UserRebateStatisticDao;
import com.lottery.core.domain.account.UserRebateStatistic;
import com.lottery.core.domain.account.UserRebateStatisticPK;
@Repository
public class UserRebateStatisticDaoImpl extends AbstractGenericDAO<UserRebateStatistic, UserRebateStatisticPK> implements UserRebateStatisticDao{

	@Override
    public List<UserRebateStatistic> getByUsernoAndMonth(String userno, int lotteryType,Long monthNum) {
	    Map<String,Object> map=new HashMap<String,Object>();
	    map.put("belongTo", userno);
	    map.put("statisticType", StatisticType.day.value);
	    map.put("monthNum", monthNum);
	    map.put("statisticLottery", lotteryType);
	    return findByCondition(map);
    }

	@Override
	public BigDecimal getMonthTotal(String userno, int lotteryType, Long monthNum) {
		String whereSql="select sum(o.amount) from UserRebateStatistic o where o.statisticType=:statisticType and  o.belongTo=:belongTo and statisticLottery=:statisticLottery and monthNum=:monthNum";
		TypedQuery<BigDecimal>  query=getEntityManager().createQuery(whereSql, BigDecimal.class);
		query.setParameter("belongTo", userno);
		query.setParameter("statisticType", StatisticType.day.value);
		query.setParameter("monthNum", monthNum);
		query.setParameter("statisticLottery", lotteryType);
		return query.getSingleResult();
	}

	
	

}
