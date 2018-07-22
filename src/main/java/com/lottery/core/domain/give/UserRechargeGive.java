package com.lottery.core.domain.give;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Table(name="user_recharge_give")
@Entity
public class UserRechargeGive implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
 * 充值赠送
 * */
	@Id
	@Column(name="id")
	private String id;
	@Column(name="status")
	private Integer status;//yesnostatus
	@Column(name="recharge_amount")
	private BigDecimal rechargeAmount;//充值金额
	@Column(name="give_amount")
	private BigDecimal giveAmount;//赠送金额,如果小于1是按百分比赠送
	@Column(name="recharge_give_type")
	private Integer rechargeGiveType;//赠送类型 RechargeGiveType
	@Column(name="start_time")
	private Date startTime;//开始时间
	@Column(name="finish_time")
	private Date finishTime;//结束时间
	@Column(name="for_scope")
	private Integer forScope;//是否有范围
	@Column(name="for_limit")
	private Integer forLimit;//是否限制次数(1次或多次)
	@Column(name="not_draw_perset")
	private BigDecimal notDrawPerset;//不能提现比例
    @Column(name="agencyno")
    private String agencyno="0";//渠道


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public BigDecimal getGiveAmount() {
		return giveAmount;
	}
	public void setGiveAmount(BigDecimal giveAmount) {
		this.giveAmount = giveAmount;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public Integer getRechargeGiveType() {
		return rechargeGiveType;
	}
	public void setRechargeGiveType(Integer rechargeGiveType) {
		this.rechargeGiveType = rechargeGiveType;
	}
	public Integer getForScope() {
		return forScope;
	}
	public void setForScope(Integer forScope) {
		this.forScope = forScope;
	}
	public Integer getForLimit() {
		return forLimit;
	}
	public void setForLimit(Integer forLimit) {
		this.forLimit = forLimit;
	}
	
	public BigDecimal getNotDrawPerset() {
		return notDrawPerset;
	}
	public void setNotDrawPerset(BigDecimal notDrawPerset) {
		this.notDrawPerset = notDrawPerset;
	}
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("[");
		sb.append("充值金额:").append(this.rechargeAmount);
		sb.append(",赠送金额:").append(this.giveAmount);
		sb.append("]");
		return sb.toString();
	}

    public String getAgencyno() {
        return agencyno;
    }

    public void setAgencyno(String agencyno) {
        this.agencyno = agencyno;
    }
}
