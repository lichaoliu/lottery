package com.lottery.core.dao;

import com.lottery.common.PageBean;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.LotteryCaseLot;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface LotteryCaseLotDao extends IGenericDAO<LotteryCaseLot, String> {
	/**
	 * 计算该合买所有冻结保底金额
	 */
	public BigDecimal computeFreezeSafeAmt(String caselotId);

	public Long computeParticipantCount(String caselotId);
	
	public void findCaseLotsByPage(Integer[] state, Integer[] lotno, String search, Map<String, Object> conditionMap, PageBean<LotteryCaseLot> page);
	/**
	 * 查询用户参与的合买分页
	 * 
	 * @param state
	 *            合买状态数组
	 * @param userno
	 *            参与者用户编号
	 * @param conditionMap
	 *            查询条件集合
	 * @param page
	 *            分页集合
	 * @return
	 */
	public void findCaseLotsByUserno(Integer[] state, String userno, Integer buyType, Map<String, Object> conditionMap, PageBean<LotteryCaseLot> page);
	/**
	 * 根据状态查询
	 * @param  max 最大条数
	 * @param status 状态
	 * */
	public List<LotteryCaseLot> getByStatus(int max,int status);
}
