package com.lottery.core;

public enum SeqEnum {
	userid(1l,"user_info"),
	orderid(2l,"lottery_order"),
	ticketId(3l,"ticket"),
	batchTicketId(4l,"ticket_batch"),
	useraccount_detail(5l,"user_account_detail"),
	usertransaction(6l,"user_transaction"),
	zd_usertransaction(7l,"zdusertransaction"),
	messageid(8l,"messageid"),
	chaseid(9l,"lottery_chase"),
	caselotid(10l,"lottery_caselot"),
	caselotbuyid(11l,"lottery_caselot_buy"),
	drawid(12l,"drawid"),
	drawaccount_batchid(13l,"drawaccount_batchid"),
    terminalId(14l,"terminal"),
    terminalConfigId(15l,"terminal_config"),
    terminalPropertyId(16l,"terminal_property"),
    terminalTicketConfigId(17l,"terminal_ticket_config"),
    payPropertyId(18l,"pay_property"),
    lotteryOrderChaseId(19l,"lottery_order_chase"),
    userDrawBankId(20l,"user_draw_bank"),
    dcRaceId(21l,"dc_race"),
    zcRaceId(22l,"zc_race"),
    lotteryPhaseId(23l,"lottery_phase"),
    userachievementId(24l,"user_achievement"),
    userachievementdetailId(25l,"user_achievement_detail"),
    autojoinId(26l,"auto_join"),
    autojoin_detailId(27l,"autojoin_detail"),
    ticketallotlogId(28l,"ticket_allot_log"),
    ticketbatchsendlogId(29l,"ticket_batch_send_log"),
    useraccountaddId(30l,"user_account_add"),
    lotteryChaseBuyId(31l,"lottery_chase_buy"),
    userRechargeGiveId(32l,"user_recharge_give"),
    userRebate(33l,"user_rebate"),

    lotteryAgencyPointLocation(35l,"lottery_agency_point_location"),
    lotteryphasedrawconfig(36l,"lottery_phase_draw_config"),
    lotteryTerminalConfig(37l,"lottery_terminal_config"),

    couponTypeId(38l,"coupon_type_id"),
    userCouponId(39l,"user_coupon_id"),
    lotteryChannelPartner(40l,"lottery_channel_partner"),

    systemExceptionMessge(41l,"system_exception_message"),

    bettingLimitNumber(42l,"betting_limit_number"),
    userRedPackage(43l,"user_red_package"),

    /**
     * 以下是出票商messageid
     * */
    vender_gzcp_messageid(1000l,"vender_gzcp_messageid"),
	all(0l,"all")
	;
	public Long id;
	public String name;
	SeqEnum(Long id,String name){
		this.id=id;
		this.name=name;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "表"+this.name+"的id生成方法";
    }
}
