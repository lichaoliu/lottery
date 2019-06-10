package com.lottery.ticket.vender.process.huay;

public class Constants_HUAYANG {
	// 出票个人资料
	public static String TOUZHU_USERNAME = "13910774320";
	public static String TOUZHU_CARDNUMBER = "110105197202296116";
	public static String TOUZHU_PHONE = "13910774320";

	// 测试环境
	public static String HUAYANG_BJDC_INTERFACE_URL = "http://124.205.138.76/b2blib/lotteryxml.php";// 124.205.138.74
	public static String HUAYANG_KEY = "56de81b61bdcafe2a8ca12d7e61b8532";
	public static String AGENT_ID = "10000602";
//	 正式环境
//	 public static String HUAYANG_BJDC_INTERFACE_URL =
//	 "http://interlib.198tc.com/b2blib/lotteryxml.php";// 124.205.138.74
//	 public static String HUAYANG_KEY = "859fe415351be71a0d9d2ab8f0de2996";
//	 public static String AGENT_ID = "10000542";

	public static String XML_CODE = "utf-8";

	public static String CZID_BJDC_NAME = "北京单场";
	public static String CZID_BJDC = "bjdc";
	public static String CZID_BJDC_NUM = "45";
	/** * 北京单场单注最低倍数 */
	public static int BJDC_MULTIPLE = 99;

	public static int LOTSEND_MAX_NUM = 99;

	public static int LOTSEND_FANKUAN_SIZE = 90;

	public static int PROJECT_UPDATE_STATUS_SIZE = 30;
	public static int LOTSEND_CHECK_CHUPIAOZHONG_SIZE = 90;
	/** 可销售 */
	public static String SALESSTATUS_1 = "1";
	/** 停止销售 */
	public static String SALESSTATUS_0 = "0";

	/** 北单赛程信息 */
	public static String TRADE_TYPE_13012 = "13012";
	/** 北单赛程信息 无期号查询 */
	public static String TRADE_TYPE_13013 = "13013";
	/** 北单各彩种SP值查询 */
	public static String TRADE_TYPE_13999 = "13999";
	/** 北单查询最后开奖结果以及开奖sp值 */
	public static String TRADE_TYPE_13014 = "13014";
	/** 北单出票接口 */
	public static String TRADE_TYPE_13010 = "13010";
	/** 票状态查询 */
	public static String TRADE_TYPE_13004 = "13004";
	/** 中奖查询 */
	public static String TRADE_TYPE_13011 = "13011";
	/** 彩票落地通知 */
	public static String TRADE_TYPE_13006 = "13006";
	// 北京单场彩种编号
	/** 胜平负 */
	public static String BJDC_PLAY_ID_200 = "200";
	public static String BJDC_PLAY_ID_200_CN = "胜平负";
	/** 上下单双 */
	public static String BJDC_PLAY_ID_201 = "201";
	public static String BJDC_PLAY_ID_201_CN = "上下单双";
	/** 总进球 */
	public static String BJDC_PLAY_ID_202 = "202";
	public static String BJDC_PLAY_ID_202_CN = "总进球";
	/** 比分 */
	public static String BJDC_PLAY_ID_203 = "203";
	public static String BJDC_PLAY_ID_203_CN = "比分";
	/** 半全场 */
	public static String BJDC_PLAY_ID_204 = "204";
	public static String BJDC_PLAY_ID_204_CN = "半全场";

	/**
	 * 
	 华阳对阵信息status值对应含义备注 -1:审核未通过 0：销售中 1：停止销售 2：已开奖，奖金处理中 3：奖金处理完毕 4：取消
	 **/

	public static String[] BJDC_PLAY_ID_ALL = { BJDC_PLAY_ID_200, BJDC_PLAY_ID_201, BJDC_PLAY_ID_202, BJDC_PLAY_ID_203, BJDC_PLAY_ID_204 };

