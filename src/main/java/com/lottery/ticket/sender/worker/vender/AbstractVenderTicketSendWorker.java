package com.lottery.ticket.sender.worker.vender;

import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.lottery.HighFrequencyLottery;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.TicketBatchService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.UserInfoService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.IVenderTicketSendWorker;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.ticket.sender.TicketSendUser;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.SocketTimeoutException;
import java.util.*;

public abstract class AbstractVenderTicketSendWorker implements IVenderTicketSendWorker {

	protected final transient Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	protected VenderConfigHandler venderConfigService;
	@Resource(name = "terminalTypeVenderTicketSendWorkerBinder")
	protected Map<TerminalType, IVenderTicketSendWorker> terminalTypeVenderTicketSendWorkerBinder;
	@Resource(name = "venderConverterBinder")
	protected Map<TerminalType, IVenderConverter> venderConverterBinder;
	@Autowired
	protected TicketService ticketService;
	@Autowired
	protected UserInfoService userInfoService;
	@Autowired
	protected IdGeneratorService idGeneratorService;
	@Autowired
	protected QueueMessageSendService queueMessageSendService;
	@Autowired
	protected TicketBatchService ticketBatchService;


	private int ticketSendPerSize = 15; // 出票商请求时每次送的票数
	private long ticketSendInterval = 10l; // 出票商请求时的每次间隔

	@Override
	public List<TicketSendResult> executeSend(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType) throws Exception {
		// 进行一些基础状态检查
		if (ticketBatch.getStatus() != TicketBatchStatus.SEND_QUEUED.getValue() && ticketBatch.getStatus() != TicketBatchStatus.SEND_WAITING.getValue()) {
			logger.error("批次状态不为可送票的状态, status={}, id={}", ticketBatch.getStatus(), ticketBatch.getId());
			throw new RuntimeException("批次状态不为可送票的状态");
		}

		Long terminalId = ticketBatch.getTerminalId();
		IVenderConfig config = venderConfigService.getTerminalByTypeAndId(getTerminalType(), terminalId);
		IVenderConverter venderConverter=getVenderConverter();
		if (config == null||venderConverter==null) {
			logger.error("终端类型:{},终端id:{},配置venderconfig={},venderconverter={},两者都不能为空", getTerminalType(), terminalId,config,venderConverter);
			return null;
		}

		List<TicketSendResult> allTicketSendResultList = new ArrayList<TicketSendResult>();

		int batchCount=getBatchCount(config);
		// 分批处理
		for (int i = 0; i < ticketList.size(); i +=batchCount) {
			int sub_start = i;
			int sub_end = i + batchCount;
			// 参考subList方法的声明，这里是 *大于* 而不是 *>=*
			if (sub_end > ticketList.size()) {
				sub_end = ticketList.size();
			}

			List<Ticket> subTicketList = ticketList.subList(sub_start, sub_end);

			try {
				List<TicketSendResult> ticketSendResultList = this.executePerSend(ticketBatch, subTicketList, lotteryType,config,venderConverter);
				if (ticketSendResultList != null && !ticketSendResultList.isEmpty()) {
					allTicketSendResultList.addAll(ticketSendResultList);
				}
			} catch (Exception e) {
				logger.error("对({})号终端进行送票时出错", terminalId, e);
				TicketSendResultStatus resultStatus = this.convertResultStatusFromException(e);
				this.processError(allTicketSendResultList, ticketBatch, subTicketList, resultStatus, "0", "systemerror", "systemerror", e.getMessage());

			}
			if (this.getTicketSendInterval() > 0&&sub_end!=ticketList.size()) {
				synchronized (this) {
					try{
						this.wait(this.getTicketSendInterval());
					}catch (Exception e){
						logger.error(e.getMessage(),e);
					}

				}
			}
		}
		return allTicketSendResultList;
	}

	abstract protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList,LotteryType lotteryType,IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception;

	abstract protected TerminalType getTerminalType();

	protected IVenderConverter getVenderConverter() {
		return venderConverterBinder.get(getTerminalType());
	}

	protected TicketSendResultStatus convertResultStatusFromException(Exception e) {

		if (e instanceof SocketTimeoutException) {
			return TicketSendResultStatus.timeout;
		}
		if (e instanceof ConnectTimeoutException) {
			return TicketSendResultStatus.timeout;
		}
		if(e instanceof HttpHostConnectException){
			return TicketSendResultStatus.http_connection_refused;
		}

		if(e.getMessage()!=null){
			if (e.getMessage().contains("502")) {
				return TicketSendResultStatus.http_502;
			}

			if (e.getMessage().contains("504")) {
				return TicketSendResultStatus.http_504;
			}
			if (e.getMessage().contains("404")) {
				return TicketSendResultStatus.http_404;
			}
			if(e.getMessage().contains("Connection refused")){
				return TicketSendResultStatus.http_connection_refused;
			}
			if(e.getMessage().contains("connect timed out")){
				return TicketSendResultStatus.timeout;
			}

		}

		return TicketSendResultStatus.unkown;
	}

