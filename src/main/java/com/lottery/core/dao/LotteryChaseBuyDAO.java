package com.lottery.core.dao;

import com.lottery.common.PageBean;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.LotteryChaseBuy;

import java.util.Date;
import java.util.List;

public interface LotteryChaseBuyDAO extends IGenericDAO<LotteryChaseBuy, String> {
	  /**
     * 根据追号id,彩种，期号查询执行追号
     * @param  chaseId 追号编号
     * @param  lotteryType 彩种
     * @param  phase 期号
     * */
    
    public List<LotteryChaseBuy> getByChaseIdLotteryTypePhase(String chaseId,int lotteryType,String phase);
	  /**
     * 根据彩种，期号查询执行追号
     * @param  lotteryType 彩种
     * @param  phase 期号
     * */
    
    public List<LotteryChaseBuy> getByLotteryTypePhase(int lotteryType,String phase);
    /**
     * 根据彩种，期号,追号类型查询执行追号
     * @param  lotteryType 彩种
     * @param  phase 期号
     * @param  chaseTypeList 追号类型集合
     * */
    public List<LotteryChaseBuy> getByLotteryTypePhaseChaseType(int lotteryType,String phase,List<Integer> chaseTypeList);
    /**
     * 根据彩种，期号,追号类型查询执行追号
     * @param  lotteryType 彩种
     * @param  phase 期号
     * @param  chaseType 追号类型
     * */
    public List<LotteryChaseBuy> getByLotteryTypePhaseChaseType(int lotteryType,String phase,int chaseType);
	/**
	 * 根据追号id查询所有执行追号
	 * @param chaseId 追号id
	 * */
	public List<LotteryChaseBuy> getByChaseId(String chaseId); 
	/**
	 * 根据追号id,状态查询所有执行追号
	 * @param chaseId 追号id
	 * @param chaseBuyStatus 执行追号状态
	 * */
	public List<LotteryChaseBuy> getByChaseIdAndStatus(String chaseId,int chaseBuyStatus); 
	 
	/**
	 * 根据追号id查询所有执行追号
	 * @param chaseId 追号id
	 * */
	public List<LotteryChaseBuy> getByChaseId(String chaseId,PageBean<LotteryChaseBuy> page);
	/**
	 * 根据订单 追号id,订单号查询执行追号
	 * @param chaseId 追号id
	 * @param orderId 订单号
	 * */
	public LotteryChaseBuy getByChaseIdAndOrderId(String chaseId,String orderId);



	/**
	 * 修改追号期的开始结束时间
	 * @param lotteryType  彩种
	 * @param phase  期号
	 * @param startTime  开始时间
	 * @param endTime 结束时间
	 * */
	public int updateLotteryChaseBuyPhaseTime(int lotteryType, String phase, Date startTime, Date endTime);
	
	
}
