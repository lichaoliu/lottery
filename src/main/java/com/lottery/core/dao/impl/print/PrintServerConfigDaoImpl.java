package com.lottery.core.dao.impl.print;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lottery.common.contains.PrintServerConfigStatus;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.print.PrintServerConfigDao;
import com.lottery.core.domain.print.PrintServerConfig;

/**
 * Created by fengqinyun on 16/11/10.
 */
@Repository
public class PrintServerConfigDaoImpl extends AbstractGenericDAO<PrintServerConfig,String> implements PrintServerConfigDao {
    @Override
    public List<PrintServerConfig> getEnablePrintServer() {
        String whereSql="isOnline=:isOnline and status=:status";
        Map<String,Object> whereMap=new HashMap<String, Object>();
        whereMap.put("isOnline", YesNoStatus.yes.value);
        whereMap.put("status", PrintServerConfigStatus.print.value);
        String orderBy="order by weight desc,balance desc";
        return findByCondition(whereSql,whereMap,orderBy);
    }

	@Override
	public int updateStatus(List<String> ids, Integer status) {
		return getEntityManager().createQuery("update PrintServerConfig t set status=:status where id in (:ids)")
				.setParameter("status", status).setParameter("ids", ids).executeUpdate();
	}

	@Override
	public int updateReport(List<String> ids, String beginDate, Integer reportType) {
		return getEntityManager().createQuery("update PrintServerConfig t set status=:status, reportType=:reportType, beginDate=:beginDate where id in (:ids)")
				.setParameter("status", PrintServerConfigStatus.report.value)
				.setParameter("beginDate", beginDate)
				.setParameter("reportType", reportType)
				.setParameter("ids", ids)
				.executeUpdate();
	}

	@Override
	public Long findCountByStatus(List<String> ids, Integer status) {
		return (Long) getEntityManager().createQuery("select count(o) from PrintServerConfig o where id in (:ids) and status=:status")
				.setParameter("ids", ids)
				.setParameter("status", status)
				.getSingleResult();
	}

	@Override
	public int updateStatusByAreaId(String areaId, Integer type) {
		String sql = "update PrintServerConfig t set status=:status where isOnline=:isOnline and status=:wherestatus";
		if(areaId != null && !"".equals(areaId) && !"-1".equals(areaId)){
			sql += " and areaId = :areaId";
		}
		int status = 0;
		if(type == 1){
			status = PrintServerConfigStatus.print.value;
		}else if(type == 2){
			status = PrintServerConfigStatus.prize.value;
		}else{
			return 0;
		}
		Query query = getEntityManager().createQuery(sql)
				.setParameter("isOnline", YesNoStatus.yes.value)
				.setParameter("status", status)
				.setParameter("wherestatus", PrintServerConfigStatus.stop.value);
		
		if(areaId != null && !"".equals(areaId) && !"-1".equals(areaId)){
			query.setParameter("areaId", areaId);
		}
		return query.executeUpdate();
	}

	@Override
	public int updateServerBalance(String id, Double balance) {
		return getEntityManager().createQuery("update PrintServerConfig t set balance=:balance where id = :id")
				.setParameter("balance", balance).setParameter("id", id).executeUpdate();
	}

	@Override
	public int lotteryTypes(String ids, String lotteryTypes) {
		return getEntityManager().createQuery("update PrintServerConfig t set lotteryTypes=:lotteryTypes where id in (:ids)")
				.setParameter("lotteryTypes", lotteryTypes).setParameter("ids", Arrays.asList(ids.split(","))).executeUpdate();
	}

	@Override
	public int weight(String ids, Integer weight) {
		return getEntityManager().createQuery("update PrintServerConfig t set weight=:weight where id in (:ids)")
				.setParameter("weight", weight).setParameter("ids", Arrays.asList(ids.split(","))).executeUpdate();
	}

	@Override
	public int minSecodes(String ids, Long minSecodes) {
		return getEntityManager().createQuery("update PrintServerConfig t set minSecodes=:minSecodes where id in (:ids)")
				.setParameter("minSecodes", minSecodes).setParameter("ids", Arrays.asList(ids.split(","))).executeUpdate();
	}

	@Override
	public int updateIsBig(String ids, Integer isBigMoney) {
		return getEntityManager().createQuery("update PrintServerConfig t set isBigMoney=:isBigMoney where id in (:ids)")
				.setParameter("isBigMoney", isBigMoney).setParameter("ids", Arrays.asList(ids.split(","))).executeUpdate();
	}

	@Override
	public void updateControl(Integer maxAmount1, Long minSecodes1, Long maxSeconds1) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		if(maxAmount1 != null){
			conditionMap.put("maxAmount", maxAmount1);
		}
		if(minSecodes1 != null){
			conditionMap.put("minSecodes", minSecodes1);
		}
		if(maxSeconds1 != null){
			conditionMap.put("maxSeconds", maxSeconds1);
		}
		if(conditionMap.isEmpty()){
			return;
		}
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("isDel", YesNoStatus.no.value);
		this.update(conditionMap, whereMap);
		
	}


}
