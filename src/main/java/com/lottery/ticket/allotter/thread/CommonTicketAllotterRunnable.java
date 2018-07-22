/**
 * 
 */
package com.lottery.ticket.allotter.thread;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.LotteryTicketConfigService;
import com.lottery.core.service.TicketService;
import com.lottery.ticket.allotter.ITicketAllotWorker;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;



/**
 * @author fengqinyun
 *
 */
public class CommonTicketAllotterRunnable extends AbstractTicketAllotterRunnable {
   @Autowired
	private TicketService ticketService;
	@Autowired
	private LotteryTicketConfigService ticketConfigService;
	@Autowired
	private LotteryPhaseService lotteryPhaseService;

	
	private int maxTicketAllotSize=1000;
	@Override
	public void execute() {
		while (running) {
			for (LotteryType lotteryType : get()) {
				
				logger.info("本次要执行分票的彩种为：{},id={}", lotteryType.getName(), lotteryType.getValue());
				
				ITicketAllotWorker worker = this.getAllotExecutor(lotteryType);
				if (worker == null) {
					logger.error("未找到({})的分票器", lotteryType.getName());
					continue;
				}
				logger.info("已找到({})的分票器,执行分票", lotteryType.getName());
				
				
				// 查找终端可出票的彩期
				List<LotteryPhase> phaseList = null;
				
				try {
					phaseList = lotteryPhaseService.getByTerminalStatus(lotteryType.getValue(), TerminalStatus.enable.value);
				} catch (Exception e) {
					logger.error("查找终端可出票的彩期出错", e);
					if(e instanceof IllegalStateException){
						break;
					}
				}
				
				if (phaseList == null || phaseList.isEmpty()) {
					continue;
				}
				
				// 实际可分票的彩期
				List<LotteryPhase> canAllotPhaseList = new ArrayList<LotteryPhase>();
				if(LotteryType.getJclq().contains(lotteryType)||LotteryType.getJczq().contains(lotteryType)){
					canAllotPhaseList=phaseList;
				}else{
					long beginSaleAllotBackward = 0;
					long endSaleAllotForward = 0;
					
					LotteryTicketConfig lotteryTicketConfig = ticketConfigService.get(lotteryType);
					if (lotteryTicketConfig != null) {
						if (lotteryTicketConfig.getBeginSaleAllotBackward() != null) {
							beginSaleAllotBackward = lotteryTicketConfig.getBeginSaleAllotBackward().longValue();
						}
						if (lotteryTicketConfig.getEndSaleAllotForward() != null) {
							endSaleAllotForward = lotteryTicketConfig.getEndSaleAllotForward().longValue();
						}
//						if(lotteryTicketConfig.getBatchCount()!=null&&lotteryTicketConfig.getBatchCount()!=0){
//							maxTicketAllotSize=lotteryTicketConfig.getBatchCount();//获得打票批次
//						}
					}
					
					long currentTimeMillis = System.currentTimeMillis();
					for (LotteryPhase phase : phaseList) {
						long beginSaleTimeMillis = 0;
						long endSaleTimeMillis = 0;
						try {
							beginSaleTimeMillis = phase.getStartSaleTime().getTime();
							endSaleTimeMillis = phase.getEndSaleTime().getTime();
						} catch (Exception e) {
							logger.error("获取彩期的开售和停售时间出错,直接认为可分票", e);
							canAllotPhaseList.add(phase);
							continue;
						}
						
						long beginSaleAllotTimeMillis = beginSaleTimeMillis;
						if (beginSaleAllotBackward != 0) {
							// 开始判断开售后延时分票：开售时间 + 延后时间
							beginSaleAllotTimeMillis += beginSaleAllotBackward;
						}
						long endSaleAllotTimeMillis = endSaleTimeMillis;
						if (endSaleAllotForward != 0) {
							// 开始判断停售提前分票：停售时间 - 提前时间
							endSaleAllotTimeMillis -= endSaleAllotForward;
						}
						
						// 如果已经进入了停售前分票提前的时段，允许分票
						if (currentTimeMillis >= endSaleAllotTimeMillis) {
							canAllotPhaseList.add(phase);
							continue;
						}
						
						// 如果已经进入了开售后延迟分票的时段，允许分票
						if (currentTimeMillis >= beginSaleAllotTimeMillis) {
							canAllotPhaseList.add(phase);
							continue;
						}
					}
				}
				
				
				
				for (LotteryPhase phase : canAllotPhaseList) {
					logger.info("要执行分票的彩种({})的当前要分票的彩期为({})", lotteryType.getName(), phase.getPhase());		
				
					while(true) {
						List<Ticket> ticketList = null;
						try {
							ticketList = ticketService.findToAllot(lotteryType.getValue(), phase.getPhase(), maxTicketAllotSize);
						} catch (Exception e) {
							logger.error("查询需要分票的票出错:{}",e.getMessage());
							break;
						}
						if(ticketList==null||ticketList.isEmpty()){
							logger.info("彩种({})当前要分票的彩期({})没有未分配的票", lotteryType.getName(), phase.getPhase());
							break;
						}
						try {
							worker.execute(ticketList, lotteryType, phase.getPhase());
						}catch (LotteryException e){//防止出现死循环
							if (e.getErrorCode()== ErrorCode.no_used_terminal){
								logger.error("彩种:{},期号:{},无可用终端",lotteryType,phase.getPhase());
								break;
							}
						}catch (Exception e) {
							logger.error("查询({})要分批次的票的时候出错!",lotteryType.getName(), e);
							break;
						}
					
						if (ticketList.size() < maxTicketAllotSize) {
							break;
						}
					}
				}
			}
			
			synchronized (this) {
				try {
					wait(this.getInterval());
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

   protected List<LotteryType> get(){
	   if(this.getLotteryList()!=null){
		   return getLotteryList();
	   }
	   return LotteryType.get();
   }

public int getMaxTicketAllotSize() {
	return maxTicketAllotSize;
}

public void setMaxTicketAllotSize(int maxTicketAllotSize) {
	this.maxTicketAllotSize = maxTicketAllotSize;
}
 	
}
