package com.lottery.retrymodel.prize;

import com.lottery.common.contains.TopicName;
import com.lottery.common.contains.lottery.HighFrequencyLottery;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.topic.TopicMessageSendService;
import com.lottery.retrymodel.ApiRetryCallback;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
/**
 * 检查 算奖是否结束
 * */
public class PrizeEndCheckerCallback extends ApiRetryCallback<Object>{

	private Integer lotterytype;
	private String phase;
	private long threadSleepTime = 15000;
	protected LotteryPhaseService lotteryPhaseService;
	private TopicMessageSendService topicMessageSendService;

	private LotteryOrderService lotteryOrderService;
	@Override
	protected Boolean execute() throws Exception {
		if(lotterytype==null||StringUtils.isBlank(phase)||lotteryPhaseService==null||topicMessageSendService==null||lotteryOrderService==null){
			throw new Exception("参数不全");
		}
	 	boolean  valiteflag=true;

		if(!HighFrequencyLottery.contains(lotterytype)){
			threadSleepTime=120000;
		}

		long total = 1;

		while (valiteflag) {
			
			try {
				total = checkPrizeEnd(lotterytype, phase);
				if (total == 0) {
					sendPrizeEnd(lotterytype, phase);
					valiteflag=false;
					logger.error("彩种:{},期号:{},已经算奖完成",lotterytype,phase);
					break;
				}
                logger.error("彩种:{},期号:{},未算奖的个数是:{}",lotterytype,phase,total);
			} catch (Exception e) {
				logger.error("检查算奖结束异常lotterytype={},phase={}",lotterytype,phase, e);
				break;
			}
			synchronized (this) {
				try {
					this.wait(threadSleepTime);
				} catch (InterruptedException e) {
					logger.error("检查算奖结束异常lotterytype={},phase={}",lotterytype,phase, e);
				}
			}
			
		}
		return true;
	}
	
	public long checkPrizeEnd(int lotterytype, String phase)throws Exception{
		
		LotteryPhase currentPhase = lotteryPhaseService.getByTypeAndPhase(lotterytype, phase);
		
		if(currentPhase.getPhaseStatus() == PhaseStatus.prize_end.getValue()) {
			return 0;
		}
		
		long total = lotteryOrderService.countNotPrize(lotterytype, phase);

		if (total > 0) {
			return total;
		}
		lotteryPhaseService.updatePhasePrizeState(lotterytype, phase, PhaseStatus.prize_end.getValue());
		return 0;

	}

	private void sendPrizeEnd(int lotterytype, String phase) {
		try {
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("lotterytype", lotterytype);
			headers.put("phase", phase);
			topicMessageSendService.sendMessage(TopicName.calcuteEnd, headers);
		}catch(Exception e) {
			logger.error("发送期算奖结束消息出错lotterytype={},phase={}",lotterytype,phase,e);
		}
		
	}
	public Integer getLotterytype() {
		return lotterytype;
	}
	public void setLotterytype(Integer lotterytype) {
		this.lotterytype = lotterytype;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	
	public LotteryPhaseService getLotteryPhaseService() {
		return lotteryPhaseService;
	}
	public void setLotteryPhaseService(LotteryPhaseService lotteryPhaseService) {
		this.lotteryPhaseService = lotteryPhaseService;
	}
	public TopicMessageSendService getTopicMessageSendService() {
		return topicMessageSendService;
	}
	public void setTopicMessageSendService(TopicMessageSendService topicMessageSendService) {
		this.topicMessageSendService = topicMessageSendService;
	}

	public LotteryOrderService getLotteryOrderService() {
		return lotteryOrderService;
	}

	public void setLotteryOrderService(LotteryOrderService lotteryOrderService) {
		this.lotteryOrderService = lotteryOrderService;
	}
	
	
	
	
}
