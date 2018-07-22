package com.lottery.ticket.vender.lotterycenter.tianjin;

public enum TJFCLotteryTypeEnum {
	
	SSQ("双色球",1001,"B001"),
	D3("福彩3D",1002,"D3"),
	QLC("七乐彩",1003,"QL730"),
	TJKL10("天津快乐十分",1004,"K520"),
	TJSSC("天津时时彩",1106,"DT5");
	
	public String name;
	public int apiLotteryType;
	public String fcName;
	
	TJFCLotteryTypeEnum(String name,int apiLotteryType,String fcName){
		this.name = name;
		this.apiLotteryType = apiLotteryType;
		this.fcName = fcName;
	}
	
	public static TJFCLotteryTypeEnum getLotteryType(String fcName){
		TJFCLotteryTypeEnum[] lotteryType=TJFCLotteryTypeEnum.values();
		for(TJFCLotteryTypeEnum type:lotteryType){
			if(fcName.equals((type.fcName))){
				return type;
			}
		}
		return null;
	}
	
	public static TJFCLotteryTypeEnum getFCLotteryType(int apiLotteryType){
		TJFCLotteryTypeEnum[] lotteryType=TJFCLotteryTypeEnum.values();
		for(TJFCLotteryTypeEnum type:lotteryType){
			if(apiLotteryType == type.apiLotteryType){
				return type;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public int getApiLotteryType() {
		return apiLotteryType;
	}

	public String getFcName() {
		return fcName;
	}
	
	
	
}
