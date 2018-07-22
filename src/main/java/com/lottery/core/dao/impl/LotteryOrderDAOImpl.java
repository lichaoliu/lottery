package com.lottery.core.dao.impl;

import com.lottery.common.PageBean;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.dao.LotteryOrderDAO;
import com.lottery.core.domain.ticket.LotteryOrder;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.*;

@Repository
public class LotteryOrderDAOImpl extends AbstractGenericDAO<LotteryOrder, String> implements LotteryOrderDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 252513138047765091L;

	@Override
	public int updateOrderLastMatchNum(String orderid, String matchno) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("lastMatchNum", Long.parseLong(matchno));
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("id", orderid);
		return update(conditionMap, whereMap);
	}

	@Override
	public List<LotteryOrder> getByLotteryPhaseMatchNumAndStatus(Integer lotteryType,String phase,String matchNum,List<Integer> orderStatusList,List<Integer> orderResultStatus,PageBean<LotteryOrder> page) {
		StringBuffer whereSql = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		whereSql.append("lotteryType=:lotteryType");
        if (StringUtils.isNotBlank(phase)){
            whereSql.append(" and phase=:phase");
            map.put("phase", phase);
        }
		if(StringUtils.isNotBlank(matchNum)){
			whereSql.append(" and matchNums like :matchNums");
			map.put("matchNums", "%" + matchNum + "%");
		}

		if(orderStatusList!=null&&orderStatusList.size()>0){
			whereSql.append(" and orderStatus in (:orderStatus)");
			map.put("orderStatus", orderStatusList);
		}
		
		if(orderResultStatus!=null&&orderResultStatus.size()>0){
			whereSql.append(" and orderResultStatus in (:orderResultStatus)");
			map.put("orderResultStatus", orderResultStatus);
		}
		map.put("lotteryType", lotteryType);

		return findPageByCondition(whereSql.toString(), map, page);
	}

	
	

	@Override
	public List<LotteryOrder> getByStatus(Date startDate, Date endDate, Integer status, PageBean<LotteryOrder> page) {
		String whereSql = "orderStatus=:orderStatus  and receiveTime>:startTime and receiveTime<:endTime ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderStatus", status);
		map.put("startTime", startDate);
		map.put("endTime", endDate);
		return this.findPageByCondition(whereSql, map, page);
	}

	

	@Override
	public List<LotteryOrder> findBeidanByAndHemaiNotNull(String phase, Long matchNum) {
		List<LotteryOrder> list = findByCondition("phase = ? and firstMatchNum = ? and hemaiid is not null", new Object[] { phase, matchNum });
		return list;
	}

	@Override
	public List<LotteryOrder> findJcByfirstMatchNumAndHemaiNotNull(Long matchNum) {
		List<LotteryOrder> list = this.findByCondition("firstMatchNum = ? and hemaiid is not null", new Object[] { matchNum });
		return list;
	}

	@Override
	public List<LotteryOrder> getOrderListByUserno(String userno, Date startTime, Date endTime, PageBean<LotteryOrder> page) {
		String whereSql = "userno=:userno and receiveTime>=:startTime and receiveTime<:endTime";
		String orderBy = "order by receiveTime desc";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userno", userno);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return findPageByCondition(whereSql, map, page, orderBy);
	}

	@Override
	public List<LotteryOrder> getByLotteryTypePhaseBetTypeAndResultStatus(Integer lotteryType, String phase, int betType, int resultstatus, PageBean<LotteryOrder> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryType", lotteryType);
		map.put("phase", phase);
		map.put("betType", betType);
		map.put("orderResultStatus", resultstatus);
		return findPageByCondition(map, page);
	}

	

	@Override
	public Object[] getOrderUserBet(String userno, int lotteryType, int orderStatus, Date startDate, Date endDate) {
		String whereSql = "select count(o),sum(o.amount),sum(o.smallPosttaxPrize) from LotteryOrder o where  o.userno=:userno and o.orderStatus=:orderStatus  and lotteryType=:lotteryType and receiveTime>=:startDate and receiveTime<:endDate";

		Query query = getEntityManager().createQuery(whereSql);
		query.setParameter("userno", userno);
		query.setParameter("lotteryType", lotteryType);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("orderStatus", orderStatus);
		return (Object[]) query.getSingleResult();

	}

	@Override
	public List<LotteryOrder> getByStatus(String userno, int lotteryType, Date startDate, Date endDate, int status) {
		String whereSql = "userno=:userno and lotteryType=:lotteryType and orderStatus=:orderStatus and receiveTime>=:startDate and receiveTime<:endDate";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userno", userno);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("lotteryType", lotteryType);
		map.put("orderStatus", status);
		return findByCondition(whereSql, map);

	}

	

	@Override
	public List<LotteryOrder> getByStatusAndType(int lotteryType, String phase, List<Integer> orderStatusList, List<Integer> orderResultStatusList, String sortType,Long lastMatchNum,PageBean<LotteryOrder> page) {
		StringBuffer sb=new StringBuffer();
		sb.append("orderStatus in(:orderStatus) and orderResultStatus in(:orderResultStatus) ");
		Map<String, Object> map = new HashMap<String, Object>();
		if(lastMatchNum!=null){
			sb.append(" and lastMatchNum<=:lastMatchNum");
			map.put("lastMatchNum", lastMatchNum);
		}
		String orderby=null;
		if(StringUtils.isNotBlank(sortType)){
			 orderby="order by receiveTime "+sortType;
		}
		sb.append(" and phase=:phase and lotteryType=:lotteryType");
		map.put("orderStatus", orderStatusList);
		map.put("orderResultStatus", orderResultStatusList);
		map.put("lotteryType", lotteryType);
		map.put("phase", phase);
		return findPageByCondition(sb.toString(), map, page, orderby);
	}

	@Override
	public List<LotteryOrder> getByStatusAndDate(int lotteryType, String phase, List<Integer> orderStatusList, List<Integer> orderResultStatusList, Date beginTime, Date endTime,Long lastMatchNum, PageBean<LotteryOrder> page) {
		StringBuffer sb=new StringBuffer();
		sb.append("orderStatus in(:orderStatus) and orderResultStatus in(:orderResultStatus) and lotteryType=:lotteryType");
		Map<String, Object> map = new HashMap<String, Object>();
		if(lastMatchNum!=null){
			sb.append(" and lastMatchNum<=:lastMatchNum");
			map.put("lastMatchNum", lastMatchNum);
		}
		if (StringUtils.isNotBlank(phase)){
			sb.append(" and phase=:phase");
			map.put("phase", phase);
		}
		sb.append(" and receiveTime>=:beginTime and receiveTime<:endTime");
		
		map.put("orderStatus", orderStatusList);
		map.put("orderResultStatus", orderResultStatusList);
		map.put("endTime", endTime);
		map.put("beginTime", beginTime);
		map.put("lotteryType", lotteryType);

		return findPageByCondition(sb.toString(), map, page);
	}

	@Override
	public List<LotteryOrder> getOrder(String userno, String startTime,
			String endTime, PageBean<LotteryOrder> page, String type,
			String lotteryType, List<Integer> orderStatusList,
			List<Integer> orderResultStatusList) {
		Map<String, Object> map=new HashMap<String,Object>();
		String sql =" userno=:userno";
		if(orderStatusList.size() > 0){
			sql += " and orderStatus in (:orderStatusList)";
			map.put("orderStatusList", orderStatusList);
		}
		if(orderResultStatusList.size() > 0){
			sql += " and orderResultStatus in (:orderResultStatusList)";
			map.put("orderResultStatusList", orderResultStatusList);
		}
		sql += " and receiveTime>=:startTime and receiveTime<=:endTime ";
		if(!lotteryType.equals("")&&!lotteryType.equals("-1")){
			sql += " and lotteryType=:lotteryType";
			map.put("lotteryType", Integer.parseInt(lotteryType));
		}
		String orderByString = "order by receiveTime desc";
		Date startDate=CoreDateUtils.parseDate(startTime, CoreDateUtils.DATE);
		Date endDate=CoreDateUtils.parseDate(endTime, CoreDateUtils.DATE);
		if(endDate.getTime()-(new Date().getTime())>0){
			endDate=new Date();
		}
		map.put("userno", userno);
		map.put("startTime", startDate);
		map.put("endTime", endDate);
		
		return findPageByCondition(sql,map,page,orderByString);
	}
	
	
	@Override
	public List<LotteryOrder> getOrder(String userno, String startTime,
			String endTime, PageBean<LotteryOrder> page, String type,
			List<Integer> lotteryType, List<Integer> orderStatusList,
			List<Integer> orderResultStatusList) {
		Map<String, Object> map=new HashMap<String,Object>();
		String sql =" userno=:userno";
		if(orderStatusList.size() > 0){
			sql += " and orderStatus in (:orderStatusList)";
			map.put("orderStatusList", orderStatusList);
		}
		if(orderResultStatusList.size() > 0){
			sql += " and orderResultStatus in (:orderResultStatusList)";
			map.put("orderResultStatusList", orderResultStatusList);
		}
		sql += " and receiveTime>=:startTime and receiveTime<=:endTime ";
		if(lotteryType!=null&&lotteryType.size()>0){
			sql += " and lotteryType in :lotteryType";
			map.put("lotteryType", lotteryType);
		}
		String orderByString = "order by receiveTime desc";
		Date startDate=CoreDateUtils.parseDate(startTime, CoreDateUtils.DATE);
		Date endDate=CoreDateUtils.parseDate(endTime, CoreDateUtils.DATE);
		if(endDate.getTime()-(new Date().getTime())>0){
			endDate=new Date();
		}
		map.put("userno", userno);
		map.put("startTime", startDate);
		map.put("endTime", endDate);
		
		return findPageByCondition(sql,map,page,orderByString);
	}

	@Override
	public int updatePayStatus(int payStatus, String id) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("payStatus", payStatus);
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("id", id);
		return update(map, whereMap);
	}

	

	@Override
	public List<LotteryOrder> getByOrderStatusAndDealLine(int lotteryType,List<Integer> statusList,Date dealLine,PageBean<LotteryOrder> pageBean){
		String queryString = "orderStatus in(:orderStatus) and deadline<=:deadline and lotteryType=:lotteryType";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderStatus", statusList);
		map.put("deadline", dealLine);
		map.put("lotteryType", lotteryType);
		return findPageByCondition(queryString, map, pageBean, null);
		
	}
	
	@Override
	public List<LotteryOrder> getByLotteryTypeAndPhase(Integer lotteryType, String phase, PageBean<LotteryOrder> page) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("lotteryType", lotteryType);
		conditionMap.put("phase", phase);
		return findPageByCondition(conditionMap, page, null);
	}

	@Override
	public int updateOrderStatus(int status, String id) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderStatus", status);
		map.put("processTime", new Date());
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("id", id);
		return update(map, whereMap);
	}

	@Override
	public int updateOrderRewardStatus(int rewarStatus,String orderid) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("isExchanged",rewarStatus);
		map.put("rewardTime", new Date());
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("id", orderid);
		return update(map, whereMap);
	}





	@Override
	public int updateOrderStatusPrintTime(String orderId, int orderStatus,Date date) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderStatus",orderStatus);
		map.put("printTime", date);
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("id", orderId);
		return update(map, whereMap);
		
	}

    @Override
    public int updateOrderStatusAndTicketCount(String orderId,Integer orderStatus, Integer ticketCount) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("orderStatus",orderStatus);
        map.put("ticketCount", ticketCount);
        Map<String,Object> whereMap=new HashMap<String,Object>();
        whereMap.put("id", orderId);
        return update(map, whereMap);
    }

    @Override
    public int updateOrderStatusAndFailCount(String orderId,Integer orderStatus, Integer failCount) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("orderStatus",orderStatus);
        map.put("failCount", failCount);
        Map<String,Object> whereMap=new HashMap<String,Object>();
        whereMap.put("id", orderId);
        return update(map, whereMap);
    }

	@Override
	public int updateOrderStatusAndPayStatus(String orderId, Integer orderStatus, Integer payStatus) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderStatus",orderStatus);
		map.put("payStatus", payStatus);
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("id", orderId);
		return update(map, whereMap);
	}

	@Override
	public List<LotteryOrder> getByOrderStatus(Integer lotteryType,int orderStatus,PageBean<LotteryOrder> pageBean) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderStatus",orderStatus);
		map.put("lotteryType",lotteryType);
		return findPageByCondition(map,pageBean);
	}

	@Override
	public void updateOrderPrize(LotteryOrder lotteryOrder) {
		Map<String,Object> contentMap = new HashMap<String,Object>();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("id", lotteryOrder.getId());
		contentMap.put("wincode", lotteryOrder.getWincode());
		contentMap.put("prizeDetail", lotteryOrder.getPrizeDetail());
		contentMap.put("smallPosttaxPrize", lotteryOrder.getSmallPosttaxPrize());
		contentMap.put("pretaxPrize", lotteryOrder.getPretaxPrize());
		contentMap.put("totalPrize", lotteryOrder.getTotalPrize());
		contentMap.put("bigPosttaxPrize", lotteryOrder.getBigPosttaxPrize());
		contentMap.put("orderResultStatus", lotteryOrder.getOrderResultStatus());
		update(contentMap, whereMap);


	}

	@Override
	public List<LotteryOrder> getAllPrize(String userno, Date startDate, Date endDate, PageBean<LotteryOrder> pageBean) {
		String whereSql="userno=:userno and orderStatus=:orderStatus and orderResultStatus in(:orderResultStatus) and receiveTime>=:startDate and receiveTime<=:endDate ";
		String orderby="order by receiveTime desc";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userno",userno);
		map.put("orderStatus", OrderStatus.PRINTED.value);
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		List<Integer> orderResultStatus=new ArrayList<Integer>();
		orderResultStatus.add(OrderResultStatus.win_already.value);
		orderResultStatus.add(OrderResultStatus.win_big.value);
		map.put("orderResultStatus",orderResultStatus);
		return findPageByCondition(whereSql,map,pageBean,orderby);
	}

	@Override
	public void updateOrderResultStatus(String orderId, int orderResultstatus,String wincode) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderResultStatus", orderResultstatus);
		if(StringUtils.isNotBlank(wincode)&&!wincode.equals("null")){
			map.put("wincode", wincode);
		}
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("id", orderId);
		 update(map, whereMap);
	}

	@Override
	public List<LotteryOrder> getByLotteryPhaseAndStatus(Integer lotteryType, String phase, List<Integer> orderStatusList, List<Integer> orderResultStatusList, int max) {
		String sql="lotteryType=:lotteryType and phase=:phase and orderStatus in(:orderStatus) and orderResultStatus in(:orderResultStatus)";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderResultStatus", orderResultStatusList);
		map.put("orderStatus", orderStatusList);
		map.put("lotteryType",lotteryType);
		map.put("phase",phase);
		return findByCondition(max,sql,map);
	}

	@Override
	public List<LotteryOrder> getByLotteryStatusMatchNumAndDate(int lotteryType, String phase, List<Integer> orderStatusList, List<Integer> orderResultStatusList, Date beginTime, Date endTime, String matchNum, PageBean<LotteryOrder> page) {
		StringBuffer sb=new StringBuffer();
		sb.append("orderStatus in(:orderStatus) and orderResultStatus in(:orderResultStatus) and lotteryType=:lotteryType");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(phase)){
			sb.append(" and phase=:phase");
			map.put("phase", phase);
		}
		if(StringUtils.isNotBlank(matchNum)){
			sb.append(" and matchNums like :matchNums");
			map.put("matchNums", "%" + matchNum + "%");
		}

		sb.append(" and receiveTime>=:beginTime and receiveTime<:endTime");

		map.put("orderStatus", orderStatusList);
		map.put("orderResultStatus", orderResultStatusList);
		map.put("endTime", endTime);
		map.put("beginTime", beginTime);
		map.put("lotteryType", lotteryType);

		return findPageByCondition(sb.toString(), map, page);
	}

	@Override
	public List<LotteryOrder> getByUsernoAndStatus(String userno, int orderStatus) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userno", userno);
		map.put("orderStatus", orderStatus);
		return findByCondition(map);
	}

	@Override
	public Long countNotPrize(int lotteryType, String phase) {
		String sql="select count(o.id) from LotteryOrder o where o.lotteryType=:lotteryType and o.phase=:phase and o.orderStatus in (:orderStatus) and o.orderResultStatus in(:orderResultStatus)";
		TypedQuery<Long> total=getEntityManager().createQuery(sql,Long.class);
		total.setParameter("lotteryType", lotteryType);
		total.setParameter("phase", phase);
		List<Integer> statusList=new  ArrayList<Integer>();
		statusList.add(OrderStatus.PRINTED.value);
		statusList.add(OrderStatus.HALF_PRINTED.value);
		total.setParameter("orderStatus", statusList);
		List<Integer> resultStatusList=new  ArrayList<Integer>();
		resultStatusList.add(OrderResultStatus.not_open.value);
		resultStatusList.add(OrderResultStatus.prizing.value);
		total.setParameter("orderResultStatus", resultStatusList);
		return total.getSingleResult();
	}

	@Override
	public Long countNotEncash(int lotteryType, String phase) {
		String sql="select count(o.id) from LotteryOrder o where o.lotteryType=:lotteryType and o.phase=:phase and o.orderStatus in (:orderStatus) and o.orderResultStatus in(:orderResultStatus) and o.isExchanged=:isExchanged";
		TypedQuery<Long> total=getEntityManager().createQuery(sql,Long.class);
		total.setParameter("lotteryType", lotteryType);
		total.setParameter("phase", phase);
		List<Integer> statusList=new  ArrayList<Integer>();
		statusList.add(OrderStatus.PRINTED.value);
		statusList.add(OrderStatus.HALF_PRINTED.value);
		total.setParameter("orderStatus", statusList);
		List<Integer> resultStatusList=new  ArrayList<Integer>();
		resultStatusList.add(OrderResultStatus.win_already.value);
		resultStatusList.add(OrderResultStatus.win_big.value);
		total.setParameter("orderResultStatus", resultStatusList);
		total.setParameter("isExchanged", YesNoStatus.no.value);
		return total.getSingleResult();
	}


	public List<String> getIdByStatusAndDate(int lotteryType, String phase, List<Integer> orderStatusList, List<Integer> orderResultStatusList, Date beginTime, Date endTime, Long lastMatchNum, PageBean<LotteryOrder> page) {
		StringBuffer sb=new StringBuffer();
		sb.append("select o.id  from LotteryOrder o where o.orderStatus in(:orderStatus) and o.orderResultStatus in(:orderResultStatus) and o.lotteryType=:lotteryType");

		if(lastMatchNum!=null){
			sb.append(" and o.lastMatchNum<=:lastMatchNum");

		}
		if (StringUtils.isNotBlank(phase)){
			sb.append(" and o.phase=:phase");
		}

		sb.append(" and receiveTime>=:beginTime and receiveTime<:endTime");

		TypedQuery<String> idQuery=getEntityManager().createQuery(sb.toString(),String.class);
		idQuery.setParameter("orderStatus",orderStatusList);

		idQuery.setParameter("orderResultStatus", orderResultStatusList);
		idQuery.setParameter("endTime", endTime);
		idQuery.setParameter("beginTime", beginTime);
		idQuery.setParameter("lotteryType", lotteryType);
		if (lastMatchNum!=null&&lastMatchNum!=0){
			idQuery.setParameter("lastMatchNum", lastMatchNum);
		}
		if (StringUtils.isNotBlank(phase)){
			idQuery.setParameter("phase", phase);
		}

		//idQuery.setFirstResult((page.getPageIndex() - 1) * page.getMaxResult()).setMaxResults(page.getMaxResult());
		idQuery.setMaxResults(page.getMaxResult());
		return idQuery.getResultList();
	}

	@Override
	public List<LotteryOrder> getByStatus(int orderStatus, int orderResultStatus,int max) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("orderStatus", orderStatus);
		whereMap.put("orderResultStatus", orderResultStatus);
		return findByCondition(max,whereMap);
	}

	@Override
	public void updateWincode(String orderId, String wincode) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("wincode", wincode);
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("id", orderId);
		update(map, whereMap);
	}

	@Override
	public List<LotteryOrder> getByTypePhaseAndStatus(int lotteryType, String phase, int status, int max) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("orderStatus", status);
		whereMap.put("lotteryType",lotteryType);
		whereMap.put("phase",phase);
		return findByCondition(max,whereMap);
	}

}
