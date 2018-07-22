package com.lottery.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.LotteryTerminalConfigDAO;
import com.lottery.core.domain.terminal.LotteryTerminalConfig;
@Service
public class LotteryTerminalConfigService {
	@Autowired
	protected LotteryTerminalConfigDAO lotteryTerminalConfigDAO;
	@Autowired
    private IdGeneratorDao idGeneratorDao;

	/**
	 * 按彩种获取
	 * 
	 * @param lotteryType
	 * @return
	 */
	@Transactional
	public LotteryTerminalConfig get(Integer lotteryType) {
		try {
			
			return lotteryTerminalConfigDAO.get(lotteryType);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 按彩种批量获取配置
	 * 
	 * @param lotteryTypeList
	 * @return
	 */
	@Transactional
	public Map<LotteryType, LotteryTerminalConfig> mget(List<LotteryType> lotteryTypeList) {
		return lotteryTerminalConfigDAO.mget(lotteryTypeList);
	}
	
    @Transactional
	public LotteryTerminalConfig saveOrUpdate(LotteryTerminalConfig ltc) {
		if(ltc.getId() != null){
    		LotteryTerminalConfig tc = lotteryTerminalConfigDAO.find(ltc.getId());
    		if(tc == null){
    			return null;
    		}	
    		tc.setAllotForbidPeriod(ltc.getAllotForbidPeriod());
    		tc.setCheckForbidPeriod(ltc.getCheckForbidPeriod());
    		tc.setIsCheckEnabled(ltc.getIsCheckEnabled());
    		tc.setIsEnabled(ltc.getIsEnabled());
    		tc.setIsPaused(ltc.getIsPaused());
    		tc.setLotteryType(ltc.getLotteryType());
    		tc.setSendForbidPeriod(ltc.getSendForbidPeriod());
    		lotteryTerminalConfigDAO.merge(tc);
		}else{
            Long id=idGeneratorDao.getLotteryTerminalConfigId();
            ltc.setId(id);
            lotteryTerminalConfigDAO.insert(ltc);
		}
    	return ltc;
	}
}
