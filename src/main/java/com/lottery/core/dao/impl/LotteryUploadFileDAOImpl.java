package com.lottery.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.LotteryUploadFileDAO;
import com.lottery.core.domain.ticket.LotteryUploadFile;
@Repository
public class LotteryUploadFileDAOImpl extends AbstractGenericDAO<LotteryUploadFile, String> implements LotteryUploadFileDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
