package com.lottery.controller.admin;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.AdminPage;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.caselot.CaseLotBuyState;
import com.lottery.common.contains.lottery.caselot.CaseLotDisplayState;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.dao.LotteryCaseLotDao;
import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.handler.PrizeHandler;
import com.lottery.core.service.LotteryCaseLotService;

@Controller
@RequestMapping("/adminCaseLot")
public class AdminCaseLotController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LotteryCaseLotDao lotteryCaseLotDao;
	@Autowired
	private LotteryCaseLotService caseLotService;
	@Autowired
	private PrizeHandler prizeHandler;
	
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<LotteryCaseLot> list(
		@RequestParam(value = "condition", required = false) String condition,
		@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
		@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<LotteryCaseLot> page = new AdminPage<LotteryCaseLot>(startLine, endLine, " order by startTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		lotteryCaseLotDao.findPageByCondition(conditionMap, page);
		return page;
	}
	
	/**
	 * 合买强制撤单
	 */
	@RequestMapping(value = "/cancelCaselotNoValidate", method = RequestMethod.POST)
	public @ResponseBody ResponseData cancelCaselotNoValidate(@RequestParam("caseId") String caseId) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		logger.info("/caselot/cancelCaselotNoValidate caseId:{}", caseId);
		try {
			caseLotService.cancelCaseLot(false, null, caseId, CaseLotDisplayState.canceledBySystem, CaseLotBuyState.handRetract);
		} catch (LotteryException e) {
			logger.error("合买撤单出错", e);
			result = e.getErrorCode();
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			logger.error("合买撤单出错", e);
			rd.setValue(e.getMessage());
			result = ErrorCode.Faile;
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	/**
	 * 合买手工期结
	 */
	@RequestMapping(value = "/endCaseLot", method = RequestMethod.POST)
	public @ResponseBody ResponseData endCaseLot(@RequestParam("caseId") String caseId) {
		ResponseData rd = new ResponseData();
		try {
			caseLotService.endCaseLot(caseId);
			//useris.hemaiRebate(caseId);
			rd.setValue("合买手工期结");
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			logger.error("合买手工期结出错, errorcode:" + e.getErrorCode().value);
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			logger.error("合买手工期结出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 订单手工派奖
	 */
	@RequestMapping(value = "/encashCaseLot", method = RequestMethod.POST)
	public @ResponseBody ResponseData encashCaseLot(@RequestParam("orderid") String orderid) {
		ResponseData rd = new ResponseData();
		try {
			prizeHandler.encashProcess(orderid, true);
			rd.setValue("合买手工派奖");
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			logger.error("合买手工派奖出错, errorcode:" + e.getErrorCode().value);
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			logger.error("合买手工派奖出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	
	/**
	 * 合买置顶
	 */
	@RequestMapping(value = "/sortCaseLot", method = RequestMethod.POST)
	public @ResponseBody ResponseData sortCaseLot(@RequestParam("caseId") String caseId,
			@RequestParam("sortState") Integer sortState) {
		logger.info("/caselot/sortCaseLot caseid:{}", caseId);
		ResponseData rd = new ResponseData();
		try {
			caseLotService.sortCaseLot(caseId,sortState);
			rd.setValue("合买置顶");
			rd.setErrorCode(ErrorCode.Success.value);
		}catch (Exception e) {
			logger.error("合买置顶出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}
}
