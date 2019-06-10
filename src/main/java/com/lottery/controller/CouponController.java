package com.lottery.controller;

import com.lottery.common.PageBean;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.pay.PayStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.coupon.CouponType;
import com.lottery.core.domain.coupon.UserCoupon;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.UserInfoService;
import com.lottery.core.service.coupon.CouponTypeService;
import com.lottery.core.service.coupon.UserCouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("coupon")
public class CouponController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CouponTypeService couponTypeService;
	@Autowired
	private UserCouponService userCouponService;
    @Autowired
    private LotteryOrderService lotteryOrderService;

    @Autowired
    private UserInfoService userService;
	
	/**
	 * 查询优惠券
	 * @param pageIndex 从1开始
	 * @param maxResult
	 * @param status 1开启 2:关闭，0全部  默认是1
	 * @return
	 */
	@RequestMapping("couponTypes")
	public @ResponseBody ResponseData couponTypes(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "30") Integer maxResult,
			@RequestParam(required = false, defaultValue = "1") Integer status){
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.getValue());
		try {
			PageBean<CouponType> page = new PageBean<CouponType>(pageIndex, maxResult);
			couponTypeService.list(status, page);
			rd.setValue(page);
		} catch (Exception e) {
			logger.error("", e);
			rd.setErrorCode(ErrorCode.Faile.getValue());
		}
		return rd;
	}
	
	/**
	 * 查询用户优惠券
	 * @param pageIndex 从1开始
	 * @param maxResult
	 * @param userno 用户编号
	 * @param lotteryType 彩种 为空就查全部
	 * @param status 1:未使用 2：已使用 3：已过期
	 * @return
	 */
	@RequestMapping("userCoupons")
	public @ResponseBody ResponseData userCoupons(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "30") Integer maxResult, 
			@RequestParam String userno,  Integer status, @RequestParam(required = false, defaultValue = "") String lotteryType){
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.getValue());
		try {
			PageBean<UserCoupon> page = new PageBean<UserCoupon>(pageIndex, maxResult);
			userCouponService.list(userno, status, lotteryType, page);
			rd.setValue(page);
		} catch (Exception e) {
			logger.error("", e);
			rd.setErrorCode(ErrorCode.Faile.getValue());
		}
		return rd;
	}
	
	
	
	
	@RequestMapping("countEnable")
	public @ResponseBody ResponseData userCoupons(
			@RequestParam String userno,  Integer status){
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.getValue());
		try {
			rd.setValue(userCouponService.countEnable(userno, status));
		} catch (Exception e) {
			logger.error("", e);
			rd.setErrorCode(ErrorCode.Faile.getValue());
		}
		return rd;
	}
	
	
	
	/**
	 * 查询用户优惠券
	 * @param userno 用户编号
	 * @return lotteryType 查彩种
	 */
	@RequestMapping("userEnableCoupons")
	public @ResponseBody ResponseData userEnableCoupons(@RequestParam String userno, String[] lotteryType){
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.getValue());
		try {
			List<UserCoupon> list = userCouponService.findAllEnableCoupon(userno, lotteryType);
			if(list==null||list.size()<=0) {
				rd.setErrorCode(ErrorCode.no_exits.value);
			}
			rd.setValue(list);
		} catch (Exception e) {
			logger.error("", e);
			rd.setErrorCode(ErrorCode.Faile.getValue());
		}
		return rd;
	}
	
	/**
	 * 发优惠券
	 * @param userno 用户编号
	 * @param couponTypeId 优惠券编号
	 * @return
	 */
	@RequestMapping("addUsercoupon")
	public @ResponseBody ResponseData addUsercoupon(@RequestParam String userno, @RequestParam Long couponTypeId){
		ResponseData rd = new ResponseData();
		try {
			rd.setErrorCode(ErrorCode.Success.getValue());
			userCouponService.add(couponTypeId, new String[]{userno});
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
			rd.setValue(e.getErrorCode().getMemo());
		} catch (Exception e) {
			logger.error("", e);
			rd.setErrorCode(ErrorCode.Faile.getValue());
		}
		return rd;
	}

	/**
	 * 发优惠券
	 * @param mobilePhone 用户编号
	 * @param couponTypeIds 优惠券编号
	 * @return
	 */
	@RequestMapping("shareUsercoupon")
	public @ResponseBody ResponseData shareUsercoupon(@RequestParam("mobilePhone") String mobilePhone, @RequestParam("couponTypeIds") String couponTypeIds, @RequestParam("shareOrderId") String shareOrderId){
		ResponseData rd = new ResponseData();
		try {
            UserInfo userInfo = userService.getByPhoneNo(mobilePhone);
            if (userInfo == null) {
                rd.setErrorCode(ErrorCode.no_account.getValue());
                rd.setValue(ErrorCode.no_account.memo);
                return rd;
            }

            LotteryOrder lotteryOrder = lotteryOrderService.get(shareOrderId);
            if (lotteryOrder == null  || !(lotteryOrder.getPayStatus() == PayStatus.PAY_SUCCESS.getValue() || lotteryOrder.getPayStatus() == PayStatus.ALREADY_PAY.getValue())) {
                //订单不存在或者未支付
                rd.setErrorCode(ErrorCode.coupon_share_order_not_support.getValue());
                rd.setValue(ErrorCode.coupon_share_order_not_support.memo);
                return rd;
            }

            if ((new Date().getTime() -  lotteryOrder.getReceiveTime().getTime()) >= 24*60*60*1000 ) {
                //超过订单日期一天后不能再分享该订单
                rd.setErrorCode(ErrorCode.coupon_share_order_not_support.getValue());
                rd.setValue(ErrorCode.coupon_share_order_not_support.memo);
                return rd;
            }

            long count = userCouponService.findByusernoAndShareOrderId(userInfo.getUserno(), shareOrderId);
            if (count > 0) {
                rd.setErrorCode(ErrorCode.coupon_share_duplication.getValue());
                rd.setValue(ErrorCode.coupon_share_duplication.memo);
                return rd;
            }

            List couponList = new ArrayList();
            for(String id: couponTypeIds.split(",")) {
                UserCoupon coupon = userCouponService.share(Long.parseLong(id), userInfo.getUserno(), shareOrderId);
                if (coupon != null) {
                    couponList.add(coupon);
                }
            }
            if (couponList.size() == 0) {
                rd.setErrorCode(ErrorCode.coupon_share_not_open.getValue());
                rd.setValue(ErrorCode.coupon_share_not_open.memo);
                return rd;
            }

            PageBean<UserCoupon> page = new PageBean<UserCoupon>(1, 10);
            userCouponService.listByShareOrderId(userInfo.getUserno(), shareOrderId, page);
            rd.setValue(page);
            rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
			rd.setValue(e.getErrorCode().getMemo());
		} catch (Exception e) {
			logger.error("", e);
			rd.setErrorCode(ErrorCode.Faile.getValue());
		}
		return rd;
	}
}
