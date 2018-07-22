package com.lottery.ticket.vender.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.ticket.LogicMachineStatus;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.ticket.LotteryLogicMachine;
import com.lottery.core.handler.PrizeHandler;
import com.lottery.core.service.LotteryLogicMachineService;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.ticket.phase.thread.IPhaseHandler;
@Service
public class VenderPhaseDrawHandler {

	@Autowired
	protected LotteryPhaseService lotteryPhaseService;
	@Autowired
	protected LotteryLogicMachineService lotteryLogicMachineService;

	protected final Logger logger=LoggerFactory.getLogger(getClass().getName());

	/**
	 * 修改逻辑机
	 * @param terminalType 终端类型
	 * @param lotteryType 彩种
	 * */
	public void magicMachine(int terminalType,int lotteryType,String phase){	
		List<LotteryLogicMachine> machineList=lotteryLogicMachineService.getByTerminalAndLotteryType(terminalType, lotteryType);
		if(machineList!=null&&machineList.size()>0){
			for(LotteryLogicMachine lotteryLogicMachine:machineList){
				if(lotteryLogicMachine.getPhase().equals(phase)){
					continue;
				}
				lotteryLogicMachine.setCurrentId(1l);
				lotteryLogicMachine.setPhase(phase);
				lotteryLogicMachine.setSwitchTime(new Date());
				if(lotteryLogicMachine.getStatus()!=LogicMachineStatus.not_use.value)
				lotteryLogicMachine.setStatus(LogicMachineStatus.useing.value);
				lotteryLogicMachineService.update(lotteryLogicMachine);
			}
		}
	}


	
}
