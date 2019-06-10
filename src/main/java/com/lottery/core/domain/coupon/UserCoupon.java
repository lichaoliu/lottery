package com.lottery.core.domain.coupon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_coupon")
public class UserCoupon implements Serializable {
	private static final long serialVersionUID = 1204949747780666622L;
	
	@Id
	@Column(name = "id", length=50)
	private String id;
	/**
	 * 用户编号
	 */
	@Column(name = "userno", length=50)
	private String userno;
	
	/**
	 * 优惠券类型id
	 */
	@Column(name = "coupon_type_id")
	private Long couponTypeId;
	/**
	 * 优惠券状态
	 * @see com.lottery.common.contains.lottery.coupon.UserCouponStatus
	 * */
	@Column(name = "status")
	private Integer status;// 1 未使用 2 已使用 3已过期
	
	@Column(name = "order_id", length=50)
	private String orderId; //优惠券订单id
	
	@Column(name = "order_time")
	private Date orderTime; //使用时间 
	
	@Column(name = "end_date")
	private Date endDate; //截止时间
	
	@Column(name = "create_time")
	private Date createTime; //创建时间
	
	@Column(name = "update_time")
	private Date updateTime; //更新时间

	@Column(name="preferential_amount")
	private BigDecimal preferentialAmount;//优惠金额
	/**
	 * 优惠券对象 json
	 */
	@Column(name = "coupon_type_desc", length=500)
	private String couponTypeDesc;
	
	@Column(name = "lottery_types")
	private String lotteryTypes;  //彩种类型，多个彩种用,分割  不限制0

    @Column(name = "share_order_id", length=50)
    private String shareOrderId; //优惠券分享订单id
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public Long getCouponTypeId() {
		return couponTypeId;
	}

	public void setCouponTypeId(Long couponTypeId) {
		this.couponTypeId = couponTypeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCouponTypeDesc() {
		return couponTypeDesc;
	}

	public void setCouponTypeDesc(String couponTypeDesc) {
		this.couponTypeDesc = couponTypeDesc;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getPreferentialAmount() {
		return preferentialAmount;
	}

	public void setPreferentialAmount(BigDecimal preferentialAmount) {
		this.preferentialAmount = preferentialAmount;
	}

	public String getLotteryTypes() {
		return lotteryTypes;
	}

	public void setLotteryTypes(String lotteryTypes) {
		this.lotteryTypes = lotteryTypes;
	}

    public String getShareOrderId() {
        return shareOrderId;
    }

    public void setShareOrderId(String shareOrderId) {
        this.shareOrderId = shareOrderId;
    }
}
