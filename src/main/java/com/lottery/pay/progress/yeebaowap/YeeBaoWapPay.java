package com.lottery.pay.progress.yeebaowap;

import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.pay.IPayConfig;
import com.lottery.pay.progress.yeebao.YeeBaoPay;
import com.lottery.pay.progress.yeebaowap.util.PaymobileUtils;

public class YeeBaoWapPay {


	protected static Logger logger = LoggerFactory.getLogger(YeeBaoPay.class);
	
	 /**
	    * 订单查询
	    * @param orderId
	    * @return
	    * @throws Exception 
	    */
		public static JSONObject queryOrderInfo(String orderId,IPayConfig  config) throws Exception{
			//把请求参数打包成数组
			TreeMap<String, Object> treeMap	= new TreeMap<String, Object>();
			treeMap.put("orderid", 	orderId);
			//第一步 生成AESkey及encryptkey
			String AESKey		= PaymobileUtils.buildAESKey();
			String encryptkey	= PaymobileUtils.buildEncyptkey(AESKey,config.getPublicKey());
			//第二步 生成data
			String data			= PaymobileUtils.buildData(treeMap, AESKey,config.getSeller(),config.getPrivateKey());
			//第三步 http请求，订单查询接口的请求方式为GET
			Map<String, String> responseMap	= PaymobileUtils.httpGet(config.getSearchUrl(), config.getSeller(), data, encryptkey);
			
			//第五步 请求成功，则获取data、encryptkey，并将其解密
			String data_response					= responseMap.get("data");
			String encryptkey_response				= responseMap.get("encryptkey");
			JSONObject responseDataMap	= PaymobileUtils.decrypt(data_response, encryptkey_response,config.getPrivateKey());

	        return  responseDataMap;
		}
		
		
}
