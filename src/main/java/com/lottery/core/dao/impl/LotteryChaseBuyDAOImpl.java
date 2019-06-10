package com.lottery.core.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.ChaseBuyStatus;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.LotteryChaseBuyDAO;
import com.lottery.core.domain.LotteryChaseBuy;
@Repository
public class LotteryChaseBuyDAOImpl extends AbstractGenericDAO<LotteryChaseBuy, String> implements LotteryChaseBuyDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8149157303538118799L;

	@Override
	public List<LotteryChaseBuy> getByChaseIdLotteryTypePhase(String chaseId, int lotteryType, String phase) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("chaseId", chaseId);
		map.put("lotteryType", lotteryType);
		map.put("phase", phase);
		
		return findByCondition(map);
	}

	@Override
	public List<LotteryChaseBuy> getByLotteryTypePhase(int lotteryType, String phase) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("lotteryType", lotteryType);
		map.put("phase", phase);
		return findByCondition(map);
	}

    @Override
    public List<LotteryChaseBuy> getByLotteryTypePhaseChaseType(int lotteryType, String phase, List<Integer> chaseTypeList) {
    	String whereSql=" status=:status and chaseType in:(chaseType) and phase=:phase and lotteryType=:lotteryType";
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("lotteryType", lotteryType);
        map.put("phase", phase);
        map.put("chaseType", chaseTypeList);
        map.put("status", ChaseBuyStatus.chasebuy_no.value);
        return findByCondition(whereSql,map);
    }

	@Override
	public List<LotteryChaseBuy> getByChaseId(String chaseId) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("chaseId", chaseId);
		return findByCondition(map);
	}

	@Override
	public List<LotteryChaseBuy> getByChaseIdAndStatus(String chaseId, int chaseBuyStatus) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("chaseId", chaseId);
		map.put("status", chaseBuyStatus);
		return findByCondition(map);
		
	}

	@Override
	public List<LotteryChaseBuy> getByLotteryTypePhaseChaseType(int lotteryType, String phase, int chaseType) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("lotteryType", lotteryType);
        map.put("phase", phase);
        map.put("chaseType", chaseType);
        map.put("status", ChaseBuyStatus.chasebuy_no.value);
		return findByCondition(map);
	}

	@Override
	public List<LotteryChaseBuy> getByChaseId(String chaseId, PageBean<LotteryChaseBuy> page) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("chaseId", chaseId);
		return findPageByCondition(map, page);
	}

	@Override
	public LotteryChaseBuy getByChaseIdAndOrderId(String chaseId, String orderId) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("chaseId", chaseId);
		map.put("orderId", orderId);
		return findByUnique(map);
	}

	@Override
	public int updateLotteryChaseBuyPhaseTime(int lotteryType, String phase, Date startTime, Date endTime) {

		Map<String,Object> whereMap=new HashMap<String, Object>();
		whereMap.put("lotteryType",lotteryType);
		whereMap.put("phase",phase);

		Map<String,Object> containMap=new HashMap<String, Object>();
		containMap.put("phaseStartTime",startTime);
		containMap.put("phaseEndTime",endTime);
		return update(containMap,whereMap);
	}


}
