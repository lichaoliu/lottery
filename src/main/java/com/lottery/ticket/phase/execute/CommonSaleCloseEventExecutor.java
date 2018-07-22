/**
 * 
 */
package com.lottery.ticket.phase.execute;

import com.lottery.common.contains.TopicName;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalStatus;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.thread.IScheduledTask;
import com.lottery.ticket.phase.thread.impl.CommonScheduledTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用彩期销售截止事件
 *
 */
public class CommonSaleCloseEventExecutor extends AbstractPhaseEventExecutor {

	
	@Override
	protected boolean execute(IScheduledTask itask) {
		if (itask == null) {
			logger.error("通用彩期销售截止事件调度任务不存在,本次调度失败");
			return false;
		}
		if (itask.getLotteryType() == null) {
			logger.error("通用彩期销售截止事件调度任务的彩票类型不存在,本次调度失败");
			return false;
		}
		CommonScheduledTask task = (CommonScheduledTask) itask;
		if (task.getCurrentPhaseNo() == null) {
			logger.error("通用彩期销售截止事件调度任务的当前彩期不存在,本次调度失败");
			return false;
		}
		
		LotteryType lotteryType = task.getLotteryType();
		String phaseNo = task.getCurrentPhaseNo();
		LotteryPhase specifyPhase=phaseService.getByTypeAndPhase(lotteryType.value, phaseNo);
		// 先读取指定的当前任务彩期
		
		if (specifyPhase == null) {
			logger.error("读取{}的执行任务彩期{}失败", new Object[] {lotteryType, phaseNo});
			
			return false;
		}
			
		if (specifyPhase.getTerminalStatus() == TerminalStatus.enable.value) {
			// 将指定彩种的指定彩期设置成禁止销售

			long waitTime = specifyPhase.getEndSaleTime().getTime() - System.currentTimeMillis();
			if (waitTime > 0) {
				Calendar cd=Calendar.getInstance();
				cd.add(Calendar.MILLISECOND,(int)waitTime);
				String dataStr= CoreDateUtils.formatDateTime(cd.getTime());
				logger.error("[{}]距下次事件({})执行时间为:{},等待:({})毫秒",lotteryType.getName(), "终端销售截止", dataStr,waitTime);
				try {
					synchronized (this) {
						this.wait(waitTime);
					}
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			}

			boolean updateResult = true;
			try {
				phaseService.updateTerminalStatus(TerminalStatus.close.value, specifyPhase.getId());
		        sendEndPhaseJms(lotteryType.value, phaseNo);
			} catch (Exception e) {
				updateResult=false;
			}
			return updateResult;
		} else {
			task.setDuplicate(true);
			sendEndPhaseJms(lotteryType.value, phaseNo);
			return true;
		}
	}

	protected void sendEndPhaseJms(int lotteryType,String phase){
		
		try {

			Map<String,Object> map=new HashMap<String,Object>();
			map.put("lotterytype", lotteryType);
			map.put("phase", phase);
			queueMessageSendService.sendMessage(TopicName.end_phase.value, map);
		} catch (Exception e) {
			logger.error("发送期结消息出错",e);
		}
	}
	 
}
