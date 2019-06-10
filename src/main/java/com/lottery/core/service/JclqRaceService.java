package com.lottery.core.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.contains.LotteryConstant;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.RaceSaleStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.util.PairValue;
import com.lottery.core.cache.model.LotteryTicketConfigCacheModel;
import com.lottery.core.dao.JclqRaceDAO;
import com.lottery.core.domain.JclqRace;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.handler.PrizeErrorHandler;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.scheduler.fetcher.sp.impl.JclqMatchSpService;

@Service
public class JclqRaceService {
	protected final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	private JclqRaceDAO jclqRaceDAO;
	@Autowired
	private PrizeErrorHandler prizeErrorHandler;
	private Lock lock = new ReentrantLock();
	private Map<String, PairValue<List<JclqRace>, Long>> jclqMap = new ConcurrentHashMap<String, PairValue<List<JclqRace>, Long>>();
	private long containerTimeoutMillis = 30000L; // 先默认半分钟缓存
	@Autowired
	private JclqMatchSpService jclqMatchSpService;


	@Transactional
	public JclqRace getByMatchNum(String matchNum) {
		return jclqRaceDAO.find(matchNum);
	}
	@Transactional
	public JclqRace get(String id) {
		return jclqRaceDAO.find(id);
	}

	@Transactional
	public void save(JclqRace jclqRace) {
		jclqRaceDAO.insert(jclqRace);
	}

	@Transactional
	public void update(JclqRace jclqRace) {
		jclqRaceDAO.merge(jclqRace);
	}

