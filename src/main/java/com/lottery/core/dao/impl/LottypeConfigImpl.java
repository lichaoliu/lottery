package com.lottery.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.LottypeConfigDAO;
import com.lottery.core.domain.LottypeConfig;
@Repository
public  class LottypeConfigImpl extends AbstractGenericDAO<LottypeConfig, Integer> implements LottypeConfigDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8088387006415166705L;
}
