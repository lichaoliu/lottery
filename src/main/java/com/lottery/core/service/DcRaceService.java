package com.lottery.core.service;

import java.util.ArrayList;
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

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.DcType;
import com.lottery.common.contains.lottery.LotteryType;

import com.lottery.common.util.PairValue;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.cache.model.LotteryTicketConfigCacheModel;
import com.lottery.core.dao.DcRaceDAO;
import com.lottery.core.domain.DcRace;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.handler.PrizeErrorHandler;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.scheduler.fetcher.sp.impl.BdMatchSpService;

@Service
public class DcRaceService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DcRaceDAO dcRaceDAO;
	@Autowired
	private IdGeneratorDao idGeneratorDao;
	@Autowired
	private LotteryTicketConfigCacheModel lotteryTicketConfigCacheModel;
	@Autowired
	private BdMatchSpService bdMatchSpService;
	private Map<String, PairValue<List<DcRace>, Long>> dcMap = new ConcurrentHashMap<String, PairValue<List<DcRace>, Long>>();
	private long containerTimeoutMillis = 30000L;

	private Lock lock = new ReentrantLock();

	@Transactional
	public void save(DcRace dcRace) {
		Long id = idGeneratorDao.getDcRaceId();
		dcRace.setId(id);
		dcRaceDAO.insert(dcRace);
	}

	@Transactional
	public void update(DcRace dcRace) {
		dcRaceDAO.merge(dcRace);
	}

	/**
	 * 根据期号,单场类型,对阵状态查询
	 * 
	 * @param phase
	 *            期号
	 * @param dcType
	 *            类型
	 * @param statusList
	 *            对阵状态
	 * */
	@Transactional
	public List<DcRace> getByPhaseAndStatus(String phase, int dcType, List<Integer> statusList) {
		return dcRaceDAO.getByPhaseAndStatus(phase, dcType, statusList);
	}

	/**
	 * 根据期号,单场类型查询
	 * 
	 * @param phase
	 *            期号
	 * @param dcType
	 *            类型
	 * */
	@Transactional
	public List<DcRace> getByPhaseAndType(String phase, int dcType) {
		List<DcRace> raceList=new ArrayList<DcRace>();
		PageBean<DcRace> pageBean = new PageBean<DcRace>();
		int max=15;
		pageBean.setMaxResult(max);
		int page = 1;
		while(true){
			List<DcRace> races = null;
			pageBean.setPageIndex(page);
			try{
				races=dcRaceDAO.getByPhaseAndType(phase, dcType,pageBean);
			}catch(Exception e){
				logger.error("取订单出错",e);
				break;
			}
			if(races!=null&&races.size()>0){
				raceList.addAll(races);
				if (races.size() < max) {
					logger.info("读取到的方案列表不足一页，已读取结束");
					break;
				}
			}else{
				break;
			}
			page++;
		}
		return raceList;
	}

	public List<DcRace> getEnableRace(int lotteryType, String phase, int dcType) {
		List<DcRace> raceList = getByPhaseAndType(phase, dcType);
		if (raceList != null && raceList.size() > 0)
			for (DcRace dcRace : raceList) {
				Date end = dcRace.getEndSaleTime();
				Date rend = getEndSaleForwardDate(end, lotteryType);
				dcRace.setEndSaleTime(rend);
			}
		return raceList;
	}

	public List<DcRace> enableRace(int lotteryType, String phase, int dcType) { 

		long currentTimeMillis = System.currentTimeMillis();
		String mapKey = "dc_race_" + lotteryType + phase + dcType;
		PairValue<List<DcRace>, Long> pairValue = dcMap.get(mapKey);
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
			List<DcRace> list = getEnableRace(lotteryType, phase, dcType);

			List<DcRace> dcList = new ArrayList<DcRace>();

			if (list != null) {
				for (DcRace dcRace : list) {
					String id = dcRace.getPhase() +lotteryType+ dcRace.getMatchNum();

					// 赔率增加
					MatchSpDomain dcSp = bdMatchSpService.get(id);
					if (dcSp != null) {
						Map<String, Map<String, Object>> lottery_type = dcSp.getLotteryType();

						if (lottery_type != null) {
							dcRace.setSpStr(JSONObject.fromObject(lottery_type.get(lotteryType + "")).toString());
						}
					}
					dcList.add(dcRace);
				}
			}
			pairValue = new PairValue<List<DcRace>, Long>(dcList, currentTimeMillis);
			dcMap.put(mapKey, pairValue);
			
			return dcList;
		} catch (Exception e) {
			return null;
		} finally {
			lock.unlock();
		}
	}


	/**
	 * 根据彩种，期号,场次号查询
	 * @param phase
	 *            期号
	 * @param matchNum
	 *            场次编号
	 * @param  dcType 单场类型
	 *                @see  DcType
	 * */
	public DcRace getByPhase(String phase, Integer matchNum,int dcType) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("phase", phase);
		whereMap.put("matchNum", matchNum);
		whereMap.put("dcType", dcType);
		try {
			return dcRaceDAO.findByUnique(whereMap);
		} catch (Exception e) {
			return null;
		}
	}
	@Autowired
	PrizeErrorHandler prizeErrorHandler;

	@Transactional
	public void merge(DcRace dcRace) {
		DcRace dc = dcRaceDAO.find(dcRace.getId());
		if (dc == null) {
			return;
		}
		dc.setUpdateTime(new Date());
		// dc.setPhase(dcRace.getPhase());
		// dc.setDcType(dcRace.getDcType());
		// dc.setMatchNum(dcRace.getMatchNum());
		dc.setMatchDate(dcRace.getMatchDate());
		dc.setHomeTeam(dcRace.getHomeTeam());
		dc.setAwayTeam(dcRace.getAwayTeam());
		dc.setHandicap(dcRace.getHandicap());
		dc.setWholeScore(dcRace.getWholeScore());
		dc.setHalfScore(dcRace.getHalfScore());
		dc.setMatchName(dcRace.getMatchName());
		dc.setPrizeStatus(dcRace.getPrizeStatus());
		dc.setStatus(dcRace.getStatus());
		dc.setSpSfp(dcRace.getSpSfp());
		dc.setSpSxds(dcRace.getSpSxds());
		dc.setSpJqs(dcRace.getSpJqs());
		dc.setSpBf(dcRace.getSpBf());
		dc.setSpBcsfp(dcRace.getSpBcsfp());
		dc.setSpSf(dcRace.getSpSf());
		dcRaceDAO.merge(dc);
	}

	
	private Date getEndSaleForwardDate(Date date, Integer lotteryType) {
		Date returnDate = date;
		if (lotteryTicketConfigCacheModel != null && date != null) {
			try {
				LotteryTicketConfig lotteryTicketConfig = lotteryTicketConfigCacheModel.get(LotteryType.getPhaseType(lotteryType));
				if (lotteryTicketConfig != null) {
					if (lotteryTicketConfig.getEndSaleForward() != null) {
						long time = date.getTime() - lotteryTicketConfig.getEndSaleForward().longValue();
						returnDate = new Date(time);
					}
				}
			} catch (Exception e) {
				logger.error("获取销售提前截止时间出错", e);
			}

		}
		return returnDate;
	}

	/**
	 * 查找赛事状态为结束或者取消的，赛果状态为 结果已公布 的 最小的场次id
	 * 
	 * @return
	 */
	@Transactional
	public Integer getMinCloseAndResultMatchid(String phase, DcType type) {
		return dcRaceDAO.getMinCloseAndResultMatchid(phase, type);
	}

	/**
	 * 查找赛事状态为结束或者取消的，赛果状态为 结果已公布 的 最大的场次id
	 * 
	 * @return
	 */
	@Transactional
	public Integer getMaxCloseAndResultMatchid(String phase, DcType type) {
		return dcRaceDAO.getMaxCloseAndResultMatchid(phase, type);
	}

	/**
	 * 根据开奖状态查询已结束,取消赛果的赛事
	 * 
	 * @param phase
	 *            期号
	 * @param prizeStatus
	 *            开奖状态
	 * @return
	 */
	@Transactional
	public List<DcRace> getCloseByPhaseAndPrizeStatus(String phase, Integer prizeStatus, DcType dcType) {
		return dcRaceDAO.getCloseByPhaseAndPrizeStatus(phase, prizeStatus, dcType);
	}
}
