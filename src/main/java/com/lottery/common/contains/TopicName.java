package com.lottery.common.contains;

public enum TopicName {
	orderprize("jms:topic:orderprize","算奖结束后发送topic队列"),
	orderresult("jms:topic:orderresult","出票结果topic队列"),
	open_phase("jms:topic:open-phase","开新期"),
	end_phase("jms:topic:end-phase","期结"),
	prizeOpen("jms:topic:prizeopen-queue","开奖号码队列"),
	orderEncashed("jms:topic:orderEncashed","派奖结束队列"),
	caselotOrderEncashed("jms:topic:caselotOrderEncashed","合买派奖结束队列"),
	calcuteEnd("jms:topic:calcute-end","算奖结束队列"),
	encashEnd("jms:topic:encash-end","派奖结束队列"),
	winBigPrize("jms:topic:winBigPrize","中大奖队列"),
	prizeNotEnd("jms:topic:prizeNotEnd","规定时间内算奖未结束队列"),
	encashNotEnd("jms:topic:encashNotEnd","规定时间内派奖未结束队列"),
	createCaselot("jms:topic:createCaselot","创建合买topic队列"),

	phaseOpenPrize("jms:topic:phase-open-prize","彩期开奖"),
	
	all("defalut","默认");
	public String value;
	public String name;
	TopicName(String  value,String name) {
		this.value=value;
		this.name=name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
