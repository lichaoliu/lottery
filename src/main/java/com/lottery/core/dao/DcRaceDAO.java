package com.lottery.core.dao;

import java.util.List;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.DcType;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.DcRace;

public interface DcRaceDAO extends IGenericDAO<DcRace, Long> {

	public DcRace getDcRaceResult(String phase, int matcheid, DcType type);

	public DcRace getDcRace(String matchid, String phase, DcType type);

	/**
	 * 查找赛事状态为结束或者取消的，赛果状态为 结果已公布 的 最小的场次id
	 * 
	 * @return
	 */
	public Integer getMinCloseAndResultMatchid(String phase, DcType type);

	/**
	 * 查找赛事状态为结束或者取消的，赛果状态为 结果已公布 的 最大的场次id
	 * 
	 * @return
	 */
	public Integer getMaxCloseAndResultMatchid(String phase, DcType type);

	/**
	 * 查询场次编号在minMatchid和maxMatchid范围内的赛事
	 * 
	 * @param minMatchid
	 * @param maxMatchid
	 * @return
	 */
	public List<DcRace> getRaces(String phase, int minMatchid, int maxMatchid,DcType type);

	/**
	 * 根据期号,单场类型,对阵状态查询对阵
	 * 
	 * @param phase
	 *            期号
	 * @param dcType
	 *            单场类型
	 * @param statusList
	 *            对阵状态
	 * */
	public List<DcRace> getByPhaseAndStatus(String phase, int dcType, List<Integer> statusList);

	/**
	 * 根据期号,单场类型查询对阵
	 * 
	 * @param phase
	 *            期号
	 * @param dcType
	 *            单场类型
	 * */
	public List<DcRace> getByPhaseAndType(String phase, int dcType,PageBean<DcRace> pageBean);

	/**
	 * 查询已结束取消赛果未设置赛果的赛事
	 * 
	 * @return
	 */
	public List<DcRace> getCloseAndNoResult();
	/**
	 * 根据开奖状态查询已结束,取消赛果的赛事
	 * @param phase 期号
	 * @param prizeStatus 开奖状态
	 * @return
	 */
	public List<DcRace> getCloseByPhaseAndPrizeStatus(String phase,Integer prizeStatus, DcType dcType);
}
