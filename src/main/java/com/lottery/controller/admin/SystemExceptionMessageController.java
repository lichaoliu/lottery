package com.lottery.controller.admin;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.AdminPage;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.dao.SystemExceptionMessageDao;
import com.lottery.core.domain.SystemExceptionMessage;
import com.lottery.core.service.SystemExcepionMessageService;

@Controller
@RequestMapping("systemExceptionMessage")
public class SystemExceptionMessageController {

	private final Logger logger= LoggerFactory.getLogger(getClass());

	@Autowired
	SystemExcepionMessageService systemExcepionMessageService;
	
	@RequestMapping(value = "/page")
	public @ResponseBody AdminPage<SystemExceptionMessage> page(int start, int limit, String param) throws UnsupportedEncodingException{

		try{
			return systemExcepionMessageService.page(start, limit, param);
		}catch (Exception e){
			logger.error("读取出错",e);
			return null;
		}

	}
	
	@RequestMapping("doread")
	public @ResponseBody ResponseData doread(String ids){
		ResponseData rd = new ResponseData();
		try {
			rd.setValue(systemExcepionMessageService.updateRead(ids));
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.Faile.value);
			rd.setValue(e.getMessage());
		}
	    return rd;
	}
}
