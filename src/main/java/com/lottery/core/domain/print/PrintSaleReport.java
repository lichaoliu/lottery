package com.lottery.core.domain.print;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="print_sale_report")
public class PrintSaleReport implements Serializable{

	@EmbeddedId
	private PrintSaleReportPK id;
	@Column(name="report_amount2")
	private BigDecimal reportAmount2;
	@Column(name="report_count2")
	private Integer reportCount2;
	@Column(name = "report_amount1")
	private BigDecimal reportAmount1;
	@Column(name="report_count1")
	private Integer reportCount1;
	@Column(name = "jc_amount1")
	private BigDecimal jcamount1;
	@Column(name="jc_count1")
	private Integer jccount1;
	@Column(name="exchange_amount")
	private BigDecimal exchangeAmount;
	@Column(name="exchange_count")
	private Integer exchangeCount;
	//系统
	@Column(name = "sys_report_amount1")
	private BigDecimal sysReportAmount1;
	@Column(name="sys_report_count1")
	private Integer sysReportCount1;
	@Column(name="prize_amount")
	private BigDecimal prizeAmount;
	@Column(name="sys_exchange_amount")
	private BigDecimal sysExchangeAmount;
	@Column(name="sys_exchange_count")
	private Integer sysExchangeCount;
	@Column(name="create_time")
	private Date createTime;

	public PrintSaleReportPK getId() {
		return id;
	}

	public void setId(PrintSaleReportPK id) {
		this.id = id;
	}

	public BigDecimal getPrizeAmount() {
		return prizeAmount;
	}

	public void setPrizeAmount(BigDecimal prizeAmount) {
		this.prizeAmount = prizeAmount;
	}

	public BigDecimal getExchangeAmount() {
		return exchangeAmount;
	}

	public void setExchangeAmount(BigDecimal exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}

	public Integer getExchangeCount() {
		return exchangeCount;
	}

	public void setExchangeCount(Integer exchangeCount) {
		this.exchangeCount = exchangeCount;
	}

	public BigDecimal getSysExchangeAmount() {
		return sysExchangeAmount;
	}

	public void setSysExchangeAmount(BigDecimal sysExchangeAmount) {
		this.sysExchangeAmount = sysExchangeAmount;
	}

	public Integer getSysExchangeCount() {
		return sysExchangeCount;
	}

	public void setSysExchangeCount(Integer sysExchangeCount) {
		this.sysExchangeCount = sysExchangeCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getReportAmount2() {
		return reportAmount2;
	}

	public void setReportAmount2(BigDecimal reportAmount2) {
		this.reportAmount2 = reportAmount2;
	}

	public Integer getReportCount2() {
		return reportCount2;
	}

	public void setReportCount2(Integer reportCount2) {
		this.reportCount2 = reportCount2;
	}

	public BigDecimal getReportAmount1() {
		return reportAmount1;
	}

	public void setReportAmount1(BigDecimal reportAmount1) {
		this.reportAmount1 = reportAmount1;
	}

	public Integer getReportCount1() {
		return reportCount1;
	}

	public void setReportCount1(Integer reportCount1) {
		this.reportCount1 = reportCount1;
	}

	public BigDecimal getSysReportAmount1() {
		return sysReportAmount1;
	}

	public void setSysReportAmount1(BigDecimal sysReportAmount1) {
		this.sysReportAmount1 = sysReportAmount1;
	}

	public Integer getSysReportCount1() {
		return sysReportCount1;
	}

	public void setSysReportCount1(Integer sysReportCount1) {
		this.sysReportCount1 = sysReportCount1;
	}

	public BigDecimal getJcamount1() {
		return jcamount1;
	}

	public void setJcamount1(BigDecimal jcamount1) {
		this.jcamount1 = jcamount1;
	}

	public Integer getJccount1() {
		return jccount1;
	}

	public void setJccount1(Integer jccount1) {
		this.jccount1 = jccount1;
	}
}
