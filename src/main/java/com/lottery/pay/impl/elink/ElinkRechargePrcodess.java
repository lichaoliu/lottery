package com.lottery.pay.impl.elink;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.common.util.HTTPUtil;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.impl.AbstractRechargeProcess;
import com.lottery.pay.progress.elink.ElinkPay;
import com.lottery.pay.progress.elink.ElinkUtil;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 银联
 * 
 * @author lxy
 * 
 */
@Service
public class ElinkRechargePrcodess extends AbstractRechargeProcess {

	@Override
	public PayChannel getPayChannel() {
		return PayChannel.elinkpay;
	}

	
	/**
	 * 验证
	 */
	@Override
	public boolean verifySign(String notifyStr) {
		Map<String, String> map = getMap(notifyStr);
		try {

			IPayConfig payConfig = getIPayConfig();
			if (payConfig == null) {

				return false;
			}
			String respSignature = map.get("signature");
			// 除去数组中的空值和签名参数
			Map<String, String> filteredReq = ElinkUtil.paraFilter(map);
			String signature = ElinkUtil.buildSignature(filteredReq, payConfig.getKey());
			if (null != respSignature && respSignature.equals(signature)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("验证秘钥失败", e);
		}
		return false;
	}

	@Override
	protected RechargeResponseData sign(IPayConfig payConfig, RechargeResponseData responseData, RechargeRequestData requestData) {
		try {

			String result = ElinkPay.getReturnUrl(requestData.getAmount().multiply(new BigDecimal("100")) + "", responseData.getOrderNo(), payConfig);
			String respString = HTTPUtil.post(payConfig.getRequestUrl(), result, CharsetConstant.CHARSET_UTF8);
			logger.error("银联请求数据:{},银联返回{}:", result, respString);
			String code = respString.split("\\&")[0].split("\\=")[1];
			if ("00".equals(code)) {
				String tsn = respString.split("\\&")[1].split("\\=")[1];
				responseData.setResult(tsn);
			} else {
				logger.error("银联请求返回异常:" + code);
			}
		} catch (Exception e) {
			logger.error("银联拼装数据异常{}", e.getMessage());
		}

		return responseData;
	}

	public String notify(String notifyData) {
		try {
			IPayConfig payConfig = getIPayConfig();
			if (payConfig == null) {
				return null;
			}
			notifyData=notifyData.replace("{", "").replace("\"sysReserved\":[", "").replace("}","").replace("=", "\":[\"").replace("&","\"],\"");
			notifyData="{"+notifyData+"}";
			Map<String, String> map = getMap(notifyData);
//			String respSignature = map.get("signature");
//			// 除去数组中的空值和签名参数
//			Map<String, String> filteredReq = ElinkUtil.paraFilter(map);
//			String signature = ElinkUtil.buildSignature(filteredReq, payConfig.getKey());
//			if (null != respSignature && !respSignature.equals(signature)) {
//				logger.error("签名验证不通过,支付渠道签名:{},本地签名:{}", respSignature, signature);
//				return null;
//			}
			String transStatus = map.get("transStatus");// 交易结果
			String respCode = map.get("respCode");// 相应码
			String out_trade_no = map.get("orderNumber");// 商户订单号
			String trade_no = map.get("qn");// 流水号
			String total_fee = map.get("settleAmount");// 金额
			String respMsg = map.get("respMsg");// 描述
			if ("00".equals(respCode)) {
				if ("00".equals(transStatus)) {
					this.rechargeResult(out_trade_no, trade_no, new BigDecimal(total_fee), true, "");
				} else if ("03".equals(transStatus)) {
					this.rechargeResult(out_trade_no, trade_no, new BigDecimal(total_fee), false, respMsg);
					logger.error("银联订单{}交易支付失败，失败原因{}", out_trade_no, respMsg);
				}
			}
			return "success";
		} catch (Exception e) {
			logger.error("处理通知字符串:{}失败", notifyData, e);
			return "fail";
		}
	}

}