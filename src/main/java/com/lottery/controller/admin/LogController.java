package com.lottery.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.AdminPage;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.dao.PrizeErrorLogDAO;
import com.lottery.core.dao.TicketAllotLogDAO;
import com.lottery.core.dao.TicketBatchSendLogDAO;
import com.lottery.core.domain.PrizeErrorLog;
import com.lottery.core.domain.ticket.TicketAllotLog;
import com.lottery.core.domain.ticket.TicketBatchSendLog;

@Controller
@RequestMapping("/log")
public class LogController {
	
	@Autowired
	private TicketAllotLogDAO ticketAllotLogDAO; 
	@Autowired
	private TicketBatchSendLogDAO ticketBatchSendLogDAO; 
	@Autowired
	private PrizeErrorLogDAO prizeErrorLogDAO;
	
	@RequestMapping(value = "/ticketAllotLogs")
	public @ResponseBody AdminPage<TicketAllotLog> ticketAllotLogs(
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<TicketAllotLog> page = new AdminPage<TicketAllotLog>(startLine, endLine, " order by createTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		ticketAllotLogDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping(value = "/ticketBatchSendLogs")
	public @ResponseBody AdminPage<TicketBatchSendLog> ticketBatchSendLogs(
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<TicketBatchSendLog> page = new AdminPage<TicketBatchSendLog>(startLine, endLine, " order by createTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		ticketBatchSendLogDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping(value = "/prizeErrorLogs")
	public @ResponseBody AdminPage<PrizeErrorLog> prizeErrorLogs(
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<PrizeErrorLog> page = new AdminPage<PrizeErrorLog>(startLine, endLine, " order by createTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		prizeErrorLogDAO.findPageByCondition(conditionMap, page);
		return page;
	}
}
