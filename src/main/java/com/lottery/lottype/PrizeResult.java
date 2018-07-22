package com.lottery.lottype;

import java.math.BigDecimal;

public class PrizeResult {

	private BigDecimal preTaxAmt;
	
	private BigDecimal afterTaxAmt;
	
	private BigDecimal addPrizeAmt;
	
	private String prizeLevelInfo;
	
	private boolean isBig;

	public BigDecimal getPreTaxAmt() {
		return preTaxAmt;
	}

	public void setPreTaxAmt(BigDecimal preTaxAmt) {
		this.preTaxAmt = preTaxAmt;
	}

	public BigDecimal getAfterTaxAmt() {
		return afterTaxAmt;
	}

	public void setAfterTaxAmt(BigDecimal afterTaxAmt) {
		this.afterTaxAmt = afterTaxAmt;
	}

	public String getPrizeLevelInfo() {
		return prizeLevelInfo;
	}

	public void setPrizeLevelInfo(String prizeLevelInfo) {
		this.prizeLevelInfo = prizeLevelInfo;
	}

	public boolean isBig() {
		return isBig;
	}

	public void setBig(boolean isBig) {
		this.isBig = isBig;
	}
	
	
	
	
	public BigDecimal getAddPrizeAmt() {
		return addPrizeAmt;
	}

	public void setAddPrizeAmt(BigDecimal addPrizeAmt) {
		this.addPrizeAmt = addPrizeAmt;
	}

	public PrizeResult(BigDecimal preTaxAmt, BigDecimal afterTaxAmt,
			String prizeLevelInfo) {
		super();
		this.preTaxAmt = preTaxAmt;
		this.afterTaxAmt = afterTaxAmt;
		this.prizeLevelInfo = prizeLevelInfo;
	}

	public void add(PrizeResult caculatePrize) {
		this.preTaxAmt = this.preTaxAmt.add(caculatePrize.getPreTaxAmt());
		this.afterTaxAmt = this.afterTaxAmt.add(caculatePrize.getAfterTaxAmt());
	}
	
	
}
