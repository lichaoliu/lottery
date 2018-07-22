package com.lottery.core.service;

import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.LotteryPhaseDrawConfigDAO;
import com.lottery.core.dao.TerminalDAO;
import com.lottery.core.domain.LotteryPhaseDrawConfig;
import com.lottery.core.domain.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fengqinyun on 15/4/22.
 */
@Service
public class LotteryPhaseDrawConfigService {
    @Autowired
    protected LotteryPhaseDrawConfigDAO dao;
    @Autowired
    protected IdGeneratorDao idGeneratorDao;
    @Autowired
	protected TerminalDAO terminalDAO;

    @Transactional
    public void merge(LotteryPhaseDrawConfig lpdc){
    	Long id = lpdc.getId();
		Terminal terminal=terminalDAO.find(lpdc.getTerminalId());
    	if(id == null){
	    	lpdc.setId(idGeneratorDao.getLotteryPhaseDrawConfgId());
			lpdc.setTerminalName(terminal.getName());
	        dao.insert(lpdc);
    	}else{
    		LotteryPhaseDrawConfig config = dao.find(id);
    		config.setIsEnabled(lpdc.getIsEnabled());
    		config.setLotteryType(lpdc.getLotteryType());
    		config.setSyncTime(lpdc.getSyncTime());
    		config.setTerminalId(lpdc.getTerminalId());
			config.setTerminalName(terminal.getName());
			config.setIsDraw(lpdc.getIsDraw());
    		dao.merge(config);
    	}
    }
    
    @Transactional
    public void delete(Long id){
		LotteryPhaseDrawConfig config = dao.find(id);
		if(config != null){
			dao.remove(config);
		}
    }
    @Transactional
    public List<LotteryPhaseDrawConfig> get(int lotteryType,Long terminalId){
        return dao.get(lotteryType,terminalId);
    }


}
