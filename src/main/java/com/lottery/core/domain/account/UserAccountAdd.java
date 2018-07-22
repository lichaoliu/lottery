package com.lottery.core.domain.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 用户账户加款
 * */
@Entity
@Table(name="user_account_add")
public class UserAccountAdd implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4306832110186679978L;
	@Id
	@Column(name="id")
	private String id;//主键
	@Column(name="username")
	private String username;
	@Column(name="userno")
	private String userno;//赠送人userno
	@Column(name="amount")
	private BigDecimal amount;//增加金额
	@Column(name="for_draw")
	private Integer forDraw;//是否可提现，YesNoStatus;
	@Column(name="autdit_status")
	private Integer auditStatus;//审核状态
	@Column(name="create_time")
	private Date createTime;//创建时间
	@Column(name="audit_time")
	private Date  auditTime;//审核时间
	@Column(name="memo")
	private String memo;//描述
	@Column(name="creator")
	private String creator;//创建者
	@Column(name="aduiter")
	private String aduiter;//审核人
    @Column(name="error_message")
	private String errorMessage; //失败原因
    @Column(name="is_add")
    private Integer isAdd;//是否加款，YesNoStatus
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getForDraw() {
		return forDraw;
	}

	public void setForDraw(Integer forDraw) {
		this.forDraw = forDraw;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAduiter() {
		return aduiter;
	}

	public void setAduiter(String aduiter) {
		this.aduiter = aduiter;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(Integer isAdd) {
		this.isAdd = isAdd;
	}
	
} 
