package com.lottery.core.dao;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.common.PageBean;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.account.UserRebate;

public interface UserRebateDAO extends IGenericDAO<UserRebate, String>{

	/**
	 * 获取固定详情
	 * @param userno 用户编号
	 * @param lotteryType 彩种
	 * */
	public UserRebate getFix(String userno,Integer lotteryType);
	/**
	 * 根据返点类型查询
	 * @param rebatType 返点类型
	 * */
	public List<UserRebate> getByType(Integer rebateType,PageBean<UserRebate> page);
	/**
	 * 获取浮动返点
	 * @param userno 用户编号
	 * @param lotteryType 彩种
	 * @param amount 投注金额
	 * */
	public List<UserRebate> getFloat(String userno,int lotteryType,BigDecimal amount);
	/**
	 * 获取浮动返点
	 * @param userno 用户编号
	 * @param lotteryType 彩种
	 * @param rebateType 返点类型
	 * @param max 最大条数
	 * */
	public List<UserRebate> getByUsernoAndLottery(String userno,int lotteryType,int rebateType,int max);
	
}
