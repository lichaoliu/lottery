package com.lottery.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.lottery.core.dao.UserRebateDAO;
import com.lottery.core.dao.UserRebateInstantDAO;
import com.lottery.core.dao.UserRebateStatisticDao;
import com.lottery.core.domain.account.UserRebate;
import com.lottery.core.domain.account.UserRebateInstant;
import com.lottery.core.domain.account.UserRebateStatistic;
import com.lottery.core.service.account.UserRebateService;

@Controller
@RequestMapping("/userrebate")
public class AdminUserrebateController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserRebateService rebateService;
	@Autowired
	private UserRebateDAO userRebateDAO;
	@Autowired
	private UserRebateInstantDAO instantDAO;
	@Autowired
	private UserRebateStatisticDao statisticDao;
	
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<UserRebate> list(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<UserRebate> page = new AdminPage<UserRebate>(startLine, endLine, " order by createTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		userRebateDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(@RequestParam(value = "body", required=false) String body) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			UserRebate userRebate = JsonUtil.flexToObject(UserRebate.class, body);
			rd.setValue(rebateService.saveOrUpdate(userRebate));
		} catch (Exception e) {
			logger.error("创建修改 userrebate 异常",  e);
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
			rebateService.delete(strChecked);
		} catch (Exception e) {
			logger.error("rebate delete异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	
	
	@RequestMapping(value = "/instantList")
	public @ResponseBody AdminPage<UserRebateInstant> instantList(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<UserRebateInstant> page = new AdminPage<UserRebateInstant>(startLine, endLine, " order by createTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		instantDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping(value = "/statisticList")
	public @ResponseBody AdminPage<UserRebateStatistic> statisticList(@RequestParam(value = "lotteryType", required = false) Integer lotteryType,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(value = "maxResult", required = false, defaultValue = "30") int maxResult) {
		AdminPage<UserRebateStatistic> page = new AdminPage<UserRebateStatistic>(pageIndex, maxResult," order by createTime");
		try {
			String where = "";
			List<Object> param = new ArrayList<Object>();
			if(lotteryType != null){
				where += " and id.lotteryType = ?";
				param.add(lotteryType);
			}
			statisticDao.findPageByCondition(new HashMap<String, Object>(), page);
		} catch (Exception e) {
			logger.error("statisticList error", e);
		}
		return page;
	}
	
}
