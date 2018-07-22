package com.lottery.pay.drawamount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.progress.zfb.util.ZfbHttpUtil;
import com.lottery.pay.progress.zfb.util.ZfbUtil;
/**
 * 支付宝银行提现
 * @author lxy
 *
 */
public class ZfbBankDrawAmount {
	private static final Logger logger = LoggerFactory.getLogger(ZfbBankDrawAmount.class);
	

    /**
     * 请求上传excel文件
     * @param config
     * @param strFilePath 摘要
     * @return 
     * @throws Exception
     */
    public static Map<String,String> getResult(IPayConfig  config,String digest,String strParaFileName,String strFilePath ) throws Exception{
		try {
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "bptb_pay_file_confirm");
	        sParaTemp.put("partner", config.getPartner());
	        sParaTemp.put("_input_charset", CharsetConstant.CHARSET_UTF8);
			sParaTemp.put("file_digest_type", ZfbUtil.sign_type_md5);
			sParaTemp.put("digest_bptb_pay_file", digest);
			sParaTemp.put("bussiness_type", "T0");
			 //待请求参数数组
	        Map<String, String> sPara = ZfbUtil.buildRequestPara(sParaTemp,config.getKey(),ZfbUtil.sign_type_md5);
	    	return sPara;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
    	
    }
    /**
     * 确定上传
     * @param config
     * @param filename
     * @return
     */
    public static String confirm(IPayConfig  config,String filename){
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "bptb_user_confirm");
        sParaTemp.put("partner", config.getPartner());
        sParaTemp.put("_input_charset",CharsetConstant.CHARSET_UTF8);
		sParaTemp.put("notify_url", config.getNoticeUrl());
		sParaTemp.put("return_url", config.getNoticeUrl());
		sParaTemp.put("email", config.getSeller());
		sParaTemp.put("file_name", filename);
		 //待请求参数数组
        Map<String, String> sPara = ZfbUtil.buildRequestPara(sParaTemp,config.getKey(),ZfbUtil.sign_type_md5);
        List<String> keys = new ArrayList<String>(sPara.keySet());
		 //拼成form串
        String sbHtml=ZfbUtil.getFormStr(keys,sPara,config.getRequestUrl());
        return sbHtml;
    }
    
    /**
     * 查询批量银行卡提现
     * @param fileName
     * @param config
     * @return
     * @throws Exception
     */
    public static  String queryDrawBank(String fileName,IPayConfig  config) throws Exception{
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.batchpay.bptb.detail.query");
        sParaTemp.put("partner", config.getPartner());
        sParaTemp.put("_input_charset", CharsetConstant.CHARSET_UTF8);
		sParaTemp.put("file_name", fileName);
		//建立请求
	    Map<String, String> sPara = ZfbUtil.buildRequestPara(sParaTemp,config.getKey(),ZfbUtil.sign_type_md5);;
		//待请求参数数组
		String strResult="";
		try {
			logger.error("查询提现请求数据为:{}",sPara.toString());
			strResult = ZfbHttpUtil.buildRequest(sPara,config.getRequestUrl(),"","");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
  	    return strResult;
    }
}
