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
 * //出票商对应表
 */
@Table(name="merchant_order")
@Entity
public class MerchantOrder implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1425236152103106220L;
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
    @Column(name="multiple",nullable=false)
    private Integer multiple;
    @Column(name="amount")// 倍数
    private BigDecimal amount;//投注总金额
    @Column(name="play_type")
    private Integer playType;//玩法
    @Column(name="bet_code")
    private String betCode;//投注注码
    @Column(name="order_status")
    private Integer orderStatus;//出票状态只有三种状态 (成功,失败,处理中)
    @Column(name="order_result_status")
    private Integer orderResultStatus;//中奖状态
    @Column(name="is_exchanged")
    private Integer isExchanged;		//派奖状态  YesNoStatus
    @Column(name="wincode")
    private String wincode;//中奖号码
    @Column(name="create_time")
    private Date createTime;//创建时间
    @Column(name="print_time")
    private Date printTime;//出票时间
    @Column(name="addition")
    private Integer addition;//是否追加
    @Column(name="prize_detail")
    private String prizeDetail;                     //中奖详情
    @Column(name="total_prize")
    private  BigDecimal totalPrize;//中奖总金额
	@Column(name="order_status_notice")
	private Integer orderStatusNotice;//票状态通知 NoticeStatus
	@Column(name="prize_status_notice")
	private Integer prizeStatusNotice;//中奖状态通知 PrizeStatus
	@Column(name="end_time")
	private Date endTime;//结束时间
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
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getPlayType() {
		return playType;
	}
	public void setPlayType(Integer playType) {
		this.playType = playType;
	}
	public String getBetCode() {
		return betCode;
	}
	public void setBetCode(String betCode) {
		this.betCode = betCode;
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
	
	public Integer getIsExchanged() {
		return isExchanged;
	}
	public void setIsExchanged(Integer isExchanged) {
		this.isExchanged = isExchanged;
	}
	public String getWincode() {
		return wincode;
	}
	public void setWincode(String wincode) {
		this.wincode = wincode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getPrintTime() {
		return printTime;
	}
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}
	public Integer getAddition() {
		return addition;
	}
	public void setAddition(Integer addition) {
		this.addition = addition;
	}
	public String getPrizeDetail() {
		return prizeDetail;
	}
	public void setPrizeDetail(String prizeDetail) {
		this.prizeDetail = prizeDetail;
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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
}
