package com.lottery.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
import com.lottery.core.dao.JczqRaceDao;
import com.lottery.core.domain.JczqRace;
import com.lottery.core.handler.PrizeErrorHandler;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.scheduler.fetcher.sp.impl.JczqMatchSpService;

import net.sf.json.JSONObject;

@Service
public class JczqRaceService {
	protected final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	protected JczqRaceDao dao;
	@Autowired
	protected JczqMatchSpService jczqMatchSpService;
	private Map<String, PairValue<List<JczqRace>, Long>> jczqMap = new ConcurrentHashMap<String, PairValue<List<JczqRace>, Long>>();
	private long containerTimeoutMillis = 30000L; // 先默认半分钟缓存
	private Lock lock = new ReentrantLock();


	@Transactional
	public JczqRace getByMatchNum(String matchNum) {
		return dao.find(matchNum);
	}
	@Transactional
	public JczqRace get(String id) {
		return dao.find(id);
	}

	@Transactional
	public void save(JczqRace jczqRace) {
		dao.insert(jczqRace);
	}

	@Transactional
	public void update(JczqRace jczqRace) {
		dao.merge(jczqRace);
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
	public List<JczqRace> getByPhaseAndStatus(String phase, List<Integer> statusList) {
		String whereSql = "phase=:phase and status in(:status)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phase", phase);
		map.put("status", statusList);
		return dao.findByCondition(whereSql, map);

	}

