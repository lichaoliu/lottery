package com.lottery.core.dao;

import java.util.List;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.GuanyajunRacePK;
import com.lottery.core.domain.JcGuanYaJunRace;
import com.lottery.core.domain.JclqRace;

public interface JcGuanyajunRaceDao extends IGenericDAO<JcGuanYaJunRace, GuanyajunRacePK>{

	
	public JcGuanYaJunRace getRace(int lotteryType,String phase,String matchnum);
	
	public JcGuanYaJunRace getGuanyajunResult(int lotteryType,String phase,String matchnum);
	
	public String getMinCloseAndResultMatchid(int lotteryType,String phase); 
	
	public List<JcGuanYaJunRace> getRaces(int lotteryType,String phase,String minMatchid, String maxMatchid);
}
