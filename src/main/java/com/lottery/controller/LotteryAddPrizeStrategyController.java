package com.lottery.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.domain.LotteryAddPrizeStrategy;
import com.lottery.core.service.LotteryAddPrizeStrategyService;

@Controller
@RequestMapping("/lotteryAddPrizeStrategy")
public class LotteryAddPrizeStrategyController {
	
	private final Logger logger=LoggerFactory.getLogger(getClass().getName());
	
	@Autowired
	private LotteryAddPrizeStrategyService addPrizeStrategyService;
	
	
	/**
	 * 增加一条加奖策略
	 * @param lotterytype
	 * @param prizelevel
	 * @param startphase
	 * @param endphase
	 * @param addamt
	 * @return
	 */
	@RequestMapping(value = "/addPrizeStrategy", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData addPrizeStrategy(
			@RequestParam(value = "lotterytype", required = true) int lotterytype,
			@RequestParam(value = "prizelevel", required = true) String prizelevel,
			@RequestParam(value = "startphase", required = true) String startphase,
			@RequestParam(value = "endphase", required = true) String endphase,
			@RequestParam(value = "addamt", required = true) int addamt) {
		
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.value);
		try {
			addPrizeStrategyService.addAddPrizeStrategy(lotterytype, prizelevel, startphase, endphase, addamt);
		}catch(LotteryException e) {
			logger.error("增加加奖策略出错lotterytype={} prizelevel={} start={} end={} addamt={}  value={}",new Object[]{lotterytype,prizelevel,startphase,endphase,addamt,e.getErrorCode().memo});
			rd.setErrorCode(e.getErrorCode().value);
			rd.setValue(e.getMessage());
		}catch(Exception e) {
			logger.error("增加加奖策略出错",e);
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	
	
	
	@RequestMapping(value = "/closePrizeStrategy", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData closePrizeStrategy(
			@RequestParam(value = "lotterytype", required = true) int lotterytype,
			@RequestParam(value = "prizelevel", required = true) String prizelevel,
			@RequestParam(value = "startphase", required = true) String startphase,
			@RequestParam(value = "endphase", required = true) String endphase) {
		
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.value);
		try {
			addPrizeStrategyService.closeAddPrizeStrategy(lotterytype, prizelevel, startphase, endphase);
		}catch(LotteryException e) {
			logger.error("关闭加奖策略出错lotterytype={} prizelevel={} start={} end={} value={}",new Object[]{lotterytype,prizelevel,startphase,endphase,e.getErrorCode().memo});
			rd.setErrorCode(e.getErrorCode().value);
			rd.setValue(e.getErrorCode().memo);
		}catch(Exception e) {
			logger.error("关闭加奖策略出错",e);
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	
	
	@RequestMapping(value = "/openPrizeStrategy", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData openPrizeStrategy(
			@RequestParam(value = "lotterytype", required = true) int lotterytype,
			@RequestParam(value = "prizelevel", required = true) String prizelevel,
			@RequestParam(value = "startphase", required = true) String startphase,
			@RequestParam(value = "endphase", required = true) String endphase) {
		
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.value);
		try {
			addPrizeStrategyService.openAddPrizeStrategy(lotterytype, prizelevel, startphase, endphase);
		}catch(LotteryException e) {
			logger.error("开启加奖策略出错lotterytype={} prizelevel={} start={} end={} value={}",new Object[]{lotterytype,prizelevel,startphase,endphase,e.getErrorCode().memo});
			rd.setErrorCode(e.getErrorCode().value);
			rd.setValue(e.getErrorCode().memo);
		}catch(Exception e) {
			logger.error("开启加奖策略出错",e);
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	
	
	@RequestMapping(value = "/deletePrizeStrategy", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData deletePrizeStrategy(
			@RequestParam(value = "lotterytype", required = true) int lotterytype,
			@RequestParam(value = "prizelevel", required = true) String prizelevel,
			@RequestParam(value = "startphase", required = true) String startphase,
			@RequestParam(value = "endphase", required = true) String endphase) {
		
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.value);
		try {
			addPrizeStrategyService.deleteAddPrizeStrategy(lotterytype, prizelevel, startphase, endphase);
		}catch(LotteryException e) {
			logger.error("删除加奖策略出错lotterytype={} prizelevel={} start={} end={} value={}",new Object[]{lotterytype,prizelevel,startphase,endphase,e.getErrorCode().memo});
			rd.setErrorCode(e.getErrorCode().value);
			rd.setValue(e.getErrorCode().memo);
		}catch(Exception e) {
			logger.error("删除加奖策略出错",e);
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	
	@RequestMapping(value = "/findStrategys", method = RequestMethod.POST)
	public @ResponseBody
	List<LotteryAddPrizeStrategy> findStrategys(
			@RequestParam(value = "lotterytype", required = true) int lotterytype,
			@RequestParam(value = "prizelevel", required = false,defaultValue="") String prizelevel,
			@RequestParam(value = "status", required = false,defaultValue="-1") int status) {
		
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.value);
		try {
			return addPrizeStrategyService.findLotteryStrategys(lotterytype, prizelevel, status);
		}catch(Exception e) {
			logger.error("查询出错", e);
		}
		return null;
	}

}
