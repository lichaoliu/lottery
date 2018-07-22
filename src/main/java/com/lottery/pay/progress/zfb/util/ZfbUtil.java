package com.lottery.pay.progress.zfb.util;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.ListSerializable;
import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.core.cache.model.PayPropertyCacheModel;
import com.lottery.core.domain.PayProperty;
import com.lottery.pay.BasePayConfig;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.IPayConfigFactory;


public class ZfbUtil {
	private static Logger logger=LoggerFactory.getLogger(ZfbUtil.class);
	 //DSA、RSA、MD5 三个值可选，必须大写。
    public static String sign_type_rsa="RSA";
    public static String sign_type_md5="MD5";
    public static String sign_type_nummd5="0001";
    public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
    
    
    public static String getFormStr(List<String> keys,Map<String, String> sPara,String requestUrl){
    	  StringBuffer sbHtml = new StringBuffer();
          sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + requestUrl
                        + "_input_charset=" + CharsetConstant.CHARSET_UTF8+ "\" method=\"get\">");
          for (int i = 0; i < keys.size(); i++) {
              String name = (String) keys.get(i);
              String value = (String) sPara.get(name);
              sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
          }
          //submit按钮控件请不要含有name属性
          sbHtml.append("<input type=\"submit\" value=\"确定 \" style=\"display:none;\"></form>");
          sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
  		
          return sbHtml.toString();
    }
    
    
    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
	public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp,String privatekey,String signType) {
    	 //除去数组中的空值和签名参数
        Map<String, String> sPara = RSASignature.paraFilter(sParaTemp);
    	//生成签名结果
        String mysign = buildRequestMysign(sPara,privatekey,signType,true);
        //签名结果与签名方式加入请求提交参数组中
        sParaTemp.put("sign", mysign);
        if(!sParaTemp.get("service").equals("alipay.wap.trade.create.direct") && ! sParaTemp.get("service").equals("alipay.wap.auth.authAndExecute")) {
        	sParaTemp.put("sign_type",signType);
        }
        return sParaTemp;
    }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param publicKey 支付宝公钥
	* @return 布尔值
	*/
	public static boolean verify(String content, String sign, String ali_public_key, String input_charset)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(ali_public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
			boolean bverify = signature.verify(Base64.decode(sign));
			return bverify;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}

    /**
     * 生成签名结果
     * @param sPara 要签名的数组 
     * @param  privatekey 商户的私钥
     * @return 签名结果字符串
     * 
     */
	public static String buildRequestMysign(Map<String, String> sPara,String privatekey,String signType,Boolean isSort) {
		//过滤空值、sign与sign_type参数
    	Map<String, String> sParaNew = RSASignature.paraFilter(sPara);
    	String prestr="";
    	String mysign="";
    	if(isSort) {
    	     prestr = RSASignature.createLinkString(sParaNew); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
    	 }else {
			 prestr = RSASignature.createLinkStringNoSort(sParaNew);
	     }
    	  logger.info("原始加密串是:{}",prestr);
    	 if(signType.equals("MD5") ) {
         	mysign = RSASignature.sign(prestr, privatekey,CharsetConstant.CHARSET_UTF8);
         }
         if(signType.equals("0001") ){
        	 mysign  = RSASignature.sign(prestr,privatekey);
         }
        return mysign;
    }
	
	/**
	 * 支付宝验签
	 * @param sPara
	 * @param sign
	 * @param privatekey
	 * @param signType
	 * @return
	 */
	public static boolean VerfyMysign(Map<String, String> sPara,String sign,String privatekey) {
		//过滤空值、sign与sign_type参数
    	Map<String, String> sParaNew = RSASignature.paraFilter(sPara);
		String prestr = RSASignature.createLinkString(sParaNew); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		boolean mysign = verify(prestr,sign,privatekey,CharsetConstant.CHARSET_UTF8);
        return mysign;
    }
	/**
	 * 验证签名
	 * @param params
	 * @param config
	 * @return 
	 * @return
	 */
	public  static boolean VerfySign(Map<String, String> params,IPayConfig  config){
        String sign=ZfbUtil.buildRequestMysign(params,config.getKey(), ZfbUtil.sign_type_md5,true);
	    if(params.get("sign").equals(sign)) return true;
	    else return false;
	}
	
	public static BasePayConfig getPayConfig(PayPropertyCacheModel payConfigCacheModel,Logger logger,Map<PayChannel, IPayConfigFactory> payFactoryMap,PayChannel payChannel){
		List<PayProperty> payConfigs = new ArrayList<PayProperty>();
		try {
			ListSerializable<PayProperty> listSerializable=payConfigCacheModel.get(payChannel.getValue());
			if(listSerializable!=null){
				payConfigs=listSerializable.getList();
			}
		} catch (Exception e1) {
			logger.error("获取[id={}]支付配置属性信息过程出现异常!",payChannel.getValue());
			return null;
		}
		if (payConfigs == null || payConfigs.isEmpty()) {
			logger.error("未找到对应的支付属性, terminalId={}",payChannel.getValue());
			return null;
		}
		IPayConfig config = payFactoryMap.get(payChannel).getVenderConfig(payConfigs);
		BasePayConfig zfbConfig = (BasePayConfig) config;
		return zfbConfig;
	}
}
