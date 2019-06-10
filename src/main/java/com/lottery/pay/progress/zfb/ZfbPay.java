package com.lottery.pay.progress.zfb;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.util.HTTPUtil;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.progress.zfb.util.RSASignature;
import com.lottery.pay.progress.zfb.util.ZfbUtil;

public class ZfbPay {
protected static Logger logger = LoggerFactory.getLogger(ZfbPay.class);
	//默认值为：1（商品购买）
    private static String payment_type="1";
    //接口名称(固定值)
    private static String service="mobile.securitypay.pay";
    //设置未付款交易的超时时间，一 旦超时，该笔交易就会自动被关闭m-分钟，h-小时，d-天，1c-当天
    private static String it_b_pay="30m";
   
  
    /**
     * 
     * 
     * 拼装数据返回
     * @throws UnsupportedEncodingException 
     */
    public static String Md5Return(String totalFee,String outTradeNo,String type,IPayConfig  config) throws UnsupportedEncodingException{
    	Map<String,String>signMap= getSignDate(totalFee,outTradeNo,config);
		String signData=RSASignature.mapToUrl(signMap);
		//组装待签名数据
		String sign = URLEncoder.encode(RSASignature.sign(signData,config.getPrivateKey()),CharsetConstant.CHARSET_UTF8);
		//返回待签名数据和签名数据
		String result=signData+"&sign="+'"'+sign+'"'+"&sign_type="+'"'+ZfbUtil.sign_type_rsa+'"';
		return result;
    }
	/**
	 * 准备待签名的数据
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static Map<String,String> getSignDate(String totalFee,String outTradeNo,IPayConfig  config) throws UnsupportedEncodingException {
        String notify_url = "";
        Map<String,String>map=new HashMap<String, String>();
		// 接收支付宝发送的通知的url 商户可根据自己的情况修改此参数
        notify_url=config.getNoticeUrl();
        map.put("partner",config.getPartner());
        map.put("seller_id",config.getSeller());
	    map.put("out_trade_no",outTradeNo);
	    map.put("subject",config.getSubject());
	    map.put("body",config.getDescription());
	    map.put("total_fee",totalFee);
	    map.put("notify_url",notify_url);
	    map.put("service",service);
	    map.put("_input_charset",CharsetConstant.CHARSET_UTF8);
	    map.put("payment_type",payment_type);
	    map.put("it_b_pay",it_b_pay);
	    return map;
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
	
	    strResult=HTTPUtil.sendPostMsg(config.getSearchUrl(),sParaTemp,CharsetConstant.CHARSET_UTF8,CharsetConstant.CHARSET_UTF8,1000);
        return  strResult;
	}
	


}
