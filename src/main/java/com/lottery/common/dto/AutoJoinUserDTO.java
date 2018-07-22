package com.lottery.common.dto;

import com.lottery.core.domain.UserAchievement;
import com.lottery.core.domain.UserInfo;

public class AutoJoinUserDTO {
	private String count;
	
	private String starterUserno;
	
	private UserInfo starter;
	
	private UserAchievement userachievement;
	
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

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getStarterUserno() {
		return starterUserno;
	}

	public void setStarterUserno(String starterUserno) {
		this.starterUserno = starterUserno;
	}
	
}

