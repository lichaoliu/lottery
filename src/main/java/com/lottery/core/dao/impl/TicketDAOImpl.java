package com.lottery.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.lottery.common.PageBean;
import com.lottery.common.contains.AgencyExchanged;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TicketResultStatus;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.TicketDAO;
import com.lottery.core.domain.ticket.Ticket;

@Repository
public class TicketDAOImpl extends AbstractGenericDAO<Ticket, String> implements TicketDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6064304044303686382L;


	@Override
	public List<Ticket> getByorderId(String orderId) {
		List<Ticket> list = new ArrayList<Ticket>();
		PageBean<Ticket> pageBean = new PageBean<Ticket>();
		int max = 15;
		pageBean.setMaxResult(max);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		int page = 1;
		while (true) {
			List<Ticket> orders = null;
			pageBean.setPageIndex(page);
			try {
				orders = findPageByCondition(map, pageBean);
			} catch (Exception e) {
			}
			if (orders != null && orders.size() > 0) {
				list.addAll(orders);
				if (orders.size() < max) {
					break;
				}
			} else {
				break;
			}
			page++;
		}
		return list;
	}



	@Override
	public int updateTicketsEncash(String orderId) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("isExchanged", YesNoStatus.yes.value);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		String whereString = " orderId=:orderId and posttaxPrize > 0";
		whereMap.put("orderId", orderId);
		return update(conditionMap, whereString, whereMap);
	}

	@Override
	public int updateTicketPrize(String orderId) {
		List<Ticket> list = this.getByorderId(orderId);
		int i = 0;
		for (Ticket ticket : list) {
			ticket.setIsExchanged(YesNoStatus.no.getValue());
			ticket.setTicketResultStatus(TicketResultStatus.not_draw.getValue());
			ticket.setPosttaxPrize(BigDecimal.ZERO);
			ticket.setPretaxPrize(BigDecimal.ZERO);
			ticket.setAgencyExchanged(AgencyExchanged.exchange_no.value);
			ticket.setPrizeDetail(null);
			this.update(ticket);
			i++;
		}
		return i;
	}



	@Override
	public Long getCountNotCalByOrderId(String orderId) {
		return this.getEntityManager().createQuery("select count(o.id) from Ticket o where o.orderId=:orderid and o.ticketResultStatus=:ticketResultStatus and o.status=:status", Long.class).setParameter("orderid", orderId).setParameter("ticketResultStatus", TicketResultStatus.not_draw.value)
				.setParameter("status", TicketStatus.PRINT_SUCCESS.getValue()).getSingleResult();
	}

	@Override
	public List<Ticket> getByorderId(String orderId, PageBean<Ticket> page) {
		String orderby = " order by id ";
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("orderId", orderId);
		whereMap.put("status", TicketStatus.PRINT_SUCCESS.getValue());
		return findPageByCondition(whereMap, page, orderby);
	}



	@Override
	public BigDecimal getSumAmountByOrderIdAndStatus(String orderId, int ticketStatus) {
		TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(o.amount) from Ticket o where  o.orderId=:orderid and o.status=:status ", BigDecimal.class);
		query.setParameter("orderid", orderId);
		query.setParameter("status", ticketStatus);
		return query.getSingleResult();
	}

	@Override
	public BigDecimal getSumPosttaxPrizeByOrderIdAndStatus(String orderId, TicketStatus status) {
		TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(o.posttaxPrize) from Ticket o where  o.orderId=:orderid and o.status=:status ", BigDecimal.class);
		query.setParameter("orderid", orderId);
		query.setParameter("status", status.getValue());
		return query.getSingleResult();
	}

	@Override
	public BigDecimal getSumPretaxPrizeByOrderIdAndStatus(String orderId, TicketStatus status) {
		TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(o.pretaxPrize) from Ticket o where  o.orderId=:orderid and o.status=:status ", BigDecimal.class);
		query.setParameter("orderid", orderId);
		query.setParameter("status", status.getValue());
		return query.getSingleResult();
	}

	@Override
	public Long getCountWinBigTicket(String orderid, TicketStatus status) {
		return this.getEntityManager().createQuery("select count(o.id) from Ticket o where o.orderId=:orderid and o.status=:status and o.ticketResultStatus=:ticketResultStatus", Long.class).setParameter("orderid", orderid).setParameter("status", status.getValue())
				.setParameter("ticketResultStatus", TicketResultStatus.win_big.value).getSingleResult();
	}

	

	@Override
	public int updateTicketStatusByOrderId(String orderId, int fromTicketStatus, int toTicketStatus) {
		List<Ticket> list = this.getByorderIdAndStatus(orderId, fromTicketStatus);
		int i = 0;
		for (Ticket ticket : list) {
			ticket.setStatus(toTicketStatus);
			this.update(ticket);
			i++;
		}
		return i;
	}

	@Override
	public List<Ticket> getByBatchIdAndStatus(String batchTicketId, int ticketStatus, PageBean<Ticket> page) {
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("batchId", batchTicketId);
		contentMap.put("status", ticketStatus);
		return findPageByCondition(contentMap, page);
	}

	@Override
	public List<Ticket> getByorderIdAndStatus(String orderId, Integer ticketStatus) {
		List<Ticket> list = new ArrayList<Ticket>();
		PageBean<Ticket> pageBean = new PageBean<Ticket>();
		int max = 15;
		pageBean.setMaxResult(max);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("status", ticketStatus);
		int page = 1;
		while (true) {
			List<Ticket> orders = null;
			pageBean.setPageIndex(page);
			try {
				orders = findPageByCondition(map, pageBean);
			} catch (Exception e) {

			}
			if (orders != null && orders.size() > 0) {
				list.addAll(orders);
				if (orders.size() < max) {
					break;
				}
			} else {
				break;
			}
			page++;
		}
		return list;
	}

	@Override
	public List<Ticket> getByBatchId(String batchId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("batchId", batchId);
		return findByCondition(map);
	}

	@Override
	public List<Ticket> getUnCheckTicket(Long terminalId, List<LotteryType> lotteryTypeList, int max, Date date) {
		List<Integer> typeList = new ArrayList<Integer>();
		if (lotteryTypeList != null && lotteryTypeList.size() > 0) {
			for (LotteryType lottertType : lotteryTypeList) {
				typeList.add(lottertType.getValue());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();

		StringBuffer sb = new StringBuffer();
		sb.append("status=:status and  terminalId=:terminalId ");
		if (typeList.size() > 0) {
			if (typeList.size() == 1) {
				sb.append("and lotteryType =:lotteryType ");
				map.put("lotteryType", typeList.get(0));
			} else {
				sb.append("and lotteryType in(:lotteryType) ");
				map.put("lotteryType", typeList);
			}
		}
		map.put("status", TicketStatus.PRINTING.getValue());
		map.put("terminalId", terminalId);
		sb.append("and  sendTime<=:sendTime ");
		map.put("sendTime", date);
		return findByCondition(max, sb.toString(), map);
	}

	@Override
	public List<Ticket> getPrizeList(List<Integer> ticketResultStatus, String orderId) {
		String whereString = "ticketResultStatus in (:ticketResultStatus) and orderId=:orderId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ticketResultStatus", ticketResultStatus);
		map.put("orderId", orderId);
		return findByCondition(whereString, map);
	}

	@Override
	public Long getByResultStatus(List<Integer> ticketResultStatus, String orderId) {
		String whereString = "ticketResultStatus in (:ticketResultStatus) and orderId=:orderId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ticketResultStatus", ticketResultStatus);
		map.put("orderId", orderId);
		return getCountByCondition(whereString, map);
	}

	@Override
	public Long getByResultStatus(Integer ticketResultStatus, String orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", TicketStatus.PRINT_SUCCESS.value);
		map.put("ticketResultStatus", ticketResultStatus);
		map.put("orderId", orderId);
		return getCountByCondition(map);
	}

	@Override
	public List<Ticket> getUnPrize(int ticketResultStatus, Integer lotteryType, String phase, int max) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ticketResultStatus", ticketResultStatus);
		map.put("status", TicketStatus.PRINT_SUCCESS.value);
		map.put("phase", phase);
		map.put("lotteryType", lotteryType);
		return findByCondition(max, map);
	}

	@Override
	public int updateTicketStatus(Ticket ticket) {
		Map<String, Object> contantMap = new HashMap<String, Object>();
		contantMap.put("status", ticket.getStatus());
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("id", ticket.getId());
		return update(contantMap, whereMap);
	}

	@Override
	public int updateTicketPrintingInfo(Ticket ticket) {
		Map<String, Object> contantMap = new HashMap<String, Object>();
		contantMap.put("status", ticket.getStatus());
		contantMap.put("sendTime", ticket.getSendTime());
		contantMap.put("terminalId", ticket.getTerminalId());
		if (ticket.getExternalId() != null) {
			contantMap.put("externalId", ticket.getExternalId());
		}
		if (StringUtils.isNotBlank(ticket.getPasswd()))
			contantMap.put("passwd", ticket.getPasswd());
		contantMap.put("terminalType", ticket.getTerminalType());
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("id", ticket.getId());
		return update(contantMap, whereMap);
	}

	@Override
	public int updateTicketSuccessInfo(Ticket ticket,boolean isSync) {
		Map<String, Object> contantMap = new HashMap<String, Object>();
		contantMap.put("status", ticket.getStatus());
		contantMap.put("printTime", ticket.getPrintTime());
		contantMap.put("terminalId", ticket.getTerminalId());
		if (isSync&&ticket.getSendTime()!=null){
			contantMap.put("sendTime",ticket.getSendTime());
		}

		if (StringUtils.isNotBlank(ticket.getExternalId()))
			contantMap.put("externalId", ticket.getExternalId());

		if (StringUtils.isNotBlank(ticket.getPeilv()))
			contantMap.put("peilv", ticket.getPeilv());
		if (ticket.getTerminalType() != null)
			contantMap.put("terminalType", ticket.getTerminalType());
		if (StringUtils.isNotBlank(ticket.getPasswd()))
			contantMap.put("passwd", ticket.getPasswd());
		if(StringUtils.isNotBlank(ticket.getMd5Code()))
			contantMap.put("md5Code", ticket.getMd5Code());
		if(ticket.getMachineCode()!=null)
			contantMap.put("machineCode", ticket.getMachineCode());
		if(ticket.getMachineCode()!=null)
			contantMap.put("sellRunCode", ticket.getSellRunCode());
		if(ticket.getSerialId()!=null){
			contantMap.put("serialId", ticket.getSerialId());
		}
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("id", ticket.getId());
		return update(contantMap, whereMap);
	}



	@Override
	public int updateTicketStatusAndFailureInfo(Ticket ticket) {
		Map<String, Object> contantMap = new HashMap<String, Object>();
		contantMap.put("status", ticket.getStatus());
		contantMap.put("printTime", new Date());
		if (ticket.getFailureMessage() != null)
			contantMap.put("failureMessage", ticket.getFailureMessage());

		if (ticket.getFailureType() != null)
			contantMap.put("failureType", ticket.getFailureType());
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("id", ticket.getId());
		return update(contantMap, whereMap);
	}

	@Override
	public List<Ticket> getChangeTerminal(Long terminalId, Integer lotteryType, List<Integer> statusList, Integer minute,PageBean<Ticket> pageBean) {
		StringBuffer whereSql = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		whereSql.append("status in (:status)");
		if (lotteryType != null && lotteryType != LotteryType.ALL.value) {
			whereSql.append(" and lotteryType=:lotteryType");
			map.put("lotteryType", lotteryType);
		}
		
		if(minute!=null&&minute>0){
			whereSql.append(" and deadline<=:deadline");
			Calendar calendar=Calendar.getInstance();
			calendar.add(Calendar.MILLISECOND, minute*1000);
			map.put("deadline", calendar.getTime());
		}
		
		whereSql.append(" and terminalId=:terminalId");
		map.put("status", statusList);
		map.put("terminalId", terminalId);
		return findPageByCondition(whereSql.toString(), map, pageBean);

	}

	@Override
	public int updateTicketStatus(int status, String id) {
		Map<String, Object> contantMap = new HashMap<String, Object>();
		contantMap.put("status",status);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("id", id);
		return update(contantMap, whereMap);
		
	}

	@Override
	public List<Ticket> findToAllot(int lotteryType, String phase, int max) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryType", lotteryType);
		map.put("phase", phase);
		map.put("status", TicketStatus.UNALLOTTED.getValue());
		return findByCondition(max, map);
	
	}

	@Override
	public Long getCountByOrderIdAndStatus(String orderId, int ticketStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("status", ticketStatus);
		return getCountByCondition(map);
	}

	@Override
	public Long getCountByOrderId(String orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		return getCountByCondition(map);
	}

	@Override
	public List<Ticket> getByTerminalIdAndExternalId(Long terminalId, String externalId) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("terminalId", terminalId);
		whereMap.put("externalId", externalId);
		return findByCondition(whereMap);
	}

	@Override
	public List<Ticket> getByStatusAndTerminateTime(List<Integer> statusList, Date beginTime, Date endTime, PageBean<Ticket> page) {
		String whereString="status in (:status) and terminateTime>=:beginTime and terminateTime<=:endTime";
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("status", statusList);
		whereMap.put("beginTime", beginTime);
		whereMap.put("endTime", endTime);
		return findPageByCondition(whereString, whereMap, page);
	}

	@Override
	public int updateTicketRewardStatus(int rewarStatus, String orderid) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("isExchanged",rewarStatus);
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("orderId", orderid);
		return update(map, whereMap);
	}

    @Override
    public List<Ticket> getByStatus(Integer ticketStatus, int max) {
        Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("status", ticketStatus);
        return findByCondition(max,whereMap);
    }

    @Override
    public List<Ticket> getByLotteryTypeAndStatus(Integer lotteryType,int ticketStatus, int max) {
        Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("status", ticketStatus);
        whereMap.put("lotteryType", lotteryType);
        return findByCondition(max,whereMap);
    }

	@Override
	public List<Ticket> getUnCheckTicket(int lotteryType,Long terminalId,Date date,int max) {
		String whereSql="lotteryType =:lotteryType and terminalId=:terminalId and status=:status and  sendTime<=:sendTime order by deadline asc ";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("lotteryType", lotteryType);
		map.put("status", TicketStatus.PRINTING.getValue());
		map.put("terminalId", terminalId);
		map.put("sendTime", date);

		return findByCondition(max, whereSql, map);
	}

	@Override
	public int updateOdd(String ticketid, String odd) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("peilv",odd);
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("id", ticketid);
		return update(map, whereMap);
		
	}

	@Override
	public List<Ticket> getByTerminalIdLotteryTypeAndStatus(Long terminalId, int lotteryType, int status, int max) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("lotteryType", lotteryType);
		map.put("status", status);
		map.put("terminalId", terminalId);
		return findByCondition(max,map);
	}

	@Override
	public int updateStatusByOrderId(String orderId, int status, int ticketResultStatus) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("status",status);
		map.put("ticketResultStatus",ticketResultStatus);
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("orderId", orderId);
		return update(map, whereMap);
	}

	@Override
	public int updateTicketResultStatusByOrderId(String orderId, int ticketResultStatus) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ticketResultStatus",ticketResultStatus);
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("orderId", orderId);
		return update(map, whereMap);
	}

	@Override
	public List<Ticket> getByorderIdIgnoreStatus(String orderId,
			PageBean<Ticket> page) {
		String orderby = " order by id ";
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("orderId", orderId);
		return findPageByCondition(whereMap, page, orderby);
	}



	@Override
	public List<Ticket> getByTerminalIdAndStatus(Long terminalId, int status, int max) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("status",status);
		map.put("terminalId", terminalId);
		return findByCondition(max, map);
	}

	@Override
	public int updateExchange(String ticketId, int isExchange) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("isExchanged",isExchange);
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("id", ticketId);
		return update(map, whereMap);
	}


	@Override
	public List<Ticket> findWinBigTicket(String orderId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderId",orderId);
		map.put("ticketResultStatus", TicketResultStatus.win_big.value);
		return findByCondition(map);
	}
	
	@Override
	public List<Object[]> getIdsByTerminalIdAndStatus(Long terminalId, int status, int max) {
		String sql = "select id,createTime,sendTime,deadline,lotteryType,amount from Ticket o where o.status = :status and deadline>=:deadline";
		if(terminalId != null){
			sql += " and terminalId = :terminalId";
		}
		Query query = entityManager.createQuery(sql+" order by deadline, sendTime");
		query.setParameter("status", status);
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MINUTE, (-1)*120);
		query.setParameter("deadline",calendar.getTime());
		if(terminalId != null){
			query.setParameter("terminalId", terminalId);
		}
		query.setMaxResults(max);
		return query.getResultList();
	}
	
	public Long countByTerminalIdAndStatus(Long terminalId, int status) {
		String sql = "select count(id) from Ticket o where o.status = :status and deadline>=:deadline";
		if(terminalId != null){
			sql += " and terminalId = :terminalId";
		}
		TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
		query.setParameter("status", status);
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MINUTE, (-1)*120);
		query.setParameter("deadline",calendar.getTime());
		if(terminalId != null){
			query.setParameter("terminalId", terminalId);
		}
		return query.getSingleResult();
	}
	
	public Long countByTerminalIdAndStatus(Long terminalId, int status, Date deadline) {
		String sql = "select count(id) from Ticket o where o.status = :status and terminalId = :terminalId and deadline=:deadline";
		TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
		query.setParameter("status", status);
		query.setParameter("terminalId", terminalId);
		query.setParameter("deadline",deadline);
		return query.getSingleResult();
	}

	public List<Object[]> countsByTerminalIdAndStatus(Set<Long> terminalIds, int status){
		String sql = "select terminalId, count(id) from Ticket o where o.status = :status  and deadline>=:deadline and terminalId in (:terminalIds) group by terminalId";
		Query query = entityManager.createQuery(sql);
		query.setParameter("status", status);
		query.setParameter("terminalIds", terminalIds);
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MINUTE, (-1)*120);
		query.setParameter("deadline",calendar.getTime());
		return  query.getResultList();
	}
	
	@Override
	public List<Ticket> getUnVenderExcechange(Integer lotteryType, String phase,Integer ticketResultStatus,Integer agencyExchange,Long terminalId, Date startTime, Date endTime,PageBean<Ticket> page) {
		StringBuffer stringBuff=new StringBuffer();
		stringBuff.append("agencyExchanged=:agencyExchanged and terminalId=:terminalId");

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("agencyExchanged", agencyExchange);
		whereMap.put("terminalId", terminalId);
		if (lotteryType!=null&&lotteryType!=0){
			stringBuff.append(" and lotteryType=:lotteryType");
			whereMap.put("lotteryType", lotteryType);
		}
		if (StringUtils.isNotBlank(phase)){
			stringBuff.append(" and phase=:phase");
			whereMap.put("phase", phase);
		}

		if (ticketResultStatus!=null){
			stringBuff.append(" and ticketResultStatus=:ticketResultStatus");
			whereMap.put("ticketResultStatus", ticketResultStatus);
		}

		if (startTime!=null){
			stringBuff.append(" and createTime>=:startTime");
			whereMap.put("startTime", startTime);
		}
		if (endTime!=null){
			stringBuff.append(" and createTime<=:endTime");
			whereMap.put("endTime", endTime);
		}

		return findPageByCondition(stringBuff.toString(), whereMap, page);
	}

	@Override
	public List<Ticket> getByCreateTimeAndEndTimeAndStatus(Date createTime, Date endTime, Long terminalId,Integer status, PageBean<Ticket> page) {
		String sql="terminalId=:terminalId and status=:status and  createTime>=:createTime and deadline<=:deadline";
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("terminalId", terminalId);
		whereMap.put("createTime", createTime);
		whereMap.put("deadline", endTime);
		whereMap.put("status", status);
		return findPageByCondition(sql,whereMap,page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getByLotteryPhaseAndStatusForMonitor(Integer lotteryType, Date deadline, List<Integer> status) {
		String sql = "select status,phase, deadline, count(*), sum(amount) from Ticket o where deadline > :deadline and o.lotteryType = :lotteryType and status in (:status) group by status,phase,deadline ";
		Query query = entityManager.createQuery(sql);
		query.setParameter("lotteryType", lotteryType);
		query.setParameter("status", status);
		query.setParameter("deadline", deadline);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getByLotteryPhaseAndStatus(Integer lotteryType, String phase, Integer status, Integer max) {
		String sql = "select id, agentId, amount, ticketEndTime, terminalId, sendTime from Ticket o where lotteryType = :lotteryType and phase = :phase and o.status = :status ";
		Query query = entityManager.createQuery(sql + " order by createTime asc");
		query.setParameter("lotteryType", lotteryType).setParameter("phase", phase).setParameter("status", status).setMaxResults(max);
		return query.getResultList();
	}
	
	@Override
	public Long getCountByLotteryPhaseAndStatus(Integer lotteryType, String phase, Integer status) {
		String sql = "select count(id) from Ticket o where lotteryType = :lotteryType and phase = :phase and o.status = :status ";
		TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
		query.setParameter("lotteryType", lotteryType).setParameter("phase", phase).setParameter("status", status);
		return query.getSingleResult();
	}
}
