package com.lottery.common.contains.lottery;

public enum TerminalStatus {
	enable(1,"可出票"),
	disenable(2,"不可出票"),
	close(3,"已关闭"),
	all(0,"全部"),
	;
	public int value;
	public String name;
	
	 TerminalStatus(int value,String name){
		this.value=value;
		this.name=name;
	}
	 @Override
		public String toString(){
			return "[name="+this.name+",value="+this.value+"]";
		}

}
