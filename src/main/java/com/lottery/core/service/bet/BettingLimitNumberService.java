package com.lottery.core.service.bet;

import com.lottery.common.AdminPage;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.BettingLimitNumberDao;
import com.lottery.core.domain.BettingLimitNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

/**
 * Created by fengqinyun on 16/8/26.
 */
@Service
public class BettingLimitNumberService {
    @Autowired
    private IdGeneratorDao idGeneratorDao;
    @Autowired
    private BettingLimitNumberDao bettingLimitNumberDao;
    @Transactional
    public void save(BettingLimitNumber bettingLimitNumber){
        bettingLimitNumber.setId(idGeneratorDao.getBettingLimitNumber());
        bettingLimitNumberDao.insert(bettingLimitNumber);
    }

    @Transactional
    public AdminPage<BettingLimitNumber> pageForAdmin(int start, int limit, int lotteryType){
    	AdminPage<BettingLimitNumber> page = new AdminPage<BettingLimitNumber>(start, limit, "order by updateTime desc");
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		if(lotteryType != 0){
			conditionMap.put("EQI_lotteryType", lotteryType);
		}
		bettingLimitNumberDao.findPageByCondition(conditionMap, page);
		return page;
    }
    
    @Transactional
    public void update(Long id, Integer limitType, Integer lotteryType, Integer playType, Integer status, String content){
    	BettingLimitNumber bln = bettingLimitNumberDao.find(id);
    	bln.setLimitType(limitType);
    	bln.setLotteryType(lotteryType);
    	bln.setPlayType(playType);
    	bln.setStatus(status);
    	bln.setContent(content);
    	bln.setUpdateTime(new Date());
    	bettingLimitNumberDao.merge(bln);
    }
    
    @Transactional
    public void delete(String ids){
    	for (String id : ids.split(",")) {
    		BettingLimitNumber bln = bettingLimitNumberDao.find(Long.parseLong(id));
        	bettingLimitNumberDao.remove(bln);
		}
    }

    /**
     * 根据彩种,玩法查询
     * @param lotteryType 彩种
     *@param playType  玩法
     * */
    @Transactional
    public List<BettingLimitNumber> getByLotteryTypePlayType(int lotteryType, int playType){
        return bettingLimitNumberDao.getByLotteryTypePlayType(lotteryType, playType);
    }
    
    
    /**
     * 根据彩种查询
     * @param lotteryType 彩种
     * */
    @Transactional
    public List<BettingLimitNumber> getByLotteryType(int lotteryType){
        return bettingLimitNumberDao.getByLotteryType(lotteryType);
    }
}
