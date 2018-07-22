package com.lottery.controller;

import com.lottery.common.PageBean;
import com.lottery.common.ResponseData;
import com.lottery.common.cache.CacheService;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.lottery.*;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.thread.IThreadRunnable;
import com.lottery.common.thread.ThreadContainer;
import com.lottery.common.util.StringUtil;
import com.lottery.core.camelconfig.ApplicationContextUtil;
import com.lottery.core.domain.terminal.TerminalConfig;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.handler.PrizeErrorHandler;
import com.lottery.core.handler.PrizeHandler;
import com.lottery.core.service.*;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.retrymodel.ApiRetryTaskExecutor;
import com.lottery.retrymodel.prize.error.PrizeErrorRetryCallback;
import com.lottery.ticket.allotter.thread.AbstractTicketAllotterRunnable;
import com.lottery.ticket.checker.thread.AbstractVenderTicketCheckerRunnable;
import com.lottery.ticket.phase.thread.AbstractPhaseHandler;
import com.lottery.ticket.phase.thread.AsyncZcPhaseRunnable;
import com.lottery.ticket.phase.thread.IPhaseHandler;
import com.lottery.ticket.phase.thread.ZcRaceResultRunnable;
import com.lottery.ticket.sender.worker.thread.AbstractTicketSenderRunnable;
import com.lottery.ticket.sender.worker.thread.ITicketSenderRunnable;
import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/system")
public class SystemController {

	protected static List<Integer> orderStatusList;
	protected static List<Integer> orderResultStatusList;
	static {
		orderStatusList=new ArrayList<Integer>();
		orderStatusList.add(OrderStatus.PRINTED.value);
		orderStatusList.add(OrderStatus.HALF_PRINTED.value);

		orderResultStatusList=new ArrayList<Integer>();
		orderResultStatusList.add(OrderResultStatus.not_win.value);
		orderResultStatusList.add(OrderResultStatus.win_already.value);
		orderResultStatusList.add(OrderResultStatus.win_big.value);
	}

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	private PrizeService prizeService;
	@Autowired
	private PrizeErrorHandler errorHandler;
	@Autowired
	private SystemService systemService;

	@Resource(name = "apiRetryTaskExecutor")
	protected ApiRetryTaskExecutor apiReryTaskExecutor;
	@Autowired
	private LotteryOrderService lotteryOrderService;

	@Autowired
	private ApplicationContextUtil applicationContextUtil;
	@Autowired
	private QueueMessageSendService queueMessageSendService;

	@Autowired
	private TicketService ticketService;

	@Autowired
	protected TerminalConfigService terminalconfigService;
	@Autowired
	protected CacheService cacheService;
    @Autowired
	protected PrizeHandler prizeHandler;
	@Autowired
	protected LotteryPhaseService lotteryPhaseService;
	@Resource(name = "allPhaseHandlerBinder")
	protected Map<LotteryType, IPhaseHandler> phaseHandle;

	// 保存开奖号码并开奖
	@RequestMapping(value = "/openPrize", method = RequestMethod.POST)
	public @ResponseBody ResponseData openPrize(@RequestParam(value = "lotterytype", required = true) int lotterytype, @RequestParam(value = "phase", required = true) String phase, @RequestParam(value = "wincode", required = true) String wincode) {
		ResponseData rd = new ResponseData();
		try {
			prizeHandler.prizeOpen(lotterytype, phase, wincode);
			
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}

		return rd;
	}

