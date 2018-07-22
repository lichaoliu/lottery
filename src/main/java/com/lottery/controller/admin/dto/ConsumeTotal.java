package com.lottery.controller.admin.dto;

import java.math.BigDecimal;

public class ConsumeTotal {
	private String userno;
	private Integer lotteryType;//彩种
	private Long totalCount = 0L;//总数
	private BigDecimal totalbetAmount = BigDecimal.ZERO;//总金额
	private BigDecimal totalprizeAmount = BigDecimal.ZERO;
	
	private Long orderCount = 0L;
	private BigDecimal orderbetAmount = BigDecimal.ZERO;//订单投注
	private BigDecimal orderprizeAmount = BigDecimal.ZERO;//
	
	private Long caselotCount = 0L;
	private BigDecimal caselotBetAmount = BigDecimal.ZERO;//合买投注
	private BigDecimal caselotPrizeAmount = BigDecimal.ZERO;
	
	private Integer halfbetCount = 0;
	private BigDecimal halfbetAmount = BigDecimal.ZERO;//部分成功金额
	private BigDecimal halfprizeAmount = BigDecimal.ZERO;
	
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public int getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(int lotteryType) {
		this.lotteryType = lotteryType;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Long getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}
	public BigDecimal getTotalbetAmount() {
		return totalbetAmount;
	}
	public void setTotalbetAmount(BigDecimal totalbetAmount) {
		this.totalbetAmount = totalbetAmount;
	}
	public BigDecimal getTotalprizeAmount() {
		return totalprizeAmount;
	}
	public void setTotalprizeAmount(BigDecimal totalprizeAmount) {
		this.totalprizeAmount = totalprizeAmount;
	}
	public BigDecimal getOrderbetAmount() {
		return orderbetAmount;
	}
	public void setOrderbetAmount(BigDecimal orderbetAmount) {
		this.orderbetAmount = orderbetAmount;
	}
	public BigDecimal getOrderprizeAmount() {
		return orderprizeAmount;
	}
	public void setOrderprizeAmount(BigDecimal orderprizeAmount) {
		this.orderprizeAmount = orderprizeAmount;
	}
	public Long getCaselotCount() {
		return caselotCount;
	}
	public void setCaselotCount(Long caselotCount) {
		this.caselotCount = caselotCount;
	}
	public BigDecimal getCaselotBetAmount() {
		return caselotBetAmount;
	}
	public void setCaselotBetAmount(BigDecimal caselotBetAmount) {
		this.caselotBetAmount = caselotBetAmount;
	}
	public BigDecimal getCaselotPrizeAmount() {
		return caselotPrizeAmount;
	}
	public void setCaselotPrizeAmount(BigDecimal caselotPrizeAmount) {
		this.caselotPrizeAmount = caselotPrizeAmount;
	}
	public Integer getHalfbetCount() {
		return halfbetCount;
	}
	public void setHalfbetCount(Integer halfbetCount) {
		this.halfbetCount = halfbetCount;
	}
	public BigDecimal getHalfbetAmount() {
		return halfbetAmount;
	}
	public void setHalfbetAmount(BigDecimal halfbetAmount) {
		this.halfbetAmount = halfbetAmount;
	}
	public BigDecimal getHalfprizeAmount() {
		return halfprizeAmount;
	}
	public void setHalfprizeAmount(BigDecimal halfprizeAmount) {
		this.halfprizeAmount = halfprizeAmount;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	
	

}
