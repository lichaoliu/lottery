package com.lottery.core.dao.give;


import java.math.BigDecimal;
import java.util.List;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.give.UserRechargeGive;


public interface UserRechargeGiveDAO extends IGenericDAO<UserRechargeGive, String>{
	/**
	 * 根据充值金额，类型查询赠送
	 * @param amount 充值金额
	 * @param rechargeGiveType 充值赠送类型
	 * @return 
	 * */
	public List<UserRechargeGive> getByAmountAndType(BigDecimal amount,int rechargeGiveType);
	/**
	 * 根据充值类型查询赠送
	 * @param rechargeGiveType 充值赠送类型
	 * @param max 条数
	 * @return
	 * */
	public List<UserRechargeGive> getType(int rechargeGiveType,int max);
	
	/**
	 * 根据充值类型，渠道查询
	 * @param rechargeGiveType 充值赠送类型
	 * @param agecyno渠道号
	 * @param max 条数
	 * 
	 * */
	public List<UserRechargeGive> getByGiveTypeAndAgencyno(int rechargeGiveType,String agencyno,int max);
	
	
}
