package com.lottery.core.service;

import com.lottery.common.AdminPage;
import com.lottery.core.dao.TicketSplitConfigDao;
import com.lottery.core.domain.ticket.TicketSplitConfig;
import com.lottery.core.domain.ticket.TicketSplitConfigPK;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fengqinyun on 2016/12/25.
 */
@Service
public class TicketSplitConfigService {
    @Autowired
    private TicketSplitConfigDao dao;
    @Transactional
    public TicketSplitConfig get(int lotteryType,int playType,int splitType){
        TicketSplitConfigPK pk=new TicketSplitConfigPK(lotteryType,playType,splitType);
        return dao.find(pk);
    }
    @Transactional
    public TicketSplitConfig get(TicketSplitConfigPK id){
        return dao.find(id);
    }
    
    
	public void page(Map<String, Object> conditionMap, AdminPage<TicketSplitConfig> page) {
		dao.findPageByCondition(conditionMap, page);
	}
	
	@Transactional
	public void save(Integer lotteryType, Integer playType, Integer splitType, Integer betNum, Integer betmount, Integer prizeAmount) {
		TicketSplitConfig t = new TicketSplitConfig();
		TicketSplitConfigPK pk = new TicketSplitConfigPK(lotteryType, playType, splitType);
		t.setId(pk);
		t.setBetNum(betNum);
		t.setBetmount(betmount);
		t.setPrizeAmount(prizeAmount);
		dao.insert(t);
	}
	
	@Transactional
	public void update(Integer lotteryType, Integer playType, Integer splitType, Integer betNum, Integer betmount, Integer prizeAmount) {
		TicketSplitConfigPK pk = new TicketSplitConfigPK(lotteryType, playType, splitType);
		TicketSplitConfig t = dao.find(pk);
		t.setBetNum(betNum);
		t.setBetmount(betmount);
		t.setPrizeAmount(prizeAmount);
		dao.update(t);
	}
	
	@Transactional
	public void remove(Integer lotteryType, Integer playType, Integer splitType){
		TicketSplitConfigPK pk = new TicketSplitConfigPK(lotteryType, playType, splitType);
		TicketSplitConfig t = dao.find(pk);
		dao.remove(t);
	}
}
