package com.lottery.common.contains.lottery;

import java.util.ArrayList;
import java.util.List;

/***
 * 奖级
 * */
public enum LotteryDrawPrizeAwarder {
	
	NOT_WIN("", "未中奖奖级",LotteryType.ALL,false), 
	
	SSQ_1("1", "双色球一等奖",LotteryType.SSQ,false), 
	SSQ_2("2", "双色球二等奖",LotteryType.SSQ,false), 
	SSQ_3("3", "双色球三等奖",LotteryType.SSQ,true), 
	SSQ_4("4", "双色球四等奖",LotteryType.SSQ,true), 
	SSQ_5("5", "双色球五等奖",LotteryType.SSQ,true), 
	SSQ_6("6", "双色球六等奖",LotteryType.SSQ,true), 
	SSQ_6_ADD("6-ADD", "双色球六等奖复式加奖",LotteryType.SSQ,true), 
	SSQ_2A("2A", "双色球二等奖幸运蓝球加奖",LotteryType.SSQ,false), 
	
	
	D3_1("1","3D直选",LotteryType.F3D,true),
	D3_2("2","3D组三",LotteryType.F3D,true),
	D3_3("3","3D组六",LotteryType.F3D,true),
	D3_1D("1D","1D",LotteryType.F3D,true),
	D3_2D("2D","2D",LotteryType.F3D,true),
	D3_3T1("3T1","三星通选中三",LotteryType.F3D,true),
	D3_3T2("3T2","三星通选中二",LotteryType.F3D,true),
	D3_HS0("HS0","和数0\27",LotteryType.F3D,true),
	D3_HS1("HS1","和数1\26",LotteryType.F3D,true),
	D3_HS2("HS2","和数2\25",LotteryType.F3D,true),
	D3_HS3("HS3","和数3\24",LotteryType.F3D,true),
	D3_HS4("HS4","和数4\23",LotteryType.F3D,true),
	D3_HS5("HS5","和数5\22",LotteryType.F3D,true),
	D3_HS6("HS6","和数6\21",LotteryType.F3D,true),
	D3_HS7("HS7","和数7\20",LotteryType.F3D,true),
	D3_HS8("HS8","和数8\19",LotteryType.F3D,true),
	D3_HS9("HS9","和数9\18",LotteryType.F3D,true),
	D3_HS10("HS10","和数10\17",LotteryType.F3D,true),
	D3_HS11("HS11","和数11\16",LotteryType.F3D,true),
	D3_HS12("HS12","和数12\15",LotteryType.F3D,true),
	D3_HS13("HS13","和数13\14",LotteryType.F3D,true),
	D3_DX("DX","猜大小",LotteryType.F3D,true),
	D3_JO("JO","猜奇偶",LotteryType.F3D,true),
	D3_ST("ST","猜三同",LotteryType.F3D,true),
	D3_TLJ("TLJ","拖拉机",LotteryType.F3D,true),
	D3_C1D1("C1D1","猜1D中1",LotteryType.F3D,true),
	D3_C1D2("C1D2","猜1D中2",LotteryType.F3D,true),
	D3_C1D3("C1D3","猜1D中3",LotteryType.F3D,true),
	D3_C2D_ET("C2D1","猜2D二同",LotteryType.F3D,true),
	D3_C2D_EBT("C2D2","猜2D二不同",LotteryType.F3D,true),
	
	
	QLC_1("1", "七乐彩一等奖",LotteryType.QLC,false), 
	QLC_2("2", "七乐彩二等奖",LotteryType.QLC,false), 
	QLC_3("3", "七乐彩三等奖",LotteryType.QLC,false), 
	QLC_4("4", "七乐彩四等奖",LotteryType.QLC,true), 
	QLC_5("5", "七乐彩五等奖",LotteryType.QLC,true), 
	QLC_6("6", "七乐彩六等奖",LotteryType.QLC,true),
	QLC_7("7", "七乐彩七等奖",LotteryType.QLC,true),
	
	
	P3_1("1","排列三直选",LotteryType.PL3,true),
	P3_2("2","排列三组三",LotteryType.PL3,true),
	P3_3("3","排列三组六",LotteryType.PL3,true),
	
	
	P5_1("1","排列五一等奖",LotteryType.PL5,true),
	
	
	QXC_1("1", "七星彩一等奖",LotteryType.QXC,false), 
	QXC_2("2", "七星彩二等奖",LotteryType.QXC,false), 
	QXC_3("3", "七星彩三等奖",LotteryType.QXC,true), 
	QXC_4("4", "七星彩四等奖",LotteryType.QXC,true), 
	QXC_5("5", "七星彩五等奖",LotteryType.QXC,true), 
	QXC_6("6", "七星彩六等奖",LotteryType.QXC,true), 
	
	
	DLT_1A("1A", "大乐透一等奖",LotteryType.CJDLT,false), 
	DLT_1B("1B", "大乐透一等奖追加",LotteryType.CJDLT,false), 
	DLT_2A("2A", "大乐透二等奖",LotteryType.CJDLT,false), 
	DLT_2B("2B", "大乐透二等奖追加",LotteryType.CJDLT,false), 
	DLT_3A("3A", "大乐透三等奖",LotteryType.CJDLT,false), 
	DLT_3B("3B", "大乐透三等奖追加",LotteryType.CJDLT,false), 
	DLT_4A("4A", "大乐透四等奖",LotteryType.CJDLT,true), 
	DLT_4B("4B", "大乐透四等奖追加",LotteryType.CJDLT,true), 
	DLT_5A("5A", "大乐透五等奖",LotteryType.CJDLT,true), 
	DLT_5B("5B", "大乐透五等奖追加",LotteryType.CJDLT,true), 
//	DLT_6("6", "大乐透六等奖",LotteryType.CJDLT,true),
	DLT_6A("6A", "大乐透六等奖",LotteryType.CJDLT,true),
	DLT_6B("6B", "大乐透六等奖追加",LotteryType.CJDLT,true),
	
	
	CQSSC_1D("1D","重庆时时彩一星",LotteryType.CQSSC,true),
	CQSSC_2D("2D","重庆时时彩二星",LotteryType.CQSSC,true),
	CQSSC_3D("3D","重庆时时彩三星",LotteryType.CQSSC,true),
	CQSSC_5D("5D","重庆时时彩五星",LotteryType.CQSSC,true),
	CQSSC_Z3("Z3","重庆时时彩组三",LotteryType.CQSSC,true),
	CQSSC_Z6("Z6","重庆时时彩组六",LotteryType.CQSSC,true),
	CQSSC_H2("H2","重庆时时彩二星直选和值",LotteryType.CQSSC,true),
	CQSSC_H3("H3","重庆时时彩三星直选和值",LotteryType.CQSSC,true),
	CQSSC_2Z("2Z","重庆时时彩二星组选",LotteryType.CQSSC,true),
	CQSSC_DD("DD","重庆时时彩大小单双",LotteryType.CQSSC,true),
	CQSSC_5T5("5T5","重庆时时彩五星通选中五20000",LotteryType.CQSSC,true),
	CQSSC_5T2("5T2","重庆时时彩五星通选中二20",LotteryType.CQSSC,true),
	CQSSC_5T3("5T3","重庆时时彩五星通选中二200",LotteryType.CQSSC,true),
	
	
	XJSSC_1D("1D","新疆时时彩一星",LotteryType.XJSSC,true),
	XJSSC_2D1("2D1","新疆时时彩二星一等奖",LotteryType.XJSSC,true),
	XJSSC_2D2("2D2","新疆时时彩二星二等奖",LotteryType.XJSSC,true),
	XJSSC_3D("3D","新疆时时彩三星",LotteryType.XJSSC,true),
	XJSSC_4DALL("4DALL","新疆时时彩四星中4",LotteryType.XJSSC,true),
//	XJSSC_4D1("4D1","新疆时时彩四星一等奖",LotteryType.XJSSC,true),
	XJSSC_4D2("4D2","新疆时时彩四星二等奖",LotteryType.XJSSC,true),
	XJSSC_5D("5D","新疆时时彩五星",LotteryType.XJSSC,true),
	XJSSC_Z3("Z3","新疆时时彩组三",LotteryType.XJSSC,true),
	XJSSC_Z6("Z6","新疆时时彩组六",LotteryType.XJSSC,true),
	XJSSC_2Z("2Z","新疆时时彩二星组选",LotteryType.XJSSC,true),
	XJSSC_DD("DD","新疆时时彩大小单双",LotteryType.XJSSC,true),
	XJSSC_5T5("5T5","新疆时时彩五星通选中五",LotteryType.XJSSC,true),
	XJSSC_5T2("5T2","新疆时时彩五星通选中二20",LotteryType.XJSSC,true),
	XJSSC_5T3("5T3","新疆时时彩五星通选中二200",LotteryType.XJSSC,true),
	
