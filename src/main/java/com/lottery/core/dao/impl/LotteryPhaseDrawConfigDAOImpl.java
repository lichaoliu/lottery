package com.lottery.core.dao.impl;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.LotteryPhaseDrawConfigDAO;
import com.lottery.core.domain.LotteryPhaseDrawConfig;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengqinyun on 15/4/22.
 */
@Repository
public class LotteryPhaseDrawConfigDAOImpl extends AbstractGenericDAO<LotteryPhaseDrawConfig, Long> implements LotteryPhaseDrawConfigDAO {

    @Override
    public List<LotteryPhaseDrawConfig> get(int lotteryType, Long terminalId) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("lotteryType",lotteryType);
        map.put("terminalId",terminalId);
        return findByCondition(map);
    }
}
