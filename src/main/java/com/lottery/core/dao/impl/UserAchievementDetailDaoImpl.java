package com.lottery.core.dao.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.UserAchievementDetailDao;
import com.lottery.core.domain.UserAchievementDetail;

@Repository
public class UserAchievementDetailDaoImpl  extends AbstractGenericDAO<UserAchievementDetail, Long> implements UserAchievementDetailDao{

	public UserAchievementDetail createUserAchievementDetail(Long id, String userno, Integer lotteryType, BigDecimal achievement,
			Integer achievementType, String caselotId) {
		UserAchievementDetail detail = new UserAchievementDetail();
		detail.setId(id);
		detail.setUserno(userno);
		detail.setLotteryType(lotteryType);
		detail.setAchievement(achievement);
		detail.setAchievementType(achievementType);
		detail.setCaselotId(caselotId);
		detail.setCreateTime(new Date());
		this.insert(detail);
		return detail;
	}
}
