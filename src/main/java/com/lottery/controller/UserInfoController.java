package com.lottery.controller;

import com.lottery.common.ResponseData;
import com.lottery.common.cache.CacheService;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.mail.MailTool;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.service.UserInfoService;
import com.lottery.core.service.give.UserRedPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserInfoController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserInfoService userService;

	@Autowired
	private UserRedPackageService userRedPackageService;


	/**
	 * 注册接口
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            加密后的字段
	 * @param realname
	 *            真实姓名
	 * @param idcard
	 *            身份证
	 * @param mobilephone
	 *            手机号码
	 * @param agencyno
	 *            渠道号
	 * @param  status 用户状态(YesNoStatus)
	 * */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody ResponseData Register(@RequestParam(value = "username", required = true) String username, 
			@RequestParam(value = "password", required = true) String password, 
			@RequestParam(value = "realname", required = false) String realname,
	        @RequestParam(value = "idcard", required = false) String idcard, 
	        @RequestParam(value = "mobilephone", required = true) String mobilephone,
	        @RequestParam(value = "agencyno", defaultValue = "0") String agencyno,
			@RequestParam(value = "status", defaultValue = "1")int status,
			@RequestParam(value = "ticketTypeId", defaultValue = "0") int ticketTypeId) {
		ResponseData rd = new ResponseData();
		try {
			UserInfo info = userService.register(username, password, realname, idcard, mobilephone, agencyno, ticketTypeId,status);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(info);
			userRedPackageService.updateUserno(mobilephone,info.getUserno());
		} catch (LotteryException e) {
			logger.error("[{}]注册出错", username, e);
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("[{}]注册出错", username, e);
		}
		return rd;
	}

	/**
	 * 用户名登录接口
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseData login(@RequestParam(value = "username", required = true) String userName,
                                            @RequestParam(value = "password", required = true) String password) {
		ResponseData rd = new ResponseData();

		try {

			UserInfo userInfo = userService.getByUserName(userName);
			if (userInfo == null) {
				rd.setErrorCode(ErrorCode.no_user.getValue());
			} else {
				if (password.equals(userInfo.getPasswd())) {
					rd.setErrorCode(ErrorCode.Success.getValue()); // 正常
					rd.setValue(userInfo);
					userInfo.setLastLoginTime(new Date());
					userService.update(userInfo);
				} else {
					rd.setErrorCode(ErrorCode.user_passwd_error.getValue()); // 密码错误
					logger.error("用户[{}]密码登录错误", userName);
				}
			}
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
			logger.error("[{}]登陆出错", userName, e);
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("[{}]登陆出错", userName, e);
		}
		return rd;
	}

	/**
	 * 合作渠道登录接口
	 *
	 * @param userNo
	 *            用户ID
	 * @param password
	 *            密码
	 * */
	@RequestMapping(value = "/chanel_login", method = RequestMethod.POST)
	public @ResponseBody ResponseData chanel_login(@RequestParam(value = "userno", required = true) String userNo,
                                            @RequestParam(value = "password", required = true) String password) {
		ResponseData rd = new ResponseData();

		try {

			UserInfo userInfo = userService.getUserInfo(userNo);
			if (userInfo == null) {
				rd.setErrorCode(ErrorCode.no_user.getValue());
			} else {
				if (password.equals(userInfo.getPasswd())) {
					rd.setErrorCode(ErrorCode.Success.getValue()); // 正常
					rd.setValue(userInfo);
					userInfo.setLastLoginTime(new Date());
					userService.update(userInfo);
				} else {
					rd.setErrorCode(ErrorCode.user_passwd_error.getValue()); // 密码错误
					logger.error("用户UserNo[{}]密码登录错误", userNo);
				}
			}
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
			logger.error("UserNo[{}]登陆出错", userNo, e);
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("UserNo[{}]登陆出错", userNo, e);
		}
		return rd;
	}

    /**
     * 验证码登陆需要更新最后登陆时间
     * 该接口只针对验证码登陆用
     * @param mobilephone
     *            用户名
     * */
    @RequestMapping(value = "/authCodeLoginUpdateDate", method = RequestMethod.POST)
    public @ResponseBody ResponseData authcodeUpdateLoginDate(@RequestParam(value = "mobilephone", required = true) String mobilephone) {
        ResponseData rd = new ResponseData();
        try {
            UserInfo userInfo = userService.getByPhoneNo(mobilephone);
            if (userInfo == null) {
                rd.setErrorCode(ErrorCode.no_user.getValue());
            } else {
                rd.setErrorCode(ErrorCode.Success.getValue()); // 正常
                rd.setValue(userInfo);
                userInfo.setLastLoginTime(new Date());
                userService.update(userInfo);
            }
            rd.setValue(userInfo);
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.getValue());
            logger.error("[{}]登陆出错", mobilephone, e);
        }
        return rd;
    }

	/**
	 * 注册接口
	 * 
	 * @param password
	 *            加密后的字段
	 * @param mobilephone
	 *            手机号码
	 * @param agencyno
	 *            渠道号
	 * @param terminalType
	 *            终端id
	 * @param status 状态 (YesNoStatus)
	 * */
	@RequestMapping(value = "/mobileRegister", method = RequestMethod.POST)
	public @ResponseBody ResponseData mobileRegister(@RequestParam(value = "password", required = true) String password, 
			@RequestParam(value = "mobilephone", required = true) String mobilephone, @RequestParam(value = "agencyno", defaultValue = "0") String agencyno,
	        @RequestParam(value = "terminalType", defaultValue = "0") int terminalType,@RequestParam(value = "status", defaultValue = "1") int status) {
		ResponseData rd = new ResponseData();
		try {
			UserInfo info = userService.mobilePhoneRegister(mobilephone, password, agencyno, terminalType,status);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(info);
		} catch (LotteryException e) {
			logger.error("[{}]注册出错", mobilephone, e);
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("[{}]注册出错", mobilephone, e);
		}
		return rd;
	}

	/**
	 * 手机号码登录
	 * 
	 * @param mobilephone
	 *            手机号码
	 * @param password
	 *            密码
	 * */
	@RequestMapping(value = "/mobileLogin", method = RequestMethod.POST)
	public @ResponseBody ResponseData mobileLogin(@RequestParam(value = "mobilephone", required = true) String mobilephone, @RequestParam(value = "password", required = true) String password) {
		ResponseData rd = new ResponseData();
		try {
			UserInfo userInfo = userService.getByPhoneNo(mobilephone);
			if (userInfo == null) {
				rd.setErrorCode(ErrorCode.no_user.getValue());
			} else {
				if (password.equals(userInfo.getPasswd())) {
					rd.setErrorCode(ErrorCode.Success.getValue()); // 正常
					rd.setValue(userInfo);
					userInfo.setLastLoginTime(new Date());
					userService.update(userInfo);
				} else {
					rd.setErrorCode(ErrorCode.user_passwd_error.getValue()); // 密码错误
					logger.error("手机[{}]密码登录错误", mobilephone);
				}
			}
			rd.setValue(userInfo);
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("[{}]登陆出错", mobilephone, e);
		}
		return rd;
	}

	/**
	 * 修改密码
	 * 
	 * @param oldPassword
	 *            加密的旧密码
	 * @param newPassword
	 *            加密的新密码
	 * */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public @ResponseBody ResponseData Register(@RequestParam(value = "uuid", required = true) String uuid,
                                               @RequestParam(value = "oldPassword", required = true) String oldPassword, @RequestParam(value = "newPassword", required = true) String newPassword) {
		ResponseData rd = new ResponseData();
		String userName = "";
		try {
			UserInfo info = userService.updatePassword(uuid, oldPassword, newPassword);
			userName = info.getUsername();
			String returnPw = info.getPasswd();
			if (returnPw.equals(newPassword)) {
				rd.setErrorCode(ErrorCode.Success.getValue());
			} else {
				rd.setErrorCode(ErrorCode.user_passwd_error.getValue());
			}
			rd.setValue(info);
		} catch (LotteryException e) {
			logger.error("{}修改密码失败", userName, e);
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}修改密码失败", userName, e);
		}
		return rd;
	}

	/**
	 * 修改密码
	 * 
	 * @param userno
	 *            用户名
	 * @param phoneno
	 *            新手机号码
	 * */
	@RequestMapping(value = "/modifyPhone", method = RequestMethod.POST)
	public @ResponseBody ResponseData modifyPhone(@RequestParam(value = "userno", required = true) String userno,
                                                  @RequestParam(value = "phoneno", required = true) String phoneno) {
		ResponseData rd = new ResponseData();

		try {
			UserInfo info = userService.get(userno);
			if (info == null) {
				rd.setErrorCode(ErrorCode.no_user.getValue());
			} else {
				info.setPhoneno(phoneno);
				userService.update(info);
				rd.setErrorCode(ErrorCode.Success.getValue());
			}
			rd.setValue(info);
		}catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}修改手机号码失败", userno, e);
		}
		return rd;
	}

	/**
	 * 直接修改新密码
	 * 
	 * @param userno
	 *            用户名
	 * @param newpassword
	 *            新密码
	 * */
	@RequestMapping(value = "/updateNewPasword", method = RequestMethod.POST)
	public @ResponseBody ResponseData updateNewPasword(@RequestParam(value = "userno", required = true) String userno, @RequestParam(value = "newpassword", required = true) String newpassword) {
		ResponseData rd = new ResponseData();
		try {
			UserInfo info = userService.get(userno);
			if (info == null) {
				rd.setErrorCode(ErrorCode.no_user.getValue());
			} else {
				info.setPasswd(newpassword);
				userService.update(info);
				rd.setErrorCode(ErrorCode.Success.getValue());
			}
			rd.setValue(info);
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}修改密码失败", userno, e);
		}
		return rd;
	}

	@RequestMapping(value = "/getInfo", method = RequestMethod.POST)
	public @ResponseBody ResponseData getInfo(@RequestParam(value = "userno", required = true) String uuid) {
		ResponseData rd = new ResponseData();
		String userName = "";
		try {
			UserInfo info = userService.getUserInfo(uuid);
            if (info==null){
                rd.setErrorCode(ErrorCode.no_user.getValue());
                rd.setValue(info);
            }else{
                userName = info.getUsername();
                rd.setErrorCode(ErrorCode.Success.getValue());
                rd.setValue(info);
            }

		} catch (LotteryException e) {
			logger.error("{}获取用户信息失败", userName, e);
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}获取用户信息失败", userName, e);
		}
		return rd;
	}
	
	
	@RequestMapping(value = "/getInfoByUsername", method = RequestMethod.POST)
	public @ResponseBody ResponseData getInfoByUsername(@RequestParam(value = "username", required = true) String userName) {
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.getValue());
		try {
			UserInfo userInfo = userService.getByUserName(userName);
			if (userInfo == null) {
				rd.setErrorCode(ErrorCode.no_user.getValue());
			} 
			rd.setValue(userInfo);
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
			logger.error("{}查询用户信息出错", userName, e);
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}查询用户信息出错", userName, e);
		}
		return rd;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param userno
	 *            用户名
	 * @param email
	 *            邮件
	 * @param realname
	 *            真实姓名
	 * @param alias
	 *            别名
	 * @param cardId
	 *            身份证
	 * @param sex
	 *            性别
	 * */
	@RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
	public @ResponseBody ResponseData updateInfo(@RequestParam(value = "userno", required = true) String userno, @RequestParam(value = "email", defaultValue = "") String email,

	@RequestParam(value = "sex", defaultValue = "") String sex, @RequestParam(value = "realname",required = true) String realname, @RequestParam(value = "cardId", required =true ) String cardId, @RequestParam(value = "alias", defaultValue = "") String alias) {
		ResponseData rd = new ResponseData();

		try {

			UserInfo info = userService.updateUserInfo(userno, email, sex, alias, cardId, realname);

			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(info);
		} catch (LotteryException e) {
			logger.error("{}完善个人信息失败", userno, e);
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}完善个人信息失败", userno, e);
		}
		return rd;
	}



	/**
	 * 根据手机号验证用户是否存在
	 * 
	 * @param mobilephone
	 *            手机号码
	 * 
	 * 
	 * */
	@RequestMapping(value = "/mobileExist", method = RequestMethod.POST)
	public @ResponseBody ResponseData mobileExist(@RequestParam(value = "mobilephone", required = true) String mobilephone) {
		ResponseData rd = new ResponseData();
		try {
			UserInfo userInfo = userService.getByPhoneNo(mobilephone);
			if (userInfo != null) {
				rd.setErrorCode(ErrorCode.mobile_exits.getValue());
				rd.setValue(ErrorCode.mobile_exits.memo);
			} else {
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setErrorCode(ErrorCode.Success.value);
			}

		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}登陆出错", mobilephone, e);
		}
		return rd;
	}

	/**
	 * 根据用户名验证用户是否已存在
	 * @param username 用户名
	 * */
	
	@RequestMapping(value = "/usernameExist", method = RequestMethod.POST)
	public @ResponseBody ResponseData usernameExist(@RequestParam(value = "username", required = true) String username) {
		ResponseData rd = new ResponseData();
		try {
			UserInfo userInfo = userService.getByUserName(username);
			if (userInfo != null) {
				rd.setErrorCode(ErrorCode.username_exits.getValue());
				rd.setValue(ErrorCode.username_exits.memo);
			} else {
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(ErrorCode.Success.value);
			}

		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}登陆出错", username, e);
		}
		return rd;
	}

	/**
	 * 验证用户是否存在
	 * 
	 * @param userno
	 *            用户名
	 * 
	 * 
	 * */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public @ResponseBody ResponseData get(@RequestParam(value = "userno", required = true) String userno) {
		ResponseData rd = new ResponseData();
		try {

			UserInfo userInfo = userService.get(userno);
			if (userInfo != null) {
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(userInfo);
			} else {
				rd.setErrorCode(ErrorCode.no_exits.getValue());
			}

		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}验证出错", userno, e);
		}
		return rd;
	}
	/**
	 * 用户名,手机号注册
	 * @param username 用户名
	 * @param mobilephone 手机号码
	 * @param password 密码
	 * @param agencyno 渠道号
	 * @param terminalType 指定出票中心
	 * @param status 用户状态
	 * */
	@RequestMapping(value = "/usernameMobileRegister", method = RequestMethod.POST)
	public @ResponseBody ResponseData usernameMobileRegister(@RequestParam(value = "username", required = true) String username,@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "mobilephone", required = true) String mobilephone,@RequestParam(value = "agencyno",required = false,defaultValue = "0") String agencyno,
	        @RequestParam(value = "terminalType",required=false, defaultValue = "0") int terminalType,@RequestParam(value = "status", defaultValue = "1") int status) {
		ResponseData rd = new ResponseData();
		try {
			UserInfo info=userService.usernameMobilePhoneRegister(username, mobilephone, password, agencyno, terminalType,status);
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(info);
		}catch(LotteryException e){
			ErrorCode errorCode=e.getErrorCode();
			rd.setErrorCode(errorCode.getValue());
			rd.setValue(errorCode.getMemo());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("用户名手机号注册出错",e);
		}
		return rd;
	}
	
	
	
	

}
