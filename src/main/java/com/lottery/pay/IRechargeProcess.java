package com.lottery.pay;


import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.contains.pay.RechargeResponseData;

/**
 * Created by fengqinyun on 14-3-19.
 */
public interface IRechargeProcess {

	/**
	 * 
	 * @param requestData
	 * @return
	 */
    public RechargeResponseData process(RechargeRequestData requestData);
    
    /**
     *
     * 签名验证
     * @param paramStr 原始字符串
     * */
    public boolean verifySign(String paramStr);
    /**
     * 通知结果处理 
     * @param notifyData
     * @return
     */
    public String notify(String notifyData);
    /**
     * 同步应答
     * @param requestData 请求数据
     * */

    public boolean syncResponse(String requestData);


    
}
