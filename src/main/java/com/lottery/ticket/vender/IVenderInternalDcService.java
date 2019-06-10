package com.lottery.ticket.vender;

import java.util.Collection;
import java.util.Map;

import net.sf.json.JSONArray;

import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;

public interface IVenderInternalDcService {
	/**
	 * 获取北京单场赛程
	 */
	public JSONArray getBjdcSchedule(Map<String, String> requestMap);

	/**
	 * 获取北京单场开奖结果
	 */
	public JSONArray getBjdcDrawResult( Map<String, String> requestMap);

	/**
	 * 获取北京单场实时sp值
	 */
	public Collection<MatchSpDomain> getBjdcInstantSp(Map<String, String> requestMap);
	
	/**
	 * 获取北京单场新期
	 */
	public JSONArray getBjdcPhase(Map<String, String> requestMap);

}
