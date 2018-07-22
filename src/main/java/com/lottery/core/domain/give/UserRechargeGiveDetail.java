package com.lottery.core.domain.give;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="user_recharge_give_detail")
public class UserRechargeGiveDetail implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 9212284957643348344L;
/**
 * 充值赠送详情
 * */
	@EmbeddedId
	private UserRechargeGiveDetailPK pk;//联合主键
	@Column(name="recharge_amount")
	private BigDecimal rechargeAmount;//充值金额
	@Column(name="give_amount")
	private BigDecimal giveAmount;//赠送金额
	@Column(name="create_time")
	private Date createTime;//创建时间
	@Column(name="status")
	private Integer commonStatus;//充值状态
    @Column(name="update_time")
	private Date updateTime;//修改时间
    @Column(name="finish_time")
    private Date finishTime;//完成时间
    @Column(name="transation_id")
    private String transationId;//充值记录id
    @Column(name="memo")
    private String memo;//充值描述
	public UserRechargeGiveDetail(){}
	public UserRechargeGiveDetail(UserRechargeGiveDetailPK pk){
		this.pk=pk;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public UserRechargeGiveDetailPK getPk() {
		return pk;
	}
	public void setPk(UserRechargeGiveDetailPK pk) {
		this.pk = pk;
	}
	public Integer getCommonStatus() {
		return commonStatus;
	}
	public void setCommonStatus(Integer commonStatus) {
		this.commonStatus = commonStatus;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getTransationId() {
		return transationId;
	}
	public void setTransationId(String transationId) {
		this.transationId = transationId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
