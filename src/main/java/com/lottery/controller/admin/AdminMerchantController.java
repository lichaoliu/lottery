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
import com.lottery.core.dao.merchant.MerchantDAO;
import com.lottery.core.dao.merchant.MerchantOrderDAO;
import com.lottery.core.domain.merchant.Merchant;
import com.lottery.core.domain.merchant.MerchantOrder;
import com.lottery.core.service.merchant.MerchantService;

@Controller
@RequestMapping("/adminMerchant")
public class AdminMerchantController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MerchantDAO merchantDAO;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private MerchantOrderDAO merchantOrderDAO;
	
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<Merchant> list(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<Merchant> page = new AdminPage<Merchant>(startLine, endLine, "");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		merchantDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(@RequestParam(value = "body", required=false) String body) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			Merchant merchant = JsonUtil.flexToObject(Merchant.class, body);
			merchantService.update(merchant);
		} catch (Exception e) {
			logger.error("merchant/update 异常{}", new String[] { e.getMessage() }, e);
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
			merchantService.delete(strChecked);
		} catch (Exception e) {
			logger.error("merchant delete", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}

	@RequestMapping(value = "/orderList")
	public @ResponseBody AdminPage<MerchantOrder> orderList(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<MerchantOrder> page = new AdminPage<MerchantOrder>(startLine, endLine, " order by createTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		merchantOrderDAO.findPageByCondition(conditionMap, page);
		return page;
	}
}
