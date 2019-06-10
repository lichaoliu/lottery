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
import com.lottery.core.IdGenerator;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.IdGeneratorService;

@Controller
@RequestMapping("/systemConfig")
public class SystemConfigController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IdGeneratorDao idGeneratorDao;
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<IdGenerator> list() {
		AdminPage<IdGenerator> ret = new AdminPage<IdGenerator>(0,0);
		ret.setList(idGeneratorDao.getAll());
		return ret;
	}
	
	@RequestMapping(value = "/mergeid", method = RequestMethod.POST)
	public @ResponseBody ResponseData mergeid(@RequestParam Long id,
			@RequestParam Long seqid, @RequestParam String sequeueName) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			idGeneratorService.mergeid(id, seqid, sequeueName);
		} catch (Exception e) {
			logger.error("systemConfig mergeid异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
}
