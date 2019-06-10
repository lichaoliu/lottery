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
import com.lottery.core.dao.LotteryPhaseDrawConfigDAO;
import com.lottery.core.domain.LotteryPhaseDrawConfig;
import com.lottery.core.service.LotteryPhaseDrawConfigService;

@Controller
@RequestMapping("/phaseDrawConfig")
public class LotteryPhaseDrawConfigController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LotteryPhaseDrawConfigDAO phaseDrawConfigDAO;
	@Autowired
	private LotteryPhaseDrawConfigService configService;
	
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<LotteryPhaseDrawConfig> list(@RequestParam String condition, int start, int limit) {
		AdminPage<LotteryPhaseDrawConfig> page = new AdminPage<LotteryPhaseDrawConfig>(start, limit, " order by lotteryType asc");
		phaseDrawConfigDAO.findPageByCondition(JsonUtil.transferJson2Map(condition), page);
		return page;
	}
	
	
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(@RequestParam(value = "body", required=false) String body) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			LotteryPhaseDrawConfig config = JsonUtil.flexToObject(LotteryPhaseDrawConfig.class, body);
			configService.merge(config);
		}  catch (Exception e) {
			logger.error("创建LotteryPhaseDrawConfig异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody ResponseData delete(@RequestParam String ids) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			String[] idss = ids.split(",");
			for (String id : idss) {
				configService.delete(Long.parseLong(id));
			}
		}  catch (Exception e) {
			logger.error("删除LotteryPhaseDrawConfig异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
}
