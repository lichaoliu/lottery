package com.lottery.core.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.PageBean;
import com.lottery.common.PageBean.Sort;
import com.lottery.common.contains.lottery.caselot.AutoJoinDetailState;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.common.util.PropertyFilter;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.AutoJoinDetailDao;
import com.lottery.core.domain.AutoJoin;
import com.lottery.core.domain.AutoJoinDetail;

@Repository
public class AutoJoinDetailDaoImpl  extends AbstractGenericDAO<AutoJoinDetail, Long> implements AutoJoinDetailDao{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3097947256018566084L;
	@Autowired
	private IdGeneratorDao idGeneratorDao;
	
	@Transactional
	public AutoJoinDetail createAutoJoinDetail(AutoJoin autoJoin, String caselotId, BigDecimal joinAmt,
			AutoJoinDetailState state, String memo, String caseLogBuyId, Integer lotteryType) {
		AutoJoinDetail detail = new AutoJoinDetail();
		detail.setId(idGeneratorDao.getAutoJoinDetailId());
		detail.setAutoJoinId(autoJoin.getId());
		detail.setCaseLotBuyId(caseLogBuyId);
		detail.setCaselotId(caselotId);
		detail.setJoinAmt(joinAmt);
		detail.setUserno(autoJoin.getUserno());
		detail.setStatus(state.state);
		detail.setMemo(memo);
		detail.setLotteryType(lotteryType);
		detail.setCreateTime(new Date());
		this.insert(detail);
		return detail;
	}

	@Override
	public void findAutoJoinDetailByPage(Map<String, Object> conditionMap,
			PageBean<AutoJoinDetail> page) {
		String sql = "SELECT o FROM AutoJoinDetail o ";
		String countSql = "SELECT count(*) FROM AutoJoinDetail o ";
		StringBuilder whereSql = new StringBuilder(" WHERE 1=1 ");
		List<PropertyFilter> pfList = null;
		if (conditionMap != null && conditionMap.size() > 0) {
			pfList = PropertyFilter.buildFromMap(conditionMap);
			String buildSql = PropertyFilter.transfer2Sql(pfList, "o");
			whereSql.append(buildSql);
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
		TypedQuery<AutoJoinDetail> q = getEntityManager().createQuery(tsql, AutoJoinDetail.class);
		TypedQuery<Long> total = getEntityManager().createQuery(tCountSql, Long.class);
		if (conditionMap != null && conditionMap.size() > 0) {
			PropertyFilter.setMatchValue2Query(q, pfList);
			PropertyFilter.setMatchValue2Query(total, pfList);
		}
		q.setFirstResult(page.getPageIndex()-1).setMaxResults(page.getMaxResult());
		List<AutoJoinDetail> resultList = q.getResultList();
		int count = total.getSingleResult().intValue();
		page.setList(resultList);
		page.setTotalResult(count);
		
	}
	
	
	
}
