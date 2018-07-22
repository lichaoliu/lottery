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
import com.lottery.core.dao.TerminalPropertyDAO;
import com.lottery.core.domain.terminal.TerminalProperty;
import com.lottery.core.service.TerminalPropertyService;

@Controller
@RequestMapping("/terminalProperty")
public class TerminalPropertyController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TerminalPropertyDAO terminalPropertyDAO;
	@Autowired
	private TerminalPropertyService terminalPropertyService;
	
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<TerminalProperty> list(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<TerminalProperty> page = new AdminPage<TerminalProperty>(startLine, endLine, "");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		terminalPropertyDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(@RequestParam(value = "body", required=false) String body) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			TerminalProperty terminalProperty = JsonUtil.flexToObject(TerminalProperty.class, body);
			rd.setValue(terminalPropertyService.saveOrUpdate(terminalProperty));
		} catch (Exception e) {
			logger.error("terminalProperty异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody ResponseData delete(@RequestParam String strChecked) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			terminalPropertyService.delete(strChecked);
		} catch (Exception e) {
			logger.error("terminalProperty delete异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
}
