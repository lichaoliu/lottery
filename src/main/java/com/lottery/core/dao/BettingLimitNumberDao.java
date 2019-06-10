package com.lottery.core.dao;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.BettingLimitNumber;

import java.util.List;


/**
 * Created by fengqinyun on 16/8/25.
 */
public interface BettingLimitNumberDao extends IGenericDAO<BettingLimitNumber, Long> {


    public List<BettingLimitNumber> getBytype(int limitType);

    /**
     * 根据彩种,玩法查询
     * @param lotteryType 彩种
     *@param playType  玩法
     * */

    public List<BettingLimitNumber> getByLotteryTypePlayType(int lotteryType,int playType);
    
    
    public List<BettingLimitNumber> getByLotteryType(int lotteryType);
}
