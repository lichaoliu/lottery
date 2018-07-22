package com.lottery.pay.impl.yeebaowap;

import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.PayStatus;
import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.core.domain.UserTransaction;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.impl.AbstractRechargeProcess;
import com.lottery.pay.progress.yeebao.util.DigestUtil;
import com.lottery.pay.progress.yeebaowap.util.PaymobileUtils;
import com.lottery.pay.progress.zfb.util.RSASignature;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 易宝
 * @author lxy
 *
 */
@Service
public class YeeBaoWapRechargePrcodess extends AbstractRechargeProcess {
	@Override
	public PayChannel getPayChannel() {
		return PayChannel.yeebaowap;
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
	protected RechargeResponseData sign(IPayConfig payConfig, RechargeResponseData responseData, RechargeRequestData requestData) {
		String payUrl= "";
		try {
			//使用TreeMap
			TreeMap<String, Object> treeMap	= new TreeMap<String, Object>();
			treeMap.put("orderid", 			responseData.getOrderNo());
			treeMap.put("productcatalog", 	"8");
			treeMap.put("productname", 		payConfig.getSubject());
			treeMap.put("identityid", 		responseData.getUserno());
			treeMap.put("userip", 			"127.0.0.0");
			treeMap.put("terminalid", 		responseData.getUserno());
			treeMap.put("userua","Mozilla/5.0"); 
			treeMap.put("transtime",(int)(System.currentTimeMillis()/1000));
			treeMap.put("amount",requestData.getAmount().multiply(new BigDecimal(100)));
			treeMap.put("identitytype", 	2);
			treeMap.put("terminaltype", 	2);
			treeMap.put("productdesc", 		payConfig.getDescription());
			treeMap.put("fcallbackurl", 	payConfig.getReturnUrl());
			treeMap.put("callbackurl", 		payConfig.getNoticeUrl());
			treeMap.put("paytypes", 		"1|2|3");
			treeMap.put("currency", 		156);
			treeMap.put("orderexpdate", 	60);
			treeMap.put("version", 0);
			treeMap.put("cardno", 			"");
			treeMap.put("idcardtype", 		"");
			treeMap.put("idcard", 			"");
			treeMap.put("owner", 			"");
			//第一步 生成AESkey及encryptkey
			String AESKey		= PaymobileUtils.buildAESKey();
			String encryptkey	= PaymobileUtils.buildEncyptkey(AESKey,payConfig.getPublicKey());
			//第二步 生成data
			String data			= PaymobileUtils.buildData(treeMap, AESKey,payConfig.getSeller(),payConfig.getPrivateKey());
			//第三步 获取商户编号及请求地址，并组装支付链接
			payUrl= payConfig.getRequestUrl() + "?merchantaccount=" + URLEncoder.encode(payConfig.getSeller(), "UTF-8")
						+ "&data=" + URLEncoder.encode(data, "UTF-8") + "&encryptkey=" + URLEncoder.encode(encryptkey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("拼装签名数据异常",e);
		}
		responseData.setResult(payUrl);
		return responseData;
	}

	
	public String notify(String notifyData) {
		   IPayConfig payConfig = getIPayConfig();
		    Map<String, String> responseMap=getMap(notifyData);
		    //第五步 请求成功，则获取data、encryptkey，并将其解密
			String data_response					= responseMap.get("data");
			String encryptkey_response				= responseMap.get("encryptkey");
			JSONObject jsObject	= PaymobileUtils.decrypt(data_response, encryptkey_response,payConfig.getPrivateKey());
			try {
				if(jsObject.containsKey("status")){
					String orderId=jsObject.getString("orderid");
					UserTransaction userTransaction = userTransactionService.get(orderId);
					if(jsObject.getInt("status")==1){//成功
						this.rechargeResult(userTransaction.getId(),jsObject.getString("yborderid"),new BigDecimal(jsObject.get("amount")+""),true,"成功");
					}else if(jsObject.getInt("status")==0){//等待支付
						 long leftTime=(new Date().getTime()-userTransaction.getCreateTime().getTime())/60000;
						 if(leftTime>30){
							userTransaction.setStatus(PayStatus.PAY_FAILED.getValue());
							rechargeResult(userTransaction.getId(),"",userTransaction.getAmount(),false,"等待支付超时");
						}	
				   }else if(jsObject.getInt("status")==2){//已撤销
					   rechargeResult(userTransaction.getId(),"",userTransaction.getAmount(),false,"已取消,充值失败");
				   }else if(jsObject.getInt("status")==3){
					   rechargeResult(userTransaction.getId(),"",userTransaction.getAmount(),false,"交易阻断,充值失败");
				   }else if(jsObject.getInt("status")==4){
					   rechargeResult(userTransaction.getId(),"",userTransaction.getAmount(),false,"充值失败");
				   }   
				}
			} catch (Exception e) {
				logger.error("易宝wap充值订单处理结果异常" + e);
				return "SUCCESS";
			}
			
			return "SUCCESS";
	}





}