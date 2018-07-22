package com.lottery.core.dao.impl.merchant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.merchant.MerchantOrderDAO;
import com.lottery.core.domain.merchant.MerchantOrder;
@Repository
public class MerchantOrderDAOImpl extends AbstractGenericDAO<MerchantOrder, String> implements MerchantOrderDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8169704601422533884L;

	@Override
	public MerchantOrder getByMerchantCodeAndMerchantNO(String merchantCode,
			String merchantNo) {
		Map<String,Object> map=new HashMap<String,Object>(); 
		map.put("merchantCode", merchantCode);
		map.put("merchantNo",merchantNo);
		return findByUnique(map);
	}

	@Override
	public int updateMerchantOrderStatus(String orderid, int orderStatus) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("orderStatus", orderStatus);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("orderid", orderid);
		return update(conditionMap, whereMap);
		
	}

	@Override
	public int updateMerchantOrderResultStatus(String orderid, int orderResultStatus, BigDecimal amount) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("orderResultStatus", orderResultStatus);
		conditionMap.put("totalPrize", amount);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("orderid", orderid);
		
		return update(conditionMap, whereMap);
		
	}

	@Override
	public int updateIsExchanged(String orderId, int status) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("isExchanged", status);
		
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("orderid", orderId);
		return update(conditionMap, whereMap);
		
	}

	@Override
	public int updateMerchantOrderStatusAndPrintTime(String orderid, int orderStatus, Date date) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("orderStatus", orderStatus);
		conditionMap.put("printTime", date);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("orderid", orderid);
		return update(conditionMap, whereMap);
	}
}
