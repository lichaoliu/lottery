/**
 * 
 */
package com.lottery.common.contains;



public class LotteryConstant {
	
	

	/**
	 * 大小单双投注中的小
	 */
	public static String SMALL = "1";
	/**
	 * 大小单双投注中的大
	 */
	public static String LARGE = "2";
	/**
	 * 大小单双投注中的单
	 */
	public static String ODD = "5";
	/**
	 * 大小单双投注中的双
	 */
	public static String EVEN = "4";
	/**
	 * 竞彩单关
	 * */
	public static String DAN_GUAN="01";
	/**
	 * 竞彩过关
	 * */
	public static String GUO_GUAN="02";
	/**
	 * 足球比赛特殊字符,如单场则为比赛取消
	 */
	public static String MATCH_SPECIAL_CHARACTER = "*";
	//足彩
	public static final String ZC_SFC_S_VALUE = "3";
	public static final String ZC_SFC_F_VALUE = "0";
	public static final String ZC_SFC_P_VALUE = "1";
	public static final String ZC_SFR9_S_VALUE = "3";
	public static final String ZC_SFR9_F_VALUE = "0";
	public static final String ZC_SFR9_P_VALUE = "1";
    
    public static final String ZC_JQC_3_VALUE = "3";//进球数为3+个
	public static final String ZC_JQC_0_VALUE = "0";//进球数为0个
	public static final String ZC_JQC_1_VALUE = "1";//进球数为1个
	public static final String ZC_JQC_2_VALUE = "2";//进球数为2个

	public static final String ZC_BQC_S_VALUE = "3";
	public static final String ZC_BQC_F_VALUE = "0";
	public static final String ZC_BQC_P_VALUE = "1";
	
	//单场胜负平
	public static final String DC_SFP_S_VALUE = "3";
	public static final String DC_SFP_F_VALUE = "0";
	public static final String DC_SFP_P_VALUE = "1";
	
	//单场上下盘单双
	public static final String DC_SXDS_SD_VALUE = "1"; 
	public static final String DC_SXDS_SS_VALUE = "2"; 
	public static final String DC_SXDS_XD_VALUE = "3"; 
	public static final String DC_SXDS_XS_VALUE = "4"; 
	
	//单场总进球数
	public static final String DC_JQX_0_VALUE = "1"; 
	public static final String DC_JQX_1_VALUE = "2"; 
	public static final String DC_JQX_2_VALUE = "3"; 
	public static final String DC_JQX_3_VALUE = "4"; 
	public static final String DC_JQX_4_VALUE = "5"; 
	public static final String DC_JQX_5_VALUE = "6"; 
	public static final String DC_JQX_6_VALUE = "7"; 
	public static final String DC_JQX_7_VALUE = "8"; 
	
	//单场比分
	public static final String DC_BF_S_Ohter_VALUE = "1"; //胜其它
	public static final String DC_BF_S_10_VALUE = "2"; //1:0
	public static final String DC_BF_S_20_VALUE = "3"; //2:0
	public static final String DC_BF_S_21_VALUE = "4"; //2:1
	public static final String DC_BF_S_30_VALUE = "5"; //3:0
	public static final String DC_BF_S_31_VALUE = "6"; //3:1
	public static final String DC_BF_S_32_VALUE = "7"; //3:2
	public static final String DC_BF_S_40_VALUE = "8"; //4:0
	public static final String DC_BF_S_41_VALUE = "9"; //4;1
	public static final String DC_BF_S_42_VALUE = "10"; //4:2

	public static final String DC_BF_P_Other_VALUE = "11"; //平其它
	public static final String DC_BF_P_0_VALUE = "12"; //0:0
	public static final String DC_BF_P_1_VALUE = "13"; //1:1
	public static final String DC_BF_P_2_VALUE = "14"; //2:2
	public static final String DC_BF_P_3_VALUE = "15"; //3:3
	
