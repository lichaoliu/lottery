package com.lottery.core.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.JclqRaceDAO;
import com.lottery.core.domain.JclqRace;

@Repository
public class JclqRaceDAOImpl extends AbstractGenericDAO<JclqRace, String> implements JclqRaceDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public JclqRace getJclqRaceResult(String matchid) {

		JclqRace result = find(matchid);
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

		sql.append("select min(o.matchNum) from JclqRace o where o.status in (:status) and o.prizeStatus=:prizeStatus");

		List<String> matchids = this.getEntityManager().createQuery(sql.toString(), String.class).setParameter("status", status).setParameter("prizeStatus", PrizeStatus.result_set.value).getResultList();

		if (!matchids.isEmpty()) {
			return matchids.get(0);
		}
		return "";
	}

	@Override
	public List<JclqRace> getRaces(String minMatchid, String maxMatchid) {
		StringBuilder sql = new StringBuilder();
		sql.append("matchNum>=:minMatchid").append(" and matchNum<=:maxMatchid").append(" order by matchNum");

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("minMatchid", minMatchid);
		condition.put("maxMatchid", maxMatchid);
		return findByCondition(sql.toString(), condition);
	}

	@Override
	public List<JclqRace> getByPhase(String phase) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phase", phase);
		return findByCondition(map);
	}

	public List<JclqRace> getCloseAndNoResult() {
		List<Integer> status = new ArrayList<Integer>();
		status.add(RaceStatus.CLOSE.value);
		status.add(RaceStatus.CANCEL.value);
		String sql = "from JclqRace o where o.status in (:status) and o.prizeStatus=:prizeStatus";
		List<JclqRace> races = this.getEntityManager().createQuery(sql, JclqRace.class).setParameter("status", status).setParameter("prizeStatus", PrizeStatus.result_null.value).getResultList();

		return races;
	}

	@Override
	public String getMaxCloseAndResultMatchid() {
		StringBuilder sql = new StringBuilder();

		List<Integer> status = new ArrayList<Integer>();

		status.add(RaceStatus.CLOSE.value);
		status.add(RaceStatus.CANCEL.value);

		sql.append("select max(o.matchNum) from JclqRace o where o.status in (:status) and o.prizeStatus=:prizeStatus");

		List<String> matchids = this.getEntityManager().createQuery(sql.toString(), String.class).setParameter("status", status).setParameter("prizeStatus", PrizeStatus.result_set.value).getResultList();

		if (!matchids.isEmpty()) {
			return matchids.get(0);
		}
		return "";
	}

	@Override
	public List<JclqRace> getCloseByPhaseAndPrizeStatus(String phase, Integer prizeStatus) {
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
