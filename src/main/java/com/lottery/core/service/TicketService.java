package com.lottery.core.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.PageBean;
import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.StringUtil;
import com.lottery.controller.admin.dto.TicketMonitor;
import com.lottery.core.dao.TicketBatchDAO;
import com.lottery.core.dao.TicketDAO;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;

@Service
public class TicketService {
	// TicketStatus.UNALLOTTED
	private static Logger logger = LoggerFactory.getLogger(TicketService.class);
	@Autowired
	protected TicketDAO ticketDAO;
	@Autowired
	protected TicketBatchDAO ticketBatchDao;

	@Transactional
	public List<Ticket> findToAllot(int lotteryType, String phase, int max) {
		return   ticketDAO.findToAllot(lotteryType, phase, max);

	}

    /**
     * 修改票状态
     * @param status 状态
     * @param id 票号
     * */
	@Transactional
	public int updateTicketStatus(int status, String id) {
		return ticketDAO.updateTicketStatus(status, id);

	}

	/**
	 * 查询未检票的票
	 * */
	@Transactional
	public List<Ticket> getUnCheckTicket(Long terminalId, List<LotteryType> lotteryTypeList, int max, Date date) {
		return ticketDAO.getUnCheckTicket(terminalId, lotteryTypeList, max, date);
	}

	@Transactional
	public boolean saveAllottedTicketsAndBatch(List<Ticket> ticketList, TicketBatch ticketBatch) {
		try {
			ticketBatchDao.insert(ticketBatch);
			Long batchIndex = 1l;
			for (Ticket ticket : ticketList) {
				Map<String, Object> contentMap = new HashMap<String, Object>();
				contentMap.put("batchIndex", batchIndex);
				contentMap.put("batchId", ticketBatch.getId());
				contentMap.put("status", TicketStatus.UNSENT.getValue());
				contentMap.put("terminalType", ticketBatch.getTerminalTypeId());
				contentMap.put("terminalId", ticketBatch.getTerminalId());
				Map<String, Object> whereMap = new HashMap<String, Object>();
				whereMap.put("id", ticket.getId());
				ticketDAO.update(contentMap, whereMap);
				batchIndex++;
			}
		} catch (Exception e) {
			logger.error("分票保存出错，batchid={}", ticketBatch.getId(), e);
		}
		return false;
	}



	@Transactional
	public Ticket getTicket(String ticketid) {
		return ticketDAO.find(ticketid);

	}

	@Transactional
	public void update(Ticket ticket) {
		ticketDAO.update(ticket);
	}

	/**
	 * 送票成功更新订单票状态
	 * 
	 * @param ticketList
	 * @param ticketBatch
	 * @param lotteryOrder
	 * @param ticketCode
	 */
	@Transactional
	public void updateTicketStatus(List<Ticket> ticketList, TicketBatch ticketBatch, String ticketCode) {
		ticketBatch.setSendTime(new Date());
		ticketBatch.setStatus(TicketBatchStatus.SEND_SUCCESS.value);
		ticketBatchDao.updateStatusAndSendTime(ticketBatch);
	}

	@Transactional
	public void updateTicketStatus(Ticket ticket) {
		ticketDAO.updateTicketStatus(ticket);
	}

	


	/**
	 * 根据批次号状态查询票
	 * 
	 * @param batchTicketId批次号
	 * @param ticketStatus
	 *            票状态
	 * */
	@Transactional
	public List<Ticket> getByBatchIdAndStatus(String batchTicketId, int ticketStatus) {
		List<Ticket> ticketList = new ArrayList<Ticket>();
		PageBean<Ticket> pageBean = new PageBean<Ticket>();
		int max = 20;
		pageBean.setMaxResult(max);
		int page = 1;
		while (true) {
			List<Ticket> list = null;
			pageBean.setPageIndex(page);
			try {
				list = ticketDAO.getByBatchIdAndStatus(batchTicketId, ticketStatus, pageBean);
			} catch (Exception e) {
				logger.error("根据批次查询票状态出错", e);
				break;
			}
			if (list != null && list.size() > 0) {
				ticketList.addAll(list);
				if (list.size() < max) {
					logger.info("读取到的方案列表不足一页，已读取结束");
					break;
				}
			} else {
				break;
			}
			page++;
		}
		return ticketList;
	}

	/**
	 * 根据订单,票状态查询总数
	 * 
	 * @param orderId
	 *            订单号
	 * @param ticketStatus
	 *            票状态
	 * */
	@Transactional
	public Long getCountByOrderIdAndStatus(String orderId, int ticketStatus) {
		return ticketDAO.getCountByOrderIdAndStatus(orderId, ticketStatus);
	}