	public static final String DC_BF_F_Other_VALUE = "16"; //负其它
	public static final String DC_BF_F_01_VALUE = "17"; //0:1
	public static final String DC_BF_F_02_VALUE = "18"; //0:2
	public static final String DC_BF_F_12_VALUE = "19"; //1:2
	public static final String DC_BF_F_03_VALUE = "20"; //0:3
	public static final String DC_BF_F_13_VALUE = "21"; //1:3
	public static final String DC_BF_F_23_VALUE = "22"; //2:3
	public static final String DC_BF_F_04_VALUE = "23"; //0:4
	public static final String DC_BF_F_14_VALUE = "24"; //1:4
	public static final String DC_BF_F_24_VALUE = "25"; //2:4
	
	
	//单场半场胜负平
	public static final String DC_BCSFP_SS_VALUE = "1"; 
	public static final String DC_BCSFP_SP_VALUE = "2"; 
	public static final String DC_BCSFP_SF_VALUE = "3"; 
	public static final String DC_BCSFP_PS_VALUE = "4"; 
	public static final String DC_BCSFP_PP_VALUE = "5"; 
	public static final String DC_BCSFP_PF_VALUE = "6"; 
	public static final String DC_BCSFP_FS_VALUE = "7"; 
	public static final String DC_BCSFP_FP_VALUE = "8"; 
	public static final String DC_BCSFP_FF_VALUE = "9"; 
	
	
	//单场半全场
	public static final String DC_BQC_SS_VALUE = "1"; 
	public static final String DC_BQC_SP_VALUE = "3"; 
	public static final String DC_BQC_SF_VALUE = "5"; 
	public static final String DC_BQC_PS_VALUE = "7"; 
	public static final String DC_BQC_PP_VALUE = "9"; 
	public static final String DC_BQC_PF_VALUE = "11"; 
	public static final String DC_BQC_FS_VALUE = "13"; 
	public static final String DC_BQC_FP_VALUE = "15"; 
	public static final String DC_BQC_FF_VALUE = "17"; 
	
	//单场胜负过关
	public static final String DC_SFGG_S_VALUE = "3";
	public static final String DC_SFGG_F_VALUE = "0";
	
	// 竞彩篮球胜负
	public static final String JCLQ_SF_S_VALUE = "3";	// 主胜
	public static final String JCLQ_SF_F_VALUE = "0";	// 主负
	public static final String JCLQ_SF_S_NAME = "胜负-主胜";	// 主胜
	public static final String JCLQ_SF_F_NAME = "胜负-主负";	// 主负
	
	// 竞彩篮球让分胜负
	public static final String JCLQ_RFSF_S_VALUE = "3";	// 主胜
	public static final String JCLQ_RFSF_F_VALUE = "0";	// 主负
	public static final String JCLQ_RFSF_S_NAME = "让分胜负-主胜";	// 主胜
	public static final String JCLQ_RFSF_F_NAME = "让分胜负-主负";	// 主负
	public static final String JCLQ_RFSF_HANDICAP = "handicap";	// 让分
	
	// 竞彩篮球胜分差
	public static final String JCLQ_SFC_H_1_5_VALUE = "1";	// 主胜1-5分
	public static final String JCLQ_SFC_H_6_10_VALUE = "2";	// 主胜6-10分
	public static final String JCLQ_SFC_H_11_15_VALUE = "3";	// 主胜11-15分
	public static final String JCLQ_SFC_H_16_20_VALUE = "4";	// 主胜16-20分
	public static final String JCLQ_SFC_H_21_25_VALUE = "5";	// 主胜21-25分
	public static final String JCLQ_SFC_H_26_PLUS_VALUE = "6";	// 主胜26+分
	public static final String JCLQ_SFC_A_1_5_VALUE = "7";	// 客胜1-5分
	public static final String JCLQ_SFC_A_6_10_VALUE = "8";	// 客胜6-10分
	public static final String JCLQ_SFC_A_11_15_VALUE = "9";	// 客胜11-15分
	public static final String JCLQ_SFC_A_16_20_VALUE = "10";	// 客胜16-20分
	public static final String JCLQ_SFC_A_21_25_VALUE = "11";	// 客胜21-25分
	public static final String JCLQ_SFC_A_26_PLUS_VALUE = "12";	// 客胜26+分
	public static final String JCLQ_SFC_H_1_5_NAME = "胜分差-主胜1-5分";	// 主胜1-5分
	public static final String JCLQ_SFC_H_6_10_NAME = "胜分差-主胜6-10分";	// 主胜6-10分
	public static final String JCLQ_SFC_H_11_15_NAME = "胜分差-主胜11-15分";	// 主胜11-15分
	public static final String JCLQ_SFC_H_16_20_NAME = "胜分差-主胜16-20分";	// 主胜16-20分
	public static final String JCLQ_SFC_H_21_25_NAME = "胜分差-主胜21-25分";	// 主胜21-25分
	public static final String JCLQ_SFC_H_26_PLUS_NAME = "胜分差-主胜26+分";	// 主胜26+分
	public static final String JCLQ_SFC_A_1_5_NAME = "胜分差-客胜1-5分";	// 客胜1-5分
	public static final String JCLQ_SFC_A_6_10_NAME = "胜分差-客胜6-10分";	// 客胜6-10分
	public static final String JCLQ_SFC_A_11_15_NAME = "胜分差-客胜11-15分";	// 客胜11-15分
	public static final String JCLQ_SFC_A_16_20_NAME = "胜分差-客胜16-20分";	// 客胜16-20分
	public static final String JCLQ_SFC_A_21_25_NAME = "胜分差-客胜21-25分";	// 客胜21-25分
	public static final String JCLQ_SFC_A_26_PLUS_NAME = "胜分差-客胜26+分";	// 客胜26+分
	
