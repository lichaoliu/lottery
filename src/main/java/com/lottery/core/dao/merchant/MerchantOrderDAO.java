package com.lottery.core.dao.merchant;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.merchant.MerchantOrder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fengqinyun on 14-7-10.
 */
public interface MerchantOrderDAO extends IGenericDAO<MerchantOrder,String>{
	/**
	 * 根据用户编号，订单号查询
	 * @param merchantCode 用户编号
	 * @param merchantNo 用户订单
	 * */
	public MerchantOrder getByMerchantCodeAndMerchantNO(String merchantCode,String merchantNo);
	/**
	 * 修改订单状态
	 * @param orderid 订单号
	 * @param orderStatus 订单状态
	 * */
	public int updateMerchantOrderStatus(String orderid,int orderStatus);
	/**
	 * 修改订单中奖状态
	 * @param orderid 订单号
	 * @param orderResultStatus 订单中奖状态
	 * @param amount 中奖金额
	 * */
	public int updateMerchantOrderResultStatus(String orderid,int orderResultStatus,BigDecimal amount);
	/**
	 * 修改派奖状态
	 * @param orderId 订单号
	 * @param status 状态
	 * */
	public int updateIsExchanged(String orderId,int status);
	/**
	 * 修改订单状态和成功时间
	 * @param orderid 订单号
	 * @param orderStatus 订单状态
	 * */
	public int updateMerchantOrderStatusAndPrintTime(String orderid,int orderStatus,Date date);

	
}
