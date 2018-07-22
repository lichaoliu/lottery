package com.lottery.pay.progress.yeebao;


import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.progress.yeebao.util.DigestUtil;


public class YeeBaoPay {
protected static Logger logger = LoggerFactory.getLogger(YeeBaoPay.class);

    /**
     * 
     * 
     * 拼装数据返回
     * @throws UnsupportedEncodingException 
     */
public static RechargeResponseData Md5Return(String totalFee,String outTradeNo,IPayConfig  config,String cardTypeRecharge,RechargeResponseData responseData) throws UnsupportedEncodingException{
//	 Map<String,String>signMap= getSignDate(totalFee,outTradeNo,config,cardTypeRecharge);
     String sValue=DigestUtil.mapToUrl(totalFee,outTradeNo,config,cardTypeRecharge);
	 // 获得MD5-HMAC签名
	 String hmac = DigestUtil.hmacSign(sValue.toString(), config.getKey());
     // 拼成form串
     String sbHtml=getFormStr(totalFee,outTradeNo,config,cardTypeRecharge, hmac);
     responseData.setSign(hmac);
	 responseData.setResult(sbHtml);
	 return responseData;
}


public static String getFormStr(String totalFee,String outTradeNo,IPayConfig  config,String cardType,String hmac){
	  StringBuffer sbHtml = new StringBuffer();
    sbHtml.append("<form id=\"form1\"  action=\""+config.getRequestUrl()+"\" method=\"post\">");
    sbHtml.append("<input type=\"hidden\" name=\"p0_Cmd\" value=\"Buy\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"p1_MerId\" value=\""+ config.getPartner()+"\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"p2_Order\" value=\""+outTradeNo+"\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"p3_Amt\" value=\"" +totalFee+ "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"p4_Cur\" value=\"CNY\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"p5_Pid\" value=\"\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"p6_Pcat\" value=\"\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"p7_Pdesc\"  value=\"\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"p8_Url\" value=\"" +config.getNoticeUrl()+ "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"p9_SAF\" value=\"0\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"pa_MP\" value=\"\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"pd_FrpId\" value=\"\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"pr_NeedResponse\" value=\"1\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"hmac\" value=\""+hmac+"\"/>");
    //submit按钮控件请不要含有name属性
    sbHtml.append("<input type=\"submit\" value=\"确定 \" style=\"display:none;\"></form>");
    sbHtml.append("<script>document.forms['form1'].submit();</script>");
    return sbHtml.toString();
   
}

	/**
	 * 准备待签名的数据
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
//	private static Map<String,String> getSignDate(String totalFee,String outTradeNo,IPayConfig  config,String cardType) throws UnsupportedEncodingException {
//	
//        //把请求参数打包成数组
//		Map<String, String> sParaTemp = new HashMap<String, String>();
//		sParaTemp.put("p0_Cmd","Buy");
//        sParaTemp.put("p1_MerId", config.getPartner());
//        sParaTemp.put("p2_Order",outTradeNo);          // 商户订单号
//		sParaTemp.put("p3_Amt", totalFee);             // 支付金额
//		sParaTemp.put("p4_Cur","CNY");
//		sParaTemp.put("p5_Pid","");       // 商品名称
//		sParaTemp.put("p6_Pcat","");               // 商品种类
//		sParaTemp.put("p7_Pdesc",""); // 商品描述
//		sParaTemp.put("p8_Url",config.getNoticeUrl()); // 商户接收支付成功数据的地址
//		sParaTemp.put("p9_SAF", "0");                  // 需要填写送货信息 0：不需要  1:需要
//		sParaTemp.put("pa_MP", "");                    // 商户扩展信息
//		sParaTemp.put("pd_FrpId",cardType); 
//		sParaTemp.put("pr_NeedResponse", "1");         // 默认为"1"，需要应答机制
//	    return sParaTemp;
//	}
   /**
    * 订单查询
    * @param orderId
    * @return
    * @throws Exception 
    */
	public static List<String> queryOrderInfo(String orderId,IPayConfig  config) throws Exception{
		//把请求参数打包成数组
		StringBuilder sParaTemp = new StringBuilder();
		String hmac = DigestUtil.getHmac(new String[] {"QueryOrdDetail",config.getPartner(),orderId},config.getKey());
		sParaTemp.append("p0_Cmd=QueryOrdDetail");
		sParaTemp.append("&p1_MerId="+config.getPartner());
		sParaTemp.append("&p2_Order="+orderId);
		sParaTemp.append("&hmac="+hmac);
		List<String> strList=DigestUtil.sendGet(config.getSearchUrl(),sParaTemp.toString(),3000,"GBK");
		logger.error("易宝支付查询url:{},发送的请求是:{},返回的是:{}",new Object[]{config.getSearchUrl(),sParaTemp.toString(),strList});
        return  strList;
	}
	
	
	


}