	/**
	 * 根据彩期，状态集合查询对阵
	 * 
	 * @param statusList
	 *            销售状态集合
	 * */
	@Transactional
	public List<JczqRace> getByStatus(List<Integer> statusList) {
		String whereSql = "status in(:status)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", statusList);
		return dao.findByCondition(whereSql, map);

	}

	/**
	 * 根据彩期，状态查询对阵
	 * 
	 * @param status
	 *            销售状态
	 * */
	@Transactional
	public List<JczqRace> getByStatus(int status) {
		String whereSql = "status =:status";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		return dao.findByCondition(whereSql, map);
	}

	@Autowired
	private PrizeErrorHandler prizeErrorHandler;

	@Transactional
	public void merge(JczqRace jczqRace) {
		JczqRace pj = dao.find(jczqRace.getMatchNum());
		if (pj == null) {
			return;
		}

		pj.setEndSaleTime(jczqRace.getEndSaleTime());
		pj.setStatus(jczqRace.getStatus());
		pj.setMatchDate(jczqRace.getMatchDate());
		pj.setMatchName(jczqRace.getMatchName());
		pj.setOfficialDate(jczqRace.getOfficialDate());

		pj.setOfficialNum(jczqRace.getOfficialNum());
		pj.setOfficialWeekDay(jczqRace.getOfficialWeekDay());
		pj.setHandicap(jczqRace.getHandicap());
		pj.setHomeTeam(jczqRace.getHomeTeam());
		pj.setAwayTeam(jczqRace.getAwayTeam());

		pj.setStaticSaleSpfStatus(jczqRace.getStaticSaleSpfStatus());
		pj.setStaticSaleSpfWrqStatus(jczqRace.getStaticSaleSpfWrqStatus());
		pj.setStaticSaleBfStatus(jczqRace.getStaticSaleBfStatus());
		pj.setStaticSaleJqsStatus(jczqRace.getStaticSaleJqsStatus());
		pj.setStaticSaleBqcStatus(jczqRace.getStaticSaleBqcStatus());
		
		pj.setDgStaticSaleSpfStatus(jczqRace.getDgStaticSaleSpfStatus());
		pj.setDgStaticSaleSpfWrqStatus(jczqRace.getDgStaticSaleSpfWrqStatus());
		pj.setDgStaticSaleBfStatus(jczqRace.getDgStaticSaleBfStatus());
		pj.setDgStaticSaleJqsStatus(jczqRace.getDgStaticSaleJqsStatus());
		pj.setDgStaticSaleBqcStatus(jczqRace.getDgStaticSaleBqcStatus());

		pj.setPrizeStatus(jczqRace.getPrizeStatus());
		pj.setFirstHalf(jczqRace.getFirstHalf());
		pj.setSecondHalf(jczqRace.getSecondHalf());
		pj.setFinalScore(jczqRace.getFinalScore());
		pj.setPrizeSpf(jczqRace.getPrizeSpf());
		pj.setPrizeJqs(jczqRace.getPrizeJqs());
		pj.setPrizeBqc(jczqRace.getPrizeBqc());
		pj.setPrizeSpfWrq(jczqRace.getPrizeSpfWrq());

		dao.merge(pj);
	}

	public List<JczqRace> getEnableJczqRace(int lotteryType, String type) {
		List<JczqRace> enableList = new ArrayList<JczqRace>();
		List<JczqRace> allList = getFromCache(RaceStatus.OPEN.getValue(), lotteryType);
		Map<String, JczqRace> map = new HashMap<String, JczqRace>();
		if (allList != null && allList.size() != 0) {
			for (JczqRace jczqRace : allList) {
				jczqStaticSp(jczqRace, lotteryType);
				if (type.equals(LotteryConstant.DAN_GUAN)) {

					if (lotteryType == LotteryType.JCZQ_BF.getValue()) {
						if (jczqRace.getDgStaticSaleBfStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jczqRace.getMatchNum(), jczqRace);
						}
					}
					if (lotteryType == LotteryType.JCZQ_BQC.getValue()) {
						if (jczqRace.getDgStaticSaleBqcStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jczqRace.getMatchNum(), jczqRace);
						}
					}
					if (lotteryType == LotteryType.JCZQ_JQS.getValue()) {
						if (jczqRace.getDgStaticSaleJqsStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jczqRace.getMatchNum(), jczqRace);
						}
					}
					if (lotteryType == LotteryType.JCZQ_RQ_SPF.getValue()) {
						if (jczqRace.getDgStaticSaleSpfStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jczqRace.getMatchNum(), jczqRace);
						}
					}
					if (lotteryType == LotteryType.JCZQ_SPF_WRQ.getValue()) {
						if (jczqRace.getDgStaticSaleSpfWrqStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jczqRace.getMatchNum(), jczqRace);
						}
					}
				}
				if (type.equals(LotteryConstant.GUO_GUAN)) {

					if (lotteryType == LotteryType.JCZQ_BF.getValue()) {
						if (jczqRace.getStaticSaleBfStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jczqRace.getMatchNum(), jczqRace);
						}
					}
					if (lotteryType == LotteryType.JCZQ_BQC.getValue()) {
						if (jczqRace.getStaticSaleBqcStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jczqRace.getMatchNum(), jczqRace);
						}
					}
					if (lotteryType == LotteryType.JCZQ_JQS.getValue()) {
						if (jczqRace.getStaticSaleJqsStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jczqRace.getMatchNum(), jczqRace);
						}
					}
					if (lotteryType == LotteryType.JCZQ_RQ_SPF.getValue()) {
						if (jczqRace.getStaticSaleSpfStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jczqRace.getMatchNum(), jczqRace);
						}
					}
					if (lotteryType == LotteryType.JCZQ_SPF_WRQ.getValue()) {
						if (jczqRace.getStaticSaleSpfWrqStatus() == RaceSaleStatus.SALE_OPEN.getValue()) {
							map.put(jczqRace.getMatchNum(), jczqRace);
						}
					}
					if (lotteryType == LotteryType.JCZQ_HHGG.getValue()) {
						jczqStaticSp(jczqRace, lotteryType);
						map.put(jczqRace.getMatchNum(), jczqRace);
					}
				}

			}
			enableList.addAll(map.values());
		}
		return enableList;

	}

	@Transactional
	public List<JczqRace> getJczqResult(String officialDate) {
		String whereSql = "official_date=:official_date and prize_status=:prize_status";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("official_date", officialDate);
		map.put("prize_status", PrizeStatus.draw.value);
		return dao.findByCondition(whereSql, map);
	}
	
	
	@Transactional
	public List<JczqRace> getJczqPhaseResult(String phase) {
		String whereSql = "phase=:phase and prizeStatus=:prize_status";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phase", phase);
		map.put("prize_status", PrizeStatus.draw.value);
		return dao.findByCondition(whereSql, map);
	}

	private List<JczqRace> getFromCache(int status, int lotteryType) {
		String mapKey = "jczqListCache";
		long currentTimeMillis = System.currentTimeMillis();
		PairValue<List<JczqRace>, Long> pairValue = jczqMap.get(mapKey);
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
			List<JczqRace> list = getByStatus(status);


			pairValue = new PairValue<List<JczqRace>, Long>(list, currentTimeMillis);
			jczqMap.put(mapKey, pairValue);
			return list;
		} catch (Exception e) {
			logger.error("获取缓存出错", e);
			return null;
		} finally {
			lock.unlock();
		}

	}

	/**
	 * 串关sp
	 * */
	private void jczqStaticSp(JczqRace jczqRace, int lotteryType) {
		try {
			MatchSpDomain jczqStaticSp = jczqMatchSpService.get(jczqRace.getMatchNum());
			if (jczqStaticSp == null)
				return;
			Map<String, Map<String, Object>> lotteryMap = jczqStaticSp.getLotteryType();
			if (lotteryMap == null) {
				return;
			}
			if (lotteryType == LotteryType.JCZQ_HHGG.getValue()) {
				jczqRace.setSpStr(JSONObject.fromObject(lotteryMap).toString());
			} else {
				jczqRace.setSpStr(JSONObject.fromObject(lotteryMap.get(lotteryType + "")).toString());
			}
		} catch (Exception e) {
			logger.error("获取竞彩sp出错", e);
		}
	}



	/**
	 * 根据期号查询对阵
	 * 
	 * @param phase
	 *            期号
	 * */
	public List<JczqRace> getByPhase(String phase) {
		return dao.getByPhase(phase);
	}
	/**
	 * 查找赛事状态为结束或者取消的，赛果状态为初赛过未审核的 最小的场次id
	 * @return
	 */
	@Transactional
	public String getMinCloseAndResultMatchid() {
	
		return dao.getMinCloseAndResultMatchid();
	}
	/**
	 * 查找赛事状态为结束或者取消的，赛果状态为初赛过未审核的 最大的场次id
	 * @return
	 */
	@Transactional
	public String getMaxCloseAndResultMatchid() {
	
		return dao.getMaxCloseAndResultMatchid();
	}
	
	/**
	 * 根据开奖状态查询已结束,取消赛果的赛事
	 * @param phase 期号
	 * @param prizeStatus 开奖状态
	 * @return
	 */
	@Transactional
	public List<JczqRace> getCloseByPhaseAndPrizeStatus(String phase,Integer prizeStatus){
		return dao.getCloseByPhaseAndPrizeStatus(phase, prizeStatus);
	}
}
