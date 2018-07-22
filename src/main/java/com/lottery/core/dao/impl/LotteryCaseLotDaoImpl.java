package com.lottery.core.dao.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.lottery.common.PageBean;
import com.lottery.common.PageBean.Sort;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.common.util.PropertyFilter;
import com.lottery.core.dao.LotteryCaseLotDao;
import com.lottery.core.domain.LotteryCaseLot;

@Repository
public class LotteryCaseLotDaoImpl extends AbstractGenericDAO<LotteryCaseLot, String> implements LotteryCaseLotDao {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9205585727764334915L;

	/**
	 * 计算该合买所有冻结保底金额
	 */
	public BigDecimal computeFreezeSafeAmt(String caseLotId) {
		String sql = "select sum(o.freezeSafeAmt) from LotteryCaseLotBuy o where o.caselotId = :caseLotId";
		TypedQuery<BigDecimal> query = getEntityManager().createQuery(sql, BigDecimal.class);
		query.setParameter("caseLotId", caseLotId);
		BigDecimal ret = query.getSingleResult();
		return ret==null?BigDecimal.ZERO:ret;
	}

	@Override
	public Long computeParticipantCount(String caselotId) {
		String sql = "select count(distinct o.userno) from LotteryCaseLotBuy o where o.caselotId=:caselotId and (o.num>'0' or o.freezeSafeAmt>'0') ";
		TypedQuery<Long> q = getEntityManager().createQuery(sql, Long.class);
		q.setParameter("caselotId", caselotId);
		Long count = q.getSingleResult();
		return count;
	}
	
	public void findCaseLotsByPage(Integer[] state, Integer[] lotteryType, String search, Map<String, Object> conditionMap,
			PageBean<LotteryCaseLot> page) {
		String sql = "SELECT o FROM LotteryCaseLot o ";
		String countSql = "SELECT count(o) FROM LotteryCaseLot o ";
		StringBuilder whereSql = new StringBuilder(" WHERE 1=1 ");
		if (null != state && state.length > 0) {
			whereSql.append(" and o.state in (:state) ");
		}
		if (null != lotteryType && lotteryType.length > 0) {
			whereSql.append(" and o.lotteryType in (:lotteryType) ");
		}
		if (StringUtils.isNotBlank(search)) {
			whereSql.append(" AND ( o.id like :search1 or o.startName like :search2 ) ");
		}
		List<PropertyFilter> pfList = null;
		if (conditionMap != null && conditionMap.size() > 0) {
			pfList = PropertyFilter.buildFromMap(conditionMap);
			String buildSql = PropertyFilter.transfer2Sql(pfList, "o");
			whereSql.append(buildSql);
		}
		
		List<Sort> sortList = page.fetchSort();
		StringBuilder topOrderSql = new StringBuilder(" ORDER BY o.sortState DESC, ");
		if (page.isOrderBySetted()) {
			for (Sort sort : sortList) {
				topOrderSql.append(" " + sort.getProperty() + " " + sort.getDir() + ",");
			}
			topOrderSql.append(" o.startTime DESC ");
		} else {
			topOrderSql.append(" (o.buyAmtByStarter+o.buyAmtByFollower+o.safeAmt)/o.totalAmt DESC,o.startTime DESC ");
		}
		
		String topSql = sql + whereSql.toString() + topOrderSql.toString();
		String topCountSql = countSql + whereSql.toString();

		TypedQuery<LotteryCaseLot> q = this.getEntityManager().createQuery(topSql, LotteryCaseLot.class);
		TypedQuery<Long> topTotal = this.getEntityManager().createQuery(topCountSql, Long.class);
		
		if (null != state && state.length > 0) {
			List<Integer> states = Arrays.asList(state);
			q.setParameter("state", states);
			topTotal.setParameter("state", states);
		}
		if (null != lotteryType && lotteryType.length > 0) {
			List<Integer> lotteryTypes = Arrays.asList(lotteryType);
			q.setParameter("lotteryType", lotteryTypes);
			topTotal.setParameter("lotteryType", lotteryTypes);
		}
		if (StringUtils.isNotBlank(search)) {
			q.setParameter("search1", "%" + search + "%");
			q.setParameter("search2", "%" + search + "%");
			topTotal.setParameter("search1", "%" + search + "%");
			topTotal.setParameter("search2", "%" + search + "%");
		}
		if (conditionMap != null && conditionMap.size() > 0) {
			PropertyFilter.setMatchValue2Query(q, pfList);
			PropertyFilter.setMatchValue2Query(topTotal, pfList);
		}

		q.setFirstResult(page.getPageIndex()-1).setMaxResults(page.getMaxResult());
		List<LotteryCaseLot> topResultList = q.getResultList();
		Integer count = topTotal.getSingleResult().intValue();
		page.setList(topResultList);
		page.setTotalResult(count);
	}
	
