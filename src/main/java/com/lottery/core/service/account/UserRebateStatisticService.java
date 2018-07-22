package com.lottery.core.service.account;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.core.dao.UserRebateStatisticDao;
import com.lottery.core.domain.account.UserRebateStatistic;
import com.lottery.core.domain.account.UserRebateStatisticPK;

@Service
public class UserRebateStatisticService {
    @Autowired
	protected UserRebateStatisticDao userRebateStatisticDao;
    @Transactional
    public void save(UserRebateStatistic entity){
    	userRebateStatisticDao.insert(entity);
    }
    
    @Transactional
    public void update(UserRebateStatistic entity){
    	userRebateStatisticDao.merge(entity);
    }
    
    @Transactional
    public UserRebateStatistic get(UserRebateStatisticPK id){
    	return userRebateStatisticDao.find(id);
    }
    
    /**
	 * 根据用户名，统计彩种，月份查询
	 * @param userno 编号
	 * @param lotteryType 彩种
	 * @param monthNum 月份
	 * **/
    @Transactional
	public List<UserRebateStatistic> getByUsernoAndMonth(String userno,int lotteryType,Long monthNum){
		return userRebateStatisticDao.getByUsernoAndMonth(userno, lotteryType, monthNum);
	}
	/**
	 * 根据用户名，统计彩种，月份 查询投注总金额
	 * @param userno 编号
	 * @param lotteryType 彩种
	 * @param monthNum 月份
	 * **/
    @Transactional
	public BigDecimal getMonthTotal(String userno,int lotteryType,Long monthNum){
		return userRebateStatisticDao.getMonthTotal(userno, lotteryType, monthNum);
	}
}
