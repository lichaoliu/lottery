package com.lottery.core.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.caselot.AutoJoinDetailState;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.AutoJoin;
import com.lottery.core.domain.AutoJoinDetail;

public interface AutoJoinDetailDao extends IGenericDAO<AutoJoinDetail, Long>{

	public AutoJoinDetail createAutoJoinDetail(AutoJoin autoJoin, String caselotid,
			BigDecimal joinAmt, AutoJoinDetailState joinstate, String memo,
			String caselotbuyid, Integer lotteryType);

	public void findAutoJoinDetailByPage(Map<String, Object> conditionMap,
			PageBean<AutoJoinDetail> page);
	
}