	/**
	 * 查询用户参与的合买分页
	 * @param state 合买状态数组
	 * @param userno  参与者用户编号
	 * @param conditionMap 查询条件集合
	 * @param page 分页集合
	 * @return
	 */
	public void findCaseLotsByUserno(Integer[] state, String userno, Integer buyType, Map<String, Object> conditionMap, PageBean<LotteryCaseLot> page) {
		String sql = "SELECT distinct o.* FROM lottery_caselot o left join lottery_caselot_buy t on o.id=t.caselot_Id ";
		String countSql = "SELECT count(distinct o.id) FROM lottery_caselot o left join lottery_caselot_buy t on o.id=t.caselot_id ";
		StringBuilder whereSql = new StringBuilder(" WHERE 1=1 ");
		// 组装whereSql开始
		if (null != state && state.length > 0) {
			whereSql.append(" and o.state in (:state)");
		}
		List<PropertyFilter> pfList = null;
		if (conditionMap != null && conditionMap.size() > 0) {
			pfList = PropertyFilter.buildFromMap(conditionMap);
			String buildSql = PropertyFilter.transfer2Sql(pfList, "o");
			whereSql.append(buildSql);
		}
		whereSql.append(" and t.userno = :userno");
		if (buyType != null) {
			whereSql.append(" and t.buy_type = :buyType");
		}
		List<Sort> sortList = page.fetchSort();
		StringBuilder topOrderSql = new StringBuilder(" ORDER BY ");
		if (page.isOrderBySetted()) {
			for (Sort sort : sortList) {
				topOrderSql.append(" " + sort.getProperty() + " " + sort.getDir() + ",");
			}
		}
		topOrderSql.append(" o.start_time DESC ");

		String selectSql = sql + whereSql.toString() + topOrderSql.toString();
		String selectCountSql = countSql + whereSql.toString();
		
		Query q = this.getEntityManager().createNativeQuery(selectSql, LotteryCaseLot.class);
		Query total = this.getEntityManager().createNativeQuery(selectCountSql);
		
		if (null != state && state.length > 0) {
			List<Integer> states = Arrays.asList(state);
			q.setParameter("state", states);
			total.setParameter("state", states);
		}
		if (conditionMap != null && conditionMap.size() > 0) {
			PropertyFilter.setMatchValue2Query(q, pfList);
			PropertyFilter.setMatchValue2Query(total, pfList);
		}
		q.setParameter("userno", userno);
		total.setParameter("userno", userno);
		if (buyType != null) {
			q.setParameter("buyType", buyType);
			total.setParameter("buyType", buyType);
		}
		q.setFirstResult(page.getPageIndex()-1).setMaxResults(page.getMaxResult());
		List<LotteryCaseLot> resultList = q.getResultList();
		List<Object> countList = total.getResultList();
		String count = countList.get(0).toString();
		page.setList(resultList);
		page.setTotalResult(Integer.parseInt(count));
	}

	@Override
	public List<LotteryCaseLot> getByStatus(int max, int status) {
		Map<String, Object> conditionMap=new HashMap<String, Object>();
		conditionMap.put("state",status);
		
		return findByCondition(max,conditionMap,"order by endTime asc");
	}

}
