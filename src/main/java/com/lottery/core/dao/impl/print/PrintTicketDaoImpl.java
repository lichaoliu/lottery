package com.lottery.core.dao.impl.print;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.print.PrintTicketDao;
import com.lottery.core.domain.print.PrintTicket;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * Created by fengqinyun on 16/11/10.
 */
@Repository
public class PrintTicketDaoImpl extends AbstractGenericDAO<PrintTicket,String> implements PrintTicketDao{

	@Override
	public int updateStatusByIds(List<String> ids, Integer status) {
        return getEntityManager().createQuery("update PrintTicket set status=:status where id in (:ids)")
        		.setParameter("status", status).setParameter("ids", ids).executeUpdate();
	}
	
    @Override
    public int updatePrizeInfo(String ticketId, int ticketResultStatus, BigDecimal pretaxPrize, BigDecimal posttaxPrize, int isExchanged) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("ticketResultStatus",ticketResultStatus);
        map.put("pretaxPrize",pretaxPrize);
        map.put("posttaxPrize",posttaxPrize);
        map.put("isExchanged",isExchanged);
        Map<String,Object> whereMap=new HashMap<String,Object>();
        whereMap.put("id", ticketId);
        return update(map, whereMap);
    }

    @Override
	public Long findCountByStatus(List<String> ids, Integer status) {
		return (Long) getEntityManager().createQuery("select count(o) from PrintTicket o where id in (:ids) and status=:status")
				.setParameter("ids", ids)
				.setParameter("status", status)
				.getSingleResult();
	}

	@Override
	public int updateStatusByServerIdsAndStatus(List<String> ids, Integer status, Integer status2) {
		 return getEntityManager().createQuery("update PrintTicket set status=:status where printServer in (:ids) and status=:status2")
	        		.setParameter("status", status)
	        		.setParameter("ids", ids)
	        		.setParameter("status2", status2).executeUpdate();
	}
	@Override
	public int updateExchanged(List<String> ids) {
		return getEntityManager().createQuery("update PrintTicket set isExchanged=1 where id in (:ids) ")
        		.setParameter("ids", ids).executeUpdate();
	}
	@Override
	public int updateNotExchange(List<String> ids) {
		return getEntityManager().createQuery("update PrintTicket set isExchanged=0,exchangeCount=0 where id in (:ids)")
        		.setParameter("ids", ids).executeUpdate();
	}

	@Override
	public int updateTicketIsPriority(List<String> ids) {
		return getEntityManager().createQuery("update PrintTicket set isPriority=1 where id in (:ids)")
        		.setParameter("ids", ids).executeUpdate();
	}

}
