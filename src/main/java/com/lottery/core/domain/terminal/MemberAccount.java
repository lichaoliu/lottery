package com.lottery.core.domain.terminal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="t_member_account")
public class MemberAccount implements Serializable{
    /**
	 *出票商余额统计
	 */
	private static final long serialVersionUID = 882918611606793280L;
	@Id
	@Column(name="id")
	Long id;
	@Column(name="agent_code")
	private String agentCode;
    @Column(name="terminal_name")
	private String terminalName;//终端名字
    @Column(name="balance")
    private BigDecimal balance; //余额(金额:元)
    @Column(name="credit_balance")
    private BigDecimal creditBalance; //信用额度(金额:元)
    @Column(name="total_prize")
    private BigDecimal totalPrize;//中奖总金额(金额:元)
    @Column(name="small_prize")
    private BigDecimal smallPrize;//中小奖金额(金额:元)
    @Column(name="big_prize")
    private BigDecimal bigPrize;//中大奖金额(金额:元)
    @Column(name="warn_amount")
    private BigDecimal warnAmount;//报警金额(单位:元)
    @Column(name="update_time")
    private Date updateTime;
    
    @Column(name="sms_flag")
    private Integer smsFlag;//短信是否发送标示  0不发送   1 发送
    @Column(name = "is_sync")
    private Integer isSync;//是否余额同步(YesNoStatus)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTerminalName() {
		return terminalName;
	}
	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getCreditBalance() {
		return creditBalance;
	}
	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}
	public BigDecimal getTotalPrize() {
		return totalPrize;
	}
	public void setTotalPrize(BigDecimal totalPrize) {
		this.totalPrize = totalPrize;
	}
	public BigDecimal getSmallPrize() {
		return smallPrize;
	}
	public void setSmallPrize(BigDecimal smallPrize) {
		this.smallPrize = smallPrize;
	}
	public BigDecimal getBigPrize() {
		return bigPrize;
	}
	public void setBigPrize(BigDecimal bigPrize) {
		this.bigPrize = bigPrize;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public BigDecimal getWarnAmount() {
		return warnAmount;
	}
	public void setWarnAmount(BigDecimal warnAmount) {
		this.warnAmount = warnAmount;
	}
	public Integer getSmsFlag() {
		return smsFlag;
	}
	public void setSmsFlag(Integer smsFlag) {
		this.smsFlag = smsFlag;
	}

    public Integer getIsSync() {
        return isSync;
    }

    public void setIsSync(Integer isSync) {
        this.isSync = isSync;
    }

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
}
