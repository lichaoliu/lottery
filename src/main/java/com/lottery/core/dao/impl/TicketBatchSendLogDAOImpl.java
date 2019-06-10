package com.lottery.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.TicketBatchSendLogDAO;
import com.lottery.core.domain.ticket.TicketBatchSendLog;
@Repository
public class TicketBatchSendLogDAOImpl extends AbstractGenericDAO<TicketBatchSendLog, String> implements TicketBatchSendLogDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
