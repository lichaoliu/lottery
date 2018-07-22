package com.lottery.common.dto;

import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.domain.LotteryCaseLotBuy;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.ticket.LotteryOrder;

public class CaselotBuyDTO {
	LotteryCaseLotBuy caseLotBuy;
	UserInfo userinfo;
	LotteryCaseLot caseLot;
	LotteryOrder order;
	public LotteryCaseLotBuy getCaseLotBuy() {
		return caseLotBuy;
	}
	public void setCaseLotBuy(LotteryCaseLotBuy caseLotBuy) {
		this.caseLotBuy = caseLotBuy;
	}
	public UserInfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
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
}
