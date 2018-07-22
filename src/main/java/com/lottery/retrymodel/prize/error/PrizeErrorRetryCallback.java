package com.lottery.retrymodel.prize.error;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.*;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.handler.PrizeErrorHandler;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.TicketService;
import com.lottery.retrymodel.ApiRetryCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 重新算奖恢复
 * */
public class PrizeErrorRetryCallback extends ApiRetryCallback<Object> {

	private static List<Integer> statusList;
	private static List<Integer> resultList;
    private int max=50;
    private LotteryOrderService lotteryOrderService;
    private PrizeErrorHandler prizeErrorHandler;
    private Integer lotteryType;
    private String phase;
    private LotteryPhaseService lotteryPhaseService;

	private TicketService ticketService;



	private Date startDate;
	private Date endDate;

	private Integer idex=1;

	private String matchNum;



    static {
		statusList=new ArrayList<Integer>();
		statusList.add(OrderStatus.PRINTED.value);
		statusList.add(OrderStatus.HALF_PRINTED.value);

		resultList=new ArrayList<Integer>();
		resultList.add(OrderResultStatus.not_win.value);
		resultList.add(OrderResultStatus.win_big.value);
		resultList.add(OrderResultStatus.win_already.value);
	}

	@Override
	protected Object execute() throws Exception {
		if(lotteryOrderService==null||lotteryType==null||lotteryPhaseService==null||prizeErrorHandler==null||ticketService==null){
			throw new LotteryException("参数不全");
		}
		if (LotteryType.getJclqValue().contains(lotteryType)||LotteryType.getJczqValue().contains(lotteryType)||LotteryType.getDcValue().contains(lotteryType)){

			for (Integer lottype:LotteryType.getLotteryTypeList(lotteryType)){
				if (idex==2){
					handler(lottype,phase);
				}else {
					jingjicaihandler(lottype, phase);
				}

			}

		}else{
			LotteryPhase lotteryphase=lotteryPhaseService.getByTypeAndPhase(lotteryType, phase);
			if(lotteryphase!=null){
				lotteryphase.setPhaseStatus(PhaseStatus.close.value);
				lotteryphase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.value);
				lotteryPhaseService.update(lotteryphase);
			}
			handler(lotteryType,phase);

		}


		
		return null;
	}

	protected  void handler(Integer lotteryType,String phase){
		while (true){
			List<LotteryOrder> orders=lotteryOrderService.getByLotteryPhaseAndStatus(lotteryType,phase,statusList,resultList,max);
			if(orders!=null&&orders.size()>0){
				process(orders);
				if(orders.size()<max){
					logger.info("读取到的方案列表不足一页，已读取结束");
					break;
				}
			}else{
				break;
			}
		}
	}

	protected void jingjicaihandler(Integer lotteryType,String phase){

		try{

			PageBean<LotteryOrder> pageBean=new PageBean<LotteryOrder>();
			pageBean.setPageFlag(false);
			pageBean.setMaxResult(max);
			pageBean.setTotalFlag(false);
			int page=1;
			while(true){
				pageBean.setPageIndex(page);
				List<LotteryOrder> list=null;
				try{
					list=lotteryOrderService.getByLotteryStatusMatchNumAndDate(lotteryType,phase,statusList,resultList,startDate,endDate,matchNum,pageBean);
				}catch(Exception e){
					logger.error("查询出错",e);
					break;
				}
				if(list!=null&&list.size()>0){
                     process(list);
					if(list.size()<max){
						logger.info("读取到的方案列表不足一页，已读取结束");
    					break;
					}
				}else{
					break;
				}
				page ++;

			}
			

		}catch(Exception e){
			logger.error("重新算奖重置出错",e);
		}
	}


	protected void process(List<LotteryOrder> orderList){
		for (LotteryOrder lotteryOrder:orderList){
			try {
                if (lotteryOrder.getOrderResultStatus()==OrderResultStatus.not_win.value){
					lotteryOrderService.updateOrderResultStatus(lotteryOrder.getId(),OrderResultStatus.not_open.value,null);
				}else{
					prizeErrorHandler.prizeRecovery(lotteryOrder);
				}

                ticketService.updateTicketResultStatusByOrderId(lotteryOrder.getId(),TicketResultStatus.not_draw.value);
			}catch (Exception e){
				logger.error("修改算奖状态出错",e);
			}
		}
	}



	public LotteryOrderService getLotteryOrderService() {
		return lotteryOrderService;
	}

	public void setLotteryOrderService(LotteryOrderService lotteryOrderService) {
		this.lotteryOrderService = lotteryOrderService;
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

		public LotteryPhaseService getLotteryPhaseService() {
		return lotteryPhaseService;
	}

	public void setLotteryPhaseService(LotteryPhaseService lotteryPhaseService) {
		this.lotteryPhaseService = lotteryPhaseService;
	}

	public PrizeErrorHandler getPrizeErrorHandler() {
		return prizeErrorHandler;
	}

	public void setPrizeErrorHandler(PrizeErrorHandler prizeErrorHandler) {
		this.prizeErrorHandler = prizeErrorHandler;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMatchNum() {
		return matchNum;
	}

	public void setMatchNum(String matchNum) {
		this.matchNum = matchNum;
	}

	public Integer getIdex() {
		return idex;
	}

	public void setIdex(Integer idex) {
		this.idex = idex;
	}

	public static List<Integer> getStatusList() {
		return statusList;
	}

	public static void setStatusList(List<Integer> statusList) {
		PrizeErrorRetryCallback.statusList = statusList;
	}

	public static List<Integer> getResultList() {
		return resultList;
	}

	public static void setResultList(List<Integer> resultList) {
		PrizeErrorRetryCallback.resultList = resultList;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public TicketService getTicketService() {
		return ticketService;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}


}
