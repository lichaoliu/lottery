package com.lottery.core.dao;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.LotteryPhaseDrawConfig;

import java.util.List;

/**
 * Created by fengqinyun on 15/4/22.
 */
public interface LotteryPhaseDrawConfigDAO extends IGenericDAO<LotteryPhaseDrawConfig, Long> {

    public List<LotteryPhaseDrawConfig>  get(int lotteryType,Long terminalId);
}
