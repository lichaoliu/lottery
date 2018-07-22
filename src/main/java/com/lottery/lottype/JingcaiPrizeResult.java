package com.lottery.lottype;

import java.math.BigDecimal;

//竞彩奖金计算结果
public class JingcaiPrizeResult {

	private int type;
	
	private BigDecimal prize;
	
	private BigDecimal afterTaxPrize;
	
	private String prizedetail;
	
	private boolean isBig;
	
	private boolean iscancelprize;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public BigDecimal getPrize() {
		return prize;
	}

	public void setPrize(BigDecimal prize) {
		this.prize = prize;
	}

	public BigDecimal getAfterTaxPrize() {
		return afterTaxPrize;
	}

	public void setAfterTaxPrize(BigDecimal afterTaxPrize) {
		this.afterTaxPrize = afterTaxPrize;
	}

	public String getPrizedetail() {
		return prizedetail;
	}

	public void setPrizedetail(String prizedetail) {
		this.prizedetail = prizedetail;
	}

	public boolean isBig() {
		return isBig;
	}

	public void setBig(boolean isBig) {
		this.isBig = isBig;
	}

	public boolean isIscancelprize() {
		return iscancelprize;
	}

	public void setIscancelprize(boolean iscancelprize) {
		this.iscancelprize = iscancelprize;
	}
	
	
	
}