	@Transactional
	public Long getCountByOrderId(String orderId) {
		return ticketDAO.getCountByOrderId(orderId);
	}

	/**
	 * 根据订单号，订单状态查询票的总金额
	 * 
	 * @param orderId
	 *            订单号
	 * @param ticketStatus
	 *            票状态
	 * */
	@Transactional
	public BigDecimal getSumAmountByOrderIdAndStatus(String orderId, int ticketStatus) {
		return ticketDAO.getSumAmountByOrderIdAndStatus(orderId, ticketStatus);
	}

	/**
	 * 根据订单号修改票的状态
	 * 
	 * @param orderId
	 *            订单号
	 * @param fromTicketStatus
	 *            原来状态
	 * @param toTicketStatus
	 *            需要修改的状态
	 * */
	@Transactional
	public int updateTicketStatusByOrderId(String orderId, int fromTicketStatus, int toTicketStatus) {
		return ticketDAO.updateTicketStatusByOrderId(orderId, fromTicketStatus, toTicketStatus);
	}

	/**
	 * 根据订单号查询订单
	 * */
	@Transactional
	public List<Ticket> getByorderId(String orderId) {
		return ticketDAO.getByorderId(orderId);
	}
	
	
	
	/**
	 * 查询需要转换终端的票
	 * 
	 * @param terminalId
	 *            票所在终端
	 * @param lotteryType
	 *            彩种
	 * @param statusList
	 *            状态
	 * @param minute 距离期结时间
	 * */
	@Transactional
	public List<Ticket> getByorderIdIgnoreStatus(String orderId, PageBean<Ticket> pageBean) {
		return ticketDAO.getByorderIdIgnoreStatus(orderId, pageBean);
	}
	

	/**
	 * 根据批次号查询
	 * */
	@Transactional
	public List<Ticket> getByBatchId(String batchId) {
		return ticketDAO.getByBatchId(batchId);
	}

	/***
	 * 根据订单号,中奖状态查询票数量
	 * 
	 * @param orderId
	 *            订单号
	 * @param ticketResultStatus
	 *            票开奖奖状态
	 * */
	@Transactional
	public Long getByResultStatus(List<Integer> ticketResultStatus, String orderId) {
		return ticketDAO.getByResultStatus(ticketResultStatus, orderId);
	}

	/***
	 * 根据订单号,中奖状态查询票数量
	 * 
	 * @param orderId
	 *            订单号
	 * @param ticketResultStatus
	 *            票开奖状态
	 * */
	@Transactional
	public Long getByResultStatus(Integer ticketResultStatus, String orderId) {
		return ticketDAO.getByResultStatus(ticketResultStatus, orderId);
	}

	/***
	 * 根据订单号,中奖状态查询票
	 * 
	 * @param orderId
	 *            订单号
	 * @param ticketResultStatus
	 *            票中奖状态
	 * */
	@Transactional
	public List<Ticket> getPrizeList(List<Integer> ticketResultStatus, String orderId) {
		return ticketDAO.getPrizeList(ticketResultStatus, orderId);
	}

	/**
	 * 根据彩种,期号,开奖状态查询
	 * 
	 * @param ticketResultStatus
	 *            开奖状态
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param max
	 *            最大条数
	 * */
	@Transactional
	public List<Ticket> getUnPrize(int ticketResultStatus, Integer lotteryType, String phase, int max) {
		return ticketDAO.getUnPrize(ticketResultStatus, lotteryType, phase, max);
	}

	/**
	 * 修改送票成功信息
	 * */
	@Transactional
	public int updateTicketPrintingInfo(Ticket ticket) {
		return ticketDAO.updateTicketPrintingInfo(ticket);
	}

	/**
	 * 修改出票成功信息
	 * @param  isSync 是否同步出票
	 * */
	@Transactional
	public int updateTicketSuccessInfo(Ticket ticket,boolean isSync) {
		return ticketDAO.updateTicketSuccessInfo(ticket, isSync);
	}

	/**
	 * 修改出票失败信息
	 * */
	@Transactional
	public int updateTicketStatusAndFailureInfo(Ticket ticket) {
		return ticketDAO.updateTicketStatusAndFailureInfo(ticket);
	}

	/**
	 * 查询需要转换终端的票
	 * 
	 * @param terminalId
	 *            票所在终端
	 * @param lotteryType
	 *            彩种
	 * @param statusList
	 *            状态
	 * @param minute 距离期结时间
	 * */
	@Transactional
	public List<Ticket> getChangeTerminal(Long terminalId, Integer lotteryType, List<Integer> statusList,Integer minute, PageBean<Ticket> pageBean) {
		return ticketDAO.getChangeTerminal(terminalId, lotteryType, statusList, minute, pageBean);
	}
	/**
	 * 根据终端id,外部票号查询票 
	 * @param terminalId 终端id
	 * @param externalId 外部票号
	  * */
	@Transactional
	public List<Ticket> getByTerminalIdAndExternalId(Long terminalId,String externalId){
		return ticketDAO.getByTerminalIdAndExternalId(terminalId, externalId);
	}
	
