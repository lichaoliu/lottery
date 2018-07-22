package com.lottery.core.dao.coupon;

import java.util.Date;
import java.util.List;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.coupon.CouponType;

public interface CouponTypeDao extends IGenericDAO<CouponType, Long> {

	public List<CouponType> findByStatusAndEndtime(int value, Date date);

}
