package com.lottery.core.dao;

import java.util.List;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.agency.LotteryAgency;

public interface LotteryAgencyDao extends IGenericDAO<LotteryAgency, String>{
	
	/**
	 * 某个父渠道下的所有子渠道
	 * @param parentAgencyNo 父渠道号
	 * @return
	 */
	public List<LotteryAgency> findAgencyByParent(String parentAgencyNo);
}
