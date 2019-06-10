package com.lottery.pay.impl;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.UserTransactionDao;
import com.lottery.core.domain.UserTransaction;
import com.lottery.core.domain.give.UserRechargeGive;
import com.lottery.core.domain.give.UserRechargeGiveDetail;
import com.lottery.core.domain.give.UserRechargeGiveDetailPK;
import com.lottery.core.service.UserTransactionService;
import com.lottery.core.service.bet.BetService;
import com.lottery.core.service.give.UserRechargeGiveDetailSerivce;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.IRechargeProcess;
import com.lottery.pay.handler.UserRechargeGiveHandler;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengqinyun on 14-3-19.
 */
public abstract class AbstractRechargeProcess implements IRechargeProcess {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserRechargeGiveHandler giveHandler;
	@Autowired
	UserTransactionDao userTransactionDao;
	@Autowired
	protected UserTransactionService userTransactionService;
	@Autowired
    protected PayConfigService payConfigService;
	@Autowired
	protected UserRechargeGiveDetailSerivce userRechargeGiveDetailSerivce;
	@Resource(name="payChannelProcessMap")
	protected  Map<PayChannel,IRechargeProcess> payChannelProcessMap;
	@Autowired
    protected BetService betService;
	/**
	 * 获得充值配置和信息 
	 */
	@Override
	public RechargeResponseData process(RechargeRequestData requestData) {
		RechargeResponseData responseData = new RechargeResponseData();
		IPayConfig payConfig = getIPayConfig();
		if (payConfig == null) {
			responseData.setIsPaused(YesNoStatus.yes.value+"");
			return responseData;
		}

		if(StringUtils.isBlank(payConfig.getIsPaused())||payConfig.getIsPaused().equals(YesNoStatus.yes.value+"")){
			responseData.setIsPaused("1");
			return responseData;
		}

		BigDecimal giveAmount = BigDecimal.ZERO;
		String giveId = null;
		BigDecimal notDrawPerset=null;
		String userno=requestData.getUserno();
		UserRechargeGiveDetail rechargeGiveDetail=null;
		if(requestData.getForGive()==YesNoStatus.yes.value){
			UserRechargeGive rechargeGive = giveHandler.get(userno, requestData.getAmount());// 获得充值金额
			if (rechargeGive != null) {
				giveAmount = giveAmount.add(rechargeGive.getGiveAmount());
				giveId = rechargeGive.getId();
				notDrawPerset=rechargeGive.getNotDrawPerset();
				rechargeGiveDetail=userRechargeGiveDetailSerivce.get(new UserRechargeGiveDetailPK(giveId, userno));
			}
		}
		UserTransaction userTransaction = userTransactionService.getUserTransactionBean(userno, requestData.getAmount(), getPayChannel(), payConfig.getFee(), giveAmount, giveId,notDrawPerset);
		if(rechargeGiveDetail!=null){
			rechargeGiveDetail.setTransationId(userTransaction.getId());
			rechargeGiveDetail.setUpdateTime(new Date());
			userRechargeGiveDetailSerivce.update(rechargeGiveDetail);
		}

		if (userTransaction != null) {
			responseData.setUserno(userno);
			responseData.setOrderNo(userTransaction.getId());
			responseData.setNoticeUrl(payConfig.getNoticeUrl());
			responseData.setPartner(payConfig.getPartner());
			responseData.setRequestUrl(payConfig.getRequestUrl());
			responseData.setReturnUrl(payConfig.getReturnUrl());
			responseData.setSeller(payConfig.getSeller());
			responseData.setKey(payConfig.getKey());
			responseData.setCardType(requestData.getCardType());
		}
		 this.sign(payConfig, responseData, requestData);
		return responseData;
	}
  
	/**
	 * 充值确认
	 * @param transactionId 平台充值订单号
	 * @param tradeNo 充值渠道订单 号
	 * @param amount 充值金额
	 * @param flag 充值是否成功  (true 成功,false 失败)
	 * @param memo 充值说明
	 * */
	protected boolean rechargeResult(String transactionId, String tradeNo, BigDecimal amount, boolean flag, String memo){

		try{
			UserTransaction	userTransaction=userTransactionService.chargeResult(transactionId, tradeNo, amount, flag, memo);
			if (flag&& userTransaction!=null){
				String userno=userTransaction.getUserno();
				betService.transactionBet(userno);
			}
			return true;
		}catch(Exception e){
			logger.error("充值订单{}充值失败",transactionId,e);
			return false;
		}
	}

	/**
	 * 字符串拼接，包括加密等
	 * @param payConfig 充值配置
	 * @param responseData 返回数据
	 * @param requestData 请求的数据
	 * */
	protected abstract RechargeResponseData sign(IPayConfig payConfig,RechargeResponseData responseData,RechargeRequestData requestData);

	protected IPayConfig getIPayConfig(){
		if(getPayChannel()==null){
			logger.error("充值渠道为空");
			return null;
		}
		IPayConfig payConfig=payConfigService.getByPayChannel(getPayChannel());
		if(payConfig==null){
			logger.error("充值渠道:{}配置为空",getPayChannel());
			return null;
		}
		return payConfig;
	}

	public String notify(String requestStr){
		return null;
	}
		
	protected abstract PayChannel getPayChannel();
	
	protected Map<String,String> getMap(String notifyStr){
		if (StringUtil.isEmpty(notifyStr)){
			logger.error("通知信息为空");
			throw  new LotteryException("通知内容为空");
		}

		JSONObject jsonObject=JSONObject.fromObject(notifyStr);
		Map m = (Map)jsonObject;
		String []array=m.toString().split("\\{")[1].split("\\}")[0].split("\\,");
		Map<String,String> map = new HashMap<String,String>();
		for(int i=0; i<array.length; i++) {
			String[] data = array[i].split("\\:");
		    if(data.length>1){
		    	map.put(data[0].substring(1,data[0].length()-1),data[1].toString().substring(2,data[1].toString().length()-2));
		    }else{
		    	map.put(data[0],"");
		    }	 
		}
		return map;
	}
	protected String getPath() {

		return this.getClass().getClassLoader().getResource("").getPath();
	}
	@Override
	public boolean verifySign(String notifyStr){
		return true;
	}
	@PostConstruct
	protected  void init(){
		if (getPayChannel()!=null)
		payChannelProcessMap.put(getPayChannel(),this);
	}

	public boolean syncResponse(String requestData){
		return true;
	}
}
