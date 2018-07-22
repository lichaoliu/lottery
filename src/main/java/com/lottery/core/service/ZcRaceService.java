package com.lottery.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.ZcRaceDAO;
import com.lottery.core.domain.ZcRace;

@Service
public class ZcRaceService {
	@Autowired
	private IdGeneratorDao idGeneratorDao;
	@Autowired
	private ZcRaceDAO zcRaceDAO;

	@Transactional
	public void save(ZcRace zcRace) {
		Long id = idGeneratorDao.getZcRaceId();
		zcRace.setId(id);
		zcRaceDAO.insert(zcRace);
	}

	/**
	 * 根据彩种，期号,场次号查询
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param matchNum
	 *            场次编号
	 * */
	@Transactional
	public ZcRace getByLotteryPhaseAndNum(int lotteryType, String phase, int matchNum) {

		try {
			return zcRaceDAO.getByLotteryPhaseAndNum(lotteryType, phase, matchNum);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 根据彩种，期号查询
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * */
	@Transactional
	public List<ZcRace> getByLotteryTypeAndPhase(int lotteryType, String phase) {
		return zcRaceDAO.getByLotteryTypeAndPhase(lotteryType, phase);
	}

	@Transactional
	public void update(ZcRace zcRace) {
		zcRaceDAO.update(zcRace);
	}

	@Transactional
	public void saveOrUpdate(ZcRace zcRace) {
		if (zcRace.getId() == null) {
			zcRace.setCreateTime(new Date());
			save(zcRace);
			return;
		}
		ZcRace zr = zcRaceDAO.find(zcRace.getId());
		if (zr == null) {
			return;
		}
		zr.setAverageIndex(zcRace.getAverageIndex());
		zr.setAwayTeam(zcRace.getAwayTeam());
		zr.setExt(zcRace.getExt());
		zr.setFinalScore(zcRace.getFinalScore());
		zr.setHalfScore(zcRace.getHalfScore());
		zr.setHomeTeam(zcRace.getHomeTeam());
		zr.setIsPossibledelay(zcRace.getIsPossibledelay());
		zr.setMatchDate(zcRace.getMatchDate());
		zr.setMatchId(zcRace.getMatchId());
		zr.setMatchName(zcRace.getMatchName());
		zr.setMatchNum(zcRace.getMatchNum());
		zr.setPhase(zcRace.getPhase());
		zcRaceDAO.merge(zr);
	}

}
