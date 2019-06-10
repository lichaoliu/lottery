package com.lottery.lottype.jc;

import java.util.HashMap;
import java.util.Map;

import com.lottery.core.domain.JcGuanYaJunRace;

public class JingcaiGuanyajunCanCal {

	//是否全部开奖
	private boolean canCal;
		
	//第一个没开奖的场次
	private String notOpenMatchid;
	
	
	private Map<String,JcGuanYaJunRace> result;
	
	
	public void addResult(JcGuanYaJunRace race) {
		result.put(race.getId().getMatchNum(), race);
	}


	public boolean isCanCal() {
		return canCal;
	}


	public void setCanCal(boolean canCal) {
		this.canCal = canCal;
	}


	public String getNotOpenMatchid() {
		return notOpenMatchid;
	}


	public void setNotOpenMatchid(String notOpenMatchid) {
		this.notOpenMatchid = notOpenMatchid;
	}


	public Map<String, JcGuanYaJunRace> getResult() {
		return result;
	}


	public void setResult(Map<String, JcGuanYaJunRace> result) {
		this.result = result;
	}


	public JingcaiGuanyajunCanCal() {
		this.result = new HashMap<String, JcGuanYaJunRace>();
	}
	
	
	
	
		
		
}
