package com.lottery.core.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Repository;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.BettingLimitNumberDao;
import com.lottery.core.domain.BettingLimitNumber;

/**
 * Created by fengqinyun on 16/8/25.
 */
@Repository
public class BettingLimitNumberDaoImpl extends AbstractGenericDAO<BettingLimitNumber,Long> implements BettingLimitNumberDao {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
    public List<BettingLimitNumber> getBytype(int limitType) {
        Map<String,Object> whereMap=new HashedMap<String, Object>();
        whereMap.put("limitType",limitType);
        whereMap.put("status", YesNoStatus.yes.value);
        return findByCondition(whereMap);
    }

    @Override
    public List<BettingLimitNumber> getByLotteryTypePlayType(int lotteryType, int playType) {
        Map<String,Object> whereMap=new HashedMap<String, Object>();
        whereMap.put("lotteryType",lotteryType);
        whereMap.put("PlayType", playType);
        whereMap.put("status", YesNoStatus.yes.value);
        return findByCondition(whereMap);
    }
    
    
    @Override
    public List<BettingLimitNumber> getByLotteryType(int lotteryType) {
        Map<String,Object> whereMap=new HashedMap<String, Object>();
        whereMap.put("lotteryType",lotteryType);
        whereMap.put("status", YesNoStatus.yes.value);
        return findByCondition(whereMap);
    }
}
