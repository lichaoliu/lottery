package com.lottery.core.dao;

import java.util.List;

import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.terminal.TerminalConfig;

public interface TerminalConfigDAO extends IGenericDAO<TerminalConfig, Long> {
	
	
	public  List<TerminalConfig> getTerminalConfigListByPlayType(PlayType playType);
	
	/***
	 * 根据终端类型,彩种,玩法查询终端
	 * @param terminalType 终端类型
	 * @param lotteryType 彩种类型
	 * @param playType 玩法
	 * */
	public  List<TerminalConfig>  get(int terminalType,int lotteryType,int playType);
	/***
	 * 根据彩种类型,终端id,玩法查询终端
	 * @param terminalType 终端类型
	 * @param lotteryType 彩种类型
	 * @param playType 玩法
	 * */
	public TerminalConfig get(int lotteryType, Long terminalId,int playType)throws Exception;
	/***
	 * 根据终端类型,彩种查询终端
	 * @param terminalType 终端类型
	 * @param lotteryType 彩种类型
	 * */
	public  List<TerminalConfig> getByTerminalTypeAndLotteryType(int terminalType,int lotteryType);

	/***
	 * 根据终端类型,彩种查询终端
	 * @param terminalId 终端id
	 * @param lotteryType 彩种类型
	 * */
	public List<TerminalConfig> getByTerminalIdAndLotteryType(Long terminalId,int lotteryType);
	
	
	
	
}
