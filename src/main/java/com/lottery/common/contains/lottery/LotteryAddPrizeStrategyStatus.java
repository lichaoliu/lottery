package com.lottery.common.contains.lottery;

public enum LotteryAddPrizeStrategyStatus {

	//投注类型
		SAVED(1,"保存"),
		OPEN(2,"开启"),
		CLOSED(3,"关闭"),
		DELETE(4,"删除"),
		all(0,"全部")
		;
		public int value;
		public String name;
		
		LotteryAddPrizeStrategyStatus(int value,String name){
			this.value=value;
			this.name=name;
		}
		
		
		public static LotteryAddPrizeStrategyStatus get(int value) {// 获取所有的值
			LotteryAddPrizeStrategyStatus[] lotteryAddPrizeStrategyStatuses = LotteryAddPrizeStrategyStatus.values();
			for (LotteryAddPrizeStrategyStatus type : lotteryAddPrizeStrategyStatuses) {
				if (type.value == value) {
					return type;
				}
			}
			return null;
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
