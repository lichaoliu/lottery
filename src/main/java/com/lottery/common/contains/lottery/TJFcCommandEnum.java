package com.lottery.common.contains.lottery;

public enum TJFcCommandEnum {
	TjSystemTime("时间同步",8,""),
	betLottery("销售请求",4,""),
	newIssue("新期文件通知",1,""),
	rsNewIssue("新期文件通知回复",0,"80000001"),
	winInfo("开奖公告通知",2,""),
	rsWinInfo("开奖公告通知回复",0,"80000002"),
	winData("中奖文件通知",3,""),
	rsWinData("中奖文件通知回复",0,"80000003"),
	balanceAccout("当前用户余额查询",11,""),
	awardCommand("兑奖命令",5,"");

	TJFcCommandEnum(String command,int value,String hex){
		this.command=command;
		this.value=value;
		this.hex=hex;
	}
	
	private String command;
	private int value;
	private String hex;
	
	public String getCommand() {
		return command;
	}
	public int getValue() {
		return value;
	}
	public String getHex() {
		return hex;
	}
}
