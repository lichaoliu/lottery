package com.lottery.core.dao;

import java.util.List;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.JclqRace;

public interface JclqRaceDAO extends IGenericDAO<JclqRace, String> {

	/**
	 * 获取已经开奖的赛事 static_sale_bf_status和dynamic_sale_bf_status为开奖
	 * @param matchid
	 * @return
	 */
	public JclqRace getJclqRaceResult(String matchid);
	
	/**
	 * 查找赛事状态为结束或者取消的，赛果状态为 赛果已公布 的 最小的场次id
	 * @return
	 */
	public String getMinCloseAndResultMatchid(); 
	
	/**
	 * 查找赛事状态为结束或者取消的，赛果状态为 赛果已公布 的 最大的场次id
	 * @return
	 */
	public String getMaxCloseAndResultMatchid(); 
	/**
	 * 查询场次编号在minMatchid和maxMatchid范围内的赛事
	 * @param minMatchid
	 * @param maxMatchid
	 * @return
	 */
	public List<JclqRace> getRaces(String minMatchid,String maxMatchid);
	/**
	 * 根据期号查询对阵
	 * @param phase 期号
	 * @return List
	 * */
	public List<JclqRace> getByPhase(String phase);
	
	/**
	 * 查询已结束取消赛果未设置赛果的赛事
	 * @return
	 */
	public List<JclqRace> getCloseAndNoResult();
	/**
	 * 根据开奖状态查询已结束,取消赛果的赛事
	 * @param phase 期号
	 * @param prizeStatus 开奖状态
	 * @return
	 */
	public List<JclqRace> getCloseByPhaseAndPrizeStatus(String phase,Integer prizeStatus);
}
