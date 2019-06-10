package com.lottery.common.contains.pay;


public enum PayStatus {
	
	NOT_PAY(1,"未支付"),
	ALREADY_PAY(2,"支付中"),
    REFUNDED(3,"已退款"),
	PAY_SUCCESS(4,"支付成功"),
	PAY_FAILED(5,"支付失败"),
	PART_REFUNDED(6,"部分退款"),
	all(0,"全部");
	public int value;
	public String name;
	
	PayStatus(int value,String name){
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
	 public String toString(){  //自定义的public方法   
	 return "{value:"+value+",name:"+name+"}";   
	 } 
   
}
