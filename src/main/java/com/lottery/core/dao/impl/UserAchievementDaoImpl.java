package com.lottery.core.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.UserAchievementDao;
import com.lottery.core.domain.UserAchievement;

@Repository
public class UserAchievementDaoImpl  extends AbstractGenericDAO<UserAchievement, Long> implements UserAchievementDao{

	@Transactional
	public UserAchievement createUserachievement(String userno, Integer lotteryType, Long id) {
		UserAchievement userachievement = new UserAchievement();
		userachievement.setId(id);
		userachievement.setUserno(userno);
		userachievement.setLotteryType(lotteryType);
		userachievement.setEffectiveAchievement(BigDecimal.ZERO);
		userachievement.setIneffectiveAchievement(BigDecimal.ZERO);
		userachievement.setUpdateTime(new Date());
		this.insert(userachievement);
		return userachievement;
	}
	
	public UserAchievement findByUsernoAndLottype(String userno, Integer lotteryType) {
		List<UserAchievement> list = this.findByCondition("userno = ? AND lotteryType = ?", new Object[]{userno, lotteryType});
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}
