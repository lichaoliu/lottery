package com.lottery.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.lottery.common.AdminPage;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.AwardLevelDao;
import com.lottery.core.domain.AwardLevel;
import com.lottery.core.domain.AwardLevelPK;
@Repository
public class AwardLevelDaoImpl extends AbstractGenericDAO<AwardLevel, AwardLevelPK> implements AwardLevelDao{


	private static final long serialVersionUID = 1L;
	
	public Map<String,Long> getPrizeLevel(int lotterytype) {
		
		String sql = "SELECT o FROM AwardLevel o where o.id.lotterytype=:lotterytype";
		
		TypedQuery<AwardLevel> query = this.getEntityManager().createQuery(sql, AwardLevel.class).setParameter("lotterytype", lotterytype);
		
		List<AwardLevel> awardLevels = query.getResultList();
		
		Map<String,Long> awardLevelMap = new HashMap<String, Long>();
		
		for(AwardLevel level:awardLevels) {
			awardLevelMap.put(level.getId().getPrizelevel(), level.getPrize());
		}
		return awardLevelMap;
	}
	
	
	/**
	 * 查询合买根据置顶状态
	 * @param page
	 * @return
	 */
	public void findPageByLotterytype(Integer lotterytype, AdminPage<AwardLevel> page) {
		String sql = "SELECT o FROM AwardLevel o ";
		String countSql = "SELECT count(o.levelname) FROM AwardLevel o ";
		StringBuilder whereSql = new StringBuilder();
		if (null != lotterytype) {
			whereSql.append("WHERE o.id.lotterytype = ?");
		}
		String normalSql = sql + whereSql.toString() + page.getOrderby();
		String normalCountSql = countSql + whereSql;
		TypedQuery<AwardLevel> q = this.getEntityManager().createQuery(normalSql, AwardLevel.class);
		TypedQuery<Long> total = this.getEntityManager().createQuery(normalCountSql, Long.class);
		if (null != lotterytype) {
			q.setParameter(1, lotterytype);
			total.setParameter(1, lotterytype);
		}
		q.setFirstResult(page.getStart()).setMaxResults(page.getLimit());
		List<AwardLevel> resultList = q.getResultList();
		int count = total.getSingleResult().intValue();
		page.setList(resultList);
		page.setTotalResult(count);
	}

}
