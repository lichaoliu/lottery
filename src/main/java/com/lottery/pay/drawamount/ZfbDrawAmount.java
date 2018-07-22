package com.lottery.pay.drawamount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryDrawAmount;
import com.lottery.pay.AbstractPayConfig;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.progress.zfb.util.ZfbHttpUtil;
import com.lottery.pay.progress.zfb.util.ZfbUtil;
/**
 * 支付宝提现
 * @author lxy
 *
 */

public class ZfbDrawAmount {




    /**得到提现确定form 
     * 
     * @param accountName  账户名
     * @param config 
     * @param lotteryDrawList 提现list
     * @return
     */
	public static String getDrawStr(IPayConfig  config,List<LotteryDrawAmount>lotteryDrawList,String batchNo){
		StringBuilder strB=new StringBuilder();
		int i=0;
		BigDecimal totalMoney=new BigDecimal(0);
		for(LotteryDrawAmount lotteryDraw:lotteryDrawList){
			strB.append(lotteryDraw.getId()+"^"+lotteryDraw.getBankId()+"^")
			.append(lotteryDraw.getUserName()+"^"+lotteryDraw.getDrawAmount().divide(new BigDecimal(100))+"^")
			.append(lotteryDraw.getDescription());
			if(i!=lotteryDrawList.size()-1){
				strB.append("|");
			}
			totalMoney=totalMoney.add(lotteryDraw.getDrawAmount());
			i++;
		}
		//得到form串
		String sHtmlText =buildForm(config,batchNo,totalMoney,lotteryDrawList.size(),strB.toString());
		return sHtmlText;
	}
	

	public static String buildForm(IPayConfig  config,String batchNo,BigDecimal totalMoney,int size,String strs){
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "batch_trans_notify");
	    sParaTemp.put("partner", config.getPartner());
	    sParaTemp.put("_input_charset", CharsetConstant.CHARSET_UTF8);
		sParaTemp.put("notify_url", config.getNoticeUrl());
		sParaTemp.put("email", config.getSeller());
		sParaTemp.put("account_name", config.getAccountName());
		sParaTemp.put("pay_date", DateUtil.format("yyyyMMdd", new Date()));
		sParaTemp.put("batch_no", batchNo);
		sParaTemp.put("batch_fee", totalMoney.divide(new BigDecimal("100")).doubleValue()+"");
		sParaTemp.put("batch_num", size+"");
		sParaTemp.put("detail_data", strs.toString());
		 //待请求参数数组
        Map<String, String> sPara = ZfbUtil.buildRequestPara(sParaTemp,config.getKey(),ZfbUtil.sign_type_md5);
        List<String> keys = new ArrayList<String>(sPara.keySet());
        //拼成form串
        String sbHtml=ZfbUtil.getFormStr(keys,sPara,config.getRequestUrl());
        return sbHtml;
	}
	


	/**
	 * 查询接口封装
	 * @param config
	 * @param batchNo
	 * @return
	 */
	public static String getMapStr(AbstractPayConfig  config,String batchNo){
		String resultStr="";
		try {
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "btn_status_query");
	        sParaTemp.put("partner", config.getPartner());
	        sParaTemp.put("_input_charset", CharsetConstant.CHARSET_UTF8);
			sParaTemp.put("email", config.getSeller());
			sParaTemp.put("batch_no", batchNo);
			//建立请求
		    Map<String, String> sPara = ZfbUtil.buildRequestPara(sParaTemp,config.getKey(),ZfbUtil.sign_type_md5);;
			resultStr = ZfbHttpUtil.buildRequest(sPara,config.getRequestUrl(),"","");
		} catch (Exception e) {
			throw new LotteryException(ErrorCode.system_error, "提现查询接口请求异常"+e.getMessage());
		}
		return resultStr;
	}


}
