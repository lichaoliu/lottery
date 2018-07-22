package com.lottery.ticket.phase.thread.impl;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseEventType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.TerminalStatus;
import com.lottery.common.thread.ThreadContainer;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.cache.model.LotteryTicketConfigCacheModel;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.ticket.phase.execute.CommonPhaseSwitchEventExecutor;
import com.lottery.ticket.phase.execute.CommonSaleCloseEventExecutor;
import com.lottery.ticket.phase.execute.PhaseNotEnoughCreateWorker;
import com.lottery.ticket.phase.thread.AbstractPhaseHandler;
import com.lottery.ticket.phase.thread.IPhaseEventExecutor;
import com.lottery.ticket.phase.thread.IScheduledTask;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 通用彩期守护线程
 * 
 * @author fengqinyun
 */
public class CommonPhaseHandler extends AbstractPhaseHandler {

	// private IWarningTool warningTool;

	private List<IScheduledTask> viewTaskList; // 供外部查看的任务列表
	@Autowired
	private LotteryPhaseService phaseService;
    @Autowired
    private PhaseNotEnoughCreateWorker phaseNotEnoughCreateWorker;
	@Autowired
	protected LotteryTicketConfigCacheModel lotteryTicketConfigCacheModel;
	// 正常运行时的任务列表
	protected List<IScheduledTask> tasks; // 要调度的任务列表

	protected List<IScheduledTask> reloadRecycleTasks; // 重载时保存未调度的任务列表,供回收使用,防止在reload过程中由于并发问题漏过事件执行

	protected Object _specify_phase_lock__ = new Object(); // 为了避免其他线程锁等待,为指定彩期事件定义专门的同步锁


	private long emptyTaskReloadInterval = 30000;// 没有任务时重载休眠时间
	private boolean emptyTaskReload = true; // 没有任务时自动重载

	@Override
	protected void init() {
		CommonSaleCloseEventExecutor closeEvent = new CommonSaleCloseEventExecutor();
		closeEvent.setPhaseService(phaseService);
		closeEvent.setQueueMessageSendService(queueMessageSendService);
		ThreadContainer closeContainer = new ThreadContainer(closeEvent, getLotteryType().getName() + "销售截止线程");
		closeContainer.setBeforeRunWaitTime(0);
		closeContainer.start();
		eventTypeMap.put(PhaseEventType.close, closeEvent);
		CommonPhaseSwitchEventExecutor swithEvent = new CommonPhaseSwitchEventExecutor();
		swithEvent.setPhaseHandler(this);
		swithEvent.setPhaseService(phaseService);
		swithEvent.setQueueMessageSendService(queueMessageSendService);
        swithEvent.setPhaseNotEnoughCreateWorker(phaseNotEnoughCreateWorker);
		ThreadContainer threadContainer = new ThreadContainer(swithEvent, getLotteryType().getName() + "新期切换线程");
		threadContainer.setBeforeRunWaitTime(0);
		threadContainer.start();
		eventTypeMap.put(PhaseEventType.swich, swithEvent);
		swithEvent.setNextEventExecuter(closeEvent);
	}



	/**
	 * 更新守护任务的供查询的事件任务列表，供外部接口或查询调用
	 */
	protected void initViewTaskListWithCurrent(IScheduledTask currentTask) {
		List<IScheduledTask> viewTaskList = new ArrayList<IScheduledTask>();

		if (currentTask != null) {
			// 优先添加当前任务
			viewTaskList.add(currentTask);
		}

		if (this.tasks != null && !this.tasks.isEmpty()) {
			viewTaskList.addAll(this.tasks);
		}

		List<IScheduledTask> _viewTaskList = Collections.unmodifiableList(viewTaskList);

		this.viewTaskList = _viewTaskList;
	}



