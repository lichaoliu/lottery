package com.lottery.pay.progress.elinknew;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.util.DateUtil;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.progress.elinkpc.ElinkpcUtil;
public class ElinkNew {
	
	protected static Logger logger = LoggerFactory.getLogger(ElinkNew.class);
	
	public static Map<String, String> getReturnUrl(String amount,String out_trade_no,IPayConfig config,
			String signCertPath,String signCertPwd) throws Exception{	
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		data.put("encoding", CharsetConstant.CHARSET_UTF8);
		data.put("signMethod", "01");// 签名方法 01 RSA
		data.put("txnType", "01");// 交易类型 01-消费
		data.put("txnSubType", "01");// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("bizType", "000201");// 业务类型
		data.put("channelType", "08");// 渠道类型，07-PC，08-手机
		data.put("frontUrl",config.getReturnUrl());// 商户/收单前台接收地址 选送
		data.put("backUrl",config.getNoticeUrl());// 后台通知地址
		data.put("accessType", "0");// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("merId", config.getPartner());// 商户号码
		data.put("orderId", out_trade_no);// 商户订单号
		data.put("txnTime",DateUtil.format("yyyyMMddHHmmss",new Date()));// 订单发送时间
		data.put("txnAmt", amount);// 交易金额，单位分
		data.put("currencyCode", "156");// 交易币种
		data.put("reqReserved", config.getSubject());
		data.put("orderDesc", config.getDescription());// 订单描述
		Map<String, String> map= ElinkpcUtil.signData(data,signCertPath,signCertPwd);
		return map;
	}
	
	 

}
