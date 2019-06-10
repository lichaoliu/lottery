package com.lottery.common.contains.lottery;

public enum ChaseBuyStatus {

	chasebuy_no(1,"未追号"),
	chasebuy_yes(2,"已追号"),
	chasebuy_cancel(3,"撤销追号"),
	all(0,"全部");
	public int value;
	public String name;
	ChaseBuyStatus(int value,String name) {
      this.value=value;
      this.name=name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static ChaseBuyStatus getChaseBuyStatus(int value){
		ChaseBuyStatus[] chaseBuyStatus= ChaseBuyStatus.values();
		for(ChaseBuyStatus type:chaseBuyStatus){
			if(type.value==value){
				return type;
			}
		}
		return null;
	}
	 @Override
	    public String toString() {
	      return "[value="+value+",name="+name+"]";
	    }
}
