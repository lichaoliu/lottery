package com.lottery.pay.progress.zfb;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.util.DateUtil;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.progress.zfb.util.RSASignature;
import com.lottery.pay.progress.zfb.util.ZfbHttpUtil;
import com.lottery.pay.progress.zfb.util.ZfbUtil;

public class ZfbWapPay {
	//返回格式
	private static String format = "xml";
	//必填，不需要修改
	private static String v = "2.0";
	//操作中断返回地址
	private static String errorUrl = "http://118.145.27.37:8080/ptsinet/error.jsp";
	
	/**
	 * 返回 确定表单
	 * @return
	 * @throws Exception 
	 */
	public static String getReturnUrl(String out_trade_no,String amount,IPayConfig config) throws Exception{
		//获取token
		String token=getToken(out_trade_no,amount,config);
		//业务详细
		String req_data = "<auth_and_execute_req><request_token>" + token + "</request_token></auth_and_execute_req>";
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
		sParaTemp.put("partner", config.getPartner());
		sParaTemp.put("_input_charset",CharsetConstant.CHARSET_UTF8);
		sParaTemp.put("sec_id",ZfbUtil.sign_type_nummd5);
		sParaTemp.put("format", format);
		sParaTemp.put("v", v);
		sParaTemp.put("req_data", req_data);
		
		//待请求参数数组
        Map<String, String> sPara = ZfbUtil.buildRequestPara(sParaTemp,config.getPrivateKey(),ZfbUtil.sign_type_nummd5);
        List<String> keys = new ArrayList<String>(sPara.keySet());
        
        //拼成form串
        String sbHtml=ZfbUtil.getFormStr(keys,sPara,config.getRequestUrl());
        return sbHtml;
	}
	
	/**
	 * 调用授权接口获取授权码token
	 * @return
	 * @throws Exception 
	 */
	/**
	 * 调用授权接口获取授权码token
	 * @return
	 * @throws Exception 
	 */
	public static String getToken(String out_trade_no,String amount,IPayConfig config) throws Exception{
		//建立请求
		String sHtmlTextToken="";
		String request_token="";
		//请求号
		String req_id =DateUtil.format("yyyyMMddHHmmss",new Date());
		//请求业务参数详细
		StringBuilder req_dataToken =new StringBuilder();
		req_dataToken.append("<direct_trade_create_req>");
		req_dataToken.append("<notify_url>"+config.getNoticeUrl()+"</notify_url>");
		req_dataToken.append("<call_back_url>"+config.getReturnUrl()+"</call_back_url>");
		req_dataToken.append("<seller_account_name>" + config.getSeller() + "</seller_account_name>");
		req_dataToken.append("<out_trade_no>" + out_trade_no + "</out_trade_no>");
		req_dataToken.append("<subject>" + config.getSubject()+ "</subject>");
		req_dataToken.append("<total_fee>" + amount + "</total_fee>");
		req_dataToken.append("<merchant_url>" + errorUrl + "</merchant_url>");
		req_dataToken.append("</direct_trade_create_req>");
		//把请求参数打包成数组
		Map<String, String> sParaTempToken = new HashMap<String, String>();
		sParaTempToken.put("service", "alipay.wap.trade.create.direct");
		sParaTempToken.put("partner", config.getPartner());
		sParaTempToken.put("_input_charset",CharsetConstant.CHARSET_UTF8);
		sParaTempToken.put("sec_id", ZfbUtil.sign_type_nummd5);
		sParaTempToken.put("format", format);
		sParaTempToken.put("v", v);
		sParaTempToken.put("req_id", req_id);
		sParaTempToken.put("pay_expire","30");//自动关闭时间（分钟）
		sParaTempToken.put("req_data", req_dataToken.toString());
		
		
		sHtmlTextToken = buildRequest(config.getRequestUrl(),sParaTempToken,config.getPrivateKey());
		//URLDECODE返回的信息
		sHtmlTextToken = URLDecoder.decode(sHtmlTextToken,CharsetConstant.CHARSET_UTF8);
		//获取token
		request_token = getRequestToken(sHtmlTextToken,config.getPrivateKey());
		return request_token;
	}
	
	  /**
     * @param sParaTemp 请求参数数组
     * @return 支付宝处理结果
     * @throws Exception
     */
    public static  String buildRequest(String requestUrl,Map<String, String> sParaTemp,String privateKey) throws Exception {
    	
    	Map<String, String> sPara = ZfbUtil.buildRequestPara(sParaTemp,privateKey,ZfbUtil.sign_type_nummd5);
    	String strResult =ZfbHttpUtil.buildRequest(sPara,requestUrl,"","");
    	return strResult;
    }
    
    /**
     * 解析远程模拟提交后返回的信息，获得token
     * @param text 要解析的字符串
     * @return 解析结果
     * @throws Exception 
     */
    public static String getRequestToken(String text,String privatekey) throws Exception {
    	String request_token = "";
    	//以“&”字符切割字符串
    	String[] strSplitText = text.split("&");
    	//把切割后的字符串数组变成变量与数值组合的字典数组
    	Map<String, String> paraText = new HashMap<String, String>();
        for (int i = 0; i < strSplitText.length; i++) {
    		//获得第一个=字符的位置
        	int nPos = strSplitText[i].indexOf("=");
        	//获得字符串长度
    		int nLen = strSplitText[i].length();
    		//获得变量名
    		String strKey = strSplitText[i].substring(0, nPos);
    		//获得数值
    		String strValue = strSplitText[i].substring(nPos+1,nLen);
    		//放入MAP类中
    		paraText.put(strKey, strValue);
        }
    	if (paraText.get("res_data") != null) {
    		String res_data = paraText.get("res_data");
    		//解析加密部分字符串
    		res_data = RSASignature.decrypt(res_data,privatekey,CharsetConstant.CHARSET_UTF8);
    		//解析出来token
    		Document document = DocumentHelper.parseText(res_data);
    		request_token = document.selectSingleNode("//direct_trade_create_res/request_token").getText();
    	}
    	return request_token;
    }
    
	/**
     * 解密
     * @param inputPara 要解密数据
     * @return 解密后结果
     */
    public static String decrypt(String notify_data,String privateKey) throws Exception {
    	String data= RSASignature.decrypt(notify_data, privateKey,CharsetConstant.CHARSET_UTF8);
    	return data;
    }

}
