package com.lottery.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 定制跟单
 */
@Entity
@Table(name = "auto_join")
public class AutoJoin implements Serializable{

	/** ID */
	@Id
	@Column(name = "id")
	private Long id;

	/** 用户编号 */
	@Column(name = "userno")
	private String userno;

	/** 彩种 */
	@Column(name = "lottery_type")
	private Integer lotteryType;

	/** 发起人 */
	@Column(name = "starter")
	private String starter;

	/** 最多跟单次数 */
	@Column(name = "times")
	private Integer times;
	
	/** 跟单金额 */
	@Column(name = "join_amt")
	private BigDecimal joinAmt;
	
	/** 实际跟单次数 */
	@Column(name = "join_times")
	private Integer joinTimes;

	/** 失败次数 */
	@Column(name = "fail_num")
	private Integer failNum;

	/** 创建时间 */
	@Column(name = "create_time")
	private Date createTime;

	/** 修改时间 */
	@Column(name = "update_time")
	private Date updateTime;

	/** 自动跟单状态  AutoJoinState */
	@Column(name = "status")
	private Integer status;

	/** 强制跟单 1:强制跟单 */
	@Column(name = "force_join")
	private Integer forceJoin;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getStarter() {
		return starter;
	}

	public void setStarter(String starter) {
		this.starter = starter;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getJoinTimes() {
		return joinTimes;
	}

	public void setJoinTimes(Integer joinTimes) {
		this.joinTimes = joinTimes;
	}

	public BigDecimal getJoinAmt() {
		return joinAmt;
	}

	public void setJoinAmt(BigDecimal joinAmt) {
		this.joinAmt = joinAmt;
	}

	public Integer getFailNum() {
		return failNum;
	}

	public void setFailNum(Integer failNum) {
		this.failNum = failNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getForceJoin() {
		return forceJoin;
	}
	public void setForceJoin(Integer forceJoin) {
		this.forceJoin = forceJoin;
	}
	
}
