package com.lottery.core.dao;

import java.util.List;

import com.lottery.common.AdminPage;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.ticket.LogicMachinePK;
import com.lottery.core.domain.ticket.LotteryLogicMachine;

public interface LotteryLogicMachineDAO extends IGenericDAO<LotteryLogicMachine, LogicMachinePK> {

	public void findPageById(Long id, Integer terminalType, Integer lotteryType, AdminPage<LotteryLogicMachine> page);

	



	

	/**
	 * 根据终端类型,彩种查询逻辑机
	 * 
	 * @param terminalType
	 *            终端类型
	 * @param lotteryType
	 *            彩种类型
	 * */
	public List<LotteryLogicMachine> getByTerminalAndLotteryType(int terminalType, int lotteryType);
	/**
	 * 根据终端类型,彩种类型,终端号查询机器
	 * @param terminalType
	 *            终端类型
	 * @param lotteryType
	 *            彩种类型
	 * @param terminalId 终端号
	 * */
	public List<LotteryLogicMachine>  getByTerminalLotteryAndId(int terminalType, int lotteryType,Long terminalId);
	

	/**
	 * 根据终端类型查询逻辑机
	 * 
	 * @param terminalType
	 *            终端类型
	 * */
	public List<LotteryLogicMachine> getByTerminalType(int terminalType);
}
