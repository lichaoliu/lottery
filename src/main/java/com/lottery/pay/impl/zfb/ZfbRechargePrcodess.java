package com.lottery.pay.impl.zfb;

import com.lottery.common.Constants;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.impl.AbstractRechargeProcess;
import com.lottery.pay.progress.zfb.ZfbPay;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class ZfbRechargePrcodess extends AbstractRechargeProcess {

	@Override
	public PayChannel getPayChannel() {
		return PayChannel.zfbpay;
	}



	@Override
	protected RechargeResponseData sign(IPayConfig payConfig,
			RechargeResponseData responseData, RechargeRequestData requestData) {
		String result = "";
		try {
			result = ZfbPay.Md5Return(requestData.getAmount()+"",responseData.getOrderNo(), "0", payConfig);
		} catch (UnsupportedEncodingException e) {
			logger.error("拼装签名数据异常" + e.getMessage());
		}
		responseData.setResult(result);
		return responseData;
	}
	
	public String notify(String notifyData) {
		Map<String, String> map=getMap(notifyData);
		String out_trade_no = map.get("out_trade_no");//订单号
		String trade_no =  map.get("trade_no");//支付宝交易号
		String trade_status = map.get("trade_status");//交易状态
		String total_fee =  map.get("total_fee");//交易金额
		// 成功
		if (Constants.TRADE_FINISHED.equals(trade_status)|| Constants.TRADE_SUCCESS.equals(trade_status)) {
			this.rechargeResult(out_trade_no, trade_no, new BigDecimal(total_fee).multiply(new BigDecimal("100")), true, "");
			return "success";
		}
		return "fail";
	}

}