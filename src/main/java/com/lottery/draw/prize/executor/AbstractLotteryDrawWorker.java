package com.lottery.draw.prize.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.core.service.BeidanService;
import com.lottery.core.service.JingcaiGuanjunService;
import com.lottery.core.service.JingcaiService;
import com.lottery.core.service.PrizeService;


public abstract class AbstractLotteryDrawWorker implements ILotteryDrawWorker {

	protected final Logger logger=LoggerFactory.getLogger(getClass().getName());
	
	@Autowired
	protected PrizeService prizeService;
	@Autowired
	protected BeidanService beidanService;
	@Autowired
	protected JingcaiService jingcaiService;
	
	@Autowired
	protected JingcaiGuanjunService guanjunService;

}
