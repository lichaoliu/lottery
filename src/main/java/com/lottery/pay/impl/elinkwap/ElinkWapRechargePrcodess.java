package com.lottery.pay.impl.elinkwap;

import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.impl.AbstractRechargeProcess;
import com.lottery.pay.progress.elinkpc.ElinkPcPay;
import com.lottery.pay.progress.elinkpc.ElinkpcUtil;
import com.lottery.pay.progress.elinkpc.util.SDKUtil;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 银联wap新版支付
 * @author Liuxy
 *
 */
@Service 
public class ElinkWapRechargePrcodess extends AbstractRechargeProcess {


	@Override
	public PayChannel getPayChannel() {
		return PayChannel.elinkwappay;
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
			Map<String,String>	resultMap =ElinkPcPay.getReturnUrl(payConfig.getPartner(),"08",String.valueOf(requestData.getAmount().multiply(BigDecimal.valueOf(100))),responseData.getOrderNo(),requestData.getCardType(),payConfig.getReturnUrl(),payConfig.getNoticeUrl(),singcerpath,payConfig.getPasswd());
			//创建表单
			String html = ElinkpcUtil.createHtml(payConfig.getRequestUrl(), resultMap);
			
			logger.error("银联wap封装成html数据为{}",html);
			responseData.setResult(html);
			responseData.setRequestUrl(payConfig.getRequestUrl());
			return responseData;
		} catch (Exception e) {
			logger.error("银联wap拼装数据异常",e);
			return null;
		}
		
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
				logger.error("充值订单{}通知异常",out_trade_no,e);
			}
		    return "200";
	}


}