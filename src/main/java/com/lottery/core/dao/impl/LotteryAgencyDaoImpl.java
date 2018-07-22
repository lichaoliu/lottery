package com.lottery.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.LotteryAgencyDao;
import com.lottery.core.domain.agency.LotteryAgency;

@Repository
public class LotteryAgencyDaoImpl  extends AbstractGenericDAO<LotteryAgency, String> implements LotteryAgencyDao{

	public List<LotteryAgency> findAgencyByParent(String parentAgencyNo){
		return findByCondition(" parentAgency = ?", new Object[]{parentAgencyNo});
	}
}
