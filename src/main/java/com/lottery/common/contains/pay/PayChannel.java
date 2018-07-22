package com.lottery.common.contains.pay;

import java.util.Arrays;
import java.util.List;

public enum PayChannel {
	rbpay("rbao","融宝充值"),
	zfbpay("zfb","支付宝快捷支付"),
    zxwechatpay("zxwechatpay","中信微信充值"),
    inowpaymobileqq("inowpaymobileqq","现在支付(手Q支付)"),
	yeebao("yeebao","易宝充值"),
	yeebaowap("yeebaowap","易宝wap充值"),
	yeebaonew("yeebaonew","易宝手机新版充值"),
	zfbwappay("zfbwap","支付宝WAP支付"),
	zfbwebpay("zfbweb","支付宝Web充值"),
	elinkpay("elink","银联控件充值"),
	elinknewpay("elinknew","银联控件新版充值"),
	elinkwappay("elinkwappay","银联wap充值"),
	elinkpcpay("elinkpc","银联快捷pc充值"),
	xfwap("xfwap","先锋wap充值"),
	elinkpcwypay("elinkpcwy","银联网银pc充值"),
	zfbdraw("zfbdraw","支付宝提现"),
	zfbbankdraw("zfbbankdraw","支付宝银行卡提现"),
	elinkdraw("elinkdraw","银联提现"),
	handpay("handpay","人工充值"),
	jdwappay("jdwappay","京东wap充值"),
    virtualcoinpay("virtualcoinpay","虚拟钱充值"),
    sfth5pay("sfth5pay","盛付通h5充值"),
    sftpcpay("sftpcpay","盛付通pc充值"),
    sftpay("sftpay","盛付通手机充值"),
	all("0","全部");
	public String value;
	public String name;
	PayChannel(String value,String name){
		this.name=name;
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	public String getName() {
		return name;
	}



	public static PayChannel get(String value){
		PayChannel[] lotteryType =PayChannel.values();
		for(PayChannel type:lotteryType){
			if(type.value.equals(value)){
				return type;
			}
		}
		return null;
	}


	 @Override
	 public String toString(){  //自定义的public方法   
	 return "[value:"+value+",name:"+name+"]";   
	 }   

	
	public static List<PayChannel> get(){//获取所有的值
		return Arrays.asList(PayChannel.values());
	}
	
	
	

}
