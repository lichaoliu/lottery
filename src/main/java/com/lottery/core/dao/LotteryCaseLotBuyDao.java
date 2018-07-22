package com.lottery.core.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lottery.common.PageBean;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.LotteryCaseLotBuy;

public interface LotteryCaseLotBuyDao extends IGenericDAO<LotteryCaseLotBuy, String> {

	/**
	 * 根据合买id,状态查询
	 * @param caselotId 合买id
	 * @param state 状态
	 * **/
	public List<LotteryCaseLotBuy> findCaseLotBuysByCaselotIdAndState(String caselotId,Integer state);
	/**
	 * 根据合买id,购买方式查询
	 * @param caselotId 合买id
	 * @param buyType 购买方式
	 * **/
	public List<LotteryCaseLotBuy> findCaseLotBuysByCaselotIdAndBuyType(String caselotId,	int buyType);

	List<LotteryCaseLotBuy> findCaseLotBuysByCaselotIdAndUserno(String id, String userno);

	/**®
	 * 查询用户或合买方案的合买详细
	 * @param userno   用户编号
	 * @param caselotId  合买编号
	 * @param conditionMap   查询条件集合
	 * @param page   分页条件
	 * @return Page<CaseLotBuy>
	 */
	public void findCaseLotBuys(String userno, String caselotId, Map<String, Object> conditionMap, PageBean<LotteryCaseLotBuy> page);
	
	/***
	 * 根据用户编号查询合买记录
	 * @param userno 用户编号
	 * @param startTime 开始时间
	 * @param endTime 结束时间、
	 * @param page 分页
	 * */
	public List<LotteryCaseLotBuy> getByUserno(String userno,Date startTime,Date endTime,PageBean<LotteryCaseLotBuy> page);
	 /**
     * 根据用户，彩种，订单状态，时间查询合买消费额
     * @param userno 用户名
     * @param lotteryType 彩种
     * @param startDate 开始时间
     * @param endDate 结束时间
     * */
    
    public Object[] getCaselotUserbet(String userno,int flag,int lotteryType,Date startDate,Date endDate);
    /***
     * 修改用户合买购买派奖
     * @param amount 中奖金额
     * @param encashStatus 派奖状态
     * @param id 购买订单 号
     * */
    public int updatePrizeAmtAndEncashStatus(BigDecimal amount,int encashStatus,String id);

    /**
	 * 根据合买id修改购买状态
	 * @param  caselotId 合买id
	 * @param  caslelotBuyState 状态
	 * */
	public int updateStateByCaselotId(String caselotId,int caslelotBuyState);


	/**
	 * 根据合买id查询购买记录
	 * @param caseLotId 合买id
	 * */
	public List<LotteryCaseLotBuy> getByCaseLotId(String caseLotId);
	
	
	
 
}
