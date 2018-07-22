package com.lottery.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.AdminPage;
import com.lottery.core.domain.ticket.TicketSplitConfig;
import com.lottery.core.service.TicketSplitConfigService;

@Controller
@RequestMapping("ticketSplitConfig")
public class TicketSplitConfigController {

	@Autowired
	TicketSplitConfigService ticketSplitConfigService;
	@RequestMapping("page")
	public @ResponseBody AdminPage<TicketSplitConfig> page(int start, int limit){
		AdminPage<TicketSplitConfig> page = new AdminPage<TicketSplitConfig>(start, limit, " order by id.lotteryType");
		Map<String, Object> map = new HashMap<String, Object>();
		ticketSplitConfigService.page(map, page);
		return page;
	}
	
	@RequestMapping("save")
	public @ResponseBody String save(Integer lotteryType, Integer playType, Integer splitType, Integer betNum, Integer betmount, Integer prizeAmount){
		ticketSplitConfigService.save(lotteryType, playType, splitType, betNum, betmount, prizeAmount);
		return "suc";
	}
	
	@RequestMapping("update")
	public @ResponseBody String update(Integer lotteryType, Integer playType, Integer splitType, Integer betNum, Integer betmount, Integer prizeAmount){
		ticketSplitConfigService.update(lotteryType, playType, splitType, betNum, betmount, prizeAmount);
		return "suc";
	}
	@RequestMapping("remove")
	public @ResponseBody String remove(Integer lotteryType, Integer playType, Integer splitType){
		ticketSplitConfigService.remove(lotteryType, playType, splitType);
		return "suc";
	}
	
}
