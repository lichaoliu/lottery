package com.lottery.core.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.JczqRaceDao;
import com.lottery.core.domain.JczqRace;

@Repository
public class JczqRaceDaoImpl extends AbstractGenericDAO<JczqRace, String> implements JczqRaceDao {

	private static final long serialVersionUID = 1L;

	@Override
	public JczqRace getJczqRaceResult(String matchid) {
		JczqRace result = find(matchid);
		if (result == null) {
			return null;
		}
		if (result.getPrizeStatus() == PrizeStatus.draw.value || result.getPrizeStatus() == PrizeStatus.rewarded.value) {
			return result;
		}
		return null;
	}

	@Override
	public String getMinCloseAndResultMatchid() {
		StringBuilder sql = new StringBuilder();

		List<Integer> status = new ArrayList<Integer>();

		status.add(RaceStatus.CLOSE.value);
		status.add(RaceStatus.CANCEL.value);

		sql.append("select min(o.matchNum) from JczqRace o where o.status in (:status) and o.prizeStatus=:prizeStatus");

		List<String> matchids = this.getEntityManager().createQuery(sql.toString(), String.class).setParameter("status", status).setParameter("prizeStatus", PrizeStatus.result_set.value).getResultList();

		if (!matchids.isEmpty()) {
			return matchids.get(0);
		}
		return "";
	}

	@Override
	public List<JczqRace> getRaces(String minMatchid, String maxMatchid) {
		StringBuilder sql = new StringBuilder();
		sql.append("matchNum>=:minMatchid").append(" and matchNum<=:maxMatchid").append(" order by matchNum");

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("minMatchid", minMatchid);
		condition.put("maxMatchid", maxMatchid);
		return findByCondition(sql.toString(), condition);
	}

	@Override
	public List<JczqRace> getByPhase(String phase) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phase", phase);
		return findByCondition(map);
	}

	@Override
	public List<JczqRace> getCloseAndNoResult() {
		List<Integer> status = new ArrayList<Integer>();
		status.add(RaceStatus.CLOSE.value);
		status.add(RaceStatus.CANCEL.value);
		String sql = "from JczqRace o where o.status in (:status) and o.prizeStatus=:prizeStatus";
		List<JczqRace> races = this.getEntityManager().createQuery(sql, JczqRace.class).setParameter("status", status).setParameter("prizeStatus", PrizeStatus.result_null.value).getResultList();

		return races;
	}

	@Override
	public String getMaxCloseAndResultMatchid() {
		StringBuilder sql = new StringBuilder();

		List<Integer> status = new ArrayList<Integer>();

		status.add(RaceStatus.CLOSE.value);
		status.add(RaceStatus.CANCEL.value);

		sql.append("select max(o.matchNum) from JczqRace o where o.status in (:status) and o.prizeStatus=:prizeStatus");

		List<String> matchids = this.getEntityManager().createQuery(sql.toString(), String.class).setParameter("status", status).setParameter("prizeStatus", PrizeStatus.result_set.value).getResultList();

		if (!matchids.isEmpty()) {
			return matchids.get(0);
		}
		return "";
	}

	@Override
	public List<JczqRace> getCloseByPhaseAndPrizeStatus(String phase, Integer prizeStatus) {
		List<Integer> status = new ArrayList<Integer>();
		status.add(RaceStatus.CLOSE.value);
		status.add(RaceStatus.CANCEL.value);
		String whereSql="status in (:status) and prizeStatus=:prizeStatus and phase=:phase";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("status", status);
		map.put("prizeStatus", prizeStatus);
		map.put("phase", phase);
		return findByCondition(whereSql, map);
	}

}
