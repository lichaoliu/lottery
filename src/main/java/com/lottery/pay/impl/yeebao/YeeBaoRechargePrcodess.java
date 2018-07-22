package com.lottery.pay.impl.yeebao;

import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.PayStatus;
import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.core.domain.UserTransaction;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.impl.AbstractRechargeProcess;
import com.lottery.pay.progress.yeebao.YeeBaoPay;
import com.lottery.pay.progress.yeebao.util.DigestUtil;
import com.lottery.pay.progress.zfb.util.RSASignature;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 易宝
 * @author lxy
 *
 */
@Service
public class YeeBaoRechargePrcodess extends AbstractRechargeProcess {
	@Override
	public PayChannel getPayChannel() {
		return PayChannel.yeebao;
	}



    /**
     * 验证
     */
	@Override
	public boolean verifySign(String notifyStr) {
		Map<String,String> map=getMap(notifyStr);
	    String sValue;
		try {
			 List<String> keys = new ArrayList<String>(map.keySet());
			Map<String,String>mapStr=new HashMap<String, String>();
			 for (int i = 0; i < keys.size(); i++) {
		            String key = keys.get(i);
		            String value = map.get(key);
		            if("hmac".equals(key)||"type".equals(key)){
		            	continue;
		            }
		            mapStr.put(key, value);   
			 }
			sValue = RSASignature.createValueLinkString(mapStr);
			String sNewString=DigestUtil.hmacSign(sValue.toString(), getIPayConfig().getKey());
			if (map.get("hmac").equals(sNewString)) {
				return true;
			}
		} catch (Exception e) {
			logger.error("前面验证失败",e);
		}
		return false;
	}


	@Override
	protected RechargeResponseData sign(IPayConfig payConfig,
			RechargeResponseData responseData, RechargeRequestData requestData) {
		
		try {
			responseData = YeeBaoPay.Md5Return(requestData.getAmount()+"",responseData.getOrderNo(), payConfig,requestData.getCardType(),responseData);
		} catch (UnsupportedEncodingException e) {
			logger.error("拼装签名数据异常",e);
		}
		
		return responseData;
	}

	
	public String notify(String notifyData) {
		    Map<String, String> map=getMap(notifyData);
			String out_trade_no = map.get("r6_Order");//订单号
			String trade_no =  map.get("r2_TrxId");//易宝交易号
			String trade_status = map.get("r1_Code");//支付结果
			String total_fee =  map.get("r3_Amt");//交易金额
			String noticeType=map.get("r9_BType");//通知类别
			try {
				// 成功
				if ("1".equals(trade_status)) {
					if ("1".equals(noticeType)) {//同步通知
						UserTransaction userTransaction = userTransactionService.get(out_trade_no);
						if(userTransaction!=null&&userTransaction.getStatus()==PayStatus.NOT_PAY.getValue()){
							userTransactionService.updateStatus(userTransaction.getId(),PayStatus.ALREADY_PAY.value);
						}
						return "0";
					} else if ("2".equals(noticeType)) {//异步通知
						this.rechargeResult(out_trade_no, trade_no, new BigDecimal(total_fee).multiply(new BigDecimal("100")), true, "");
						return "2";
					}
				}
			} catch (Exception e) {
				logger.error("充值订单{}通知异常",out_trade_no,e);
				return "1";
			}
			return "1";
	}






}