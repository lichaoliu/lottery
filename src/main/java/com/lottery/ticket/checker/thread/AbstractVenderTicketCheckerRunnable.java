package com.lottery.ticket.checker.thread;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.domain.terminal.TerminalConfig;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.service.TicketService;
import com.lottery.core.terminal.ITerminalSelector;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.checker.worker.IVenderTicketCheckWorker;
import com.lottery.ticket.processor.ITicketVenderProcessor;
import com.lottery.core.handler.VenderConfigHandler;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import java.util.*;

public abstract class AbstractVenderTicketCheckerRunnable extends AbstractThreadRunnable {

	@Resource(name = "venderCheckerWorkerMap")
	protected Map<TerminalType, IVenderTicketCheckWorker> venderCheckerWorkerMap;
	@Resource(name = "ticketVenderProcessorMap")
	protected Map<TicketVenderStatus, ITicketVenderProcessor> ticketVenderProcessorMap;
	@Resource(name = "venderConverterBinder")
	protected Map<TerminalType, IVenderConverter> venderConverterBinder;
	private long interval = 10000;
	@Autowired
	protected VenderConfigHandler venderConfigService;
	@Autowired
	protected ITerminalSelector terminalSelector;
	@Autowired
	protected TicketService ticketService;

	protected int checkcount = 10;

	protected int timeOutSecondForCheck = 30;// 秒



	private List<LotteryType> specialLotteryTypeList;//特殊彩种检票


	private List<LotteryType> excludeLotteryTypeList;//排除彩种

	
	protected TerminalType terminalType;

	@Override
	public void executeRun() {
		while (running) {
			try {
				execute();
			} catch (Exception e) {
				logger.error("检票出现错", e);
			}
			synchronized (this) {
				try {
					wait(this.getInterval());
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
				}
			}

		}

	}




	protected void execute() {
		if (getTerminalType() == null) {
			logger.error("终端不能为空");
			return;
		}

		List<IVenderConfig> configList = venderConfigService.getAllByTerminalType(getTerminalType());
		for (IVenderConfig venderConfig : configList) {
			Long terminalId = venderConfig.getTerminalId();
			// 如果终端不存在、禁用异步检票的终端不执行本次查票
			if (!terminalSelector.isCheckEnabled(terminalId)) {
				logger.error("终端[{}]已禁止检票",terminalId);
				continue;
			}

			List<Integer> enableList = getLotteryList(terminalId);
			if (enableList == null || enableList.isEmpty()) {
				continue;
			}

            this.check(enableList,terminalId,venderConfig);

		}
	}


	protected void check(List<Integer> enableList,Long terminalId,IVenderConfig venderConfig){
		for (Integer lotteryType:enableList){
			List<Ticket> ticketList = ticketService.getUnCheckTicket(lotteryType,terminalId, getCheckcount(venderConfig), getTimeOutForCheck(venderConfig));

			if (ticketList==null||ticketList.size()==0){
				continue;
			}
			int len = 0;
			int ticketCount=ticketList.size();
			if (ticketCount % checkcount == 0) {
				len =ticketCount/checkcount;
			} else {
				len =ticketCount/checkcount + 1;
			}
			for (int i=0;i<len;i++){

				List<Ticket> ticketBatchList=null;
				if (((i * checkcount) + checkcount) <ticketCount) {
					ticketBatchList = ticketList.subList(i * checkcount, i* checkcount + checkcount);
				} else {
					ticketBatchList = ticketList.subList(i * checkcount,ticketCount);
				}
				this.executePreCheck(ticketBatchList,venderConfig);
			}

		}
	}



	private void executePreCheck(List<Ticket> ticketList,IVenderConfig venderConfig) {
		try{
			if (ticketList == null || ticketList.isEmpty()) {
				return;
			}
			Map<String, Ticket> mapTicket = new HashMap<String, Ticket>();
			for (Ticket ticket : ticketList) {
				mapTicket.put(ticket.getId(), ticket);
			}
			List<TicketVender>	ticketVenderList = this.executeCheck(ticketList, venderConfig);
			if (ticketVenderList == null || ticketVenderList.isEmpty()) {
				logger.error("查询返回结果为空,请检查");
				return;
			}
			for (TicketVender ticketVender : ticketVenderList) {
				TicketVenderStatus ticketVenderStatus = ticketVender.getStatus();
				ITicketVenderProcessor process = ticketVenderProcessorMap.get(ticketVenderStatus);
				if (process != null) {
					Ticket ticket = mapTicket.get(ticketVender.getId());
					process.process(ticket, ticketVender);
				} else {
					logger.error("{}状态下的票处理器不存在", ticketVenderStatus);
				}
			}
		}catch (Exception e){
             logger.error("查票失败",e);
		}

	}

