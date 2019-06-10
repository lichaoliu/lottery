package com.lottery.controller.admin;

import java.util.List;
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
import com.lottery.common.contains.DrawOperateType;
import com.lottery.common.contains.DrawType;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.JsonUtil;
import com.lottery.controller.admin.dto.CashRecord;
import com.lottery.core.dao.LotteryDrawAmountDAO;
import com.lottery.core.dao.UserDrawBankDAO;
import com.lottery.core.domain.LotteryDrawAmount;
import com.lottery.core.domain.UserDrawBank;
import com.lottery.core.service.LotteryDrawAmountService;

@Controller
@RequestMapping("/adminDrawAmount")
public class DrawAmountController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LotteryDrawAmountDAO lotteryDrawAmountDao;
	@Autowired
	private LotteryDrawAmountService lotteryDrawAmountService;
	@Autowired
	private LotteryDrawAmountService drawAmountService;
	@Autowired
	private UserDrawBankDAO userDrawBankDAO;
	
	@RequestMapping("/list")
	public @ResponseBody AdminPage<LotteryDrawAmount> list(
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<LotteryDrawAmount> page = new AdminPage<LotteryDrawAmount>(startLine, endLine, " order by createTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		lotteryDrawAmountDao.findPageByCondition(conditionMap, page);
		return page;
	}
	
	/**
	 * 审核
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	public @ResponseBody ResponseData audit(@RequestParam String id, @RequestParam Integer type,@RequestParam String memo) {
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.value);
		try {
			drawAmountService.audit(id ,type, memo);
		} catch (LotteryException e) {
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.Faile.value);
		} catch (Exception e) {
			logger.error("adminDrawAmount audit异常", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}
	
	//支付宝银行卡提现 导出
	@RequestMapping(value = "/getHasAuditAndUpdate")
	public @ResponseBody List<LotteryDrawAmount> getHasAuditAndUpdate(@RequestParam String batchid) {
		return lotteryDrawAmountService.getHasAuditAndUpdate(batchid);
	}
	
	//查找 导出excel 记录
	@RequestMapping(value = "/findExcelRecord")
	public @ResponseBody AdminPage<CashRecord> getExcelRecord(@RequestParam Integer status,
			@RequestParam(value = "batchId", required = false) String batchId,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<CashRecord> page =new AdminPage<CashRecord>(startLine, endLine);
		lotteryDrawAmountDao.findExcelRecord(status, batchId, page);
		return page;
	}
	
	@RequestMapping("/userDrawBanks")
	public @ResponseBody AdminPage<UserDrawBank> userDrawBanks(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<UserDrawBank> page = new AdminPage<UserDrawBank>(startLine, endLine, " order by createTime desc");
		userDrawBankDAO.findPageByCondition(JsonUtil.transferJson2Map(condition), page);
		return page;
	}
}