	/**
	 * 根据状态,截止时间 查询
	 * @param statusList 票状态
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * 
	 * */
	@Transactional
	public List<Ticket> getByStatusAndTerminateTime(List<Integer> statusList,Date beginTime,Date endTime,PageBean<Ticket> page){
		return ticketDAO.getByStatusAndTerminateTime(statusList, beginTime, endTime, page);
	}
	/**
	 * 修改订单已 算奖状态
	 * @param orderid 订单号
	 * */
	@Transactional
	public int updateTicketsEncash(String orderid){
		return ticketDAO.updateTicketsEncash(orderid);
	}

    /**
     * 根据票状态查询
     * @param  ticketStatus 票状态
     * @param max 最大条数
     * */
    @Transactional
     public List<Ticket> getByStatus(int ticketStatus,int max){
        return ticketDAO.getByStatus(ticketStatus, max);
    }
    /**
     * 根据彩种,票状态查询
     * @param  lotteryType 彩种
     * @param  ticketStatus 票状态
     * @param max 最大条数
     * */
    @Transactional
     public List<Ticket> getByLotteryTypeAndStatus(Integer lotteryType,int ticketStatus,int max){
        return ticketDAO.getByLotteryTypeAndStatus(lotteryType, ticketStatus, max);
    }
	/**
	 *查找未检票的票
	 * @param  lotteryType 彩种
	 * @param terminalId 终端id
	 * **/
	@Transactional
	public List<Ticket> getUnCheckTicket(int lotteryType,Long terminalId,int max,Date date){
		return  ticketDAO.getUnCheckTicket(lotteryType, terminalId, date, max);
	}
	/**
	 * 根据终端id,彩种,状态 查询
	 * @param  terminalId 终端id
	 * @param  lotteryType 彩种
	 * @param status 票状态
	 * @param max 最大条数
	 * */
	@Transactional
	public List<Ticket> getByTerminalIdLotteryTypeAndStatus(Long terminalId,int lotteryType,int status,int max){
		return ticketDAO.getByTerminalIdLotteryTypeAndStatus(terminalId, lotteryType, status, max);
	}
	/**
	 * 根据订单号修票状态
	 * @param  orderId 订单号
	 * @param  status 出票状态
	 * @param  ticketResultStatus 中奖状态
	 * */
	@Transactional
	public  int updateStatusByOrderId(String orderId,int status,int ticketResultStatus){
		return  ticketDAO.updateStatusByOrderId(orderId, status, ticketResultStatus);
	}
	/**
	 * 根据订单号修票中奖状态
	 * @param  orderId 订单号
	 * @param  ticketResultStatus 中奖状态
	 * */
	@Transactional
	public  int updateTicketResultStatusByOrderId(String orderId,int ticketResultStatus){
		return ticketDAO.updateTicketResultStatusByOrderId(orderId, ticketResultStatus);
	}
	
	/**
	 * 根据终端号,票状态查询
	 * @param terminalId 终端号
	 * @param status 票状态
	 * @param max 最大条数
	 * */
	@Transactional
	public List<Ticket> getByTerminalIdAndStatus(Long terminalId,int status,int  max){
		return ticketDAO.getByTerminalIdAndStatus(terminalId, status, max);
	}

	/***
	 * 修改票是否兑奖
	 * @param  ticketId 票号
	 * @param isExchange 是否中奖
	 * */
	@Transactional
	public int updateExchange(String ticketId,int isExchange){
		return ticketDAO.updateExchange(ticketId, isExchange);
	}
	
