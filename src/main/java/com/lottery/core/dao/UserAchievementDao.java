package com.lottery.core.dao;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.UserAchievement;

public interface UserAchievementDao extends IGenericDAO<UserAchievement, Long>{
	
	public UserAchievement createUserachievement(String userno, Integer lotteryType, Long id);
	
	public UserAchievement findByUsernoAndLottype(String userno, Integer lotteryType) ;
}