	// 保存开奖号码
	@RequestMapping(value = "/savePrize", method = RequestMethod.POST)
	public @ResponseBody ResponseData savePrize(@RequestParam(value = "lotterytype", required = true) String lotterytype, @RequestParam(value = "phase", required = true) String phase, @RequestParam(value = "wincode", required = true) String wincode) {
		ResponseData rd = new ResponseData();
		try {
			prizeService.savePrize(lotterytype, phase, wincode);
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 订单手动撤单
	 * 
	 * @param orderid
	 *            订单id
	 * @return
	 */
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
	public @ResponseBody ResponseData cancelOrder(@RequestParam(value = "orderid", required = true) String orderid) {
		ResponseData rd = new ResponseData();
		try {
			errorHandler.cancelOrder(orderid);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 票手动撤单
	 * 
	 * @param ticketid
	 *            票id
	 * @return
	 */
	@RequestMapping(value = "/cancelTicket", method = RequestMethod.POST)
	public @ResponseBody ResponseData cancelTicket(@RequestParam(value = "ticketId", required = true) String ticketid) {
		ResponseData rd = new ResponseData();
		try {
			Ticket ticket = ticketService.getTicket(ticketid);
			if (ticket == null) {
				logger.error("票id={}不存在", ticketid);
				rd.setErrorCode(ErrorCode.no_exits.getValue());
			} else {
				if (ticket.getStatus() == TicketStatus.PRINT_SUCCESS.getValue() || ticket.getStatus() == TicketStatus.PRINT_FAILURE.getValue() || ticket.getStatus() == TicketStatus.CANCELLED.getValue()) {
					logger.error("票id={}状态为:{},属于完结状态,不能进行撤单", new Object[] { ticketid, ticket.getStatus() });
				} else {
					ticket.setStatus(TicketStatus.CANCELLED.getValue());
					ticketService.update(ticket);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("orderId", ticket.getOrderId());
					queueMessageSendService.sendMessage(QueueName.betChercher, map);
					rd.setErrorCode(ErrorCode.Success.getValue());
					logger.error("票id={}手动撤单成功", ticketid);
				}

			}

		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/***
	 * 停止某个线程
	 * 
	 * @param type
	 *            线程类型
	 *            all 全部线程
	 *            check 查票线程 
	 *            phase 新期线程 
	 *            allot 分票线程 
	 *            send 送票线程
	 * */
	@RequestMapping(value = "/stopThread", method = RequestMethod.POST)
	public @ResponseBody ResponseData stopThread(@RequestParam(value = "type", required = true) String type) {

		ResponseData rd = new ResponseData();
		try {
			StringBuffer sb = new StringBuffer();
			Map<String, ThreadContainer> map = systemService.getMap(ThreadContainer.class);
			for (Map.Entry<String, ThreadContainer> entry : map.entrySet()) {
				String key = entry.getKey();
				ThreadContainer value = entry.getValue();
				IThreadRunnable run = value.getRunnable();
				if (isflagParent(type, run)) {
					logger.error("新期线程容器:{},状态是：{}", new Object[] { key, value.isRunning() });
					if (value.isRunning()) {
						value.stop();
					}
					sb.append(value.getName()).append(":").append(value.isRunning()).append("<br/>");
				}
			}
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(sb.toString());
		} catch (Exception e) {
			rd.setErrorCode(e.getMessage());
		}
		return rd;
	}

	/***
	 * 开启某个线程
	 * 
	 * @param type
	 *            线程类型 
	 *            all 全部线程
	 *            check 查票线程 
	 *            phase 新期线程
	 *            allot 分票线程
	 *            send 送票线程
	 * */
	@RequestMapping(value = "/startThread")
	public @ResponseBody ResponseData startThread(@RequestParam(value = "type", required = true) String type, @RequestParam(value = "isRun", required = false, defaultValue = "") String isRun) {
		ResponseData rd = new ResponseData();
		try {
			
			StringBuffer sb = new StringBuffer();
			Map<String, ThreadContainer> map = systemService.getMap(ThreadContainer.class);
			for (Map.Entry<String, ThreadContainer> entry : map.entrySet()) {
				String key = entry.getKey();
				ThreadContainer value = entry.getValue();
				IThreadRunnable run = value.getRunnable();
				{
					if (isflagParent(type, run)) {
						if (StringUtils.isNotBlank(isRun)) {
							sb.append(value.getName()).append("<br/>").append("状态:").append(value.isRunning()).append("<br/>");
						} else {
							if (!run.isRunning()) {
								value.setBeforeRunWaitTime(0);
								value.start();
								logger.error("线程容器{}开启", key);
							}
							sb.append(value.getName()).append(":").append(value.isRunning()).append("<br/>");
						}

					}
				}
			}
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(sb.toString());
		} catch (Exception e) {
			rd.setErrorCode(e.getMessage());
		}
		return rd;
	}

	/***
	 * 彩期守护操作
	 * 
	 * @param lotteryType 彩种
	 * @param type
	 *            check 检查 
	 *            pause 暂停
	 *            resume 恢复
	 *            reload 重载
	 * */
	@RequestMapping(value = "/processPhase")
	public @ResponseBody ResponseData processPhase(@RequestParam(value = "type", required = true) String type, @RequestParam(value = "lotterytype", required = true) int lotteryType) {
		ResponseData rd = new ResponseData();
		try {
			logger.error("彩期守护{},{}",lotteryType,type);
			LotteryType lotteryType2 = LotteryType.get(lotteryType);
			if (lotteryType2==LotteryType.ALL){

				if (type.equals("start")){
					for (IPhaseHandler phaseHandler:phaseHandle.values()){
						phaseHandler.executeNotify();
					}
				}
				if (type.equals("pause")){
					for (IPhaseHandler phaseHandler:phaseHandle.values()){
						phaseHandler.executePause();
					}
				}
				if (type.equals("resume")){
					for (IPhaseHandler phaseHandler:phaseHandle.values()){
						phaseHandler.executeResume();
					}
				}
				if (type.equals("reload")){
					for (IPhaseHandler phaseHandler:phaseHandle.values()){
						phaseHandler.executeReload();
					}
				}

				StringBuffer stringBuffer=new StringBuffer();
				for (IPhaseHandler phaseHandler:phaseHandle.values()){
					stringBuffer.append(phaseHandler.executeQueryTaskInfo());
					stringBuffer.append("</br>");
				}
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(stringBuffer.toString());
			}else{
				IPhaseHandler container = phaseHandle.get(lotteryType2);
				if (container == null) {
					logger.error("彩种:{}的线程容器不存在", new Object[] { lotteryType2 });
					rd.setErrorCode(ErrorCode.no_exits.getValue());
					rd.setValue("线程容器不存在");
				} else {
					if (type.equals("start")) {
						container.executeNotify();
					}
					if (type.equals("pause")) {
						container.executePause();
					}
					if (type.equals("resume")) {
						container.executeResume();
					}
					if (type.equals("reload")) {
						container.executeReload();
					}
					rd.setErrorCode(ErrorCode.Success.getValue());
					rd.setValue(container.executeQueryTaskInfo());
				}
			}


		} catch (Exception e) {
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	@RequestMapping(value = "/flushAll")
	public @ResponseBody ResponseData flush() {
		ResponseData rd = new ResponseData();
		try {
			cacheService.flushAll();
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (Exception e) {
			logger.error("刷新缓存失败", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}

		return rd;
	}

	/**
	 * 查询所有context
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllContext", method = RequestMethod.POST)
	public @ResponseBody ResponseData getAllContext() {
		ResponseData rd = new ResponseData();
		try {
			rd.setValue(applicationContextUtil.getMap().keySet());
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 查询所有context
	 * 
	 * @return
	 */
	@RequestMapping(value = "/stopAllContext", method = RequestMethod.POST)
	public @ResponseBody ResponseData stopAllContext() {
		logger.error("开始停止所有camel context");
		ResponseData rd = new ResponseData();
		try {
			Set<String> contexts = applicationContextUtil.getMap().keySet();
			for (String context : contexts) {
				logger.error("开始停止context name={}", context);
				applicationContextUtil.stop(context);
				logger.error("停止成功context name={}", context);
			}
			logger.error("停止所有camel context完成");
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	@RequestMapping(value = "/startAllContext", method = RequestMethod.POST)
	public @ResponseBody ResponseData startAllContext() {
		logger.error("开始开启所有camel context");
		ResponseData rd = new ResponseData();
		try {
			Set<String> contexts = applicationContextUtil.getMap().keySet();
			for (String context : contexts) {
				logger.error("开始开启context name={}", context);
				applicationContextUtil.start(context);
				logger.error("开启成功context name={}", context);
			}
			logger.error("开启所有camel context完成");
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	@RequestMapping(value = "/getAllRoutes", method = RequestMethod.POST)
	public @ResponseBody ResponseData getAllRoutes(@RequestParam(value = "contextname", required = true) String contextname) {
		ResponseData rd = new ResponseData();
		try {
			rd.setValue(applicationContextUtil.getRoutes(contextname));
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	@RequestMapping(value = "/stopContext", method = RequestMethod.POST)
	public @ResponseBody ResponseData stopContext(@RequestParam(value = "contextname", required = true) String contextname) {
		ResponseData rd = new ResponseData();
		logger.error("开始停止camelcontext name={}", contextname);
		try {
			applicationContextUtil.stop(contextname);
			logger.error("停止camelcontext name={}成功", contextname);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	@RequestMapping(value = "/startContext", method = RequestMethod.POST)
	public @ResponseBody ResponseData startContext(@RequestParam(value = "contextname", required = true) String contextname) {
		ResponseData rd = new ResponseData();
		try {
			logger.error("开始启动camelcontext name={}", contextname);
			applicationContextUtil.start(contextname);
			logger.error("启动成功camelcontext name={}", contextname);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	@RequestMapping(value = "/stopRoute", method = RequestMethod.POST)
	public @ResponseBody ResponseData stopRoute(@RequestParam(value = "contextname", required = true) String contextname, @RequestParam(value = "routeid", required = true) String routeid) {
		ResponseData rd = new ResponseData();
		try {
			logger.error("开始停止camelcontext route name={} route={}", new Object[] { contextname, routeid });
			applicationContextUtil.stopRoute(contextname, routeid);
			logger.error("停止成功camelcontext route name={} route={}", new Object[] { contextname, routeid });
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	@RequestMapping(value = "/allRoute", method = RequestMethod.POST)
	public @ResponseBody ResponseData allRoute() {
		ResponseData rd = new ResponseData();
		try {
			StringBuffer sb = new StringBuffer();
			Map<String, CamelContext> map = systemService.getMap(CamelContext.class);
			for (Map.Entry<String, CamelContext> entry : map.entrySet()) {
				String key = entry.getKey();
				CamelContext value = entry.getValue();
                sb.append("key=").append(key);
				sb.append(",value=").append(value);
				sb.append(",isauto=").append(value.isAutoStartup());
				sb.append(",isstarting=").append(value.isStartingRoutes());
				List<Route> routes=value.getRoutes();
				for(Route route:routes){
					sb.append(",route=").append(route.getId());
				}
                sb.append("|");

			}
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(sb.toString());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	@RequestMapping(value = "/startRoute", method = RequestMethod.POST)
	public @ResponseBody ResponseData startRoute(@RequestParam(value = "contextname", required = true) String contextname, @RequestParam(value = "routeid", required = true) String routeid) {
		ResponseData rd = new ResponseData();
		try {
			logger.error("开始启动camelcontext route name={} route={}", new Object[] { contextname, routeid });
			applicationContextUtil.startRoute(contextname, routeid);
			logger.error("启动成功camelcontext route name={} route={}", new Object[] { contextname, routeid });
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 普通彩种按照彩种期号修改订单，账务信息，为重新算奖做准备
	 * 
	 * @param  lotterytype 彩种
	 * @param  phase 期号
	 * */
	@RequestMapping(value = "/prizeAgain", method = RequestMethod.POST)
	public @ResponseBody ResponseData prizeAgain(@RequestParam(value = "lotterytype", required = true) int lotterytype,
			@RequestParam(value = "phase", required = true) String phase
			) {
		ResponseData rd = new ResponseData();
		try {
			logger.error("prize agein start lotteryType={},phase={}", new Object[]{lotterytype, phase});
			PrizeErrorRetryCallback callBack = new PrizeErrorRetryCallback();
			callBack.setLotteryOrderService(lotteryOrderService);
			callBack.setLotteryType(lotterytype);
			callBack.setPhase(phase);
			callBack.setIdex(2);
            callBack.setPrizeErrorHandler(errorHandler);
			callBack.setName("按期重新算奖");
			callBack.setLotteryPhaseService(lotteryPhaseService);
			callBack.setRetry(1);
			callBack.setTicketService(ticketService);
			apiReryTaskExecutor.invokeAsync(callBack);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
			logger.error("重新算奖出错", e);
		}
		return rd;
	}

	/**
	 * 竞技彩按照彩种期号修改订单，账务信息，为重新算奖做准备
	 *
	 * @param  lotterytype 彩种
	 * @param  phase 期号
	 * @param  matchNum 场次
	 * */
	@RequestMapping(value = "/prizejingjicaiAgain", method = RequestMethod.POST)
	public @ResponseBody ResponseData prizejingjicaiAgain(@RequestParam(value = "lotterytype", required = true) int lotterytype,
												 @RequestParam(value = "phase", required = false) String phase,@RequestParam(value = "matchNum", required = false) String matchNum,
														  @RequestParam(value = "startDate", required = true) Date startDate, @RequestParam(value = "endDate", required = true) Date endDate
	) {
		ResponseData rd = new ResponseData();
		try {

			PrizeErrorRetryCallback callBack = new PrizeErrorRetryCallback();
			callBack.setLotteryOrderService(lotteryOrderService);
			callBack.setLotteryType(lotterytype);
			callBack.setPhase(phase);
			callBack.setTicketService(ticketService);
			callBack.setPrizeErrorHandler(errorHandler);
			callBack.setName("竞技彩重新算奖");
			callBack.setLotteryPhaseService(lotteryPhaseService);
			callBack.setRetry(1);
			callBack.setMatchNum(matchNum);
			callBack.setStartDate(startDate);
			callBack.setEndDate(endDate);
			apiReryTaskExecutor.invokeAsync(callBack);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
			logger.error("重新算奖出错", e);
		}
		return rd;
	}
	/**
	 * 检查竞技彩是否重置有已算奖
	 *
	 * @param  lotterytype 彩种
	 * @param  phase 期号
	 * @param  matchNum 场次
	 * */
	@RequestMapping(value = "/prizejingjicaiAgainCheck", method = RequestMethod.POST)
	public @ResponseBody ResponseData prizejingjicaiAgainCheck(@RequestParam(value = "lotterytype", required = true) int lotterytype,
														  @RequestParam(value = "phase", required = false) String phase,@RequestParam(value = "matchNum", required = false) String matchNum,
														  @RequestParam(value = "startDate", required = true) Date startDate, @RequestParam(value = "endDate", required = true) Date endDate
	) {
		ResponseData rd = new ResponseData();
		try {
			PageBean<LotteryOrder> pageBean=new PageBean<LotteryOrder>();
			pageBean.setPageFlag(false);
			pageBean.setMaxResult(1);
			pageBean.setPageFlag(false);
			pageBean.setTotalFlag(false);
			boolean flag=true;
			for (Integer lottype:LotteryType.getLotteryTypeList(lotterytype)){
				List<LotteryOrder> orders=lotteryOrderService.getByLotteryStatusMatchNumAndDate(lottype,phase,orderStatusList,orderResultStatusList,startDate,endDate,matchNum,pageBean);
				if (orders!=null&&orders.size()>0){
					flag=false;
					break;
				}
			}
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(flag);
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
			logger.error("重新算奖查询出错", e);
		}
		return rd;
	}
	/**
	 * 根据期号，彩种检查重置是否完成
	 * 
	 * @param lotterytype 彩种
	 * @param phase 期号
	 * */
	@RequestMapping(value = "/prizeAgainCheck", method = RequestMethod.POST)
	public @ResponseBody ResponseData prizeAgainCheck(@RequestParam(value = "lotterytype", required = true) int lotterytype, 
			@RequestParam(value = "phase", required = true) String phase) {
		ResponseData rd = new ResponseData();
		try {
			boolean flag=true;
			rd.setErrorCode(ErrorCode.Success.getValue());
			for (Integer lottype:LotteryType.getLotteryTypeList(lotterytype)){
				List<LotteryOrder>	list=lotteryOrderService.getByLotteryPhaseAndStatus(lottype, phase, orderStatusList, orderResultStatusList, 1);
				if (list!=null&&list.size()>0){
					flag=false;
					break;
				}
			}


			rd.setValue(flag);
			
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
			logger.error("lotteryType={},phase={},orderstatus={},resultstatus={}",lotterytype,phase,orderStatusList,orderResultStatusList);
			logger.error("检查是否重置失败", e);
		}
		return rd;
	}




	/**
	 * 检查订单重新算奖是否重置成功
	 * 
	 * @param orderid 订单号

	 * */
	@RequestMapping(value = "/prizeAgainOrderCheck", method = RequestMethod.POST)
	public @ResponseBody ResponseData prizeAgainOrderCheck(@RequestParam(value = "orderid", required = true) String orderid) {
		ResponseData rd = new ResponseData();
		try {
			LotteryOrder lotteryOrder = lotteryOrderService.get(orderid);
			if (lotteryOrder != null) {
				rd.setValue(lotteryOrder.getOrderResultStatus());
			}
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	@RequestMapping(value = "/prizeAgainOrder", method = RequestMethod.POST)
	public @ResponseBody ResponseData prizeAgainOrder(@RequestParam(value = "orderid", required = true) String orderid) {
		ResponseData rd = new ResponseData();
		try {
			logger.error("订单重新算奖开始,订单号{}", orderid);
			errorHandler.prizeRecovery(orderid);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 手动发送jms消息
	 *
	 * @param quename
	 *            jms 名字 QueueName
	 * @param key
	 *            jms 键值
	 * @param value
	 *            jms 值
	 * */
	@RequestMapping(value = "/sendJmsAgain", method = RequestMethod.POST)
	public @ResponseBody ResponseData sendJMSAgain(@RequestParam(value = "name", required = true) String quename, @RequestParam(value = "key", required = true) String key, @RequestParam(value = "value", required = true) Object value) {
		ResponseData rd = new ResponseData();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(key, value);
			queueMessageSendService.sendMessage(quename, map);
			logger.error("发送队列名称:{},队列内容,键:{},值:{}", new Object[]{quename, key, value});
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 票转换终端
	 * @param ticketId
	 *            票编号
	 * @param terminalId
	 *            终端号
	 * */
	@RequestMapping(value = "/changeTerminal", method = RequestMethod.POST)
	public @ResponseBody ResponseData changeTerminal(@RequestParam(value = "terminalId", required = true) Long terminalId, @RequestParam(value = "ticketId", required = true) String ticketId) {
		ResponseData rd = new ResponseData();
		try {
			logger.error("票:{},重新转换terminalId:{}",ticketId,terminalId);
			String[] ticketIds = ticketId.split(",");
			int suc = 0;
			int fail = 0;
			for(String tid : ticketIds){
				if(errorHandler.changeTerminal(tid,terminalId)){
					suc++;
				}else{
					fail++;
				}
			}
			rd.setValue("成功:"+suc+",失败:"+fail);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 根据条件，转换票id
	 * 
	 * @param lotteryType 
	 *            彩种类型
	 * @param fromId
	 *            需要转换的终端
	 * @param toId
	 *            转换的终端
	 * */
	@RequestMapping(value = "/changeTerminalId", method = RequestMethod.POST)
	public @ResponseBody ResponseData changeTerminalId(@RequestParam(value = "lotteryType", required = true) int lotteryType, 
			@RequestParam(value = "fromId", required = true) Long fromId, @RequestParam(value = "toId", required =true) Long toId,
			@RequestParam(value = "minute", required =false,defaultValue="0") int  minute
			) {
		ResponseData rd = new ResponseData();
		try {
			errorHandler.changeTerminalId(fromId, toId, lotteryType,minute);
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue("success");
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 批次转换终端
	 * 
	 * @param terminalType
	 *            终端类型
	 * @param batchId
	 *            批次编号
	 * */
	@RequestMapping(value = "/changeBatch", method = RequestMethod.POST)
	public @ResponseBody ResponseData changeBatch(@RequestParam(value = "terminalType", required = true) int terminalType, @RequestParam(value = "batchId", required = true) String batchId) {
		ResponseData rd = new ResponseData();
		try {

			TerminalType type = TerminalType.get(terminalType);
			logger.error("终端:{},批次号:{}重新转换终端开始....", new Object[]{type, batchId});
			boolean flag = errorHandler.changeTerminal(terminalType, batchId);
			if (flag) {
				logger.error("终端:{},批次:{},玩法:{}重新转换终端成功", new Object[] { type, batchId });
				rd.setErrorCode(ErrorCode.Success.getValue());
			} else {
				logger.error("终端:{},批次:{},玩法:{}重新转换终端失败", new Object[] { type, batchId });
				rd.setErrorCode(ErrorCode.Faile.getValue());
			}
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 批次手工投注
	 * 
	 * @param batchId
	 *            批次编号
	 * */
	@RequestMapping(value = "/batchBet", method = RequestMethod.POST)
	public @ResponseBody ResponseData batchBet(@RequestParam(value = "batchId", required = true) String batchId) {
		ResponseData rd = new ResponseData();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("batchId", batchId);
			queueMessageSendService.sendMessage(QueueName.ticketSend, map);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 订单手工处理
	 * 
	 * @param orderId
	 *            订单号
	 * @param id
	 *            编号
	 * */
	@RequestMapping(value = "/orderProcess", method = RequestMethod.POST)
	public @ResponseBody ResponseData orderProcess(@RequestParam(value = "orderId", required = true) String orderId, @RequestParam(value = "id", required = true) int id) {
		ResponseData rd = new ResponseData();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			QueueName queueName = getQueue().get(id);
			if (queueName == null) {
				if(id==6){//撤单
					int total=errorHandler.cancelOrder(orderId);
					rd.setErrorCode(ErrorCode.Success.getValue());
					rd.setValue("撤单总数:"+total);
				}else{
					rd.setErrorCode(ErrorCode.Faile.getValue());
				}
			} else {
				queueMessageSendService.sendMessage(queueName, map);
				rd.setErrorCode(ErrorCode.Success.getValue());
			}

		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 订单手动设置为成功
	 * 
	 * @param orderId
	 *            订单号
	 * */
	@RequestMapping(value = "/updateSuccess", method = RequestMethod.POST)
	public @ResponseBody ResponseData updateSuccess(@RequestParam(value = "orderId", required = true) String orderId) {
		ResponseData rd = new ResponseData();
		try {
			errorHandler.upateOrderSuccess(orderId);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 手动设置彩种，终端出票
	 * 
	 * @param lotterytype
	 *            彩种
	 * @param terminaltid
	 *            终端id
	 * @param isPaused
	 *            是否暂停送票
	 * @param isEnabled
	 *            是否可用
	 * 
	 * */
	@RequestMapping(value = "/updateTerminal", method = RequestMethod.POST)
	public @ResponseBody ResponseData updateTerminal(@RequestParam(value = "lotterytype", required = true) int lotterytype,
			@RequestParam(value = "terminaltid", required = true) Long  terminaltid, 
			@RequestParam(value = "isPaused", required = false, defaultValue = "0") int isPaused,
	        @RequestParam(value = "isEnabled", required = false, defaultValue = "1") int isEnabled,
	        @RequestParam(value = "isCheckEnabled", required = false, defaultValue = "1") int isCheckEnabled,
	        @RequestParam(value = "weight", required = false, defaultValue = "0") int weight, @RequestParam int amountEnabled,
	        @RequestParam String preferAmountRegion) {
		ResponseData rd = new ResponseData();
		try {
			if (LotteryType.getDcValue().contains(lotterytype)) {
				for (int lottype : LotteryType.getDcValue()) {
					this.updateTermialForEache(terminaltid, lottype, isPaused, isEnabled,isCheckEnabled,weight,preferAmountRegion, amountEnabled);
				}
			} else if (LotteryType.getJclqValue().contains(lotterytype)) {
				for (int lottype : LotteryType.getJclqValue()) {
					this.updateTermialForEache(terminaltid, lottype, isPaused, isEnabled,isCheckEnabled,weight,preferAmountRegion, amountEnabled);
				}
			} else if (LotteryType.getJczqValue().contains(lotterytype)) {
				for (int lottype : LotteryType.getJczqValue()) {
					this.updateTermialForEache(terminaltid, lottype, isPaused, isEnabled,isCheckEnabled,weight,preferAmountRegion, amountEnabled);
				}
			} else if (LotteryType.getZcValue().contains(lotterytype)) {
				for (int lottype : LotteryType.getZcValue()) {
					this.updateTermialForEache(terminaltid, lottype, isPaused, isEnabled,isCheckEnabled,weight,preferAmountRegion, amountEnabled);
				}
			} else if(lotterytype==LotteryType.ALL.value){
				for (LotteryType lottype : LotteryType.get()) {
					this.updateTermialForEache(terminaltid, lottype.value, isPaused, isEnabled,isCheckEnabled,weight,preferAmountRegion, amountEnabled);
				}
			} else if(lotterytype == -2001){
				//体彩数字彩
				LotteryType[] lt = new LotteryType[]{LotteryType.CJDLT,LotteryType.PL3, LotteryType.PL5, LotteryType.QXC};
				for (LotteryType lottype : lt) {
					this.updateTermialForEache(terminaltid, lottype.value, isPaused, isEnabled,isCheckEnabled,weight,preferAmountRegion, amountEnabled);
				}
			}else {
				this.updateTermialForEache(terminaltid, lotterytype, isPaused, isEnabled,isCheckEnabled,weight,preferAmountRegion, amountEnabled);
			}
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

    /**
     * 订单指定出票终端
     *
     * @param orderId 订单号
       @param  terminalId 终端号
     * */
    @RequestMapping(value = "/changeOrderIdTerminal", method = RequestMethod.POST)
    public @ResponseBody ResponseData changeOrderIdTerminal(@RequestParam(value = "orderId", required = true) String orderId,
                                                            @RequestParam(value = "terminalId", required = true) Long terminalId) {
        ResponseData rd = new ResponseData();
        try {
            errorHandler.changeOrderIdTerminal(orderId,terminalId);
            rd.setErrorCode(ErrorCode.Success.getValue());
        }  catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }


    /**
     * 彩种指定终端号
     *
     * @param lotteryType 彩种
     @param  terminalId 终端号
      * */
    @RequestMapping(value = "/lotteryTypeChangeTerminal", method = RequestMethod.POST)
    public @ResponseBody ResponseData lotteryTypeChangeTerminal(@RequestParam(value = "lotteryType", required = true) Integer lotteryType,
            @RequestParam(value = "terminalId", required = true) Long terminalId,@RequestParam(value = "max", required = true) int max) {
        ResponseData rd = new ResponseData();
        try {
            errorHandler.lotteryTypeChangeTerminal(lotteryType, terminalId, max);
            rd.setErrorCode(ErrorCode.Success.getValue());
        }  catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }


    protected Map<Integer, QueueName> getQueue() {
		Map<Integer, QueueName> map = new HashMap<Integer, QueueName>();
		map.put(1, QueueName.betSplitQueue);// 拆票
		map.put(2, QueueName.betChercher);// 检票
		map.put(3, QueueName.betSuccessDeduct);// 扣款
		map.put(4, QueueName.betFailuerUnfreeze);// 解冻队列
		map.put(5, QueueName.betFreeze);// 冻结队列
		return map;
	}

	protected void updateTermialForEache(Long  terminalId, int lotteryType, int pase, int eable,int isCheckEnabled,int weight,String preferAmountRegion, int amountEnabled) {
		List<TerminalConfig> list = terminalconfigService.getByTerminalIdAndLotteryType(terminalId, lotteryType);
		for (TerminalConfig terminalConfig : list) {
			terminalConfig.setIsEnabled(eable);
			terminalConfig.setIsPaused(pase);
			terminalConfig.setIsCheckEnabled(isCheckEnabled);
			if(weight>0){
				terminalConfig.setWeight(weight);
			}
			terminalConfig.setAmountEnabled(amountEnabled);
			if(StringUtil.isNotEmpt(preferAmountRegion)){
				terminalConfig.setPreferAmountRegion(preferAmountRegion);
			}
			terminalconfigService.update(terminalConfig);
		}
	}

	protected boolean isflagParent(String type, IThreadRunnable runable) {
		if (type.equals("phase")) {
			if (AbstractPhaseHandler.class.isAssignableFrom(runable.getClass())) {
				return true;
			}
		}
		if (type.equals("allot")) {
			if (AbstractTicketAllotterRunnable.class.isAssignableFrom(runable.getClass())) {
				return true;
			}
		}
		if (type.equals("send")) {
			if (AbstractTicketSenderRunnable.class.isAssignableFrom(runable.getClass())) {
				return true;
			}
		}
		if (type.equals("check")) {
			if (AbstractVenderTicketCheckerRunnable.class.isAssignableFrom(runable.getClass())) {
				return true;
			}
		}
		if (type.equals("all")) {
			return true;
		}
		return false;
	}
	/**
	 *
	 * 根据订单状态操作
	 * @param  orderStatus 订单状态
	 * */

	@RequestMapping(value = "/orderBatchProcess", method = RequestMethod.POST)
	public @ResponseBody ResponseData orderBatchProcess(@RequestParam(value = "lotteryType", required = false) Integer lotteryType,@RequestParam(value = "orderStatus", required = true) int orderStatus, @RequestParam(value = "dowith", required = true) int dowith) {
		ResponseData rd = new ResponseData();
		try {
			logger.error("订单操作:lotteryType={},orderStatus={},dowith={}",lotteryType,orderStatus,dowith);
			rd.setErrorCode(ErrorCode.Success.getValue());
			QueueName queueName = getQueue().get(dowith);
			String name=null;
			if (dowith==6){
				name="撤单";
			}
			if (queueName!=null){
				name=queueName.value;
			}
			errorHandler.orderBatchProcess(lotteryType,orderStatus,name);
			rd.setValue("success");
		} catch (Exception e) {
			logger.error("彩种订单状态修改出错",e);
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	@RequestMapping(value = "/phasewindo", method = RequestMethod.GET)
	public @ResponseBody String testphase(@RequestParam(value = "id", required = true) int orderStatus){
		try{
			if (orderStatus==1){
				logger.error("cache product");
				cacheService.set("lhh_phase_update_value","123456");
			}else{
				logger.error("cache delete");
				cacheService.delete("lhh_phase_update_value");
			}
			return "success";
		}catch (Exception e){
			return e.getMessage();
		}

	}


	@RequestMapping(value = "/orderListProcess", method = RequestMethod.POST)
	public @ResponseBody ResponseData orderListProcess(@RequestParam(value = "orders", required = true)  String orders) {
		ResponseData rd = new ResponseData();
		try {
			rd.setErrorCode(ErrorCode.Success.getValue());
			final String orderids=orders;
			Thread thread=new Thread(new Runnable() {
				@Override
				public void run() {
					errorHandler.updateSuccessList(orderids);
				}
			});
			thread.setName("批量修改订单成功");
			thread.start();
			rd.setValue("success");
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping(value = "fyticketsuc", method = RequestMethod.POST)
	public @ResponseBody ResponseData fyticketsuc(@RequestParam String tickets) throws Exception {
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.value);
		try {
			int suc = 0;
			int fail = 0;
			for (String ticketid : tickets.split(",")) {
				Ticket ticket = ticketService.getTicket(ticketid);
				if (ticket.getStatus() != TicketStatus.PRINT_SUCCESS.value) {
					ticket.setStatus(TicketStatus.PRINT_SUCCESS.value);
					ticket.setFailureMessage("fyticket手工成功");
					LotteryType lotteryType = LotteryType.get(ticket.getLotteryType());
					if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType)) {
						String odds = prizeService.simulateOdd(ticket.getContent(), lotteryType);
						ticket.setPeilv(odds);
					}
					ticket.setPrintTime(new Date());
					ticketService.update(ticket);
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("orderId", ticket.getOrderId());
					queueMessageSendService.sendMessage(QueueName.betChercher, map);
					suc++;
				}else{
					fail++;
				}
			}
			rd.setValue("成功:"+suc+",失败:"+fail);
		} catch (Exception e) {
			logger.error("", e);
			rd.setErrorCode(ErrorCode.Faile.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	/**
	 * 批量修改票的状态
	 * @param  tickets 票批量
	 * @param  useid (1,成功;2,撤单;3,重新分配;4,出票商检票;5,优先送票;6,对方成功，直接设置状态;7,订单检票;8,票合并;9,限号撤单)
	 * */
	@RequestMapping(value = "/ticketListProcess", method = RequestMethod.POST)
	public @ResponseBody ResponseData ticketListProcess(@RequestParam(value = "tickets", required = true)  String tickets,@RequestParam(value = "useid", required = true)  int useid) {
		ResponseData rd = new ResponseData();
		try {
			rd.setErrorCode(ErrorCode.Success.getValue());
			final String orders=tickets;
			final int u=useid;
			Thread thread=new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						errorHandler.updateTicketListStatus(orders,u);
					}catch (Exception e){

					}

				}
			});
			thread.setName("批量修改票状态");
			thread.start();
			rd.setValue("success");
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));// true:允许输入空值，false:不能为空值
	}

	/**
	 * 手动发送jms
	 * @param  queueName 队列名
	 * @param  key 主键
	 * @param  value 值
	 * */

    @RequestMapping(value = "sendQueueJms",method = RequestMethod.POST)
	public String sendQueueJms(@RequestParam(value = "queueName", required = true) String queueName,@RequestParam(value = "key", required = true) String key,
						  @RequestParam(value = "value", required = true) String value) {
       try{
		   Map<String,String> map=new HashMap<String, String>();
		   map.put(key,value);
		   queueMessageSendService.sendMessage(queueName,map);
		   return "success";
	   }catch (Exception e){
		  return  e.getMessage();
	   }
	}
	/**
	 * 足彩新期,赛程手动控制
	 * @param  type 1开启线程,2关闭线程,3查询线程状态
	 * */
	@RequestMapping(value = "/zcRacePhase", method = RequestMethod.POST)
	public @ResponseBody ResponseData zcRace(@RequestParam(value = "type", required = true) Integer type){
		ResponseData rd=new ResponseData();
		try{
			Map<String, ThreadContainer> map = systemService.getMap(ThreadContainer.class);
			StringBuffer stringBuffer=new StringBuffer();
			for (Map.Entry<String, ThreadContainer> entry : map.entrySet()) {
				String key = entry.getKey();
				ThreadContainer value = entry.getValue();
				IThreadRunnable run = value.getRunnable();

				if(AsyncZcPhaseRunnable.class.isAssignableFrom(run.getClass())){
					if(type==1){
						stringBuffer.append(value.getName()).append("<br/>").append("状态:").append(value.isRunning()).append("<br/>");
						if (!run.isRunning()) {
							value.setBeforeRunWaitTime(0);
							value.start();
							logger.error("线程容器{}开启", key);
							stringBuffer.append("操作以后:<br/>");
						}
						stringBuffer.append(value.getName()).append(":").append(value.isRunning()).append("<br/>");

					}
					if(type==2){
						stringBuffer.append(value.getName()).append("<br/>").append("状态:").append(value.isRunning()).append("<br/>");
						if (value.isRunning()) {
							value.stop();
						}
						stringBuffer.append("操作以后:<br/>");
						stringBuffer.append(value.getName()).append(":").append(value.isRunning()).append("<br/>");
					}
					if(type==3){
						stringBuffer.append(value.getName()).append("<br/>").append("状态:").append(value.isRunning()).append("<br/>");
					}
				}

			}
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(stringBuffer.toString());
		}catch (Exception e){
			rd.setErrorCode(e.getMessage());
		}

		return rd;
	}

	/**
	 * 足彩新期,赛程手动控制
	 * @param  type 1开启线程,2关闭线程,3查询线程状态
	 * */
	@RequestMapping(value = "/zcResult", method = RequestMethod.POST)
	public @ResponseBody ResponseData zcResult(@RequestParam(value = "type", required = true) Integer type){

		ResponseData rd=new ResponseData();
		try{
			Map<String, ThreadContainer> map = systemService.getMap(ThreadContainer.class);
			StringBuffer stringBuffer=new StringBuffer();
			for (Map.Entry<String, ThreadContainer> entry : map.entrySet()) {
				String key = entry.getKey();
				ThreadContainer value = entry.getValue();
				IThreadRunnable run = value.getRunnable();

				if(ZcRaceResultRunnable.class.isAssignableFrom(run.getClass())){
					if(type==1){
						stringBuffer.append(value.getName()).append("<br/>").append("状态:").append(value.isRunning()).append("<br/>");
						if (!run.isRunning()) {
							value.setBeforeRunWaitTime(0);
							value.start();

							stringBuffer.append("操作以后:<br/>");
						}
						stringBuffer.append(value.getName()).append(":").append(value.isRunning()).append("<br/>");

					}
					if(type==2){
						stringBuffer.append(value.getName()).append("<br/>").append("状态:").append(value.isRunning()).append("<br/>");
						if (value.isRunning()) {
							value.stop();
						}
						stringBuffer.append("操作以后:<br/>");
						stringBuffer.append(value.getName()).append(":").append(value.isRunning()).append("<br/>");
					}
					if(type==3){
						stringBuffer.append(value.getName()).append("<br/>").append("状态:").append(value.isRunning()).append("<br/>");
					}
				}

			}
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(stringBuffer.toString());
		}catch (Exception e){
			rd.setErrorCode(e.getMessage());
		}

		return rd;
	}


	@RequestMapping(value = "/terminalThreadReload")
	public @ResponseBody ResponseData zcResult(){

		ResponseData rd=new ResponseData();
		try{
			Map<String, ITicketSenderRunnable> map = systemService.getMap(ITicketSenderRunnable.class);
			for (Map.Entry<String, ITicketSenderRunnable> entry : map.entrySet()){

				ITicketSenderRunnable runnable=entry.getValue();
				runnable.reload();
			}
			rd.setErrorCode(ErrorCode.Success.value);
		}catch (Exception e){
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	@RequestMapping(value = "/checkAllByTerminalId")
	public @ResponseBody ResponseData checkAllByTerminalId(@RequestParam(value = "createTime", required = true) Date createTime
	,@RequestParam(value = "endTime", required = true) Date endTime,@RequestParam(value = "terminalId", required = true) Long  terminalId){

		ResponseData rd=new ResponseData();
		try{
			errorHandler.checkTicketAll(createTime, endTime, terminalId);
			rd.setErrorCode(ErrorCode.Success.value);
		}catch (Exception e){
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
/**
 *
 * 所有线程重载
 * */

	@RequestMapping(value = "/threadReload", method = RequestMethod.POST)
	public @ResponseBody ResponseData threadReload(){

		ResponseData rd=new ResponseData();
		try{
			Map<String, ThreadContainer> map = systemService.getMap(ThreadContainer.class);
			StringBuffer stringBuffer=new StringBuffer();
			for (Map.Entry<String, ThreadContainer> entry : map.entrySet()) {
				String key = entry.getKey();
				ThreadContainer value = entry.getValue();
				stringBuffer.append("线程:"+value.getName());
				value.executeNotify();
				stringBuffer.append("重载<br/>");
			}
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(stringBuffer.toString());
		}catch (Exception e){
			rd.setErrorCode(e.getMessage());
		}

		return rd;
	}



}
