package com.lottery.common.dto;

import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.ticket.LotteryOrder;

public class CaselotDTO {
	UserInfo starter;
	LotteryCaseLot caseLot;
	LotteryOrder order;
	boolean disPlayContent;
	public UserInfo getStarter() {
		return starter;
	}
	public void setStarter(UserInfo starter) {
		this.starter = starter;
	}
	public LotteryCaseLot getCaseLot() {
		return caseLot;
	}
	public void setCaseLot(LotteryCaseLot caseLot) {
		this.caseLot = caseLot;
	}
	public LotteryOrder getOrder() {
		return order;
	}
	public void setOrder(LotteryOrder order) {
		this.order = order;
	}
	public boolean isDisPlayContent() {
		return disPlayContent;
	}
	public void setDisPlayContent(boolean disPlayContent) {
		this.disPlayContent = disPlayContent;
	}
	
	
}
