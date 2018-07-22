package com.lottery.controller.admin;

import com.lottery.common.ResponseData;
import com.lottery.common.cache.CacheService;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.dao.LottypeConfigDAO;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.LottypeConfig;
import com.lottery.core.handler.LotteryPhaseHandler;
import com.lottery.core.service.LottypeConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/lottypeConfig")
public class LottypeConfigController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LottypeConfigService lottypeConfigService;
	@Autowired
	private LottypeConfigDAO lottypeConfigDAO;
    @Autowired
    private LotteryPhaseHandler phaseHandler;
    @Autowired
    private CacheService cacheService;
    
    @RequestMapping(value = "/list")
	public @ResponseBody ResponseData list() {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			rd.setValue(lottypeConfigDAO.findAll());
		}  catch (Exception e) {
			logger.error("lottypeConfig/list error", e);
			result = ErrorCode.Faile;
		}
		rd.setErrorCode(result.value);
		return rd;
	}

	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(@RequestParam String body) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			LottypeConfig lottypeConfig = JsonUtil.flexToObject(LottypeConfig.class, body);
			rd.setValue(lottypeConfigService.saveOrUpdate(lottypeConfig));
			cacheService.flushAll();
		} catch (Exception e) {
			logger.error("创建lottypeConfig异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	@RequestMapping(value = "/validateConfig", method = RequestMethod.POST)
	public @ResponseBody ResponseData validateConfig(@RequestParam(value = "lotterytype", required=true) int lotteryType) {
		ResponseData rd = new ResponseData();
		try {
			rd.setErrorCode(ErrorCode.Success.value);
			LottypeConfig config=phaseHandler.getValidateConfig(lotteryType);
			rd.setValue(config);
		} catch (Exception e) {
			logger.error("查询toltypeconfig所有配置异常", e);
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}




	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody ResponseData delete(@RequestParam String strChecked) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			lottypeConfigService.delete(strChecked);
			cacheService.flushAll();
		} catch (Exception e) {
			logger.error("toltypeconfig", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
}
