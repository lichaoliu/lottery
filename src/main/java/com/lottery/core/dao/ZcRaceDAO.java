package com.lottery.core.dao;

import java.util.List;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.ZcRace;

public interface ZcRaceDAO extends IGenericDAO<ZcRace, Long> {
	  /**
     * 根据彩种，期号,场次号查询
     * @param  lotteryType  彩种
     * @param  phase 期号
     * @param  matchNum 场次编号
     * */
   
    public ZcRace getByLotteryPhaseAndNum(int lotteryType,String phase,int matchNum);
    
    /**
     * 根据彩种，期号查询
     * @param  lotteryType  彩种
     * @param  phase 期号
     * */
  public  List<ZcRace> getByLotteryTypeAndPhase(int lotteryType,String phase);
}
