package com.lottery.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryAddPrizeStrategyStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.dao.LotteryAddPrizeStrategyDao;
import com.lottery.core.dao.impl.AwardLevelDaoImpl;
import com.lottery.core.domain.LotteryAddPrizeStrategy;
import com.lottery.core.domain.LotteryAddPrizeStrategyPK;
import com.lottery.lottype.LotManager;

@Service
public class LotteryAddPrizeStrategyService {
	
	
	
	@Autowired
	private AwardLevelDaoImpl awardLevelDao;
	
	@Autowired
	private LotteryAddPrizeStrategyDao strategyDao;
	
	@Autowired
	private LotManager lotManager;
	
	
	
	public List<LotteryAddPrizeStrategy> findLotteryStrategys(int lotterytype,String prizelevel,int status) {
		LotteryAddPrizeStrategyStatus stragegyStatus = null;
		
		if(status!=-1) {
			stragegyStatus = LotteryAddPrizeStrategyStatus.get(status);
		}
		return strategyDao.findExistAddPrizeStrategys(lotterytype, prizelevel, stragegyStatus);
	}

	
	/**
	 * 添加一条加奖
	 * @param lotterytype
	 * @param prizelevel
	 * @param start
	 * @param end
	 * @param amt
	 */
	@Transactional
	public void addAddPrizeStrategy(int lotterytype,String prizelevel,String start,String end,int amt) {
		
		if(!LotteryType.get().contains(LotteryType.get(lotterytype))) {
			throw new LotteryException(ErrorCode.lotterytype_not_exits, "彩种不存在");
		}
		
		if(amt<=0) {
			throw new LotteryException(ErrorCode.paramter_error, "加奖金额小于等于0");
		}
		
		if(lotManager.getLot(String.valueOf(lotterytype)).validatePhase(start)==false
				||lotManager.getLot(String.valueOf(lotterytype)).validatePhase(end)==false) {
			throw new LotteryException(ErrorCode.phase_format_error, "期号格式不正确");
		}
		
//		Map<String, Long> prizelevels = awardLevelDao.getPrizeLevel(lotterytype);
//		
//		if(!prizelevels.containsKey(prizelevel)) {
//			throw new LotteryException(ErrorCode.paramter_error, "加奖奖级不存在");
//		}
		
		
		if(isStrategyRepeat(strategyDao.findExistAddPrizeStrategys(lotterytype, prizelevel),start,end)) {
			throw new LotteryException(ErrorCode.phase_addprize_strategy_repeat, "加奖策略重复");
		}
		
		
		LotteryAddPrizeStrategy strategy = new LotteryAddPrizeStrategy();
		
		LotteryAddPrizeStrategyPK pk = new LotteryAddPrizeStrategyPK();
		pk.setLotteryType(lotterytype);
		pk.setPrizeLevel(prizelevel);
		pk.setStartPhase(start);
		pk.setEndPhase(end);
		
		strategy.setAddamt(amt);
		strategy.setId(pk);
		
		strategy.setStatus(LotteryAddPrizeStrategyStatus.SAVED.value);
		
		strategyDao.createLotteryAddPrizeStrategy(strategy);
		
	}
	
	
	
