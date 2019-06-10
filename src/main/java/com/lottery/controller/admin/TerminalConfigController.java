package com.lottery.controller.admin;

import com.lottery.common.AdminPage;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.dao.TerminalConfigDAO;
import com.lottery.core.domain.terminal.TerminalConfig;
import com.lottery.core.service.TerminalConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/terminalConfig")
public class TerminalConfigController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TerminalConfigDAO terminalConfigDAO;
	@Autowired
	private TerminalConfigService terminalConfigService;
	
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<TerminalConfig> list(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<TerminalConfig> page = new AdminPage<TerminalConfig>(startLine, endLine, "");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		terminalConfigDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(@RequestParam(value = "body", required=false) String body, @RequestParam(required=false, defaultValue="") String mixContain) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			TerminalConfig terminalConfig = JsonUtil.flexToObject(TerminalConfig.class, body);
			rd.setValue(terminalConfigService.saveOrUpdate(terminalConfig, mixContain.replaceAll("@", "&")));
		} catch (Exception e) {
			logger.error("创建终端异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody ResponseData delete(@RequestParam String strChecked) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			terminalConfigService.delete(strChecked);
		} catch (Exception e) {
			logger.error("terminal delete异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
}
