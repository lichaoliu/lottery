package com.lottery.controller.admin;

import java.math.BigDecimal;
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
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.JsonUtil;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.give.UserRechargeGiveDAO;
import com.lottery.core.domain.give.UserRechargeGive;
import com.lottery.core.service.give.UserRechargeGiveSerivce;



@Controller
@RequestMapping("/userrechargegive")
public class UserrechargegiveController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserRechargeGiveSerivce giveSerivce;
	@Autowired
	private UserRechargeGiveDAO giveDAO;
	
	@RequestMapping("/list")
	public @ResponseBody AdminPage<UserRechargeGive> list(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<UserRechargeGive> page = new AdminPage<UserRechargeGive>(startLine, endLine);
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		giveDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(String id, String finishTime, Integer status, String startTime, BigDecimal giveAmount, BigDecimal rechargeAmount, 
			Integer rechargeGiveType, Integer forLimit, Integer forScope, BigDecimal notDrawPerset, String agencyno) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			if(StringUtil.isEmpty(id)){
				giveSerivce.save(id, DateUtil.parse(finishTime), status, DateUtil.parse(startTime), giveAmount, rechargeAmount, 
					 rechargeGiveType, forLimit, forScope, notDrawPerset, agencyno);
			}else{
				giveSerivce.update(id, DateUtil.parse(finishTime), status, DateUtil.parse(startTime), giveAmount, rechargeAmount, 
						 rechargeGiveType, forLimit, forScope, notDrawPerset, agencyno);
			}
		} catch (Exception e) {
			logger.error("创建修改异常", e);
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
			giveSerivce.delete(strChecked);
		} catch (Exception e) {
			logger.error("delete异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
}
