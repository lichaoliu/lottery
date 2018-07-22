package com.lottery.core.service;

import com.lottery.common.util.JsonUtil;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.TicketAllotLogDAO;
import com.lottery.core.domain.ticket.TicketAllotLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketAllotLogService {
    private final Logger warnlog= LoggerFactory.getLogger("lottery-warn");
    @Autowired
	private TicketAllotLogDAO ticetAllotLogDAO;
    @Autowired
    private IdGeneratorDao idGeneratorDao;
    @Transactional
    public void save(TicketAllotLog ticketAllotLong){
    //	ticketAllotLong.setId(idGeneratorDao.getTicketAllotLogId());
    //	ticetAllotLogDAO.insert(ticketAllotLong);
        String json= JsonUtil.toJson(ticketAllotLong);
        warnlog.error("分票日志:{}",json);
    }
    @Transactional
    public void update(TicketAllotLog ticketAllotLong){
    	ticetAllotLogDAO.merge(ticketAllotLong);
    }



    
}