	// 竞彩篮球大小分
	public static final String JCLQ_DXF_SMALL = "1";		// 小分
	public static final String JCLQ_DXF_LARGE = "2";		// 大分
	public static final String JCLQ_DXF_SMALL_NAME = "大小分-小分";	// 小分
	public static final String JCLQ_DXF_LARGE_NAME = "大小分-大分";	// 大分
	public static final String JCLQ_DXF_PRESETSCORE = "preset_score";		// 预设总分
	
	//竞彩足球让球胜平负
	public static final String JCZQ_SPF_S_VALUE = "3";//让球胜平负-主队胜
	public static final String JCZQ_SPF_P_VALUE = "1";//让球胜平负-主队平
	public static final String JCZQ_SPF_F_VALUE = "0";//让球胜平负-主队负"
	public static final String JCZQ_SPF_S_NAME = "让球胜平负-主队胜";//让球胜平负-主队胜
	public static final String JCZQ_SPF_P_NAME = "让球胜平负-主队平";//让球胜平负-主队平
	public static final String JCZQ_SPF_F_NAME = "让球胜平负-主队负";//让球胜平负-主队负"
	//public static final String JCZQ_SPF_RQ_VALUE = "handicap";//让球胜平负-主队让球数
	
	//竞彩足球胜平负
	public static final String JCZQ_SPF_WRQ_S_VALUE = "3";//胜平负-主队胜
	public static final String JCZQ_SPF_WRQ_P_VALUE = "1";//胜平负-主队平
	public static final String JCZQ_SPF_WRQ_F_VALUE = "0";//胜平负-主队负
	public static final String JCZQ_SPF_WRQ_S_NAME = "胜平负-主队胜";//胜平负-主队胜
	public static final String JCZQ_SPF_WRQ_P_NAME = "胜平负-主队平";//胜平负-主队平
	public static final String JCZQ_SPF_WRQ_F_NAME = "胜平负-主队负";//胜平负-主队负
	
