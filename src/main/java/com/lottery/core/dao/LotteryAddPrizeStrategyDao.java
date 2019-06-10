package com.lottery.core.dao;

import java.util.List;

import com.lottery.common.contains.lottery.LotteryAddPrizeStrategyStatus;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.LotteryAddPrizeStrategy;
import com.lottery.core.domain.LotteryAddPrizeStrategyPK;

public interface LotteryAddPrizeStrategyDao extends IGenericDAO<LotteryAddPrizeStrategy, LotteryAddPrizeStrategyPK>{

public LotteryAddPrizeStrategy createLotteryAddPrizeStrategy(LotteryAddPrizeStrategy lotteryAddPrizeStrategy);
	
	/**
	 * 查询某一期是否处于加奖策略中
	 * @param lotterytype 彩种
	 * @param prizelevel 奖级
	 * @param status 加奖状态
	 * @return
	 */
	public List<LotteryAddPrizeStrategy> findAddPrizeStrategys(int lotterytype,String prizelevel,String phase,LotteryAddPrizeStrategyStatus status);
	
	
	/**
	 * 查询某一期是否处于加奖策略中
	 * @param lotterytype 彩种
	 * @param prizelevel 奖级
	 * @param status 加奖状态
	 * @return
	 */
	public List<LotteryAddPrizeStrategy> findAddPrizeStrategys(int lotterytype,String phase,LotteryAddPrizeStrategyStatus status);
	
	/**
	 * 查询所有彩种某个奖级的加奖(状态为保存和开始)
	 * @param lotterytype
	 * @param prizelevel
	 * @return
	 */
	public List<LotteryAddPrizeStrategy> findExistAddPrizeStrategys(int lotterytype,String prizelevel);
	
	
	/**
	 * 查询所有彩种某个奖级的加奖(状态为保存和开始)
	 * @param lotterytype
	 * @param prizelevel
	 * @return
	 */
	public List<LotteryAddPrizeStrategy> findExistAddPrizeStrategys(int lotterytype,String prizelevel,LotteryAddPrizeStrategyStatus status);
	
	
	
}
