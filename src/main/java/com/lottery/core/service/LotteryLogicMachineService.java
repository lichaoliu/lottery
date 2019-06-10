package com.lottery.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.contains.ticket.LogicMachineStatus;
import com.lottery.core.dao.LotteryLogicMachineDAO;
import com.lottery.core.domain.ticket.LogicMachinePK;
import com.lottery.core.domain.ticket.LotteryLogicMachine;

@Service
public class LotteryLogicMachineService {
    @Autowired
	private LotteryLogicMachineDAO dao;
    
	@Transactional
    public LotteryLogicMachine saveOrUpdate(LotteryLogicMachine machine){
		LotteryLogicMachine lm = dao.find(machine.getPk());
    	if(lm != null){
    		lm.setCityCode(machine.getCityCode());
    		lm.setTerminalId(machine.getTerminalId());
			lm.setStartId(machine.getStartId());
			lm.setEndId(machine.getEndId());
			lm.setCurrentId(machine.getCurrentId());
			lm.setStatus(machine.getStatus());
			lm.setUpdateTime(new Date());
			lm.setWeight(machine.getWeight());
			lm.setPhase(machine.getPhase());
			
			lm.setIp(machine.getIp());
			lm.setPort(machine.getPort());
			lm.setDescribeStr(machine.getDescribeStr());
    		dao.merge(lm);
		}else{
			dao.insert(machine);
		}
    	return lm;
    }
	
	@Transactional
    public void delete(List<LogicMachinePK> pks){
		for (LogicMachinePK pk : pks) {
			LotteryLogicMachine lm = dao.find(pk);
			dao.remove(lm);
		}
    }
	 /**
     * 根据终端类型,彩种查询逻辑机
     * @param termianlType 终端类型
     * @param lotteryType 彩种类型
     * */
	@Transactional
    public List<LotteryLogicMachine> getByTerminalAndLotteryType(int termianlType,int lotteryType){
    	return dao.getByTerminalAndLotteryType(termianlType, lotteryType);
    }
	@Transactional
	public void update(LotteryLogicMachine entity){
		dao.merge(entity);
	}

	/**
	 * 根据终端类型查询逻辑机
	 * 
	 * @param terminalType
	 *            终端类型
	 * */
	@Transactional
	public List<LotteryLogicMachine> getByTerminalType(int terminalType){
		return dao.getByTerminalType(terminalType);
	}
	


	@Transactional
	public LotteryLogicMachine getUseingMachine(int terminalType,int lotteryType,Long terminalId){
		List<LotteryLogicMachine> useingList=dao.getByTerminalLotteryAndId(terminalType, lotteryType, terminalId);
		if(useingList==null||useingList.isEmpty())
			return null;
		LotteryLogicMachine lotteryLogicMachine=null;
		for(LotteryLogicMachine matchine:useingList){
			if(matchine.getStatus()!=LogicMachineStatus.useing.value){
				continue;
			}
			if(matchine.getCurrentId().intValue()==matchine.getEndId().intValue()){
				matchine.setStatus(LogicMachineStatus.used.value);
				matchine.setUpdateTime(new Date());
				dao.merge(matchine);
				continue;
			}
			return matchine;
		}
		return lotteryLogicMachine;
		
	}
	/**
	 * 根据终端类型,彩种类型,终端号查询机器
	 * @param terminalType
	 *            终端类型
	 * @param lotteryType
	 *            彩种类型
	 * @param terminalId 终端号
	 * */
	@Transactional
	public List<LotteryLogicMachine> getByTerminalLotteryAndId(int terminalType,int lotteryType,Long terminalId){
		return dao.getByTerminalLotteryAndId(terminalType, lotteryType, terminalId);
	}
	
}