	protected TicketSendResult createInitializedTicketSendResult(Ticket ticket) {
		TicketSendResult result = new TicketSendResult();
		result.setId(ticket.getId());
		result.setRequestId(ticket.getId().toString());
		result.setLotteryType(LotteryType.get(ticket.getLotteryType()));
		result.setPhase(ticket.getPhase());
		result.setTerminalType(this.getTerminalType());

		return result;
	}

	/**
	 * @param ticketResultStatus
	 *            送票类型
	 * @param code
	 *            对方返回错误码
	 * @param requestStr
	 *            请求内容
	 * @param responseStr
	 *            返回内容
	 * @param errorMessage
	 *            对方错误信息
	 * */
	protected void processError(List<TicketSendResult> ticketSendResultList, TicketBatch ticketBatch, List<Ticket> ticketList, TicketSendResultStatus ticketResultStatus, String code, String requestStr, String responseStr, String errorMessage) {
		if (ticketResultStatus == null) {
			ticketResultStatus = TicketSendResultStatus.unkown;
		}
		Date sendTime = new Date();
		for (Ticket ticket : ticketList) {
			TicketSendResult ticketSendResult = createInitializedTicketSendResult(ticket);
			ticketSendResult.setStatus(ticketResultStatus);
			ticketSendResult.setStatusCode(code);
			ticketSendResult.setResponseMessage(responseStr);
			ticketSendResult.setSendMessage(requestStr);
			ticketSendResult.setMessage(errorMessage);
			ticketSendResult.setSendTime(sendTime);
			ticketSendResult.setTerminalType(getTerminalType());
			ticketSendResult.setTerminalId(ticketBatch.getTerminalId());
			ticketSendResultList.add(ticketSendResult);
		}

        //批次做失败校验,针对高频
		if(HighFrequencyLottery.contains(ticketBatch.getLotteryType())){
			if(ticketResultStatus==TicketSendResultStatus.http_502||ticketResultStatus==TicketSendResultStatus.http_504||ticketResultStatus==TicketSendResultStatus.http_404
					||ticketResultStatus==TicketSendResultStatus.http_connection_refused){
				logger.error("批次:{}送票返回:{},需要关闭终端,terminalId={}",ticketBatch.getId(),ticketResultStatus,ticketBatch.getTerminalId());
				boolean flag=venderConfigService.closeTerminaConfig(ticketBatch.getTerminalId(),ticketBatch.getLotteryType());
				if(flag){
					ticketBatchService.updateStatus(ticketBatch.getId(),TicketBatchStatus.SEND_WAITING.value);//将批次修改为未送票,下次送票自动重分
				}else {
					logger.error("彩种:{},批次:{},关闭送票终端:{}失败",ticketBatch.getLotteryType(),ticketBatch.getId(),ticketBatch.getTerminalId());
				}
			}
		}


	}

	  protected void sendJms(Ticket ticket){
	    	
	    	try {
	    		String queuename=QueueName.betChercher.value;
	    		LotteryType lotteryType=LotteryType.get(ticket.getLotteryType());
	    		if(HighFrequencyLottery.contains(lotteryType)){
	    			queuename=QueueName.gaopinChercher.value;
	    		}
	    		Map<String,Object> map=new HashMap<String,Object>();
	    		map.put("orderId", ticket.getOrderId());
				this.queueMessageSendService.sendMessage(queuename, map);
			} catch (Exception e) {
			   logger.error("发送检票消息出错",e);
			}
	    }
	  
	 
	public int getTicketSendPerSize() {
		return ticketSendPerSize;
	}
	protected int getBatchCount(IVenderConfig config){
		if(config.getSendCount()!=null){
			return config.getSendCount();
		}
		return ticketSendPerSize;
	}
	

	public void setTicketSendPerSize(int ticketSendPerSize) {
		this.ticketSendPerSize = ticketSendPerSize;
	}

	public long getTicketSendInterval() {
		return ticketSendInterval;
	}

	public void setTicketSendInterval(long ticketSendInterval) {
		this.ticketSendInterval = ticketSendInterval;
	}

	public TicketSendUser getDefaultUser(){
		TicketSendUser ticketSendUser=new TicketSendUser();
		ticketSendUser.setIdCard("220622198310032015");
		ticketSendUser.setPhone("18600758958");
		ticketSendUser.setRealName("冯钦云");
		ticketSendUser.setEmail("fengqinyun@gmail.com");
		ticketSendUser.setUserName("fengqinyun");
		return ticketSendUser;
	}



	@PostConstruct
	protected void init() {
		terminalTypeVenderTicketSendWorkerBinder.put(getTerminalType(), this);
	}


	protected  void closeTermianl(){

	}




}
