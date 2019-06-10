package com.lottery.core.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.lottery.common.PageBean;
import com.lottery.common.PageBean.Sort;
import com.lottery.common.contains.lottery.caselot.AutoJoinState;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.common.dto.AutoJoinUserDTO;
import com.lottery.common.util.PropertyFilter;
import com.lottery.core.dao.AutoJoinDao;
import com.lottery.core.domain.AutoJoin;

@Repository
public class AutoJoinDaoImpl extends AbstractGenericDAO<AutoJoin, Long> implements AutoJoinDao{

	public int findCountByStarterAndLotteryType(String starter, Integer lotteryType) {
		StringBuilder sql = new StringBuilder("SELECT count(o) FROM AutoJoin o WHERE o.status = :status and o.starter = :starter ");
		if (lotteryType != null) {
			sql.append(" and lotteryType = :lotteryType");
		}
		TypedQuery<Long> total = getEntityManager().createQuery(sql.toString(), Long.class);
		total.setParameter("status", AutoJoinState.available.value);
		total.setParameter("starter", starter);
		if (lotteryType != null) {
			total.setParameter("lotteryType", lotteryType);
		}
		return total.getSingleResult().intValue();
	}
	
	public void findAutoJoinByPage(Integer[] lotnos, Map<String, Object> conditionMap, PageBean<AutoJoin> page) {
		String sql = "SELECT o FROM AutoJoin o ";
		String countSql = "SELECT count(o) FROM AutoJoin o ";
		StringBuilder whereSql = new StringBuilder(" WHERE 1=1 ");
		List<PropertyFilter> pfList = null;
		if (conditionMap != null && conditionMap.size() > 0) {
			pfList = PropertyFilter.buildFromMap(conditionMap);
			String buildSql = PropertyFilter.transfer2Sql(pfList, "o");
			whereSql.append(buildSql);
		}
		if (null != lotnos && lotnos.length > 0) {
			whereSql.append(" and o.lotteryType in (:lotteryType)");
		}
		List<Sort> sortList = page.fetchSort();
		StringBuilder orderSql = new StringBuilder(" ORDER BY ");
		if (page.isOrderBySetted()) {
			for (Sort sort : sortList) {
				orderSql.append(" " + sort.getProperty() + " " + sort.getDir() + ",");
			}
			orderSql.delete(orderSql.length() - 1, orderSql.length());
		} else {
			orderSql.append(" o.createTime desc ");
		}
		String tsql = sql + whereSql.toString() + orderSql.toString();
		String tCountSql = countSql + whereSql.toString();
		TypedQuery<AutoJoin> q = getEntityManager().createQuery(tsql, AutoJoin.class);
		TypedQuery<Long> total = getEntityManager().createQuery(tCountSql, Long.class);
		if (conditionMap != null && conditionMap.size() > 0) {
			PropertyFilter.setMatchValue2Query(q, pfList);
			PropertyFilter.setMatchValue2Query(total, pfList);
		}
		if (null != lotnos && lotnos.length > 0) {
			List<Integer> lotteryTypes = Arrays.asList(lotnos);
			q.setParameter("lotteryType", lotteryTypes);
			total.setParameter("lotteryType", lotteryTypes);
		}
		q.setFirstResult(page.getPageIndex()-1).setMaxResults(page.getMaxResult());
		List<AutoJoin> resultList = q.getResultList();
		int count = total.getSingleResult().intValue();
		page.setList(resultList);
		page.setTotalResult(count);
	}
	
	@Override
	public void findAutoJoinUser(Integer lotteryType, PageBean<AutoJoinUserDTO> page) {
		String where = "";
		if (lotteryType != null) {
			where = " and lottery_type=:lotteryType";
		}
		String sql = "select count(*) count, starter from auto_join where status = :status "+where+" group by starter order by count desc ";
		Query q = getEntityManager().createNativeQuery(sql);
		if (lotteryType != null) {
			q.setParameter("lotteryType", lotteryType);
		}
		q.setParameter("status", AutoJoinState.available.value)
		.setFirstResult(page.getPageIndex()-1).setMaxResults(page.getMaxResult());
		
		List<Object[]> list = q.getResultList();
		List<AutoJoinUserDTO> ajus = new ArrayList<AutoJoinUserDTO>();
		for (Object[] objects : list) {
			AutoJoinUserDTO aju = new AutoJoinUserDTO();
			aju.setCount(objects[0].toString());
			aju.setStarterUserno(objects[1].toString());
			ajus.add(aju);
		}
		page.setList(ajus);
		
		String countSql = "SELECT count(*) FROM ("+sql+") o";
		Query total  = getEntityManager().createNativeQuery(countSql);
		total.setParameter("status", AutoJoinState.available.value);
		if (lotteryType != null) {
			total.setParameter("lotteryType", lotteryType);
		}
		page.setTotalResult(Integer.parseInt(total.getSingleResult().toString()));
	}

	@Override
	public List<AutoJoin> findByLotteryTypeAndStarter(Integer lotteryType, String starter) {
		return findByCondition("lotteryType = ? and starter = ? and status = ? order by updateTime ASC", new Object[]{lotteryType, starter, AutoJoinState.available.value});
	}
}
