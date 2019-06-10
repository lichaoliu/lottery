package com.lottery.common.contains.lottery;

import java.util.Arrays;
import java.util.List;

public enum TerminalType {
	T_DYJ(1,"大赢家"),
	T_TJFC(2,"天津福彩"),
	T_FCBY(3,"丰彩博雅"),
	T_NXFC(4,"宁夏福彩"),
	T_ZCH(5,"中彩汇"),
	T_HY(6,"华阳"),
	T_YM(7,"饮马"),
	T_YX(8,"银溪"),
	T_HLJFC(9,"黑龙江福彩"),
	T_TJFC_CENTER(10,"天津福彩中心"),
	T_GXFC(11,"广西福彩"),
	T_QH(12,"齐汇"),
	T_XCS(13,"小财神"),
	T_ARUI(14,"安瑞"),
	T_GZCP(15,"内置出票_GZ"),
	T_GX(16,"国信出票"),
	T_ZHONGYING(17,"默认出票"),
	T_OWN_CENTER(18,"出票中心"),
	T_HUAI(19,"互爱科技"),
	T_ZY(20,"掌奕"),
	T_SDHC(21,"山东宏彩"),
	T_XMAC(22,"厦门爱彩"),
	T_JINRUO(23,"金诺"),
	T_JYDP(24,"内置出票_JY"),
	T_SHCP(25,"内置出票_SH"),
	T_GAODE(26,"高德出票"),
	T_HUANCAI(27,"环彩出票"),
	T_RUIYING(28,"瑞盈出票"),
	T_LETOU(29,"硬件打票_LT"),
	T_HENAN(30,"河南出票"),
	T_SJZ(31,"石家庄出票"),
	T_Virtual(-1,"虚拟出票"),
	
	all(0,"全部");
	public int value;
	public String name;
	TerminalType(int value,String name){
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
	
	
	public static TerminalType get(int value){
		TerminalType[] type=values();
		for(TerminalType terminalType:type){
			if(terminalType.value==value){
				return terminalType;
			}
		}
		return null;
	}
	
	public String toString(){
		return "[name="+name+",value="+value+"]";
	}
	
	public static List<TerminalType> get(){
		return Arrays.asList(values());
	}

}
