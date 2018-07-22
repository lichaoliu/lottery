package com.lottery.core.dao.impl.coupon;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.coupon.CouponTypeDao;
import com.lottery.core.domain.coupon.CouponType;

@Repository
public class CouponTypeDaoImpl extends AbstractGenericDAO<CouponType, Long> implements CouponTypeDao{

	@Override
	public List<CouponType> findByStatusAndEndtime(int status, Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("endDate", endDate);
		return findByCondition("status=:status and endDate<:endDate", map);
	}
}
