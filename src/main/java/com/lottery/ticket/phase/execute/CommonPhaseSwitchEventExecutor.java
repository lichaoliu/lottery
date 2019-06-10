/**
 * 
 */
package com.lottery.ticket.phase.execute;

import com.lottery.common.contains.TopicName;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseEventType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.TerminalStatus;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.thread.IScheduledTask;
import com.lottery.ticket.phase.thread.impl.CommonScheduledTask;

import java.util.*;

/**
 * 彩期切换事件
 * 
 */
public class CommonPhaseSwitchEventExecutor extends AbstractPhaseEventExecutor {

	protected ThreadLocal<LotteryPhase> currentLocal = new ThreadLocal<LotteryPhase>();


    private  PhaseNotEnoughCreateWorker phaseNotEnoughCreateWorker;

	@Override
	protected boolean execute(IScheduledTask itask) {
		if (itask.getLotteryType() == null) {
			logger.error("通用彩期切换事件调度任务的彩票类型不存在,本次调度失败");
			return false;
		}
		CommonScheduledTask task = (CommonScheduledTask) itask;
		if (task.getCurrentPhaseNo() == null) {
			logger.error("通用彩期切换事件调度任务的当前彩期不存在,本次调度失败");
			return false;
		}

		LotteryType lotteryType = itask.getLotteryType();

		// 先读取当前期
		LotteryPhase currentPhase = phaseService.getCurrent(lotteryType.value);
		if (currentPhase == null) {
			logger.error("读取{}当前期失败,无可用当前期", lotteryType);
			return false;
		}
		long offsetTime=(currentPhase.getEndTicketTime().getTime()-task.getOffsetTime())-System.currentTimeMillis();

		if(offsetTime>0){
			logger.error("彩种:{},期号:{},偏移量:{}有差异,请检查",lotteryType,currentPhase.getPhase(),offsetTime);
            if (phaseHandler != null) {
                phaseHandler.executeTaskLoad();
            }
			return false;
		}

		currentLocal.set(currentPhase);
		if (!currentPhase.getPhase().equals(task.getCurrentPhaseNo())) {
			// 当前期已经不是任务传递时的当前期，说明彩期已切换，为了避免重复切换，设置成重复事件
			task.setDuplicate(true);
			// 直接返回成功
			return true;
		}



		// 关闭当前彩期
		{
			boolean flag = true;
			try {
				phaseService.phaseSwitch(PhaseStatus.close.value, YesNoStatus.no.value, YesNoStatus.no.value,TerminalStatus.enable.value,currentPhase.getId());
			} catch (Exception e) {
				logger.error("({})关闭当前彩期({})失败", lotteryType, task.getCurrentPhaseNo(),e);
				flag = false;
			}

			if (!flag) {
				logger.error("({})关闭当前彩期({})失败", lotteryType, task.getCurrentPhaseNo());
				return false;
			}

		}

		//关闭后,创建下一期

		if (phaseNotEnoughCreateWorker!=null&&!LotteryType.getZc().contains(lotteryType)){
			phaseNotEnoughCreateWorker.create(lotteryType);
		}

		// 切换下一彩期
		{
			LotteryPhase nextPhase = null;
			try {
				List<LotteryPhase> nextPhaseList = phaseService.getNextPhase(currentPhase.getLotteryType(), currentPhase.getPhase(), 1);
				if (nextPhaseList != null && nextPhaseList.size() > 0) {
					nextPhase = nextPhaseList.get(0);
					Long id=nextPhase.getId();
					phaseService.phaseSwitch(PhaseStatus.open.value, YesNoStatus.yes.value, YesNoStatus.yes.value,TerminalStatus.enable.value,id);
					if (LotteryType.getZc().contains(lotteryType)) {
						nextPhase.setTerminalStatus(TerminalStatus.disenable.value);
						phaseService.updateTerminalStatus(TerminalStatus.disenable.value, id);
					}
					
				}

			} catch (Exception e) {
				nextPhase = null;
			}



			if (nextPhase == null) {
				logger.error("({})切换当前彩期({})下一期失败,原因:未找到下一期", lotteryType, task.getCurrentPhaseNo());
				return false;
			}




			// 发送新期jms
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("lotterytype", nextPhase.getLotteryType());
				map.put("phase", nextPhase.getPhase());
				getQueueMessageSendService().sendMessage(TopicName.open_phase.value, map);
			} catch (Exception e) {
				logger.error("发送新期队列出错", e);
			}
			logger.info("({})新的当前彩期为({})", lotteryType, nextPhase.getPhase());
		}

		// 通知彩期守护线程加载新一期的守护事件
		if (phaseHandler != null) {
			phaseHandler.executeTaskLoad();
		} else {
			logger.error("未找到{}的彩期守护线程", lotteryType.getValue());
		}
		return true;
	}

	@Override
	protected IScheduledTask generateScheduledTaskForNextEvent(IScheduledTask currentTask) {
		if (currentTask.isDuplicate()) {
			// 如果已重复，不做处理直接返回原任务
			return currentTask;
		}

		// 彩期切换，计算出给下一事件的任务，更新当前期
		CommonScheduledTask nextTask = new CommonScheduledTask();
		nextTask.setEventType(PhaseEventType.close);
		nextTask.setLotteryType(currentTask.getLotteryType());
		nextTask.setTaskTime(currentLocal.get().getEndSaleTime());
		nextTask.setCurrentPhaseNo(currentLocal.get().getPhase());
		nextTask.setOffsetTime(currentTask.getOffsetTime());
		logger.info("彩期切换,传递给下一事件的任务,{},phase={}", nextTask.getLotteryType(), nextTask.getCurrentPhaseNo());
		return nextTask;
	}


    public void setPhaseNotEnoughCreateWorker(PhaseNotEnoughCreateWorker phaseNotEnoughCreateWorker) {
        this.phaseNotEnoughCreateWorker = phaseNotEnoughCreateWorker;
    }
}