	//竞彩足球全场比分
	public static final String JCZQ_BF_ZS_1_0_VALUE = "1";//全场比分-主胜-1:0
	public static final String JCZQ_BF_ZS_2_0_VALUE = "2";//全场比分-主胜-2:0
	public static final String JCZQ_BF_ZS_2_1_VALUE = "3";//全场比分-主胜-2:1
	public static final String JCZQ_BF_ZS_3_0_VALUE = "4";//全场比分-主胜-3:0
	public static final String JCZQ_BF_ZS_3_1_VALUE = "5";//全场比分-主胜-3:1
	public static final String JCZQ_BF_ZS_3_2_VALUE = "6";//全场比分-主胜-3:2
	public static final String JCZQ_BF_ZS_4_0_VALUE = "7";//全场比分-主胜-4:0
	public static final String JCZQ_BF_ZS_4_1_VALUE = "8";//全场比分-主胜-4:1
	public static final String JCZQ_BF_ZS_4_2_VALUE = "9";//全场比分-主胜-4:2
	public static final String JCZQ_BF_ZS_5_0_VALUE = "10";//全场比分-主胜-5:0
	public static final String JCZQ_BF_ZS_5_1_VALUE = "11";//全场比分-主胜-5:1
	public static final String JCZQ_BF_ZS_5_2_VALUE = "12";//全场比分-主胜-5:2
	public static final String JCZQ_BF_ZS_QT_VALUE = "13";//全场比分-主胜-胜其他
	public static final String JCZQ_BF_ZS_1_0_NAME = "全场比分-主胜-1:0";//全场比分-主胜-1:0
	public static final String JCZQ_BF_ZS_2_0_NAME = "全场比分-主胜-2:0";//全场比分-主胜-2:0
	public static final String JCZQ_BF_ZS_2_1_NAME = "全场比分-主胜-2:1";//全场比分-主胜-2:1
	public static final String JCZQ_BF_ZS_3_0_NAME = "全场比分-主胜-3:0";//全场比分-主胜-3:0
	public static final String JCZQ_BF_ZS_3_1_NAME = "全场比分-主胜-3:1";//全场比分-主胜-3:1
	public static final String JCZQ_BF_ZS_3_2_NAME = "全场比分-主胜-3:2";//全场比分-主胜-3:2
	public static final String JCZQ_BF_ZS_4_0_NAME = "全场比分-主胜-4:0";//全场比分-主胜-4:0
	public static final String JCZQ_BF_ZS_4_1_NAME = "全场比分-主胜-4:1";//全场比分-主胜-4:1
	public static final String JCZQ_BF_ZS_4_2_NAME = "全场比分-主胜-4:2";//全场比分-主胜-4:2
	public static final String JCZQ_BF_ZS_5_0_NAME = "全场比分-主胜-5:0";//全场比分-主胜-5:0
	public static final String JCZQ_BF_ZS_5_1_NAME = "全场比分-主胜-5:1";//全场比分-主胜-5:1
	public static final String JCZQ_BF_ZS_5_2_NAME = "全场比分-主胜-5:2";//全场比分-主胜-5:2
	public static final String JCZQ_BF_ZS_QT_NAME = "全场比分-主胜-胜其他";//全场比分-主胜-胜其他
	
	public static final String JCZQ_BF_ZP_0_0_VALUE = "14";//全场比分-主平-0:0
	public static final String JCZQ_BF_ZP_1_1_VALUE = "15";//全场比分-主平-1:1
	public static final String JCZQ_BF_ZP_2_2_VALUE = "16";//全场比分-主平-2:2
	public static final String JCZQ_BF_ZP_3_3_VALUE = "17";//全场比分-主平-3:3
	public static final String JCZQ_BF_ZP_QT_VALUE = "18";//全场比分-主平-平其他
	public static final String JCZQ_BF_ZP_0_0_NAME = "全场比分-主平-0:0";//全场比分-主平-0:0
	public static final String JCZQ_BF_ZP_1_1_NAME = "全场比分-主平-1:1";//全场比分-主平-1:1
	public static final String JCZQ_BF_ZP_2_2_NAME = "全场比分-主平-2:2";//全场比分-主平-2:2
	public static final String JCZQ_BF_ZP_3_3_NAME = "全场比分-主平-3:3";//全场比分-主平-3:3
	public static final String JCZQ_BF_ZP_QT_NAME = "全场比分-主平-平其他";//全场比分-主平-平其他
	
