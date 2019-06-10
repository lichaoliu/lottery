package com.lottery.core.dao;

import java.math.BigDecimal;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.UserAchievementDetail;

public interface UserAchievementDetailDao extends IGenericDAO<UserAchievementDetail, Long>{

	/**
	 * 创建战绩记录
	 * 
	 * @param userno
	 * @param lotteryType
	 * @param achievement
	 * @param achievementType
	 * @param caselotId
	 */
	public UserAchievementDetail createUserAchievementDetail(Long id, String userno, Integer lotteryType, BigDecimal achievement,
			Integer achievementType, String caselotId);
}
