package com.lottery.core.dao;

import java.util.List;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.terminal.LotteryTerminalConfig;

public interface LotteryTerminalConfigDAO extends IGenericDAO<LotteryTerminalConfig, Long>{
	 /**
     * 按彩种获取
     * @param lotteryType
     * @return
     */
    public LotteryTerminalConfig get(int lotteryType);
    /**
     * 按彩种批量获取配置
     * @param lotteryTypeList
     * @return
     */
    public Map<LotteryType, LotteryTerminalConfig> mget(List<LotteryType> lotteryTypeList);

}
