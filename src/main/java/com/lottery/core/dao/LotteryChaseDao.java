package com.lottery.core.dao;

import com.lottery.common.PageBean;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.LotteryChase;

import java.util.Date;
import java.util.List;

public interface LotteryChaseDao extends IGenericDAO<LotteryChase, String> {

	/**
	 * 查询追号
	 * @param userno 用户编号
	 * @param lotteryType 彩种
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param page 分页
	 * */
	public List<LotteryChase> get(String userno,Integer lotteryType,Date startDate,Date endDate,int state,PageBean<LotteryChase> page);


	/**
	 * 查询追号
	 * @param lotteryType 彩种
	 * @param state 状态
	 * @param page 分页
	 * */
	public List<LotteryChase> get(int lotteryType,int state,PageBean<LotteryChase> page);


}
