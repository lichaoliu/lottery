package com.lottery.core.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.account.UserAccountDetail;

public interface UserAccountDetailDAO extends
		IGenericDAO<UserAccountDetail, String> {
	
	/**
	 * 根据交易号，支付类型，交易类型查询
	 * @param payid 交易号
	 * @param payType 交易类型
	 * @param accountDetailType 交易类型
	 * */
	
	public UserAccountDetail getByPayIdAndType(String payid,AccountType payType,AccountDetailType accountDetailType);
	/**
	 * 根据交易号，支付类型，交易类型查询
	 * @param payid 交易号
	 * @param payType 交易类型
	 * @param accountDetailType 交易类型
	 * */
	
	public UserAccountDetail getByPayIdAndType(String payid,int payType,int accountDetailType);
   
	/**
	 * 根据交易号，交易类型查询
	 * @param payid 交易号
	 * @param accountDetailType 交易类型
	 * */
	public List<UserAccountDetail> getByPayIdAndDetailType(String payid,int accountDetailType);
    /**
     * 根据用户名查询交易详情
     * @param useno 用户编号
     * @param startDate 开始时间
     * @param endDate 结束时间
     * */
    public List<UserAccountDetail> getByUserno(String userno,Date startDate,Date endDate,PageBean<UserAccountDetail> page);
    /**
     * 投注成功金额统计
     * @param userno 用户编号
     * @param lotteryTypeList 彩种
     * @param startDate 开始时间
     * @param endDate 结束时间
     * */
    public Map<Integer,BigDecimal> getBetAmountStatic(String userno,List<Integer> lotteryTypeList,Date startDate,Date endDate);
    /**
     * 用户投注成功金额统计
     * @param userno 用户编号
     * @param lotteryTypeList 彩种
     * @param startDate 开始时间
     * @param endDate 结束时间
     * */
    public BigDecimal getUsernoBetAmountStatic(String userno,List<Integer> lotteryTypeList,Date startDate,Date endDate);
    /**
     * 渠道投注成功金额统计
     * @param agencyno 渠道号
     * @param lotteryTypeList 彩种
     * @param startDate 开始时间
     * @param endDate 结束时间
     * */
    public BigDecimal getAgentBetAmountStatic(String agencyno,List<Integer> lotteryTypeList,Date startDate,Date endDate);

    /**
     * 修改中奖总金额
     * @param orderId 订单号
     * @param orderPrizeAmount 中奖金额
     * */
    public  int updateOrderPrizeAmount(String orderId,BigDecimal orderPrizeAmount);
    

}
