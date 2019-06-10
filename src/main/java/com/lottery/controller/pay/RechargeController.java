package com.lottery.controller.pay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.pay.PayChannel;
import net.sf.json.JSONObject;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.Constants;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.pay.IRechargeProcess;

import javax.annotation.Resource;

/**
 * 充值
 * @author lxy
 *
 */
@Controller
@RequestMapping("/recharge")
public class RechargeController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name="payChannelProcessMap")
	private Map<PayChannel,IRechargeProcess> process;

	/**
	 * 客户端充值请求
	 * @param payChannel 支付渠道 PayChannel 类
	 * @param amount 充值金额(单位:元)
	 * @param userno 用户名
	 * @param forGive 是否赠送 (0否,1是)Yesnostatus
	 * @param frontUrl 返回前端地址(比如html5,wap)
	 */
	@RequestMapping(value = "/toReCharge", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData toReCharge(
			@RequestParam(value = "payChannel", required = true) String payChannel,
			@RequestParam(value = "amount", required = true) String amount,
			@RequestParam(value = "userno", required = true) String userno,
			@RequestParam(value = "cardType", required = false) String cardType,
			@RequestParam(value = "forGive", required = false,defaultValue="0") Integer forGive,
			@RequestParam(value = "version", required = false,defaultValue="0") String version,
			@RequestParam(value = "frontUrl", required = false) String frontUrl
			) {
		ResponseData rd = new ResponseData();
		try {
			logger.error("payChannel:"+payChannel+",amount:"+amount+",userno:"+userno);
			RechargeRequestData requestDate=new RechargeRequestData();
			requestDate.setAmount(new BigDecimal(amount));
			requestDate.setUserno(userno);
			requestDate.setForGive(forGive);
			requestDate.setCardType(cardType);
            requestDate.setFrontUrl(frontUrl);
			IRechargeProcess rechargeProcess=process.get(PayChannel.get(payChannel));
			if(rechargeProcess==null){
				rd.setErrorCode(ErrorCode.no_exits.getValue());
				logger.error("充值渠道:{},不存在",payChannel);
				rd.setValue("pay channel no exits");
				return rd;
			}
			RechargeResponseData responseProcess=rechargeProcess.process(requestDate);
			logger.error(JSONObject.fromObject(responseProcess).toString());
			if(responseProcess==null){
				rd.setErrorCode(ErrorCode.no_exits.getValue());
				rd.setValue("pay channel no configure");
				return rd;
			}
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(responseProcess);
			logger.error("用户:{},通过渠道:{}充值:{},充值订单号:{},是否赠送:{},充值卡为{},版本:{}",new Object[]{userno,payChannel,amount,responseProcess.getOrderNo(),forGive,responseProcess.getCardType(),version});
		} catch (Exception e) {
			logger.error("用户{},通过渠道:{},充值出错",userno,payChannel,e);
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}
	

	
	
	/**
	 * 返回通知处理
	 * @param strs
	 * @return
	 */
	@RequestMapping(value = "/toZfbWebReturn", method = RequestMethod.POST)
	public @ResponseBody ResponseData toZfbWebReturn(@RequestParam("notify_data") String notifyStr,@RequestParam(value = "payChannel", required = true) String payChannel) {
		
		ResponseData rd = new ResponseData();
		try {
			//验证签名
		    JSONObject jsonObject=JSONObject.fromObject(notifyStr);
		    Map p= (Map)jsonObject;
			String []array=p.toString().split("\\{")[1].split("\\}")[0].split("\\,");
			Map<String,String> params = new HashMap<String,String>();
			for(int i=0; i<array.length; i++) {
				String[] data = array[i].split("\\:");
			    if(data.length>1){
			    	params.put(data[0].substring(1,data[0].length()-1).trim(),data[1].toString().substring(2,data[1].toString().length()-2).trim());
			    }else{
			    	params.put(data[0],"");
			    }	 
			}
			String result_code=(String)params.get("result_code");
			rd.setValue(Constants.zfbDrawError.get(result_code)!=null?Constants.zfbDrawError.get(result_code):"提交成功");
			rd.setErrorCode(ErrorCode.Success.getValue());	
		}catch (Exception e) {
			logger.error("充值回调出错,{}",notifyStr,e);
			rd.setErrorCode(ErrorCode.Faile.getValue());
		}
		return rd;
	}
	
	
	@RequestMapping(value = "/notify", method = RequestMethod.POST)
	public @ResponseBody String notify(@RequestParam(value="notify_data",required=true) String strs,@RequestParam(value = "payChannel", required = true) String payChannel){
		try {

			IRechargeProcess processpay=process.get(PayChannel.get(payChannel));
			if(processpay==null){
				logger.error("渠道:{}配置为空,的字符串是:{}",payChannel,strs);
				return null;
			}
			return processpay.notify(strs);
		} catch (Exception e) {
			logger.error("通知{}处理异常",strs,e);
		}
		return null;
	}

	
	
	

}
