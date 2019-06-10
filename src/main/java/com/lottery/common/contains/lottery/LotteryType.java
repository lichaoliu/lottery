package com.lottery.common.contains.lottery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum LotteryType {
	/**
	 * 彩种规则：福彩以10开头，四位数字 体彩以20开头，四位数字 竞彩以30开头
	 * */
	SSQ(1001, "双色球"), 
	F3D(1002, "福彩3D"), 
	QLC(1003, "七乐彩"), 
	TJKL10(1004,"天津快乐十分"),
	NXK3(1005, "宁夏快三"), 
	HLJSSC(1006, "黑龙江时时彩"), 
	CQSSC(1007,"重庆时时彩"), 
	AHK3(1008, "安徽快三"), 
	JSK3(1009, "江苏快三"), 
	GXK3(1010, "广西快三"),
	JXSSC(1011,"江西时时彩"),
	CQKL10(1012,"重庆快乐十分"),
	GDKL10(1013,"广东快乐十分"),
	XJSSC(1014,"新疆时时彩"),
	JXK3(1015,"江西快三"),


	HLJKL10(1104, "黑龙江快乐十分"), 
	GXKL10(1105, "广西快乐十分"), 
	TJSSC(1106, "天津时时彩"), 
	
	CJDLT(2001, "大乐透"), 
	PL3(2002, "排列三"), 
	PL5(2003, "排列五"), 
	QXC(2004, "七星彩"),
	GD_11X5(2005, "广东11选5"),
	JX_11X5(2006, "江西11选5"), 
	SD_11X5(2007, "山东11选5"),
	XJ_11X5(2008,"新疆11选5"),
	SD_KLPK3(2009, "山东快乐扑克3"),
	HB_11X5(2010,"河北11选5"),
	HUBEI_11X5(2011,"湖北11选5"),

	JCLQ_SF(3001, "竞彩篮球胜负"), 
	JCLQ_RFSF(3002, "竞彩篮球让分胜负"),
	JCLQ_SFC(3003,"竞彩篮球胜分差"), 
	JCLQ_DXF(3004, "竞彩篮球大小分"), 
	JCLQ_HHGG(3005, "竞彩篮球混合过关"), 
	JCZQ_RQ_SPF(3006, "竞彩足球让球胜平负"), 
	JCZQ_BF(3007, "竞彩足球比分"), 
	JCZQ_JQS(3008,"竞彩足球总进球数"), 
	JCZQ_BQC(3009, "竞彩足球半全场胜平负"), 
	JCZQ_SPF_WRQ(3010,"竞彩足球胜平负"), 
	JCZQ_HHGG(3011, "竞彩足球混合过关"),
	
	JC_GUANJUN(3012, "竞彩冠军"),
	JC_GUANYAJUN(3013, "竞彩冠亚军"),

	ZC_SFC(4001, "足彩胜负彩"), 
	ZC_RJC(4002, "足彩任九场"), 
	ZC_JQC(4003, "足彩4场进球彩"),
	ZC_BQC(4004, "足彩6场半全场"),

	DC_SPF(5001, "北单胜平负"), 
	DC_ZJQ(5002, "北单总进球"), 
	DC_BQC(5003, "北单半全场"),
	DC_SXDS(5004, "北单上下盘单双"), 
	DC_BF(5005, "北单比分"), 
	DC_SFGG(5006, "北单胜负过关"),

	ALL(0, "全部");
	;
	public int value;
	public String name;

	LotteryType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static LotteryType getLotteryType(int value) {
		return get(value);
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() { // 自定义的public方法
		return "[value:" + value + ",name:" + name + "]";
	}

	public String getName() {
		return name;
	}

	public static List<LotteryType> get() {// 获取所有的值
		
		return Collections.unmodifiableList(Arrays.asList(LotteryType.values()));
	}

	public static LotteryType get(int value) {// 获取所有的值
		LotteryType[] lotteryType = LotteryType.values();
		for (LotteryType type : lotteryType) {
			if (type.value == value) {
				return type;
			}
		}
		return null;
	}

	public static List<LotteryType> getGaoPin() {
		List<LotteryType> typeList = new ArrayList<LotteryType>();
		typeList.add(LotteryType.NXK3);
		typeList.add(LotteryType.TJKL10);
		typeList.add(LotteryType.HLJSSC);
		typeList.add(LotteryType.CQSSC);
		typeList.add(LotteryType.AHK3);
		typeList.add(LotteryType.JSK3);
		typeList.add(LotteryType.GXK3);
		typeList.add(LotteryType.HLJKL10);
		typeList.add(LotteryType.GXKL10);
		typeList.add(LotteryType.TJSSC);
		typeList.add(LotteryType.JX_11X5);
		typeList.add(LotteryType.SD_11X5);
		typeList.add(LotteryType.SD_KLPK3);
		typeList.add(LotteryType.XJ_11X5);
		typeList.add(LotteryType.CQKL10);
		typeList.add(LotteryType.GDKL10);
		typeList.add(LotteryType.XJSSC);
		typeList.add(LotteryType.JXK3);
		typeList.add(LotteryType.GD_11X5);
		typeList.add(LotteryType.HB_11X5);
		typeList.add(LotteryType.HUBEI_11X5);
		return typeList;
	}

	public static List<Integer> getGaoPinValue() {
		List<Integer> typeList = new ArrayList<Integer>();
		for(LotteryType lotteryType:getGaoPin()){
			typeList.add(lotteryType.getValue());
		}
		return typeList;
	}

	public static List<LotteryType> getJclq() {
		List<LotteryType> typeList = new ArrayList<LotteryType>();
		typeList.add(LotteryType.JCLQ_DXF);
		typeList.add(LotteryType.JCLQ_HHGG);
		typeList.add(LotteryType.JCLQ_RFSF);
		typeList.add(LotteryType.JCLQ_SF);
		typeList.add(LotteryType.JCLQ_SFC);
		return typeList;

	}
	
	

	public static List<LotteryType> getDC() {
		List<LotteryType> typeList = new ArrayList<LotteryType>();
		typeList.add(LotteryType.DC_SPF);
		typeList.add(LotteryType.DC_ZJQ);
		typeList.add(LotteryType.DC_BQC);
		typeList.add(LotteryType.DC_SXDS);
		typeList.add(LotteryType.DC_BF);
		typeList.add(LotteryType.DC_SFGG);
		return typeList;
	}
	

	public static List<Integer> getJclqValue() {
		List<Integer> typeList = new ArrayList<Integer>();
		for(LotteryType lotteryType:getJclq()){
			typeList.add(lotteryType.getValue());
		}
		return typeList;

	}
	
	
	public static List<Integer> getGuanyajunValue() {
		List<Integer> typeList = new ArrayList<Integer>();
		for(LotteryType lotteryType:getGuanyajun()){
			typeList.add(lotteryType.getValue());
		}
		return typeList;

	}

	public static List<LotteryType> getZc() {
		List<LotteryType> typeList = new ArrayList<LotteryType>();
		typeList.add(LotteryType.ZC_SFC);
		typeList.add(LotteryType.ZC_BQC);
		typeList.add(LotteryType.ZC_JQC);
		typeList.add(LotteryType.ZC_RJC);
		return typeList;
	}

	public static List<Integer> getZcValue() {
		List<Integer> typeList = new ArrayList<Integer>();
		for(LotteryType lotteryType:getZc()){
			typeList.add(lotteryType.getValue());
		}
		return typeList;
	}

	public static List<LotteryType> getJczq() {
		List<LotteryType> typeList = new ArrayList<LotteryType>();
		typeList.add(LotteryType.JCZQ_BF);
		typeList.add(LotteryType.JCZQ_BQC);
		typeList.add(LotteryType.JCZQ_HHGG);
		typeList.add(LotteryType.JCZQ_JQS);
		typeList.add(LotteryType.JCZQ_RQ_SPF);
		typeList.add(LotteryType.JCZQ_SPF_WRQ);
		return typeList;
	}

	public static List<Integer> getJczqValue() {
		List<Integer> typeList = new ArrayList<Integer>();
		for(LotteryType lotteryType:getJczq()){
			typeList.add(lotteryType.getValue());
		}
		return typeList;
	}

	public static List<LotteryType> getDc() {
		List<LotteryType> typeList = new ArrayList<LotteryType>();
		typeList.add(LotteryType.DC_SPF);
		typeList.add(LotteryType.DC_ZJQ);
		typeList.add(LotteryType.DC_BQC);
		typeList.add(LotteryType.DC_SXDS);
		typeList.add(LotteryType.DC_BF);
		typeList.add(LotteryType.DC_SFGG);
		return typeList;
	}
	
	
	public static List<LotteryType> getGuanyajun() {
		List<LotteryType> typeList = new ArrayList<LotteryType>();
		typeList.add(LotteryType.JC_GUANJUN);
		typeList.add(LotteryType.JC_GUANYAJUN);
		return typeList;
	}
	
	
	public static List<LotteryType> getDaPan() {
		List<LotteryType> typeList = new ArrayList<LotteryType>();
		typeList.add(LotteryType.SSQ);
		typeList.add(LotteryType.F3D);
		typeList.add(LotteryType.QLC);
		typeList.add(LotteryType.CJDLT);
		typeList.add(LotteryType.PL3);
		typeList.add(LotteryType.PL5);
		typeList.add(LotteryType.QXC);
		return typeList;
	}

	public static List<Integer> getDcValue() {
		List<Integer> typeList = new ArrayList<Integer>();
		for(LotteryType lotteryType:getDc()){
			typeList.add(lotteryType.getValue());
		}
		return typeList;
	}

	public static LotteryType getPhaseType(int value) {
		LotteryType lotteryType = getLotteryType(value);
		if (lotteryType == null)
			return null;
		return getPhaseType(lotteryType);
	}

	public static int getPhaseTypeValue(int value) {
		LotteryType lotteryType = getPhaseType(value);
		if (lotteryType == null)
			return 0;
		return lotteryType.getValue();
	}

	public static LotteryType getPhaseType(LotteryType lotteryType) {
		if (getJclq().contains(lotteryType)) {
			return LotteryType.JCLQ_SF;
		}
		if (getJczq().contains(lotteryType)) {
			return LotteryType.JCZQ_RQ_SPF;
		}
		if (getDc().contains(lotteryType)) {
			if(lotteryType.equals(DC_SFGG)) {
				return LotteryType.DC_SFGG;
			}
			return LotteryType.DC_SPF;
		}
		return lotteryType;
	}
	
	public static LotteryType getSingle(LotteryType lotteryType) {
		if (getJclq().contains(lotteryType)) {
			return LotteryType.JCLQ_SF;
		}
		if (getJczq().contains(lotteryType)) {
			return LotteryType.JCZQ_RQ_SPF;
		}
		if (getDc().contains(lotteryType)) {
			return LotteryType.DC_SPF;
		}
		if (getZc().contains(lotteryType)) {
			return LotteryType.ZC_SFC;
		}
		return lotteryType;
	}
	public static int getSingleValue(int value) {
		LotteryType lotteryType = getSingle(get(value));
		if (lotteryType == null)
			return 0;
		return lotteryType.getValue();
	}
	
	
	public static List<Integer>  getLotteryTypeList(int phaseType){
		List<Integer> phaseList=new  ArrayList<Integer>();
		if(getDcValue().contains(phaseType)){
			phaseList.addAll(getDcValue());
		}else if(getZcValue().contains(phaseType)){
			phaseList.addAll(getZcValue());
		}else if(getJczqValue().contains(phaseType)){
			phaseList.addAll(getJczqValue());
		}else if(getJclqValue().contains(phaseType)){
			phaseList.addAll(getJclqValue());
		}else{
			phaseList.add(phaseType);
		}
		return phaseList;
	}

	public static List<Integer> getAllLotteryType(){
		List<Integer> list=new ArrayList<Integer>();
		for (LotteryType lotteryType:values()){
			if (lotteryType==LotteryType.ALL){
				continue;
			}
			list.add(lotteryType.value);
		}
		return list;
	}
    public static List<Integer> getValues(){
        List<Integer> list=new ArrayList<Integer>();
        for (LotteryType lotteryType:values()){
            list.add(lotteryType.value);
        }
        return list;
    }

	public static List<LotteryType> getJc(){
		List<LotteryType> list=new ArrayList<LotteryType>();
		list.addAll(getJclq());
		list.addAll(getJczq());

		return list;
	}
	public static List<Integer> getJcValue(){
		List<Integer> list=new ArrayList<Integer>();
		list.addAll(getJclqValue());
		list.addAll(getJczqValue());
		return list;
	}

}
