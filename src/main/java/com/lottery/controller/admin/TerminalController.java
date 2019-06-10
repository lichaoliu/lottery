package com.lottery.controller.admin;

import java.util.ArrayList;
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
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.dao.TerminalDAO;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.service.TerminalService;

@Controller
@RequestMapping("/terminal")
public class TerminalController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TerminalDAO terminalDAO;
	@Autowired
	private TerminalService terminalService;
	
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<Terminal> list(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<Terminal> page = new AdminPage<Terminal>(startLine, endLine, "");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		terminalDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(
			@RequestParam(value = "id", required=false) Long id,
			@RequestParam(value = "name") String name, 
			@RequestParam(value = "isEnabled") Integer isEnabled,
			@RequestParam(value = "isPaused") Integer isPaused,
			@RequestParam(value = "terminalType") Integer terminalType,
			@RequestParam(value = "isCheckEnabled") Integer isCheckEnabled,
			@RequestParam(value = "allotForbidPeriod") String allotForbidPeriod,
			@RequestParam(value = "sendForbidPeriod") String sendForbidPeriod,
			@RequestParam(value = "checkForbidPeriod") String checkForbidPeriod
			) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			rd.setValue(terminalService.saveOrUpdate(id, name, isEnabled, isPaused,isCheckEnabled,terminalType,allotForbidPeriod,sendForbidPeriod,checkForbidPeriod));
		}  catch (Exception e) {
			logger.error("创建终端异常",  e);
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
		logger.info("terminal/delete");
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			terminalService.delete(strChecked);
		} catch (Exception e) {
			logger.error("terminal delete异常",  e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	@RequestMapping("terminals")
	public @ResponseBody List<Terminal> terminals(){
		try {
			return terminalDAO.findAll();
		} catch (Exception e) {
			logger.error("终端列表查询异常",  e);
		}
		return new ArrayList<Terminal>();
	}
}
