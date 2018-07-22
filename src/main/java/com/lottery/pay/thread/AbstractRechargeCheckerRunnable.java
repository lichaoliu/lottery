package com.lottery.pay.thread;

import com.lottery.common.ListSerializable;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.cache.model.PayPropertyCacheModel;
import com.lottery.core.domain.PayProperty;
import com.lottery.core.domain.UserTransaction;
import com.lottery.core.service.UserTransactionService;
import com.lottery.core.service.bet.BetService;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.IPayConfigFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public abstract class AbstractRechargeCheckerRunnable extends AbstractThreadRunnable {

	protected long interval = 45000; // 每次处理间隔
	protected int maxTicketAllotSize=10;
	@Autowired
	protected UserTransactionService userTransactionService;
	@Autowired
	protected PayPropertyCacheModel payConfigCacheModel;
	@Resource(name="payFactoryMap")
	protected Map<PayChannel,IPayConfigFactory> payFactoryMap;
	@Autowired
	protected BetService betService;
	@Override
	protected void executeRun() {
		while(running){
			PayChannel payChannel=getPayChannel();
			try{
				List<UserTransaction> transactionList=userTransactionService.findTransactions(maxTicketAllotSize,payChannel.getValue());
				if(transactionList!=null&&transactionList.size()>0){
					IPayConfig payConfig=getPayConfig(payChannel);
					if(payConfig!=null&& StringUtils.isNotBlank(payConfig.getIsPaused())&&payConfig.getIsPaused().equals(YesNoStatus.no.value+"")){
						process(payConfig,transactionList);
					}
				}
			}catch(Exception e){
				logger.error("查询充值出错",payChannel,e);
			}
			synchronized(this){
				try {
					this.wait(getInterval());
				} catch (InterruptedException e) {
					logger.error("等待异常",e);
				}
			}
		}
		
		
	}
	

	
	
	 protected IPayConfig getPayConfig(PayChannel payChannel){
			List<PayProperty> payConfigs =null;
			String key=null;
			try {
				key=payChannel.getValue();
				ListSerializable<PayProperty> listSerializable=payConfigCacheModel.get(key);
				if(listSerializable!=null){
					payConfigs=listSerializable.getList();
				}
			} catch (Exception e) {
			 logger.error("获取充值配置出错",e);
			}
			if(payConfigs==null||payFactoryMap.get(payChannel)==null){
				logger.error("[{}]充值配置为空",key);
				return null;
			}
			IPayConfig config = payFactoryMap.get(payChannel).getVenderConfig(payConfigs);
			return config;
	 }


	protected String getPath() {
		return this.getClass().getClassLoader().getResource("").getPath();
	}
	 /**充值结果处理
	  * @param userTransactionId 充值id
	  * @param trandNo 充值渠道订单号
	  * @param tradeAmount 充值渠道金额
	  * @param flag true 成功，flase 失败
	  * @param memo 描述
	  * */
	 
	 protected void processResult(String userTransactionId,String trandNo,String tradeAmount,boolean flag,String memo){
	   UserTransaction userTransaction=	userTransactionService.chargeResult(userTransactionId, trandNo, new BigDecimal(tradeAmount),flag,memo);
		 if (flag&& userTransaction!=null){
			 String userno=userTransaction.getUserno();
			 betService.transactionBet(userno);
		 }
	 } 
	 
	 protected abstract PayChannel getPayChannel();
	 /**
	  * 具体实现业务逻辑
	  * */
	 protected abstract void process(IPayConfig payConfig,List<UserTransaction> userTransactionList);


	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}
}
