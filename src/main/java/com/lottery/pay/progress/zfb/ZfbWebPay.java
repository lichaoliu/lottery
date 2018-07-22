package com.lottery.pay.progress.zfb;


import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.util.HTTPUtil;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.progress.zfb.util.AlipayConfig;
import com.lottery.pay.progress.zfb.util.AlipaySubmit;
import com.lottery.pay.progress.zfb.util.RSASignature;
import com.lottery.pay.progress.zfb.util.ZfbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ZfbWebPay {
protected static Logger logger = LoggerFactory.getLogger(ZfbWebPay.class);

	//默认值为：1（商品购买）
    private static String payment_type="1";

    /**
     * 
     * 
     * 返回签名
     * @throws UnsupportedEncodingException 
     */
    public static String Md5Return(String totalFee,String outTradeNo,IPayConfig  config) throws UnsupportedEncodingException{
    	Map<String,String>signMap= getSignDate(totalFee,outTradeNo,config);
    	//待请求参数数组
        Map<String, String> sPara = ZfbUtil.buildRequestPara(signMap,config.getKey(),ZfbUtil.sign_type_md5);
//        List<String> keys = new ArrayList<String>(sPara.keySet());
        //拼成form串
//        String sbHtml=ZfbUtil.getFormStr(keys,sPara,config.getRequestUrl());
        String sign=sPara.get("sign");
		return sign;
    }
	/**
	 * 准备待签名的数据
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static Map<String,String> getSignDate(String totalFee,String outTradeNo,IPayConfig  config) throws UnsupportedEncodingException {
      
        //把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", config.getPartner());
        sParaTemp.put("_input_charset", CharsetConstant.CHARSET_UTF8);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", config.getNoticeUrl());
		sParaTemp.put("return_url", config.getReturnUrl());
		sParaTemp.put("seller_email", config.getSeller());
		sParaTemp.put("out_trade_no", outTradeNo);
		sParaTemp.put("subject", config.getSubject());
		sParaTemp.put("total_fee", totalFee);
		sParaTemp.put("body", config.getDescription());
		sParaTemp.put("show_url", "");
		sParaTemp.put("anti_phishing_key", "");
		sParaTemp.put("exter_invoke_ip", "");
		sParaTemp.put("it_b_pay", "30m");
	    return sParaTemp;
	}


	public static String  newSignDate(String totalFee,String outTradeNo,IPayConfig  config,String defaultbank){
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", config.getPartner());
		sParaTemp.put("seller_email", config.getSeller());
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", config.getNoticeUrl());
		sParaTemp.put("return_url", config.getReturnUrl());
		sParaTemp.put("out_trade_no", outTradeNo);
		sParaTemp.put("subject", config.getSubject());
		sParaTemp.put("total_fee", totalFee);
		sParaTemp.put("body", config.getDescription());
	   //sParaTemp.put("paymethod", paymethod);
		sParaTemp.put("defaultbank", defaultbank);
		sParaTemp.put("show_url", config.getShowUrl());
	//	sParaTemp.put("anti_phishing_key", anti_phishing_key);
	//	sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,config.getKey(),"get", "确认");
		return sHtmlText;
	}



   /**
    * 订单查询
    * @param orderId
    * @return
 * @throws Exception 
    */
	public static String queryOrderInfo(String orderId,IPayConfig  config) throws Exception{
		//把请求参数打包成数组
		String strResult="";
		Map<String, String> sParaTemp = new HashMap<String, String>();
	    sParaTemp.put("out_trade_no", orderId);
	    sParaTemp.put("service", "single_trade_query");
	    sParaTemp.put("partner",config.getPartner());
	    sParaTemp.put("_input_charset",CharsetConstant.CHARSET_UTF8);   
        //生成签名结果
        String mysign = RSASignature.buildMysign(sParaTemp,config.getKey());
        //签名结果与签名方式加入请求提交参数组中
        sParaTemp.put("sign", mysign);
        sParaTemp.put("sign_type", ZfbUtil.sign_type_md5);
	
	    strResult=HTTPUtil.sendPostMsg(config.getSearchUrl(),sParaTemp,CharsetConstant.CHARSET_UTF8,CharsetConstant.CHARSET_UTF8,6000);
	    logger.error("支付宝web查询url:{},发送的请求是:{},返回的是:{}",new Object[]{config.getSearchUrl(),sParaTemp.toString(),strResult});
        return  strResult;
	}
	


}
