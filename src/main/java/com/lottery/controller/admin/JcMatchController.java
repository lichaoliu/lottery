package com.lottery.controller.admin;

import java.util.Map;

import net.sf.json.JSONArray;

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
import com.lottery.core.dao.JclqRaceDAO;
import com.lottery.core.dao.JczqRaceDao;
import com.lottery.core.dao.ZcRaceDAO;
import com.lottery.core.domain.JclqRace;
import com.lottery.core.domain.JczqRace;
import com.lottery.core.domain.ZcRace;
import com.lottery.core.service.JclqRaceService;
import com.lottery.core.service.JczqRaceService;
import com.lottery.core.service.ZcRaceService;
import com.lottery.scheduler.fetcher.jcResult.IGetJclqResult;
import com.lottery.scheduler.fetcher.jcResult.IGetJczqResult;

@Controller
@RequestMapping("/jcMatch")
public class JcMatchController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private JczqRaceDao jczqRaceDao;
	@Autowired
	private JclqRaceDAO jclqRaceDao;
	@Autowired
	private JczqRaceService jczqRaceService;
	@Autowired
	private JclqRaceService jclqRaceService;
	@Autowired
	private ZcRaceDAO zcRaceDAO;
	@Autowired
	private ZcRaceService zcRaceService;
	@Autowired
	private Map<String, IGetJclqResult> getJclqResult;
	@Autowired
	private Map<String, IGetJczqResult> getJczqResult;
	
	@RequestMapping(value = "/zqlist")
	public @ResponseBody AdminPage<JczqRace> zqlist(@RequestParam(value = "condition", required = false) String condition, 
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine, 
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<JczqRace> page = new AdminPage<JczqRace>(startLine, endLine, " order by matchNum desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		jczqRaceDao.findPageByCondition(conditionMap, page);
		return page;
	}

	@RequestMapping(value = "/lqlist")
	public @ResponseBody AdminPage<JclqRace> lqlist(@RequestParam(value = "condition", required = false) String condition, 
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine, 
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<JclqRace> page = new AdminPage<JclqRace>(startLine, endLine, " order by matchNum desc");
		try {
			Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
			jclqRaceDao.findPageByCondition(conditionMap, page);
		} catch (Exception e) {
			logger.error("JcMatch/lqlist error", e);
		}
		return page;
	}

	@RequestMapping(value = "/mergeJclqMatch", method = RequestMethod.POST)
	public @ResponseBody ResponseData mergeJclqMatch(@RequestParam String body) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			JclqRace jclqRace = JsonUtil.fromJsonToObject(body, JclqRace.class);
			jclqRaceService.merge(jclqRace);
		} catch (Exception e) {
			logger.error("mergeJclqMatch异常",  e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}

	@RequestMapping(value = "/mergeJczqMatch", method = RequestMethod.POST)
	public @ResponseBody ResponseData mergeJczqMatch(@RequestParam String body) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			JczqRace jczqRace = JsonUtil.fromJsonToObject(body, JczqRace.class);
			jczqRaceService.merge(jczqRace);
		} catch (Exception e) {
			logger.error("mergeJczqMatch异常",  e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}

	@RequestMapping("/zclist")
	public @ResponseBody AdminPage<ZcRace> zclist(@RequestParam(value = "condition", required = false) String condition, 
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine, 
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<ZcRace> page = new AdminPage<ZcRace>(startLine, endLine, " order by createTime desc");
		try {
			Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
			zcRaceDAO.findPageByCondition(conditionMap, page);
		} catch (Exception e) {
			logger.error("jcMatch/zclist error", e);
		}
		return page;
	}

	@RequestMapping(value = "/mergeZcMatch", method = RequestMethod.POST)
	public @ResponseBody ResponseData mergeZcMatch(@RequestParam String body) {
		logger.info("jcMatch/mergeZcMatch");
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			ZcRace zcRace = JsonUtil.flexToObject(ZcRace.class, body);
			zcRaceService.saveOrUpdate(zcRace);
		} catch (Exception e) {
			logger.error("mergeZcMatch异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}

	
	/**
	 * 手动按期获取竞彩赛果
	 * @param phaseNo
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/doUpdateJcResult", method = RequestMethod.POST)
	public @ResponseBody ResponseData doUpdateJcResult(@RequestParam String phaseNo, @RequestParam String type, @RequestParam String channel) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			if ("zq".equals(type)) {
				String key = "";
				if ("zch".equals(channel)) {
					key = "jczqResultFromZch";
				} else if ("taovo".equals(channel)) {
					key = "jczqResultFromDc";
				}
				IGetJczqResult zqresult = getJczqResult.get(key);
				if(zqresult != null){
					zqresult.process(phaseNo, null);
				}
			} else if ("lq".equals(type)) {
				String key = "";
				if ("zch".equals(channel)) {
					key = "jclqResultFromZch";
				} else if ("taovo".equals(channel)) {
					key = "jclqResultFromDc";
				}
				IGetJclqResult lqresult = getJclqResult.get(key);
				if(lqresult != null){
					lqresult.process(phaseNo, null);
				}
			}
			
		} catch (Exception e) {
			logger.error("获取赛果异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}

}
