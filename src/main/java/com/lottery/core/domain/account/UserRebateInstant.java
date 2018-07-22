package com.lottery.core.domain.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="user_rebate_instant")
public class UserRebateInstant implements Serializable{
	
	
    /**
	 * 用户时时返点
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="orderid")
	@Id
	private String orderId;
	@Column(name="userno")
	private String userno;
	@Column(name="amount")
	private BigDecimal amount;//金额
	@Column(name="buy_amount")
	private BigDecimal buyAmount;//合买购买金额
	@Column(name="safe_amount")
	private BigDecimal safeAmount;//合买保底金额
	@Column(name="point_location")
	private Double pointLocation;//点位
	@Column(name="rebate_amount")
	private BigDecimal rebateAmount;//返点金额
	@Column(name="bet_type")
	private Integer betType;//投注类型
	@Column(name="create_time")
	private Date createTime;//创建时间
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Double getPointLocation() {
		return pointLocation;
	}
	public void setPointLocation(Double pointLocation) {
		this.pointLocation = pointLocation;
	}
	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}
	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getBetType() {
		return betType;
	}
	public void setBetType(Integer betType) {
		this.betType = betType;
	}
	public BigDecimal getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(BigDecimal buyAmount) {
		this.buyAmount = buyAmount;
	}
	public BigDecimal getSafeAmount() {
		return safeAmount;
	}
	public void setSafeAmount(BigDecimal safeAmount) {
		this.safeAmount = safeAmount;
	}
	
	
}
