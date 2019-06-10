package com.lottery.scheduler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.schedule.AbstractSingletonScheduler;
import com.lottery.common.util.DateUtil;
import com.lottery.core.service.coupon.CouponTypeService;
import com.lottery.core.service.coupon.UserCouponService;


public class RecoverCouponJob extends AbstractSingletonScheduler {
	@Autowired
	private CouponTypeService couponTypeService;
	@Autowired
	private UserCouponService userCouponService;
	
	@Override
	protected boolean executeRun() {
		logger.error("执行回收优惠券定时任务");
		boolean flag = true;
		Date date = DateUtil.getCurrentDay();
		try {
			int i = couponTypeService.updateCouponTypeClose(date);
			logger.error("共回收了:{} 条优惠券记录", i);
		} catch (Exception e) {
			logger.error("",e);
			flag = false;
		}
		try {
			int i = userCouponService.updateUserCouponInvalid(date);
			logger.error("共回收了:{} 条 用户优惠券记录", i);
		} catch (Exception e) {
			logger.error("",e);
			flag = false;
		}
		return flag;
	}
}
