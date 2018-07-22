package com.lottery.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.PageBean;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseEncaseStatus;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.PhaseTimeStatus;
import com.lottery.common.contains.lottery.TerminalStatus;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.LotteryPhaseDAO;
import com.lottery.core.domain.LotteryPhase;

@Service
public class LotteryPhaseService {
	
	@Autowired
	private LotteryPhaseDAO dao;

	@Autowired
	private IdGeneratorDao idGeneratorDao;



	/**
	 * 根据彩种，彩种状态查询
	 * 
	 * @param lotteryType 彩种
	 * @param terminalStatus
	 *            彩期状态
	 * @return list
	 * */
	@Transactional
	public List<LotteryPhase> getByTerminalStatus(int lotteryType, int terminalStatus) {
		return dao.getByTerminalStatus(lotteryType, terminalStatus);
	}

	/**
	 * 新增
	 * */
	@Transactional
	public void insert(LotteryPhase lotteryPhase) {
		Long id = idGeneratorDao.getLotteryPhaseId();
		lotteryPhase.setId(id);
		dao.insert(lotteryPhase);
	}

	/**
	 * 根据期次和彩种查询期次信息
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase 期次
	 * */
	@Transactional
	public LotteryPhase getPhase(int lotteryType, String phase) throws Exception{
		return dao.getByTypeAndPhase(lotteryType, phase);

	}

	/**
	 * 查询某个彩种历史开奖
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param page
	 * @return
	 */
	@Transactional
	public List<LotteryPhase> historyRecord(int lotteryType, PageBean<LotteryPhase> page) {

		return dao.getHistoryList(lotteryType, page);
	}
	
	
	/**
	 * 查询某个彩种历史开奖,同时查出刚结束还未开奖的期
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param page
	 * @return
	 */
	@Transactional
	public List<LotteryPhase> historyRecordWithClosed(int lotteryType, PageBean<LotteryPhase> page) {

		return dao.getHistoryListWithClosed(lotteryType, page);
	}

