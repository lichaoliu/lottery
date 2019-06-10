package com.lottery.controller.admin;

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
import com.lottery.core.dao.impl.AwardLevelDaoImpl;
import com.lottery.core.domain.AwardLevel;
import com.lottery.core.service.AwardLevelService;

@Controller
@RequestMapping("/awardLevel")
public class AwardLevelController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AwardLevelDaoImpl awardleveldao;
	@Autowired
	protected AwardLevelService awardLevelService;
	
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<AwardLevel> list(@RequestParam(value = "lotterytype", required = false) Integer lotterytype,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<AwardLevel> page = new AdminPage<AwardLevel>(startLine, endLine, " order by id.lotterytype asc");
		awardleveldao.findPageByLotterytype(lotterytype, page);
		return page;
	}
	 
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(@RequestParam String body) {
		logger.info("awardLevel/merge");
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			AwardLevel awardLevel = JsonUtil.flexToObject(AwardLevel.class, body);
			awardLevelService.merge(awardLevel);
		} catch (Exception e) {
			logger.error("awardLevel创建修改异常",  e);
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
		logger.info("awardLevel/delete");
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			awardLevelService.delete(strChecked);
		} catch (Exception e) {
			logger.error("awardLevel delete异常",  e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
}
