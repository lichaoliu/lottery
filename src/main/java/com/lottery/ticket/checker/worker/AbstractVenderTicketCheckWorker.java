package com.lottery.ticket.checker.worker;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.core.IdGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;


public abstract class AbstractVenderTicketCheckWorker implements IVenderTicketCheckWorker {

	protected final Logger logger=LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	protected IdGeneratorService idGeneratorService;
	@Resource(name="venderCheckerWorkerMap")
	protected Map<TerminalType,IVenderTicketCheckWorker> venderCheckerWorkerMap;
	
	protected abstract TerminalType getTerminalType();

    @PostConstruct
    protected void init(){
    	venderCheckerWorkerMap.put(getTerminalType(), this);
    }
    
    
    protected TicketVender createTicketVender(Long  terminalId,TerminalType terminalType){
    	TicketVender ticketVender=new TicketVender();
    	ticketVender.setTerminalId(terminalId);
    	ticketVender.setTerminalType(terminalType);
		ticketVender.setTicketNotify(false);
    	return ticketVender;
    }
    
   
}
