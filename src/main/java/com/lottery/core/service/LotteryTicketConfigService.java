package com.lottery.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.LotteryTicketConfigDAO;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
@Service
public class LotteryTicketConfigService {
    @Autowired
	private LotteryTicketConfigDAO ticketConfigDao;
    @Autowired
    private IdGeneratorDao idGeneratorDao;
    
    @Transactional
	public LotteryTicketConfig get(LotteryType lotteryType){
		try {
			return ticketConfigDao.getByLotteryType(lotteryType);
		} catch (Exception e) {
			return null;
		}
	}
    

    @Transactional
    public LotteryTicketConfig saveOrUpdate(LotteryTicketConfig ltc){
    	if(ltc.getId() != null){
    		LotteryTicketConfig tc = ticketConfigDao.find(ltc.getId());
    		tc.setBatchCount(ltc.getBatchCount());
    		tc.setBatchTime(ltc.getBatchTime());
    		tc.setBeginSaleAllotBackward(ltc.getBeginSaleAllotBackward());
    		tc.setBeginSaleForward(ltc.getBeginSaleForward());
    		tc.setDrawBackward(ltc.getDrawBackward());
    		tc.setEndSaleAllotForward(ltc.getEndSaleAllotForward());
    		tc.setEndSaleForward(ltc.getEndSaleForward());
    		tc.setEndTicketTimeout(ltc.getEndTicketTimeout());
    		tc.setLotteryType(ltc.getLotteryType());
    		tc.setSendCount(ltc.getSendCount());
    		tc.setChaseEndSaleForward(ltc.getChaseEndSaleForward());
    		tc.setSendThreadCount(ltc.getSendThreadCount());
			ticketConfigDao.merge(tc);
		}else{
            Long id=idGeneratorDao.getTerminalTicketConfigId();
            ltc.setId(id);
			ticketConfigDao.insert(ltc);
		}
    	return ltc;
    }
}