	/**
	 * 根据彩期，销售状态查询对阵
	 * 
	 * @param phase
	 *            彩期
	 * @param statusList
	 *            销售状态
	 * */
	@Transactional
	public List<JclqRace> getByPhaseAndStatus(String phase, List<Integer> statusList) {
		String whereSql = "phase=:phase and status in(:status) and endSaleTime>=:endSaleTime";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phase", phase);
		map.put("status", statusList);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);
		map.put("endSaleTime", calendar.getTime());
		return jclqRaceDAO.findByCondition(whereSql, map);

	}

	/**
	 * 根据彩期，状态集合查询对阵
	 * 
	 * @param statusList
	 *            状态集合
	 * */
	@Transactional
	public List<JclqRace> getByStatus(List<Integer> statusList) {
		String whereSql = "status in(:status) and endSaleTime>=:endSaleTime";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", statusList);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);
		map.put("endSaleTime", calendar.getTime());
		return jclqRaceDAO.findByCondition(whereSql, map);

	}

	/**
	 * 根据彩期，状态查询对阵
	 * 
	 * @param status
	 *            销售状态
	 * */
	@Transactional
	public List<JclqRace> getByStatus(int status) {
		String whereSql = "status =:status";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		return jclqRaceDAO.findByCondition(whereSql, map);

	}

	@Transactional
	public void merge(JclqRace jclqRace) {
		JclqRace race = jclqRaceDAO.find(jclqRace.getMatchNum());
		if (race == null) {
			return;
		}
		race.setEndSaleTime(jclqRace.getEndSaleTime());

		race.setStatus(jclqRace.getStatus());
		race.setPrizeStatus(jclqRace.getPrizeStatus());
		race.setMatchDate(jclqRace.getMatchDate());
		race.setMatchName(jclqRace.getMatchName());
		race.setOfficialDate(jclqRace.getOfficialDate());
		race.setOfficialNum(jclqRace.getOfficialNum());
		race.setOfficialWeekDay(jclqRace.getOfficialWeekDay());
		race.setHomeTeam(jclqRace.getHomeTeam());
		race.setAwayTeam(jclqRace.getAwayTeam());

		race.setStaticSaleSfStatus(jclqRace.getStaticSaleSfStatus());
		race.setStaticSaleRfsfStatus(jclqRace.getStaticSaleRfsfStatus());
		race.setStaticSaleSfcStatus(jclqRace.getStaticSaleSfcStatus());
		race.setStaticSaleDxfStatus(jclqRace.getStaticSaleDxfStatus());
		
		race.setDgStaticSaleSfStatus(jclqRace.getDgStaticSaleSfStatus());
		race.setDgStaticSaleRfsfStatus(jclqRace.getDgStaticSaleRfsfStatus());
		race.setDgStaticSaleSfcStatus(jclqRace.getDgStaticSaleSfcStatus());
		race.setDgStaticSaleDxfStatus(jclqRace.getDgStaticSaleDxfStatus());
		
		race.setFinalScore(jclqRace.getFinalScore());
		race.setStaticHandicap(jclqRace.getStaticHandicap());
		race.setStaticPresetScore(jclqRace.getStaticPresetScore());
		race.setUpdateTime(new Date());
		jclqRaceDAO.merge(race);
	}

	public List<JclqRace> getEnableJclqRace(int lotteryType, String type) {
		List<JclqRace> allList = getFromCache(RaceStatus.OPEN.getValue(), lotteryType);
		List<JclqRace> enableList = new ArrayList<JclqRace>();
		Map<String, JclqRace> map = new HashMap<String, JclqRace>();
		if (allList != null && allList.size() != 0) {
			for (JclqRace jclqRace : allList) {
				jclqStaticSp(jclqRace, lotteryType);
				if (type.equals(LotteryConstant.DAN_GUAN)) {

					if (lotteryType == LotteryType.JCLQ_DXF.getValue()) {
						if (jclqRace.getDgStaticSaleDxfStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jclqRace.getMatchNum(), jclqRace);
						}
					}
					if (lotteryType == LotteryType.JCLQ_RFSF.getValue()) {
						if (jclqRace.getDgStaticSaleRfsfStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jclqRace.getMatchNum(), jclqRace);
						}
					}
					if (lotteryType == LotteryType.JCLQ_SF.getValue()) {
						if (jclqRace.getDgStaticSaleSfStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jclqRace.getMatchNum(), jclqRace);
						}
					}
					if (lotteryType == LotteryType.JCLQ_SFC.getValue()) {
						if (jclqRace.getDgStaticSaleSfcStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jclqRace.getMatchNum(), jclqRace);
						}
					}
				}
				if (type.equals(LotteryConstant.GUO_GUAN)) {

					if (lotteryType == LotteryType.JCLQ_DXF.getValue()) {
						if (jclqRace.getStaticSaleDxfStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jclqRace.getMatchNum(), jclqRace);
						}
					}
					if (lotteryType == LotteryType.JCLQ_RFSF.getValue()) {
						if (jclqRace.getStaticSaleRfsfStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jclqRace.getMatchNum(), jclqRace);
						}
					}
					if (lotteryType == LotteryType.JCLQ_SF.getValue()) {
						if (jclqRace.getStaticSaleSfStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jclqRace.getMatchNum(), jclqRace);
						}
					}
					if (lotteryType == LotteryType.JCLQ_SFC.getValue()) {
						if (jclqRace.getStaticSaleSfcStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jclqRace.getMatchNum(), jclqRace);
						}
					}
					if (lotteryType == LotteryType.JCLQ_HHGG.getValue()) {
						jclqStaticSp(jclqRace, lotteryType);
						map.put(jclqRace.getMatchNum(), jclqRace);
					}
				}


			}
			enableList.addAll(map.values());
		}

		return enableList;
	}

	private List<JclqRace> getFromCache(int status, int lotteryType) {
		String mapKey = "jclqListCache";
		long currentTimeMillis = System.currentTimeMillis();
		PairValue<List<JclqRace>, Long> pairValue = jclqMap.get(mapKey);
		if (pairValue != null && pairValue.getRight() != null && pairValue.getRight() + containerTimeoutMillis > currentTimeMillis) {
			// 如果缓存已存在且未过期，直接使用缓存
			return pairValue.getLeft();
		}
		try {
			lock.lock();
			if (pairValue != null && pairValue.getRight() != null && pairValue.getRight() + containerTimeoutMillis > currentTimeMillis) {
				// 如果缓存已存在且未过期，直接使用缓存
				return pairValue.getLeft();
			}
			List<JclqRace> list = getByStatus(status);

			pairValue = new PairValue<List<JclqRace>, Long>(list, currentTimeMillis);
			jclqMap.put(mapKey, pairValue);
			return list;
		} catch (Exception e) {
			return null;
		} finally {
			lock.unlock();
		}

	}

	/**
	 * 串关sp
	 * */
	private void jclqStaticSp(JclqRace jclqRace, int lotteryType) {
		try {

			MatchSpDomain jclqStaticSp = jclqMatchSpService.get(jclqRace.getMatchNum());
			if (jclqStaticSp == null)
				return;
			Map<String, Map<String, Object>> lotteryMap = jclqStaticSp.getLotteryType();
			if (lotteryMap == null) {
				return;
			}
			if (lotteryType == LotteryType.JCLQ_HHGG.getValue()) {
				jclqRace.setSpStr(JSONObject.fromObject(lotteryMap).toString());
			} else {
				jclqRace.setSpStr(JSONObject.fromObject(jclqStaticSp.getLotteryType().get(lotteryType + "")).toString());
			}
		} catch (Exception e) {
			logger.error("获取sp出错", e);
		}
	}



	/**
	 * 根据期号查询对阵
	 * 
	 * @param phase
	 *            期号
	 * @return List
	 * */
	@Transactional
	public List<JclqRace> getByPhase(String phase) {
		return jclqRaceDAO.getByPhase(phase);
	}

	/**
	 * 查找赛事状态为结束或者取消的，赛果状态为 赛果已公布 的 最小的场次id
	 * 
	 * @return
	 */
	@Transactional
	public String getMinCloseAndResultMatchid() {
		return jclqRaceDAO.getMinCloseAndResultMatchid();
	}

	/**
	 * 查找赛事状态为结束或者取消的，赛果状态为 赛果已公布 的 最大的场次id
	 * 
	 * @return
	 */
	@Transactional
	public String getMaxCloseAndResultMatchid() {
		return jclqRaceDAO.getMaxCloseAndResultMatchid();
	}
	
	/**
	 * 根据开奖状态查询已结束,取消赛果的赛事
	 * @param phase 期号
	 * @param prizeStatus 开奖状态
	 * @return
	 */
	@Transactional
	public List<JclqRace> getCloseByPhaseAndPrizeStatus(String phase,Integer prizeStatus){
		return jclqRaceDAO.getCloseByPhaseAndPrizeStatus(phase, prizeStatus);
	}
	
	
	@Transactional
	public List<JclqRace> getJclqResult(String officialDate) {
		String whereSql = "official_date=:official_date and prize_status=:prize_status";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("official_date", officialDate);
		map.put("prize_status", PrizeStatus.draw.value);
		return jclqRaceDAO.findByCondition(whereSql, map);
	}
	
	
	@Transactional
	public List<JclqRace> getJclqPhaseResult(String phase) {
		String whereSql = "phase=:phase and prizeStatus=:prize_status";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phase", phase);
		map.put("prize_status", PrizeStatus.draw.value);
		return jclqRaceDAO.findByCondition(whereSql, map);
	}

}
