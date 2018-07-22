package com.lottery.pay.impl.zfb;

import com.lottery.common.Constants;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.impl.AbstractRechargeProcess;
import com.lottery.pay.progress.zfb.ZfbWapPay;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ZfbWapRechargePrcodess extends AbstractRechargeProcess {


	@Override
	public PayChannel getPayChannel() {
		return PayChannel.zfbwappay;
	}


	

	@Override
	protected RechargeResponseData sign(IPayConfig payConfig, RechargeResponseData responseData, RechargeRequestData requestData) {
		String result = "";
		try {
			result = ZfbWapPay.getReturnUrl(responseData.getOrderNo(), requestData.getAmount() + "", payConfig);
		} catch (Exception e) {
			logger.error("拼装签名数据异常" + e.getMessage());
		}
		responseData.setResult(result);
		return responseData;
	}
	
	public String notify(String notifyData) {
		Map<String, String> map=getMap(notifyData);
		IPayConfig config = getIPayConfig();
		if (config == null) {
			logger.error("充值配置为空");
			return null;
		}
		String notify_data = map.get("notify_data").replace("*", "==");

		// XML解析数据
		Document doc_notify_data = null;
		try {
			// 解密
			String decrypt_params = ZfbWapPay.decrypt(notify_data, config.getPrivateKey());
			doc_notify_data = DocumentHelper.parseText(decrypt_params);
		} catch (Exception e) {
			logger.error("解析wap通知结果" + notify_data + "异常" + e);
		}

		if (doc_notify_data == null || doc_notify_data.selectSingleNode("//notify/out_trade_no") == null 
		|| doc_notify_data.selectSingleNode("//notify/trade_no") == null)
			return "fail";
		String out_trade_no = doc_notify_data.selectSingleNode("//notify/out_trade_no").getText();// 订单号
		String trade_no = doc_notify_data.selectSingleNode("//notify/trade_no").getText();// 支付宝交易号
		String trade_status = doc_notify_data.selectSingleNode("//notify/trade_status").getText();// 交易状态
		String total_fee = doc_notify_data.selectSingleNode("//notify/price").getText();// 交易金额
		// 成功
		if (Constants.TRADE_FINISHED.equals(trade_status) || Constants.TRADE_SUCCESS.equals(trade_status)) {
			this.rechargeResult(out_trade_no, trade_no, new BigDecimal(total_fee).multiply(new BigDecimal("100")), true, "");
			return "success";
		}
		return "fail";
	}
	

}