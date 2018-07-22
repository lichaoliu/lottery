package com.lottery.ticket.vender.impl;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.IVenderInternalDcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInternalDcService implements
		IVenderInternalDcService {

	    @Autowired
		protected VenderConfigHandler venderConfigService;
		protected abstract TerminalType getTerminalType();
		
		protected List<IVenderConfig> getVenderCofig(){
			return venderConfigService.getAllByTerminalType(getTerminalType());
		}

}
