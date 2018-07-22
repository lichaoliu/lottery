package com.lottery.core.dao;

import java.util.List;
import java.util.Map;

import com.lottery.common.PageBean;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.common.dto.AutoJoinUserDTO;
import com.lottery.core.domain.AutoJoin;

public interface AutoJoinDao extends IGenericDAO<AutoJoin, Long>{
	
	public int findCountByStarterAndLotteryType(String userno, Integer lotteryType);

	public void findAutoJoinByPage(Integer[] lotteryType, Map<String, Object> conditionMap, PageBean<AutoJoin> page);
	
	public void findAutoJoinUser(Integer lotteryType, PageBean<AutoJoinUserDTO> page);

	public List<AutoJoin> findByLotteryTypeAndStarter(Integer lotteryType, String starter);
}
