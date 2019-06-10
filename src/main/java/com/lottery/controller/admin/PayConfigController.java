package com.lottery.controller.admin;

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
import com.lottery.core.dao.PayPropertyDAO;
import com.lottery.core.domain.PayProperty;
import com.lottery.core.service.PayPropertyService;

@Controller
@RequestMapping("/payConfig")
public class PayConfigController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PayPropertyService payConfigService;
	@Autowired
	private PayPropertyDAO payConfigDao;
	
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<PayProperty> list(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<PayProperty> page = new AdminPage<PayProperty>(startLine, endLine, "");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		payConfigDao.findPageByCondition(conditionMap, page);
		return page;
	}

	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(@RequestParam String body) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			PayProperty payConfig = JsonUtil.flexToObject(PayProperty.class, body);
			rd.setValue(payConfigService.saveOrUpdate(payConfig));
		} catch (Exception e) {
			logger.error("创建修改PayConfig异常", e);
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
	public @ResponseBody
	ResponseData delete(@RequestParam String strChecked) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			payConfigService.delete(strChecked);
		} catch (Exception e) {
			logger.error("payConfig delete异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
}
