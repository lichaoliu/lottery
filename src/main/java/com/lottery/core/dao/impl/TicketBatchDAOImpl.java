package com.lottery.core.dao.impl;

import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.TicketBatchDAO;
import com.lottery.core.domain.ticket.TicketBatch;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TicketBatchDAOImpl extends AbstractGenericDAO<TicketBatch, String> implements TicketBatchDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8378665824182570708L;

	@Override
	public int updateStatusAndSendTime(TicketBatch ticketBatch) {
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("status", ticketBatch.getStatus());
		contentMap.put("sendTime", ticketBatch.getSendTime());
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("id", ticketBatch.getId());
		return update(contentMap, whereMap);

	}

	@Override
	public int updateStatusAndTerminalId(TicketBatch ticketBatch) {
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("status", ticketBatch.getStatus());
		contentMap.put("terminalId", ticketBatch.getTerminalId());
		contentMap.put("terminalTypeId", ticketBatch.getTerminalTypeId());
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("id", ticketBatch.getId());
		return update(contentMap, whereMap);

	}

	@Override
	public List<TicketBatch> findForSend(LotteryType lotteryType, List<Long> excludeTerminalIdList,int max) {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();

		sb.append("lotteryType=:lotteryType and status=:status");
		if (excludeTerminalIdList != null && excludeTerminalIdList.size() > 0) {
			sb.append(" and  terminalId not in(:terminalList)");
			map.put("terminalList", excludeTerminalIdList);
		}
		sb.append("  and terminateTime>=:terminateTime  order by terminateTime asc");
		map.put("lotteryType", lotteryType.getValue());
		map.put("status", TicketBatchStatus.SEND_WAITING.value);
		map.put("terminateTime", new Date());
		return findByCondition(max, sb.toString(), map);
	}


	public List<TicketBatch> findForSend(LotteryType lotteryType, Long terminalId, int max){

		Map<String, Object> map = new HashMap<String, Object>();

		String sql="lotteryType=:lotteryType and status=:status and  terminalId=:terminalId and terminateTime>=:terminateTime  order by terminateTime asc";
		map.put("lotteryType", lotteryType.getValue());
		map.put("status", TicketBatchStatus.SEND_WAITING.value);
		map.put("terminateTime", new Date());
		map.put("terminalId", terminalId);
		return findByCondition(max, sql, map);
	}

	@Override
	public int updateStatus(String id, int ticketBatchStatus) {
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("status", ticketBatchStatus);
		contentMap.put("updateTime", new Date());
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("id", id);
		return update(contentMap, whereMap);
	}

	@Override
	public int updateTicketBatchStatus(TicketBatch ticketBatch) {
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("status", ticketBatch.getStatus());
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("id", ticketBatch.getId());
		return update(contentMap, whereMap);
	}

	@Override
	public List<TicketBatch> findForSendRecycle(int lotteryType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryType", lotteryType);
		map.put("status", TicketBatchStatus.SEND_QUEUED.value);
		return findByCondition(map);
	}

	
}
