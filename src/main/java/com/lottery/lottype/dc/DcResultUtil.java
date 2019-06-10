package com.lottery.lottype.dc;

import com.lottery.common.contains.LotteryConstant;
import com.lottery.common.contains.lottery.LotteryType;

public class DcResultUtil {

	
	public static String getCalcuteResult(LotteryType type,String result) {
		String calresult = "";
		if(LotteryType.DC_SPF==type) {
			if(LotteryConstant.DC_SFP_S_VALUE.equals(result)) {
				calresult = "3";
			}else if(LotteryConstant.DC_SFP_P_VALUE.equals(result)) {
				calresult = "1";
			}else if(LotteryConstant.DC_SFP_F_VALUE.equals(result)) {
				calresult = "0";
			}
		}else if(LotteryType.DC_ZJQ==type) {
			if(LotteryConstant.DC_JQX_0_VALUE.equals(result)) {
				calresult = "0";
			}else if(LotteryConstant.DC_JQX_1_VALUE.equals(result)) {
				calresult = "1";
			}else if(LotteryConstant.DC_JQX_2_VALUE.equals(result)) {
				calresult = "2";
			}else if(LotteryConstant.DC_JQX_3_VALUE.equals(result)) {
				calresult = "3";
			}else if(LotteryConstant.DC_JQX_4_VALUE.equals(result)) {
				calresult = "4";
			}else if(LotteryConstant.DC_JQX_5_VALUE.equals(result)) {
				calresult = "5";
			}else if(LotteryConstant.DC_JQX_6_VALUE.equals(result)) {
				calresult = "6";
			}else if(LotteryConstant.DC_JQX_7_VALUE.equals(result)) {
				calresult = "7";
			}
		}else if(LotteryType.DC_BQC==type) {
			if(LotteryConstant.DC_BCSFP_SS_VALUE.equals(result)) {
				calresult = "33";
			}else if(LotteryConstant.DC_BCSFP_SP_VALUE.equals(result)) {
				calresult = "31";
			}else if(LotteryConstant.DC_BCSFP_SF_VALUE.equals(result)) {
				calresult = "30";
			}else if(LotteryConstant.DC_BCSFP_PS_VALUE.equals(result)) {
				calresult = "13";
			}else if(LotteryConstant.DC_BCSFP_PP_VALUE.equals(result)) {
				calresult = "11";
			}else if(LotteryConstant.DC_BCSFP_PF_VALUE.equals(result)) {
				calresult = "10";
			}else if(LotteryConstant.DC_BCSFP_FS_VALUE.equals(result)) {
				calresult = "03";
			}else if(LotteryConstant.DC_BCSFP_FP_VALUE.equals(result)) {
				calresult = "01";
			}else if(LotteryConstant.DC_BCSFP_FF_VALUE.equals(result)) {
				calresult = "00";
			}
		}else if(LotteryType.DC_SXDS==type) {
			if(LotteryConstant.DC_SXDS_SD_VALUE.equals(result)) {
				calresult = "1";
			}else if(LotteryConstant.DC_SXDS_SS_VALUE.equals(result)) {
				calresult = "2";
			}else if(LotteryConstant.DC_SXDS_XD_VALUE.equals(result)) {
				calresult = "3";
			}else if(LotteryConstant.DC_SXDS_XS_VALUE.equals(result)) {
				calresult = "4";
			}
		}else if(LotteryType.DC_BF==type) {
			if(LotteryConstant.DC_BF_S_10_VALUE.equals(result)) {
				calresult = "10";
			}else if(LotteryConstant.DC_BF_S_20_VALUE.equals(result)) {
				calresult = "20";
			}else if(LotteryConstant.DC_BF_S_21_VALUE.equals(result)) {
				calresult = "21";
			}else if(LotteryConstant.DC_BF_S_30_VALUE.equals(result)) {
				calresult = "30";
			}else if(LotteryConstant.DC_BF_S_31_VALUE.equals(result)) {
				calresult = "31";
			}else if(LotteryConstant.DC_BF_S_32_VALUE.equals(result)) {
				calresult = "32";
			}else if(LotteryConstant.DC_BF_S_40_VALUE.equals(result)) {
				calresult = "40";
			}else if(LotteryConstant.DC_BF_S_41_VALUE.equals(result)) {
				calresult = "41";
			}else if(LotteryConstant.DC_BF_S_42_VALUE.equals(result)) {
				calresult = "42";
			}else if(LotteryConstant.DC_BF_P_0_VALUE.equals(result)) {
				calresult = "00";
			}else if(LotteryConstant.DC_BF_P_1_VALUE.equals(result)) {
				calresult = "11";
			}else if(LotteryConstant.DC_BF_P_2_VALUE.equals(result)) {
				calresult = "22";
			}else if(LotteryConstant.DC_BF_P_3_VALUE.equals(result)) {
				calresult = "33";
			}else if(LotteryConstant.DC_BF_F_01_VALUE.equals(result)) {
				calresult = "01";
			}else if(LotteryConstant.DC_BF_F_02_VALUE.equals(result)) {
				calresult = "02";
			}else if(LotteryConstant.DC_BF_F_12_VALUE.equals(result)) {
				calresult = "12";
			}else if(LotteryConstant.DC_BF_F_03_VALUE.equals(result)) {
				calresult = "03";
			}else if(LotteryConstant.DC_BF_F_13_VALUE.equals(result)) {
				calresult = "13";
			}else if(LotteryConstant.DC_BF_F_23_VALUE.equals(result)) {
				calresult = "23";
			}else if(LotteryConstant.DC_BF_F_04_VALUE.equals(result)) {
				calresult = "04";
			}else if(LotteryConstant.DC_BF_F_14_VALUE.equals(result)) {
				calresult = "14";
			}else if(LotteryConstant.DC_BF_F_24_VALUE.equals(result)) {
				calresult = "24";
			}else if(LotteryConstant.DC_BF_S_Ohter_VALUE.equals(result)) {
				calresult = "90";
			}else if(LotteryConstant.DC_BF_P_Other_VALUE.equals(result)) {
				calresult = "99";
			}else if(LotteryConstant.DC_BF_F_Other_VALUE.equals(result)) {
				calresult = "09";
			}
		}else if(LotteryType.DC_SFGG==type) {
			if(LotteryConstant.DC_SFGG_S_VALUE.equals(result)) {
				calresult = "3";
			}else if(LotteryConstant.DC_SFGG_F_VALUE.equals(result)) {
				calresult = "0";
			}
		}
		
		return calresult;
	}
}