	public static final String JCZQ_BF_ZF_0_1_VALUE = "19";//全场比分-主负-0:1
	public static final String JCZQ_BF_ZF_0_2_VALUE = "20";//全场比分-主负-0:2
	public static final String JCZQ_BF_ZF_1_2_VALUE = "21";//全场比分-主负-1:2
	public static final String JCZQ_BF_ZF_0_3_VALUE = "22";//全场比分-主负-0:3
	public static final String JCZQ_BF_ZF_1_3_VALUE = "23";//全场比分-主负-1:3
	public static final String JCZQ_BF_ZF_2_3_VALUE = "24";//全场比分-主负-2:3
	public static final String JCZQ_BF_ZF_0_4_VALUE = "25";//全场比分-主负-0:4
	public static final String JCZQ_BF_ZF_1_4_VALUE = "26";//全场比分-主负-1:4
	public static final String JCZQ_BF_ZF_2_4_VALUE = "27";//全场比分-主负-2:4
	public static final String JCZQ_BF_ZF_0_5_VALUE = "28";//全场比分-主负-0:5
	public static final String JCZQ_BF_ZF_1_5_VALUE = "29";//全场比分-主负-1:5
	public static final String JCZQ_BF_ZF_2_5_VALUE = "30";//全场比分-主负-2:5
	public static final String JCZQ_BF_ZF_QT_VALUE = "31";//全场比分-主负-负其他
	public static final String JCZQ_BF_ZF_0_1_NAME = "全场比分-主负-0:1";//全场比分-主负-0:1
	public static final String JCZQ_BF_ZF_0_2_NAME = "全场比分-主负-0:2";//全场比分-主负-0:2
	public static final String JCZQ_BF_ZF_1_2_NAME = "全场比分-主负-1:2";//全场比分-主负-1:2
	public static final String JCZQ_BF_ZF_0_3_NAME = "全场比分-主负-0:3";//全场比分-主负-0:3
	public static final String JCZQ_BF_ZF_1_3_NAME = "全场比分-主负-1:3";//全场比分-主负-1:3
	public static final String JCZQ_BF_ZF_2_3_NAME = "全场比分-主负-2:3";//全场比分-主负-2:3
	public static final String JCZQ_BF_ZF_0_4_NAME = "全场比分-主负-0:4";//全场比分-主负-0:4
	public static final String JCZQ_BF_ZF_1_4_NAME = "全场比分-主负-1:4";//全场比分-主负-1:4
	public static final String JCZQ_BF_ZF_2_4_NAME = "全场比分-主负-2:4";//全场比分-主负-2:4
	public static final String JCZQ_BF_ZF_0_5_NAME = "全场比分-主负-0:5";//全场比分-主负-0:5
	public static final String JCZQ_BF_ZF_1_5_NAME = "全场比分-主负-1:5";//全场比分-主负-1:5
	public static final String JCZQ_BF_ZF_2_5_NAME = "全场比分-主负-2:5";//全场比分-主负-2:5
	public static final String JCZQ_BF_ZF_QT_NAME = "全场比分-主负-负其他";//全场比分-主负-负其他
	
	//竞彩足球总进球数
	public static final String JCZQ_JQS_0_VALUE = "0";//总进球数-0
	public static final String JCZQ_JQS_1_VALUE = "1";//总进球数-1
	public static final String JCZQ_JQS_2_VALUE = "2";//总进球数-2
	public static final String JCZQ_JQS_3_VALUE = "3";//总进球数-3
	public static final String JCZQ_JQS_4_VALUE = "4";//总进球数-4
	public static final String JCZQ_JQS_5_VALUE = "5";//总进球数-5
	public static final String JCZQ_JQS_6_VALUE = "6";//总进球数-6
	public static final String JCZQ_JQS_7_VALUE = "7";//总进球数-7+
	public static final String JCZQ_JQS_0_NAME = "总进球数-0";//总进球数-0
	public static final String JCZQ_JQS_1_NAME = "总进球数-1";//总进球数-1
	public static final String JCZQ_JQS_2_NAME = "总进球数-2";//总进球数-2
	public static final String JCZQ_JQS_3_NAME = "总进球数-3";//总进球数-3
	public static final String JCZQ_JQS_4_NAME = "总进球数-4";//总进球数-4
	public static final String JCZQ_JQS_5_NAME = "总进球数-5";//总进球数-5
	public static final String JCZQ_JQS_6_NAME = "总进球数-6";//总进球数-6
	public static final String JCZQ_JQS_7_NAME = "总进球数-7+";//总进球数-7+
	
	//竞彩足球半场胜平负
	public static final String JCZQ_BQC_SS_VALUE = "1";//半全场胜平负-胜胜
	public static final String JCZQ_BQC_SP_VALUE = "2";//半全场胜平负-胜平
	public static final String JCZQ_BQC_SF_VALUE = "3";//半全场胜平负-胜负
	public static final String JCZQ_BQC_PS_VALUE = "4";//半全场胜平负-平胜
	public static final String JCZQ_BQC_PP_VALUE = "5";//半全场胜平负-平平
	public static final String JCZQ_BQC_PF_VALUE = "6";//半全场胜平负-平负
	public static final String JCZQ_BQC_FS_VALUE = "7";//半全场胜平负-负胜
	public static final String JCZQ_BQC_FP_VALUE = "8";//半全场胜平负-负平
	public static final String JCZQ_BQC_FF_VALUE = "9";//半全场胜平负-负负
	public static final String JCZQ_BQC_SS_NAME = "半全场胜平负-胜胜";//半全场胜平负-胜胜
	public static final String JCZQ_BQC_SP_NAME = "半全场胜平负-胜平";//半全场胜平负-胜平
	public static final String JCZQ_BQC_SF_NAME = "半全场胜平负-胜负";//半全场胜平负-胜负
	public static final String JCZQ_BQC_PS_NAME = "半全场胜平负-平胜";//半全场胜平负-平胜
	public static final String JCZQ_BQC_PP_NAME = "半全场胜平负-平平";//半全场胜平负-平平
	public static final String JCZQ_BQC_PF_NAME = "半全场胜平负-平负";//半全场胜平负-平负
	public static final String JCZQ_BQC_FS_NAME = "半全场胜平负-负胜";//半全场胜平负-负胜
	public static final String JCZQ_BQC_FP_NAME = "半全场胜平负-负平";//半全场胜平负-负平
	public static final String JCZQ_BQC_FF_NAME = "半全场胜平负-负负";//半全场胜平负-负负
	
