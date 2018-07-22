package com.lottery.pay.impl.zfb;

import com.lottery.common.Constants;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.common.util.CoreHttpUtils;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.impl.AbstractRechargeProcess;
import com.lottery.pay.progress.zfb.ZfbWebPay;
import com.lottery.pay.progress.zfb.util.ZfbUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ZfbWebRechargePrcodess extends AbstractRechargeProcess {
	

	@Override
	public PayChannel getPayChannel() {
		return PayChannel.zfbwebpay;
	}


	@Override
	public boolean verifySign(String notifyStr) {
		Map<String,String> map=getMap(notifyStr);
		IPayConfig config = getIPayConfig();
		if (config == null) {
			logger.error("充值配置为空");
			return false;
		}
		String sign = ZfbUtil.buildRequestMysign(map, config.getKey(), ZfbUtil.sign_type_md5, true);
		String msign = map.get("sign");
		if (sign == null || msign == null) {
			logger.error("数据签名不能为空,本地签名{},支付宝签名{}", new Object[] { sign, msign });
			return false;
		}
		if (msign.equals(sign)) {
			return true;
		} else {
			logger.error("数据签名不一致,本地签名{},支付宝签名{}", new Object[] { sign, msign });
			return false;
		}
	}

	@Override
	protected RechargeResponseData sign(IPayConfig payConfig, RechargeResponseData responseData, RechargeRequestData requestData) {
		String result = "";
		try {
			result =
					ZfbWebPay.newSignDate(requestData.getAmount()+"",responseData.getOrderNo(),payConfig,requestData.getCardType());

			 //ZfbWebPay.Md5Return(requestData.getAmount().divide(new BigDecimal("100")) + "", responseData.getOrderNo(), payConfig);
		} catch (Exception e) {
			logger.error("拼装签名数据异常",e);
		}
		responseData.setSign(result);
		logger.error("支付宝请求返回{}",responseData.toString());
		return responseData;
	}
	
	public String notify(String notifyData) {
		Map<String, String> map=getMap(notifyData);
		String out_trade_no = map.get("out_trade_no");// 订单号
		String trade_no = map.get("trade_no");// 支付宝交易号
		String trade_status = map.get("trade_status");// 交易状态
		String total_fee = map.get("total_fee");// 交易金额
		// 成功
		if (Constants.TRADE_FINISHED.equals(trade_status)) {
			return "0";
		}
		if (Constants.TRADE_SUCCESS.equals(trade_status)) {
			this.rechargeResult(out_trade_no, trade_no, new BigDecimal(total_fee), true, "");
			return "0";
		}
		return "1";
	}
	public boolean syncResponse(String requestData){

		IPayConfig config = getIPayConfig();
		if (config == null) {
			logger.error("充值配置为空");
			return false;
		}
		Map<String,String> map= CoreHttpUtils.parseQueryString(requestData);
		String sign = ZfbUtil.buildRequestMysign(map, config.getKey(), ZfbUtil.sign_type_md5, true);
		String msign = map.get("sign");
		if (sign == null || msign == null) {
			logger.error("数据签名不能为空,本地签名{},支付宝签名{}", new Object[] { sign, msign });
			return false;
		}
		if (!msign.equals(sign)) {
			logger.error("数据签名不一致,本地签名{},支付宝签名{}", new Object[] { sign, msign });
			return false;
		}
		String out_trade_no = map.get("out_trade_no");// 订单号
		String trade_no = map.get("trade_no");// 支付宝交易号
		String trade_status = map.get("trade_status");// 交易状态
		String total_fee = map.get("total_fee");// 交易金额

		if (Constants.TRADE_SUCCESS.equals(trade_status)) {
			this.rechargeResult(out_trade_no, trade_no, new BigDecimal(total_fee).multiply(new BigDecimal(100)), true, trade_status);
			return true;
		}else{
			this.rechargeResult(out_trade_no, trade_no, new BigDecimal(total_fee), false, trade_status);
			return  false;
		}

	}
}