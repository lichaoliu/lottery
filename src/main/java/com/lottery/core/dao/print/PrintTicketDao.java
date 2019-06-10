package com.lottery.core.dao.print;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.print.PrintTicket;


/**
 * Created by fengqinyun on 16/11/10.
 */
public interface PrintTicketDao extends IGenericDAO<PrintTicket,String>{
	
	public int updateStatusByIds(List<String> ids, Integer status);
	public int updateStatusByServerIdsAndStatus(List<String> serverIds, Integer status, Integer status2);
    /**
     * 修改中奖状态
     * @param ticketId  票号
     * @param pretaxPrize 税前奖金
     *@param posttaxPrize 税后奖金
     * @param isExchanged 是否兑奖
     * */

    public int updatePrizeInfo(String ticketId, int ticketResultStatus, BigDecimal pretaxPrize, BigDecimal posttaxPrize, int isExchanged);
    
    
    public Long findCountByStatus(List<String> ids, Integer status);
    
	public int updateExchanged(List<String> ids);
	
	public int updateNotExchange(List<String> ids);
	
	public int updateTicketIsPriority(List<String> ids);
}
