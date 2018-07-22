package com.lottery.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.PrizeErrorLogDAO;
import com.lottery.core.domain.PrizeErrorLog;

@Repository
public class PrizeErrorLogDAOImpl extends
		AbstractGenericDAO<PrizeErrorLog, String> implements
		PrizeErrorLogDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1581410397140693680L;

	
}