	XJSSC_QW1("QW1","新疆时时彩趣味二星一等奖",LotteryType.XJSSC,true),
	XJSSC_QW2("QW2","新疆时时彩趣味二星二等奖",LotteryType.XJSSC,true),
	XJSSC_QW3("QW3","新疆时时彩趣味二星三等奖",LotteryType.XJSSC,true),
	
	XJSSC_QJ1("QJ1","新疆时时彩区间二星一等奖",LotteryType.XJSSC,true),
	XJSSC_QJ2("QJ2","新疆时时彩区间二星二等奖",LotteryType.XJSSC,true),
	
	XJSSC_R21("R21","新疆时时彩任选二一等奖",LotteryType.XJSSC,true),
	XJSSC_R22("R22","新疆时时彩任选二二等奖",LotteryType.XJSSC,true),
	
	XJSSC_R31("R31","新疆时时彩任选三一等奖",LotteryType.XJSSC,true),
	XJSSC_R32("R32","新疆时时彩任选三二等奖",LotteryType.XJSSC,true),
	
	
	JXSSC_1D("1D","江西时时彩一星",LotteryType.JXSSC,true),
	JXSSC_2D("2D","江西时时彩二星",LotteryType.JXSSC,true),
	JXSSC_3D("3D","江西时时彩三星",LotteryType.JXSSC,true),
	JXSSC_4D("4D","江西时时彩四星",LotteryType.JXSSC,true),
	JXSSC_5D("5D","江西时时彩五星",LotteryType.JXSSC,true),
	JXSSC_Z3("Z3","江西时时彩组三",LotteryType.JXSSC,true),
	JXSSC_Z6("Z6","江西时时彩组六",LotteryType.JXSSC,true),
	JXSSC_H2("H2","江西时时彩二星直选和值",LotteryType.JXSSC,true),
	JXSSC_H3("H3","江西时时彩三星直选和值",LotteryType.JXSSC,true),
	JXSSC_2Z("2Z","江西时时彩二星组选",LotteryType.JXSSC,true),
	JXSSC_DD("DD","江西时时彩大小单双",LotteryType.JXSSC,true),
	JXSSC_5T5("5T5","江西时时彩五星通选中五20460",LotteryType.JXSSC,true),
	JXSSC_5T2("5T2","江西时时彩五星通选中二30",LotteryType.JXSSC,true),
	JXSSC_5T3("5T3","江西时时彩五星通选中二200",LotteryType.JXSSC,true),
	JXSSC_1R("1R","江西时时彩任选一",LotteryType.JXSSC,true),
	JXSSC_2R("2R","江西时时彩任选二",LotteryType.JXSSC,true),
	
	
	AHK3_ET_DAN("2","安徽快三二同号单式",LotteryType.AHK3,true),
	AHK3_ET_FU("6","安徽快三二同号复选",LotteryType.AHK3,true),
	AHK3_EBT_DAN("10","安徽快三二不同单式",LotteryType.AHK3,true),
	AHK3_ST_DAN("1","安徽快三三同号单式",LotteryType.AHK3,true),
	AHK3_ST_TONG("3","安徽快三三同号通选",LotteryType.AHK3,true),
	AHK3_SBT("4","安徽快三三不同单式",LotteryType.AHK3,true),
	AHK3_SLH_TONG("8","安徽快三三连号通选",LotteryType.AHK3,true),
	AHK3_HZ_H2("h2","安徽快三和值4\17",LotteryType.AHK3,true),
	AHK3_HZ_H3("h3","安徽快三和值5\16",LotteryType.AHK3,true),
	AHK3_HZ_H4("h4","安徽快三和值6\15",LotteryType.AHK3,true),
	AHK3_HZ_H5("h5","安徽快三和值7\14",LotteryType.AHK3,true),
	AHK3_HZ_H7("h7","安徽快三和值8\13",LotteryType.AHK3,true),
	AHK3_HZ_H8("h8","安徽快三和值9\12",LotteryType.AHK3,true),
	AHK3_HZ_H9("h9","安徽快三和值10\11",LotteryType.AHK3,true),
	
	
	GXK3_ET_DAN("2","广西快三二同号单式",LotteryType.GXK3,true),
	GXK3_ET_FU("6","广西快三二同号复选",LotteryType.GXK3,true),
	GXK3_EBT_DAN("10","广西快三二不同单式",LotteryType.GXK3,true),
	GXK3_ST_DAN("1","广西快三三同号单式",LotteryType.GXK3,true),
	GXK3_ST_TONG("3","广西快三三同号通选",LotteryType.GXK3,true),
	GXK3_SBT("4","广西快三三不同单式",LotteryType.GXK3,true),
	GXK3_SLH_TONG("8","广西快三三连号通选",LotteryType.GXK3,true),
	GXK3_HZ_H2("h2","广西快三和值4\17",LotteryType.GXK3,true),
	GXK3_HZ_H3("h3","广西快三和值5\16",LotteryType.GXK3,true),
	GXK3_HZ_H4("h4","广西快三和值6\15",LotteryType.GXK3,true),
	GXK3_HZ_H5("h5","广西快三和值7\14",LotteryType.GXK3,true),
	GXK3_HZ_H7("h7","广西快三和值8\13",LotteryType.GXK3,true),
	GXK3_HZ_H8("h8","广西快三和值9\12",LotteryType.GXK3,true),
	GXK3_HZ_H9("h9","广西快三和值10\11",LotteryType.GXK3,true),
	
	
	JSK3_ET_DAN("2","江苏快三二同号单式",LotteryType.JSK3,true),
	JSK3_ET_FU("6","江苏快三二同号复选",LotteryType.JSK3,true),
	JSK3_EBT_DAN("10","江苏快三二不同单式",LotteryType.JSK3,true),
	JSK3_ST_DAN("1","江苏快三三同号单式",LotteryType.JSK3,true),
	JSK3_ST_TONG("3","江苏快三三同号通选",LotteryType.JSK3,true),
	JSK3_SBT("4","江苏快三三不同单式",LotteryType.JSK3,true),
	JSK3_SLH_TONG("8","江苏快三三连号通选",LotteryType.JSK3,true),
	JSK3_HZ_H2("h2","江苏快三和值4\17",LotteryType.JSK3,true),
	JSK3_HZ_H3("h3","江苏快三和值5\16",LotteryType.JSK3,true),
	JSK3_HZ_H4("h4","江苏快三和值6\15",LotteryType.JSK3,true),
	JSK3_HZ_H5("h5","江苏快三和值7\14",LotteryType.JSK3,true),
	JSK3_HZ_H7("h7","江苏快三和值8\13",LotteryType.JSK3,true),
	JSK3_HZ_H8("h8","江苏快三和值9\12",LotteryType.JSK3,true),
	JSK3_HZ_H9("h9","江苏快三和值10\11",LotteryType.JSK3,true),
	
	
	
