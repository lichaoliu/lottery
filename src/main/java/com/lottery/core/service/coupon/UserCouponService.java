package com.lottery.core.service.coupon;

import com.lottery.common.PageBean;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.coupon.CouponTypeStatus;
import com.lottery.common.contains.lottery.coupon.UserCouponLineContains;
import com.lottery.common.contains.lottery.coupon.UserCouponStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.StringUtil;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.coupon.CouponTypeDao;
import com.lottery.core.dao.coupon.UserCouponDao;
import com.lottery.core.domain.coupon.CouponType;
import com.lottery.core.domain.coupon.UserCoupon;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserCouponService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	
	@Autowired
	private UserCouponDao userCouponDao;
	@Autowired
	private CouponTypeDao couponTypeDao;
	@Autowired
	private IdGeneratorDao idGeneratorDao;
	

	
	/**
	 *订单失败优惠券 变成未使用
	 */
	@Transactional
	public void orderFailUpdateCoupon(String userCouponId){
		UserCoupon userCoupon = userCouponDao.find(userCouponId);
		if(userCoupon == null){
			logger.error("退款时用户优惠券id不存在:{}", userCouponId);
			return;
		}
		if(userCoupon.getStatus() != UserCouponStatus.used.getValue()){
			logger.error("退款时用户优惠券不是已使用", userCouponId);
			return;
		}
		if(userCoupon.getEndDate().getTime() + 24*60*60*1000 < System.currentTimeMillis()){
			int delayDay = 7;//结束时间 延长7天
			//当前时间已经过了截止时间
//			UserCoupon newuc = new UserCoupon();
//			newuc.setId(idGeneratorDao.getUserCouponId());
//			newuc.setCreateTime(new Date());
//			newuc.setCouponTypeId(userCoupon.getCouponTypeId());
//			newuc.setEndDate(DateUtil.addDay(userCoupon.getEndDate(), delayDay));
//			newuc.setStatus(UserCouponStatus.unuse.getValue());
//			newuc.setUserno(userCoupon.getUserno());
//			userCouponDao.insert(newuc);
			//不新送一张了 直接修改结束时间
			userCoupon.setEndDate(DateUtil.addDay(userCoupon.getEndDate(), delayDay));
		}
		userCoupon.setOrderId("");
		userCoupon.setOrderTime(null);
		userCoupon.setUpdateTime(new Date());
		userCoupon.setStatus(UserCouponStatus.unuse.getValue());
		userCouponDao.update(userCoupon);
	}
	
	/**
	 * 定时执行 检查用户优惠券超时
	 */
	@Transactional
	public int updateUserCouponInvalid(Date date){
		return userCouponDao.updateUserCouponInvalid(date);
	}

	@Transactional
	public void delete(String[] userCouponIds) {
		for (String userCouponId : userCouponIds) {
			userCouponDao.remove(userCouponDao.find(userCouponId));
		}
	}
	
	public void list(String userno, Integer status, String lotteryType, PageBean<UserCoupon> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer(" userno = :userno");
		map.put("userno", userno);
		if(status != null && status != 0){
			where.append(" and status = :status");
			map.put("status", status);
		}
		if(StringUtil.isNotEmpt(lotteryType)){
			where.append(" and lotteryTypes = 0 or lotteryTypes like :lotteryType");
			map.put("lotteryType", "%"+lotteryType+"%");
		}
		userCouponDao.findPageByCondition(where.toString(), map, page, " order by endDate desc");
	}

	public void listByShareOrderId(String userno, String shareOrderId, PageBean<UserCoupon> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer(" userno = :userno");
		map.put("userno", userno);
		if(!StringUtil.isEmpty(shareOrderId)){
			where.append(" and shareOrderId = :shareOrderId");
			map.put("shareOrderId", shareOrderId);
		}
		userCouponDao.findPageByCondition(where.toString(), map, page, " order by endDate desc");
	}

	
	public Long countEnable(String userno, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer(" userno = :userno");
		map.put("userno", userno);
		if(status != null && status != 0){
			where.append(" and status = :status");
			map.put("status", status);
		}
		
		return userCouponDao.getCountByCondition(map);
	}
	
	
	public List<UserCoupon> findAllEnableCoupon(String userno, String[] lotteryType) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer(" userno = :userno");
		map.put("userno", userno);
		where.append(" and status = :status");
		map.put("status", UserCouponStatus.unuse.getValue());
		where.append(" and (lotteryTypes = 0 ");
		
		for(int i=0;i<lotteryType.length;i++) {
			where.append(" or lotteryTypes like :lotteryType"+i );
			map.put("lotteryType"+i, "%"+lotteryType[i]+"%");
		}
		where.append(")");
		return userCouponDao.findByCondition(where.toString(), map, " order by endDate asc");
	}

    public Long findByusernoAndShareOrderId(String userno, String shareOrderId) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer where = new StringBuffer(" userno = :userno");
        map.put("userno", userno);
        where.append(" and shareOrderId = :shareOrderId");
        map.put("shareOrderId", shareOrderId);
        return userCouponDao.getCountByCondition(map);
    }
	
	/**
	 * 用户 发券
	 * @param couponTypeId
	 * @param usernos
	 */
	@Transactional
	public void add(Long couponTypeId, String[] usernos) {
		CouponType couponType = couponTypeDao.find(couponTypeId);
		if(couponType == null){
			throw new LotteryException(ErrorCode.coupon_notexists, "优惠券不存在");
		}
		if(couponType.getStatus() != CouponTypeStatus.normal.getValue()){
			throw new LotteryException(ErrorCode.coupon_not_open, "优惠券状态不是启用");
		}
		JSONObject descObj = new JSONObject();
		descObj.put(UserCouponLineContains.ORDERAMOUNT, couponType.getOrderAmount());
		descObj.put(UserCouponLineContains.DISCOUNTAMOUNT, couponType.getDiscountAmount());
		descObj.put(UserCouponLineContains.LOTTERYTYPES, couponType.getLotteryTypes());
		descObj.put(UserCouponLineContains.TITLE, couponType.getTitle());
		String couponTypeDesc = descObj.toString();
		
		Long id = idGeneratorDao.getUserCouponId(usernos.length);
		for (String userno : usernos) {
			UserCoupon uc = new UserCoupon();
			uc.setId(idGeneratorDao.getUserCouponId(id++));
			uc.setCreateTime(new Date());
			uc.setCouponTypeId(couponType.getId());
			uc.setEndDate(couponType.getEndDate());
			uc.setStatus(UserCouponStatus.unuse.getValue());
			uc.setUserno(userno);
			uc.setCouponTypeDesc(couponTypeDesc);
			uc.setPreferentialAmount(couponType.getDiscountAmount());
			uc.setLotteryTypes(couponType.getLotteryTypes());
			userCouponDao.insert(uc);
		}
	}

    /**
     * 分享获取优惠券
     * @param couponTypeId
     * @param userno
     */
    @Transactional
    public UserCoupon share(Long couponTypeId, String userno, String shareOrderId) {
        CouponType couponType = couponTypeDao.find(couponTypeId);
        if(couponType == null){
            return null;
        }
        if(couponType.getStatus() != CouponTypeStatus.normal.getValue()){
            return null;
        }
        JSONObject descObj = new JSONObject();
        descObj.put(UserCouponLineContains.ORDERAMOUNT, couponType.getOrderAmount());
        descObj.put(UserCouponLineContains.DISCOUNTAMOUNT, couponType.getDiscountAmount());
        descObj.put(UserCouponLineContains.LOTTERYTYPES, couponType.getLotteryTypes());
        descObj.put(UserCouponLineContains.TITLE, couponType.getTitle());
        String couponTypeDesc = descObj.toString();

        Long id = idGeneratorDao.getUserCouponId(1);
        UserCoupon uc = new UserCoupon();
        uc.setId(idGeneratorDao.getUserCouponId(id));
        uc.setCreateTime(new Date());
        uc.setCouponTypeId(couponType.getId());
        uc.setEndDate(couponType.getEndDate());
        uc.setStatus(UserCouponStatus.unuse.getValue());
        uc.setUserno(userno);
        uc.setCouponTypeDesc(couponTypeDesc);
        uc.setPreferentialAmount(couponType.getDiscountAmount());
        uc.setLotteryTypes(couponType.getLotteryTypes());
        uc.setShareOrderId(shareOrderId);
        userCouponDao.insert(uc);
        return uc;
    }

	@Transactional
	public  UserCoupon get(String id){
		return userCouponDao.find(id);
	}

	@Transactional
	public  UserCoupon getWithLock(String id){
		return userCouponDao.findWithLock(id,true);
	}
	@Transactional
	public  void update(UserCoupon userCoupon){
		 userCouponDao.update(userCoupon);
	}
}
