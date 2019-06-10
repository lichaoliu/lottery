package com.lottery.pay.drawamount;

import com.lottery.common.contains.DrawAmountStatus;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.cache.model.PayPropertyCacheModel;
import com.lottery.core.dao.LotteryDrawAmountDAO;
import com.lottery.pay.BasePayConfig;
import com.lottery.pay.IPayConfigFactory;
import com.lottery.pay.progress.zfb.util.ZfbUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public abstract class AbstractZfbBankDraw extends AbstractThreadRunnable{
	private long interval = 300000; // 每次处理间隔
	@Autowired
	private PayPropertyCacheModel payConfigCacheModel;
	@Resource(name = "payFactoryMap")
	private Map<PayChannel, IPayConfigFactory> payFactoryMap;
	@Autowired
	private LotteryDrawAmountDAO lotteryDrawAmountDAO;
	
	@Override
	public void executeRun(){
		while(running){
			try{
				execute();
			}catch(Exception e){
				logger.error("提现查询明细异常",e);

			}
			
			synchronized (this) {
				try {
					wait(this.getInterval());
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}
	
	protected  abstract void execute ()  throws Exception;
	
	
	protected BasePayConfig getConfig(List<String> lotteryDrawAmounts,PayChannel payChannel){
		BasePayConfig zfbConfig =null;
		if (lotteryDrawAmounts != null && lotteryDrawAmounts.size() > 0) {
			//得到配置
			zfbConfig =ZfbUtil.getPayConfig(payConfigCacheModel,logger,payFactoryMap,payChannel);
			if(zfbConfig==null){
				logger.error(payChannel.getName()+"配置为空");
			}
		}
		return zfbConfig;
	}
	
	protected List<String> getDataList(int type,int operateType){
		List<String> lotteryDrawAmounts = null;
		try {
			lotteryDrawAmounts = lotteryDrawAmountDAO.findLotteryDrawList(DrawAmountStatus.handing.getValue(),type,operateType);
		} catch (Exception e) {
			logger.error("支付宝提现订单查询出错!", e);
		}
		return lotteryDrawAmounts;
	}
	
	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}
}
