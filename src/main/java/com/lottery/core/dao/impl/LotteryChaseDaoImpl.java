package com.lottery.core.dao.impl;

import com.lottery.common.PageBean;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.LotteryChaseDao;
import com.lottery.core.domain.LotteryChase;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LotteryChaseDaoImpl extends AbstractGenericDAO<LotteryChase, String> implements LotteryChaseDao{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5815880118427090017L;






	@Override
	public List<LotteryChase> get(String userno, Integer lotteryType, Date startDate, Date endDate, int state,PageBean<LotteryChase> page) {
		Map<String,Object> map=new HashMap<String, Object>();
		StringBuffer sb=new StringBuffer();
		sb.append("userno=:userno");
		if(lotteryType!=null){
			sb.append(" and lotteryType=:lotteryType");
			map.put("lotteryType", lotteryType);
		}
		if(state!=-1) {
			sb.append(" and state=:state");
			map.put("state", state);
		}
		sb.append(" and createTime>=:startDate and createTime<=:endDate");
		map.put("userno", userno);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		String orderBy=" order by createTime desc";
		return findPageByCondition(sb.toString(),map,page,orderBy);
	}

	@Override
	public List<LotteryChase> get(int lotteryType, int state, PageBean<LotteryChase> page) {
		Map<String,Object> whereMap=new HashMap<String, Object>();
		whereMap.put("lotteryType",lotteryType);
		whereMap.put("state",state);
		return findPageByCondition(whereMap,page);
	}


}
