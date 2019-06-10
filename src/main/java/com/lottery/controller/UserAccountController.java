package com.lottery.controller;

import com.lottery.common.ListSerializable;
import com.lottery.common.PageBean;
import com.lottery.common.ResponseData;
import com.lottery.common.cache.CacheService;
import com.lottery.common.contains.AgencyType;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.dto.ShareOrder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.JsonUtil;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.domain.give.UserAccountHandsel;
import com.lottery.core.handler.UserAccountHandler;
import com.lottery.core.service.account.UserAccountDetailService;
import com.lottery.core.service.account.UserAccountProcess;
import com.lottery.core.service.account.UserAccountService;
import com.lottery.core.service.account.UserBetShowProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 账户查询记录
 * */
@Controller
@RequestMapping("/account")
public class UserAccountController {
	private static Logger logger=LoggerFactory.getLogger(UserAccountController.class);
	@Autowired
    private UserAccountService uas;
	@Autowired
	private UserAccountDetailService accountDetailService;
	@Resource(name="xmemcachedService")
	private CacheService xmemcachedService;
	@Autowired
	protected UserAccountProcess userRedWalletProcess;
    @Autowired
	protected UserBetShowProcess userBetShowProcess;
	@Autowired
	private UserAccountHandler userAccountHandler;
	@RequestMapping(value="/get",method=RequestMethod.POST)
	public @ResponseBody ResponseData getAccount(@RequestParam( value="userno",required=true) String userno){
		ResponseData rd=new ResponseData();
		try{
			UserAccount userAccount=uas.get(userno);
			if(userAccount==null){
				rd.setErrorCode(ErrorCode.no_account.getValue());
			}else{
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(userAccount);
			}
		}catch(LotteryException e){
			rd.setErrorCode(e.getErrorCode().getValue());
		}catch(Exception e){
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}
	/**
	 * 获得账户记录
	 * @param userno
	 * @param payId
	 * @param startLine
	 * @param maxResult
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value="/getInfo",method=RequestMethod.POST)
	public @ResponseBody ResponseData getAccountInfo(@RequestParam("userno")  String userno,@RequestParam("payId") String payId,
			@RequestParam(value="startLine",required=false,defaultValue="1") int startLine,
			@RequestParam(value="maxLine",required=false,defaultValue="15") int maxResult,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "dtype",required = false,defaultValue="") String dtype){
		ResponseData rd=new ResponseData();
		try{
			if(StringUtil.isEmpty(endTime)){
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.DATE, 1);
				endTime=CoreDateUtils.formatDate(c.getTime(), CoreDateUtils.DATETIME);
			}
			if(StringUtil.isEmpty(startTime)){
				Date end=CoreDateUtils.parseDate(endTime,CoreDateUtils.DATETIME);
				Calendar c=Calendar.getInstance();
				c.setTime(end);
				c.add(Calendar.MONTH, -3);
				startTime=CoreDateUtils.formatDate(c.getTime(), CoreDateUtils.DATETIME);
			}
			if((CoreDateUtils.parseDate(startTime, CoreDateUtils.DATETIME)).after(CoreDateUtils.parseDate(endTime, CoreDateUtils.DATETIME))){
				rd.setErrorCode(ErrorCode.select_date_error.getValue());
				return rd;
			}
			PageBean<UserAccountDetail> page=new PageBean<UserAccountDetail>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxResult);
			List<UserAccountDetail> userAccount=accountDetailService.findEncashAccountDetailPage(payId, userno, startTime, endTime, page);
			if(userAccount==null){
				rd.setErrorCode(ErrorCode.no_account.getValue());
			}else{
				rd.setErrorCode(ErrorCode.Success.getValue());
			}
			page.setList(userAccount);
			rd.setValue(page);
		}catch(LotteryException e){
			rd.setErrorCode(e.getErrorCode().getValue());
		}catch(Exception e){
			rd.setErrorCode(ErrorCode.system_error.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	/***
	 * 中奖排行榜
	 * */
	@RequestMapping(value="/getWinTop",method=RequestMethod.POST)
	public @ResponseBody ResponseData getWinTop(@RequestParam( value="num",required=false,defaultValue="60") int max){
		ResponseData rd=new ResponseData();
		try{
			rd.setErrorCode(ErrorCode.Success.getValue());
			String key="useraccountWinTop";
			ListSerializable<Map<String,String>> mapsList=xmemcachedService.get(key);
			
			if(mapsList==null||mapsList.getList()==null){
				List<UserAccount> accountList=uas.getWinTop(max);
				mapsList=new ListSerializable<Map<String,String>>();
				if(accountList!=null){
					List<Map<String,String>> mapList=new ArrayList<Map<String,String>>();
					for(UserAccount userAccount:accountList){
						Map<String,String> map=new HashMap<String, String>();
						map.put("userno", userAccount.getUserno());
						map.put("username", userAccount.getUserName());
						map.put("totalprizeamt", userAccount.getTotalprizeamt().toString());
						map.put("totalbetamt", userAccount.getTotalbetamt().toString());
						mapList.add(map);
					}
				}
				if(mapsList!=null&&mapsList.getList()!=null){
					xmemcachedService.set(key, 30*6, mapsList);
				}
			}
			if(mapsList!=null&&mapsList.getList()!=null){
				rd.setValue(mapsList);
			}else{
				rd.setErrorCode(ErrorCode.no_exits.getValue());
			}
		}catch(Exception e){
			rd.setErrorCode(ErrorCode.system_error.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	/***
	 * 查询用户明细
	 * @param userno 用户编号 
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * */
	@RequestMapping(value="/getAccountDetail",method=RequestMethod.POST)
	public @ResponseBody ResponseData getAccountDetail(@RequestParam(value="userno",required=true)  String userno,
			@RequestParam(value="startLine",required=false,defaultValue="1") int startLine,
			@RequestParam(value="maxLine",required=false,defaultValue="15") int maxResult,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime){
		ResponseData rd=new ResponseData();
		try{
			PageBean<UserAccountDetail> page=new PageBean<UserAccountDetail>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxResult);
			accountDetailService.getByUserno(userno, startTime, endTime, page);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(page);
		}catch(Exception e){
			logger.error("查询用户明细出错",e);
			rd.setErrorCode(ErrorCode.system_error.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	/***
	 * 用户加款
	 * @param userno 
	 * @param payid 相关编号
	 * @param amount 金额
	 * @param memo 加钱说明
	 * @param isDraw 是否可提现  0不可提现,1可提现
	 * @param isAdd 是否加款  0扣款，1加款
	 * */
	@RequestMapping(value="/addMoney",method=RequestMethod.POST)
	public @ResponseBody ResponseData addMoney(@RequestParam( value="userno",required=true) String  userno,@RequestParam(value="payid",required=true) String  payid,
			@RequestParam(value="amount",required=true) String amount,
			@RequestParam(value="memo",required=true) String memo,
			@RequestParam(value="isDraw",required=false,defaultValue="0") int isDraw,
			@RequestParam(value="isAdd",required=false,defaultValue="1") int isAdd
			){
		ResponseData rd=new ResponseData();
		try{
			logger.error("给用户{},加款{},相关编号{},说明：{},可提现状态:{},是否加款:{}",new Object[]{userno,amount,payid,memo,isDraw,isAdd});
			boolean flag=false;
			boolean addFlag=true;
			if(isDraw==YesNoStatus.yes.getValue()){
				flag=true;
			}
			if(isAdd==YesNoStatus.no.value){
				addFlag=false;
			}
			uas.addMondy(userno, payid, new BigDecimal(amount), memo,flag,addFlag);
			rd.setErrorCode(ErrorCode.Success.getValue());
		}catch(Exception e){
			logger.error("给用户加钱出错",e);
			rd.setErrorCode(ErrorCode.system_error.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	/***
	 * 专家订阅
	 * @param userno 
	 * @param payid 订阅编号
	 * @param amount 订阅金额
	 * */
	@RequestMapping(value="/expert",method=RequestMethod.POST)
	public @ResponseBody ResponseData expert(@RequestParam( value="userno",required=true) String  userno,@RequestParam(value="payid",required=true) String  payid,
			@RequestParam(value="amount",required=true) int amount
			){
		ResponseData rd=new ResponseData();
		try{
			uas.deductMoney(userno, payid, AccountType.expert, AccountDetailType.deduct, payid, new BigDecimal(amount), "专家订阅扣款",LotteryType.ALL.value,"1",AgencyType.all.value);
			rd.setErrorCode(ErrorCode.Success.getValue());
		}catch(Exception e){
			logger.error("专家订阅扣款出错",e);
			rd.setErrorCode(ErrorCode.system_error.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	} 
	/**
	 * 晒单跟单提成
	 * **/
	@RequestMapping(value="/shareOrderCommision",method=RequestMethod.POST)
	public @ResponseBody ResponseData shareOrderCommision (@RequestParam( value="shareBody",required=true) String  shareBody
			){
		ResponseData rd=new ResponseData();
		try{
			logger.error("晒单跟单计算提成:{}",shareBody);
			ShareOrder shareOrder=JsonUtil.flexToObject(ShareOrder.class, shareBody);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(userBetShowProcess.shareOrderDeduct(shareOrder));
		}catch(Exception e){
			logger.error("晒得跟单扣款出错",e);
			rd.setErrorCode(ErrorCode.system_error.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 红包赠送
	 * @param userno 用户编号
	 * **/
	@RequestMapping(value="/redWalletGive",method=RequestMethod.POST)
	public @ResponseBody ResponseData redWalletGive (@RequestParam( value="userno",required=true)String userno,@RequestParam( value="redWalletGiveId",required=true)String redWalletGiveId,
			@RequestParam( value="amount",required=true)String amount
			){
		ResponseData rd=new ResponseData();
		try{
			userRedWalletProcess.redWalletGive(userno, redWalletGiveId,new BigDecimal(amount));
			rd.setErrorCode(ErrorCode.Success.getValue());
		}catch(Exception e){
			logger.error("用户{}使用红包出错",userno,e);
			rd.setErrorCode(ErrorCode.system_error.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	/**
	 * 代理渠道返点
	 * @param userno 用户编号
	 * **/
	@RequestMapping(value="/agencyPoinTlocation",method=RequestMethod.POST)
	public @ResponseBody ResponseData agencyPoinTlocation (@RequestParam( value="userno",required=true)String userno,@RequestParam( value="payid",required=true)String payid,
													 @RequestParam( value="amount",required=true)String amount
	){
		ResponseData rd=new ResponseData();
		try{
			userRedWalletProcess.agencyPoinTlocation(userno, payid,new BigDecimal(amount));
			rd.setErrorCode(ErrorCode.Success.getValue());
		}catch(Exception e){
			logger.error("用户{}渠道返点出错",userno,e);
			rd.setErrorCode(ErrorCode.system_error.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}


	@RequestMapping(value = "/refund", method = RequestMethod.POST)
	public
	@ResponseBody
	ResponseData refund(@RequestParam(value = "orderId", required = true) String orderId) {
		ResponseData rd = new ResponseData();
		try {
            userAccountHandler.refund(orderId);
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue("");
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}


}
