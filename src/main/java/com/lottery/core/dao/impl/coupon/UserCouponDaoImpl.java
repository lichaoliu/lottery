package com.lottery.core.dao.impl.coupon;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.contains.lottery.coupon.UserCouponStatus;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.coupon.UserCouponDao;
import com.lottery.core.domain.coupon.UserCoupon;

@Repository
public class UserCouponDaoImpl extends AbstractGenericDAO<UserCoupon, String> implements UserCouponDao{

	@Override
	public int updateUserCouponInvalid(Date date) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("status", UserCouponStatus.invalid.getValue());
		conditionMap.put("updateTime", new Date());
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("unuseStatus", UserCouponStatus.unuse.getValue());
		whereMap.put("endDate", date);
		return update(conditionMap, " status=:unuseStatus and endDate<:endDate ", whereMap);
	}


}
