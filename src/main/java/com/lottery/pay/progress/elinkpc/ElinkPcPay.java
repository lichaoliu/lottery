package com.lottery.pay.progress.elinkpc;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 银联PC
 * @author wyliuxiaoyan
 *
 */
public class ElinkPcPay {
	
	protected static Logger logger = LoggerFactory.getLogger(ElinkPcPay.class);
	
	

    /**
	 * @param channelType 渠道类型 07-互联网渠道,08-手机
	 * */
	public static Map<String, String> getReturnUrl(String partner,String channelType,String amount,String out_trade_no,String cardType,String returnUrl,String noticeUrl,String signCertPath,String signCertPwd) throws Exception{
		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		data.put("version", "5.0.0");// 版本号
		data.put("encoding", CharsetConstant.CHARSET_UTF8);
		data.put("signMethod", "01");// 签名方法 01 RSA
		data.put("txnType", "01");// 交易类型 01-消费
		data.put("txnSubType", "01");// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("bizType", "000201");// 业务类型 000201 B2C网关支付
		data.put("channelType", channelType);// 渠道类型 07-互联网渠道,08-手机
		data.put("frontUrl", returnUrl);// 商户/收单前台接收地址 选送
		data.put("backUrl",noticeUrl);// 商户/收单后台接收地址 必送
		data.put("accessType", "0");// 接入类型:商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("merId",partner);// 商户号码
		data.put("orderId",out_trade_no);// 订单号
		data.put("txnTime", DateUtil.format("yyyyMMddHHmmss", new Date()));//商户发送交易时间
		data.put("txnAmt", amount);// 交易金额
		data.put("currencyCode", "156");// 交易币种
		if(cardType!=null&&!"".equals(cardType)){
			data.put("issInsCode", cardType);
		}
		return ElinkpcUtil.signData(data,signCertPath,signCertPwd);
	}


	

	/**
	 * 订单查询
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> queryOrderInfo(String orderId,String transTime,String partner,String signCertPath,String signCertPwd) throws Exception{
		//把请求参数打包成数组
		Map<String, String> req = new HashMap<String, String>();
		req.put("version", "5.0.0");// 版本号
		req.put("encoding", CharsetConstant.CHARSET_UTF8);// 字符编码
		req.put("orderId", orderId);// 订单号
		req.put("signMethod", "01");
		req.put("txnType", "00");// 交易类型
		req.put("txnSubType", "00");
		req.put("bizType", "000000");//产品类型
		req.put("accessType", "0");//接入类型
		req.put("merId", partner);// 商户代码
		req.put("txnTime",transTime);
		Map<String, String> resData= ElinkpcUtil.signData(req,signCertPath,signCertPwd);
		return  resData;
	}
	
	


}
