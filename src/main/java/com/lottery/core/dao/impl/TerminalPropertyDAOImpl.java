package com.lottery.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.TerminalPropertyDAO;
import com.lottery.core.domain.terminal.TerminalProperty;
@Repository
public class TerminalPropertyDAOImpl extends AbstractGenericDAO<TerminalProperty, Long> implements TerminalPropertyDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2758700672045872470L;

}
