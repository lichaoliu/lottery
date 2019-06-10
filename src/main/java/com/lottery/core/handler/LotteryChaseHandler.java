package com.lottery.core.handler;

import com.lottery.common.contains.lottery.ChaseType;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.LotteryChaseBuy;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.service.LotteryChaseBuyService;
import com.lottery.core.service.LotteryChaseService;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.bet.BetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryChaseHandler {
	@Autowired
	private IdGeneratorService idGeneratorService;
	@Autowired
	private LotteryChaseBuyService lotteryChaseBuyService;
	@Autowired
	private BetService betService;
    @Autowired
	private LotteryChaseService lotteryChaseService;
	@Autowired
	protected LotteryPhaseService phaseService;
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	public void process(Integer lotteryType,String phase,int chaseType) {
		//logger.error("彩种:{},期号:{},追号类型:{}",lotteryType,phase,chaseType);
		List<LotteryChaseBuy> chaseBuys = lotteryChaseBuyService.getByLotteryTypePhaseChaseType(lotteryType, phase, chaseType);
		if (chaseBuys == null || chaseBuys.isEmpty()) {
			return;
		}
		logger.error("彩种:{},期号:{},需要追号的订单个数为:{}", new Object[] { lotteryType, phase,chaseBuys.size() });
		for (LotteryChaseBuy lotteryChaseBuy : chaseBuys) {
			String chaseId = lotteryChaseBuy.getChaseId();
			String chaseBuyid = lotteryChaseBuy.getId();
			try {
				String orderId = idGeneratorService.getLotteryOrderId();
				betService.chaseBet(orderId,chaseBuyid);
				betService.sendJms(orderId);
			} catch (Exception e) {
				logger.error("开期追号出错,追号id:{},执行追号Id:{}", chaseId, chaseBuyid, e);
			}
		}
	}



	/**
	 * 中奖后的追号
	 * */
	public void processPrize(Integer lotteryType,String phase){
		List<LotteryChaseBuy> chaseBuyList=lotteryChaseBuyService.getByLotteryTypePhase(lotteryType, phase);
		if (chaseBuyList!=null&&chaseBuyList.size()>0){
			for (LotteryChaseBuy lotteryChaseBuy:chaseBuyList){
				if (lotteryChaseBuy.getPrize()!=null){
					try {
						lotteryChaseService.updatePrzieChase(lotteryChaseBuy.getChaseId(),lotteryChaseBuy.getPrize());
					}catch (Exception e){
						logger.error("中奖追号处理",e);
					}
				}
			}

		}

		LotteryPhase lotteryPhase=phaseService.getCurrent(lotteryType);
		if (lotteryPhase!=null){
			this.process(lotteryType,lotteryPhase.getPhase(),ChaseType.prize_end.value);
			this.process(lotteryType,lotteryPhase.getPhase(),ChaseType.total_prize_end.value);
		}
	}

	public void toBet(String chaseId,int lotteryType,String phase){
		  try {
			  LotteryChaseBuy chaseBuy=lotteryChaseBuyService.getByChaseIdLotteryTypePhase(chaseId, lotteryType, phase);
			  if (chaseBuy!=null){
				  String orderId = idGeneratorService.getLotteryOrderId();
				  betService.chaseBet(orderId,chaseBuy.getId());
				  betService.sendJms(orderId);
			  }
		  }catch (Exception e){
			  logger.error("具体追号失败",e);
		  }

	}
  //撤销追号
	public void canelChasle(Integer lotteryType,String phase){
		List<LotteryChaseBuy> chaseBuyList=lotteryChaseBuyService.getByLotteryTypePhase(lotteryType, phase);
		if (chaseBuyList!=null&&chaseBuyList.size()>0){
			for (LotteryChaseBuy lotteryChaseBuy:chaseBuyList){
				try {
					lotteryChaseService.giveupChaseBuy(lotteryChaseBuy.getId(),"该期销售暂停,追号撤销");
				}catch (Exception e){
					logger.error("追号撤销出错",e);
				}
			}

		}
	}


	public void canelChaseBuy(String chaseBuyId,String memo){
		 lotteryChaseService.giveupChaseBuy(chaseBuyId,memo);
	}
}
