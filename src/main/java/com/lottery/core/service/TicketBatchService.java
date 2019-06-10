package com.lottery.core.service;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.dao.TicketBatchDAO;
import com.lottery.core.domain.ticket.TicketBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketBatchService {
	@Autowired
	private TicketBatchDAO ticketBatchDao;

	// update TicketBatch set status=:status, sendTime=:sendTime where id=:id
	@Transactional
	public void updateStatusAndSendTime(TicketBatch ticketBatch) {
		ticketBatchDao.updateStatusAndSendTime(ticketBatch);

	}

	// update TicketBatch set status=:status, terminalId=:terminalId where
	// id=:id
	@Transactional
	public void updateStatusAndTerminalId(TicketBatch ticketBatch) {
		ticketBatchDao.updateStatusAndTerminalId(ticketBatch);
		
	}

	/**
	 * 查询需要送票的批次
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param excludeTerminalIdList
	 *            暂停的终端
	 * @param max
	 *            最大条数
	 * */
	@Transactional
	public List<TicketBatch> findForSend(LotteryType lotteryType, List<Long> excludeTerminalIdList, int max) throws Exception {
		return ticketBatchDao.findForSend(lotteryType, excludeTerminalIdList, max);

	}


	/**
	 * 查询需要送票的批次
	 *
	 * @param lotteryType
	 *            彩种
	 * @param terminalId
	 *            终端号
	 * @param max
	 *            最大条数
	 * */
	@Transactional
	public List<TicketBatch> findForSend(LotteryType lotteryType, Long terminalId, int max) throws Exception {
		return ticketBatchDao.findForSend(lotteryType, terminalId, max);

	}
	@Transactional
	public void update(TicketBatch ticketBatch) {
		ticketBatchDao.update(ticketBatch);
	}

	@Transactional
	public void save(TicketBatch ticketBatch) {
		ticketBatchDao.insert(ticketBatch);
	}

	@Transactional
	public TicketBatch get(String id) {
		return ticketBatchDao.find(id);
	}
	/**
	 * 修改批次状态
	 * 
	 * */
	@Transactional
	public int updateTicketBatchStatus(TicketBatch ticketBatch){
		return ticketBatchDao.updateTicketBatchStatus(ticketBatch);
	}
	
	/**
	 * 查找处于进入送票队列等待状态的批次供回收处理
	 * @param lotteryType
	 * @return
	 */
	@Transactional
	public List<TicketBatch> findForSendRecycle(int lotteryType){
		return ticketBatchDao.findForSendRecycle(lotteryType);
	}

	@Transactional
	public int updateStatus(String id,int ticketBatchStatus){
		return ticketBatchDao.updateStatus(id, ticketBatchStatus);
	}

}
