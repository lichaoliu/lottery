package com.lottery.core.domain.coupon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "coupon_type")
public class CouponType implements Serializable{
	private static final long serialVersionUID = -7907429331361433207L;
	
	@Id
	@Column(name = "id")
	private Long id; //优惠券类型id
	
	@Column(name = "lottery_types")
	private String lotteryTypes;  //彩种类型，多个彩种用,分割  不限制0
	
	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate; // 开始日期
	
	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
	private Date endDate; //结束日期
	
	@Column(name = "order_amount")
	private BigDecimal orderAmount; //订单条件金额
	
	@Column(name = "discount_amount")
	private BigDecimal discountAmount;  //优惠金额
	
	@Column(name = "title", length=100)
	private String title; //标题
	
	@Column(name = "description", length=300)
	private String description; //描述
	
	@Column(name = "status")
	private Integer status; //1开启，2关闭 
	
	@Column(name = "create_time")
	private Date createTime; // 创建时间
	
	@Column(name = "update_time")
	private Date updateTime; // 更新时间
	
	@Column(name = "content", length=300)
	private String content;//优惠券图片地址 或缓存编号

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getLotteryTypes() {
		return lotteryTypes;
	}

	public void setLotteryTypes(String lotteryTypes) {
		this.lotteryTypes = lotteryTypes;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
