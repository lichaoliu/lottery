package com.lottery.controller.admin;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.ResponseData;
import com.lottery.common.contains.PrintServerConfigStatus;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.core.service.print.PrintServerConfigService;
import com.lottery.core.service.print.PrintTicketService;

@Controller
@RequestMapping("print")
public class PrintController {

	@Autowired
	private PrintServerConfigService printServerConfigService;
	@Autowired
	private PrintTicketService printTicketService;
	
	@RequestMapping("updateServerStatus")
	public @ResponseBody ResponseData updateServerStatus(String ids, Integer status){
		int i = 0;
		if(status == PrintServerConfigStatus.stop.value){
			i = printServerConfigService.updateServerStop(Arrays.asList(ids.split(",")));
		}else if(status == PrintServerConfigStatus.print.value 
				|| status == PrintServerConfigStatus.prize.value 
				|| status == PrintServerConfigStatus.free.value){
			i = printServerConfigService.updateServerStatus(Arrays.asList(ids.split(",")), status);
		} 
		ResponseData rd = new ResponseData();
		rd.setErrorCode("0");
		rd.setValue(i);
		return rd;
	}
	
	@RequestMapping("updateServerBalance")
	public @ResponseBody ResponseData updateServerBalance(String id, Double balance){
		ResponseData rd = new ResponseData();
		rd.setErrorCode("0");
		rd.setValue(printServerConfigService.updateServerBalance(id, balance));
		return rd;
	}
	
	@RequestMapping("updateReport")
	public @ResponseBody ResponseData updateReport(String ids, String beginDate, Integer reportType){
		int i = printServerConfigService.updateReport(Arrays.asList(ids.split(",")), beginDate, reportType);
		ResponseData rd = new ResponseData();
		rd.setErrorCode("0");
		rd.setValue(i);
		return rd;
	}
	
	@RequestMapping("batchprint")
	public @ResponseBody ResponseData batchprint(String province, Integer type){
		ResponseData rd = new ResponseData();
		rd.setErrorCode("0");
		rd.setValue(printServerConfigService.batchprint(province, type));
		return rd;
	}
	
	
	@RequestMapping("updatePrintTicketStatus")
	public @ResponseBody ResponseData updatePrintTicketStatus(String ids, int type){
		int i = 0;
		if(type == 1){
			i = printTicketService.updateTicketStatus(Arrays.asList(ids.split(",")), TicketStatus.UNSENT.value);
		}else if(type == 2){
			i = printTicketService.updateTicketStatus(Arrays.asList(ids.split(",")), TicketStatus.UNINIT.value);
		}else if(type == 3){
			i = printTicketService.updateTicketCancelled(Arrays.asList(ids.split(",")));
		}else if(type == 4){
			i = printTicketService.updateTicketIsPriority(Arrays.asList(ids.split(",")));
		}else if(type == 6){
			i = printTicketService.updateExchanged(Arrays.asList(ids.split(",")));
		}else if(type == 7){
			i = printTicketService.updateNotExchange(Arrays.asList(ids.split(",")));
		}
		ResponseData rd = new ResponseData();
		rd.setErrorCode("0");
		rd.setValue(i);
		return rd;
	}
	
	@RequestMapping("saveServer")
	public @ResponseBody String saveServer(String id, String shortId, String areaName, String areaId, String machineType, Integer weight, String lotteryTypes,
			Integer isBigMoney, Integer maxAmount, Integer minAmount, Long minSecodes, Long maxSeconds, Integer isDel){
		try {
			printServerConfigService.save(id, shortId, areaName, areaId, machineType, weight, lotteryTypes, isBigMoney, maxAmount, minAmount, minSecodes, maxSeconds, isDel);
			return "保存成功";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@RequestMapping("updateServer")
	public @ResponseBody String updateServer(String id, String shortId, String areaName, String areaId, String machineType, Integer weight, String lotteryTypes,
			Integer isBigMoney, Integer maxAmount, Integer minAmount, Long minSecodes, Long maxSeconds, Integer isDel){
		try {
			if("0".equals(id)){
				printServerConfigService.updateControl(shortId, areaName, areaId, machineType, weight, lotteryTypes, isBigMoney, maxAmount, minAmount, minSecodes, maxSeconds, isDel);
			}else{
				printServerConfigService.update(id, shortId, areaName, areaId, machineType, weight, lotteryTypes, isBigMoney, maxAmount, minAmount, minSecodes, maxSeconds, isDel);
			}
			return "修改成功";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	@RequestMapping("updateIsBig")
	public @ResponseBody String updateIsBig(String ids, Integer isBig){
		try {
			return ""+ printServerConfigService.updateIsBig(ids, isBig);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	@RequestMapping("minSecodes")
	public @ResponseBody String minSecodes(String ids, Long minSecodes){
		try {
			return printServerConfigService.minSecodes(ids, minSecodes) +"";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	@RequestMapping("weight")
	public @ResponseBody String weight(String ids, Integer weight){
		try {
			return ""+ printServerConfigService.weight(ids, weight);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	@RequestMapping("lotteryTypes")
	public @ResponseBody String lotteryTypes(String ids, String lotteryTypes){
		try {
			return ""+ printServerConfigService.lotteryTypes(ids, lotteryTypes);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
