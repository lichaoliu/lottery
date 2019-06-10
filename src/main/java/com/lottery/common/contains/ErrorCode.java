package com.lottery.common.contains;

public enum ErrorCode {
	Success("0","成功"),
	handling("1","处理中"),
	Faile("2","失败"),
	paused_sale("3","暂停销售"),
	isused("4","属性被占用"),
	no_exits("5","结果不存在"),
	exits("6","结果已存在"),
	paramter_error("7", "参数错误"),
	http_error("9","http请求错"),
	http_502("502","http502错误"),

	
	//彩种新期
	lotterytype_not_exits("10001","彩种不存在"),
	current_phase_not_exits("10002","当前期不存在"),
	phase_status_error("10003","期状态不正确"),
	no_phase("10004","彩期不存在"),
	not_enough_phase("10005","彩期不足"),
	not_sale_phase("10006","彩期未开售"),
	no_current_phase("10007","彩期无当前期"),
	phase_outof("10008","已过期"),
	phase_not_close("10009","未截止"),
	phase_is_not_current("10010","彩期不是当前期"),
	phase_format_error("10011","彩期格式不正确"),
	
	//投注查询
	betamount_error("20001","注码金额错误"),
	betcode_lotmulti_error("20002","注码拆分倍数错误"),
	betcode_jingcai_odds_error("20003","竞彩注码赔率不对应"),
	match_not_exist("20004","对阵不存在"),
	match_not_open("20005","对阵未开启"),
	match_out_time("20006","对阵已经过期"),
	match_playtype_unsupport("20007","对阵不支持该玩法"),
	not_chaseorder("20008","请求不是追号订单"),
	chase_not_allowcancel("20009","追号不允许撤销"),
	select_date_error("20010","日期范围不正确"),
	no_used_terminal("20011","无可用终端"),
	chase_not_support("20022","彩种不允许追号"),
	bet_limit("20023","投注限号或不允许投注"),
	//开奖，派奖
	prizelevel_not_exist("30001","奖级信息不存在"),
	no_windcode("30002","开奖号码不存在"),
	phase_info_error("30003","奖级信息不正确"),
	phase_already_prize("30004","期已经开奖"),
	phase_addprize_strategy_repeat("30005","加奖策略重复"),
	already_encash("30006","订单已派奖"),
	phase_wincode_regex_error("30007","开奖号码格式不正确"),
	lottery_draw_task_error("30008","开奖任务不正确"),

	//用户账号

	user_exits("40001","用户已经存在"),
	no_user("40002","用户不存在"),
	user_passwd_error("40003","原始密码错误"),
	no_account("40004","用户账户不存在"),
	account_no_enough("40005","用户可用余额不足"),
	account_error("40006","账号操作出错"),
	account_drawamt_not_enough("40007", "可提现余额不足"),
	no_usertransaction("40008","充值订单不存在"),
	no_draw_bank("40009","用户未绑定银行卡号"),
	user_registe_error("40010","用户注册出错"),
    username_exits("40011","用户名已存在"),
    mobile_exits("40012","手机号码已存在"),
    user_binding_phone_null("40013","用户无绑定手机号码"),
	user_realName_noexits("40014","用户未填写真实姓名"),
	card_bind_exits("40015","该卡号已绑定"),
	card_bind_noexits("40016","该卡号未绑定"),
	user_card_bind_noexits("40017","用户绑定不存在"),
    user_phone_authcode_error("400018","手机验证码错误"),
    user_phone_limit_error("400019","验证码操作太频繁"),

	user_red_package_notexits("400020","红包不存在或已领取"),




    //合买
	caseLot_order_not_exist("50001", "该合买订单不存在"),
	caselot_totalamt_lessthan_requirements("50002","合买金额小于要求金额"),
	caseLot_safe_not_zero("50003", "合买金额不能都为零"),
	caselot_not_exist("50004", "合买方案不存在"),
	caselot_full("50005","合买方案已满"),
	caselot_safeamt_error("50006", "保底金额异常"),
	caselot_unfree_error("50007","合买解冻异常"),
	caseLot_not_starter("50008", "只有发起者才能取消"),
	caselot_cancel_error("50009", "合买撤销异常"),
	caseLot_rate_bigger50("50010", "撤销的合买进度大于50%"),
	caselot_starter_not_cancel_buy("50011", "合买发起人不能撤资"),
	caselot_cancel_buy_error("50012", "合买撤资异常"),
	caselot_retract_bigger20("50013", "撤资的合买进度大于20%"),
	autojoin_cannt_join_self("50014","不能自动跟单自己的合买"),
	autojoin_amt_error("50015","跟单金额错误"),
	casetlot_not_support("50016","该彩种合买不支持"),
	caseLot_not_bystarter("50017", "只有发起者才能取消定制我的"),
	
	//代理
	agency_data_notexists("60001","数据不存在"),
	agency_data_hasexists("60002","数据已存在"),
	agency_modify_percent_not_parent("60003","只有上级代理可以修改下级代理比例"),
	agency_child_not_bigger_parent("60004","下级代理比例不能超过上级代理比例"),

	//优惠券
	coupon_notexists("62001","优惠券不存在"),
	coupon_notunuse("62002","优惠券不是未使用"),
	coupon_notowner("62003","优惠券不是本人"),
	coupon_timeout("62004","优惠券过期"),
	coupon_notmatch_amount("62005","优惠券金额不符合金额条件"),
	coupon_notmatch_lotterytype("62006","优惠券金额不符合彩种条件"),
	coupon_not_open("62007","优惠券不是启用"),
	coupon_share_not_open("62008","优惠券分享未启用"),
	coupon_share_duplication("62009","优惠券分享重复领取"),
	coupon_share_order_not_support("62010","分享的订单不存在或已失效"),

	//出票商错误编码
    ip_error("70001","ip错误"),
    des_error("70002","解密错误"),
    body_error("70003","解析错误"),
    command_error("70004","请求命令不存在"),
    merchant_stop("70005","账号已停用"),
    
    
    unknown_error("4444","未知异常"),
    system_error("9999","系统错误");

	public String value;
	public String memo;
	ErrorCode(String value, String memo) {
		this.value = value;
		this.memo = memo;
	}
	
	public static ErrorCode getErrorCode(String value){
		ErrorCode[] errorCode=ErrorCode.values();
		for(ErrorCode error:errorCode){
			if(error.value.equals(value)){
				return error;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public String getMemo() {
		return memo;
	}
	

}
