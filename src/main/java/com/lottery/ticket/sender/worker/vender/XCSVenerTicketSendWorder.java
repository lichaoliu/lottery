package com.lottery.ticket.sender.worker.vender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.LogicMachineStatus;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.core.domain.ticket.LotteryLogicMachine;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.LotteryLogicMachineService;
import com.lottery.core.service.PrizeService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;


public class XCSVenerTicketSendWorder extends AbstractVenderTicketSendWorker {
	@Autowired
	protected LotteryLogicMachineService logicService;
    @Autowired
	protected PrizeService prizeService;
	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList,LotteryType lotteryType, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<LotteryLogicMachine> list = getList(lotteryType.value, ticketBatch.getTerminalId());
		if (list == null || list.size() == 0) {
			throw new Exception("小彩神终端选择为0");
		}

		List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
		
		
		try{
			int preThreadCount = list.size();
			for (int i = 0; i < ticketList.size(); i += preThreadCount) {
				int sub_start = i;
				int sub_end = i + preThreadCount;

				// 参考subList方法的声明，这里是 *大于* 而不是 *>=*
				if (sub_end > ticketList.size()) {
					sub_end = ticketList.size();
				}

				// 分拆待处理任务
				List<Ticket> subList = ticketList.subList(sub_start, sub_end);

				if (subList == null || subList.size() == 0) {
					continue;
				}
				ThreadFactory threadFactory = new ThreadFactory() {
					private int i = 0;

					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r, "xcs_ticketsend_thread_" + i++);
					}

				};
				ExecutorService queryThreadPool = Executors.newCachedThreadPool(threadFactory);
				CountDownLatch sendLatch = new CountDownLatch(subList.size());
				IVenderConverter xcsConverter = getVenderConverter();
				for (int index = 0; index < subList.size(); index++) {
	                LotteryLogicMachine logicMachine=list.get(index);
					Ticket ticket = subList.get(index);
					TicketSendResult ticketSendResult = createInitializedTicketSendResult(ticket);
					ticketSendResultList.add(ticketSendResult);
					XCSTicketSendRunnbale sendRunable = new XCSTicketSendRunnbale(venderConfig,logicMachine,ticket, ticketSendResult, sendLatch,xcsConverter,prizeService);
					queryThreadPool.execute(sendRunable);

				}
				try {
					sendLatch.await();
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}

				queryThreadPool.shutdown();
				
			/*	synchronized (this) {
					try {
						this.wait(5000);
					
					} catch (InterruptedException e) {
						logger.error(e.getMessage(), e);
					}
				}*/
				

				
			}
			
		}catch(Exception e){
			logger.error("总体送票出错",e);
		}
		

		return ticketSendResultList;
	}

	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_XCS;
	}

	protected List<LotteryLogicMachine> getList(int lotteryType, Long terminalId) {
		int terminalType = getTerminalType().value;
		List<LotteryLogicMachine> list = logicService.getByTerminalLotteryAndId(terminalType, lotteryType, terminalId);
		if (list == null || list.isEmpty()) {
			list = logicService.getByTerminalLotteryAndId(terminalType, LotteryType.ALL.value, terminalId);
		}
		return filterLogicMatchine(list);

	}

	protected List<LotteryLogicMachine> filterLogicMatchine(List<LotteryLogicMachine> list) {

		List<LotteryLogicMachine> enableList = new ArrayList<LotteryLogicMachine>();
				//new CopyOnWriteArrayList<LotteryLogicMachine>();
		if (list != null && list.size() > 0) {
			for (LotteryLogicMachine matchine : list) {
				if (matchine.getStatus() == LogicMachineStatus.useing.value) {
					enableList.add(matchine);
				}
			}
		}
		Collections.sort(enableList,new Comparator<LotteryLogicMachine>() {

			@Override
			public int compare(LotteryLogicMachine o1, LotteryLogicMachine o2) {
				if(o1.getWeight()!=null&&o2.getWeight()!=null){
					return o1.getWeight().compareTo(o2.getWeight());
				}
				return 0;
			}
		});
		return enableList;
	}

}
