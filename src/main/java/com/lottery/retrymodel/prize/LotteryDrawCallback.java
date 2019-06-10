package com.lottery.retrymodel.prize;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.draw.prize.CommonLotteryDrawTask;
import com.lottery.draw.prize.executor.ILotteryDrawExecutor;
import com.lottery.retrymodel.ApiRetryCallback;
import org.apache.commons.lang3.StringUtils;

/**
 * 异步开奖 
 * */
public class LotteryDrawCallback extends ApiRetryCallback<Object> {
	protected ILotteryDrawExecutor lotteryDrawExecutor;
	

	protected Integer lotteryType;
	
	protected String phase;
	
	protected String wincode;
	
	protected Long lastMatchNum;

	
	@Override
	protected Boolean execute() throws Exception {
		if (lotteryDrawExecutor == null || phase == null || lotteryType == null) {
			throw new LotteryException("参数不全");
		}

		if(!(LotteryType.getJcValue().contains(lotteryType)||LotteryType.getDcValue().contains(lotteryType))){
           if(StringUtils.isEmpty(wincode)){
			   throw new LotteryException("彩种:"+lotteryType+",开奖号码不能为空");
		   }
		}

		try{
			CommonLotteryDrawTask lotteryDrawTask = new CommonLotteryDrawTask();
			lotteryDrawTask.setLotteryType(lotteryType);
			lotteryDrawTask.setPhase(phase);
			lotteryDrawTask.setWinCode(wincode);
			lotteryDrawTask.setLastMatchNum(lastMatchNum);
			lotteryDrawExecutor.execute(lotteryDrawTask);
		}catch(Exception e){
			logger.error("彩种:{},期号:{},最大场次:{},算奖失败原因",lotteryType,phase,lastMatchNum);
			logger.error(e.getMessage(),e);
			return false;
		}

		return true;
	}


	

	public ILotteryDrawExecutor getLotteryDrawExecutor() {
		return lotteryDrawExecutor;
	}

	public void setLotteryDrawExecutor(ILotteryDrawExecutor lotteryDrawExecutor) {
		this.lotteryDrawExecutor = lotteryDrawExecutor;
	}

	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}



	public String getPhase() {
		return phase;
	}



	public void setPhase(String phase) {
		this.phase = phase;
	}



	public String getWincode() {
		return wincode;
	}



	public void setWincode(String wincode) {
		this.wincode = wincode;
	}



	public Long getLastMatchNum() {
		return lastMatchNum;
	}



	public void setLastMatchNum(Long lastMatchNum) {
		this.lastMatchNum = lastMatchNum;
	}

	
	
	
}
