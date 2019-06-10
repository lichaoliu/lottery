package com.lottery.core.dao;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.terminal.Terminal;


public interface TerminalDAO  extends IGenericDAO<Terminal, Long>{
	public Long[] getTerminalIdByType(int terminalType);

	
	public Terminal getByType(int terminalType);





}
