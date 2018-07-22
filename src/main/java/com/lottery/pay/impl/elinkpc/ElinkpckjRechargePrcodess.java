package com.lottery.pay.impl.elinkpc;


import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.common.util.CoreHttpUtils;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.impl.AbstractRechargeProcess;
import com.lottery.pay.progress.elinkpc.ElinkPcPay;
import com.lottery.pay.progress.elinkpc.ElinkpcUtil;
import com.lottery.pay.progress.elinkpc.util.SDKUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLDecoder;

import java.util.Map;

/**
 * 银联Pc
 * @author Liuxy
 *
 */
@Service
public class ElinkpckjRechargePrcodess extends AbstractRechargeProcess {

	@Override
	public PayChannel getPayChannel() {
		return PayChannel.elinkpcpay;
	}


    /**
     * 验证
     */
	@Override
	public boolean verifySign(String notifyStr) {
		IPayConfig payConfig = getIPayConfig();
		if (payConfig == null) {

			return false;
		}
		String validateCertDir=getPath()+payConfig.getPublicCerPath();
		Map<String,String> map=getMap(notifyStr);
          if(!SDKUtil.validate(map,validateCertDir)) {
        	  logger.error("验证签名结果[失败].");
        	  return false;
          }
		return true;
	}


	@Override
	protected RechargeResponseData sign(IPayConfig payConfig,
			RechargeResponseData responseData, RechargeRequestData requestData) {
		try {
            String singcerpath=getPath()+payConfig.getPrivateCerPath();
			String returnUrl=requestData.getFrontUrl();
			if(StringUtils.isBlank(returnUrl)){
				returnUrl=payConfig.getReturnUrl();
			}
			Map<String,String>	resultMap =ElinkPcPay.getReturnUrl(payConfig.getPartner(),"07",requestData.getAmount().multiply(BigDecimal.valueOf(100)).intValue()+"",responseData.getOrderNo(),requestData.getCardType(),returnUrl,payConfig.getNoticeUrl(),singcerpath,payConfig.getPasswd());


			responseData.setSign(resultMap.toString());
			String requestHtml= ElinkpcUtil.createHtml(payConfig.getRequestUrl(),resultMap);
			responseData.setResult(requestHtml);
			return responseData;
		} catch (Exception e) {
			logger.error("拼装签名数据异常",e);
			return null;
		}
		
	}

	protected String getPath(){
		return this.getClass().getClassLoader().getResource("").getPath();
	}
	
	
	protected String getPath(String fileName,String path) {
		File f = new File(this.getClass().getResource("/").getFile());
		String str=f.getPath() +path+fileName;
		return str;
	}


	public String notify(String notifyData) {
		Map<String,String> map=getMap(notifyData);
          if(!this.verifySign(notifyData)) {
        	  logger.error("验证签名结果[失败],通知的参数为{}",notifyData);
        	  return "200";
          }
		   String out_trade_no = map.get("orderId");//订单号
		   String trade_no =  map.get("queryId");//交易号
		   String trade_status = map.get("respCode");//支付结果
		   String total_fee =  map.get("txnAmt");//交易金额
		   try {
				// 成功
				if("00".equals(trade_status)){
					this.rechargeResult(out_trade_no, trade_no, new BigDecimal(total_fee), true, "");
				}
			} catch (Exception e) {
				logger.error("充值订单{}通知异常{}",out_trade_no,e);
			}
		    return "200";
	}

	public boolean syncResponse(String requestData){
		try{
			IPayConfig payConfig = getIPayConfig();
			if (payConfig == null) {
				return false;
			}
			String validateCertDir=getPath()+payConfig.getPublicCerPath();
			Map<String,String> map= CoreHttpUtils.parseQueryString(requestData);
			String stringSign = (String)map.get("signature");
			stringSign=URLDecoder.decode(stringSign, CharsetConstant.CHARSET_UTF8);
			map.put("signature", stringSign);

			if(!SDKUtil.validate(map,validateCertDir)) {
				logger.error("验证签名结果[失败],requestdate={},signature={}",requestData,stringSign);
				return false;
			}

			String out_trade_no = map.get("orderId");//订单号
			String trade_no =  map.get("queryId");//交易号
			String trade_status = map.get("respCode");//支付结果
			String total_fee =  map.get("txnAmt");//交易金额
			String respMsg=map.get("respMsg");
			if("00".equals(trade_status)){
				this.rechargeResult(out_trade_no, trade_no, new BigDecimal(total_fee), true, respMsg);
				return true;
			}
			return true;
		}catch (Exception e){
			logger.error("银联充值同步处理错误",e);
		return false;
		}

	}


}