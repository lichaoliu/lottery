package com.lottery.core.dao;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.account.UserRebateStatistic;
import com.lottery.core.domain.account.UserRebateStatisticPK;

public interface UserRebateStatisticDao extends IGenericDAO<UserRebateStatistic, UserRebateStatisticPK>{
	/**
	 * 根据用户名，统计彩种，月份查询
	 * @param userno 编号
	 * @param lotteryType 彩种
	 * @param monthNum 月份
	 * **/
	public List<UserRebateStatistic> getByUsernoAndMonth(String userno,int lotteryType,Long monthNum);
	/**
	 * 根据用户名，统计彩种，月份 查询投注总金额
	 * @param userno 编号
	 * @param lotteryType 彩种
	 * @param monthNum 月份
	 * **/
	public BigDecimal getMonthTotal(String userno,int lotteryType,Long monthNum);
}
