package com.lottery.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.AdminPage;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.JsonUtil;
import com.lottery.controller.admin.dto.KcMonitor;
import com.lottery.controller.admin.dto.TicketMonitor;
import com.lottery.core.dao.TicketBatchDAO;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.handler.PrizeErrorHandler;
import com.lottery.core.service.TicketService;

@Controller
@RequestMapping("/adminTicket")
public class AdminTicketController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired 
	private TicketBatchDAO ticketBatchDAO;
	@Autowired
	private PrizeErrorHandler errorHandler;
	@Autowired
	private TicketService ticketService;
	
	@RequestMapping(value = "/batchlist")
	public @ResponseBody AdminPage<TicketBatch> batchlist(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<TicketBatch> page = new AdminPage<TicketBatch>(startLine, endLine, " order by createTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		ticketBatchDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping(value = "monitor")
	public @ResponseBody ResponseData monitor(Integer[] lotteryType) {
		ResponseData rd = new ResponseData();
		rd.setErrorCode(DateUtil.format("yyyy-MM-dd HH:mm:ss", new Date()));
		try {
			List<KcMonitor> list = new ArrayList<KcMonitor>();
			for(int i=0;i<lotteryType.length;i++){
				list.addAll(getData(lotteryType[i]));
			}
			rd.setValue(list);
		} catch (Exception e) {
			rd.setValue(e.getMessage());
			logger.error("adminTicket/ticketMonitor error", e);
		}
		return rd;
	}
	
	Integer[] ts = {TicketStatus.UNALLOTTED.value,TicketStatus.UNSENT.value,TicketStatus.PRINTING.value};
	private Collection<KcMonitor> getData(Integer lotteryType){
		String lt = LotteryType.getLotteryType(lotteryType).name;
		Map<String, KcMonitor> m = new HashMap<String, KcMonitor>();
		Date dbefore20 = DateUtil.addMinites(new Date(), -10);
		List<Object[]> tickets = ticketService.getMonitorData(lotteryType, dbefore20, Arrays.asList(ts));
		for(Object[] ob : tickets){
			String phase = ob[1].toString();
			KcMonitor km = m.get(phase);
			if(km == null){
				km = new KcMonitor();
				km.setLotteryType(lotteryType);
				km.setLotname(lt);
				km.setPhase(phase);
				km.setDeadLine(ob[2].toString());
				m.put(phase, km);
			}
			Integer status = Integer.parseInt(ob[0].toString());
			if(status == TicketStatus.UNALLOTTED.value){
				km.setUnallottedTicket(ob);
			}else if(status == TicketStatus.UNSENT.value){
				km.setUnsentTicket(ob);
			}else if(status == TicketStatus.PRINTING.value){
				km.setPrintTicket(ob);
			}
		}
		return m.values();
	}
	
	@RequestMapping("getMonitorData")
	public @ResponseBody TicketMonitor getMonitorData(@RequestParam Integer lotteryType, @RequestParam String phase, @RequestParam Integer type){
		if(type == 1){
			return ticketService.getMonitorDataDetail(lotteryType, phase, TicketStatus.UNALLOTTED);
		}else if(type == 2){
			return ticketService.getMonitorDataDetail(lotteryType, phase, TicketStatus.UNSENT);
		}else if(type == 3){
			return ticketService.getMonitorDataDetail(lotteryType, phase, TicketStatus.PRINTING);
		}else{
			return null;
		}
	}
	
	@RequestMapping(value = "/ticketMonitor")
	public @ResponseBody ResponseData ticketMonitor(@RequestParam Integer ticketstatus, @RequestParam String terminalids, Integer num, Integer c) {
		ResponseData rd = new ResponseData();
		try {
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(ticketService.getMonitorData(ticketstatus, terminalids, num, c));
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.Faile.value);
			rd.setValue(e.getMessage());
			logger.error("adminTicket/ticketMonitor error", e);
		}
		return rd;
	}

	@RequestMapping(value = "/venderExchange")
	public @ResponseBody ResponseData venderExchange(@RequestParam(value ="lotteryType",required = false) Integer lotteryType,
			@RequestParam(value ="terminaId",required = false) Long terminaId,
			@RequestParam(value ="phase",required = false) String phase, @RequestParam(value ="ticketResultStatus",required = false) Integer ticketResultStatus,
			@RequestParam(value ="idlist",required = false)String idList,@RequestParam(value ="agencyExchange",required = false) Integer agencyExchange,
			@RequestParam(value="startTime",required=false) Date startTime,@RequestParam(value="endTime",required=false) Date endTime) {
		ResponseData rd = new ResponseData();
		try {
             rd.setErrorCode(ErrorCode.Success.value);
			errorHandler.ticketExchange(lotteryType, phase, ticketResultStatus, agencyExchange, terminaId, idList,startTime,endTime);
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.Faile.value);
			rd.setValue(e.getMessage());
			logger.error("adminTicket/venderExchange error", e);
		}
		return rd;
	}
	@RequestMapping(value = "/venderTicketPrizeCheck")
	public @ResponseBody ResponseData venderTicketPrizeCheck(@RequestParam(value ="lotteryType",required = false) Integer lotteryType,
			@RequestParam(value ="terminaId",required = false) Long terminaId,
			@RequestParam(value ="phase",required = false) String phase, @RequestParam(value ="ticketResultStatus",required = false) Integer ticketResultStatus,
			@RequestParam(value ="idlist",required = false)String idList, @RequestParam(value ="agencyExchange",required = false) Integer agencyExchange,
			@RequestParam(value="startTime",required=false) Date startTime, @RequestParam(value="endTime",required=false) Date endTime) {
		ResponseData rd = new ResponseData();
		try {
             rd.setErrorCode(ErrorCode.Success.value);
			errorHandler.ticketCheckPrize(lotteryType, phase, ticketResultStatus, agencyExchange, terminaId, idList,startTime,endTime);
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.Faile.value);
			rd.setValue(e.getMessage());
			logger.error("adminTicket/venderTicketPrizeCheck error", e);
		}
		return rd;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));// true:允许输入空值，false:不能为空值
	}
}