	public static String[] BJDC_COLOR = { "#FD91B5", "#F75000", "#750000", "#004488", "#808080", "#401C15", "#338FCC", "#660033", "#336699", "#DB31EE", "#ACA96C", "#0066FF", "#328955", "#006633", "#5A9400", "#ff6699", "#663333", "#FF1717", "#990099", "#008888", "#666666", "#003C00", "#00CCFF", "#9A00FF", "#CC3300", "#4DB361", "#660000", "#FFAAFF", "#054594", "#146F18", "#3F1C10", "#57A87B", "#FF3333", "#EF5D0E", "#A6A600", "#7F4E27", "#DF6161", "#00C7C7", "#56004f", "#ff2d51" };

	public static String NOTIFY_NUM_0 = "0";
	public static String NOTIFY_STR_0 = "操作成功";
	public static String NOTIFY_NUM_10000 = "10000";
	public static String NOTIFY_STR_10000 = "代理权限不足,不能执行此操作";
	public static String NOTIFY_NUM_10003 = "10003";
	public static String NOTIFY_STR_10003 = "用户名已经存在";
	public static String NOTIFY_NUM_10010 = "10010";
	public static String NOTIFY_STR_10010 = "账户金额不足,无法完成此操作";
	public static String NOTIFY_NUM_10018 = "10018";
	public static String NOTIFY_STR_10018 = "交易通讯超时";
	public static String NOTIFY_NUM_10019 = "10019";
	public static String NOTIFY_STR_10019 = "参数过长";
	public static String NOTIFY_NUM_10032 = "10032";
	public static String NOTIFY_STR_10032 = "交易已存在";
	public static String NOTIFY_NUM_12901 = "12901";
	public static String NOTIFY_STR_12901 = "无效或者不完整XML数据格式";
	public static String NOTIFY_NUM_12902 = "12902";
	public static String NOTIFY_STR_12902 = "要查询的数据还未开奖，请等开奖后再查";
	public static String NOTIFY_NUM_12903 = "12903";
	public static String NOTIFY_STR_12903 = "传入参数信息错误";
	public static String NOTIFY_NUM_12904 = "12904";
	public static String NOTIFY_STR_12904 = "一次提交数据过多";
	public static String NOTIFY_NUM_14000 = "14000";
	public static String NOTIFY_STR_14000 = "系统目前不支持此玩法";
	public static String NOTIFY_NUM_14001 = "14001";
	public static String NOTIFY_STR_14001 = "操作代码格式错误";
	public static String NOTIFY_NUM_14002 = "14002";
	public static String NOTIFY_STR_14002 = "注数计算错误";
	public static String NOTIFY_NUM_14003 = "14003";
	public static String NOTIFY_STR_14003 = "投注金额计算错误";
	public static String NOTIFY_NUM_14004 = "14004";
	public static String NOTIFY_STR_14004 = "玩法已期结，无法完成本次投注";
	public static String NOTIFY_NUM_14005 = "14005";
	public static String NOTIFY_STR_14005 = "一票超过20000元限";
	public static String NOTIFY_NUM_9999 = "9999";
	public static String NOTIFY_STR_9999 = "操作失败";
	// playMethod
	public static String BJDC_PLAY_METHOD_100 = "100";
	public static String BJDC_PLAY_METHOD_101 = "101";
	public static String BJDC_PLAY_METHOD_100_CN = "单关";
	public static String BJDC_PLAY_METHOD_201 = "201";
	public static String BJDC_PLAY_METHOD_201_CN = "2串1";
	public static String BJDC_PLAY_METHOD_301 = "301";
	public static String BJDC_PLAY_METHOD_301_CN = "3串1";
	public static String BJDC_PLAY_METHOD_401 = "401";
	public static String BJDC_PLAY_METHOD_401_CN = "4串1";
	public static String BJDC_PLAY_METHOD_501 = "501";
	public static String BJDC_PLAY_METHOD_501_CN = "5串1";
	public static String BJDC_PLAY_METHOD_601 = "601";
	public static String BJDC_PLAY_METHOD_601_CN = "6串1";
	public static String BJDC_PLAY_METHOD_701 = "701";
	public static String BJDC_PLAY_METHOD_701_CN = "7串1";
	public static String BJDC_PLAY_METHOD_801 = "801";
	public static String BJDC_PLAY_METHOD_801_CN = "8串1";
	public static String BJDC_PLAY_METHOD_901 = "901";
	public static String BJDC_PLAY_METHOD_901_CN = "9串1";
	public static String BJDC_PLAY_METHOD_1001 = "1001";
	public static String BJDC_PLAY_METHOD_1001_CN = "10串1";
	public static String BJDC_PLAY_METHOD_1101 = "1101";
	public static String BJDC_PLAY_METHOD_1101_CN = "11串1";
	public static String BJDC_PLAY_METHOD_1201 = "1201";
	public static String BJDC_PLAY_METHOD_1201_CN = "12串1";
	public static String BJDC_PLAY_METHOD_1301 = "1301";
	public static String BJDC_PLAY_METHOD_1301_CN = "13串1";
	public static String BJDC_PLAY_METHOD_1401 = "1401";
	public static String BJDC_PLAY_METHOD_1401_CN = "14串1";
	public static String BJDC_PLAY_METHOD_1501 = "1501";
	public static String BJDC_PLAY_METHOD_1501_CN = "15串1";
	public static String BJDC_PLAY_METHOD_203 = "203";
	public static String BJDC_PLAY_METHOD_203_CN = "2串3";
	public static String BJDC_PLAY_METHOD_304 = "304";
	public static String BJDC_PLAY_METHOD_304_CN = "3串4";
	public static String BJDC_PLAY_METHOD_307 = "307";
	public static String BJDC_PLAY_METHOD_307_CN = "3串7";
	public static String BJDC_PLAY_METHOD_405 = "405";
	public static String BJDC_PLAY_METHOD_405_CN = "4串5";
	public static String BJDC_PLAY_METHOD_411 = "411";
	public static String BJDC_PLAY_METHOD_411_CN = "4串11";
	public static String BJDC_PLAY_METHOD_415 = "415";
	public static String BJDC_PLAY_METHOD_415_CN = "4串15";
	public static String BJDC_PLAY_METHOD_506 = "506";
	public static String BJDC_PLAY_METHOD_506_CN = "5串6";
	public static String BJDC_PLAY_METHOD_516 = "516";
	public static String BJDC_PLAY_METHOD_516_CN = "5串16";
	public static String BJDC_PLAY_METHOD_526 = "526";
	public static String BJDC_PLAY_METHOD_526_CN = "5串26";
	public static String BJDC_PLAY_METHOD_531 = "531";
	public static String BJDC_PLAY_METHOD_531_CN = "5串31";
	public static String BJDC_PLAY_METHOD_607 = "607";
	public static String BJDC_PLAY_METHOD_607_CN = "6串7";
	public static String BJDC_PLAY_METHOD_622 = "622";
	public static String BJDC_PLAY_METHOD_622_CN = "6串22";
	public static String BJDC_PLAY_METHOD_642 = "642";
	public static String BJDC_PLAY_METHOD_642_CN = "6串42";
	public static String BJDC_PLAY_METHOD_657 = "657";
	public static String BJDC_PLAY_METHOD_657_CN = "6串57";
	public static String BJDC_PLAY_METHOD_663 = "663";
	public static String BJDC_PLAY_METHOD_663_CN = "6串63";

