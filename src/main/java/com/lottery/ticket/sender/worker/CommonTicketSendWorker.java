package com.lottery.ticket.sender.worker;

import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketBatchSendResultProcessType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.domain.terminal.TerminalConfig;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.allotter.ITicketAllotWorker;
import com.lottery.ticket.sender.IVenderTicketSendWorker;
import com.lottery.ticket.sender.processor.ITicketSendVenderProcessor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CommonTicketSendWorker extends AbstractTicketSendWorker {
	protected ThreadLocal<TerminalType> terminalTypeThreadLocal = new ThreadLocal<TerminalType>();
	@Resource(name = "venderConverterBinder")
	private Map<TerminalType, IVenderConverter> venderConverterBinder;
	@Resource(name = "terminalTypeVenderTicketSendWorkerBinder")
	private Map<TerminalType, IVenderTicketSendWorker> terminalTypeVenderTicketSendWorkerBinder;
	@Resource(name = "sendResultMap")
	protected Map<TicketSendResultStatus, ITicketSendVenderProcessor> sendResultMap;
	@Resource(name = "executorBinder")
	private Map<LotteryType, ITicketAllotWorker> executorBinder;

	@Override
	public void executeSend(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType) throws Exception {
		TerminalType terminalType = terminalTypeThreadLocal.get();
		IVenderTicketSendWorker venderTicketSendWorker = terminalTypeVenderTicketSendWorkerBinder.get(terminalType);
		if (venderTicketSendWorker == null) {
			throw new RuntimeException(terminalType + "送票worker为空");
		}
		Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
		for (Ticket ticket : ticketList) {
			ticketMap.put(ticket.getId(), ticket);
		}
		List<TicketSendResult> ticketSendResultList = venderTicketSendWorker.executeSend(ticketBatch, ticketList, lotteryType);
		if (ticketSendResultList == null || ticketSendResultList.isEmpty()) {
			String errormsg = String.format("批次(%s)返回送票内容为空,送票不成功", ticketBatch.getId());
			throw new Exception(errormsg);
		}

		Map<TicketBatchSendResultProcessType, Integer> processTypeCountMap = new HashMap<TicketBatchSendResultProcessType, Integer>();
		List<Ticket> needToAllotTicketList = new ArrayList<Ticket>();

		for (TicketSendResult ticketSendResult : ticketSendResultList) {
			Ticket ticket = ticketMap.get(ticketSendResult.getId());
			TicketSendResultStatus resultStatus = ticketSendResult.getStatus();
			ITicketSendVenderProcessor venderProcessor = sendResultMap.get(resultStatus);
			if (venderProcessor == null) {
				logger.error("票:{},状态{}的处理类为空", ticketSendResult.getId(),resultStatus);
				continue;
			}

			TicketBatchSendResultProcessType processType = venderProcessor.process(ticketSendResult, ticket, ticketBatch);
			if (processType == TicketBatchSendResultProcessType.reallot) {
				needToAllotTicketList.add(ticket);
			}

			if (processTypeCountMap.containsKey(processType)) {
				processTypeCountMap.put(processType, processTypeCountMap.get(processType) + 1);
			} else {
				processTypeCountMap.put(processType, 1);
			}
		}
		// 处理完票以后开始处理批次
		int totalCount = ticketList.size();
		int successfulCount = processTypeCountMap.containsKey(TicketBatchSendResultProcessType.success) ? processTypeCountMap.get(TicketBatchSendResultProcessType.success) : 0;
		// 如果全都成功, 直接标记批次送票成功
		if (successfulCount == totalCount) {

			ticketBatch.setStatus(TicketBatchStatus.SEND_SUCCESS.value);
			ticketBatch.setSendTime(new Date());
			ticketBatch.setUpdateTime(new Date());
			ticketBatchService.updateStatusAndSendTime(ticketBatch);
		} else {
			// 处理逻辑
			int reallotCount = needToAllotTicketList.size();
			// 说明又有需要重分的又有需要重试的, 这种情况直接将需要重分的票单独打包新的批次处理
			ITicketAllotWorker allotWorker = this.getAllotExecutor(lotteryType);
			for (Ticket ticket : needToAllotTicketList) {
				String externalId = ticket.getExternalId();
				try {
					// 将需要重分的票分到其他批次，留下当前批次以及该批次下不需要重分的票等待重试
					Long oldterminalId=ticket.getTerminalId();
					Long newterminalId=allotWorker.executeWithFailureCheck(ticket);
					String msg = String.format("票(%s)对方票号(%s)送票失败需要重分, 已自动做拆分成单张批次重新分票处理,新终端是:%s,旧终端是:%s", ticket.getId(), externalId,newterminalId,oldterminalId);
					logger.error(msg);
				} catch (Exception e) {
					logger.error("送票重分出错",e);
				}
			}

			if (reallotCount > 0) {
				// 有需要重分的情况
				// 如果全都需要重分
				if (reallotCount == totalCount - successfulCount) {
					// 直接抛出异常重分当前批次
					logger.error("批次({})送票({})未全部成功,重分终端,{}/{},重分数量:{}",  ticketBatch.getId(), terminalType.getName(), successfulCount, totalCount, reallotCount );
					String exceptionMsg=String.format("彩种%s,批次(%s),送票未全部成功,需要重分", lotteryType,ticketBatch.getId());

					throw new Exception(exceptionMsg);
				}
			} else {
				// 没有需要重分的, 剩下的默认都不抛出异常等待自动重试
				logger.error("批次:({})未全部成功,等待自动重试,票总数:{},成功总数:{}",ticketBatch.getId(),totalCount,successfulCount);
			}
		}

	}

	@Override
	protected boolean beforeHandler(TicketBatch ticketBatch, LotteryType lotteryType) throws Exception{
		TerminalType terminalType = this.getTerminalSelector().getTerminalType(ticketBatch.getTerminalId());
		terminalTypeThreadLocal.set(terminalType);
		if (terminalType == TerminalType.T_Virtual) {
			return false;
		}
		Long terminalId=ticketBatch.getTerminalId();
		Terminal terminal=venderConfigService.getTerminal(terminalId);
		if(terminal!=null&&terminal.getIsPaused()==YesNoStatus.yes.value){
			logger.error("终端:{}全局暂停送票",terminalId);
			return true;
		}
		
		TerminalConfig  terminalConfig=venderConfigService.getTerminalConfig(lotteryType.value, terminalId, ticketBatch.getPlayType());//判断整个彩种
		if(terminalConfig!=null&&terminalConfig.getIsPaused()==YesNoStatus.yes.value){
			logger.error("终端:{},彩种:{},全局暂停送票",terminalId,lotteryType);
			return true;
		}
		
		
		IVenderConverter venderConverter = this.getVenderConverter(terminalType);
		if (venderConverter != null) {
			return venderConverter.isDuringSendForbidPeriod(lotteryType);
		}
		
		return false;
	}

	protected boolean terminalIsEnabled(TicketBatch ticketBatch, LotteryType lotteryType) throws Exception {
		if (terminalTypeThreadLocal.get() == TerminalType.T_Virtual) {
			return true;
		}
		return super.terminalIsEnabled(ticketBatch, lotteryType);
	}

	protected IVenderConverter getVenderConverter(TerminalType terminalType) {
		return venderConverterBinder.get(terminalType);
	}

	public ITicketAllotWorker getAllotExecutor(LotteryType lotteryType) {
		ITicketAllotWorker worker = this.executorBinder.get(lotteryType);
		if (worker == null) {
			worker = this.executorBinder.get(LotteryType.ALL);
		}
		return worker;
	}
}
