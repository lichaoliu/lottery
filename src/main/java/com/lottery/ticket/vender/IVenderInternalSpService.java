/**
 * 
 */
package com.lottery.ticket.vender;

import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;


public interface IVenderInternalSpService {
	
	/**
	 * 同步出票商出的竞彩的票sp值
	 * @param venderConfig
	 * @param ticketId
	 * @return
	 */
	public String syncVenderTicketSp(IVenderConfig venderConfig, Ticket ticket);		

}
