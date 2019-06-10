package com.lottery.controller.admin;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.AdminPage;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.dao.coupon.CouponTypeDao;
import com.lottery.core.dao.coupon.UserCouponDao;
import com.lottery.core.domain.coupon.CouponType;
import com.lottery.core.domain.coupon.UserCoupon;
import com.lottery.core.service.coupon.CouponTypeService;
import com.lottery.core.service.coupon.UserCouponService;

@Controller
@RequestMapping("adminCoupon")
public class AdminCouponController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CouponTypeDao couponTypeDao;
	@RequestMapping("couponTypes")
	public @ResponseBody AdminPage<CouponType> couponTypes(@RequestParam String condition, int start, int limit){
		AdminPage<CouponType> page = new AdminPage<CouponType>(start, limit, " order by createTime desc");
		couponTypeDao.findPageByCondition(JsonUtil.transferJson2Map(condition), page);
		return page;
	}
	
	@Autowired
	private CouponTypeService couponTypeService;
	@RequestMapping("addCoupontype")
	public @ResponseBody ResponseData add(BigDecimal orderAmount, BigDecimal discountAmount,
			 Integer status, String title, String description, String content,String lotteryTypes,
			@RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
		ResponseData rd = new ResponseData();
		try {
			couponTypeService.addCouponType(orderAmount, discountAmount, startDate, endDate, status, title, description, content, lotteryTypes);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			logger.error("", e);
			rd.setErrorCode(ErrorCode.Faile.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	@RequestMapping("updateCoupontype")
	public @ResponseBody ResponseData updateCoupontype(Long id, BigDecimal orderAmount, BigDecimal discountAmount,
			Integer status, String title, String description, String content,String lotteryTypes,
			@RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
		ResponseData rd = new ResponseData();
		try {
			couponTypeService.updateCoupontype(id, orderAmount, discountAmount, startDate, endDate, status, title, description, content, lotteryTypes);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			logger.error("", e);
			rd.setErrorCode(ErrorCode.Faile.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	@RequestMapping("deleteCouponType")
	public @ResponseBody ResponseData deleteCouponType(Long id){
		ResponseData rd = new ResponseData();
		try {
			couponTypeService.deleteCouponType(id);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			logger.error("", e);
			rd.setErrorCode(ErrorCode.Faile.getValue());
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	
	
	@Autowired
	private UserCouponDao userCouponDao;
	@RequestMapping("userCoupons")
	public @ResponseBody AdminPage<UserCoupon> userCoupons(@RequestParam String condition, int start, int limit){
		AdminPage<UserCoupon> page = new AdminPage<UserCoupon>(start, limit, " order by createTime desc");
		userCouponDao.findPageByCondition(JsonUtil.transferJson2Map(condition), page);
		return page;
	}
	
	@Autowired
	private UserCouponService userCouponService;
	@RequestMapping("addUsercoupon")
	public @ResponseBody ResponseData addUsercoupon(@RequestParam String usernos, @RequestParam Long couponTypeId){
		ResponseData rd = new ResponseData();
		try {
			userCouponService.add(couponTypeId, usernos.split(","));
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (Exception e) {
			logger.error("", e);
			rd.setErrorCode(ErrorCode.Faile.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	@RequestMapping("deleteUsercoupon")
	public @ResponseBody ResponseData deleteUsercoupon(@RequestParam String userCouponIds){
		ResponseData rd = new ResponseData();
		try {
			userCouponService.delete(userCouponIds.split(","));
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (Exception e) {
			logger.error("", e);
			rd.setErrorCode(ErrorCode.Faile.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
}
