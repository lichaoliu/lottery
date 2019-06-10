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
import com.lottery.common.util.JsonUtil;
import com.lottery.core.dao.LotteryTerminalConfigDAO;
import com.lottery.core.dao.LotteryTicketConfigDAO;
import com.lottery.core.domain.terminal.LotteryTerminalConfig;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.service.LotteryTerminalConfigService;
import com.lottery.core.service.LotteryTicketConfigService;



@Controller
@RequestMapping("/lotteryTicketConfig")
public class LotteryTicketConfigController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LotteryTicketConfigDAO lotteryTicketConfigDAO;
	@Autowired
	private LotteryTicketConfigService lotterConfigService;
	@Autowired
	private LotteryTerminalConfigDAO configDAO;
	@Autowired
	private LotteryTerminalConfigService configService;
	
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<LotteryTicketConfig> list(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<LotteryTicketConfig> page = new AdminPage<LotteryTicketConfig>(startLine, endLine, " order by lotteryType asc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		lotteryTicketConfigDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(@RequestParam String body) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			LotteryTicketConfig lotteryTicketConfig = JsonUtil.flexToObject(LotteryTicketConfig.class, body);
			rd.setValue(lotterConfigService.saveOrUpdate(lotteryTicketConfig));
		} catch (Exception e) {
			logger.error("异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}

	
	@RequestMapping(value = "/lotteryTerminalConfig")
	public @ResponseBody LotteryTerminalConfig lotteryTerminalConfig(@RequestParam Integer lotteryType) {
		try {
			return configDAO.get(lotteryType);
		} catch (Exception e) {
			logger.error("lotteryTerminalConfig/list error", e);
		}
		return new LotteryTerminalConfig();
	}
	
	@RequestMapping(value = "/mergeLTC", method = RequestMethod.POST)
	public @ResponseBody ResponseData mergeLTC(@RequestParam String body) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			LotteryTerminalConfig lotteryTerminalConfig = JsonUtil.flexToObject(LotteryTerminalConfig.class, body);
			rd.setValue(configService.saveOrUpdate(lotteryTerminalConfig));
		} catch (Exception e) {
			logger.error("异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
}