	@Transactional
	public List<TicketMonitor> getMonitorData(int ticketstatus, String terminalids, Integer num, Integer c){
		List<TicketMonitor> monitors = new ArrayList<TicketMonitor>();
		if(TicketStatus.UNALLOTTED.value == ticketstatus){
			TicketMonitor monitor = new TicketMonitor();
			monitor.setTerminalName("未知终端");
			if(num == 0){
				monitor.setSize(ticketDAO.countByTerminalIdAndStatus(null, ticketstatus));
			}else{
				List<Object[]> ids = ticketDAO.getIdsByTerminalIdAndStatus(null, TicketStatus.UNALLOTTED.value, num);
				monitor.setIds(ids);
				if(ids.size() == num){
					monitor.setSize(ticketDAO.countByTerminalIdAndStatus(null, ticketstatus));
				}else{
					monitor.setSize(new Long(ids.size()));
				}
			}
			monitors.add(monitor);
		}else{
			if(num == 0){
				Map<Long, String> tmap = new HashMap<Long, String>();
				for(String terminalid : terminalids.split(",")){
					String[] ts = terminalid.split("_");
					tmap.put(Long.parseLong(ts[0]), ts[1]);
				}
				List<Object[]> mo = ticketDAO.countsByTerminalIdAndStatus(tmap.keySet(), ticketstatus);
				for (Object[] obj : mo) {
					Long count = (Long)obj[1];
					if(count > 0){
						TicketMonitor monitor = new TicketMonitor();
						monitor.setSize(count);
						monitor.setTerminalName(tmap.get((Long)obj[0]));
						monitors.add(monitor);
					}
				}
			}else{
				for(String terminalid : terminalids.split(",")){
					if(StringUtil.isEmpty(terminalid)){
						continue;
					}
					TicketMonitor monitor = new TicketMonitor();
					String[] ts = terminalid.split("_");
					long terminalId = Long.parseLong(ts[0]);
					monitor.setTerminalName(ts[1]);
					//如果是0 不查票明细
					List<Object[]> ids = ticketDAO.getIdsByTerminalIdAndStatus(terminalId, ticketstatus, num);
					monitor.setIds(ids);
					if(ids.size() == num){
						monitor.setSize(ticketDAO.countByTerminalIdAndStatus(terminalId, ticketstatus));
					}else{
						monitor.setSize(new Long(ids.size()));
					}
					if(monitor.getSize() > 0){
						monitors.add(monitor);
						if(c==1){
							Object[] obj = ids.get(0);
							Date deadline = (Date)obj[3];
							Long dcount = ticketDAO.countByTerminalIdAndStatus(terminalId, ticketstatus, deadline);
							monitor.setTerminalName(monitor.getTerminalName() + "<br>" + DateUtil.format("yyyy-MM-dd HH:mm:ss", deadline)+" ："+ dcount);
						}
					}
				}
				Collections.sort(monitors, new Comparator<TicketMonitor>() {
					@Override
					public int compare(TicketMonitor o1, TicketMonitor o2) {
						List<Object[]> oo1=o1.getIds();
						List<Object[]> oo2=o2.getIds();
		                if (oo1.size()>0&&oo2.size()>0){
							Date d1= (Date) oo1.get(0)[1];
							Date d2=(Date) oo2.get(0)[1];
							return d1.compareTo(d2);
						}
						return 0;
					}
				});
			}
		}
		return monitors;
	}
	/**
	 * 查找渠道未兑奖票
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param terminalId 终端号
	 * @param startTime  开始时间
	 * @param endTime 结束时间
	 * */
	@Transactional
	public List<Ticket> getUnVenderExcechange(Integer lotteryType,String phase,Integer ticketResultStatus,Integer agencyExchange,Long terminalId,Date startTime,Date endTime,PageBean<Ticket> page){
		return ticketDAO.getUnVenderExcechange(lotteryType, phase,ticketResultStatus,agencyExchange,terminalId, startTime, endTime, page);
	}

	public List<Object[]> getMonitorData(Integer lotteryType, Date deadline, List<Integer> status) {
		return ticketDAO.getByLotteryPhaseAndStatusForMonitor(lotteryType, deadline, status);
	}
	
	public TicketMonitor getMonitorDataDetail(Integer lotteryType, String phase, TicketStatus status){
		TicketMonitor monitor = new TicketMonitor();
		monitor.setTerminalName(LotteryType.getLotteryType(lotteryType).name +" "+ phase +" " + status.name);
		List<Object[]> ids = ticketDAO.getByLotteryPhaseAndStatus(lotteryType, phase, status.value, 200);
		if (ids.size()>0){//只查询有值的
			monitor.setIds(ids);
			int size = ids.size();
			if(size == 200){
				monitor.setSize(ticketDAO.getCountByLotteryPhaseAndStatus(lotteryType, phase, status.value));
			}else{
				monitor.setSize(new Long(size+""));
			}
		}
		return monitor;
	}



	/**
	 * 根据创建时间,结束时间,票状态查询
	 * @param createTime 创建时间
	 * @param endTime 结束时间
	 * @param terminalId 终端号
	 * @param status 状态
	 * */
	@Transactional
	public List<Ticket> getByCreateTimeAndEndTimeAndStatus(Date createTime,Date endTime,Long terminalId,Integer status,PageBean<Ticket> page){
		return ticketDAO.getByCreateTimeAndEndTimeAndStatus(createTime, endTime, terminalId, status, page);
	}
}
