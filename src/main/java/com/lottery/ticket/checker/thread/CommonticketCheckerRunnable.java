package com.lottery.ticket.checker.thread;

import java.util.List;

import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.checker.worker.IVenderTicketCheckWorker;

public class CommonticketCheckerRunnable extends AbstractVenderTicketCheckerRunnable{

	@Override
    protected List<TicketVender> executeCheck(List<Ticket> ticketList, IVenderConfig venderConfig) throws Exception {
	    IVenderTicketCheckWorker ticketCheckWorker=venderCheckerWorkerMap.get(getTerminalType());
	    if(ticketCheckWorker==null||getVenderConverter()==null){
	    	logger.error("终端{}的检票配置={},转换配置={},两者都不为空",getTerminalType(),ticketCheckWorker,getVenderConverter());
	    	return null;
	    }
	    return ticketCheckWorker.process(ticketList,venderConfig,getVenderConverter());
    }

	


	
}