	JXK3_ET_DAN("2","江西快三二同号单式",LotteryType.JXK3,true),
	JXK3_ET_FU("6","江西快三二同号复选",LotteryType.JXK3,true),
	JXK3_EBT_DAN("10","江西快三二不同单式",LotteryType.JXK3,true),
	JXK3_ST_DAN("1","江西快三三同号单式",LotteryType.JXK3,true),
	JXK3_ST_TONG("3","江西快三三同号通选",LotteryType.JXK3,true),
	JXK3_SBT("4","江西快三三不同单式",LotteryType.JXK3,true),
	JXK3_SLH_TONG("8","江西快三三连号通选",LotteryType.JXK3,true),
	JXK3_HZ_H2("h2","江西快三和值4\17",LotteryType.JXK3,true),
	JXK3_HZ_H3("h3","江西快三和值5\16",LotteryType.JXK3,true),
	JXK3_HZ_H4("h4","江西快三和值6\15",LotteryType.JXK3,true),
	JXK3_HZ_H5("h5","江西快三和值7\14",LotteryType.JXK3,true),
	JXK3_HZ_H7("h7","江西快三和值8\13",LotteryType.JXK3,true),
	JXK3_HZ_H8("h8","江西快三和值9\12",LotteryType.JXK3,true),
	JXK3_HZ_H9("h9","江西快三和值10\11",LotteryType.JXK3,true),
	
