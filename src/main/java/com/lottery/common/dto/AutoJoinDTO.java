package com.lottery.common.dto;

import com.lottery.core.domain.AutoJoin;
import com.lottery.core.domain.UserAchievement;
import com.lottery.core.domain.UserInfo;

public class AutoJoinDTO {

	private AutoJoin autoJoin;

	private UserInfo starter;
	private UserInfo user;
	private UserAchievement userachievement;

	public AutoJoin getAutoJoin() {
		return autoJoin;
	}

	public void setAutoJoin(AutoJoin autoJoin) {
		this.autoJoin = autoJoin;
	}

	public UserInfo getStarter() {
		return starter;
	}

	public void setStarter(UserInfo starter) {
		this.starter = starter;
	}

	public UserAchievement getUserachievement() {
		return userachievement;
	}

	public void setUserachievement(UserAchievement userachievement) {
		this.userachievement = userachievement;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
	
}