	/**
	 * 关闭一条加奖
	 * @param lotterytype
	 * @param prizelevel
	 * @param startPhase
	 * @param endPhase
	 */
	@Transactional
	public void closeAddPrizeStrategy(int lotterytype,String prizelevel,String startPhase,String endPhase) {
		
		LotteryAddPrizeStrategyPK id = new LotteryAddPrizeStrategyPK();
		id.setLotteryType(lotterytype);
		id.setPrizeLevel(prizelevel);
		id.setStartPhase(startPhase);
		id.setEndPhase(endPhase);
		
		LotteryAddPrizeStrategy strategy = strategyDao.find(id);
		
		if(strategy==null) {
			throw new LotteryException(ErrorCode.no_exits, "加奖策略不存在");
		}
		
		strategy.setStatus(LotteryAddPrizeStrategyStatus.CLOSED.value);
		strategyDao.merge(strategy);
	}
	
	
	/**
	 * 开启一个加奖策略
	 * 只有状态为保存的期可用开启加奖策略
	 * @param lotterytype
	 * @param prizelevel
	 * @param startPhase
	 * @param endPhase
	 */
	@Transactional
	public void openAddPrizeStrategy(int lotterytype,String prizelevel,String startPhase,String endPhase) {
		LotteryAddPrizeStrategyPK id = new LotteryAddPrizeStrategyPK();
		id.setLotteryType(lotterytype);
		id.setPrizeLevel(prizelevel);
		id.setStartPhase(startPhase);
		id.setEndPhase(endPhase);
		
		LotteryAddPrizeStrategy strategy = strategyDao.find(id);
		
		if(strategy==null) {
			throw new LotteryException(ErrorCode.no_exits, "加奖策略不存在");
		}
		
		if(strategy.getStatus()!=LotteryAddPrizeStrategyStatus.SAVED.value) {
			throw new LotteryException(ErrorCode.paramter_error, "只有状态为保存的期可用开启");
		}
		
		strategy.setStatus(LotteryAddPrizeStrategyStatus.OPEN.value);
		strategyDao.merge(strategy);
		
	}
	
	
	
	/**
	 * 开启一个加奖策略
	 * 只有状态为保存的期可用开启加奖策略
	 * @param lotterytype
	 * @param prizelevel
	 * @param startPhase
	 * @param endPhase
	 */
	@Transactional
	public void deleteAddPrizeStrategy(int lotterytype,String prizelevel,String startPhase,String endPhase) {
		LotteryAddPrizeStrategyPK id = new LotteryAddPrizeStrategyPK();
		id.setLotteryType(lotterytype);
		id.setPrizeLevel(prizelevel);
		id.setStartPhase(startPhase);
		id.setEndPhase(endPhase);
		
		LotteryAddPrizeStrategy strategy = strategyDao.find(id);
		
		if(strategy==null) {
			throw new LotteryException(ErrorCode.no_exits, "加奖策略不存在");
		}
		
		strategy.setStatus(LotteryAddPrizeStrategyStatus.DELETE.value);
		
		strategyDao.merge(strategy);
	}
	
	
	public LotteryAddPrizeStrategy findCurrentStrategy(int lotterytype,String prizelevel,String phase) {
		List<LotteryAddPrizeStrategy> strategys = strategyDao.findAddPrizeStrategys(lotterytype, prizelevel, phase, LotteryAddPrizeStrategyStatus.OPEN);
		if(strategys==null||strategys.size()==0) {
			return null;
		}
		if(strategys.size()>1) {
			throw new LotteryException(ErrorCode.phase_addprize_strategy_repeat, "加奖策略重复");
		}
		return strategys.get(0);
	}
	
	
	/**
	 * 判断是否有重复的加奖策略 
	 * 开始期和结束期 在之前对应的加奖策略的开始期和结束期之间，算重复
	 * 开始期小于已经存在的策略的开始期 结束期大于已经存在的结束期，算重复
	 * 已经存在的期，状态查询为保存和已开始
	 * @param strategys
	 * @param start
	 * @param end
	 * @return
	 */
	private boolean isStrategyRepeat(List<LotteryAddPrizeStrategy> strategys,String start,String end) {
		boolean flag = false;
		for(LotteryAddPrizeStrategy strategy:strategys) {
			if(Integer.parseInt(start)>=Integer.parseInt(strategy.getId().getStartPhase())&&
					Integer.parseInt(start)<=Integer.parseInt(strategy.getId().getEndPhase())) {
				flag = true;
				break;
			} else if(Integer.parseInt(end)>=Integer.parseInt(strategy.getId().getStartPhase())&&
					Integer.parseInt(end)<=Integer.parseInt(strategy.getId().getEndPhase())) {
				flag = true;
				break;
			} else if (Integer.parseInt(start)<=Integer.parseInt(strategy.getId().getStartPhase())&&
					Integer.parseInt(end)>=Integer.parseInt(strategy.getId().getEndPhase())) {
				flag = true;
				break;
			}
			
		}
		return flag;
	}
}