	//竞彩足球冠军
	public static final String JCZQ_GJ_VALUE = "1";
	public static final String JCZQ_GJ_NAME = "猜冠军";
	//竞彩足球冠亚军
	public static final String JCZQ_GYJ_VALUE = "1";
	public static final String JCZQ_GYJ_NAME = "猜冠亚军";
	
	public static final String JCLQ_MATCH_NUM_CODE_DEFAULT = "00";  //比赛编码中间预留
	public static final String JCZQ_MATCH_NUM_CODE_DEFAULT = "01";  //比赛编码中间预留
	
	//吉林快3
	public static final String JLK3_3TH_TX_VALUE = "777";
	public static final String JLK3_3TH_TX_NAME = "三同号通选";
	
	public static final String JLK3_3TH_111_VALUE = "111";//三同号单选
	public static final String JLK3_3TH_222_VALUE = "222";//三同号单选
	public static final String JLK3_3TH_333_VALUE = "333";//三同号单选
	public static final String JLK3_3TH_444_VALUE = "444";//三同号单选
	public static final String JLK3_3TH_555_VALUE = "555";//三同号单选
	public static final String JLK3_3TH_666_VALUE = "666";//三同号单选
	
	public static final String JLK3_2TH_11_VALUE = "11";//二同号单选
	public static final String JLK3_2TH_22_VALUE = "22";//二同号单选
	public static final String JLK3_2TH_33_VALUE = "33";//二同号单选
	public static final String JLK3_2TH_44_VALUE = "44";//二同号单选
	public static final String JLK3_2TH_55_VALUE = "55";//二同号单选
	public static final String JLK3_2TH_66_VALUE = "66";//二同号单选
	
	public static final String JLK3_3LH_TX_VALUE = "789";
	public static final String JLK3_3LH_TX_NAME = "三连号通选";
	
	//广西快3
	public static final String GXK3_3TH_TX_VALUE = "777";
	public static final String GXK3_3TH_TX_NAME = "三同号通选";
	
	public static final String GXK3_3TH_111_VALUE = "111";//三同号单选
	public static final String GXK3_3TH_222_VALUE = "222";//三同号单选
	public static final String GXK3_3TH_333_VALUE = "333";//三同号单选
	public static final String GXK3_3TH_444_VALUE = "444";//三同号单选
	public static final String GXK3_3TH_555_VALUE = "555";//三同号单选
	public static final String GXK3_3TH_666_VALUE = "666";//三同号单选
	
	public static final String GXK3_2TH_11_VALUE = "11";//二同号单选
	public static final String GXK3_2TH_22_VALUE = "22";//二同号单选
	public static final String GXK3_2TH_33_VALUE = "33";//二同号单选
	public static final String GXK3_2TH_44_VALUE = "44";//二同号单选
	public static final String GXK3_2TH_55_VALUE = "55";//二同号单选
	public static final String GXK3_2TH_66_VALUE = "66";//二同号单选
	
	public static final String GXK3_3LH_TX_VALUE = "789";
	public static final String GXK3_3LH_TX_NAME = "三连号通选";
	
	//湖南幸运赛车
	public static final String HNXYSC_LARGE_ODD_VALUE = "25";//大奇
	public static final String HNXYSC_LARGE_EVEN_VALUE = "24";//大偶
	public static final String HNXYSC_SMALL_ODD_VALUE = "15";//小奇
	public static final String HNXYSC_SMALL_EVEN_VALUE = "14";//小偶
	
	public static final String HNXYSC_LARGE_ODD_NAME = "大奇";//大奇
	public static final String HNXYSC_LARGE_EVEN_NAME = "大偶";//大偶
	public static final String HNXYSC_SMALL_ODD_NAME = "小奇";//小奇
	public static final String HNXYSC_SMALL_EVEN_NAME = "小偶";//小偶
	
