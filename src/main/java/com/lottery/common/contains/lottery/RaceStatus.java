package com.lottery.common.contains.lottery;


/**
 * 竞彩足球，篮球，单场状态
 * */
public enum RaceStatus {
	 UNOPEN(1,"未开启"),//获取到，待审核
	 OPEN(2,"已开启"),//正常销售
	 PAUSE(3,"暂停销售"),//暂停
	 CLOSE(4,"关闭"),//比赛到期结束
	 CANCEL(5,"赛事取消"),
	 ALL(0,"全部");
	public int value;
	public String name;
	 RaceStatus(int value,String name) {
       this.value=value;
       this.name=name;
	 }
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString(){
		return "[name="+this.name+",value="+this.value+"]";
	}
}

