package com.lottery.lottype;

import java.math.BigDecimal;

public class OrderCalResult {

	private String orderid;
	private boolean isbig;
	private boolean isencash;
	private BigDecimal pretaxprize;
	private BigDecimal aftertaxprize;
	private int lotterytype;
	private int bettype;
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public boolean isIsbig() {
		return isbig;
	}
	public void setIsbig(boolean isbig) {
		this.isbig = isbig;
	}
	public boolean isIsencash() {
		return isencash;
	}
	public void setIsencash(boolean isencash) {
		this.isencash = isencash;
	}
	public BigDecimal getPretaxprize() {
		return pretaxprize;
	}
	public void setPretaxprize(BigDecimal pretaxprize) {
		this.pretaxprize = pretaxprize;
	}
	public BigDecimal getAftertaxprize() {
		return aftertaxprize;
	}
	public void setAftertaxprize(BigDecimal aftertaxprize) {
		this.aftertaxprize = aftertaxprize;
	}
	public int getLotterytype() {
		return lotterytype;
	}
	public void setLotterytype(int lotterytype) {
		this.lotterytype = lotterytype;
	}
	public int getBettype() {
		return bettype;
	}
	public void setBettype(int bettype) {
		this.bettype = bettype;
	}
	
	
}
