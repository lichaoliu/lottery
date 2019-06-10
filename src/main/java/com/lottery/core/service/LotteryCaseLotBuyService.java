package com.lottery.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.PageBean;
import com.lottery.common.util.BetweenDate;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.SpecialDateUtils;
import com.lottery.core.dao.LotteryCaseLotBuyDao;
import com.lottery.core.domain.LotteryCaseLotBuy;
@Service
public class LotteryCaseLotBuyService {
    @Autowired
	private LotteryCaseLotBuyDao lotBuyDao;
	/**
	 * 根据用户查询合买记录
	 * @param userno 用户编号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param page 分页
	 * 
	 * */
	public List<LotteryCaseLotBuy> getByUserno(String userno,String startTime,String endTime,PageBean<LotteryCaseLotBuy> page){
		BetweenDate bd=SpecialDateUtils.getBetweenDate(3, startTime, endTime);
		Date startDate=bd.getStartDate();
		Date endDate=bd.getEndDate();
		return lotBuyDao.getByUserno(userno, startDate, endDate, page);
	}
	
	 /**
     * 根据用户，彩种，订单状态，时间查询合买消费额
     * @param userno 用户名
     * @param lotteryType 彩种
     * @param startTime 开始时间 yyyy-mm-dd hh:mm:ss
     * @param endTime 结束时间
     * */
    @Transactional
    public Object[] getCaselotUserbet(String userno,int flag,int lotteryType,String startTime,String endTime){
    	 Date startDate=CoreDateUtils.StrToDate(startTime);
         Date endDate=CoreDateUtils.StrToDate(endTime);
    	 return lotBuyDao.getCaselotUserbet(userno, flag, lotteryType, startDate, endDate);
    }
    
	/**
	 * 根据合买id,状态查询
	 * @param caselotId 合买id
	 * @param state 状态
	 * **/
    @Transactional
	public List<LotteryCaseLotBuy> findCaseLotBuysByCaselotIdAndState(String caselotId,int state){
		return lotBuyDao.findCaseLotBuysByCaselotIdAndState(caselotId, state);
	}
    @Transactional
    public void update(LotteryCaseLotBuy lotteryCaseLotBuy){
    	 lotBuyDao.merge(lotteryCaseLotBuy);
    }
    
    /**
	 * 根据合买id,购买方式查询
	 * @param caselotId 合买id
	 * @param buyType 购买方式
	 * **/
    @Transactional
	public List<LotteryCaseLotBuy> findCaseLotBuysByCaselotIdAndBuyType(String caselotId,	int buyType){
		return lotBuyDao.findCaseLotBuysByCaselotIdAndBuyType(caselotId, buyType);
	}

	/**
	 * 根据合买id修改购买状态
	 * @param  caselotId 合买id
	 * @param  caslelotBuyState 状态
	 * */
	@Transactional
	public int updateStateByCaselotId(String caselotId,int caslelotBuyState){
	return  	lotBuyDao.updateStateByCaselotId(caselotId, caslelotBuyState);
	}

	/**
	 *
	 * 根据合买id查询合买购买记录
	 * @param  caseLotId 合买编号
	 * */

	@Transactional
	public List<LotteryCaseLotBuy> getByCaseLotId(String caseLotId){
		return lotBuyDao.getByCaseLotId(caseLotId);
	}
}
