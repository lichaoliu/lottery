package com.lottery.core.util;

public enum ResultError {
	
	//送票异常
	Parameter_error("SYS5001","验证参数信息错误"),
	Convert_error("SYS0101","转化请求协议参数错误"),
	terminal_enable("APP0205","终端设备状态无效"),			
	Lottery_error("SYS0311","期号错误"),			
	Error_serizeNum_password("SYS0312","获取流水号和彩票随机密码错误"),			
	Lotteryinfo_error("SYS0313","获取游戏信息错误"),			
	Acquire_systemparameter("SYS0302","获取系统参数值错误"),			
	Acquire_uuid("SYS0314","获取UUID错误"),		
	Saveticket_error("SYS0315","保存票据信息错误"),			
	FailedCon_center("SYS9016","连接福彩中心失败"),			
	Center_return("SYS9017","福彩中心返回空"),			
	Deal_failed("SYS9003","FCSrv 处理结果:失败"),			
	save_ticket_failed("SYS0318","保存票据信息失败"),			
	Query_password("SYS0317","查询用户密码错误（查表）"),			
	User_setpassword("SYS0309","用户输入密码错误"),			
	acquire_lotteryinfo("SYS0301","获取游戏区域信息错误"),			
	Deduct_money("SYS0402","扣款失败"),
	
	//博彩异常
	orderid_exists("2007","代理商订单号已存在"),
	content_limit("20100713","限号"),
	notify_sell("200028", "彩种暂停销售"),
	stop_sell("200029", "彩种停止销售"),

    //充值异常
	GENERAL_FAIL("GENERAL_FAIL","一般性错误"),//	一般性错误
	ILLEGAL_PARAMETER("ILLEGAL_PARAMETER","参数错误"),//参数错误
	ILLEGAL_MERCHANT_ID("ILLEGAL_MERCHANT_ID","合作伙伴错误"),//合作伙伴错误
	ILLEGAL_SIGN("ILLEGAL_SIGN","签名错误"),//签名错误
	SERVICE_NOT_SUPPORT("SERVICE_NOT_SUPPORT","不支持此服务"),	//不支持此服务
	ILLEGAL_ROYALTY_AMOUNT("ILLEGAL_ROYALTY_AMOUNT","错误的纷扰金额"),//错误的纷扰金额
	OUT_TRADE_NO_REPEAT("OUT_TRADE_NO_REPEAT","外部提交的交易号重复"),//外部提交的交易号重复
	ILLEGAL_CHARSET("ILLEGAL_CHARSET","错误字符编码"),//错误字符编码
	USER_NOT_EXIST("USER_NOT_EXIST","用户不存在"),//用户不存在
	BINDING_NOT_EXIST("BINDING_NOT_EXIST","分润绑定关系不存在"),//分润绑定关系不存在
	ACCOUNT_STATUS_NOT_ALLOW("ACCOUNT_STATUS_NOT_ALLOW","账户状态不允许"),//账户状态不允许
	AVAILABLE_AMOUNT_NOT_ENOUGH("AVAILABLE_AMOUNT_NOT_ENOUGH","可用余额不足"),//可用余额不足
	TRADE_NOT_EXIST("TRADE_NOT_EXIST","交易不存在"),//交易不存在
	TRADE_STATUS_NOT_ALLOW("TRADE_STATUS_NOT_ALLOW","交易状态不允许"),//交易状态不允许
	GREATER_REFUND_MONEY("GREATER_REFUND_MONEY","没有足够的退款金额"),//没有足够的退款金额
	GREATER_UNFROZEN_MONEY("GREATER_UNFROZEN_MONEY","没有足够的解冻金额"),//没有足够的解冻金额
	pay_error("60001","充值错误"),
	pay_not_exits("60002","充值记录不存在"),
	Paymoney_not_equal("60002","充值金额不正确"),
	REQUIREPAY_FAIL("10001","请求支付异常"),//	请求支付异常异常
	CONFIRMPAY_FAIL("10002","确定支付异常");//	确定支付异常异常
	
	
	public String value;
	public String memo;
	public String getValue() {
		return value;
	}

	public String getMemo() {
		return memo;
	}
	
	ResultError(String value, String memo) {
		this.value = value;
		this.memo = memo;
	}
}
