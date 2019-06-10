package com.lottery.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.core.dao.LotteryCaseLotDao;
import com.lottery.core.dao.LotteryChaseDao;
import com.lottery.core.dao.LotteryPhaseDAO;
import com.lottery.core.dao.TerminalDAO;
import com.lottery.core.dao.UserTransactionDao;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.test.SpringBeanTest;

public class PhaseServiceTest extends SpringBeanTest {
	@Autowired
	LotteryPhaseDAO lotteryPhaseDAO;
	
	@Autowired
	TerminalDAO terminalDAO;
    @Test
    public void testPhase(){
    	LotteryType lottery=LotteryType.SSQ;
    	PhaseStatus status=PhaseStatus.open;
    	List<Terminal> terminals = new ArrayList<Terminal>();
    	Terminal t = new Terminal();
    	t.setAllotForbidPeriod("1");
    	t.setIsEnabled(1);
    	t.setIsPaused(1);
    	t.setName("1");
    	t.setSendForbidPeriod("1");
    	t.setTerminalType(1);
    	terminals.add(t);
    	//terminalDAO.insertBatch(terminals);
    	
    	//List<LotteryPhase> phase=ps.getByPhaseStatusOrderByTime(lottery.getValue(), status.getValue());
    	//System.out.println(phase.get(0).getLotteryType());
    }
    @Autowired
    LotteryPhaseService lotteryPhaseService;
    @Test
    public void testGet(){
    	LotteryPhase nowPhase =lotteryPhaseService.getByTypeAndPhase(1001, "2014002");
    	 //LotteryPhase nowPhase = lotteryPhaseDAO.getByTypeAndPhase(1001, "2014002");
    	 System.out.println(nowPhase+"111111111111111111111");
    }
    
    @Test
    public void testMergetime(){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.MINUTE, 41);
		calendar.set(Calendar.HOUR_OF_DAY, 11);
    	//lotteryPhaseService.mergeNXKuai3PhaseEndtime(calendar.getTime(), 1004, "20140220017", 5);
    }
    
    @Autowired
    LotteryChaseDao lotteryChaseDao;
    
    @Autowired
    LotteryCaseLotDao lotteryCaseLotDao;
    
    @Autowired
    UserTransactionDao userTransactionDao;
    @Test
    public void testFind(){
    	System.out.println(lotteryChaseDao);
    	//lotteryChaseDao.findAllForPrizeend(1004,"20140208058");
    	//lotteryChaseDao.findAllChases(null,null,"20140208","20140209",3,new PageBean<LotteryChase>());
    	//lotteryDrawAmountDAO.findExcelRecord(3, "1", new PageBean<Object[]>());
    	//BigDecimal b = lotteryCaseLotDao.computeFreezeSafeAmt("1");
    }
    
}
