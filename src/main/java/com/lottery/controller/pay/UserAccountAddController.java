package com.lottery.controller.pay;

import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.pay.AuditStatus;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.account.UserAccountAdd;
import com.lottery.core.service.UserInfoService;
import com.lottery.core.service.account.UserAccountAddService;
import com.lottery.core.service.bet.BetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;

@Controller
@RequestMapping("/userAccountAdd")
public class UserAccountAddController {
	
	private static Logger logger=LoggerFactory.getLogger(UserAccountAddController.class);
	@Autowired
	private UserAccountAddService userAccountAddService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	protected BetService betService;
	/**
	 * 给用户加钱接口
	 * @param userno 用户id
	 * @param amount 加款金额
	 * @param forDraw 是否可提现, 1可提现，0 不可提现
	 * @param memo 描述
	 * @param creator 创建者
	 * @param isAdd 是否加款
	 * */
	@RequestMapping(value = "/addMoney", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData addMoney(
  			@RequestParam(value = "userno", required = true) String userno,
			@RequestParam(value = "amount", required = true) String amount,
			@RequestParam(value = "forDraw", required = true,defaultValue="0") int forDraw,
			@RequestParam(value = "creator", required = true,defaultValue="0") String  creator,
			@RequestParam(value = "memo",defaultValue="0") String memo,
			@RequestParam(value = "isAdd", required = true,defaultValue="1") int isAdd) {
		ResponseData rd = new ResponseData();
		try{
			logger.error("给用户:{},变动金额:{},是否加款:{}",new Object[]{userno,amount,isAdd});
			UserInfo userinf=userInfoService.get(userno);
			if(userinf==null){
				logger.error("用户:{}不存在",userno);
				rd.setErrorCode(ErrorCode.no_exits.getValue());
			}else{
				UserAccountAdd userAccountAdd=new UserAccountAdd();
				userAccountAdd.setUserno(userinf.getUserno());
				userAccountAdd.setCreateTime(new Date());
				userAccountAdd.setUsername(userinf.getUsername());
				userAccountAdd.setAuditStatus(AuditStatus.autid_not.value);
				userAccountAdd.setAmount(new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP));
				userAccountAdd.setForDraw(forDraw);
				userAccountAdd.setCreator(creator);
				userAccountAdd.setMemo(memo);
				userAccountAdd.setIsAdd(isAdd);
				userAccountAddService.save(userAccountAdd);
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(userAccountAdd);
			}

		}catch(Exception e){
			rd.setErrorCode(ErrorCode.system_error.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	/**
	 * 用户加款审核
	 * @param id 加款id
	 * @param  auditer 审核人
	 * @param  forPass 是否通过 ,0不通过,1通过
	 * @param  errorMessage 错误 描述
	 * */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	public @ResponseBody ResponseData audit(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "auditer", required = true) String auditer,
			@RequestParam(value = "forPass", required = true,defaultValue="0") int forPass,
			@RequestParam(value = "errorMessage",required = false) String errorMessage) {
		ResponseData rd = new ResponseData();
		try{
			logger.error("{}审核加款:{}",new Object[]{auditer,id});
			boolean flag=false;
			if(forPass==YesNoStatus.yes.value){
				flag=true;
			}
			UserAccountAdd userAccountAdd=userAccountAddService.audit(id, auditer, flag, errorMessage);
            betService.transactionBet(userAccountAdd.getUserno());
			rd.setErrorCode(ErrorCode.Success.getValue());
		}catch(Exception e){
			logger.error("加款审核异常", e);
			rd.setErrorCode(ErrorCode.system_error.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 代理返点直接加款(不可提现)
	 * @param userno
	 * @param amt 金额
	 * @param detailid 详细id
	 * @return
	 */
	@RequestMapping(value = "/directAddmoney", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData directAddmoney(
			@RequestParam(value = "userno", required = true) String userno,
			@RequestParam(value = "amt", required = true) BigDecimal amt,
			@RequestParam(value = "isDraw", required = false, defaultValue="1") Integer isDraw,
			@RequestParam(value = "detailid", required = true) String detailid) {
		ResponseData rd = new ResponseData();
		try{
			logger.error("代理返点直接加款userno:{}, amt:{}, detailid:{}",new Object[]{userno, amt, detailid});
			userAccountAddService.directAddmoney(userno, amt, detailid, isDraw);
			rd.setErrorCode(ErrorCode.Success.getValue());
		}catch(Exception e){
			rd.setErrorCode(ErrorCode.system_error.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}
}