	@Override
	protected void reload() {
		LotteryPhase currentPhase=null;
		while (running && (currentPhase = specifyCurrentPhase()) == null) {
			logger.error("彩种[{}]未找到当前期或设置当前期失败", getLotteryType().getName());
			try {
				synchronized (this) {
					this.wait(getFailoverInterval());
				}
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
			continue;
		}

		if (!running) {
			logger.error("({})守护线程退出", this.getLotteryType().getName());
			return;
		}

        if (currentPhase==null){
			logger.error("彩种[{}]当前期为空", getLotteryType().getName());
			return;
		}

		tasks = new ArrayList<IScheduledTask>();
		this.initViewTaskListWithCurrent(null);


		// 获取当前期的调度任务
		List<IScheduledTask> phaseTasks = computeScheduledTasks(currentPhase);
		if (phaseTasks != null && phaseTasks.size() > 0) {
			logger.info("找到当前期({})的调度任务，数量为({})", currentPhase, phaseTasks.size());
			tasks.addAll(phaseTasks);
		} else {
			logger.error("未找到当前期({})的调度任务", currentPhase.getPhase());
		}
		//loadSpecifyAndRecycleTask();// 对任务时间排序
		sortScheduledTasks(tasks);
		logger.error(tasksToString());

	}

	protected String tasksToString(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("彩种:").append(getLotteryType());
		buffer.append("加载的任务列表为:");
		if(tasks!=null&&!tasks.isEmpty()){
			buffer.append("[");
			for (IScheduledTask task:tasks){
				buffer.append("当前期:").append(task.getCurrentPhaseNo());
				buffer.append(",彩期事件:").append(task.getEventType().getName());
				buffer.append(",偏移量:").append(task.getOffsetTime());
                buffer.append(",时间:").append(CoreDateUtils.formatDateTime(task.getTaskTime()));
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(task.getTaskTime());
                if (task.getOffsetTime()>0){
                    calendar.add(Calendar.MILLISECOND, -1*(int)task.getOffsetTime());
                }
				buffer.append(",执行时间:").append(CoreDateUtils.formatDateTime(calendar.getTime()));
			}
			buffer.append("]");
		}else {
			buffer.append("空");
		}
		return  buffer.toString();
	}

	protected void loadSpecifyAndRecycleTask() {
		List<IScheduledTask> recycleComparedScheduledTaskList = new ArrayList<IScheduledTask>();

		// 回收reload之前记录的调度任务
		synchronized (_specify_phase_lock__){
			if (reloadRecycleTasks != null && !reloadRecycleTasks.isEmpty()) {
				// 把当前计算得出的任务 和 重载回收的任务和指定要执行的任务 取交集, 不在当前任务当中的添加到当前任务里
				recycleComparedScheduledTaskList.addAll(reloadRecycleTasks);

			}
		}


		if (recycleComparedScheduledTaskList != null && !recycleComparedScheduledTaskList.isEmpty()) {
			List<IScheduledTask> toRecycleTasks = findRecycleScheduledTasks(recycleComparedScheduledTaskList, tasks);
			if (toRecycleTasks != null && !toRecycleTasks.isEmpty()) {
				logger.error("重载时找到需要回收和指定要执行的调度任务,数量为({})", toRecycleTasks.size());
				// 进行二次处理
				toRecycleTasks = this.processRecycleScheduledTasks(toRecycleTasks);
				if (toRecycleTasks != null && !toRecycleTasks.isEmpty()) {
					logger.error("二次处理后找到需要回收和指定要执行的调度任务,数量为({})", toRecycleTasks.size());
					tasks.addAll(toRecycleTasks);
				}
			}
		}

		// 按照任务时间进行排序
		sortScheduledTasks(tasks);

	}

	/**
	 * 找到重载后相对于重载前丢失的彩期调度事件，也就是返回reloadRecycleTasks中有而currentHandlingTasks中没有的事件
	 * 
	 * @param reloadRecycleTasks
	 *            重载前记录的彩期调度事件
	 * @param currentHandlingTasks
	 *            重载后计算出的彩期调度事件
	 * @return
	 */
	protected List<IScheduledTask> findRecycleScheduledTasks(List<IScheduledTask> reloadRecycleTasks, List<IScheduledTask> currentHandlingTasks) {
		List<IScheduledTask> foundTasks = new ArrayList<IScheduledTask>();
		for (IScheduledTask recycleTask : reloadRecycleTasks) {
			boolean found = false;
			for (IScheduledTask handlingTask : currentHandlingTasks) {
				if (isEqualTask(recycleTask, handlingTask)) {
					found = true;
					break;
				}
			}
			if (!found) {
				foundTasks.add(recycleTask);
			}
		}
		return foundTasks;
	}

	protected boolean isEqualTask(IScheduledTask itask1, IScheduledTask itask2) {
		if (itask1.getEventType().getValue() == itask2.getEventType().getValue() && itask1.getLotteryType().getValue() == itask2.getLotteryType().getValue()) {
			CommonScheduledTask task1 = (CommonScheduledTask) itask1;
			CommonScheduledTask task2 = (CommonScheduledTask) itask2;

			if (task1.getCurrentPhaseNo().equals(task2.getCurrentPhaseNo())) {
				// 对于常规数字彩和高频彩,只要是相同期的相同事件即认为是同一事件
				return true;
			}
		}
		return false;
	}

	@Override
	protected void execute() {
		while (running) {

			while (paused) {
				logger.error("({})守护被暂停，等待线程恢复", this.getLotteryType());
				try {
					synchronized (this) {
						this.wait();
					}
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			}
			this.initViewTaskListWithCurrent(null);
			if (tasks == null || tasks.isEmpty()) {
				logger.info("没有要处理的任务");
				if (!emptyTaskReload) {
					logger.error("不需要自动重载，线程结束");
					running = false;
					continue;
				}
				logger.info("自动重载");
				try {
					synchronized (this) {
						this.wait(emptyTaskReloadInterval);
					}
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
				this.reload();
				reloading = false;
				continue;
			}

			IScheduledTask scheduledTask = tasks.remove(0);
			// 设置最近要调度的任务,以便查询彩期守护线程的状态
			logger.info("任务信息: {}", scheduledTask.getTaskInfo());
			Calendar calendar = Calendar.getInstance();
			// 加上时间偏移然后计算需要等待的事件执行时间
			long waitTime = (scheduledTask.getTaskTime().getTime() -scheduledTask.getOffsetTime()) - calendar.getTimeInMillis();
			if(!updateEndTicketTime(scheduledTask.getLotteryType().value,scheduledTask.getCurrentPhaseNo(),scheduledTask.getTaskTime(),scheduledTask.getOffsetTime())){
                logger.error("任务信息:{},设置期结时间失败",scheduledTask.getTaskInfo());
				continue;
			}

			if (waitTime > 0) {
				Calendar cd=Calendar.getInstance();
				cd.add(Calendar.MILLISECOND,(int)waitTime);
				String dataStr=CoreDateUtils.formatDateTime(cd.getTime());
				logger.error("[{}]下次事件({})执行时间为:{},需等待({})毫秒,进入等待", new Object[] { this.getLotteryType().getName(), scheduledTask.getEventType().getName(),dataStr,waitTime });
				try {
					synchronized (this) {
						this.wait(waitTime);
					}
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			}

			// 如果设置了暂停标记，继续wait直到收到恢复操作，其他操作也可能唤醒线程，所以需要用循环
			boolean awakeFromPaused = false;
			while (paused) {
				logger.error("当前彩期[{}]守护被暂停，不执行后续事件[{}]，等待线程恢复", scheduledTask.getCurrentPhaseNo(), scheduledTask.getTaskInfo());
				try {
					synchronized (this) {
						this.wait();
					}
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}

				// 从暂停恢复
				awakeFromPaused = true;
			}

			// 如果有设置停止和重载标记和任务清空标记，退出处理
			if (!running) {
				break;
			}

			// 如果从暂停中恢复，直接continue处理之前的事件
			if (awakeFromPaused) {
				this.reload();
				continue;
			}

			// 载入新一期的事件调度任务，然后继续处理当前任务
			if (taskLoading) {
				this.loadTask(scheduledTask);

				// 更新任务描述信息
				this.initViewTaskListWithCurrent(scheduledTask);

				taskLoading = false;
			}

			// wait时间到或唤醒后，判断是否有停止标记，如果有则退出
			if (!running) {
				break;
			}
			if (reloading) {
				// 如果是重载,记录当前守护中的调度事件供重载完成后回收使用,防止重载过程中丢失未执行的事件
				reloadRecycleTasks = new ArrayList<IScheduledTask>();
				// 添加当前守护的事件
				reloadRecycleTasks.add(scheduledTask);
				// 添加剩余未调度的事件
				reloadRecycleTasks.addAll(tasks);

				this.reload();
				reloading = false;
				continue;
			}
			// 清空守护事件
			if (taskClearing) {
				if (tasks != null) {
					tasks.clear();
					this.initViewTaskListWithCurrent(null);
				}

				// 清空重载记录中的守护事件，保证完整的清空
				if (reloadRecycleTasks != null) {
					reloadRecycleTasks.clear();
					reloadRecycleTasks = null;
				}

				// 直接进行下次循环，走正常彩期守护流程
				taskClearing = false;
				continue;
			}

			// 往事件执行线程添加任务
			IPhaseEventExecutor executor = eventTypeMap.get(scheduledTask.getEventType());
			executor.addScheduledTask(scheduledTask);

			// 唤醒事件执行
			executor.executeNotify();

			afterHandle(scheduledTask);

		}

		logger.info("({})守护线程退出", this.getLotteryType().getName());
		try {
			destroyInstance();
		} catch (Exception e) {
			logger.error("新期线程销毁失败", e);
		}
	}

	protected void afterHandle(IScheduledTask scheduledTask) {
		// 竞彩监控守护线程中的赛程使用
	}

	/**
	 * 销毁送票子线程
	 * 
	 * @throws Exception
	 */
	protected void destroyInstance() throws Exception {
		if (eventTypeMap != null) {
			Set<PhaseEventType> typeList = eventTypeMap.keySet();
			for (PhaseEventType eventType : typeList) {
				IPhaseEventExecutor phaseExecutor = eventTypeMap.get(eventType);
				phaseExecutor.executeStop();
			}
			eventTypeMap.clear();
		}
	}

	/**
	 * 按照任务时间从前到后进行排序
	 * 
	 * @param tasks
	 */
	protected void sortScheduledTasks(List<IScheduledTask> tasks) {
		if (tasks == null) {
			return;
		}
		Collections.sort(tasks, getScheduledTaskComparator());
	}

	protected Comparator<IScheduledTask> getScheduledTaskComparator() {
		return new Comparator<IScheduledTask>() {

			@Override
			public int compare(IScheduledTask o1, IScheduledTask o2) {
				if (o1.getTaskTime().before(o2.getTaskTime())) {
					return -1;
				}
				if (o1.getTaskTime().after(o2.getTaskTime())) {
					return 1;
				}
				return 0;
			}

		};
	}

	/**
	 * @param phase
	 * @return 指定彩期未过期的任务列表
	 */
	protected List<IScheduledTask> computeScheduledTasks(LotteryPhase phase) {
		List<IScheduledTask> taskList = new ArrayList<IScheduledTask>();

		// 为了避免彩期事件在计算过程中过期，使用统一的时间戳来限定
		long timestamp = System.currentTimeMillis();

		LotteryTicketConfig lotteryTicketConfig = null;
		try {
			lotteryTicketConfig = lotteryTicketConfigCacheModel.get(getLotteryType());
		} catch (Exception e) {

		}
		IScheduledTask scheduledTask = getScheduledTask(phase, lotteryTicketConfig, PhaseEventType.swich, timestamp);
		if (scheduledTask != null) {
			taskList.add(scheduledTask);
		}

//		 IScheduledTask closeScheduledTask = getScheduledTask(phase, lotteryTicketConfig,PhaseEventType.close, timestamp);
//		if (scheduledTask != null) {
//			taskList.add(closeScheduledTask);
//		}

		return taskList;
	}

	protected IScheduledTask getScheduledTask(LotteryPhase phase, LotteryTicketConfig config, PhaseEventType eventType, long timestamp) {
		return getScheduledTask(phase, config, eventType, timestamp, false);
	}

	protected IScheduledTask getScheduledTask(LotteryPhase phase, LotteryTicketConfig config, PhaseEventType eventType, long timestamp, boolean forceLoadingFlag) {
		try {

			CommonScheduledTask task = new CommonScheduledTask();
			task.setCurrentPhaseNo(phase.getPhase());
			task.setEventType(eventType);
			task.setLotteryType(getLotteryType());
			task.setTaskTime(phase.getEndSaleTime());
			task.setOffsetTime(0);
			if (PhaseEventType.swich == eventType) {
				if (config != null && config.getEndSaleForward() != null) {
					task.setOffsetTime(config.getEndSaleForward());
					task.setTaskTime(phase.getEndSaleTime());
				}else{
					//task.setOffsetTime(100);
					task.setTaskTime(phase.getEndSaleTime());
				}
			}
			if (PhaseEventType.close == eventType){
				task.setTaskTime(phase.getEndSaleTime());
			}


			return task;
		} catch (Exception e) {
			logger.error("计算{}期的{}任务执行时间出错", phase.getPhase(), eventType.getName());
			return null;
		}
	}

	/**
	 * 设置当前彩期
	 * 
	 * @return 当前期期数，如果未找到当前期，返回null
	 */
	protected LotteryPhase specifyCurrentPhase() {

		LotteryPhase lotteryPhase=null;
		// 查找已设置的当前期
		try {
			lotteryPhase=phaseService.getCurrent(this.getLotteryType().value);
		} catch (Exception e) {

		}

		if(lotteryPhase!=null){
			return lotteryPhase;
		}

		//防止有多个当前期
		try{
			phaseService.closeCurrent(this.getLotteryType().getValue());
		}catch (Exception e){
			logger.error("关闭所有当前期失败",e);
		}


		//将已开售的第一个作为当前期
		try{
			List<LotteryPhase> lotteryPhaseList=phaseService.getByLotteryTypeAndStatus(getLotteryType().value, PhaseStatus.open.value);
			if(lotteryPhaseList!=null&&lotteryPhaseList.size()>0){
				lotteryPhase=lotteryPhaseList.get(0);
				return lotteryPhase;
			}

		}catch (Exception e){
			logger.error("或取所有开售期失败",e);
		}

		//获取所有未开售期

		try{
			List<LotteryPhase> lotteryPhaseList=phaseService.getByLotteryTypeAndStatus(getLotteryType().value, PhaseStatus.unopen.value);
			if(lotteryPhaseList!=null&&lotteryPhaseList.size()>0){
				lotteryPhase=lotteryPhaseList.get(0);
				Long id=lotteryPhase.getId();
				phaseService.phaseSwitch(PhaseStatus.open.value, YesNoStatus.yes.value, YesNoStatus.yes.value,TerminalStatus.enable.value,id);
				if (LotteryType.getZc().contains(getLotteryType())) {
					lotteryPhase.setTerminalStatus(TerminalStatus.disenable.value);
					phaseService.updateTerminalStatus(TerminalStatus.disenable.value, id);
				} else {
					lotteryPhase.setTerminalStatus(TerminalStatus.enable.value);
				}
				return lotteryPhase;
			}

		}catch (Exception e){
			logger.error("或取所有未售期失败",e);
		}


		return lotteryPhase;

	}



	private boolean updateEndTicketTime(int lotteryType,String phase,Date endSaleTime,long offsetTime){
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endSaleTime);
			calendar.add(Calendar.MILLISECOND,(-1)*(int)offsetTime);
			phaseService.updateEndTicketTime(lotteryType, phase, calendar.getTime());
			return true;
		}catch (Exception e){
			logger.error("修改当前期失败",e);
			return false;
		}
	}

	@Override
	public String executeQueryTaskInfo() {
		logger.info("查询彩期守护线程任务信息");
		StringBuffer sb = new StringBuffer();
		sb.append("彩种:[");
		sb.append(getLotteryType().getName());
		sb.append("]");
		sb.append("运行状态:");
		if (running) {
			if (paused) {
				sb.append("已暂停");
			} else {
				sb.append("运行中");
			}
		} else {
			sb.append("已退出");
		}
		sb.append("<br/>");

		List<IScheduledTask> viewTaskList = this.getScheduledTaskList();

		if (viewTaskList == null || viewTaskList.isEmpty()) {
			sb.append("没有待执行的彩期事件");
		} else {
			for (IScheduledTask scheduledTask : viewTaskList) {
				sb.append(scheduledTask.getTaskInfo()).append("<br />");
			}
		}
		return sb.toString();
	}

	@Override
	public String executeSpecifyPhaseEvent(LotteryPhase phase, PhaseEventType phaseEventType, List<String> races) {
		return null;
	}

	// 指定当前彩种的彩期事件任务的清空
	public String executeSpecifyPhaseEventClear(PhaseEventType phaseEventType) {

		// 根据指定彩期事件获取事件执行器
		IPhaseEventExecutor executor = eventTypeMap.get(phaseEventType);
		// 返回真，表示清空成功，返回假表示任务为空
		if (executor.removeScheduledTask(this.getLotteryType(), phaseEventType)) {
			return String.format("指定要执行的彩种: %s的彩期事件：%s清除任务成功", this.getLotteryType().getName(), phaseEventType.getName());
		} else {
			return String.format("指定要执行的彩种: %s的彩期事件：%s任务为空", this.getLotteryType().getName(), phaseEventType.getName());
		}
	}

	protected List<IScheduledTask> processRecycleScheduledTasks(List<IScheduledTask> toRecycleTasks) {
		return toRecycleTasks;
	}

	protected void loadTask(IScheduledTask task) {

		LotteryPhase currentPhaseNew = phaseService.getCurrent(this.getLotteryType().value);
		if (currentPhaseNew != null) {
			if (!task.getCurrentPhaseNo().equals(currentPhaseNew.getPhase())) {

				logger.info("彩期已切换，取到新的当前期({})", currentPhaseNew.getPhase());
				List<IScheduledTask> phaseTasks = computeScheduledTasks(currentPhaseNew);
				if (phaseTasks != null && phaseTasks.size() > 0) {
					logger.info("找到当前期({})的调度任务，数量为({})", currentPhaseNew, phaseTasks.size());
					tasks.addAll(phaseTasks);

					// 按照任务时间进行排序
					sortScheduledTasks(tasks);
				} else {
					logger.error("未找到当前期({})的调度任务", currentPhaseNew.getPhase());
				}
			} else {
				logger.info("进入{}新期({})彩期任务加载，和原彩期一致", this.getLotteryType(), currentPhaseNew.getPhase());
			}
		} else {
			logger.error("未找到彩种{}的新的当前期", this.getLotteryType().getName());
		}
	}

	@Override
	public boolean hasPhaseEvent(PhaseEventType phaseEventType) {

		return false;
	}

	@Override
	public List<IScheduledTask> getScheduledTaskList() {
		return this.viewTaskList;
	}
	@Override
	 protected boolean isStopSale(LotteryType lotteryType){
		if(terminalSelector.isSaleEnabledForLotteryType(lotteryType)){
			return false;
		}
		 return true;
	 }
	

}
