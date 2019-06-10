package com.lottery.pay.impl.sfth5;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.PayStatus;
import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.UserTransaction;
import com.lottery.core.service.UserInfoService;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.impl.AbstractRechargeProcess;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 盛付通
 * @author lxy
 *
 */
@Service
public class Sfth5RechargePrcodess extends AbstractRechargeProcess {
	@Override
	public PayChannel getPayChannel() {
		return PayChannel.sfth5pay;
	}
	@Autowired
	UserInfoService userInfoService;

	   
    /**
     * 验证
     */
	@Override
	public boolean verifySign(String notifyStr) {
		return true;
	}

	public  Map<String, String> getReturnUrl(IPayConfig payConfig,String amount,String orderNo,String userNo) {
		/**
		 * 组装请求报文
		 */
		UserInfo userInfo=userInfoService.getUserInfo(userNo);
		String requestTime=DateUtil.format("yyyyMMddHHmmss", new Date());
		String phone=StringUtils.isEmpty(userInfo.getPhoneno())?"11111111111":userInfo.getPhoneno();
        Map <String, String> data = new HashMap<String, String>();
		data.put("merchantNo", payConfig.getPartner());
		data.put("charset", CharsetConstant.CHARSET_UTF8);
		data.put("requestTime", requestTime);
		data.put("outMemberId", userInfo.getUserno());
		data.put("outMemberRegistTime", "20170407112233");
		data.put("outMemberRegistIP", "10.45.251.153");
		data.put("outMemberVerifyStatus", "1");
		data.put("outMemberName", userInfo.getUsername());
		data.put("outMemberMobile",phone);
		data.put("merchantOrderNo", orderNo);// 接入类型:商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("productName","充值");// 商户号码
		data.put("productDesc","充值");// 订单号
		data.put("currency","CNY");//商户发送交易时间
		data.put("amount", amount);// 交易金额
		data.put("pageUrl", payConfig.getReturnUrl());
		data.put("notifyUrl", payConfig.getNoticeUrl());
		data.put("userIP", "10.45.251.153");// 
		data.put("bankCardType", "");
		data.put("bankCode", "");
		data.put("exts", "");
		data.put("jsCallback", "");
		data.put("backUrl",payConfig.getReturnUrl());
		data.put("signType", "MD5");
		StringBuffer encryptCode=new StringBuffer();
		encryptCode.append(payConfig.getPartner()+"|");
		encryptCode.append("UTF-8|");
		encryptCode.append(requestTime+"|");
		encryptCode.append(userInfo.getUserno()+"|");
		encryptCode.append("20170407112233|");
		encryptCode.append("10.45.251.153|");
		encryptCode.append("1|");//商户会员是否已实名
		encryptCode.append(userInfo.getUsername()+"|");//商户会员注册姓名
		encryptCode.append(phone+"|");//商户会员注册手机号
		encryptCode.append(orderNo+"|");
		encryptCode.append("充值|");
		encryptCode.append("充值|");
		encryptCode.append("CNY|");
		encryptCode.append(amount+"|");
		encryptCode.append(payConfig.getReturnUrl()+"|");//前台通知回调地址
		encryptCode.append(payConfig.getNoticeUrl()+"|");//后台通知回调地址
		encryptCode.append("10.45.251.153|");//用户IP
		encryptCode.append("MD5|");//签名类型
		System.out.println("签名 内容 ："+encryptCode.toString());
		String signMd5Msg= MD5Util.encrypt(encryptCode.toString()+payConfig.getKey());
	    data.put("signMsg",signMd5Msg.toUpperCase());   
		return data;
	}	

	
	
	@Override
	protected RechargeResponseData sign(IPayConfig payConfig, RechargeResponseData responseData, RechargeRequestData requestData) {
		Map<String, String> map=getReturnUrl(payConfig, requestData.getAmount().toString(), responseData.getOrderNo(),requestData.getUserno());
		String returnStr=createHtml(payConfig.getRequestUrl(), map);
		responseData.setResult(returnStr);
		return responseData;
	}

	
	public static String createHtml(String action, Map<String, String> hiddens) {
		StringBuffer sf = new StringBuffer();
		sf.append("<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></head><body>");
		sf.append("<form id = \"frmBankID\" action=\"" + action
				+ "\" method='post' enctype='application/x-www-form-urlencoded' accept-charset='utf-8'>");
		if (null != hiddens && 0 != hiddens.size()) {
			Set<Entry<String, String>> set = hiddens.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, String> ey = it.next();
				String key = ey.getKey();
				String value = ey.getValue();
				sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""+key+"\" value=\""+value.trim()+"\"/>");
			}
		}
		sf.append("</form>");
		sf.append("</body>");
		sf.append("<script type=\"text/javascript\">");
		sf.append("document.all.frmBankID.submit();");
		sf.append("</script>");
		sf.append("</html>");
		return sf.toString();
	}
	
	public String notify(String notifyData) {
		logger.error("接收到的参数为{}",notifyData);
		String []array=notifyData.split("\\&");
		Map<String,String> map = new HashMap<String,String>();
		for(int i=0; i<array.length; i++) {
			if(array[i].split("\\=").length>1){
				map.put(array[i].split("\\=")[0],array[i].split("\\=")[1]);
			}
		}
		String orderId=map.get("OrderNo");
		String status=map.get("TransStatus");
		String TransNo=map.get("TransNo");
		BigDecimal amount=new BigDecimal(map.get("TransAmount")).multiply(new BigDecimal("100"));
		UserTransaction userTransaction = userTransactionService.get(orderId);
		if("01".equals(status)){//成功
			this.rechargeResult(userTransaction.getId(),TransNo,amount,true,"成功");
		}else if("00".equals(status)||"03".equals(status)){//等待支付|过期
			 long leftTime=(new Date().getTime()-userTransaction.getCreateTime().getTime())/60000;
			 if(leftTime>30){
				userTransaction.setStatus(PayStatus.PAY_FAILED.getValue());
				rechargeResult(userTransaction.getId(),"",userTransaction.getAmount(),false,"等待支付超时");
			}	
	   }else if("04".equals(status)){//已撤销
		   rechargeResult(userTransaction.getId(),"",userTransaction.getAmount(),false,"已取消,充值失败");
	   }else if("02".equals(status)){
		   rechargeResult(userTransaction.getId(),"",userTransaction.getAmount(),false,"充值失败");
	   } 
		  return "SUCCESS";
	}
	




}