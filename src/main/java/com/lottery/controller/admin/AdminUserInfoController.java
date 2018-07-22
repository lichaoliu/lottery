package com.lottery.controller.admin;

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
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.dao.UserInfoDao;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.service.UserInfoService;

@Controller
@RequestMapping("/adminUserInfo")
public class AdminUserInfoController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private UserAccountDAO userAccountlDAO;
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping(value = "/list")
	public @ResponseBody AdminPage<UserInfo> list(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<UserInfo> page = new AdminPage<UserInfo>(startLine, endLine, " order by registerTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		userInfoDao.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping(value = "/callcenterUserinfo")
	public @ResponseBody UserInfo callcenterUserinfo(@RequestParam(value = "userno", required = false) String userno,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "realName", required = false) String realName,
			@RequestParam(value = "phoneno", required = false) String phoneno) {
		UserInfo userinfo = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if(StringUtil.isNotEmpt(userno)){
				map.put("userno", userno);
			}
			if(StringUtil.isNotEmpt(username)){
				map.put("username", username);
			}
			if(StringUtil.isNotEmpt(realName)){
				map.put("realName", realName);
			}
			if(StringUtil.isNotEmpt(phoneno)){
				map.put("phoneno", phoneno);
			}
			
			if(!map.isEmpty()){
				List<UserInfo> users = userInfoDao.findByCondition(1, map);
				if(users.size() > 0){
					return users.get(0);
				}
			}
		} catch (Exception e) {
			logger.error("callcenterUserinfo出错", e);
		}
		return userinfo;
	}
	
	/**
	 * 修用户信息
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(@RequestParam(value = "body", required=false) String body) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			UserInfo userinfo = JsonUtil.flexToObject(UserInfo.class, body);
			userInfoService.mergeInfoForAdmin(userinfo);
		} catch (Exception e) {
			logger.error("修改信息异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	/**
	 * 修用户信息
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public @ResponseBody ResponseData resetPassword(@RequestParam String userno, @RequestParam String pwd) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			userInfoService.resetPassword(userno, pwd);
		} catch (Exception e) {
			logger.error("修改密码异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	@RequestMapping(value = "/accountList")
	public @ResponseBody AdminPage<UserAccount> accountList(
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderDir", required = false) String orderDir) {
		AdminPage<UserAccount> page = new AdminPage<UserAccount>(startLine, endLine, " order by "+orderBy+" " + orderDir);
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		userAccountlDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@RequestMapping("getUserno")
	public @ResponseBody String getUserno(@RequestParam(value="mobileno", required=false) String mobileno,
			@RequestParam(value="username", required=false) String username) {
		UserInfo user = null;
		try {
			if(StringUtil.isNotEmpt(mobileno)){
				user = userInfoService.getByPhoneNo(mobileno);
			}else if(StringUtil.isNotEmpt(username)){
				user = userInfoService.getByUserName(username);
			}
		}  catch (Exception e) {
			logger.error("mobileno:{},username:{} 没找到userinfo", mobileno, username);
		}
		if(user == null){
			return "";
		}else{
			return user.getUserno();
		}
	}
}
