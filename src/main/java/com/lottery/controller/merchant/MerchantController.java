package com.lottery.controller.merchant;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.core.domain.merchant.Merchant;
import com.lottery.core.service.merchant.MerchantService;

@Controller
@RequestMapping("/merchant")
public class MerchantController {
	private Logger logger=LoggerFactory.getLogger(MerchantController.class);
	@Autowired
	private MerchantService merchantService;
	/**
	 * 增加出票商
	 * @param username 出票商名字
	 * @param key 秘钥
	 * @param realname 真实姓名
	 * @param idCard 身份证
	 * @param mobilePhone 手机号码
	 * @param ip 出票ip
	 * @param amount 信用额度
	 * */
	@RequestMapping(value="/add",method=RequestMethod.POST)
    public @ResponseBody  ResponseData add(@RequestParam(value="username",required=true) String username,
		  @RequestParam(value="key",required=true) String key,
		  @RequestParam(value="realname",required=true) String realname,
		  @RequestParam(value="idCard",required=true) String idCard,
		  @RequestParam(value="mobilePhone",required=true) String mobilePhone,
		  @RequestParam(value="ip",required=true) String ip,
		  @RequestParam(value="amount",required=true) BigDecimal amount,
		  @RequestParam(value="noticeUrl",required=false) String noticeUrl,
		  @RequestParam(value="isNotice",required=true) Integer isNotice,
		  @RequestParam(value="isIp",required=true) Integer isIp,
		  @RequestParam Long terminalId) {
		ResponseData rd = new ResponseData();
		try {
			Merchant merchant = merchantService.save(username, key, realname, idCard, mobilePhone, ip, amount, noticeUrl, isNotice, isIp, terminalId);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(merchant);
		} catch (Exception e) {
			logger.error("增加出票商失败", e);
			rd.setErrorCode(ErrorCode.system_error.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	
}