	/**
	 * 查询 所有彩种上一期的开奖详情
	 * */
	@Transactional
	public List<LotteryPhase> getWinDetails() {
		List<LotteryPhase> phaseList = new ArrayList<LotteryPhase>();
		List<LotteryType> lotteryTypeList = LotteryType.get();
		for (LotteryType lotteryType : lotteryTypeList) {
			if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType) || LotteryType.getDc().contains(lotteryType)) {
				continue;
			}
			LotteryPhase phase = dao.getLastRecord(lotteryType.getValue());
			if (phase != null) {
				phaseList.add(phase);
			}
		}
		return phaseList;
	}

	/**
	 * 查询某个彩种上期开奖记录
	 * 
	 * @param lotteryType
	 *            彩种
	 * @return
	 */
	@Transactional
	public LotteryPhase getLastRecord(int lotteryType) {
		return dao.getLastRecord(lotteryType);
	}
	
	@Transactional
	public boolean updatePhase(Long id,String phase, Date endTicketTime,
			Date endSaleTime,Date hemaiEndTime,Date startSaleTime,Date drawTime,Date switchTime,
			int phaseEncaseStatus,int phaseStatus,int phaseTimeStatus,int terminalStatus, int forCurrent, Integer forSale) {
		boolean isNotify = false;

		LotteryPhase lp = dao.find(id);
		if (lp.getEndSaleTime().getTime() != endSaleTime.getTime() || endTicketTime.getTime() != endTicketTime.getTime() 
				|| lp.getHemaiEndTime().getTime() != hemaiEndTime.getTime()) {
			isNotify = true;
		}
		lp.setPhase(phase);
		lp.setEndTicketTime(endTicketTime);
		lp.setEndSaleTime(endSaleTime);
		lp.setHemaiEndTime(hemaiEndTime);
		lp.setStartSaleTime(startSaleTime);
		lp.setDrawTime(drawTime);
		lp.setSwitchTime(switchTime);
		lp.setPhaseEncaseStatus(phaseEncaseStatus);
		lp.setPhaseStatus(phaseStatus);
		lp.setPhaseTimeStatus(phaseTimeStatus);
		lp.setTerminalStatus(terminalStatus);
		lp.setForCurrent(forCurrent);
		lp.setForSale(forSale);
		dao.merge(lp);
		return isNotify;
	}

	@Transactional
	public void delete(String strs) {
		String[] ids = strs.split(",");
		for (String id : ids) {
			dao.remove(dao.find(Long.parseLong(id)));
		}
	}

	/**
	 * 根据彩种查询期信息
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param max
	 *            最大条数
	 * */
	@Transactional
	public List<LotteryPhase> getByLotteryType(Integer lotteryType, int max) {
		return dao.getByLotteryType(lotteryType, max);
	}

	/**
	 * 最新彩期获取
	 */
	@Transactional
	public List<LotteryPhase> getCurrentList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("forCurrent", YesNoStatus.yes.getValue());
		return dao.findByCondition(map);
	}

	@Transactional
	public void updateStatus(int lotteryType, String phase, int phaseStatus, int terminalStatus, int forSale, int forCurrent) {
		dao.updateStatus(lotteryType, phase, phaseStatus, terminalStatus, forSale, forCurrent);
	}

	/**
	 * 根据彩种期号查询彩期
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            新 期
	 * */
	@Transactional
	public LotteryPhase getByTypeAndPhase(int lotteryType, String phase) {
		try {
			return dao.getByTypeAndPhase(lotteryType, phase);
		} catch (Exception e) {
			return null;
		}

	}


	/**
	 *
	 * 根据彩种,彩期状态查询
	 * @param lotteryType 彩种
	 * @param phaseStatus 彩期状态
	 * */

	@Transactional
	public List<LotteryPhase> getByLotteryTypeAndStatus(int lotteryType, int phaseStatus) {
		return dao.getByLotteryTypeAndStatus(lotteryType, phaseStatus);
	}

	@Transactional
	public Long getCountByLotteryTypeAndStatus(int lotteryType, int phaseStatus) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("lotteryType", lotteryType);
		whereMap.put("phaseStatus", phaseStatus);
		return dao.getCountByCondition(whereMap);
	}

	/**
	 * 根据彩种，开售状态查询
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param forSale
	 *            是否销售
	 * 
	 * */
	@Transactional
	public List<LotteryPhase> getByTypeAndForSale(int lotteryType, int forSale) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryType", lotteryType);
		map.put("forSale", forSale);
		String orderByString = "order by phase asc";
		return dao.findByCondition(map, orderByString);
	}

	@Transactional
	public void update(LotteryPhase lotteryPhase) {
		dao.merge(lotteryPhase);
	}

	/**
	 * 设置为当前期
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * */
	@Transactional
	public void updateCurrent(int lotteryType, String phase) throws Exception{
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("forCurrent", YesNoStatus.yes.value);
		whereMap.put("lotteryType", lotteryType);
		List<LotteryPhase> phaseList = dao.findByCondition(whereMap);
		if (phaseList != null) {
			for (LotteryPhase loteryPhase : phaseList) {
				loteryPhase.setForCurrent(YesNoStatus.no.value);
				dao.merge(loteryPhase);
			}
		}
		LotteryPhase lotteryPhase = dao.getByTypeAndPhase(lotteryType, phase);
		lotteryPhase.setForCurrent(YesNoStatus.yes.value);
		dao.merge(lotteryPhase);

	}

	/**
	 * 获取当前，所有的彩种都有且只有一个当前期
	 * 
	 * @param lotteryType
	 *            当前期
	 * */
	@Transactional
	public LotteryPhase getCurrent(int lotteryType) {
		try {
			return dao.getCurrent(lotteryType);
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public void save(LotteryPhase lotteryPhase) {
		Long id = idGeneratorDao.getLotteryPhaseId();
		lotteryPhase.setId(id);
		dao.insert(lotteryPhase);
	}

	/**
	 * 查询某个彩种上几期开奖号码
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param num
	 *            最多取几期
	 * **/
	@Transactional
	public List<LotteryPhase> getWinlist(Integer lotteryType, int num) {
		return dao.findWininfoListByLottype(lotteryType, num);
	}

	
	/**
	 * 查询历史开奖
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param num
	 *            查询数量
	 * */
	@Transactional
	public List<LotteryPhase> getHistoryPhase(int lotteryType, int num) {
		return dao.getHistoryPhase(lotteryType, num);

	}

	/**
	 * 
	 * 查询彩期某期后的彩期(包括该期)
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param num
	 *            查询数量
	 */
	@Transactional
	public List<LotteryPhase> getNextPhaseWithCurrent(int lotteryType, String phase, int num) {
		return dao.getNextPhaseWithCurrent(lotteryType, phase, num);
	}
	/**
	 *
	 * 查询彩期某期后的彩期(不包括该期)
	 *
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param num
	 *            查询数量
	 */
	@Transactional
	public List<LotteryPhase> getNextPhaseNotWithCurrent(int lotteryType, String phase, int num) {
		return dao.getNextPhaseNotWithCurrent(lotteryType, phase, num);
	}

	/**
	 * 
	 * 查询彩期某期后的彩期(不包含该期号)
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param num
	 *            查询数量
	 */
	@Transactional
	public List<LotteryPhase> getNextPhase(int lotteryType, String phase, int num) {
		return dao.getNextPhase(lotteryType, phase, num);
	}

	/**
	 * 查询除了未开售的所有期
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param num
	 *            查询数量
	 * */
	@Transactional
	public List<LotteryPhase> getPhaseList(int lotteryType, int num) {
		return dao.getPhaseList(LotteryType.getPhaseTypeValue(lotteryType), num);
	}

	/**
	 * 更新截至销售时间
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param startSaleTime
	 *            开始销售时间
	 * @param endSaleTime
	 *            销售时间
	 */
	@Transactional
	public LotteryPhase savePhaseEndSaleTime(int lotteryType, String phase, Date startSaleTime, Date endSaleTime,Date endTicketTime) {

		LotteryPhase lotteryPhase = null;
		try {
			lotteryPhase = dao.getByTypeAndPhase(lotteryType, phase);
		} catch (Exception e) {

		}

		if (lotteryPhase == null) {
			lotteryPhase = new LotteryPhase();
			lotteryPhase.setLotteryType(lotteryType);
			lotteryPhase.setPhase(phase);
			lotteryPhase.setStartSaleTime(startSaleTime);
			lotteryPhase.setEndTicketTime(endTicketTime);
			lotteryPhase.setEndSaleTime(endSaleTime);
			lotteryPhase.setHemaiEndTime(endSaleTime);
			lotteryPhase.setDrawTime(endSaleTime);
			lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.value);
			lotteryPhase.setPhaseTimeStatus(PhaseTimeStatus.CORRECTION.value);
			lotteryPhase.setTerminalStatus(TerminalStatus.enable.value);
			lotteryPhase.setForSale(YesNoStatus.yes.value);
			lotteryPhase.setForCurrent(YesNoStatus.yes.value);
			lotteryPhase.setPhaseStatus(PhaseStatus.open.value);
			lotteryPhase.setCreateTime(new Date());
			lotteryPhase.setStartSaleTime(startSaleTime);
			Long id = idGeneratorDao.getLotteryPhaseId();
			lotteryPhase.setId(id);
			dao.insert(lotteryPhase);
		} else {
			lotteryPhase.setEndSaleTime(endSaleTime);
			lotteryPhase.setEndTicketTime(endTicketTime);
			lotteryPhase.setHemaiEndTime(endSaleTime);
			lotteryPhase.setStartSaleTime(startSaleTime);
			//lotteryPhase.setDrawTime(endSaleTime);
			lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.value);
			lotteryPhase.setPhaseTimeStatus(PhaseTimeStatus.CORRECTION.value);
			lotteryPhase.setTerminalStatus(TerminalStatus.enable.value);
			lotteryPhase.setForSale(YesNoStatus.yes.value);
			lotteryPhase.setForCurrent(YesNoStatus.yes.value);
			lotteryPhase.setPhaseStatus(PhaseStatus.open.value);
			lotteryPhase.setSwitchTime(new Date());
			dao.merge(lotteryPhase);
		}
		return lotteryPhase;

	}

	/**
	 * 更新开奖号码
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param winCode
	 *            开奖奖号码
	 * @throws Exception 
	 */
	@Transactional
	public void saveWincode(int lotteryType, String phase, String winCode) throws Exception {
		LotteryPhase lotteryPhase = dao.getByTypeAndPhase(lotteryType, phase);
		lotteryPhase.setWincode(winCode);
		lotteryPhase.setForCurrent(YesNoStatus.no.value);
		lotteryPhase.setForSale(YesNoStatus.no.value);
		lotteryPhase.setPhaseStatus(PhaseStatus.close.value);
		
		//lotteryPhase.setPhaseStatus(PhaseStatus.prize_ing.value);
		dao.merge(lotteryPhase);
	}

	/***
	 * 关闭彩种当前期
	 * 
	 * @param lotteryType
	 *            彩种
	 * */
	@Transactional
	public void closeCurrent(int lotteryType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryType", lotteryType);
		map.put("forCurrent", YesNoStatus.yes.value);
		List<LotteryPhase> phaseList = dao.findByCondition(map);
		if (phaseList != null && phaseList.size() > 0) {
			for (LotteryPhase lotteryPhase : phaseList) {
				lotteryPhase.setPhaseStatus(PhaseStatus.close.value);
				lotteryPhase.setForSale(YesNoStatus.no.value);
				lotteryPhase.setForCurrent(YesNoStatus.no.value);
				lotteryPhase.setTerminalStatus(TerminalStatus.disenable.value);
				lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.value);
				dao.merge(lotteryPhase);
			}
		}
	}

	/**
	 * 关闭彩种有效期
	 * */
	@Transactional
	public void closeEnablePhase(int lotteryType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryType", lotteryType);
		map.put("forCurrent", YesNoStatus.yes.value);
		List<LotteryPhase> phaseList = dao.findByCondition(map);
		if (phaseList != null && phaseList.size() > 0) {
			for (LotteryPhase lotteryPhase : phaseList) {
				lotteryPhase.setPhaseStatus(PhaseStatus.close.value);
				lotteryPhase.setForSale(YesNoStatus.no.value);
				lotteryPhase.setForCurrent(YesNoStatus.no.value);
				lotteryPhase.setTerminalStatus(TerminalStatus.disenable.value);
				lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.value);
				dao.merge(lotteryPhase);
			}
		}
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("lotteryType", lotteryType);
		statusMap.put("phaseStatus", PhaseStatus.open.value);
		List<LotteryPhase> openList = dao.findByCondition(map);
		if (openList != null && openList.size() > 0) {
			for (LotteryPhase lotteryPhase : phaseList) {
				lotteryPhase.setPhaseStatus(PhaseStatus.close.value);
				lotteryPhase.setForSale(YesNoStatus.no.value);
				lotteryPhase.setForCurrent(YesNoStatus.no.value);
				lotteryPhase.setTerminalStatus(TerminalStatus.disenable.value);
				lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.value);
				dao.merge(lotteryPhase);
			}
		}
	}

	/**
	 * 根据彩种状态查询彩期
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param statusList
	 *            状态集合
	 * @return list
	 * */
	@Transactional
	public List<LotteryPhase> getByLotteryAndStatuses(int lotteryType, List<Integer> statusList) {
		return dao.getByLotteryAndStatuses(lotteryType, statusList);
	}
	/**
	 * 修改彩期状态
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param phaseStatus 彩期状态
	 * */
	@Transactional
	public void updatePhasePrizeState(int lotteryType,String phase,int phaseStatus)throws Exception{
		dao.updatePhasePrizeState(lotteryType, phase, phaseStatus);
	}
	/**
	 * 修改算奖状态状态
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param encaseStatus 算奖状态
	 * */
	@Transactional
	public void updatePhaseEncashState(int lotteryType,String phase,int encaseStatus)throws Exception{
		dao.updatePhaseEncashState(lotteryType, phase, encaseStatus);
	}
	
	/**
	 * 修改彩期终端状态
	 * 
	 * */
	@Transactional
	public int updateTerminalStatus(int terminalStatus,Long id){
		return dao.updateTerminalStatus(terminalStatus, id);
	}
	
	/**
	 * 修改彩期终端状态
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * */
	@Transactional
	public int updateTerminalStatus(int terminalStatus,int lotteryType,String phase){
		return dao.updateTerminalStatus(terminalStatus, lotteryType, phase);
	}
	
	/**
	 * 新期切换
	 * @param phaseStatus 彩期状态
	 * @param forSale 销售状态
	 * @param forCurrent 是否当前期
	 * @param terminalStatus 终端是否可出票
	 * @param id 
	 * */
	@Transactional
	public int phaseSwitch(int phaseStatus, int forSale, int forCurrent,int terminalStatus,Long id){
		return dao.phaseSwitch(phaseStatus, forSale, forCurrent,terminalStatus, id);
	}

	/**
	 * 新期切换
	 * @param phaseStatus 彩期状态
	 * @param forSale 销售状态
	 * @param forCurrent 是否当前期
	 * @param terminalStatus 终端是否可出票
	 * @param lotteryType 彩种
	 * @param phase  期号
	 * */
	@Transactional
	public int phaseSwitch(int phaseStatus, int forSale, int forCurrent,int terminalStatus,int lotteryType, String phase){
		return dao.phaseSwitch(phaseStatus, forSale, forCurrent, lotteryType,terminalStatus,phase);
	}

	@Transactional
	public int saveWininfo(int lotteryType, String phase,String wincode,String info,String poolAmount,String addPoolAmount,String saleAmount,String whereFrom){
		return dao.saveWininfo(lotteryType, phase, wincode,info, poolAmount, addPoolAmount, saleAmount, whereFrom);
	}


	/**
	 * 修改出票截止时间
	 * @param lotteryType  彩种
	 * @param phase 期号
	 * @param endTicketTime 停止出票时间
	 * */
	@Transactional
	public int updateEndTicketTime(int lotteryType,String phase,Date endTicketTime){
		return dao.updateEndTicketTime(lotteryType, phase, endTicketTime);
	}


	
}
