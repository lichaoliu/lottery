package com.lottery.core.domain.account;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name="user_rebate")
@Entity
public class UserRebate implements Serializable{
    @Id
    @Column(name="id")
	private String id;
    @Column(name="userno")
	private String userno;
    @Column(name="user_name")
    private String userName;
    @Column(name="lottery_type")
	private Integer lotteryType;
	@Column(name="rebate_type")
	private Integer rebateType;//RebateType 返点类型
	@Column(name="bet_amount")
	private Integer betAmount;//投注金额
	@Column(name="point_location")
	private Double pointLocation;//点位
	@Column(name="is_agent")
	private Integer  isAgent;//是否代理商(YesnoStatus)
	@Column(name="agency_no")
	private String agencyno;//代理渠道
    @Column(name="create_time")
	private Date createTime;
    @Column(name="is_paused")
	private Integer isPaused;// 是否暂停
	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Integer getRebateType() {
		return rebateType;
	}

	public void setRebateType(Integer rebateType) {
		this.rebateType = rebateType;
	}

	public Integer getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Integer betAmount) {
		this.betAmount = betAmount;
	}



	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getPointLocation() {
		return pointLocation;
	}

	public void setPointLocation(Double pointLocation) {
		this.pointLocation = pointLocation;
	}

	public Integer getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(Integer isAgent) {
		this.isAgent = isAgent;
	}

	public String getAgencyno() {
		return agencyno;
	}

	public void setAgencyno(String agencyno) {
		this.agencyno = agencyno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsPaused() {
		return isPaused;
	}

	public void setIsPaused(Integer isPaused) {
		this.isPaused = isPaused;
	}
	
	
	
	
}
