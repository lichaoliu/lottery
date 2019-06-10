package com.lottery.ticket.phase.thread;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.cache.model.LotteryTicketConfigCacheModel;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.LotteryPhaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTicketPhaseRunnable extends AbstractThreadRunnable {
	protected  final Logger logger=LoggerFactory.getLogger(this.getClass().getName());
	private LotteryType lotteryType;
	protected long invitl=60000;
	@Autowired
	protected LotteryPhaseService phaseService;
	@Autowired
	protected LotteryTicketConfigCacheModel lotteryTicketConfigCacheModel;
	@Autowired
	protected LotteryOrderService lotteryOrderService;
	protected long endSaleForward = 0;
	@Override
	protected void executeRun() {
		if(lotteryType==null){
			logger.error("彩种列表为空");
			return;
		}

		this.execute();

	}
	
	
	abstract void execute();


	



	/**
	 * 回收期状态将   已关闭改为 已开奖
	 * */
	
	protected void recyclePhase(){//回收竞彩彩期
		List<LotteryPhase> phaseList=phaseService.getByLotteryTypeAndStatus(getLotteryType().value, PhaseStatus.close.value);
		if(phaseList!=null){
			List<Integer> orderStatusList=new ArrayList<Integer>();
			orderStatusList.add(OrderStatus.PRINTED.value);
			orderStatusList.add(OrderStatus.HALF_PRINTED.value);
			List<Integer> orderResultStatusList=new ArrayList<Integer>();
			orderResultStatusList.add(OrderResultStatus.not_open.value);
			orderResultStatusList.add(OrderResultStatus.prizing.value);
			for(LotteryPhase lotteryPhase:phaseList){
				try{
					boolean planFlag=false;
					String phase=lotteryPhase.getPhase();
					for (LotteryType lotteryType : this.getLotteryTypeList()){
						PageBean<LotteryOrder> page=new PageBean<LotteryOrder>();
						page.setPageIndex(1);
						page.setMaxResult(1);
						page.setPageFlag(false);
						List<LotteryOrder> orders=null;
						try{
							orders=lotteryOrderService.getByLotteryPhaseMatchNumAndStatus(lotteryType.value, phase, null, orderStatusList, orderResultStatusList, page);
						}catch(Exception e){
							logger.error("查询订单状态出错",e);
							planFlag=true;
							break;
						}
						if (orders != null && !orders.isEmpty()) {
							logger.error("竞彩[{}],[{}]期,存在未开奖订单[{}],不能修改状态为已开奖", new Object[]{lotteryType.name,phase, orders.get(0).getId()});
							planFlag = true;
							break;
						} 
					}
					if(!planFlag){
						if(isNotPrize(phase)){
							logger.error("彩种[{}],[{}]期,场次有未开奖,请检查",getLotteryType().name,phase);
							continue;
						}
						logger.error("彩种[{}],[{}]期,修改为已开奖",getLotteryType().name,phase);
						lotteryPhase.setPhaseStatus(PhaseStatus.prize_open.value);
						phaseService.update(lotteryPhase);
					}
				}catch(Exception e){
					logger.error("彩期状态回收出错",e);
				}
		
			}
			
		}
	}
	//是否有未开奖的比赛
	protected  boolean isNotPrize(String phase){
		return false;
	}
	
	protected abstract List<LotteryType> getLotteryTypeList();
	public void setEndSaleForward(long endSaleForward) {
		this.endSaleForward = endSaleForward;
	}


	public LotteryType getLotteryType() {
		return lotteryType;
	}


	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}


	public long getInvitl() {
		return invitl;
	}


	public void setInvitl(long invitl) {
		this.invitl = invitl;
	}
}