	public static final String HNXYSC_RED_VALUE = "1";//红
	public static final String HNXYSC_BLUE_VALUE = "2";//蓝
	public static final String HNXYSC_GREEN_VALUE = "3";//绿
	public static final String HNXYSC_YELLOW_VALUE = "4";//黄
	public static final String HNXYSC_SILVER_VALUE = "5";//银
	public static final String HNXYSC_PURPLE_VALUE = "6";//紫
	
	public static final String HNXYSC_RED_NAME = "红";//红
	public static final String HNXYSC_BLUE_NAME = "蓝";//蓝
	public static final String HNXYSC_GREEN_NAME = "绿";//绿
	public static final String HNXYSC_YELLOW_NAME = "黄";//黄
	public static final String HNXYSC_SILVER_NAME = "银";//银
	public static final String HNXYSC_PURPLE_NAME = "紫";//紫
	
	//江苏快3
	public static final String JSK3_3TH_TX_VALUE = "777";
	public static final String JSK3_3TH_TX_NAME = "三同号通选";
	
	public static final String JSK3_3TH_111_VALUE = "111";//三同号单选
	public static final String JSK3_3TH_222_VALUE = "222";//三同号单选
	public static final String JSK3_3TH_333_VALUE = "333";//三同号单选
	public static final String JSK3_3TH_444_VALUE = "444";//三同号单选
	public static final String JSK3_3TH_555_VALUE = "555";//三同号单选
	public static final String JSK3_3TH_666_VALUE = "666";//三同号单选
	
	public static final String JSK3_2TH_11_VALUE = "11";//二同号单选
	public static final String JSK3_2TH_22_VALUE = "22";//二同号单选
	public static final String JSK3_2TH_33_VALUE = "33";//二同号单选
	public static final String JSK3_2TH_44_VALUE = "44";//二同号单选
	public static final String JSK3_2TH_55_VALUE = "55";//二同号单选
	public static final String JSK3_2TH_66_VALUE = "66";//二同号单选
	
	public static final String JSK3_3LH_TX_VALUE = "789";
	public static final String JSK3_3LH_TX_NAME = "三连号通选";
	
	//安徽快3
	public static final String AHK3_3TH_TX_VALUE = "777";
	public static final String AHK3_3TH_TX_NAME = "三同号通选";
	
	public static final String AHK3_3TH_111_VALUE = "111";//三同号单选
	public static final String AHK3_3TH_222_VALUE = "222";//三同号单选
	public static final String AHK3_3TH_333_VALUE = "333";//三同号单选
	public static final String AHK3_3TH_444_VALUE = "444";//三同号单选
	public static final String AHK3_3TH_555_VALUE = "555";//三同号单选
	public static final String AHK3_3TH_666_VALUE = "666";//三同号单选
	
	public static final String AHK3_2TH_11_VALUE = "11";//二同号单选
	public static final String AHK3_2TH_22_VALUE = "22";//二同号单选
	public static final String AHK3_2TH_33_VALUE = "33";//二同号单选
	public static final String AHK3_2TH_44_VALUE = "44";//二同号单选
	public static final String AHK3_2TH_55_VALUE = "55";//二同号单选
	public static final String AHK3_2TH_66_VALUE = "66";//二同号单选
	
	public static final String AHK3_3LH_TX_VALUE = "789";
	public static final String AHK3_3LH_TX_NAME = "三连号通选";

	public static final String WINCODE_URL="http://101.201.197.13/number/index";//抓取开奖号码URL
	public static final String CURRENT_RACE_URL="http://101.200.196.69/jingcaidata/query/getCurrentRaces";//获取当前赛程URL
	public static final String JCZQ_RACE_RESULT="http://101.201.197.13/jc/jczq";//竞彩足球赛果
	public static final String JCLQ_RACE_RESULT="http://101.201.197.13/jc/jclq";//竞彩篮球赛果
	public static final String ZC_MATCH_URL="http://101.201.197.13/zc/duizhen/";//足彩对阵
	public static final String ZC_PHASE_URL="http://101.201.197.13/zc/presale/";//足彩新期
	public static final String ZC_RESULT_URL="http://101.201.197.13/zc/jiang/";//足彩开奖
	public static final String JC_SP_URL="http://101.200.196.69/jingcaidata/query/getCurrentSps";//竞彩sp抓取URL




   
	
}
