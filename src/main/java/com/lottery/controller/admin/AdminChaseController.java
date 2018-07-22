package com.lottery.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.AdminPage;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.dao.LotteryChaseBuyDAO;
import com.lottery.core.dao.LotteryChaseDao;
import com.lottery.core.domain.LotteryChase;
import com.lottery.core.domain.LotteryChaseBuy;

@Controller
@RequestMapping("/adminChase")
public class AdminChaseController {
	@Autowired
	private LotteryChaseDao lotteryChaseDao;
	@Autowired
	private LotteryChaseBuyDAO lotteryChaseBuyDAO;
	
	/**
	 * 查询追号方案带分页
	 */
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<LotteryChase> list(
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<LotteryChase> page = new AdminPage<LotteryChase>(startLine, endLine, " order by createTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		lotteryChaseDao.findPageByCondition(conditionMap, page);
		return page;
	}
	
	/** 
	 * 查询追号订单带分页
	 */
	@RequestMapping(value = "/orderChaseList")
	public @ResponseBody AdminPage<LotteryChaseBuy> orderChaseList(
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<LotteryChaseBuy> page = new AdminPage<LotteryChaseBuy>(startLine, endLine, " order by createTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		lotteryChaseBuyDAO.findPageByCondition(conditionMap, page);
		return page;
	}
}
