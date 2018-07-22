package com.lottery.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.AdminPage;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.dao.AutoJoinDao;
import com.lottery.core.dao.AutoJoinDetailDao;
import com.lottery.core.domain.AutoJoin;
import com.lottery.core.domain.AutoJoinDetail;

@Controller
@RequestMapping("/adminAutojoin")
public class AdminAutojoinController {
	
	@Autowired
	private AutoJoinDao autoJoinDao;
	@Autowired
	private AutoJoinDetailDao autoJoinDetailDao;
	
	@RequestMapping(value = "/autojoins")
	public @ResponseBody AdminPage<AutoJoin> list(@RequestParam(value = "condition", required = false) String condition,
		@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
		@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<AutoJoin> page = new AdminPage<AutoJoin>(startLine, endLine, " order by updateTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		autoJoinDao.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping(value = "/autojoindetails")
	public @ResponseBody AdminPage<AutoJoinDetail> autojoindetails(
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<AutoJoinDetail> page = new AdminPage<AutoJoinDetail>(startLine, endLine, "order by createTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		autoJoinDetailDao.findPageByCondition(conditionMap, page);
		return page;
	}
}
