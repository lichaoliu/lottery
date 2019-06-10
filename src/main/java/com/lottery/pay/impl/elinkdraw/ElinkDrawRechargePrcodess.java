package com.lottery.pay.impl.elinkdraw;


import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.core.domain.LotteryDrawAmount;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.impl.AbstractRechargeProcess;
import com.lottery.pay.progress.elinkdraw.ElinkDrawPay;
import com.lottery.pay.progress.elinknew.util.HttpClient;
import com.lottery.pay.progress.elinkpc.util.SDKUtil;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银联
 * 
 * @author lxy
 * 
 */
@Service
public class ElinkDrawRechargePrcodess extends AbstractRechargeProcess {

	@Override
	public PayChannel getPayChannel() {
		return PayChannel.elinkdraw;
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

	public RechargeResponseData getDataReturn(String batchNo,List<LotteryDrawAmount>lotteryDrawList) {
		IPayConfig payConfig = getIPayConfig();
		if (payConfig == null) {
			return null;
		}
		RechargeResponseData rechargeResponseData=null;
		try{
			batchNo=batchNo.substring(batchNo.length()-6, batchNo.length());
		    Map<String, String> resultMap=ElinkDrawPay.getReturnUrl(batchNo,lotteryDrawList,payConfig,getPath());
		    String respString=HttpClient.getResult(resultMap,payConfig.getRequestUrl());
			logger.error("银联提现请求数据:{},银联提现返回:{}",resultMap, respString);
        }catch(Exception e){
        	logger.error("拼装数据请求银联异常",e);
			return null;
        }
        return rechargeResponseData;
	}

	
//	public  RechargeResponseData PostMap(RechargeRequestData requestData,IPayConfig payConfig) {
//		
//		try {
//			String singcerpath=getPath()+payConfig.getPrivateCerPath();
//			Map<String,String>	resultMap =ElinkDrawPay.getReturnUrl(payConfig.getPartner(),requestData,singcerpath,payConfig.getPasswd());
//			String respString=HttpClient.getResult(resultMap,payConfig.getRequestUrl());
//			logger.error("银联提现请求数据:{},银联控件新版返回:{}",resultMap, respString);
//			Map<String,String> map=getResultMap(respString);
//			String respCode=map.get("respCode");
//			String respMsg=map.get("respMsg");
//			if ("00".equals(respCode)) {
//				String tsn = map.get("tn");
//				logger.error("银联提现tsn{}",tsn);
//				
//			} else {
//				logger.error("银联请求失败:返回的code为{},msg为{}" , respCode,respMsg);
//			}
//			return null;
//		} catch (Exception e) {
//			logger.error("拼装数据请求银联异常",e);
//			return null;
//		}
//		
//	}

	private Map<String,String>getResultMap(String returnStr){
		Map<String,String>map=new HashMap<String, String>();
		String []names=returnStr.split("\\&");
		for(String name:names){
			map.put(name.split("\\=")[0],name.split("\\=")[1]);
		}
		return map;
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


	@Override
	protected RechargeResponseData sign(IPayConfig payConfig,
			RechargeResponseData responseData, RechargeRequestData requestData) {
		// TODO Auto-generated method stub
		return null;
	}
	

}