package com.lottery.core.service.coupon;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.coupon.CouponTypeStatus;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.coupon.CouponTypeDao;
import com.lottery.core.domain.coupon.CouponType;

@Service
public class CouponTypeService {
	
	@Autowired
	private CouponTypeDao couponTypeDao;
	
	@Autowired
	private IdGeneratorDao idGeneratorDao;
	
	public void list(Integer status, PageBean<CouponType> page){
		Map<String, Object> map = new HashMap<String, Object>();
		if(status != null && status != 0){
			map.put("status", status);
		}
		couponTypeDao.findPageByCondition(map, page, " order by endDate asc");
	}
	
	@Transactional
	public void addCouponType(BigDecimal orderAmount,BigDecimal discountAmount,
			Date startDate, Date endDate, Integer status, String title, String description, String content, String lotteryTypes){
		CouponType ct = new CouponType();
		ct.setId(idGeneratorDao.getCouponTypeId());
		ct.setOrderAmount(orderAmount);
		ct.setDiscountAmount(discountAmount);
		ct.setCreateTime(new Date());
		ct.setStartDate(startDate);
		ct.setEndDate(endDate);
		ct.setStatus(status);
		ct.setTitle(title);
		ct.setDescription(description);
		ct.setContent(content);
		ct.setLotteryTypes(lotteryTypes);
		couponTypeDao.insert(ct);
	}
	
	@Transactional
	public void deleteCouponType(Long couponTypeId){
		CouponType couponType = couponTypeDao.find(couponTypeId);
		couponTypeDao.remove(couponType);
	}
	
	@Transactional
	public int updateCouponTypeClose(Date date){
		List<CouponType> cts = couponTypeDao.findByStatusAndEndtime(CouponTypeStatus.normal.getValue(), date);
		for (CouponType couponType : cts) {
			couponType.setStatus(CouponTypeStatus.close.getValue());
			couponType.setUpdateTime(new Date());
		}
		return cts.size();
	}

	@Transactional
	public void updateCoupontype(Long id, BigDecimal orderAmount, BigDecimal discountAmount, Date startDate,
			Date endDate, Integer status, String title, String description, String content, String lotteryTypes) {
		CouponType ct = couponTypeDao.find(id);
		ct.setOrderAmount(orderAmount);
		ct.setDiscountAmount(discountAmount);
		ct.setUpdateTime(new Date());
		ct.setStartDate(startDate);
		ct.setEndDate(endDate);
		ct.setStatus(status);
		ct.setTitle(title);
		ct.setDescription(description);
		ct.setContent(content);
		ct.setLotteryTypes(lotteryTypes);
		couponTypeDao.update(ct);
	}
}

