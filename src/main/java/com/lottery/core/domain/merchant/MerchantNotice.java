package com.lottery.core.domain.merchant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fengqinyun on 14-7-10.
 * //出票商通知表
 */
@Table(name="merchant_notice")
@Entity
public class MerchantNotice implements Serializable {
    /**
	 * 
	 */

	@Column(name="orderid")
    @Id
    private String orderid;//平台订单号
    @Column(name="merchant_no")
    private String merchantNo;//出票商订单号
    @Column(name="merchant_code")
    private String merchantCode;//userno
    @Column(name="lottery_type",nullable=false)
    private Integer lotteryType;//彩种
    @Column(name="phase")
    private String phase;//期号
    @Column(name="order_status")
    private Integer orderStatus;//出票状态只有三种状态 (成功,失败,处理中)
    @Column(name="order_result_status")
    private Integer orderResultStatus;//中奖状态

    @Column(name="print_time")
    private Date printTime;//出票时间

    @Column(name="total_prize")
    private  BigDecimal totalPrize;//中奖总金额
	@Column(name="order_status_notice")
	private Integer orderStatusNotice;//票状态通知 NoticeStatus
	@Column(name="prize_status_notice")
	private Integer prizeStatusNotice;//中奖状态通知 PrizeStatus

	@Column(name="batch_id")
	private String batchId;//批次时间

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getOrderResultStatus() {
		return orderResultStatus;
	}

	public void setOrderResultStatus(Integer orderResultStatus) {
		this.orderResultStatus = orderResultStatus;
	}

	public Date getPrintTime() {
		return printTime;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	public BigDecimal getTotalPrize() {
		return totalPrize;
	}

	public void setTotalPrize(BigDecimal totalPrize) {
		this.totalPrize = totalPrize;
	}

	public Integer getOrderStatusNotice() {
		return orderStatusNotice;
	}

	public void setOrderStatusNotice(Integer orderStatusNotice) {
		this.orderStatusNotice = orderStatusNotice;
	}

	public Integer getPrizeStatusNotice() {
		return prizeStatusNotice;
	}

	public void setPrizeStatusNotice(Integer prizeStatusNotice) {
		this.prizeStatusNotice = prizeStatusNotice;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
}
