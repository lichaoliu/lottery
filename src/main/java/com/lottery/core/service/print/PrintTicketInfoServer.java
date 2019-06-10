package com.lottery.core.service.print;

import com.lottery.core.dao.print.PrintTicketInfoDao;
import com.lottery.core.domain.print.PrintTicketInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fengqinyun on 16/11/12.
 */
@Service
public class PrintTicketInfoServer {
    @Autowired
    private PrintTicketInfoDao dao;
    @Transactional
    public PrintTicketInfo get(String id){
        return dao.find(id);
    }
}
