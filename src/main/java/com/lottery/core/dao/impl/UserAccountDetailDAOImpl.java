package com.lottery.core.dao.impl;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.UserAccountDetailDAO;
import com.lottery.core.domain.account.UserAccountDetail;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.*;

@Repository
public class UserAccountDetailDAOImpl extends AbstractGenericDAO<UserAccountDetail, String> implements UserAccountDetailDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7748941751671168120L;

	@Override
	public UserAccountDetail getByPayIdAndType(String payId, AccountType payType, AccountDetailType accountDetailType) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("payId", payId);
			map.put("payType", payType.getValue());
			map.put("detailType", accountDetailType.getValue());
			return findByUnique(map);
		} catch (Exception e) {
			return null;
		}

	}

	
	@Override
	public List<UserAccountDetail> getByUserno(String userno, Date startDate, Date endDate, PageBean<UserAccountDetail> page) {
		String whereSql = "userno=:userno and createtime>=:startTime and createtime<:endTime ";
		String orderBy = "order  by createtime desc";
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("userno", userno);
		whereMap.put("startTime", startDate);
		whereMap.put("endTime", endDate);
		return findPageByCondition(whereSql, whereMap, page, orderBy);
	}

	@Override
	public Map<Integer, BigDecimal> getBetAmountStatic(String userno, List<Integer> lotteryTypeList, Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		sql.append("select o.lotteryType,sum(o.amt) from UserAccountDetail o where o.detailType in (:detailType) and o.payType=:payType ");
		if (lotteryTypeList != null && lotteryTypeList.size() > 0) {
			sql.append(" and lotteryType in (:lotteryTypeList)");
		}
		sql.append(" and o.userno=:userno and o.createtime>=:startDate and o.createtime<=:endDate  group by o.lotteryType");

	
		Query query = getEntityManager().createQuery(sql.toString());
		query.setParameter("payType", AccountType.bet.getValue());
		if (lotteryTypeList != null && lotteryTypeList.size() > 0) {
			query.setParameter("lotteryTypeList", lotteryTypeList);
		}

		query.setParameter("userno", userno);
		List<Integer> detailList = new ArrayList<Integer>();
		detailList.add(AccountDetailType.deduct.value);
		detailList.add(AccountDetailType.half_deduct.value);
		query.setParameter("detailType", detailList);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
		for (Object row : query.getResultList()) {
			Object[] cells = (Object[]) row;
			map.put(Integer.valueOf(cells[0] + ""), new BigDecimal(cells[1] + ""));
		}
		return map;
	}

	@Override
	public BigDecimal getUsernoBetAmountStatic(String userno, List<Integer> lotteryTypeList, Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(o.amt) from UserAccountDetail o where o.detailType in (:detailType) and o.payType=:payType ");
		if (lotteryTypeList != null && lotteryTypeList.size() > 0) {
			sql.append(" and lotteryType in (:lotteryTypeList)");
		}
		sql.append(" and o.userno=:userno and o.createtime>=:startDate and o.createtime<=:endDate");

	
		TypedQuery<BigDecimal> query = getEntityManager().createQuery(sql.toString(), BigDecimal.class);
		query.setParameter("payType", AccountType.bet.getValue());
		if (lotteryTypeList != null && lotteryTypeList.size() > 0) {
			query.setParameter("lotteryTypeList", lotteryTypeList);
		}

		query.setParameter("userno", userno);
		List<Integer> detailList = new ArrayList<Integer>();
		detailList.add(AccountDetailType.deduct.value);
		detailList.add(AccountDetailType.half_deduct.value);
		query.setParameter("detailType", detailList);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getSingleResult();
	}

	@Override
	public BigDecimal getAgentBetAmountStatic(String agencyno, List<Integer> lotteryTypeList, Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(o.amt) from UserAccountDetail o where o.detailType in (:detailType) and o.payType=:payType ");
		if (lotteryTypeList != null && lotteryTypeList.size() > 0) {
			sql.append(" and lotteryType in (:lotteryTypeList)");
		}
		sql.append(" and o.agencyNo=:agencyNo and o.createtime>=:startDate and o.createtime<=:endDate");

	
		TypedQuery<BigDecimal> query = getEntityManager().createQuery(sql.toString(), BigDecimal.class);
		query.setParameter("payType", AccountType.bet.getValue());
		if (lotteryTypeList != null && lotteryTypeList.size() > 0) {
			query.setParameter("lotteryTypeList", lotteryTypeList);
		}

		query.setParameter("agencyNo", agencyno);
		List<Integer> detailList = new ArrayList<Integer>();
		detailList.add(AccountDetailType.deduct.value);
		detailList.add(AccountDetailType.half_deduct.value);
		query.setParameter("detailType", detailList);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getSingleResult();
	}

	@Override
	public int updateOrderPrizeAmount(String orderId, BigDecimal orderPrizeAmount) {
		Map<String,Object> map=new HashMap<String,Object>();

		map.put("orderPrizeAmount", orderPrizeAmount);
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("orderId", orderId);
		return update(map, whereMap);

	}


	@Override
	public UserAccountDetail getByPayIdAndType(String payid, int payType, int accountDetailType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payId", payid);
		map.put("payType", payType);
		map.put("detailType", accountDetailType);
		return findByUnique(map);
	}


	@Override
	public List<UserAccountDetail> getByPayIdAndDetailType(String payid, int accountDetailType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payId", payid);
		map.put("detailType", accountDetailType);
		return findByCondition(map);
	}

}
