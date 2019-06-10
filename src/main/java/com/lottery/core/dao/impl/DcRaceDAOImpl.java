package com.lottery.core.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.DcType;
import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.DcRaceDAO;
import com.lottery.core.domain.DcRace;

@Repository
public class DcRaceDAOImpl extends AbstractGenericDAO<DcRace, Long> implements DcRaceDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public DcRace getDcRaceResult(String phase, int matcheid, DcType type) {

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("phase", phase);
		condition.put("matchNum", matcheid);
		condition.put("dcType", type.value());
		DcRace race = findByUnique(condition);

		if (race == null) {
			return null;
		}

		if (race.getPrizeStatus() == PrizeStatus.draw.value || race.getPrizeStatus() == PrizeStatus.result_set.value || race.getPrizeStatus() == PrizeStatus.draw.value) {
			return race;
		}
		return null;
	}

	@Override
	public Integer getMinCloseAndResultMatchid(String phase, DcType type) {
		StringBuilder sql = new StringBuilder();

		List<Integer> status = new ArrayList<Integer>();

		status.add(RaceStatus.CLOSE.value);
		status.add(RaceStatus.CANCEL.value);

		sql.append("select min(o.matchNum) from DcRace o where o.phase=:phase and o.status in (:status) and o.prizeStatus=:prizeStatus and o.dcType=:dctype");

		List<Integer> matchids = this.getEntityManager().createQuery(sql.toString(), Integer.class).setParameter("phase", phase).setParameter("status", status).setParameter("prizeStatus", PrizeStatus.result_set.value).setParameter("dctype", type.intValue()).getResultList();

		if (!matchids.isEmpty()) {
			return matchids.get(0) == null ? 0 : matchids.get(0);
		}
		return 0;
	}

	@Override
	public List<DcRace> getRaces(String phase, int minMatchid, int maxMatchid,DcType type) {
		StringBuilder sql = new StringBuilder();
		sql.append("phase=:phase").append(" and dcType=:dcType ")
		.append(" and matchNum>=:minMatchid").append(" and matchNum<=:maxMatchid").append(" order by matchNum");

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("phase", phase);
		condition.put("minMatchid", minMatchid);
		condition.put("maxMatchid", maxMatchid);
		condition.put("dcType", type.value());
		return findByCondition(sql.toString(), condition);
	}

	@Override
	public DcRace getDcRace(String matchid, String phase, DcType type) {
		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("matchNum", Integer.parseInt(matchid));
		condition.put("phase", phase);
		condition.put("dcType", type.intValue());
		return findByUnique(condition);
	}

	@Override
	public List<DcRace> getByPhaseAndStatus(String phase, int dcType, List<Integer> statusList) {
		String whereSql = "phase=:phase and dcType=:dcType and  status in(:status)";
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("phase", phase);
		condition.put("dcType", dcType);
		condition.put("status", statusList);
		return findByCondition(whereSql, condition);
	}

	@Override
	public List<DcRace> getByPhaseAndType(String phase, int dcType,PageBean<DcRace> pageBean) {
		String whereSql = "phase=:phase and dcType=:dcType ";
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("phase", phase);
		condition.put("dcType", dcType);
		return findPageByCondition(whereSql, condition, pageBean);
	}

	@Override
	public List<DcRace> getCloseAndNoResult() {
		List<Integer> status = new ArrayList<Integer>();
		status.add(RaceStatus.CLOSE.value);
		status.add(RaceStatus.CANCEL.value);
		String sql = "from DcRace o where o.status in (:status) and o.prizeStatus=:prizeStatus order by matchNum ";
		List<DcRace> races = this.getEntityManager().createQuery(sql, DcRace.class).setParameter("status", status).setParameter("prizeStatus", PrizeStatus.result_null.value).getResultList();
		return races;
	}

	@Override
	public Integer getMaxCloseAndResultMatchid(String phase, DcType type) {
		StringBuilder sql = new StringBuilder();
		List<Integer> status = new ArrayList<Integer>();

		status.add(RaceStatus.CLOSE.value);
		status.add(RaceStatus.CANCEL.value);

		sql.append("select max(o.matchNum) from DcRace o where o.phase=:phase and o.status in (:status) and o.prizeStatus=:prizeStatus and o.dcType=:dctype");

		List<Integer> matchids = this.getEntityManager().createQuery(sql.toString(), Integer.class).setParameter("phase", phase).setParameter("status", status).setParameter("prizeStatus", PrizeStatus.result_set.value).setParameter("dctype", type.intValue()).getResultList();

		if (!matchids.isEmpty()) {
			return matchids.get(0) == null ? 0 : matchids.get(0);
		}
		return 0;
	}

	@Override
	public List<DcRace> getCloseByPhaseAndPrizeStatus(String phase, Integer prizeStatus, DcType dcType) {
		List<Integer> status = new ArrayList<Integer>();
		status.add(RaceStatus.CLOSE.value);
		status.add(RaceStatus.CANCEL.value);
		String whereSql="status in (:status) and prizeStatus=:prizeStatus and phase=:phase and dcType=:dcType";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("status", status);
		map.put("prizeStatus", prizeStatus);
		map.put("phase", phase);
		map.put("dcType", dcType.state);
		return findByCondition(whereSql, map);
	}
}
