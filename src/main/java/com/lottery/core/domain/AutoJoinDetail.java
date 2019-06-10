package com.lottery.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 定制跟单日志
 */
@Entity
@Table(name = "auto_join_detail")
public class AutoJoinDetail implements Serializable{

	/** ID */
	@Id
	@Column(name = "id")
	private Long id;

	/** 自动跟单ID */
	@Column(name = "auto_join_id")
	private Long autoJoinId;

	@Column(name = "caselot_buy_id")
	private String caseLotBuyId;

	/** 用户编号 */
	@Column(name = "userno")
	private String userno;

	/** 彩种 */
	@Column(name = "lottery_type")
	private Integer lotteryType;
	
	/** 合买方案ID */
	@Column(name = "caselot_id")
	private String caselotId;

	/** 参与金额 */
	@Column(name = "join_amt")
	private BigDecimal joinAmt;

	/** 跟单状态 */
	@Column(name = "status")
	private Integer status;

	/** 跟单状态 */
	@Column(name = "memo")
	private String memo;

	/** 创建时间 */
	@Column(name = "create_time")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAutoJoinId() {
		return autoJoinId;
	}

	public void setAutoJoinId(Long autoJoinId) {
		this.autoJoinId = autoJoinId;
	}

	public String getCaseLotBuyId() {
		return caseLotBuyId;
	}

	public void setCaseLotBuyId(String caseLotBuyId) {
		this.caseLotBuyId = caseLotBuyId;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getCaselotId() {
		return caselotId;
	}

	public void setCaselotId(String caselotId) {
		this.caselotId = caselotId;
	}

	public BigDecimal getJoinAmt() {
		return joinAmt;
	}

	public void setJoinAmt(BigDecimal joinAmt) {
		this.joinAmt = joinAmt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

}