	NXK3_ET_DAN("2","宁夏快三二同号单式",LotteryType.NXK3,true),
	NXK3_ET_FU("6","宁夏快三二同号复选",LotteryType.NXK3,true),
	NXK3_EBT_DAN("10","宁夏快三二不同单式",LotteryType.NXK3,true),
	NXK3_ST_DAN("1","宁夏快三三同号单式",LotteryType.NXK3,true),
	NXK3_ST_TONG("3","宁夏快三三同号通选",LotteryType.NXK3,true),
	NXK3_SBT("4","宁夏快三三不同单式",LotteryType.NXK3,true),
	NXK3_SLH_TONG("8","宁夏快三三连号通选",LotteryType.NXK3,true),
	NXK3_HZ_H2("h2","宁夏快三和值4\17",LotteryType.NXK3,true),
	NXK3_HZ_H3("h3","宁夏快三和值5\16",LotteryType.NXK3,true),
	NXK3_HZ_H4("h4","宁夏快三和值6\15",LotteryType.NXK3,true),
	NXK3_HZ_H5("h5","宁夏快三和值7\14",LotteryType.NXK3,true),
	NXK3_HZ_H7("h7","宁夏快三和值8\13",LotteryType.NXK3,true),
	NXK3_HZ_H8("h8","宁夏快三和值9\12",LotteryType.NXK3,true),
	NXK3_HZ_H9("h9","宁夏快三和值10\11",LotteryType.NXK3,true),
	
	
	JX11X5_R2("R2","江西11选5任选2",LotteryType.JX_11X5,true),
	JX11X5_R3("R3","江西11选5任选3",LotteryType.JX_11X5,true),
	JX11X5_R3_SADD("R3-SADD","江西11选5任选3单式加奖",LotteryType.JX_11X5,true),
	JX11X5_R3_MADD("R3-MADD","江西11选5任选3复式胆拖加奖",LotteryType.JX_11X5,true),
	JX11X5_R4("R4","江西11选5任选4",LotteryType.JX_11X5,true),
	JX11X5_R5("R5","江西11选5任选5",LotteryType.JX_11X5,true),
	JX11X5_R5_SADD("R5-SADD","江西11选5任选5单式加奖",LotteryType.JX_11X5,true),
	JX11X5_R5_MADD("R5-MADD","江西11选5任选5复式胆拖加奖",LotteryType.JX_11X5,true),
	JX11X5_R6("R6","江西11选5任选6",LotteryType.JX_11X5,true),
	JX11X5_R7("R7","江西11选5任选7",LotteryType.JX_11X5,true),
	JX11X5_R7_SADD("R7-SADD","江西11选5任选7单式加奖",LotteryType.JX_11X5,true),
	JX11X5_R7_MADD("R7-MADD","江西11选5任选7复式胆拖加奖",LotteryType.JX_11X5,true),
	JX11X5_R8("R8","江西11选5任选8",LotteryType.JX_11X5,true),
	JX11X5_Q1("Q1","江西11选5前一",LotteryType.JX_11X5,true),
	JX11X5_Q2("Q2","江西11选5前二",LotteryType.JX_11X5,true),
	JX11X5_Q3("Q3","江西11选5前三",LotteryType.JX_11X5,true),
	JX11X5_Z2("Z2","江西11选5组二",LotteryType.JX_11X5,true),
	JX11X5_Z3("Z3","江西11选5组三",LotteryType.JX_11X5,true),
	JX11X5_L31("L31","江西11选5乐选三按位前三",LotteryType.JX_11X5,true),
	JX11X5_L32("L32","江西11选5乐选三不按前三",LotteryType.JX_11X5,true),
	JX11X5_L33("L33","江西11选5乐选三任意三",LotteryType.JX_11X5,true),
	JX11X5_L41("L41","江西11选5乐选四任意四",LotteryType.JX_11X5,true),
	JX11X5_L42("L42","江西11选5乐选四任意三",LotteryType.JX_11X5,true),
	JX11X5_L51("L51","江西11选5乐选五中五",LotteryType.JX_11X5,true),
	JX11X5_L52("L52","江西11选5乐选五中四",LotteryType.JX_11X5,true),
	
	
	SD11X5_R2("R2","山东11选5任选2",LotteryType.SD_11X5,true),
	SD11X5_R3("R3","山东11选5任选3",LotteryType.SD_11X5,true),
	SD11X5_R3_ADD("R3-ADD","山东11选5任选3复式胆拖加奖",LotteryType.SD_11X5,true),
	SD11X5_R4("R4","山东11选5任选4",LotteryType.SD_11X5,true),
	SD11X5_R5("R5","山东11选5任选5",LotteryType.SD_11X5,true),
	SD11X5_R5_ADD("R5-ADD","山东11选5任选5三胆全托",LotteryType.SD_11X5,true),
	SD11X5_R6("R6","山东11选5任选6",LotteryType.SD_11X5,true),
	SD11X5_R7("R7","山东11选5任选7",LotteryType.SD_11X5,true),
	SD11X5_R8("R8","山东11选5任选8",LotteryType.SD_11X5,true),
	SD11X5_Q1("Q1","山东11选5前一",LotteryType.SD_11X5,true),
	SD11X5_Q2("Q2","山东11选5前二",LotteryType.SD_11X5,true),
	SD11X5_Q3("Q3","山东11选5前三",LotteryType.SD_11X5,true),
	SD11X5_Z2("Z2","山东11选5组二",LotteryType.SD_11X5,true),
	SD11X5_Z3("Z3","山东11选5组三",LotteryType.SD_11X5,true),
	SD11X5_L31("L31","山东11选5乐选三按位前三",LotteryType.SD_11X5,true),
	SD11X5_L32("L32","山东11选5乐选三不按前三",LotteryType.SD_11X5,true),
	SD11X5_L33("L33","山东11选5乐选三任意三",LotteryType.SD_11X5,true),
	SD11X5_L41("L41","山东11选5乐选四任意四",LotteryType.SD_11X5,true),
	SD11X5_L42("L42","山东11选5乐选四任意三",LotteryType.SD_11X5,true),
	SD11X5_L51("L51","山东11选5乐选五中五",LotteryType.SD_11X5,true),
	SD11X5_L52("L52","山东11选5乐选五中四",LotteryType.SD_11X5,true),
	
	
	
