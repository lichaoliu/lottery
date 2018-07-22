package com.lottery.core.service;

import com.lottery.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.TicketBatchSendLogDAO;
import com.lottery.core.domain.ticket.TicketBatchSendLog;

@Service
public class TicketBatchSendLogService {
    private final Logger warnlog= LoggerFactory.getLogger("lottery-warn");
    @Autowired
	private TicketBatchSendLogDAO ticketBatchSendLogDAO;
    @Autowired
    private IdGeneratorDao idGeneratorDao;
    @Transactional
    public void save(TicketBatchSendLog ticketBatchSendLog){
    //	String id=idGeneratorDao.getTicketBatchSendLogId();
    //	ticketBatchSendLog.setId(id);
    //	ticketBatchSendLogDAO.insert(ticketBatchSendLog);
        String json= JsonUtil.toJson(ticketBatchSendLog);
        warnlog.error("批次送票日志:{}",json);
    }
    @Transactional
    public void update(TicketBatchSendLog ticketBatchSendLog){
    	ticketBatchSendLogDAO.merge(ticketBatchSendLog);
    }
    
    public TicketBatchSendLog get(String id){
    	return ticketBatchSendLogDAO.find(id);
    }
    
}

