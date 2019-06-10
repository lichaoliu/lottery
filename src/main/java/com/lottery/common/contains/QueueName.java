package com.lottery.common.contains;

public enum QueueName {
	//queue
	betSplitQueue("jms:queue:betSplit","订单投注拆票队列"),
	betGaopinSplitQueue("jms:queue:betGaopinSplit","高频拆票队列"),


	tranasctionbet("jms:queue:tranasctionbet","充值完成拆票队列"),

	cancelChercher("ticketjms:queue:cancelChecker","撤单检票队列"),
	gaopinChercher("ticketjms:queue:gaopinChecker","检票队列"),

	betFreeze("ticketjms:queue:betFreeze","投注冻结队列"),
	betgaopinFreeze("ticketjms:queue:betgaopinFreeze","高频投注冻结队列"),




	betChercher("jms:queue:betChecker","检订单队列"),



	betSuccessDeduct("jms:queue:betSuccessDeduct","成功投注扣款队列"),
	betFailuerUnfreeze("jms:queue:betFailunfreeze","投注失败解冻队列"),
	
	betRefund("jms:queue:betRefund","退款队列"),
	continueChase("jms:queue:continueChaseQueue","继续追号队列"),
	chasebet("jms:queue:chasebet","具体彩种期追号"),

	hemaiSuccessDeduct("jms:queue:hemaiSuccessDeduct","合买成功扣款队列"),
	hemaiFailuerUnfreeze("jms:queue:hemaiFailunfreeze","合买失败解冻队列"),
	ticketSend("jms:queue:ticketSend","送票队列"),
	ticketPrize("jms:queue:ticketPrize","查询票中奖队列"),
	ticketCheck("jms:queue:ticketCheck","检票队列"),
	
	prizeExecute("jms:queue:prizeExecute","大盘算奖队列"),
	prizeorderDapan("jms:queue:prizeorderdapan-queue","大盘算奖队列备用"),
	prizeorderGaopin("jms:queue:prizeordergaopin-queue","高频算奖队列"),
	encashorderDapan("jms:queue:encashorderdapan-queue","大盘派奖队列"),
	encashorderGaopin("jms:queue:encashordergaopin-queue","高频派奖队列"),

	ticketNotice("jms:queue:ticketNotice","普通通知队列"),
	ticketNoticeJingcai("jms:queue:ticketNoticeJingcai","竞彩通知队列"),
    virtualPay("jms:queue:virtualPay","虚拟钱充值队列"),
    virtualJifen("jms:queue:virtualJifen","虚拟积分队列"),

	;
	public String value;
	public String name;
	QueueName(String  value,String name) {
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