	HUBEI11X5_R2("R2","湖北11选5任选2",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_R3("R3","湖北11选5任选3",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_R4("R4","湖北11选5任选4",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_R5("R5","湖北11选5任选5",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_R6("R6","湖北11选5任选6",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_R7("R7","湖北11选5任选7",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_R8("R8","湖北11选5任选8",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_Q1("Q1","湖北11选5前一",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_Q2("Q2","湖北11选5前二",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_Q3("Q3","湖北11选5前三",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_Z2("Z2","湖北11选5组二",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_Z3("Z3","湖北11选5组三",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_R3_ADD("R3-ADD","湖北11选5任选3胆拖加奖",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_L31("L31","湖北11选5乐选三按位前三",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_L32("L32","湖北11选5乐选三不按前三",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_L33("L33","湖北11选5乐选三任意三",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_L41("L41","湖北11选5乐选四任意四",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_L42("L42","湖北11选5乐选四任意三",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_L51("L51","湖北11选5乐选五中五",LotteryType.HUBEI_11X5,true),
	HUBEI11X5_L52("L52","湖北11选5乐选五中四",LotteryType.HUBEI_11X5,true),
	
	
	
	GD11X5_R2("R2","广东11选5任选2",LotteryType.GD_11X5,true),
	GD11X5_R3("R3","广东11选5任选3",LotteryType.GD_11X5,true),
//	GD11X5_R3_ADD("R3-ADD","广东11选5任选3加奖",LotteryType.GD_11X5,true),
	GD11X5_R3_ADD("R3-ADD","广东11选5任选3二胆全托加奖",LotteryType.GD_11X5,true),
	GD11X5_R4("R4","广东11选5任选4",LotteryType.GD_11X5,true),
	GD11X5_R5("R5","广东11选5任选5",LotteryType.GD_11X5,true),
	GD11X5_R6("R6","广东11选5任选6",LotteryType.GD_11X5,true),
	GD11X5_R7("R7","广东11选5任选7",LotteryType.GD_11X5,true),
	GD11X5_R8("R8","广东11选5任选8",LotteryType.GD_11X5,true),
	GD11X5_Q1("Q1","广东11选5前一",LotteryType.GD_11X5,true),
	GD11X5_Q2("Q2","广东11选5前二",LotteryType.GD_11X5,true),
	GD11X5_Q3("Q3","广东11选5前三",LotteryType.GD_11X5,true),
	GD11X5_Z2("Z2","广东11选5组二",LotteryType.GD_11X5,true),
	GD11X5_Z3("Z3","广东11选5组三",LotteryType.GD_11X5,true),
	GD11X5_L31("L31","广东11选5乐选三按位前三",LotteryType.GD_11X5,true),
	GD11X5_L32("L32","广东11选5乐选三不按前三",LotteryType.GD_11X5,true),
	GD11X5_L33("L33","广东11选5乐选三任意三",LotteryType.GD_11X5,true),
	GD11X5_L41("L41","广东11选5乐选四任意四",LotteryType.GD_11X5,true),
	GD11X5_L42("L42","广东11选5乐选四任意三",LotteryType.GD_11X5,true),
	GD11X5_L51("L51","广东11选5乐选五中五",LotteryType.GD_11X5,true),
	GD11X5_L52("L52","广东11选5乐选五中四",LotteryType.GD_11X5,true),
	
	
	HB11X5_R2("R2","河北11选5任选2",LotteryType.HB_11X5,true),
	HB11X5_R3("R3","河北11选5任选3",LotteryType.HB_11X5,true),
	HB11X5_R4("R4","河北11选5任选4",LotteryType.HB_11X5,true),
	HB11X5_R5("R5","河北11选5任选5",LotteryType.HB_11X5,true),
	HB11X5_R6("R6","河北11选5任选6",LotteryType.HB_11X5,true),
	HB11X5_R7("R7","河北11选5任选7",LotteryType.HB_11X5,true),
	HB11X5_R8("R8","河北11选5任选8",LotteryType.HB_11X5,true),
	HB11X5_Q1("Q1","河北11选5前一",LotteryType.HB_11X5,true),
	HB11X5_Q2("Q2","河北11选5前二",LotteryType.HB_11X5,true),
	HB11X5_Q3("Q3","河北11选5前三",LotteryType.HB_11X5,true),
	HB11X5_Z2("Z2","河北11选5组二",LotteryType.HB_11X5,true),
	HB11X5_Z3("Z3","河北11选5组三",LotteryType.HB_11X5,true),
	HB11X5_L31("L31","河北11选5乐选三按位前三",LotteryType.HB_11X5,true),
	HB11X5_L32("L32","河北11选5乐选三不按前三",LotteryType.HB_11X5,true),
	HB11X5_L33("L33","河北11选5乐选三任意三",LotteryType.HB_11X5,true),
	HB11X5_L41("L41","河北11选5乐选四任意四",LotteryType.HB_11X5,true),
	HB11X5_L42("L42","河北11选5乐选四任意三",LotteryType.HB_11X5,true),
	HB11X5_L51("L51","河北11选5乐选五中五",LotteryType.HB_11X5,true),
	HB11X5_L52("L52","河北11选5乐选五中四",LotteryType.HB_11X5,true),
	
	
	XJ11X5_R2("R2","新疆11选5任选2",LotteryType.XJ_11X5,true),
	XJ11X5_R3("R3","新疆11选5任选3",LotteryType.XJ_11X5,true),
	XJ11X5_R4("R4","新疆11选5任选4",LotteryType.XJ_11X5,true),
	XJ11X5_R5("R5","新疆11选5任选5",LotteryType.XJ_11X5,true),
	XJ11X5_R6("R6","新疆11选5任选6",LotteryType.XJ_11X5,true),
	XJ11X5_R7("R7","新疆11选5任选7",LotteryType.XJ_11X5,true),
	XJ11X5_R8("R8","新疆11选5任选8",LotteryType.XJ_11X5,true),
	XJ11X5_Q1("Q1","新疆11选5前一",LotteryType.XJ_11X5,true),
	XJ11X5_Q2("Q2","新疆11选5前二",LotteryType.XJ_11X5,true),
	XJ11X5_Q3("Q3","新疆11选5前三",LotteryType.XJ_11X5,true),
	XJ11X5_Z2("Z2","新疆11选5组二",LotteryType.XJ_11X5,true),
	XJ11X5_Z3("Z3","新疆11选5组三",LotteryType.XJ_11X5,true),
	XJ11X5_L31("L31","新疆11选5乐选三按位前三",LotteryType.XJ_11X5,true),
	XJ11X5_L32("L32","新疆11选5乐选三不按前三",LotteryType.XJ_11X5,true),
	XJ11X5_L33("L33","新疆11选5乐选三任意三",LotteryType.XJ_11X5,true),
	XJ11X5_L41("L41","新疆11选5乐选四任意四",LotteryType.XJ_11X5,true),
	XJ11X5_L42("L42","新疆11选5乐选四任意三",LotteryType.XJ_11X5,true),
	XJ11X5_L51("L51","新疆11选5乐选五中五",LotteryType.XJ_11X5,true),
	XJ11X5_L52("L52","新疆11选5乐选五中四",LotteryType.XJ_11X5,true),
	
	
	
	TJSSC_1D("1D","天津时时彩一星",LotteryType.TJSSC,true),
	TJSSC_2D("2D","天津时时彩二星",LotteryType.TJSSC,true),
	TJSSC_3D("3D","天津时时彩三星",LotteryType.TJSSC,true),
	TJSSC_4D("4D","天津时时彩四星",LotteryType.TJSSC,true),
	TJSSC_5D("5D","天津时时彩五星",LotteryType.TJSSC,true),
	TJSSC_Z3("Z3","天津时时彩组三",LotteryType.TJSSC,true),
	TJSSC_Z6("Z6","天津时时彩组六",LotteryType.TJSSC,true),
	TJSSC_2Z("2Z","天津时时彩二星组选",LotteryType.TJSSC,true),
	TJSSC_DD("DD","天津时时彩大小单双",LotteryType.TJSSC,true),
	TJSSC_5T5("5T5","天津时时彩五星通选中五",LotteryType.TJSSC,true),
	TJSSC_5T2("5T2","天津时时彩五星通选中二",LotteryType.TJSSC,true),
	TJSSC_5T3("5T3","天津时时彩五星通选中三",LotteryType.TJSSC,true),
	TJSSC_QW1("QW1","天津时时彩趣味二星中1",LotteryType.TJSSC,true),
	TJSSC_QW2("QW2","天津时时彩趣味二星中2",LotteryType.TJSSC,true),
	TJSSC_QJ("QJ","天津时时彩区间二星",LotteryType.TJSSC,true),
	TJSSC_1R("1R","天津时时彩任选1",LotteryType.TJSSC,true),
	TJSSC_2R("2R","天津时时彩任选2",LotteryType.TJSSC,true),
	TJSSC_3R("3R","天津时时彩任选3",LotteryType.TJSSC,true),
	
	
	HLJK10_S1("S1","黑龙江快乐十分数投",LotteryType.HLJKL10,true),
	HLJK10_H1("H1","黑龙江快乐十分红投",LotteryType.HLJKL10,true),
	HLJK10_Q2("Q2","黑龙江快乐十分前二",LotteryType.HLJKL10,true),
	HLJK10_Q3("Q3","黑龙江快乐十分前三",LotteryType.HLJKL10,true),
	HLJK10_Z2("Z2","黑龙江快乐十分组二",LotteryType.HLJKL10,true),
	HLJK10_Z3("Z3","黑龙江快乐十分组三",LotteryType.HLJKL10,true),
	HLJK10_R2("R2","黑龙江快乐十分任二",LotteryType.HLJKL10,true),
	HLJK10_R3("R3","黑龙江快乐十分任三",LotteryType.HLJKL10,true),
	HLJK10_R4("R4","黑龙江快乐十分任四",LotteryType.HLJKL10,true),
	HLJK10_R5("R5","黑龙江快乐十分任五",LotteryType.HLJKL10,true),
	
	
	TJK10_S1("S1","天津快乐十分数投",LotteryType.TJKL10,true),
	TJK10_H1("H1","天津快乐十分红投",LotteryType.TJKL10,true),
	TJK10_Q2("Q2","天津快乐十分前二",LotteryType.TJKL10,true),
	TJK10_Q3("Q3","天津快乐十分前三",LotteryType.TJKL10,true),
	TJK10_Z2("Z2","天津快乐十分组二",LotteryType.TJKL10,true),
	TJK10_Z3("Z3","天津快乐十分组三",LotteryType.TJKL10,true),
	TJK10_R2("R2","天津快乐十分任二",LotteryType.TJKL10,true),
	TJK10_R3("R3","天津快乐十分任三",LotteryType.TJKL10,true),
	TJK10_R4("R4","天津快乐十分任四",LotteryType.TJKL10,true),
	TJK10_R5("R5","天津快乐十分任五",LotteryType.TJKL10,true),
	
	
	CQK10_S1("S1","重庆快乐十分数投",LotteryType.CQKL10,true),
	CQK10_H1("H1","重庆快乐十分红投",LotteryType.CQKL10,true),
	CQK10_Q2("Q2","重庆快乐十分前二",LotteryType.CQKL10,true),
	CQK10_Q3("Q3","重庆快乐十分前三",LotteryType.CQKL10,true),
	CQK10_Z2("Z2","重庆快乐十分组二",LotteryType.CQKL10,true),
	CQK10_Z3("Z3","重庆快乐十分组三",LotteryType.CQKL10,true),
	CQK10_R2("R2","重庆快乐十分任二",LotteryType.CQKL10,true),
	CQK10_R3("R3","重庆快乐十分任三",LotteryType.CQKL10,true),
	CQK10_R4("R4","重庆快乐十分任四",LotteryType.CQKL10,true),
	CQK10_R5("R5","重庆快乐十分任五",LotteryType.CQKL10,true),
	
	
	GDK10_S1("S1","广东快乐十分数投",LotteryType.GDKL10,true),
	GDK10_H1("H1","广东快乐十分红投",LotteryType.GDKL10,true),
	GDK10_Q2("Q2","广东快乐十分前二",LotteryType.GDKL10,true),
	GDK10_Q3("Q3","广东快乐十分前三",LotteryType.GDKL10,true),
	GDK10_Z2("Z2","广东快乐十分组二",LotteryType.GDKL10,true),
	GDK10_Z3("Z3","广东快乐十分组三",LotteryType.GDKL10,true),
	GDK10_R2("R2","广东快乐十分任二",LotteryType.GDKL10,true),
	GDK10_R3("R3","广东快乐十分任三",LotteryType.GDKL10,true),
	GDK10_R4("R4","广东快乐十分任四",LotteryType.GDKL10,true),
	GDK10_R5("R5","广东快乐十分任五",LotteryType.GDKL10,true),
	
	SDPK3_R1("R1","山东快乐扑克任选一",LotteryType.SD_KLPK3,true),
	SDPK3_R2("R2","山东快乐扑克任选二",LotteryType.SD_KLPK3,true),
	SDPK3_R3("R3","山东快乐扑克任选三",LotteryType.SD_KLPK3,true),
	SDPK3_R4("R4","山东快乐扑克任选四",LotteryType.SD_KLPK3,true),
	SDPK3_R5("R5","山东快乐扑克任选五",LotteryType.SD_KLPK3,true),
	SDPK3_R6("R6","山东快乐扑克任选六",LotteryType.SD_KLPK3,true),
	SDPK3_BX_TH("BX-TH","山东快乐扑克包选同花",LotteryType.SD_KLPK3,true),
	SDPK3_BX_THS("BX-THS","山东快乐扑克包选同花顺",LotteryType.SD_KLPK3,true),
	SDPK3_BX_SZ("BX-SZ","山东快乐扑克包选顺子",LotteryType.SD_KLPK3,true),
	SDPK3_BX_BZ("BX-BZ","山东快乐扑克包选豹子",LotteryType.SD_KLPK3,true),
	SDPK3_BX_DZ("BX-DZ","山东快乐扑克包选对子",LotteryType.SD_KLPK3,true),
	SDPK3_BZ("BZ","山东快乐扑克豹子",LotteryType.SD_KLPK3,true),
	SDPK3_DZ("DZ","山东快乐扑克对子",LotteryType.SD_KLPK3,true),
	SDPK3_SZ("SZ","山东快乐扑克顺子",LotteryType.SD_KLPK3,true),
	SDPK3_TH("TH","山东快乐扑克同花",LotteryType.SD_KLPK3,true),
	SDPK3_THS("THS","山东快乐扑克同花顺",LotteryType.SD_KLPK3,true),
	
	SDPK3_R2_ADD("R2-ADD","山东快乐扑克任选二加奖",LotteryType.SD_KLPK3,true),
	SDPK3_R3_ADD("R3-ADD","山东快乐扑克任选三加奖",LotteryType.SD_KLPK3,true),
	
	
	
	GXKL10_HY1("HY1","好运一",LotteryType.GXKL10,true),
	GXKL10_HY2("HY2","好运二",LotteryType.GXKL10,true),
	GXKL10_HY3("HY3","好运三",LotteryType.GXKL10,true),
	GXKL10_HY4("HY4","好运四",LotteryType.GXKL10,true),
	GXKL10_HY5("HY5","好运五",LotteryType.GXKL10,true),
	GXKL10_HYT("HYT","好运特",LotteryType.GXKL10,true),
	GXKL10_HYTX3_3("THY3-3","好运通选3中3",LotteryType.GXKL10,true),
	GXKL10_HYTX3_2("THY3-2","好运通选3中2",LotteryType.GXKL10,true),
	GXKL10_HYTX4_4("THY4-4","好运通选4中4",LotteryType.GXKL10,true),
	GXKL10_HYTX4_3("THY4-3","好运通选4中3",LotteryType.GXKL10,true),
	GXKL10_HYTX4_2("THY4-2","好运通选4中2",LotteryType.GXKL10,true),
	GXKL10_HYTX5_5("THY5-5","好运通选5中5",LotteryType.GXKL10,true),
	GXKL10_HYTX5_4("THY5-4","好运通选5中4",LotteryType.GXKL10,true),
	GXKL10_HYTX5_3("THY5-3","好运通选5中3",LotteryType.GXKL10,true),
	
	GXKL10_HY3BX4_3("HY3BX4-3","好运三包选4中3",LotteryType.GXKL10,true),
	GXKL10_HY3BX4_4("HY3BX4-4","好运三包选4中4",LotteryType.GXKL10,true),
	GXKL10_HY3BX5_3("HY3BX5-3","好运三包选5中3",LotteryType.GXKL10,true),
	GXKL10_HY3BX5_4("HY3BX5-4","好运三包选5中4",LotteryType.GXKL10,true),
	GXKL10_HY3BX5_5("HY3BX5-5","好运三包选5中5",LotteryType.GXKL10,true),
	GXKL10_HY3BX6_3("HY3BX6-3","好运三包选6中3",LotteryType.GXKL10,true),
	GXKL10_HY3BX6_4("HY3BX6-4","好运三包选6中4",LotteryType.GXKL10,true),
	GXKL10_HY3BX6_5("HY3BX6-5","好运三包选6中5",LotteryType.GXKL10,true),
	
	GXKL10_HY4BX5_4("HY4BX5-4","好运四包选5中4",LotteryType.GXKL10,true),
	GXKL10_HY4BX5_5("HY4BX5-5","好运四包选5中5",LotteryType.GXKL10,true),
	GXKL10_HY4BX6_4("HY4BX6-4","好运四包选6中4",LotteryType.GXKL10,true),
	GXKL10_HY4BX6_5("HY4BX6-5","好运四包选6中5",LotteryType.GXKL10,true),
	GXKL10_HY4BX7_4("HY4BX7-4","好运四包选7中4",LotteryType.GXKL10,true),
	GXKL10_HY4BX7_5("HY4BX7-5","好运四包选7中5",LotteryType.GXKL10,true),
	GXKL10_HY4BX8_4("HY4BX8-4","好运四包选8中4",LotteryType.GXKL10,true),
	GXKL10_HY4BX8_5("HY4BX8-5","好运四包选8中5",LotteryType.GXKL10,true),
	GXKL10_HY4BX9_4("HY4BX9-4","好运四包选9中4",LotteryType.GXKL10,true),
	GXKL10_HY4BX9_5("HY4BX9-5","好运四包选9中5",LotteryType.GXKL10,true),
	GXKL10_HY4BX10_4("HY4BX10-4","好运四包选10中4",LotteryType.GXKL10,true),
	GXKL10_HY4BX10_5("HY4BX10-5","好运四包选10中5",LotteryType.GXKL10,true),
	
	GXKL10_HY5BX6_5("HY5BX6-5","好运五包选6中5",LotteryType.GXKL10,true),
	GXKL10_HY5BX7_5("HY5BX7-5","好运五包选7中5",LotteryType.GXKL10,true),
	GXKL10_HY5BX8_5("HY5BX8-5","好运五包选8中5",LotteryType.GXKL10,true),
	GXKL10_HY5BX9_5("HY5BX9-5","好运五包选9中5",LotteryType.GXKL10,true),
	GXKL10_HY5BX10_5("HY5BX10-5","好运五包选10中5",LotteryType.GXKL10,true),
	
	

	all("0","所有",LotteryType.ALL,false);
	public String value;
	public String name;
	public LotteryType lotteryType;
	public boolean isStatic;
	LotteryDrawPrizeAwarder(String value,String name,LotteryType lotteryType,boolean isStatic){
		this.value = value;
		this.name = name;
		this.lotteryType=lotteryType;
		this.isStatic=isStatic;
	}
	
	public static List<LotteryDrawPrizeAwarder> getByLotteryType(LotteryType lotteryType){
		List<LotteryDrawPrizeAwarder> prizeAwarderList=new ArrayList<LotteryDrawPrizeAwarder>();
		for(LotteryDrawPrizeAwarder drawPrizeAwarder:LotteryDrawPrizeAwarder.values()){
			if(drawPrizeAwarder.lotteryType==lotteryType){
				prizeAwarderList.add(drawPrizeAwarder);
			}
		}
		return prizeAwarderList;
	}
	
	public static LotteryDrawPrizeAwarder get(String value,LotteryType lotteryType){
		for(LotteryDrawPrizeAwarder drawPrizeAwarder:LotteryDrawPrizeAwarder.values()){
			if(drawPrizeAwarder.lotteryType==lotteryType&&drawPrizeAwarder.value.equals(value)){
				return drawPrizeAwarder;
			}
		}
		return null;
	}
}
