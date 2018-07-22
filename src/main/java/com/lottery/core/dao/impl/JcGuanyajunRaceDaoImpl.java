package com.lottery.core.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.JcGuanyajunRaceDao;
import com.lottery.core.domain.GuanyajunRacePK;
import com.lottery.core.domain.JcGuanYaJunRace;

@Repository
public class JcGuanyajunRaceDaoImpl extends AbstractGenericDAO<JcGuanYaJunRace, GuanyajunRacePK> implements JcGuanyajunRaceDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7699705951280932927L;

	@Override
	public JcGuanYaJunRace getRace(int lotteryType, String phase,
			String matchnum) {
		GuanyajunRacePK id = new GuanyajunRacePK();
		id.setLotteryType(lotteryType);
		id.setPhase(phase);
		id.setMatchNum(matchnum);
		return find(id);
	}

	@Override
	public JcGuanYaJunRace getGuanyajunResult(int lotteryType, String phase,
			String matchnum) {
		JcGuanYaJunRace race = getRace(lotteryType, phase, matchnum);
		if(race==null) {
			return null;
		}
		if(race.getPrizeStatus() == PrizeStatus.draw.value || race.getPrizeStatus() == PrizeStatus.rewarded.value) {
			return race;
		}
		return null;
	}

	@Override
	public String getMinCloseAndResultMatchid(int lotteryType, String phase) {
		StringBuilder sql = new StringBuilder();

		List<Integer> status = new ArrayList<Integer>();

		status.add(RaceStatus.CLOSE.value);
		status.add(RaceStatus.CANCEL.value);

		sql.append("select min(o.id.matchNum) from JcGuanYaJunRace o where o.status in (:status) and o.prizeStatus=:prizeStatus "
				+ "and o.id.lotteryType=:lotteryType and o.id.phase=:phase");

		List<String> matchids = this.getEntityManager().createQuery(sql.toString(), String.class).setParameter("status", status)
				.setParameter("lotteryType", lotteryType).setParameter("phase", phase)
				.setParameter("prizeStatus", PrizeStatus.result_set.value).getResultList();

		if (!matchids.isEmpty()) {
			return matchids.get(0);
		}
		return "";
	}

	@Override
	public List<JcGuanYaJunRace> getRaces(int lotteryType, String phase,
			String minMatchid, String maxMatchid) {
		StringBuilder sql = new StringBuilder();
		sql.append("matchNum>=:minMatchid").append(" and matchNum<=:maxMatchid")
			.append(" and id.lotteryType=:lotteryType").append(" and id.phase=:phase")
			.append(" order by matchNum");

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("minMatchid", minMatchid);
		condition.put("maxMatchid", maxMatchid);
		condition.put("lotteryType", lotteryType);
		condition.put("phase", phase);
		return findByCondition(sql.toString(), condition);
	}

	

}
