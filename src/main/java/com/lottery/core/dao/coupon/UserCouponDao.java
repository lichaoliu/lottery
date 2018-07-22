package com.lottery.core.dao.coupon;

import java.util.Date;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.coupon.UserCoupon;

public interface UserCouponDao extends IGenericDAO<UserCoupon, String>{

	public int updateUserCouponInvalid(Date date);

}
