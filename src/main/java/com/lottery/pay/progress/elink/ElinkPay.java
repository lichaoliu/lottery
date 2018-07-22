package com.lottery.pay.progress.elink;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.UserTransaction;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.progress.elinkpc.ElinkpcUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class ElinkPay {

	protected static Logger logger = LoggerFactory.getLogger(ElinkPay.class);

	public static String getReturnUrl(String amount,String out_trade_no,IPayConfig config) throws Exception{
		Map<String, String> req = new HashMap<String, String>();
		req.put("version", "1.0.0");// 版本号
		req.put("charset", CharsetConstant.CHARSET_UTF8);
		req.put("transType", "01");// 交易类型
		req.put("merId", config.getPartner());// 商户代码
		req.put("backEndUrl", config.getNoticeUrl());// 通知URL
		req.put("frontEndUrl","");// 前台通知URL(可选)
		req.put("orderDescription",config.getDescription());//订单描述
		req.put("orderTime",  DateUtil.format("yyyyMMddHHmmss",new Date()));// 交易开始日期时间yyyyMMddHHmmss
		req.put("orderTimeout", DateUtil.format("yyyyMMddHHmmss",CoreDateUtils.getBeforeTime(new Date(),-30)));// 订单超时时间yyyyMMddHHmmss(可选)
		req.put("orderNumber", out_trade_no);//订单号
		req.put("orderAmount", amount);// 订单金额
	    req.put("orderCurrency", "156");// 交易币种(可选)
	    req.put("reqReserved", config.getSubject());// 请求方保留域(可选，用于透传商户信息)

	    // 保留域填充方法
	    Map<String, String> merReservedMap = new HashMap<String, String>();
	    merReservedMap.put("test", "test");
	    req.put("merReserved", ElinkUtil.buildReserved(merReservedMap));// 商户保留域(可选)
		String dateRes = ElinkUtil.trade(req,config.getKey());
		return dateRes;
	}

	 /**
	    * 订单查询
	    * @param orderId
	    * @return
	    * @throws Exception
	    */
		public static String queryOrderInfo(UserTransaction userTransaction,IPayConfig  config) throws Exception{
			//把请求参数打包成数组
			//请求要素
			Map<String, String> req = new HashMap<String, String>();
			req.put("version", "1.0.0");// 版本号
			req.put("charset", CharsetConstant.CHARSET_UTF8);// 字符编码
			req.put("transType", "01");// 交易类型
			req.put("merId", config.getPartner());// 商户代码
			req.put("orderTime", DateUtil.format("yyyyMMddHHmmss",userTransaction.getCreateTime()));// 交易开始日期时间
			req.put("orderNumber", userTransaction.getId());// 订单号

			String resData = ElinkUtil.trade(req,config.getKey());
	        return  resData;
		}


	 public static Map<String, String> submitData(String txnType,String txnSubType,String bizType,String channelType,String returnUrl,String noticeUrl,String partNer,String transactionId,String amount,String desc){
		 Map<String, String> data = new HashMap<String, String>();
		 // 版本号
		 data.put("version", "5.0.0");
		 // 字符集编码 默认"UTF-8"
		 data.put("encoding", "UTF-8");
		 // 签名方法 01 RSA
		 data.put("signMethod", "01");
		 // 交易类型 01-消费
		 data.put("txnType", txnType);
		 // 交易子类型 01:自助消费 02:订购 03:分期付款
		 data.put("txnSubType", txnSubType);
		 // 业务类型
		 data.put("bizType", bizType);
		 // 渠道类型，07-PC，08-手机
		 data.put("channelType", channelType);
		 // 前台通知地址 ，控件接入方式无作用
		 data.put("frontUrl", returnUrl);
		 // 后台通知地址
		 data.put("backUrl", noticeUrl);
		 // 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		 data.put("accessType", "0");
		 // 商户号码，请改成自己的商户号
		 data.put("merId", partNer);
		 // 商户订单号，8-40位数字字母
		 data.put("orderId", transactionId);
		 // 订单发送时间，取系统时间
		 data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		 // 交易金额，单位分
		 data.put("txnAmt", amount);
		 // 交易币种
		 data.put("currencyCode", "156");
		 // 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		 // data.put("reqReserved", "透传信息");
		 // 订单描述，可不上送，上送时控件中会显示该信息
		 data.put("orderDesc", desc);
		 return  data;
	 }

	public static Map<String, String> signData(Map<String,String> data,String signCertPath,String signCertPwd){
	     return ElinkpcUtil.signData(data,signCertPath,signCertPwd);

	}


}
