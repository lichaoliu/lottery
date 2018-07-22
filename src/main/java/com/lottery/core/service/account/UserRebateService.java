package com.lottery.core.service.account;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.PageBean;
import com.lottery.common.contains.account.RebateType;
import com.lottery.common.util.StringUtil;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.UserInfoDao;
import com.lottery.core.dao.UserRebateDAO;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.account.UserRebate;

@Service
public class UserRebateService {
    @Autowired
	protected UserRebateDAO userRebateDAO;
	@Autowired
    protected IdGeneratorDao idGeneratorDao;
	@Autowired
	protected UserInfoDao userInfoDao;
	@Transactional
	public void save(UserRebate entity){
		String id=idGeneratorDao.getUserRebateId();
		entity.setId(id);
		userRebateDAO.insert(entity);
	}
	
	@Transactional
	public UserRebate saveOrUpdate(UserRebate userRebate) {
		if(StringUtil.isEmpty(userRebate.getId())){
			String id=idGeneratorDao.getUserRebateId();
			userRebate.setId(id);
			userRebate.setCreateTime(new Date());
			UserInfo userInfo=userInfoDao.find(userRebate.getUserno());
			userRebate.setUserName(userInfo.getUsername());
			userRebateDAO.insert(userRebate);
			return userRebate;
		}else{
			UserRebate ur = userRebateDAO.find(userRebate.getId());
			ur.setAgencyno(userRebate.getAgencyno());
			ur.setBetAmount(userRebate.getBetAmount());
			ur.setIsAgent(userRebate.getIsAgent());
			ur.setLotteryType(userRebate.getLotteryType());
			ur.setPointLocation(userRebate.getPointLocation());
			ur.setRebateType(userRebate.getRebateType());
			ur.setUserno(userRebate.getUserno());
			ur.setIsPaused(userRebate.getIsPaused());
			userRebateDAO.merge(ur);
			return ur;
		}
	}
	
	@Transactional
	public void delete(String strChecked) {
		String[] ids = strChecked.split(",");
		for (String id : ids) {
			UserRebate userRebate = userRebateDAO.find(id);
			userRebateDAO.remove(userRebate);
		}
	}
	
	/**
	 * 根据返点类型查询
	 * @param rebateType 返点类型
	 * 
	 * */
	@Transactional
	public List<UserRebate> getByType(int rebateType,PageBean<UserRebate> page){
		return userRebateDAO.getByType(rebateType, page);
	}
	/**
	 * 获取浮动返点
	 * @param userno 用户编号
	 * @param lotteryType 彩种
	 * @param amount 投注金额
	 * */
	@Transactional
	public List<UserRebate> getFloat(String userno,int lotteryType,BigDecimal amount){
		List<UserRebate> rebateList=userRebateDAO.getFloat(userno, lotteryType, amount);
		Collections.sort(rebateList, new Comparator<UserRebate>(){
			@Override
			public int compare(UserRebate o1, UserRebate o2) {
				return o1.getBetAmount().compareTo(o2.getBetAmount());
			}
		});
		return rebateList;
	}
	/**
	 * 根据金额获得返点
	 * @param userno 用户编号
	 * @param lotteryType 彩种
	 * @param amount 投注金额
	 * */
	@Transactional
	public List<UserRebate> getTopRebate(String userno,int lotteryType,BigDecimal amount){
		List<UserRebate> rebateList=userRebateDAO.getFloat(userno, lotteryType, amount);
		if(rebateList==null||rebateList.size()==0){
			rebateList=userRebateDAO.getByUsernoAndLottery(userno, lotteryType, RebateType.float_rebate.value, 2);
		}
		Collections.sort(rebateList, new Comparator<UserRebate>(){
			@Override
			public int compare(UserRebate o1, UserRebate o2) {
				return o1.getBetAmount().compareTo(o2.getBetAmount());
			}
		});
		return rebateList;
	}
	
}
