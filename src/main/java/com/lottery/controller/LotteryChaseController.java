package com.lottery.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.PageBean;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.BetType;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.domain.LotteryChase;
import com.lottery.core.domain.LotteryChaseBuy;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.handler.LotteryChaseHandler;
import com.lottery.core.service.LotteryChaseBuyService;
import com.lottery.core.service.LotteryChaseService;
import com.lottery.core.service.bet.BetService;

/**
 * 追号
 */
@Controller
@RequestMapping("/chase")
public class LotteryChaseController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LotteryChaseService lotteryChaseService;

	@Autowired
	private LotteryChaseBuyService lotteryChaseBuyService;

    @Autowired
	private LotteryChaseHandler lotteryChaseHandler;
	@Autowired
	private BetService betService;
	/**
	 * 追号投注
	 * 
	 * @param userno
	 *            用户编号
	 * @param lotteryType
	 *            彩种
	 * @param betcode
	 *            注码
	 * @param amt
	 *            单倍金额
	 * @param chaseType
	 *            追号类型
	 * @param amount
	 *            总金额 单期
	 * @param num
	 *            追号期数
	 * @param beginPhase
	 *            开始期号
	 * @param multiple
	 *            倍数
	 * @param addition
	 *            是否追加(0否，1是)
	 * @param advancedChase
	 *            高级追号
	 * @param totalPrize
	 *            中奖总金额
	 * @param buyAgencyno
	 *            购买渠道
	 * */

	@RequestMapping(value = "/bet", method = RequestMethod.POST)
	public @ResponseBody ResponseData bet(@RequestParam(value = "userno", required = true) String userno, @RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "chaseType", required = false, defaultValue = "0") int chaseType,
			@RequestParam(value = "betcode", required = true) String betcode, @RequestParam(value = "beginPhase", required = false) String beginPhase, @RequestParam(value = "amt", required = false) int amt, @RequestParam(value = "num", required = false, defaultValue = "1") int num,
			@RequestParam(value = "multiple", required = false, defaultValue = "1") int multiple, @RequestParam(value = "advancedChase", required = false) String advancedChase, @RequestParam(value = "addition", required = false, defaultValue = "0") int addition,
			@RequestParam(value = "amount", required = false) int amount, @RequestParam(value = "totalPrize", required = false, defaultValue = "0") int totalPrize, @RequestParam(value = "buyAgencyno", required = false, defaultValue = "0") String buyAgencyno) {
		ResponseData rd = new ResponseData();
		try {
            betService.lotteryTypeValidate(lotteryType,BetType.chase.value);
			LotteryOrder lotteryOrder = lotteryChaseService.createChase(userno, lotteryType, beginPhase, betcode, chaseType, totalPrize, amt, amount, multiple, num, advancedChase, addition, buyAgencyno);

			if (lotteryOrder!=null){
				betService.sendJms(lotteryOrder);
			}
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(lotteryOrder);
		} catch(LotteryException e){
			logger.error("追号投注失败",e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			logger.error("追号投注失败", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}

	/**
	 * 追号撤单
	 *
	 * @param caseId
	 *            追号id
	 * @return
	 */
	@RequestMapping(value = "/cancelChase", method = RequestMethod.POST)
	public @ResponseBody ResponseData cancelChase(@RequestParam("chaseId") String caseId) {
		ResponseData rd = new ResponseData();
		logger.info("/chase/cancelChase chaseid:{}", caseId);
		try {
			lotteryChaseService.giveupChase(caseId,1,"用户撤销追号");
			rd.setErrorCode(ErrorCode.Success.value);
		} catch(LotteryException e){
			logger.error("追号撤单出错",e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			logger.error("追号撤单出错", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * @param chaseBuyId
	 *            具体追号
	 * @return
	 */
	@RequestMapping(value = "/cancelChaseBuy", method = RequestMethod.POST)
	public @ResponseBody ResponseData cancelChaseBuy(@RequestParam("chaseBuyId") String chaseBuyId) {
		ResponseData rd = new ResponseData();

		try {
			lotteryChaseService.giveupChaseBuy(chaseBuyId,"追号撤销");
			rd.setErrorCode(ErrorCode.Success.value);
		} catch(LotteryException e){
			logger.error("追号记录撤单出错",e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			logger.error("追号记录撤单出错", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 我的追号查询
	 * 
	 * @param userno 用户名
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startLine
	 *            开始页数
	 * @param maxLine
	 *            每页多少
	 * @return
	 */
	@RequestMapping(value = "/getChaseList")
	public @ResponseBody ResponseData getChaseList(@RequestParam(value = "userno", required = true) String userno, @RequestParam(value = "lotteryType", required = false) Integer lotteryType, @RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "state", required = false,defaultValue = "-1") int state,
			@RequestParam(value = "startLine", required = false, defaultValue = "1") int startLine, @RequestParam(value = "maxLine", required = false, defaultValue = "15") int maxLine) {
		logger.info("/chase/selectChases userno:{}", userno);
		ResponseData rd = new ResponseData();

		try {
			PageBean<LotteryChase> page = new PageBean<LotteryChase>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxLine);
			lotteryChaseService.get(userno, lotteryType, startTime, endTime,state, page);
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(page);
		} catch (Exception e) {
			logger.error("查询追号列表出错", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}

	/**
	 * 追号详情
	 * 
	 * @param chaseid
	 * @return
	 */
	@RequestMapping(value = "/getChaseDetail")
	public @ResponseBody ResponseData selectChaseDetail(@RequestParam(value = "chaseId", required = true) String chaseid) {
		ResponseData rd = new ResponseData();
		try {
			LotteryChase lChase = lotteryChaseService.getLotteryChase(chaseid);
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(lChase);
		} catch (Exception e) {
			logger.error("查询{}的追号详细", chaseid, e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}

	/**
	 * 追号订单查询
	 * 
	 * @param chaseid
	 *            追号id
	 * @param startLine
	 *            开始行数
	 * @param maxLine
	 *            获取行数量
	 * @return
	 */
	@RequestMapping(value = "/getChaseBuy")
	public @ResponseBody ResponseData selectChaseOrders(@RequestParam(value = "chaseId", required = true) String chaseid, @RequestParam(value = "startLine", required = false, defaultValue = "1") int startLine, @RequestParam(value = "maxLine", required = false, defaultValue = "10") int maxLine) {
		logger.info("/chase/selectChaseOrders chaseid:{}", chaseid);
		ResponseData rd = new ResponseData();
		try {

			PageBean<LotteryChaseBuy> page = new PageBean<LotteryChaseBuy>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxLine);
			lotteryChaseBuyService.getByChaseId(chaseid, page);
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(page);
		} catch (Exception e) {
			logger.error("查询{}的追号出错", chaseid, e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}


	/**
	 * 根据彩种,期号追号撤单
	 *
	 * @param lotteryType 期号
	 * @param  phase 期号
	 * @return
	 */
	@RequestMapping(value = "/cancelChaseByPhase", method = RequestMethod.POST)
	public @ResponseBody ResponseData cancelChaseByPhase(@RequestParam("lotteryType") int lotteryType,@RequestParam("phase") String phase) {
		ResponseData rd = new ResponseData();

		try {
			lotteryChaseHandler.canelChasle(lotteryType,phase);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (Exception e) {
			logger.error("追号撤单出错", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}


}