	// playMethod
	public static String BJDC_PLAY_METHOD_HUAYANG_02 = "02";
	public static String BJDC_PLAY_METHOD_HUAYANG_02_CN = "单关";
	public static String BJDC_PLAY_METHOD_HUAYANG_03 = "03";
	public static String BJDC_PLAY_METHOD_HUAYANG_03_CN = "2串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_04 = "04";
	public static String BJDC_PLAY_METHOD_HUAYANG_04_CN = "3串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_05 = "05";
	public static String BJDC_PLAY_METHOD_HUAYANG_05_CN = "4串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_06 = "06";
	public static String BJDC_PLAY_METHOD_HUAYANG_06_CN = "5串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_07 = "07";
	public static String BJDC_PLAY_METHOD_HUAYANG_07_CN = "6串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_08 = "08";
	public static String BJDC_PLAY_METHOD_HUAYANG_08_CN = "7串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_09 = "09";
	public static String BJDC_PLAY_METHOD_HUAYANG_09_CN = "8串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_10 = "10";
	public static String BJDC_PLAY_METHOD_HUAYANG_10_CN = "9串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_11 = "11";
	public static String BJDC_PLAY_METHOD_HUAYANG_11_CN = "10串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_12 = "12";
	public static String BJDC_PLAY_METHOD_HUAYANG_12_CN = "11串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_13 = "13";
	public static String BJDC_PLAY_METHOD_HUAYANG_13_CN = "12串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_14 = "14";
	public static String BJDC_PLAY_METHOD_HUAYANG_14_CN = "13串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_15 = "15";
	public static String BJDC_PLAY_METHOD_HUAYANG_15_CN = "14串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_16 = "16";
	public static String BJDC_PLAY_METHOD_HUAYANG_16_CN = "15串1";
	public static String BJDC_PLAY_METHOD_HUAYANG_17 = "17";
	public static String BJDC_PLAY_METHOD_HUAYANG_17_CN = "2串3";
	public static String BJDC_PLAY_METHOD_HUAYANG_18 = "18";
	public static String BJDC_PLAY_METHOD_HUAYANG_18_CN = "3串4";
	public static String BJDC_PLAY_METHOD_HUAYANG_19 = "19";
	public static String BJDC_PLAY_METHOD_HUAYANG_19_CN = "3串7";
	public static String BJDC_PLAY_METHOD_HUAYANG_20 = "20";
	public static String BJDC_PLAY_METHOD_HUAYANG_20_CN = "4串5";
	public static String BJDC_PLAY_METHOD_HUAYANG_21 = "21";
	public static String BJDC_PLAY_METHOD_HUAYANG_21_CN = "4串11";
	public static String BJDC_PLAY_METHOD_HUAYANG_22 = "22";
	public static String BJDC_PLAY_METHOD_HUAYANG_22_CN = "4串15";
	public static String BJDC_PLAY_METHOD_HUAYANG_23 = "23";
	public static String BJDC_PLAY_METHOD_HUAYANG_23_CN = "5串6";
	public static String BJDC_PLAY_METHOD_HUAYANG_24 = "24";
	public static String BJDC_PLAY_METHOD_HUAYANG_24_CN = "5串16";
	public static String BJDC_PLAY_METHOD_HUAYANG_25 = "25";
	public static String BJDC_PLAY_METHOD_HUAYANG_25_CN = "5串26";
	public static String BJDC_PLAY_METHOD_HUAYANG_26 = "26";
	public static String BJDC_PLAY_METHOD_HUAYANG_26_CN = "5串31";
	public static String BJDC_PLAY_METHOD_HUAYANG_27 = "27";
	public static String BJDC_PLAY_METHOD_HUAYANG_27_CN = "6串7";
	public static String BJDC_PLAY_METHOD_HUAYANG_28 = "28";
	public static String BJDC_PLAY_METHOD_HUAYANG_28_CN = "6串22";
	public static String BJDC_PLAY_METHOD_HUAYANG_29 = "29";
	public static String BJDC_PLAY_METHOD_HUAYANG_29_CN = "6串42";
	public static String BJDC_PLAY_METHOD_HUAYANG_30 = "30";
	public static String BJDC_PLAY_METHOD_HUAYANG_30_CN = "6串57";
	public static String BJDC_PLAY_METHOD_HUAYANG_31 = "31";
	public static String BJDC_PLAY_METHOD_HUAYANG_31_CN = "6串63";

	/** 0：不可出票 */
	public static String RET_STATUS_0 = "0";
	/** 1：可出票状态 */
	public static String RET_STATUS_1 = "1";
	/** 2：出票成功 */
	public static String RET_STATUS_2 = "2";
	/** 3：出票失败(允许再出票） */
	public static String RET_STATUS_3 = "3";
	/** 4：出票中 */
	public static String RET_STATUS_4 = "4";
	/** 5：出票中（体彩中心） */
	public static String RET_STATUS_5 = "5";
	/** 6：出票失败（不允许出票） */
	public static String RET_STATUS_6 = "6";

	/** 0：未开奖 */
	public static String HIT_STATUS_0 = "0";
	/** 1：未中奖 */
	public static String HIT_STATUS_1 = "1";
	/** 2：中奖 */
	public static String HIT_STATUS_2 = "2";
}
