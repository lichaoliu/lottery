package com.lottery.core.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.lottery.common.PageBean;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.common.util.PropertyFilter;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.LotteryCaseLotBuyDao;
import com.lottery.core.domain.LotteryCaseLotBuy;

@Repository
public class LotteryCaseLotBuyDaoImpl extends AbstractGenericDAO<LotteryCaseLotBuy, String> implements LotteryCaseLotBuyDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3810406629719287120L;

	@Override
	public List<LotteryCaseLotBuy> findCaseLotBuysByCaselotIdAndState(String caselotId, Integer flag) {
		Map<String,Object> conditionMap=new HashMap<String,Object>();
		conditionMap.put("caselotId", caselotId);
		conditionMap.put("flag", flag);
		return findByCondition(conditionMap, "order by buyTime desc");
	}

	@Override
	public List<LotteryCaseLotBuy> findCaseLotBuysByCaselotIdAndBuyType(String caselotId, int buyType) {
		Map<String,Object> conditionMap=new HashMap<String,Object>();
		conditionMap.put("caselotId", caselotId);
		conditionMap.put("buyType", buyType);
		return findByCondition(conditionMap);
	}

	@Override
	public List<LotteryCaseLotBuy> findCaseLotBuysByCaselotIdAndUserno(String id, String userno) {
		Map<String,Object> conditionMap=new HashMap<String,Object>();
		conditionMap.put("caselotId", id);
		conditionMap.put("userno", userno);
		return findByCondition(conditionMap);
	}

	
	public void findCaseLotBuys(String userno, String caselotId, Map<String, Object> conditionMap, PageBean<LotteryCaseLotBuy> page) {
		String sql = "SELECT o FROM LotteryCaseLotBuy o ";
		String countSql = "SELECT count(*) FROM LotteryCaseLotBuy o ";
		StringBuilder whereSql = new StringBuilder(" WHERE 1=1 ");
		List<PropertyFilter> pfList = null;
		if (conditionMap != null && conditionMap.size() > 0) {
			pfList = PropertyFilter.buildFromMap(conditionMap);
			String buildSql = PropertyFilter.transfer2Sql(pfList, "o");
			whereSql.append(buildSql);
		}
		if (!StringUtil.isEmpty(userno)) {
			whereSql.append(" AND o.userno = :userno ");
		}
		if (!StringUtil.isEmpty(caselotId)) {
			whereSql.append(" AND o.caselotId = :caselotId ");
		}
		StringBuilder orderSql = new StringBuilder(" ORDER BY o.buyTime desc");
		String tsql = sql + whereSql.toString() + orderSql.toString();
		String tCountSql = countSql + whereSql.toString();
		TypedQuery<LotteryCaseLotBuy> q = getEntityManager().createQuery(tsql, LotteryCaseLotBuy.class);
		TypedQuery<Long> total = getEntityManager().createQuery(tCountSql, Long.class);
		if (conditionMap != null && conditionMap.size() > 0) {
			PropertyFilter.setMatchValue2Query(q, pfList);
			PropertyFilter.setMatchValue2Query(total, pfList);
		}
		if (!StringUtil.isEmpty(userno)) {
			q.setParameter("userno", userno);
			total.setParameter("userno", userno);
		}
		if (!StringUtil.isEmpty(caselotId)) {
			q.setParameter("caselotId", caselotId);
			total.setParameter("caselotId", caselotId);
		}
		//此处 PageIndex -1
		q.setFirstResult(page.getPageIndex()-1).setMaxResults(page.getMaxResult());
		List<LotteryCaseLotBuy> resultList = q.getResultList();
		int count = total.getSingleResult().intValue();
		page.setList(resultList);
		page.setTotalResult(count);
	}

	@Override
	public List<LotteryCaseLotBuy> getByUserno(String userno, Date startTime,
			Date endTime, PageBean<LotteryCaseLotBuy> page) {
		String whereSql="userno=:userno and buyTime>=:startTime and buyTime<:endTime";
		String orderSql="order by buyTime desc";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userno", userno);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return findPageByCondition(whereSql,map, page, orderSql);
	}

	@Override
	public Object[] getCaselotUserbet(String userno, int flag, int lotteryType, Date startDate, Date endDate) {
		String whereSql=" select count(o), sum(o.num + o.freezeSafeAmt), sum(prizeAmt) from LotteryCaseLotBuy o where o.userno=:userno and  o.flag=:flag and o.lotteryType=:lotteryType and  o.buyTime>=:startDate and o.buyTime<=:endDate";
		Query query=getEntityManager().createQuery(whereSql);
		query.setParameter("userno", userno);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("flag", flag);
		query.setParameter("lotteryType", lotteryType);
		return (Object[]) query.getSingleResult();
		
	}

	@Override
	public int updatePrizeAmtAndEncashStatus(BigDecimal amount, int encashStatus, String id) {
		Map<String,Object> conditionMap=new HashMap<String,Object>();
		conditionMap.put("prizeAmt", amount);
		conditionMap.put("isExchanged", encashStatus);
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("id", id);
		return update(conditionMap, whereMap);
	}

	@Override
	public int updateStateByCaselotId(String caselotId, int caslelotBuyState) {
		Map<String,Object> conditionMap=new HashMap<String,Object>();

		conditionMap.put("flag", caslelotBuyState);
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("caselotId", caselotId);
		return update(conditionMap, whereMap);
	}

	@Override
	public List<LotteryCaseLotBuy> getByCaseLotId(String caseLotId) {
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("caselotId", caseLotId);
		return findByCondition(whereMap);
	}


}
