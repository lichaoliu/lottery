package com.lottery.core.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.lottery.common.PageBean;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.LotteryPhaseDAO;
import com.lottery.core.domain.LotteryPhase;

@Repository
public class LotteryPhaseDAOImpl extends AbstractGenericDAO<LotteryPhase, Long> implements LotteryPhaseDAO {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public LotteryPhase getByTypeAndPhase(int lotteryType, String phase) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		lotteryType = LotteryType.getPhaseTypeValue(lotteryType);
		map.put("lotteryType", lotteryType);
		map.put("phase", phase);
		return findByUnique(map);
	}

	@Override
	public void saveWincode(int lotteryType, String phase, String wincode) throws Exception{
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("wincode", wincode);
		if (LotteryType.getZc().contains(LotteryType.getLotteryType(lotteryType))) {
			conditionMap.put("phaseStatus", PhaseStatus.prize_ing.value);
		}
		conditionMap.put("realDrawTime", new Date());
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		update(conditionMap, whereMap);
	}
	
	
	@Override
	public int saveWininfo(int lotteryType, String phase,String wincode, String info,String poolAmount,String addPoolAmount,String saleAmount,String whereFrom) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("prizeDetail", info);
		conditionMap.put("wincode", wincode);
		conditionMap.put("poolAmount", poolAmount);
		conditionMap.put("addPoolAmount", addPoolAmount);
		conditionMap.put("saleAmount", saleAmount);
		conditionMap.put("drawDataFrom", whereFrom);
		conditionMap.put("realDrawTime", new Date());
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		return update(conditionMap, whereMap);
	}

	@Override
	public void updatePhasePrizeState(int lotteryType, String phase, int status) throws Exception{
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("phaseStatus", status);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		update(conditionMap, whereMap);
	}

	@Override
	public void updatePhaseEncashState(int lotteryType, String phase, int status) throws Exception{
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("phaseEncaseStatus", status);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		update(conditionMap, whereMap);
	}

	@Override
	public List<LotteryPhase> getByLotteryTypeAndStatus(int lotteryType, int phaseStatus) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phaseStatus", phaseStatus);
		String orderBy = "order by endSaleTime asc";
		return findByCondition(whereMap, orderBy);
	}

	@Override
	public List<LotteryPhase> findWininfoListByLottype(int lotteryType, int num) {
		String sql = "lotteryType=:lotteryType and phaseStatus in(:pahseList) order by phase desc";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryType", lotteryType);
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(PhaseStatus.result_set.getValue());
		statusList.add(PhaseStatus.prize_open.getValue());
		statusList.add(PhaseStatus.prize_start.getValue());
		statusList.add(PhaseStatus.prize_end.getValue());
		statusList.add(PhaseStatus.prize_ing.getValue());
		map.put("pahseList", statusList);
		return findByCondition(num, sql, map);
	}

	@Override
	public int onPrize(int lotteryType, String phase, String wincode) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("wincode", wincode);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		return update(conditionMap, whereMap);

	}

	@Override
	public LotteryPhase getLastRecord(int lotteryType) {
		LotteryPhase phase = null;
		String wheresString = " lotteryType = :lotteryType and phaseStatus in (:statusList) order by phase desc";
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(PhaseStatus.result_set.getValue());
		statusList.add(PhaseStatus.prize_open.getValue());
		statusList.add(PhaseStatus.prize_start.getValue());
		statusList.add(PhaseStatus.prize_end.getValue());
		statusList.add(PhaseStatus.prize_ing.getValue());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryType", lotteryType);
		map.put("statusList", statusList);
		List<LotteryPhase> list = findByCondition(1, wheresString, map);
		if (list != null && list.size() > 0) {
			phase = list.get(0);
			return phase;
		}
		return phase;
	}

	@Override
	public List<String> getPhaseByLottypeAndNum(Integer lottype, Integer num) {
		String sql = "select phase from LotteryPhase o where o.lotteryType = ? order by o.phase desc ";
		TypedQuery<String> q = getEntityManager().createQuery(sql, String.class);
		q.setParameter(1, lottype).setFirstResult(0).setMaxResults(num);
		List<String> rets = q.getResultList();
		return rets;
	}

	@Override
	public void saveZcWincode(int lotteryType, String phase, String wincode) throws Exception {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("wincode", wincode);
		conditionMap.put("phaseStatus", PhaseStatus.prize_ing.value);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		update(conditionMap, whereMap);
	}

	@Override
	public List<LotteryPhase> getHistoryList(int lotteryType, PageBean<LotteryPhase> page) {
		String sql = "lotteryType=:lotteryType and phaseStatus in(:phaseList) order by phase desc";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryType", lotteryType);
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(PhaseStatus.result_set.getValue());
		statusList.add(PhaseStatus.prize_open.getValue());
		statusList.add(PhaseStatus.prize_start.getValue());
		statusList.add(PhaseStatus.prize_end.getValue());
		statusList.add(PhaseStatus.prize_ing.getValue());
		map.put("phaseList", statusList);
		return findPageByCondition(sql, map, page);
	}
	
	
	@Override
	public List<LotteryPhase> getHistoryListWithClosed(int lotteryType, PageBean<LotteryPhase> page) {
		String sql = "lotteryType=:lotteryType and phaseStatus in(:phaseList) order by phase desc";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryType", lotteryType);
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(PhaseStatus.close.getValue());
		statusList.add(PhaseStatus.result_set.getValue());
		statusList.add(PhaseStatus.prize_open.getValue());
		statusList.add(PhaseStatus.prize_start.getValue());
		statusList.add(PhaseStatus.prize_end.getValue());
		statusList.add(PhaseStatus.prize_ing.getValue());
		map.put("phaseList", statusList);
		return findPageByCondition(sql, map, page);
	}

	@Override
	public List<LotteryPhase> getHistoryPhase(int lotteryType, int num) {
		lotteryType = LotteryType.getPhaseTypeValue(lotteryType);
		String whereSql = "phaseStatus not in(:phaseStatus) and lotteryType=:lotteryType";
		String orderBySql = "order by phase desc";
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(PhaseStatus.open.getValue());
		statusList.add(PhaseStatus.unopen.getValue());
		whereMap.put("phaseStatus", statusList);
		return findByCondition(num, whereSql, whereMap, orderBySql);
	}

	@Override
	public List<LotteryPhase> getNextPhase(int lotteryType, String phase, int num) {
		String whereSql = "phase>:phase and lotteryType=:lotteryType";
		String orderBySql = "order by phase asc";
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		return findByCondition(num, whereSql, whereMap, orderBySql);
	}

	@Override
	public List<LotteryPhase> getPhaseList(int lotteryType, int num) {
		String whereSql = "phaseStatus not in (:phaseStatus) and lotteryType=:lotteryType";
		String orderBySql = "order by phase desc";
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		List<Integer> phaseStatusList=new ArrayList<Integer>();
		phaseStatusList.add(PhaseStatus.unopen.value);
		whereMap.put("phaseStatus", phaseStatusList);
		return findByCondition(num, whereSql, whereMap, orderBySql);
	}

	@Override
	public List<LotteryPhase> getByTerminalStatus(int lotteryType, int terminalStatus) {
		String whereString="terminalStatus=:terminalStatus and lotteryType=:lotteryType";
		String orderBy="order by endSaleTime asc";
		Map<String, Object> map = new HashMap<String, Object>();
		lotteryType = LotteryType.getPhaseTypeValue(lotteryType);
		map.put("lotteryType", lotteryType);
		map.put("terminalStatus", terminalStatus);
		return findByCondition(whereString, map, orderBy);
	}

	@Override
	public LotteryPhase getCurrent(int lotteryType) throws Exception {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("forCurrent", YesNoStatus.yes.value);
		lotteryType = LotteryType.getPhaseTypeValue(lotteryType);
		whereMap.put("lotteryType", lotteryType);
		return findByUnique(whereMap);
	}

	@Override
    public List<LotteryPhase> getNextPhaseWithCurrent(int lotteryType, String phase, int num) {
		String whereSql = "phase>=:phase and lotteryType=:lotteryType";
		String orderBySql = "order by phase asc";
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		return findByCondition(num, whereSql, whereMap, orderBySql);
    }

	@Override
	public List<LotteryPhase> getNextPhaseNotWithCurrent(int lotteryType, String phase, int num) {
		String whereSql = "phase>:phase and lotteryType=:lotteryType";
		String orderBySql = "order by phase asc";
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		return findByCondition(num, whereSql, whereMap, orderBySql);
	}

	@Override
    public List<LotteryPhase> getByLotteryAndStatuses(int lotteryType, List<Integer> statusList) {
		String whereSql = "phaseStatus  in (:phaseStatus) and lotteryType=:lotteryType";
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phaseStatus", statusList);
		return findByCondition(whereSql, whereMap);
    }

	@Override
	public List<LotteryPhase> getByLotteryType(Integer lotteryType, int max) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryType", lotteryType);
		return findByCondition(max, map, "order by phase desc");
	}

	@Override
	public int updateStatus(int lotteryType, String phase, int phaseStatus, int terminalStatus, int forSale, int forCurrent) {
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("phaseStatus", phaseStatus);
		contentMap.put("terminalStatus", terminalStatus);
		contentMap.put("switchTime", new Date());
		contentMap.put("forSale", forSale);
		contentMap.put("forCurrent", forCurrent);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		return update(contentMap, whereMap);
		
	}

	@Override
	public int updateTerminalStatus(int terminalStatus, Long id) {
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("terminalStatus", terminalStatus);
		Map<String, Object> whereMap = new HashMap<String, Object>();
	
		whereMap.put("id", id);
		return update(contentMap, whereMap);
	}

	@Override
	public int updateTerminalStatus(int terminalStatus, int lotteryType, String phase) {
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("terminalStatus", terminalStatus);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		return update(contentMap, whereMap);
	}

	@Override
	public int updateEndTicketTime(int lotteryType, String phase, Date endTicketTime) {
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("endTicketTime", endTicketTime);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		return update(contentMap, whereMap);

	}



	@Override
	public int phaseSwitch(int phaseStatus, int forSale, int forCurrent,int terminalStatus, Long id) {
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("phaseStatus", phaseStatus);
		contentMap.put("switchTime", new Date());
		contentMap.put("forSale", forSale);
		contentMap.put("forCurrent", forCurrent);
		contentMap.put("terminalStatus", terminalStatus);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("id", id);
		return update(contentMap, whereMap);
	}

	@Override
	public int phaseSwitch(int phaseStatus, int forSale, int forCurrent,int terminalStatus, int lotteryType, String phase) {
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("phaseStatus", phaseStatus);
		contentMap.put("switchTime", new Date());
		contentMap.put("forSale", forSale);
		contentMap.put("forCurrent", forCurrent);
		contentMap.put("terminalStatus", terminalStatus);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phase", phase);
		return update(contentMap, whereMap);
	}

	

}
