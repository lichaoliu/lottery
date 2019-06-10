package com.lottery.common.dto;

import com.lottery.core.domain.AutoJoinDetail;
import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.domain.LotteryCaseLotBuy;
import com.lottery.core.domain.UserInfo;

public class AutoJoinDetailDTO {

	private AutoJoinDetail autoJoinDetail;

	private LotteryCaseLot caseLot;

	private LotteryCaseLotBuy caseLotBuy;

	private UserInfo starter;
	public AutoJoinDetail getAutoJoinDetail() {
		return autoJoinDetail;
	}

	public void setAutoJoinDetail(AutoJoinDetail autoJoinDetail) {
		this.autoJoinDetail = autoJoinDetail;
	}

	public LotteryCaseLot getCaseLot() {
		return caseLot;
	}

	public void setCaseLot(LotteryCaseLot caseLot) {
		this.caseLot = caseLot;
	}

	public LotteryCaseLotBuy getCaseLotBuy() {
		return caseLotBuy;
	}

	public void setCaseLotBuy(LotteryCaseLotBuy caseLotBuy) {
		this.caseLotBuy = caseLotBuy;
	}

	public UserInfo getStarter() {
		return starter;
	}

	public void setStarter(UserInfo starter) {
		this.starter = starter;
	}
	
}
