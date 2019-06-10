package com.lottery.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.TerminalConfigDAO;
import com.lottery.core.domain.terminal.TerminalConfig;
@Repository
public class TerminalConfigDAOImpl extends	AbstractGenericDAO<TerminalConfig, Long> implements TerminalConfigDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4239235065320154342L;

	

	@Override
	public List<TerminalConfig> getTerminalConfigListByPlayType(PlayType playType) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("playType", playType.getValue());
		return findByCondition(map);
	}

	@Override
	public List<TerminalConfig> get(
			int terminalType, int lotteryType, int playType) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("playType", playType);
		map.put("lotteryType",lotteryType);
		map.put("terminalType", terminalType);
		return findByCondition(map);
	}

	@Override
	public TerminalConfig get(int lotteryType, Long terminalId, int playType) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
    	map.put("lotteryType", lotteryType);
    	map.put("terminalId", terminalId);
    	map.put("playType", playType);
    	return findByUnique(map);
	}

	@Override
	public List<TerminalConfig> getByTerminalTypeAndLotteryType(int terminalType, int lotteryType) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("lotteryType",lotteryType);
		map.put("terminalType", terminalType);
		return findByCondition(map);
	}

	@Override
	public List<TerminalConfig> getByTerminalIdAndLotteryType(Long terminalId, int lotteryType) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("lotteryType",lotteryType);
		map.put("terminalId", terminalId);
		return findByCondition(map);
	}

}