	abstract protected List<TicketVender> executeCheck(List<Ticket> ticketList, IVenderConfig venderConfig) throws Exception;

	protected List<Integer> getLotteryList(Long terminalId){


		List<Integer> allList=new ArrayList<Integer>();

		if(getSpecialLotteryTypeList()!=null&&getSpecialLotteryTypeList().size()>0){

			for(LotteryType lotteryType:getSpecialLotteryTypeList()){

				boolean flag=isEnableForCheck(LotteryType.getLotteryType(lotteryType.value),terminalId);
				if (flag){
					allList.add(lotteryType.value);
				}
			}
			return allList;
		}


		List<TerminalConfig> allTerminalConfigList = terminalSelector.getTerminalConfigList(terminalId);
		if (allTerminalConfigList==null||allTerminalConfigList.size()==0){
			return allList;
		}
		for (TerminalConfig terminalConfig : allTerminalConfigList){
			Integer lotteryType=terminalConfig.getLotteryType();
			boolean flag=isEnableForCheck(LotteryType.getLotteryType(lotteryType),terminalId);
			if (flag){
				allList.add(lotteryType);
			}
		}

		List<Integer> excludeList=new ArrayList<Integer>();


		if(getExcludeLotteryTypeList()!=null&&getExcludeLotteryTypeList().size()>0){
			for (LotteryType lotteryType:getExcludeLotteryTypeList()){
				excludeList.add(lotteryType.value);
			}
		}

		if(excludeList.size()>0){
			allList.removeAll(excludeList);
		}
		return allList;


	}

	protected  boolean isEnableForCheck(LotteryType lotteryType,Long terminalId){

		if (terminalSelector.isGlobalCheckDisabledOrDuringCheckForbidPeriod(lotteryType)) {
			logger.warn("彩种{}正处于全局禁止检票周期", lotteryType);
			return  false;
		}

		//如果终端不存在、禁用异步检票的某彩种终端不执行本次查票
		if (!terminalSelector.isCheckEnabledForLotteryType(terminalId, lotteryType)) {
			logger.warn("彩种{}当前终端设置为禁止检票, terminalId={}", lotteryType, terminalId);
			return false;
		}

		if (terminalSelector.isDuringCheckForbidPeriod(terminalId, lotteryType)) {
			logger.warn("彩种{}当前终端正处于禁止检票周期, terminalId={}", lotteryType, terminalId);
			return  false;
		}
		return true;

	}
	
	protected IVenderConverter getVenderConverter(){
		return venderConverterBinder.get(getTerminalType());
	}




	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}



	public void setTerminalType(TerminalType terminalType) {
		this.terminalType = terminalType;
	}

	public TerminalType getTerminalType() {
		return terminalType;
	}

	public int getCheckcount(IVenderConfig venderConfig) {
		if (venderConfig != null && venderConfig.getCheckCount() != null)
			return venderConfig.getCheckCount();
		return checkcount;
	}

	public void setCheckcount(int checkcount) {
		this.checkcount = checkcount;
	}

	public Date getTimeOutForCheck(IVenderConfig venderConfig) {
		int timeout=timeOutSecondForCheck;
		if (venderConfig != null && venderConfig.getTimeOutSecondForCheck() != null)
			timeout= venderConfig.getTimeOutSecondForCheck();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MILLISECOND, -1 * timeout * 1000);
		return cal.getTime();
	}

	public void setTimeOutSecondForCheck(int timeOutSecondForCheck) {
		this.timeOutSecondForCheck = timeOutSecondForCheck;
	}

	public List<LotteryType> getSpecialLotteryTypeList() {
		return specialLotteryTypeList;
	}

	public void setSpecialLotteryTypeList(List<LotteryType> specialLotteryTypeList) {
		this.specialLotteryTypeList = specialLotteryTypeList;
	}

	public List<LotteryType> getExcludeLotteryTypeList() {
		return excludeLotteryTypeList;
	}

	public void setExcludeLotteryTypeList(List<LotteryType> excludeLotteryTypeList) {
		this.excludeLotteryTypeList = excludeLotteryTypeList;
	}


}
