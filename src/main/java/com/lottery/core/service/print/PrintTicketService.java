package com.lottery.core.service.print;

import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.core.dao.print.PrintTicketDao;
import com.lottery.core.domain.print.PrintTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by fengqinyun on 16/11/10.
 */
@Service
public class PrintTicketService {
    @Autowired
    private PrintTicketDao printTicketDao;
    @Transactional
    public PrintTicket get(String id){
        return  printTicketDao.find(id);
    }
    @Transactional
    public  void save(PrintTicket printTicket){
        printTicketDao.insert(printTicket);
    }
    @Transactional
    public  void update(PrintTicket printTicket){
        printTicketDao.merge(printTicket);
    }

    /**
     * 修改中奖状态
     * @param ticketId  票号
     * @param pretaxPrize 税前奖金
     *@param posttaxPrize 税后奖金
     * @param isExchanged 是否兑奖
     * */
    @Transactional
    public int updatePrizeInfo(String ticketId, int ticketResultStatus, BigDecimal pretaxPrize, BigDecimal posttaxPrize, int isExchanged){
        return printTicketDao.updatePrizeInfo(ticketId, ticketResultStatus, pretaxPrize, posttaxPrize, isExchanged);
    }
    
	@Transactional
	public int updateTicketStatus(List<String> ids, int tostatus) {
		return printTicketDao.updateStatusByIds(ids, tostatus);
	}	
    
    @Transactional
	public int updateTicketCancelled(List<String> ids) {
		Long conut = printTicketDao.findCountByStatus(ids, TicketStatus.PRINT_SUCCESS.value);
    	if(conut > 0){
    		return -1;
    	}
    	return printTicketDao.updateStatusByIds(ids, TicketStatus.CANCELLED.value);
	}
    
    @Transactional
	public int updateExchanged(List<String> ids) {
		return printTicketDao.updateExchanged(ids);
	}
    
    @Transactional
	public int updateNotExchange(List<String> ids) {
		return printTicketDao.updateNotExchange(ids);
	}
    
    @Transactional
	public int updateTicketIsPriority(List<String> ids) {
		return printTicketDao.updateTicketIsPriority(ids);
	}

}
